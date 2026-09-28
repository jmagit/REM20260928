package curso;

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
	}
}
