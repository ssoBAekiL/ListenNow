/**
 * 
 */
package pruebas;

import static org.junit.Assert.*;

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
 * @author carlosmiret
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
	 * @throws Mp3PlayerException 
	 * @throws IOException 
	 * @throws java.lang.Exception
	 */
	@Before
	public void testObjetoComentable() throws Mp3PlayerException, IOException{
		u1=new UsuarioRegistrado("1234567891234567","carlos","6253", false,false);
		sys.addUsuario(u1);
		//login();
		u2=new UsuarioRegistrado("98092735671891927","julian","837382", false,false);
		sys.addUsuario(u2);
		obj_commentA=new Album("Album111111111", u1, "lib");
		obj_commentB=new Cancion("Cancion93282", u2, "lib");
		comment=new Comentario("Me gusta!", u1, LocalDate.now(), 8);
	}

	/**
	 * Test method for {@link es.uam.padsof.objetoreproducible.ObjetoComentable#anadirComentario(es.uam.padsof.objetoreproducible.Comentario)}.
	 */
	@Test
	public void testAnadirComentario() {
		obj_commentA.anadirComentario(u2, comment);
		assertTrue(obj_commentA.getComentarios().contains(comment));
		obj_commentB.anadirComentario(u1, comment);
		assertTrue(obj_commentB.getComentarios().contains(comment));
	}

}
