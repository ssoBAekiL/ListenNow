/**
 * 
 */
package pruebas;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.uam.padsof.objetoreproducible.Cancion;
import es.uam.padsof.objetoreproducible.Comentario;
import es.uam.padsof.sistema.Sistema;
import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * CLASE DE TEST PARA CANCION
 * @author Carlos Miret, Julian Espada y Pablo Borrelli
 *
 */
public class CancionTest {
	Sistema sys=Sistema.getInstance();
	private UsuarioRegistrado u1=new UsuarioRegistrado("carlos","6253", false,false);
	private Cancion c1;
	private Comentario comment;

	
	/**
	 * Metodo que se ejecuta antes de ejecutar cualquier 
	 * test, guardando independencia entre estos
	 * @throws Mp3PlayerException 
	 * @thro ws Mp3PlayerException 
	 * @throws IOException 
	 */
	@Before
	public void testCancion() throws IOException, Mp3PlayerException{
		sys.login("ADMIN"/*user name*/, "soyadmin"/*pwd*/);
		sys.setUsuarioEnSesion(Sistema.getInstance().getAdmin());
		//sys.setUsuarioEnSesion(Sistema.getInstance().getAdmin());
		c1 = new Cancion("Cancion 1", u1, "hive.mp3");
		sys.anadirReproducible(c1);
		comment=new Comentario("INCREIBLE",Sistema.getInstance().getUsuarioEnSesion(),LocalDate.now(),8);
		System.out.println(c1.getId());
	}
	
	/**
	 * Metodo que se ejecuta despues de los diferentes tests
	 */
	@After
	public void reset() {
		sys.reset();
	}
	
	/**
	 * Test para la funcion reproducir cancion
	 * @throws FileNotFoundException
	 * @throws Mp3PlayerException
	 * @throws InterruptedException
	 */
	@Test
	public final void testReproducirCancion() throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		int a= Sistema.getInstance().getUsuarioEnSesion().getReproducciones();
		c1.reproducir();
		int b= Sistema.getInstance().getUsuarioEnSesion().getReproducciones();
		assertTrue(b>a);
	}
	
	
	/**
	 * Test para la funcion validar cancion
	 * Metodo que hace test sobre la funcion Validar cancion
	 */
	@Test
	public final void testValidarCancion() {
		c1.validarCancion();
		assertTrue(Sistema.getInstance().getCancionesValidadas().contains(c1));
		assertFalse(Sistema.getInstance().getCancionesValidar().contains(c1));
		assertFalse(Sistema.getInstance().getNotificaciones().isEmpty());
	}
	
	
	
	/**
	 * Test para la funcion validar + 18 cancion
	 * Metodo que permite al usuario admin validar + 18 una cancion pasada por parametro
	 */
	@Test
	public final void testValidarCancion18() {
		c1.validarCancion();
		assertTrue(Sistema.getInstance().getCancionesValidadas().contains(c1));
		assertFalse(Sistema.getInstance().getCancionesValidar().contains(c1));
		assertFalse(Sistema.getInstance().getNotificaciones().isEmpty());
	}
	
	/**
	 * Test para la funcion notificar cancion 
	 */
	@Test
	public final void testNotificarPlagio() {
		c1.notificarPlagio();
		assertTrue(Sistema.getInstance().getCancionesNotificadas().contains(c1));
	}
	
	
	/**
	 * Test para la funcion marcar como plagio una cancion
	 */
	@Test
	public final void marcarComoPlagio() {
		c1.marcarComoPlagio();
		assertTrue(Sistema.getInstance().getCancionesRechazadas().contains(c1));
	}
	

	
	/**
	 * Test para la funcion borrado tras el tercer dia de haber sido mmarcada una cancion cancion
	 */
	@Test
	public final void borradoTrasRechazada3DiasEspera() {
		c1.marcarComoPlagio();
		c1.rechazar();
		assertTrue(Sistema.getInstance().getCancionesRechazadas().contains(c1));
		assertFalse(Sistema.getInstance().getCancionesValidar().contains(c1));
		c1.modificarFechaRechazo();
		c1.borradoTrasTercerDia();
		assertFalse(Sistema.getInstance().getCancionesRechazadas().contains(c1));
		assertFalse(Sistema.getInstance().getCancionesNotificadas().contains(c1));
		assertFalse(Sistema.getInstance().getCancionesValidadas().contains(c1));
	}
	
	
	/**
	 * Test para la funcion anadir comentario a cancion
	 */
	@Test
	public final void testAnadirComentario() {
		c1.anadirComentario(comment);
		assertTrue(c1.getComentarios().contains(comment));
	}
	
	@Test
	public final void testCopiarCancionASistema() throws IOException {
	assertTrue(c1.copiarCancionASistema());
//	sys.borrarReproducible(c1);
//	assertFalse(sys.getCancionesValidar().contains(c1));
//	assertFalse(sys.getCancionesValidadas().contains(c1));
//	assertFalse(sys.getCancionesNotificadas().contains(c1));
//	assertFalse(sys.getCancionesRechazadas().contains(c1));
	}

}
