package pruebas;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.uam.eps.padsof.telecard.FailedInternetConnectionException;
import es.uam.eps.padsof.telecard.InvalidCardNumberException;
import es.uam.eps.padsof.telecard.OrderRejectedException;
import es.uam.padsof.objetoreproducible.Album;
import es.uam.padsof.objetoreproducible.Cancion;
import es.uam.padsof.sistema.Notificacion;
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
	private Cancion c4;
	private Album a2;
	private Notificacion n1;
	private Notificacion n2;
	private Notificacion n3;
	
	@Before
	public void setUp() throws IOException, Mp3PlayerException, ClassNotFoundException {
		u1 = new UsuarioRegistrado("usuario1", "pass", false, false);
		u2 = new UsuarioRegistrado("usuario2", "pass123", false, false);
		sys.addUsuario(u1);
		sys.addUsuario(u2);
		c1 = new Cancion("Cancion 1", u1, "/np.mp3");
		c2 = new Cancion("Cancion 2", u2, "/hive.mp3");
		c3 = new Cancion("Cancion 3", u1, "/chicle3.mp3");
		c4 = new Cancion("Cancion 4", u2, "/np.mp3");
		sys.setCancionValidada(c1);
		sys.setCancionValidada(c2);
		sys.setCancionValidada(c3);
		u1.setCanciones(c1);//sin querer quite setCanciones de la clase usuario, añadela PABLO por favor
		u1.setCanciones(c3);
		u2.setCanciones(c2);
		a1 = new Album("Album 1", u1);
		a1.aniadirCancionesAlbum(c1, c3);
		sys.setAlbum(a1);
		a2 = new Album("Album 2", u2);
		ArrayList<UsuarioRegistrado> usuariosNotificados = new ArrayList<UsuarioRegistrado>();
		usuariosNotificados.add(u1);
		usuariosNotificados.add(u2);
		a1.aniadirCancionesAlbum(c2, c4);
		n1 = new Notificacion(c1);
		n2 = new Notificacion(c2, usuariosNotificados);
		n3 = new Notificacion(u1, u2);
		sys.setNotificaciones(n1);
		sys.setNotificaciones(n2);
		sys.setNotificaciones(n3);
//		sys.guardarSistema();
//		sys.reset();
//		sys.readObject();
	}
	
	@After
	public void reset() {
		sys.reset();
	}
	
	@Test
	public void testInicializarSistema() throws InvalidCardNumberException, FailedInternetConnectionException, OrderRejectedException, ClassNotFoundException, IOException {
		assertTrue(sys.login("ADMIN", "soyadmin"));
		u2.bloquearUsuario(false);
		u1.setEsPremium(true);
		u1.setFechaPremium(LocalDate.now().minusDays(10));
		u2.setFechaBloqueo(LocalDate.now().minusDays(10));
		sys.logout();
		sys.guardarSistema();
		sys.reset();
		sys.inicializarSistema();
		assertTrue(u1.EsPremium());
		assertTrue(u2.getBloqueado());
		assertTrue(sys.login("ADMIN", "soyadmin"));
		u2.bloquearUsuario(false);
		u1.setEsPremium(true);
		u1.setFechaPremium(LocalDate.now().minusDays(30));
		u2.setFechaBloqueo(LocalDate.now().minusDays(30));
		sys.logout();
		sys.inicializarSistema();
		assertFalse(u1.EsPremium());
		assertFalse(u2.getBloqueado());
		
	}
	
	
	
	@Test
	public void testReadObject() throws FileNotFoundException, IOException, ClassNotFoundException {
		assertTrue(sys.getUsuarios().size() > 1);
		sys.guardarSistema();
		reset();
		assertTrue(sys.getUsuarios().size() == 1);
		sys.readObject();
		assertTrue(sys.getUsuarios().size() > 1);
	}

	@Test
	public void testBuscarCancion() {
		assertEquals(c1.getAutor().getNombre(), sys.buscarCancion("Cancion 1").getAutor().getNombre());
		assertEquals(c1.getTitulo(), sys.buscarCancion("Cancion 1").getTitulo());
		assertNotSame(c1, sys.buscarCancion("Cancion 2"));
		assertNull(sys.buscarCancion("Abc def"));
	}

	@Test
	public void testBuscarAlbum() {
		assertEquals(a1.getTitulo(), sys.buscarAlbum("Album 1").getTitulo());
	}

	@Test
	public void testBuscarAutor() { 
		assertTrue(sys.buscarAutor("usuario1").containsAll(u1.getCanciones()));
	}

	@Test
	public void testRegistrarse() {
		UsuarioRegistrado u3 = new UsuarioRegistrado("Usuario3", "p123", true, false);
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
		sys.logout();
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
		sys.anadirReproducible(c4);
		assertFalse(sys.getCancionesValidar().contains(c4));
		sys.anadirReproducible(a2);
		assertFalse(sys.getAlbunes().contains(a2));
		sys.login("usuario1", "pass");
		sys.anadirReproducible(c4);
		assertTrue(sys.getCancionesValidar().contains(c4));
		sys.anadirReproducible(a2);
		assertTrue(sys.getAlbunes().contains(a2));
	}

	@Test
	public void testBorrarReproducible() throws Mp3PlayerException, IOException {
		sys.login("usuario2", "pass123");
		sys.anadirReproducible(c4);
		sys.logout();
		sys.borrarReproducible(c4);
		assertTrue(sys.getCancionesValidar().contains(c4));
		sys.login("usuario2", "pass123");
		sys.anadirReproducible(a2);
		sys.borrarReproducible(a2);
		assertFalse(sys.getAlbunes().contains(a2));
		sys.logout();
		sys.login("usuario2", "pass123");
		sys.anadirReproducible(a2);
		sys.logout();
		sys.login("ADMIN", "soyadmin");
		sys.borrarReproducible(a2);
		assertFalse(sys.getAlbunes().contains(a2));
		sys.borrarReproducible(c4);
		assertFalse(sys.getCancionesValidar().contains(c4));
	}



	@Test
	public void testDarDeBaja() {
		sys.darDeBaja(u1);
		assertFalse(sys.getUsuarios().contains(u1));
	}

	@Test
	public void testMostrarNotificacion() {
		assertNull(sys.mostrarNotificacion());
		sys.login("ADMIN", "soyadmin");
		assertEquals(n1.getTexto(), sys.mostrarNotificacion().get(0).getTexto());
		sys.logout();
		sys.login("usuario1", "pass");
		assertEquals(n2.getTexto(), sys.mostrarNotificacion().get(0).getTexto());
		sys.logout();
		sys.login("usuario2", "pass123");
		assertEquals(n2.getTexto(), sys.mostrarNotificacion().get(0).getTexto());
		assertEquals(n3.getTexto(), sys.mostrarNotificacion().get(1).getTexto());
	}
	
	@Test
	public void testIncremetaReproducciones() {
		sys.incremetareproduccionesNoRegistrados();
		assertEquals(1, sys.getReproduccionesNoRegistrados());
		sys.incremetareproduccionesNoRegistrados();
		assertEquals(2, sys.getReproduccionesNoRegistrados());
	}

	/*@Test
	public void testGetUsuarios() {
		assertTrue(sys.getUsuarios().size() == 3);
		assertTrue(sys.getUsuarios().contains(u1));
		assertTrue(sys.getUsuarios().contains(u2));
		assertFalse(sys.getUsuarios().contains(new UsuarioRegistrado("1234567891234567", "usuario3", "pass", false, false)));
	}

	@Test
	public void testSetUsuarios() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetnRepAnonimas() {
		sys.setnRepAnonimas(5000);
		assertEquals(5000, sys.getnRepAnonimas());
	}

	@Test
	public void testSetnRepAnonimas() {
		sys.setnRepAnonimas(5000);
		assertEquals(5000, sys.getnRepAnonimas());
	}

	@Test
	public void testGetnRepRegistrado() {
		sys.setnRepRegistrado(200);
		assertEquals(200, sys.getnRepRegistrado());
	}

	@Test
	public void testSetnRepRegistrado() {
		sys.setnRepRegistrado(200);
		assertEquals(200, sys.getnRepRegistrado());
	}

	@Test
	public void testGetnRepRecompensa() {
		sys.setnRepRecompensa(2000);
		assertEquals(2000, sys.getnRepRecompensa());
	}

	@Test
	public void testSetnRepRecompensa() {
		sys.setnRepRecompensa(2000);
		assertEquals(2000, sys.getnRepRecompensa());
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
	}*/

}
