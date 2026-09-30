package com.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import com.example.domain.services.NotificationService;
import com.example.domain.services.NotificationService.Notification;
import com.example.domain.services.NotificationService.Types;
import com.example.infrastructure.repository.ProductoRepository;
import com.example.presentation.console.ConsoleNotificationDisplay;
import com.example.presentation.web.PageNotificationDisplay;
import com.example.security.AuthService;
import com.example.subscribers.CacheSubscriber;
import com.example.subscribers.LoggerRegister;
import com.example.subscribers.PrintSubscriber;
import com.example.util.FilterProcessor;
import com.example.util.GenericProcessor;
import com.example.util.Sleeper;

public class App {

	public static void main(String[] args) {
		notificaciones();
	}
	public static void notificaciones() {
		NotificationService srv = new NotificationService();
		var errores = new FilterProcessor<Notification>(item -> item.type() == Types.ERROR);
		var cadenas = new GenericProcessor<Notification, String>(item -> item.message());
		var toInt = new GenericProcessor<String, Integer>(item -> item.length());
        var cache = new CacheSubscriber(10);
		srv.subscribe(errores);
		errores.subscribe(cadenas);
//		cadenas.subscribe(new LoggerRegister());
		cadenas.subscribe(toInt);
		toInt.subscribe(cache);
		versionConsola(srv);
//		versionWeb(srv);
//		srv.setOutput(item -> System.out.println("OUTPUT: " + item));
		var auth = new AuthService(srv);
		auth.login(null, null);
		var dao = new ProductoRepository(srv);
		IntStream.range(10, 20).forEach(item -> {
			dao.add(item);
			Sleeper.sleep(0);
			});
		
		System.out.println("Lista de notificaciones");
		srv.getNotifications().forEach(item -> System.out.println(item.message()));
//		System.out.println("Fin");
		if(srv.getExecutor() instanceof ExecutorService executor)
			try {
				executor.awaitTermination(10, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		else
			Sleeper.sleep(10000);		
	}
	
	public static void versionConsola(NotificationService srv) {
		srv.subscribe(new ConsoleNotificationDisplay());
	}
	public static void versionWeb(NotificationService srv) {
		srv.subscribe(new PageNotificationDisplay());
	}	
	public static void ejemplos() {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        Flow.Subscriber<Integer> display = new PrintSubscriber();
        Flow.Subscriber<Integer> cache = new CacheSubscriber(10);
        Flow.Processor<Integer, Integer> processor = new GenericProcessor<>(item -> item * 2);

        publisher.subscribe(cache);
        publisher.subscribe(processor);
        processor.subscribe(display);

        IntStream.range(0, 10).forEach(item -> {
            System.out.println("Generated item: " + item);
            publisher.submit(item);
            Sleeper.sleep(500);
        });

        publisher.close();
        System.out.println("Publisher completed and closed");
        Sleeper.sleep(6000);
	}

	
}
