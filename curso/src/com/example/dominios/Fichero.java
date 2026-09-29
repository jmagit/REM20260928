package com.example.dominios;

public class Fichero implements AutoCloseable {
	static boolean open = false;
	String file = "";
	
	public Fichero() {
		if(open) throw new RuntimeException("No se puede abrir el fichero");
		file = "open";
		open = true;
	}
	
	public void escribe() {
		if(!open) throw new RuntimeException("No se puede escribir el fichero");
	}
	
	@Override
	protected void finalize() throws Throwable {
		if(open) close();
		super.finalize();
	}

	@Override
	public void close() throws Exception {
		open = false;
		System.err.println("Cierra el fichero");
	}
}
