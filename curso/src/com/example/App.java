package com.example;

import java.util.Objects;

///
/// # Clase de ejemplos del curso
/// 
/// Parrafada con los *comentarios* que explican la **clase**
public class App {

	///
	/// Metodo principal
	/// @param args Argumentos ...
	public static void main(String[] args) {
		ejemplo1();

	}

	static void ejemplo1() {
		String s = "SELECT * "
				+ "FROM table";
		s = """
				SELECT *♾️
				FROM table
				""";
		System.out.println(s);
		s = """
1
				2""";
		System.out.println(s.length());
		Object object = null;
		
		if(object instanceof String) {
			var aux = (String)object;
			System.out.println(aux.length());
		}
		if(object instanceof String aux) {
//			var aux = (String)object;
			System.out.println(aux.length());
		}
		int key = 4;
//		switch (key) {
//		case 1:
//		case 3:
//		case 5:
//		case 7:
//			s = "impar";
//			break;
//		case 2,4,6,8: {s = "par"; break;}
//		default:
//			throw new IllegalArgumentException("Unexpected value: " + key);
//		}
		switch (key) {
		case 1,3,5,7 -> {
			s = "impar";
			}
		case 2,4,6,8 -> {s = "par"; break;}
		default -> throw new IllegalArgumentException("Unexpected value: " + key);
		}
		
		s = switch (key) {
			case 1,3,5,7 -> "impar";
			case 2,4,6,8 -> "par";
			default -> throw new IllegalArgumentException("Unexpected value: " + key);
		};
		System.out.println(s);
		var p = new Alumno("kk", null);
		p.setApellidos(null);
		
		var a = p.getApellidosRecomendado();
		System.out.println(a.orElse("").toLowerCase());
		if(a.isPresent()) {
			System.out.println(a.get());
		}
		try {
			fichero();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.runFinalization();
		fichero();
		
//		var juego = new Ajedrez();
//		var t = juego.getTablero();
//		t.ponPieza(1, 1, new Pieza());
		var punto = new Punto(10, 9);
		System.out.println(punto.equals(new Punto(10, 9)) ? "igual": "distinto");
		
		// punto.setX(0);
		
		var coor = new Coordenada(4, 5);
		coor.cuadrante();
		System.out.println(coor.equals(new Coordenada(4, 5)) ? "igual": "distinto");
		System.out.println(coor);
		
	}
	
	static record Coordenada(int x, int y) {
		public byte cuadrante() {
			if(x > 0 && y > 0) return 1;
			return 0;
		}
	}
	
	static class Punto {
		public final int x, y;

		public Punto(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

//		public void setX(int x) {
//			this.x = x;
//		}

		public int getY() {
			return y;
		}
//
//		public void setY(int y) {
//			this.y = y;
//		}

		@Override
		public int hashCode() {
			return Objects.hash(Integer.valueOf(x), Integer.valueOf(y));
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Punto other = (Punto) obj;
			return x == other.x && y == other.y;
		}
		
	}
	static void fichero() {
		var f = new Fichero();
		f.escribe();
		try {
			f.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			f.escribe();			
		} catch (Exception e) {
			throw new CursoException("Algo ha fallado", e);
		}
		
//		try(var f = new Fichero()) {
//			f.escribe();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
