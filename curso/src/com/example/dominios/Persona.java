package com.example.dominios;

import java.util.NoSuchElementException;
import java.util.Optional;

public abstract class Persona {
	protected String nombre;
	protected String apellidos;

	public Persona(String nombre) {
		super();
		
		setNombre(nombre);
	}
	public Persona(String nombre, String apellidos) {
		this(nombre);
		assert apellidos != null : "Apellidos es nulo";
		setApellidos(apellidos);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		if(nombre == null || nombre.isBlank()) throw new IllegalArgumentException("nombre no puede ser nulo");
		this.nombre = nombre;
	}
	public String getApellidos() {
		if(apellidos == null) throw new NoSuchElementException("Apellidos es nulo");
		return apellidos;
	}
	public boolean hasApellidos() {
		return apellidos != null;
	}
	
	public Optional<String> getApellidosRecomendado() {
		return Optional.ofNullable(apellidos);
	}
	public void setApellidos(String apellidos) {
		if(apellidos == null || apellidos.isBlank()) throw new IllegalArgumentException("apellidos no puede ser nulo");
		this.apellidos = apellidos.toUpperCase();
	}
	public void clearApellidos() {
		this.apellidos = null;
	}
	
}
