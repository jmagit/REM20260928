package com.example.subscribers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;

public class CacheSubscriber implements Flow.Subscriber<Integer> {
	private Flow.Subscription subscription;
	private List<Integer> cache = new ArrayList<Integer>();
	private final int MAX_CACHE;
	
	public CacheSubscriber(int maxCache) {
		MAX_CACHE = maxCache;
	}
	
	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(MAX_CACHE);
	}

	@Override
	public void onNext(Integer item) {
		cache.add(item);
		if(cache.size() == MAX_CACHE) {
			pinta();
			cache.clear();
			subscription.request(MAX_CACHE);
		}
	}

	@Override
	public void onError(Throwable error) {
		pinta();
		error.printStackTrace();
	}

	@Override
	public void onComplete() {
		pinta();
		System.out.println("CacheSubscriber onComplete");
	}
	
	public void pinta() {
		System.out.println(">>>> Elementos de la cache");
		cache.forEach(System.out::println);
		System.out.println("<<<< Elementos de la cache");
	}

}
