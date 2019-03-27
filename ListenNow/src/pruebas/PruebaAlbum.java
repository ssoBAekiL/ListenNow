package pruebas;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import es.uam.padsof.objetoreproducible.Album;
import es.uam.padsof.objetoreproducible.Cancion;
import es.uam.padsof.objetoreproducible.Comentario;
import es.uam.padsof.sistema.Sistema;
import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.exceptions.Mp3PlayerException;

public class PruebaAlbum{
	Sistema sys = Sistema.getInstance();
	private UsuarioRegistrado u1 = new UsuarioRegistrado("carlos","6253", false,false);
	private Cancion c1;
	private Cancion c2;
	private Comentario comment;
	private Album album;
	

	@Before
	public void setUp() throws Exception {
		sys.login("ADMIN", "soyadmin");
		sys.setUsuarioEnSesion(Sistema.getInstance().getAdmin());
		c1 = new Cancion ("Cancion 1", u1, "chicle3.mp3");
		c2 = new Cancion("Cancion 2", u1, "hive.mp3");
		comment = new Comentario("INCREIBLE", sys.getUsuarioEnSesion(), LocalDate.now(), 8);
		album = new Album ("Album 1", sys.getUsuarioEnSesion());
	}

	/*
	 * Test de reproducir un album entero
	 */
	@Test
	public void testReproducirAlbum()throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		album.aniadirCancionAlbum(c1);
		album.aniadirCancionAlbum(c2);
		int a= Sistema.getInstance().getUsuarioEnSesion().getReproducciones();
		album.reproducir();
		int b= Sistema.getInstance().getUsuarioEnSesion().getReproducciones();
		assertTrue(b>a);
	}
	
	/*
	 * Test de añadir una cancion a un album
	 */
	@Test
	public void testAniadirCancionAlbum() throws FileNotFoundException {
		assertTrue(album.aniadirCancionAlbum(c1));
		assertTrue(album.aniadirCancionAlbum(c2));
		assertFalse(album.aniadirCancionAlbum(c1));
		assertFalse(album.aniadirCancionAlbum(c2));
	}
	
	/*
	 * Test de borrar cancion de un album
	 */
	@Test
	public void testBorrarCancionAlbum() throws FileNotFoundException {
		album.aniadirCancionAlbum(c1);
		album.aniadirCancionAlbum(c2);
		assertTrue(album.borrarCancionAlbum(c1));
		assertTrue(album.borrarCancionAlbum(c2));
		assertFalse(album.getCanciones().contains(c1));
		assertFalse(album.getCanciones().contains(c2));
		assertFalse(album.borrarCancionAlbum(c1));
		assertFalse(album.borrarCancionAlbum(c2));
	}
	
	/*
	 * Test de añadir comentario a album correctamente
	 */
	@Test
	public void testAniadirComentarioAlbum() {
		assertTrue(album.anadirComentario(comment));
		assertTrue(album.getComentarios().contains(comment));
	}
	

}
