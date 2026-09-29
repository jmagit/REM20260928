package com.example.main;

import java.time.LocalDate;
import java.util.ServiceLoader;

import com.example.contracts.Config;
import com.example.domains.entities.ProfesorImpl;
import com.example.domains.services.PersonaServiceImpl;
import com.example.infraestructure.repositories.PersonaRepository;


public class Principal {

	public static void main(String[] args) {
		ServiceLoader<Config> servicios = ServiceLoader.load(Config.class);
		servicios.forEach(Config::configure);
		
		try {
			var srv = new PersonaServiceImpl(new PersonaRepository());
			srv.modify(new ProfesorImpl(0, "Pepito", "Grillo", LocalDate.of(2000, 1, 1), "Java"));
			srv.add(new ProfesorImpl(0, "Pepito", "Grillo", LocalDate.of(2000, 1, 1), "Java"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
