package pruebas;

import java.time.LocalDate;
import java.util.ArrayList;

import es.uam.padsof.usuario.UsuarioRegistrado;

public class TestLogin {
	
	public static void main(String[] args) {
		String usuario = "ususario2";
		String contrasena = "54321";
		ArrayList<UsuarioRegistrado> usuarios =  new ArrayList<UsuarioRegistrado>();
	//(String nombre, String contrasena, boolean esPremium, LocalDate fechaPremium,int reproducciones, boolean bloqueado)
		UsuarioRegistrado a = new UsuarioRegistrado("ususario1", "12345", false, false);
		UsuarioRegistrado b = new UsuarioRegistrado("usuario2", "54321", false, false);
		UsuarioRegistrado c = new UsuarioRegistrado("Admin", "asdfg", false, true);
		usuarios.add(a);
		usuarios.add(b);
		usuarios.add(c);
		
		System.out.println(usuarios);
		
		for (UsuarioRegistrado u: usuarios) {
			if (u.getNombre() == usuario && u.getContrasena() == contrasena) {
				System.out.println("Yes");
				return;
			}
		}
		System.out.println("No");
	}
}
