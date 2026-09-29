module com.example.domains.services {
	requires transitive com.example.domains.entities;
	requires com.example.contracts;
	requires com.example.domain.exceptions;
 	requires com.example.util;
	
	exports com.example.domains.services;
	
	provides com.example.contracts.Config with com.example.domains.services.config.ConfigImpl;
}