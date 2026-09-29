package com.example.domains.entities;

import com.example.util.anotations.Autor;

@Autor(nombre = "Yo")
public interface Profe {

	String getNombre();

	String getApellidos();

	String getNombreCompleto();

	String getAsignatura();

}