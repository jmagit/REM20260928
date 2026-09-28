package com.example;

public class Alumno extends Persona {
	protected String apellidos;

	public Alumno(String nombre) {
		if(nombre == null || nombre.isBlank()) throw new IllegalArgumentException("nombre no puede ser nulo");
		super(nombre);
	}
	public Alumno(String nombre, String apellidos) {
		super(nombre, apellidos);
	}
}
