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
import es.uam.padsof.sistema.Sistema;

/**
 * @author eps
 *
 */
public class UsuarioRegistradoTest {
	Sistema sys=Sistema.getInstance();
	private UsuarioRegistrado u1;
	private UsuarioRegistrado u2;
	File file;
	
	/**
	 * Test method for {@link es.uam.padsof.usuario.UsuarioRegistrado#UsuarioRegistrado(java.lang.String, java.lang.String, java.lang.String, boolean, boolean)}.
	 */
	@Before
	public void testUsuarioRegistrado() {
		file=new File("registro_pagos.txt");
		u1=new UsuarioRegistrado("1234567891234567","carlos","6253", false,false);
		sys.addUsuario(u1);
		u2=new UsuarioRegistrado("1904567891227567","pablo","74884", false,false);
		sys.addUsuario(u2);

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
		u1.contratarPremium(file);	
		assertTrue(u1.EsPremium());
	}

	/**
	 * Test method for {@link es.uam.padsof.usuario.UsuarioRegistrado#getNumTarjeta()}.
	 */
	@Test
	public final void testGetNumTarjeta() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link es.uam.padsof.usuario.UsuarioRegistrado#notificarPlagio(es.uam.padsof.objetoreproducible.Cancion)}.
	 */
	@Test
	public final void testNotificarPlagio() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link es.uam.padsof.usuario.UsuarioRegistrado#borrarAlbum(es.uam.padsof.objetoreproducible.Album)}.
	 */
	@Test
	public final void testBorrarAlbum() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link es.uam.padsof.usuario.UsuarioRegistrado#borrarCancion(es.uam.padsof.objetoreproducible.Cancion)}.
	 */
	@Test
	public final void testBorrarCancion() {
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
		//assertTrue(u2.getSeguidores().contains(u1));
	}


}
