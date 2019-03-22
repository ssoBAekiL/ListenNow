/**
 * 
 */
package es.uam.padsof.objetoreproducible;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import es.uam.padsof.sistema.Sistema;
import es.uam.padsof.usuario.UsuarioRegistrado;

/**
 * @author carlosmiret
 *
 */
public class ObjetoComentableTest {
	Sistema sys=Sistema.getInstance();
	private UsuarioRegistrado u1;
	private  Album album1;
	private Cancion cancion1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void testObjetoComentable() throws Exception {
		u1=new UsuarioRegistrado("1234567891234567","carlos","6253", false,false);
		sys.addUsuario(u1);
		album1=	new Album("Album111111111", u1, "lib");

	}

	/**
	 * Test method for {@link es.uam.padsof.objetoreproducible.ObjetoComentable#anadirComentario(es.uam.padsof.objetoreproducible.Comentario)}.
	 */
	@Test
	public void testAnadirComentario() {
		
	}

}
