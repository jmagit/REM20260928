package com.example.domains;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class Persona {
	protected String nombre;
	protected String apellidos;

	public Persona(@NonNull String nombre) {
		super();
		
		setNombre(nombre);
	}
	public Persona(String nombre, String apellidos) {
		this(nombre);
	
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
		return apellidos;
	}
	public void setApellidos(@NonNull String apellidos) {
		if(apellidos == null || apellidos.isBlank()) throw new IllegalArgumentException("apellidos no puede ser nulo");
		this.apellidos = apellidos.toUpperCase();
	}
	public void clearApellidos() {
		this.apellidos = null;
	}
	
}
