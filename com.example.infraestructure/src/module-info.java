module com.example.infraestructure {
	requires transitive com.example.domains.entities;
	requires com.example.contracts;
	requires java.sql;
	
	exports com.example.infraestructure.repositories;
	
	provides com.example.contracts.Config with com.example.infraestructure.config.ConfigImpl;
}