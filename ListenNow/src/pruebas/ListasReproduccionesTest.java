package pruebas;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import es.uam.padsof.objetoreproducible.Album;
import es.uam.padsof.objetoreproducible.Cancion;
import es.uam.padsof.objetoreproducible.Comentario;
import es.uam.padsof.objetoreproducible.ListaReproducciones;
import es.uam.padsof.sistema.Sistema;
import pads.musicPlayer.exceptions.Mp3PlayerException;

public class ListasReproduccionesTest {
	Sistema sys = Sistema.getInstance();
	private Album a1;
	private Album a2;
	private Cancion c1;
	private Cancion c2;
	private Cancion c3;
	private ListaReproducciones l;
	private Comentario comment;

	@Before
	public void setUp() throws Exception {
		sys.login("ADMIN", "soyadmin");
		a1 = new Album("Album 1",sys.getUsuarioEnSesion());
		a2 = new Album("Album 2", sys.getUsuarioEnSesion());
		c1 = new Cancion("Cancion 1", sys.getUsuarioEnSesion(), "chicle3.mp3");
		c2 = new Cancion("Cancion 2", sys.getUsuarioEnSesion(), "hive.mp3");
		c3 = new Cancion("Cancion 3", sys.getUsuarioEnSesion(), "athenas-jesus-eres-digno-de-alabar.mp3");
		l = new ListaReproducciones("Lista 1", sys.getUsuarioEnSesion());
		comment = new Comentario("INCREIBLE", sys.getUsuarioEnSesion(), LocalDate.now(), 8);
	}

	/*
	 * Test de aï¿½adir cancion a lista de reproduccion
	 */
	@Test
	public void testAniadirCancionLista() {
		a1.aniadirCancionAlbum(c1);
		a2.aniadirCancionAlbum(c2);
		l.aniadirAlbumALista(a1);
		l.aniadirAlbumALista(a2);
		assertTrue(l.aniadirCancionALista(c3));
		assertFalse(l.aniadirCancionALista(c2));
		assertFalse(l.aniadirCancionALista(c1));
	}
	
	/*
	 * Test para añadir un album a una lista
	 */
	@Test
	public void testAniadirAlbumLista() {
		a1.aniadirCancionAlbum(c1);
		a2.aniadirCancionAlbum(c2);
		l.aniadirCancionALista(c3);
		assertTrue(l.aniadirAlbumALista(a1));
		assertTrue(l.aniadirAlbumALista(a2));
		a1.aniadirCancionAlbum(c3);
		assertFalse(l.aniadirAlbumALista(a1));
		
	}
	
	/*
	 * Test de comprobar que cuando se borra una cancion del sistema no existe en la lista
	 */
	@Test
	public void testComprobarCancionLista() {
		sys.borrarReproducible(c1);
		assertFalse(l.getListaCanciones().contains(c1));
	}
	
	/*
	 * Test de comprobar que cuando se borra un album del sistema, no existe en la lista
	 */
	@Test
	public void testComprobarAlbumLista() {
		sys.borrarReproducible(a1);
		assertFalse(l.getListaAlbumes().contains(a1));
	}
	
	/*
	 * Test de reproduccion de listas
	 */
	@Test
	public void testReproducirLista() throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		a1.aniadirCancionAlbum(c3);
		a1.aniadirCancionAlbum(c2);
		l.aniadirAlbumALista(a1);
		l.aniadirCancionALista(c1);
		int a= Sistema.getInstance().getUsuarioEnSesion().getReproducciones();
		l.reproducir();
		int b= Sistema.getInstance().getUsuarioEnSesion().getReproducciones();
		assertTrue(b>a);
	}
}	
