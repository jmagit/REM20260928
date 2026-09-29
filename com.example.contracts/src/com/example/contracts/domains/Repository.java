package com.example.contracts.domains;

public interface Repository<T> {
	T load();
	void save(T item);
}
