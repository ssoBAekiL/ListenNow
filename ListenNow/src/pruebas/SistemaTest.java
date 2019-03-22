package pruebas;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import es.uam.padsof.*;
import es.uam.padsof.objetoreproducible.Album;
import es.uam.padsof.objetoreproducible.Cancion;
import es.uam.padsof.sistema.Sistema;
import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.exceptions.Mp3PlayerException;

public class SistemaTest {
	Sistema sys = Sistema.getInstance();
	private UsuarioRegistrado u1;
	private UsuarioRegistrado u2;
	private Cancion c1;
	private Cancion c2; 
	private Cancion c3;
	private Album a1;
	
	@Before
	public void setUp() throws IOException, Mp3PlayerException {
		sys.logout();
		u1 = new UsuarioRegistrado("1234567891234567", "usuario1", "pass", false, false);
		u2 = new UsuarioRegistrado("9876543219876543", "usuario2", "pass123", false, false);
		sys.addUsuario(u1);
		sys.addUsuario(u2);
		c1 = new Cancion("Cancion 1", u1, "/np.mp3");
		c2 = new Cancion("Cancion 2", u2, "/hive.mp3");
		c3 = new Cancion("Cancion 3", u1, "/chicle3.mp3");
		sys.setCancionValidada(c1);
		sys.setCancionValidada(c2);
		sys.setCancionValidada(c3);
		u1.setCanciones(c1);
		u1.setCanciones(c3);
		u2.setCanciones(c2);
		ArrayList<Cancion> cancionesAlbum = new ArrayList<Cancion>();
		cancionesAlbum.add(c1);
		cancionesAlbum.add(c3);
		a1 = new Album("Album 1", u1, cancionesAlbum);
		sys.setAlbum(a1);
	}
	
	@Test
	public void testInicializarSistema() {
		sys.bloquearUsuario(u2, false);
		u1.contratarPremium();
		u2.setFechaBloqueo(LocalDate.now().minusDays(35));
		assertTrue(u2.getBloqueado());
		sys.inicializarSistema();
		assertTrue(u2.getBloqueado());
		
	}

	@Test
	public void testBuscarCancion() {
		assertSame(c1, sys.buscarCancion("Cancion 1"));
		assertNotSame(c1, sys.buscarCancion("Cancion 2"));
		assertNull(sys.buscarCancion("Abc def"));
	}

	@Test
	public void testBuscarAlbum() {
		assertSame(a1.getTitulo(), sys.buscarAlbum("Album 1").getTitulo());
	}

	@Test
	public void testBuscarAutor() { 
		assertTrue(sys.buscarAutor("usuario1").containsAll(u1.getCanciones()));
	}

	@Test
	public void testRegistrarse() {
		UsuarioRegistrado u3 = new UsuarioRegistrado("9876543219876543", "Usuario3", "p123", true, false);
		sys.registrarse(u3); 
		assertTrue(sys.getUsuarios().contains(u3));
	}

	@Test
	public void testLogin() {
		assertFalse(sys.login("usuario1", "Pass"));
		assertTrue(sys.login("usuario1", "pass"));
		assertEquals("usuario1", sys.getUsuarioEnSesion().getNombre());
		assertEquals("pass", sys.getUsuarioEnSesion().getContrasena());
		assertTrue(sys.getConectado());
		assertTrue(sys.login("ADMIN", "soyadmin"));
		assertNotNull(sys.getUsuarioEnSesion());
		assertEquals("ADMIN", sys.getUsuarioEnSesion().getNombre());
		assertEquals("soyadmin", sys.getUsuarioEnSesion().getContrasena());
		assertTrue(sys.getConectado());
		sys.logout();
	}

	@Test
	public void testLogout() {
		sys.login("usuario1", "pass");
		sys.logout();
		assertNull(sys.getUsuarioEnSesion());
		assertFalse(sys.getConectado());
		sys.login("ADMIN", "soyadmin");
		sys.logout();
		assertNull(sys.getUsuarioEnSesion());
		assertFalse(sys.getConectado());
	}

	@Test
	public void testAnadirReproducible() throws IOException, Mp3PlayerException {
		Cancion c4 = new Cancion("Cancion 4", u2, "/np.mp3");
		ArrayList<Cancion> cancionesAlbum = new ArrayList<Cancion>();
		cancionesAlbum.add(c2);
		cancionesAlbum.add(c4);
		Album a2 = new Album("Album 2", u2, "chicle.mp3");
		sys.setAlbum(a1);
		sys.anadirReproducible(c4);
		assertFalse(sys.getCancionesValidar().contains(c4));
		sys.login("usuario1", "pass");
		sys.anadirReproducible(c4);
		assertSame(c4, sys.getCancionesValidar().get(0));
		sys.anadirReproducible(a2);
		assertTrue(sys.getAlbunes().contains(a2));
		sys.logout();
	}

	@Test
	public void testBorrarReproducible() throws Mp3PlayerException, IOException {
		sys.login("usuario2", "pass123");
		ArrayList<Cancion> cancionesAlbum = new ArrayList<Cancion>();
		Album a2 = new Album("Album 2", u2, cancionesAlbum);
		sys.setAlbum(a1);
		Cancion c4 = new Cancion("Cancion 4", u2, "/np.mp3");
		sys.anadirReproducible(c4);
		sys.logout();
		sys.borrarReproducible(c4);
		assertTrue(sys.getCancionesValidar().contains(c4));
		sys.login("usuario2", "pass123");
		cancionesAlbum.add(c2);
		cancionesAlbum.add(c4);
		sys.anadirReproducible(a2);
		sys.borrarReproducible(a2);
		assertFalse(sys.getAlbunes().contains(a2));
		sys.borrarReproducible(c4);
		assertFalse(sys.getCancionesValidar().contains(c4));
		sys.logout();
	}

	@Test
	public void testRecompensaPremium() {
		fail("Not yet implemented");
	}

	@Test
	public void testCaducaPremium() {
		fail("Not yet implemented");
	}

	@Test
	public void testBloquearUsuario() {
		fail("Not yet implemented");
	}

	@Test
	public void testDesbloquearUsuario() {
		fail("Not yet implemented");
	}

	@Test
	public void testDarDeBaja() {
		fail("Not yet implemented");
	}

	@Test
	public void testMostrarNotificacion() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUsuarios() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetUsuarios() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetnRepAnonimas() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetnRepAnonimas() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetnRepRegistrado() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetnRepRegistrado() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetnRepRecompensa() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetnRepRecompensa() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCancionesValidadas() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetCancionesValidadas() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetCancionValidada() {
		fail("Not yet implemented");
	}

	@Test
	public void testIncremetaReproducciones() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCancionesNotificadas() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUsuarioItera() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCancionItera() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNumUsuarios() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddUsuario() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCancionesValidar() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetNotificaciones() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNumeroCanciones() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAdmin() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetAdmin() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetAlbum() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUsuarioEnSesion() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAdminConectado() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetConectado() {
		fail("Not yet implemented");
	}

}
