package com.example;

public class Alumno extends Persona {
	public Alumno(String nombre) {
		if(nombre == null || nombre.isBlank()) throw new IllegalArgumentException("nombre no puede ser nulo");
		super(nombre);
	}
	public Alumno(String nombre, String apellidos) {
		super(nombre, apellidos);
	}
	@Override
	public String toString() {
		return "Alumno [nombre=" + nombre + ", apellidos=" + apellidos + "]";
	}
	
}
