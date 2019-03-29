package pruebas;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import es.uam.padsof.objetoreproducible.Album;
import es.uam.padsof.objetoreproducible.Cancion;
import es.uam.padsof.sistema.Notificacion;
import es.uam.padsof.sistema.Sistema;
import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.exceptions.Mp3PlayerException;

public class Demostrador {	
	public static void main(String[] args) throws IOException, Mp3PlayerException, InterruptedException, ClassNotFoundException {
		Sistema sys = Sistema.getInstance() ;
//		UsuarioRegistrado u1;
//		UsuarioRegistrado u2;
//		Cancion c1;
//		Cancion c2; 
//		Cancion c3;
//		Album a1;
//		Cancion c4;
//		Album a2;
//		Notificacion n1;
//		Notificacion n2;
//		Notificacion n3;
//		
//		u1 = new UsuarioRegistrado("usuario1", "pass", false, false);
//		u2 = new UsuarioRegistrado("usuario2", "pass123", true, false);
//		u2.setFechaPremium(LocalDate.now().minusDays(14));
//		sys.addUsuario(u1);
//		sys.addUsuario(u2);
//		c1 = new Cancion("Cancion 1", u1, "GB _10sec _video.mp3");
//		c2 = new Cancion("Cancion 2", u2, "hive.mp3");
//		c3 = new Cancion("Cancion 3", u1, "chicle3.mp3");
//		c4 = new Cancion("Cancion 4", u2, "hola.mp3");
//		sys.anadirReproducible(c1);
//		sys.anadirReproducible(c2);
//		sys.anadirReproducible(c3);
//		sys.anadirReproducible(c4);
		sys.guardarSistema();
	}
}
