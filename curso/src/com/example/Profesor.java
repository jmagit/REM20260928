package com.example;

public class Profesor extends Persona {
	protected double salario = 0;
	
	public Profesor(String nombre) {
		if(nombre == null || nombre.isBlank()) throw new IllegalArgumentException("nombre no puede ser nulo");
		super(nombre);
	}
	public Profesor(String nombre, String apellidos) {
		super(nombre, apellidos);
	}
	public Profesor(String nombre, String apellidos, double salario) {
		this(nombre, apellidos);
		this.salario = salario;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public double getSalario() {
		return salario;
	}
	public void setSalario(double salario) {
		this.salario = salario;
	}
	@Override
	public String toString() {
		return "Profesor [nombre=" + nombre + ", apellidos=" + apellidos + ", salario=" + salario + "]";
	}
	
	
}
