package com.example;

public class Tablero {
	private Pieza[][] piezas;
	
	public void ponPieza(int fila, int columna, Pieza pieza) {
		piezas[fila][columna] = pieza;
		
	}
	
	@Override
	public Object clone()  {
		// TODO Auto-generated method stub
		// return super.clone();
		var copia =  new Tablero();
		return copia;
	}
}
