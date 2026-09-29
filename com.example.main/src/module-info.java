module com.example.main {
	exports com.example.main;
	requires transitive com.example.domains.services;
	requires transitive com.example.infraestructure;
	
	requires com.example.contracts;
	uses com.example.contracts.Config;
}