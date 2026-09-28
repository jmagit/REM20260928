package com.example.selladas;

public abstract sealed class Figura permits Circulo, Rectangulo, Triangulo {
	private int color;

	public final int getColor() {
		return color;
	}

	protected void setColor(int color) {
		this.color = color;
	}
	
	public abstract double area();
	
}
