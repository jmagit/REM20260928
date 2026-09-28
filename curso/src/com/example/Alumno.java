package com.example;

public class Alumno extends Persona {
	protected String apellidos;

	public Alumno(String nombre) {
		if(nombre == null) throw new IllegalArgumentException("nombre no puede ser nulo");
		super(nombre);
	}
}
