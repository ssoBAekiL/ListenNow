package pruebas;

import java.time.LocalDate;
import java.util.ArrayList;

import es.uam.padsof.usuario.UsuarioRegistrado;

public class TestLogin {
	
	public static void main(String[] args) {
		String usuario = "Gianchi";
		String contrasena = "54321";
		ArrayList<UsuarioRegistrado> usuarios =  new ArrayList<UsuarioRegistrado>();
	//(String nombre, String contrasena, boolean esPremium, LocalDate fechaPremium,int reproducciones, boolean bloqueado)
		UsuarioRegistrado a = new UsuarioRegistrado("Pablo", "12345", false, null, 0, false);
		UsuarioRegistrado b = new UsuarioRegistrado("Gianchi", "54321", false, null, 0, false);
		UsuarioRegistrado c = new UsuarioRegistrado("Admin", "asdfg", false, null, 0, true);
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
