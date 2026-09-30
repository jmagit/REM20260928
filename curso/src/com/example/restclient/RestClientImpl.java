package com.example.restclient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class RestClientImpl implements RestClient {
	private final String URL_BASE;
	private final HttpClient cliente;

	public RestClientImpl(String url) {
		URL_BASE = url;
//		cliente = HttpClient.newHttpClient();
		cliente = HttpClient.newBuilder()
				.version(Version.HTTP_1_1)
				.followRedirects(Redirect.NORMAL)
				.connectTimeout(Duration.ofSeconds(10))
				.build();
	}

	@Override
	public String get() {
		return get("");
	}
	@Override
	public CompletableFuture<String> getAsync() {
		return getAsync("");
	}
	@Override
	public String get(String queryParams) {
		if(queryParams == null)
			throw new IllegalArgumentException("Null argument");
		return sendRequest(getRequest(null, queryParams));
	}
	@Override
	public CompletableFuture<String> getAsync(String queryParams) {
		if(queryParams == null)
			throw new IllegalArgumentException("Null argument");
		return cliente.sendAsync(getRequest(null, queryParams), HttpResponse.BodyHandlers.ofString())
				.thenApply(response -> responseHandler(response));
	}

	@Override
	public String get(int id) {
		return sendRequest(getRequest(id, ""));
	}
	@Override
	public CompletableFuture<String> getAsync(int id) {
		return cliente.sendAsync(getRequest(id, ""), HttpResponse.BodyHandlers.ofString())
				.thenApply(response -> responseHandler(response));
	}

	private HttpRequest getRequest(Integer id, String queryParams) {
		return HttpRequest.newBuilder()
				.uri(URI.create(URL_BASE + (id == null ? "" : "/" + id) 
						+ (queryParams.isBlank() ? "" : "?" + queryParams.replace(" ", "%20"))))
				.header("Accept", "application/json")
				.build();
	}

	@Override
	public String post(String json) {
		return sendRequest(postRequest(json));
	}
	@Override
	public CompletableFuture<String> postAsync(String json) {
		return cliente.sendAsync(postRequest(json), HttpResponse.BodyHandlers.ofString())
				.thenApply(response -> responseHandler(response));
	}

	private HttpRequest postRequest(String json) {
		if(json == null)
			throw new IllegalArgumentException("Null argument");
		return HttpRequest.newBuilder()
				.uri(URI.create(URL_BASE))
				.header("Accept", "application/json")
	            .header("Content-Type", "application/json; charset=UTF-8")
	            .POST(HttpRequest.BodyPublishers.ofString(json))
	            .build();
	}

	@Override
	public String put(int id, String json) {
		return sendRequest(putRequest(id, json));
	}
	@Override
	public CompletableFuture<String> putAsync(int id, String json) {
		return cliente.sendAsync(putRequest(id, json), HttpResponse.BodyHandlers.ofString())
				.thenApply(response -> responseHandler(response));
	}

	private HttpRequest putRequest(int id, String json) {
		if(json == null)
			throw new IllegalArgumentException("Null argument");
		return HttpRequest.newBuilder()
				.uri(URI.create(URL_BASE +  "/" + id))
				.header("Accept", "application/json")
	            .header("Content-Type", "application/json; charset=UTF-8")
	            .PUT(HttpRequest.BodyPublishers.ofString(json))
	            .build();
	}
	
	@Override
	public String delete(int id) {
		return sendRequest(deleteRequest(id));
	}
	@Override
	public CompletableFuture<String> deleteAsync(int id) {
		return cliente.sendAsync(deleteRequest(id), HttpResponse.BodyHandlers.ofString())
				.thenApply(response -> responseHandler(response));
	}

	private HttpRequest deleteRequest(int id) {
		return HttpRequest.newBuilder()
				.uri(URI.create(URL_BASE +  "/" + id))
	            .DELETE()
	            .build();
	}

	private String sendRequest(HttpRequest solicitud) {
		try {
			var response = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());
			return responseHandler(response);
		} catch (IOException | InterruptedException e) {
			throw new HttpException(0, e.getClass().getSimpleName() + " " + e.getMessage(), e);
		}
	}

	private String responseHandler(HttpResponse<String> response) {
		if (200 <= response.statusCode() && response.statusCode() < 300)
			return response.body();
		if ("{}".equals(response.body()))
			throw new HttpException(response.statusCode());
		else
			throw new HttpException(response.statusCode(), response.body());
	}

}
