package com.example.domains.entities;

import java.time.LocalDate;

public class AlumnoImpl extends PersonaImpl implements Alumno {
	private double nota;
	
	public AlumnoImpl(int id, String nombre, String apellidos, LocalDate fechaNacimiento, double nota) {
		super(id, nombre, apellidos, fechaNacimiento);
		this.nota = nota;
	}

	@Override
	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	@Override
	public String toString() {
		return "Alumno [nombre=" + getNombreCompleto() + " nota=" + nota + "]";
	}
	

}
