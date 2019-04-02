/**
 * 
 */
package es.uam.padsof.pruebas;

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
	private Cancion c2;
	private Cancion c3;
	private Comentario comment;

	
	/**
	 * Metodo que se ejecuta antes de ejecutar cualquier 
	 * test, guardando independencia entre estos
	 * @throws Mp3PlayerException 
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	@Before
	public void testCancion() throws IOException, Mp3PlayerException, InterruptedException{
		sys.login("ADMIN"/*user name*/, "soyadmin"/*pwd*/);
		sys.setUsuarioEnSesion(Sistema.getInstance().getAdmin());
		//sys.setUsuarioEnSesion(Sistema.getInstance().getAdmin());

		c1 = new Cancion("Cancion 143", u1, "GB 10sec video.mp3");
		c2 = new Cancion("Cancion 23", u1, "hive-2.mp3");
		c3 = new Cancion("Cancion 245", u1, "hola.mp3");
		sys.anadirReproducible(c1);
		sys.anadirReproducible(c2);
		sys.anadirReproducible(c3);
		comment=new Comentario("INCREIBLE",Sistema.getInstance().getUsuarioEnSesion(),LocalDate.now(),8);
	}
	
	/**
	 * Metodo que se ejecuta despues de los diferentes tests
	 */
	@After
	public void reset() {
		sys.borrarReproducible(c1);
		sys.borrarReproducible(c2);
		sys.borrarReproducible(c3);

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
		/*ESTE TEST FUNCIONA, TENER CUIDADO PORQUE SE BORRA EL ARCHIVO, SI QUIERE EJECUTAR ESTA PRUEBA, 
		 *POR FAVOR, DESCOMENTE EL CODIGO*/
		c3.marcarComoPlagio();
		c3.rechazar();
		assertTrue(Sistema.getInstance().getCancionesRechazadas().contains(c3));
		assertFalse(Sistema.getInstance().getCancionesValidar().contains(c3));
		c3.modificarFechaRechazo();
//		/*antes de ejecutar este test, ver carpeta del proyecto, donde se encuentra este archivo
		c3.borradoTrasTercerDia();
		assertFalse(Sistema.getInstance().getCancionesRechazadas().contains(c3));
		assertFalse(Sistema.getInstance().getCancionesNotificadas().contains(c3));
		assertFalse(Sistema.getInstance().getCancionesValidadas().contains(c3));
	}
	
	
	/**
	 * Test para la funcion anadir comentario a cancion
	 */
	@Test
	public final void testAnadirComentario() {
		c1.anadirComentario(comment);
		assertTrue(c1.getComentarios().contains(comment));
	}
	
	
	


}