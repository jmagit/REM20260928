package com.example;

import module java.base;

public class Tarea implements Callable<Integer> {
	private final int number;
	public Tarea(int number) { 
		this.number = number;
	}
	@Override
	public Integer call() {
		var name = Thread.currentThread().isVirtual() ? "Virtual" : Thread.currentThread().getName();
		System.out.println("Hilo %s - Tarea %d esperando...".formatted(name, number));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("Hilo %s - Tarea %d cancelada.".formatted(name, number));
			return -1;
		}
		System.out.println("Hilo %s - Tarea %d terminada.".formatted(name, number));
		return ThreadLocalRandom.current().nextInt(100);
	}
}
