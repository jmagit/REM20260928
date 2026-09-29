package com.example.util.base;

import java.lang.reflect.InvocationTargetException;

import com.example.util.interno.Formato;

public class KeyValue<K, V> {
	private K key;
	private V value;
	
	public KeyValue(K key, V value) {
		this.key = key;
		this.value = value;
	}
	@SuppressWarnings("unchecked")
	public KeyValue(Class<K> tipo, V value) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException {
		this.key = (K)tipo.getConstructors()[0].newInstance();
		this.value = value;
	}
	
	public K getKey() {
		return key;
	}
	public void setKey(K key) {
		this.key = key;
	}

	@SuppressWarnings("unchecked")
	public V getValue() {
		if(value instanceof String s)
			return (V)Formato.destaca(s);
		return value;
	}
	public void setValue(V value) {
		this.value = value;
	}
	
	public <T> T calcula(T item, int otro) {
		return item;
	}
	@SuppressWarnings("deprecation")
	public <T> T conv(String cad, Class<T> tipo) throws InstantiationException, IllegalAccessException {
		return tipo.newInstance();
	}
	
	
//	
//	void dame(K item) {
//		
//	}
//	
//	void dame(V item) {
//		
//	}
}
