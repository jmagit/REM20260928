package com.example.restclient;

import java.util.concurrent.CompletableFuture;

public interface RestClient {

	String get();
	String get(String queryParams);
	String get(int id);
	String post(String json);
	String put(int id, String json);
	String delete(int id);

	CompletableFuture<String> getAsync();
	CompletableFuture<String> getAsync(String queryParams);
	CompletableFuture<String> getAsync(int id);
	CompletableFuture<String> postAsync(String json);
	CompletableFuture<String> putAsync(int id, String json);
	CompletableFuture<String> deleteAsync(int id);

}