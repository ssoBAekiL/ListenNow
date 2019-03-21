package pruebas;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import es.uam.padsof.*;
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
	
	@Before
	public void setUp() throws IOException, Mp3PlayerException {
		u1 = new UsuarioRegistrado("1234567891234567", "Pablo", "pass", true, false);
		u2 = new UsuarioRegistrado("9876543219876543", "Carlos", "pass123", true, false);
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
	}
	
	@Test
	public void testInicializarSistema() {
		fail("Not yet implemented");
		/*u1.setBloqueado(true);
		sys.inicializarSistema();*/
		
	}

	@Test
	public void testBuscarCancion() {
		assertSame(c1, sys.buscarCancion("Cancion 1"));
		assertNotSame(c1, sys.buscarCancion("Cancion 2"));
		assertNull(sys.buscarCancion("Abc def"));
	}

	@Test
	public void testBuscarAlbum() {
		fail("Not yet implemented");
	}

	@Test
	public void testBuscarAutor() {
		assertTrue(sys.buscarAutor("Pablo").containsAll(u1.getCanciones()));
	}

	@Test
	public void testRegistrarse() {
		UsuarioRegistrado u3 = new UsuarioRegistrado("9876543219876543", "Usuario3", "p123", true, false);
		sys.registrarse(u3);
		assertTrue(sys.getUsuarios().contains(u3));
	}

	@Test
	public void testLogin() {
		assertFalse(sys.login("Pablo", "Pass"));
		assertTrue(sys.login("Pablo", "pass"));
	}

	@Test
	public void testLogout() {
		fail("Not yet implemented");
	}

}
