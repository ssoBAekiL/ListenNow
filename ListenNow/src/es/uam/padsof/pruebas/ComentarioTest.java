package es.uam.padsof.pruebas;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import es.uam.padsof.objetoreproducible.Cancion;
import es.uam.padsof.objetoreproducible.Comentario;
import es.uam.padsof.sistema.Sistema;
import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * Clase que comprueba el correcto funcionamiento de la clase comentario
 * @author Julian Espada, Carlos Miret y Pablo Borrelli
 *
 */
public class ComentarioTest {
	Sistema sys=Sistema.getInstance();
	private UsuarioRegistrado u1;
	private UsuarioRegistrado u2;
	private Cancion c1;
	private Comentario comment;
	
	/**
	 * Metodo que se ejecuta antes de ejecutar cualquier 
	 * test, guardando independencia entre estos
	 * @throws Mp3PlayerException 
	 * @throws IOException 
	 * 
	 */
	@Before
	public void testComentario() throws IOException, Mp3PlayerException {
		u1=new UsuarioRegistrado("carlos","6253", false,false);
		sys.registrarse(u1);
		sys.setUsuarioEnSesion(u1);
		File ruta=new File("cancionesSistema/hive-1.mp3");
		ruta.delete();
		c1 = new Cancion("Cancion 1", u1, "hive-1.mp3");
		u2=new UsuarioRegistrado("pablo","872900", false,false);
		sys.registrarse(u2);
		sys.setUsuarioEnSesion(u2);
		comment=new Comentario("Que buena cancion!",u2,LocalDate.now(),8);
	}

	/**
	 * Test que comprueba si el comentario a una cancion es del usuario que esta en sesion
	 */
	@Test
	public final void testComentar() {
		c1.anadirComentario(comment);
		assertTrue(c1.getComentarios().contains(comment));
	}
}