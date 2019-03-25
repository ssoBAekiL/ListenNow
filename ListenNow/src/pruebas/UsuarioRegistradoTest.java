/**
 * 
 */
package pruebas;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import es.uam.eps.padsof.telecard.FailedInternetConnectionException;
import es.uam.eps.padsof.telecard.InvalidCardNumberException;
import es.uam.eps.padsof.telecard.OrderRejectedException;
import es.uam.padsof.objetoreproducible.Album;
import es.uam.padsof.objetoreproducible.Cancion;
import es.uam.padsof.objetoreproducible.ListaReproducciones;
import es.uam.padsof.sistema.Notificacion;
import es.uam.padsof.sistema.Sistema;
import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * @author Carlos Miret
 *
 */
public class UsuarioRegistradoTest {
	Sistema sys=Sistema.getInstance();
	private UsuarioRegistrado u1;
	private UsuarioRegistrado u2;
	private Cancion c1;
	private ListaReproducciones l1;
	File file;
	Album a1;

	
	/**
	 * Test method for {@link es.uam.padsof.usuario.UsuarioRegistrado#UsuarioRegistrado(java.lang.String, java.lang.String, java.lang.String, boolean, boolean)}.
	 * @thro ws Mp3PlayerException 
	 * @throws IOException 
	 */
	@Before
	public void testUsuarioRegistrado() throws IOException, Mp3PlayerException {
		c1 = new Cancion("Cancion 1", u1, "chicle.mp3");
		file=new File("registro_pagos.txt");
		u1=new UsuarioRegistrado("carlos","6253", false,false);
		sys.addUsuario(u1);
		u2=new UsuarioRegistrado("pablo","74884", false,false);
		sys.addUsuario(u2);
		//Notificacion noti=new Notificacion(c1);
		a1 = new Album("Album111111111", u2);
		l1 = new ListaReproducciones("Lista377372", u2);
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
	 * Test method for {@link es.uam.padsof.usuario.UsuarioRegistrado#anadirListaReproduccion(es.uam.padsof.objetoreproducible.ListaReproducciones)}.
	 */
	@Test
	public final void testAnadirCancion() {
		u1.anadirCancion(c1);
		assertTrue(u1.getCanciones().contains(c1));
	}

	/**
	 * Test method for {@link es.uam.padsof.usuario.UsuarioRegistrado#borrarListaReproduccion(es.uam.padsof.objetoreproducible.ListaReproducciones)}.
	 */
	@Test
	public final void testBorrarCancion() {
		u1.anadirCancion(c1);
		u1.getCanciones().remove(c1);
		assertFalse(u1.getCanciones().contains(c1));
	}
	
	
	/**
	 * Test method for {@link es.uam.padsof.usuario.UsuarioRegistrado#anadirListaReproduccion(es.uam.padsof.objetoreproducible.ListaReproducciones)}.
	 */
	@Test
	public final void testAnadirAlbum() {
		u1.anadirAlbum(a1);
		assertTrue(u1.getAlbunes().contains(a1));	
	}

	/**
	 * Test method for {@link es.uam.padsof.usuario.UsuarioRegistrado#borrarListaReproduccion(es.uam.padsof.objetoreproducible.ListaReproducciones)}.
	 */
	@Test
	public final void testBorrarAlbum() {
		u1.anadirAlbum(a1);
		u1.getAlbunes().remove(a1);
		assertFalse(u1.getAlbunes().contains(a1));
	}
	

	/**
	 * Test method for {@link es.uam.padsof.usuario.UsuarioRegistrado#anadirListaReproduccion(es.uam.padsof.objetoreproducible.ListaReproducciones)}.
	 */
	@Test
	public final void testAnadirListaReproduccion() {
		u1.anadirListaReproduccion(l1);
		assertTrue(u1.getLista_reproducciones().contains(l1));	
	}

	/**
	 * Test method for {@link es.uam.padsof.usuario.UsuarioRegistrado#borrarListaReproduccion(es.uam.padsof.objetoreproducible.ListaReproducciones)}.
	 */
	@Test
	public final void testBorrarListaReproduccion() {
		u1.anadirListaReproduccion(l1);
		u1.getLista_reproducciones().remove(l1);
		assertFalse(u1.getLista_reproducciones().contains(l1));	
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
