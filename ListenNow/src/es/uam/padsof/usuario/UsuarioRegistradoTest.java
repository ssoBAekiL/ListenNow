/**
 * 
 */
package es.uam.padsof.usuario;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import es.uam.eps.padsof.telecard.FailedInternetConnectionException;
import es.uam.eps.padsof.telecard.InvalidCardNumberException;
import es.uam.eps.padsof.telecard.OrderRejectedException;
import es.uam.padsof.objetoreproducible.Cancion;
import es.uam.padsof.sistema.Notificacion;
import es.uam.padsof.sistema.Sistema;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * @author eps
 *
 */
public class UsuarioRegistradoTest {
	Sistema sys=Sistema.getInstance();
	private UsuarioRegistrado u1;
	private UsuarioRegistrado u2;
	private Cancion c1;
	File file;
	
	/**
	 * Test method for {@link es.uam.padsof.usuario.UsuarioRegistrado#UsuarioRegistrado(java.lang.String, java.lang.String, java.lang.String, boolean, boolean)}.
	 * @throws Mp3PlayerException 
	 * @throws IOException 
	 */
	@Before
	public void testUsuarioRegistrado() throws IOException, Mp3PlayerException {
		c1 = new Cancion("Cancion 1", u1, "/np.mp3");
		file=new File("registro_pagos.txt");
		u1=new UsuarioRegistrado("1234567891234567","carlos","6253", false,false);
		sys.addUsuario(u1);
		u2=new UsuarioRegistrado("1904567891227567","pablo","74884", false,false);
		sys.addUsuario(u2);
		Notificacion noti=new Notificacion(c1);
	}
	
	

	/**
	 * Test method for {@link es.uam.padsof.usuario.UsuarioRegistrado#contratarPremium(java.io.File)}.
	 * @throws IOException 
	 * @throws OrderRejectedException 
	 * @throws FailedInternetConnectionException 
	 * @throws InvalidCardNumberException 
	 */
	@Test
	public final void testContratarPremium() throws InvalidCardNumberException, FailedInternetConnectionException, OrderRejectedException, IOException {
		assertTrue(u1.contratarPremium(u1.getNumTarjeta()));	
		assertTrue(u1.EsPremium());
	}

	/**
	 * Test method for {@link es.uam.padsof.usuario.UsuarioRegistrado#notificarPlagio(es.uam.padsof.objetoreproducible.Cancion)}.
	 */
	@Test
	public final void testNotificarPlagio() {
		u1.notificarPlagio(c1);
		assertTrue(sys.getCancionesNotificadas().contains(c1));
	}

	
	/**
	 * Test method for {@link es.uam.padsof.usuario.UsuarioRegistrado#anadirListaReproduccion(es.uam.padsof.objetoreproducible.ListaReproducciones)}.
	 */
	@Test
	public final void testAnadirCancion() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link es.uam.padsof.usuario.UsuarioRegistrado#borrarListaReproduccion(es.uam.padsof.objetoreproducible.ListaReproducciones)}.
	 */
	@Test
	public final void testBorrarCancion() {
		fail("Not yet implemented"); // TODO
	}
	
	
	/**
	 * Test method for {@link es.uam.padsof.usuario.UsuarioRegistrado#anadirListaReproduccion(es.uam.padsof.objetoreproducible.ListaReproducciones)}.
	 */
	@Test
	public final void testAnadirAlbum() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link es.uam.padsof.usuario.UsuarioRegistrado#borrarListaReproduccion(es.uam.padsof.objetoreproducible.ListaReproducciones)}.
	 */
	@Test
	public final void testBorrarAlbum() {
		fail("Not yet implemented"); // TODO
	}
	
	

	/**
	 * Test method for {@link es.uam.padsof.usuario.UsuarioRegistrado#anadirListaReproduccion(es.uam.padsof.objetoreproducible.ListaReproducciones)}.
	 */
	@Test
	public final void testAnadirListaReproduccion() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link es.uam.padsof.usuario.UsuarioRegistrado#borrarListaReproduccion(es.uam.padsof.objetoreproducible.ListaReproducciones)}.
	 */
	@Test
	public final void testBorrarListaReproduccion() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link es.uam.padsof.usuario.UsuarioRegistrado#follows(es.uam.padsof.usuario.UsuarioRegistrado)}.
	 */
	@Test
	public final void testFollows() {
		u1.follows(u2);
		assertTrue(u1.getSeguidos().contains(u2));
		assertTrue(u2.getSeguidores().contains(u1));
	}


}
