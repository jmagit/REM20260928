package com.example.domains.entities;

import java.time.LocalDate;

public class EmpleadoAlumno extends PersonaImpl implements Empleado {

	public EmpleadoAlumno(int id, String nombre, String apellidos, LocalDate fechaNacimiento) {
		super(id, nombre, apellidos, fechaNacimiento);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getSalario() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSalario(double salario) {
		// TODO Auto-generated method stub
		
	}


}
