/**
 * 
 */
package pruebas;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import es.uam.padsof.objetoreproducible.Cancion;
import es.uam.padsof.sistema.Notificacion;
import es.uam.padsof.sistema.Sistema;
import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.Mp3Player;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * @author Carlos Miret, Julian Espada y Pablo Borrelli
 *
 */
public class CancionTest {
	Sistema sys=Sistema.getInstance();
	private UsuarioRegistrado u1=new UsuarioRegistrado("1234567891234567","carlos","6253", false,false);
;
	private UsuarioRegistrado u2;
	private Cancion c1;

	
	/**
	 * Test method for {@link es.uam.padsof.usuario.UsuarioRegistrado#UsuarioRegistrado(java.lang.String, java.lang.String, java.lang.String, boolean, boolean)}.
	 * @throws Mp3PlayerException 
	 * @thro ws Mp3PlayerException 
	 * @throws IOException 
	 */
	@Before
	public void testCancion() throws IOException, Mp3PlayerException{
		sys.setUsuarioEnSesion(Sistema.getInstance().getAdmin());
		//u2=new UsuarioRegistrado("1904567891227567","pablo","74884", false,false);
		//sys.addUsuario(u2);
		c1 = new Cancion("Cancion 1", u1, "chicle.mp3");
		
	}
	
	@Test
	public final void testReproducirCancion() throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		int a= Sistema.getInstance().getUsuarioEnSesion().getReproducciones();
		c1.reproducir();
		int b= Sistema.getInstance().getUsuarioEnSesion().getReproducciones();
		//System.out.println(Sistema.getInstance().getUsuarioEnSesion().getReproducciones());
		assertTrue(b>a);
	}
	
	
	/**
	 * Metodo que hace test sobre la funcion Validar cancion
	 * @param cancion
	 */
	@Test
	public final void testValidarCancion() {
		c1.validarCancion();
		assertTrue(Sistema.getInstance().getCancionesValidadas().contains(c1));
		assertFalse(Sistema.getInstance().getCancionesValidar().contains(c1));
		assertFalse(Sistema.getInstance().getNotificaciones().isEmpty());
	}
	
	/**
	 * Metodo que permite al usuario admin validar + 18 una cancion pasada por parametro
	 * @param cancion
	 */
	@Test
	public final void testValidarCancion18() {
		c1.validarCancion();
		assertTrue(Sistema.getInstance().getCancionesValidadas().contains(c1));
		assertFalse(Sistema.getInstance().getCancionesValidar().contains(c1));
		assertFalse(Sistema.getInstance().getNotificaciones().isEmpty());
	}
	
	
	/**
	 * Metodo que permite al usuario admin validar + 18 una cancion pasada por parametro
	 * @param cancion
	 */
	@Test
	public final void borradoTrasRechaza3DiasEspera() {
		c1.rechazar();
		assertTrue(Sistema.getInstance().getCancionesRechazadas().contains(c1));
		assertFalse(Sistema.getInstance().getCancionesValidar().contains(c1));
		c1.modificarFechaRechazo();
		c1.borradoTrasTercerDia();
		assertFalse(Sistema.getInstance().getCancionesRechazadas().contains(c1));
	}
	
	

}
