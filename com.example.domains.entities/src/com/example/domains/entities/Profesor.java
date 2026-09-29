package com.example.domains.entities;

public interface Profesor extends Persona {
	String getAsignatura();
	
//	String getNombreCompleto();
	
	default String getNombreCompleto() {
		return "El profe de " + getAsignatura();
	}
}
