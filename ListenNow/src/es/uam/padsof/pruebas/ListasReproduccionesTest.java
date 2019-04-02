package es.uam.padsof.pruebas;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import es.uam.padsof.objetoreproducible.Album;
import es.uam.padsof.objetoreproducible.Cancion;
import es.uam.padsof.objetoreproducible.ListaReproducciones;
import es.uam.padsof.sistema.Sistema;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * Clase que comprueba el correcto funcionamiento de la clase ListaReproducciones
 * @author Julian Espada, Carlos Miret y Pablo Borrelli
 *
 */
public class ListasReproduccionesTest {
	Sistema sys = Sistema.getInstance();
	private Album a1;
	private Album a2;
	private Cancion c1;
	private Cancion c2;
	private Cancion c3;
	private ListaReproducciones l1, l2;

	/**
	 * Funcion que se ejecutara antes de cada test
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		sys.login("ADMIN", "soyadmin");
		a1 = new Album("Album 1",sys.getUsuarioEnSesion());
		a2 = new Album("Album 2", sys.getUsuarioEnSesion());
		File ruta=new File("cancionesSistema/GB 10sec video.mp3");
		ruta.delete();
		File ruta1=new File("cancionesSistema/hive-1.mp3");
		ruta1.delete();
		File ruta2=new File("cancionesSistema/hive289.mp3");
		ruta2.delete();
		c1 = new Cancion("Cancion 1", sys.getUsuarioEnSesion(), "GB 10sec video.mp3");
		c2 = new Cancion("Cancion 2", sys.getUsuarioEnSesion(), "hive-1.mp3");
		c3 = new Cancion("Cancion 3", sys.getUsuarioEnSesion(), "hive289.mp3");
		l1 = new ListaReproducciones("Lista 1", sys.getUsuarioEnSesion());
		l2 = new ListaReproducciones("Lista 2", sys.getUsuarioEnSesion());
		sys.anadirReproducible(c1);
		sys.anadirReproducible(c2);
		sys.anadirReproducible(c3);
	}

	/**
	 * Test de anadir cancion a lista de reproduccion
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testAniadirCancionLista() throws FileNotFoundException {
		a1.aniadirCancionAlbum(c1);
		a2.aniadirCancionAlbum(c2);
		l1.aniadirAlbumALista(a1);
		l1.aniadirAlbumALista(a2);
		l1.aniadirCancionALista(c2);
		assertTrue(l1.aniadirCancionALista(c3));
		assertFalse(l1.aniadirCancionALista(c2));
		assertFalse(l1.aniadirCancionALista(c1));
	}
	
	/**
	 * Test para anadir un album a una lista
	 * @throws FileNotFoundException 
	 * 
	 */
	@Test
	public void testAniadirAlbumLista() throws FileNotFoundException {
		a1.aniadirCancionAlbum(c1);
		a2.aniadirCancionAlbum(c2);
		l1.aniadirCancionALista(c3);
		assertTrue(l1.aniadirAlbumALista(a1));
		assertTrue(l1.aniadirAlbumALista(a2));
		a1.aniadirCancionAlbum(c3);
		assertFalse(l1.aniadirAlbumALista(a1));
	}
	
	/**
	 * Test para anadir una lista a una lista
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testAniadirListaLista() throws FileNotFoundException {
		l2.aniadirAlbumALista(a1);
		l2.aniadirAlbumALista(a2);
		assertTrue(l1.aniadirListaALista(l2));
	}
	
	/**
	 * Test de comprobar que cuando se borra una cancion del sistema no existe en la lista
	 */
	@Test
	public void testBorrarCancionLista() {
		l1.aniadirCancionALista(c1);
		l1.borrarCancionLista(c1);
		assertFalse(l1.getListaCanciones().contains(c1));
	}
	
	/**
	 * Test de comprobar que cuando se borra un album del sistema, no existe en la lista
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testBorrarAlbumLista() throws FileNotFoundException {
		l1.aniadirAlbumALista(a1);
		l1.borrarAlbumALista(a1);
		assertFalse(l1.getListaAlbunes().contains(a1));
	}
	
	/**
	 * Test de reproduccion de listas
	 */
	@Test
	public void testReproducirLista() throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		a1.aniadirCancionAlbum(c3);
		a1.aniadirCancionAlbum(c1);
		l1.aniadirAlbumALista(a1);
		l1.aniadirCancionALista(c2);
		l2.aniadirListaALista(l1);
		int a= Sistema.getInstance().getUsuarioEnSesion().getReproducciones();
		l2.reproducir();
		int b= Sistema.getInstance().getUsuarioEnSesion().getReproducciones();
		assertTrue(b>a);
	}
}