package com.example.contracts.domains;

import java.util.List;

public interface CRUDDomainService<E, K> {
	List<E> getAll();
	E getOne(K id);
	
	void add(E item);
	void modify(E item);
	void delete(E item);
	void deleteById(K id);
}
