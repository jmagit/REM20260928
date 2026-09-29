package com.example.infraestructure.config;

import com.example.contracts.Config;

public class ConfigImpl implements Config {

	@Override
	public void configure() {
		System.out.println("Configurando com.example.infraestructure.config");
	}

}
