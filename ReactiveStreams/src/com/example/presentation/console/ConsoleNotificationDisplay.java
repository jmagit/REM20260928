package com.example.presentation.console;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;

import com.example.domain.services.NotificationService.Notification;
import com.example.domain.services.NotificationService.Types;

public class ConsoleNotificationDisplay implements Flow.Subscriber<Notification> {
	private Flow.Subscription subscription;
	private boolean continuar = true;
	
	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(1);
	}

	@Override
	public void onNext(Notification item) {
		if(item.type() == Types.ERROR)
			System.err.println(item.message());
		else
			System.out.printf("%s: %s\n", item.type().toString().toUpperCase(), item.message());
		if(continuar) subscription.request(1);
	}

	@Override
	public void onError(Throwable error) {
		error.printStackTrace();
	}

	@Override
	public void onComplete() {
	}
	
	public void cancel() {
		continuar = false;
	}
	
	public void continua() {
		continuar = true;
		subscription.request(1);
	}

}
