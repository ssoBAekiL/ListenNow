package pruebas;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import es.uam.padsof.objetoreproducible.Album;
import es.uam.padsof.objetoreproducible.Cancion;
import es.uam.padsof.objetoreproducible.Comentario;
import es.uam.padsof.objetoreproducible.ListaReproducciones;
import es.uam.padsof.objetoreproducible.ObjetoComentable;
import es.uam.padsof.objetoreproducible.ObjetoReproducible;
import es.uam.padsof.sistema.Sistema;
import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.exceptions.Mp3PlayerException;

public class ObjetoReproducibleTest {

	Sistema sys=Sistema.getInstance();
	private UsuarioRegistrado u1;
	private UsuarioRegistrado u2;
	private  ObjetoReproducible obj_commentA;
	private  ObjetoReproducible obj_commentB;
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
		obj_commentA=new Cancion("Album111111111", u1, "lib");
		obj_commentB=new ListaReproducciones("Cancion93282", u2, "lib");
		comment=new Comentario("Me gusta!", u1, LocalDate.now(), 8);
	}
	
	@Test
	public void testObjetoReproducible() {
		fail("Not yet implemented");
	}

	@Test
	public void testReproducir() {
		fail("Not yet implemented");
	}

	@Test
	public void testPararReproduccion() {
		fail("Not yet implemented");
	}

}
