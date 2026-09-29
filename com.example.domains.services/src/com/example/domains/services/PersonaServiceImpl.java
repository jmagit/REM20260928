package com.example.domains.services;

import java.util.List;

import com.example.contracts.domains.CRUDDomainService;
import com.example.contracts.domains.Repository;
import com.example.domain.exceptions.InvalidDataException;
import com.example.domains.entities.Persona;
import com.example.util.validadores.CadenasValidator;

public class PersonaServiceImpl implements CRUDDomainService<Persona, Integer> {
	private Repository<Persona> dao;
	
	public PersonaServiceImpl(Repository<Persona> dao) {
		this.dao = dao;
	}

	@Override
	public List<Persona> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Persona getOne(Integer id) {
		return dao.load();
	}

	@Override
	public void add(Persona item) {
		if(item == null || item.isInvalid())
			throw new InvalidDataException();
		System.out.println("Añadida persona");
	}

	@Override
	public void modify(Persona item) {
		if(item == null || item.isInvalid())
			throw new InvalidDataException();
		dao.save(item);
	}

	@Override
	public void delete(Persona item) {
		if(CadenasValidator.isNotBlank(""))
			throw new IllegalArgumentException("El npmbre no puede estar en blanco");

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

}
