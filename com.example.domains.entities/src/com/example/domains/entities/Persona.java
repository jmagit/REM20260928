package com.example.domains.entities;

import java.time.LocalDate;

public interface Persona extends Comparable<Persona> {
	int getId();
	String getNombre();
	String getApellidos();
	LocalDate getFechaNacimiento();
	
	boolean isValid();
	
	default boolean isInvalid() {
		return !isValid();
	}
	
	default String getApellidosNombre() {
		return getApellidos() + ", " + getNombre();
	}
	
	default String getNombreCompleto() {
		return getNombre() + " " + getApellidos();
	}
}
