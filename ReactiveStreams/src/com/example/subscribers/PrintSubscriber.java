package com.example.subscribers;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;

import com.example.util.Sleeper;

public class PrintSubscriber implements Flow.Subscriber<Integer> {
	private Flow.Subscription subscription;
	private boolean continuar = true;
	private boolean isCancel = false;
	
	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(1);
	}

	@Override
	public void onNext(Integer item) {
		System.out.println("PrintSubscriber: " + item);
		Sleeper.sleep(1000);
		if(continuar && !isCancel) subscription.request(1);
	}

	@Override
	public void onError(Throwable error) {
		error.printStackTrace();
	}

	@Override
	public void onComplete() {
		System.out.println("PrintSubscriber onComplete");
	}
	
	
	public void cancel() {
		isCancel = true;
		subscription.cancel();
	}

	public void pause() {
		continuar = false;
	}
	
	public void continua() {
		if(isCancel) throw new IllegalStateException("Is cancel");
		continuar = true;
		subscription.request(1);
	}
}
