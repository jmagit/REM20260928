package com.example.infraestructure.repositories;

import java.time.LocalDate;

import com.example.contracts.domains.Repository;
import com.example.domains.entities.Persona;
import com.example.domains.entities.ProfesorImpl;

public class PersonaRepository implements Repository<Persona> {

	@Override
	public Persona load() {
		return new ProfesorImpl(0, "Pepito", "Grillo", LocalDate.of(2000, 1, 1), "Java");
	}

	@Override
	public void save(Persona item) {
		System.out.println("Guardando en la base de datos: " + item);
	}

}
