/**
 * 
 */
package es.uam.padsof.pruebas;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import es.uam.padsof.objetoreproducible.Album;
import es.uam.padsof.objetoreproducible.Cancion;
import es.uam.padsof.objetoreproducible.Comentario;
import es.uam.padsof.objetoreproducible.ObjetoComentable;
import es.uam.padsof.sistema.Sistema;
import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * Clase test del objeto comentable, que prueba las funciones de la clase Objeto Comentable
 * @author Carlos Miret, Julian Espada y Pablo Borrelli
 *
 */
public class ObjetoComentableTest {
	Sistema sys=Sistema.getInstance();
	private UsuarioRegistrado u1;
	private UsuarioRegistrado u2;
	private  ObjetoComentable obj_commentA;
	private  ObjetoComentable obj_commentB;
	private Comentario comment;
	
	/**
	 * Funcion que se ejecuta antes de cualquier test con independencia entre ellos
	 * @throws Mp3PlayerException 
	 * @throws IOException 
	 * @throws java.lang.Exception
	 */
	@Before
	public void testObjetoComentable() throws Mp3PlayerException, IOException{
		u1=new UsuarioRegistrado("carlos","6253", false,false);
		sys.registrarse(u1);
		File ruta=new File("cancionesSistema/chicle3.mp3");
		ruta.delete();
		u2=new UsuarioRegistrado("julian","6565",false,false);
		obj_commentB=new Cancion("Cancion93282", u2, "chicle3.mp3");
		obj_commentA=new Album("Album111111111", u1);
		obj_commentA.anadirCancion(obj_commentB);
		comment=new Comentario("Me gusta!", u1, LocalDate.now(), 8);
	}

	/**
	 * Test method for {@link es.uam.padsof.objetoreproducible.ObjetoComentable#anadirComentario(es.uam.padsof.objetoreproducible.Comentario)}.
	 */
	@Test
	public void testAnadirComentario() {
		sys.login("carlos", "6253");
		obj_commentA.anadirComentario(comment);
		assertTrue(obj_commentA.getComentarios().contains(comment));
		obj_commentB.anadirComentario(comment);
		assertTrue(obj_commentB.getComentarios().contains(comment));
	}

}
