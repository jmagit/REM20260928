package com.example.domains.entities;

import java.time.LocalDate;

public class ProfesorImpl extends PersonaImpl implements Profesor, Empleado {
	private String asignatura;
	private double salario;

	public ProfesorImpl(int id, String nombre, String apellidos, LocalDate fechaNacimiento, String asignatura) {
		super(id, nombre, apellidos, fechaNacimiento);
		this.asignatura = asignatura;
		salario = nombre.hashCode() % 10000;
	}

	@Override
	public String getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}

	public double getSalario() {
		return Math.abs(salario);
	}

	public void setSalario(double salario) {
		this.salario = Math.abs(salario);
	}

	@Override
	public String toString() {
		return "Profesor [Nombre=" + getNombreCompleto() + " Asignatura=" + asignatura + "]";
	}

}
