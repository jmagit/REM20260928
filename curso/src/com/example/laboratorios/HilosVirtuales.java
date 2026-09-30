package com.example.laboratorios;

import module java.base;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HilosVirtuales {

	/// # Creating Virtual Threads
	///
	/// The factory method `Executors.newVirtualThreadPerTaskExecutor()` yields an
	/// ExecutorService that runs each task in a separate virtual thread.
	public void virtualThreadDemo1() {
		final int NTASKS = 100;
		try (ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()) {
			for (int i = 0; i < NTASKS; i++) {
				service.submit(() -> {
					long id = Thread.currentThread().threadId();
					LockSupport.parkNanos(1_000_000_000);
					IO.println(id);
				});
			}
		}
	}

	/// # Capturing Task Results
	///
	/// You often want to combine the results of multiple concurrent tasks
	public void virtualThreadDemo2() throws InterruptedException, ExecutionException {
		try (ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()) {
			Future<String> f1 = service.submit(() -> get("https://horstmann.com/random/adjective"));
			Future<String> f2 = service.submit(() -> get("https://horstmann.com/random/noun"));
			String result = f1.get() + " -> " + f2.get();
			IO.println(result);
		}
	}

	private HttpClient client = HttpClient.newHttpClient();

	private String get(String url) {
		try {
			var request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
			return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
		} catch (Exception ex) {
			var rex = new RuntimeException();
			rex.initCause(ex);
			throw rex;
		}
	}

	/// # Capturing Task Results **more concrete sample**
	public void virtualThreadDemo3() throws InterruptedException, ExecutionException {
		try (ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()) {
			List<Callable<String>> callables = new ArrayList<>();
			final int ADJECTIVES = 4;
			for (int i = 1; i <= ADJECTIVES; i++)
				callables.add(() -> get("https://horstmann.com/random/adjective"));
			callables.add(() -> get("https://horstmann.com/random/noun"));
			List<String> results = new ArrayList<>();
			for (Future<String> f : service.invokeAll(callables))
				results.add(f.get());
			IO.println(String.join(" ", results));
		}
	}

	/// # Rate Limiting
	///
	/// *Virtual threads* improve application throughput since you can have many more
	/// concurrent tasks than with platform threads. That can put pressure on the
	/// services that the tasks invoke. For example, a web service may not tolerate
	/// huge numbers of concurrent requests.
	public void virtualThreadDemo4() throws InterruptedException, ExecutionException {
		try (ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()) {
			List<Future<String>> futures = new ArrayList<>();
			final int TASKS = 250;
			for (int i = 1; i <= TASKS; i++)
				futures.add(service.submit(() -> getWithSemaphore("https://horstmann.com/random/word")));
			for (Future<String> f : futures)
				IO.print(f.get() + " ");
			IO.println();
		}
	}

	private final Semaphore SEMAPHORE = new Semaphore(20);

	private String getWithSemaphore(String url) {
		try {
			var request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
			SEMAPHORE.acquire();
			try {
				Thread.sleep(100);
				return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
			} finally {
				SEMAPHORE.release();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			var rex = new RuntimeException();
			rex.initCause(ex);
			throw rex;
		}
	}

	/// # Pinning
	///
	/// The virtual thread scheduler mounts virtual threads onto carrier threads. By
	/// default, there are as many carrier threads as there are CPU cores. You can
	/// tune that count with the `jdk.virtualThreadScheduler.parallelism` VM option.
	public void pinningDemo() throws InterruptedException, ExecutionException {
		ExecutorService service = Executors.newVirtualThreadPerTaskExecutor();
		// Executors.newCachedThreadPool();

		final int TASKS = 20;
		long start = System.nanoTime();
		for (int i = 1; i <= TASKS; i++) {
			// service.submit(() -> block());
			service.submit(() -> rblock());
		}
		for (int i = 1; i <= TASKS; i++) {
			service.submit(() -> noblock());
		}
		service.close();
		long end = System.nanoTime();
		System.out.printf("%.2f%n", (end - start) * 1E-9);
	}

	private synchronized void block() {
		IO.println("Entering block " + Thread.currentThread());
		LockSupport.parkNanos(1_000_000_000);
		IO.println("Exiting block " + Thread.currentThread());
	}

	private Lock lock = new ReentrantLock();

	private void rblock() {
		lock.lock();
		try {
			IO.println("Entering rblock " + Thread.currentThread());
			LockSupport.parkNanos(1_000_000_000);
			IO.println("Exiting rblock " + Thread.currentThread());
		} finally {
			lock.unlock();
		}
	}

	private void noblock() {
		IO.println("Entering noblock " + Thread.currentThread());
		LockSupport.parkNanos(1_000_000_000);
		IO.println("Exiting noblock " + Thread.currentThread());
	}

}
