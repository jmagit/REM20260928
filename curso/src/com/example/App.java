package com.example;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Function;
import java.util.stream.Gatherers;

import com.example.dominios.Alumno;
import com.example.dominios.Fichero;
import com.example.dominios.Persona;
import com.example.dominios.Profesor;
import com.example.restclient.HttpException;
import com.example.restclient.RestClient;
import com.example.restclient.RestClientImpl;

///
/// # Clase de ejemplos del curso
/// 
/// Parrafada con los *comentarios* que explican la **clase**
public class App {

	///
	/// Metodo principal
	/// @param args Argumentos ...
	public static void main(String[] args) {
//		ejemplo1();
//		flujos();
//		recolectar();
//		try {
////			hilosDePlataforma(); // sum = 48943; time = 10148933300 ns
//			hilosVirtuales();   // sum = 494349; time = 3287750700 ns
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		clienteHTTP();
		clienteRest();
	}
	
	static void clienteRest() {
		RestClient postProxy = new RestClientImpl("https://jsonplaceholder.typicode.com/posts");
		var json = """
		{
		  "userId": 1,
		  "id": 1,
		  "title": "hola mundo",
		  "body": "quia et suscipit"
		}
		""";

//		try {
//			IO.println("GET:\n" + postProxy.get(10));
//			IO.println("POST:\n" + postProxy.post(json));
//			IO.println("PUT:\n" + postProxy.put(22, postProxy.get(11)));
//			IO.println("DELETE:\n" + postProxy.delete(10));
//			IO.println(postProxy.get(1111));
//		} catch (HttpException e) {
//			System.err.println("ERROR %d %s".formatted(e.getStatusCode(), e.getMessage()).trim());
//		}
		
		Function<Throwable, ? extends Void> errorHandler = ex -> {
			if(ex.getCause() instanceof HttpException e)
				System.err.println("ERROR %d %s".formatted(e.getStatusCode(), e.getMessage()).trim());
			else
				System.err.println("ERROR %s %s".formatted(ex.getClass().getSimpleName(), ex.getMessage()));
			return null;
		};
		
		List<CompletableFuture<Void>> peticiones = List.of( 
				postProxy.getAsync(33)
					.thenApply(postProxy::post)
					.thenApply(item -> postProxy.put(44, item))
					.thenAccept(IO::println)
					.exceptionally(errorHandler),
//				postProxy.postAsync(json)
//					.thenApply(postProxy::post)
//					.thenApply(item -> postProxy.put(44, item))
//					.thenAccept(IO::println)
//					.exceptionally(errorHandler),
				postProxy.getAsync("title=nesciunt quas odio")
					.thenAccept(IO::println)
					.exceptionally(errorHandler),
				postProxy.getAsync(101)
					.thenAccept(IO::println)
					.exceptionally(errorHandler)
				);
		CompletableFuture.allOf(peticiones.toArray(new CompletableFuture[0])).join();

	}
	
	static void clienteHTTP() {
		HttpClient cliente = HttpClient.newBuilder()
				.version(Version.HTTP_2)
				.followRedirects(Redirect.NORMAL)
//				.proxy(ProxySelector.of(new InetSocketAddress("proxy.corp", 8080)))
				.build();
//		try {
//			HttpRequest solicitud = HttpRequest.newBuilder()
//					.uri(new URI("https://jsonplaceholder.typicode.com/posts/1"))
//					.GET()
//					.header("Accept", "application/json")
//					.timeout(Duration.ofSeconds(10))
//					.build();
//		    HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());
//		    System.out.println("Código de estado: " + respuesta.statusCode());
//		    System.out.println("Cuerpo de la respuesta:\n" + respuesta.body());
//		} catch (IOException | InterruptedException | URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		LockSupport.parkNanos(1_000);
		var json = """
{
  "userId": 1,
  "id": 1,
  "title": "hola mundo",
  "body": "quia et suscipit"
}
				""";
		try {
			HttpRequest solicitud = HttpRequest.newBuilder()
					.uri(new URI("https://jsonplaceholder.typicode.com/posts"))
					.POST(HttpRequest.BodyPublishers.ofString(json))
					.header("Content-type", "application/json; charset=UTF-8")
					.header("Accept", "application/json")
					.timeout(Duration.ofSeconds(10))
					.build();
		    HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());
		    System.out.println("Código de estado: " + respuesta.statusCode());
		    System.out.println("Cabecera location: " + respuesta.headers().firstValue("location").orElse("sin location"));
		    System.out.println("Cuerpo de la respuesta:\n" + respuesta.body());
		} catch (IOException | InterruptedException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void hilosDePlataforma() throws Exception {
		List<Tarea> tasks = new ArrayList<>();
		for (int i = 0; i < 1_000; i++) { tasks.add(new Tarea(i)); }
		long time = System.nanoTime(), sum = 0;

		 try (var executor = Executors.newFixedThreadPool(100)) {
			List<Future<Integer>> futures = executor.invokeAll(tasks);
			for (Future<Integer> future : futures) {
				sum += future.get();
			}
			time = System.nanoTime() - time;
			System.out.println("sum = " + sum + "; time = " + time + " ns");
		}
	}
	private static void hilosVirtuales() throws Exception {
		List<Tarea> tasks = new ArrayList<>();
		for (int i = 0; i < 10_000; i++) { tasks.add(new Tarea(i)); }
		long time = System.nanoTime(), sum = 0;

		try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
			List<Future<Integer>> futures = executor.invokeAll(tasks);
			for (Future<Integer> future : futures) {
				sum += future.get();
			}
			time = System.nanoTime() - time;
			System.out.println("sum = " + sum + "; time = " + time + " ns");
		}
	}

	static void recolectar() {
		var numeros = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
		IO.println("con fold ----------------");
		numeros.stream()
				.gather(Gatherers.fold(() -> "", (collector, number) -> collector + number))
				.findFirst().ifPresent(IO::println);
		IO.println("con scan \"\" ----------------");
		numeros.stream()
				.gather(Gatherers.scan(() -> "", (collector, number) -> collector + number))
				.forEach(IO::println);
		IO.println("con 0 ----------------");
		numeros.stream()
				.gather(Gatherers.scan(() -> 0, (collector, number) -> collector + number))
				.forEach(IO::println);
		IO.println("con windowFixed ----------------");
		numeros.stream()
				.gather(Gatherers.windowFixed(3))
				.forEach(IO::println);
		IO.println("con windowSliding ----------------");
		numeros.stream().gather(Gatherers.windowSliding(5))
				.forEach(IO::println);

	}
	static void flujos() {
		List<Persona> aula = new ArrayList<>();
		aula.add(new Profesor("p1","p1",1000));
		aula.add(new Profesor("p2","p2",3000));
		aula.add(new Alumno("a1"));
		aula.add(new Alumno("a2"));
		aula.add(new Alumno("a3", "a3"));
		aula.add(new Alumno("a4"));
		
		IO.println(aula.stream()
			.filter(o -> o instanceof Profesor)
			.map(o -> (Profesor)o)
			.map(o -> o.getSalario())
			.reduce(0.0, (acumulado, item) -> acumulado + item)
			);
		IO.println(aula.stream()
				.filter(o -> o instanceof Profesor)
				.map(o -> (Profesor)o)
				.mapToDouble(o -> o.getSalario())
				.sum()				
				);
		var q1 = aula.stream()
				.filter(o -> o instanceof Profesor)
				.map(o -> (Profesor)o)
				.mapToDouble(o -> o.getSalario());
		IO.println(q1.sum());
//		IO.println(q1.sum());
		boolean soloProfes = false, conApellidos = true, paginado = true;
		int page = 10, rows = 2;
		var q2 = aula.stream();
		if(soloProfes)
			q2 = q2.filter(o -> o instanceof Profesor);
		if(conApellidos)
			q2 = q2.filter(o -> o.hasApellidos());
		if(paginado)
			q2 = q2.skip(page * rows).limit(rows);
		q2.forEach(IO::println);
		
		List<Integer> listOfIntegers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
		System.out.println("Sequential Stream: ");
		listOfIntegers.stream()
			.peek(o -> {
			try {
				Thread.sleep(o * 100);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}).forEach(e -> System.out.print(e + " "));
		System.out.println("\nParallel Stream: ");
		listOfIntegers.stream().parallel().peek(o -> {
			try {
				Thread.sleep(o * 100);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}).sequential().forEach(e -> System.out.print(e + " "));

	}
	static void ejemplo1() {
		String s = "SELECT * "
				+ "FROM table";
		s = """
				SELECT *♾️
				FROM table
				""";
		System.out.println(s);
		s = """
1
				2""";
		System.out.println(s.length());
		Object object = null;
		
		if(object instanceof String) {
			var aux = (String)object;
			System.out.println(aux.length());
		}
		if(object instanceof String aux) {
//			var aux = (String)object;
			System.out.println(aux.length());
		}
		int key = 4;
//		switch (key) {
//		case 1:
//		case 3:
//		case 5:
//		case 7:
//			s = "impar";
//			break;
//		case 2,4,6,8: {s = "par"; break;}
//		default:
//			throw new IllegalArgumentException("Unexpected value: " + key);
//		}
		switch (key) {
		case 1,3,5,7 -> {
			s = "impar";
			}
		case 2,4,6,8 -> {s = "par"; break;}
		default -> throw new IllegalArgumentException("Unexpected value: " + key);
		}
		
		s = switch (key) {
			case 1,3,5,7 -> "impar";
			case 2,4,6,8 -> "par";
			default -> throw new IllegalArgumentException("Unexpected value: " + key);
		};
		System.out.println(s);
		var p = new Alumno("kk", null);
		p.setApellidos(null);
		
		var a = p.getApellidosRecomendado();
		System.out.println(a.orElse("").toLowerCase());
		if(a.isPresent()) {
			System.out.println(a.get());
		}
		try {
			fichero();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.runFinalization();
		fichero();
		
//		var juego = new Ajedrez();
//		var t = juego.getTablero();
//		t.ponPieza(1, 1, new Pieza());
		var punto = new Punto(10, 9);
		System.out.println(punto.equals(new Punto(10, 9)) ? "igual": "distinto");
		
		// punto.setX(0);
		
		var coor = new Coordenada(4, 5);
		coor.cuadrante();
		System.out.println(coor.equals(new Coordenada(4, 5)) ? "igual": "distinto");
		System.out.println(coor);
		
	}
	
	static record Coordenada(int x, int y) {
		public byte cuadrante() {
			if(x > 0 && y > 0) return 1;
			return 0;
		}
	}
	
	static class Punto {
		public final int x, y;

		public Punto(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

//		public void setX(int x) {
//			this.x = x;
//		}

		public int getY() {
			return y;
		}
//
//		public void setY(int y) {
//			this.y = y;
//		}

		@Override
		public int hashCode() {
			return Objects.hash(Integer.valueOf(x), Integer.valueOf(y));
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Punto other = (Punto) obj;
			return x == other.x && y == other.y;
		}
		
	}
	static void fichero() {
		var f = new Fichero();
		f.escribe();
		try {
			f.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			f.escribe();			
		} catch (Exception e) {
			throw new CursoException("Algo ha fallado", e);
		}
		
//		try(var f = new Fichero()) {
//			f.escribe();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
