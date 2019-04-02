package es.uam.padsof.pruebas;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import es.uam.padsof.objetoreproducible.*;
import es.uam.padsof.sistema.Sistema;
import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * Clase que comprueba el correcto funcionamiento de la clase album
 * @author Julian Espada, Carlos Miret y Pablo Borrelli
 *
 */
public class AlbumTest{
	Sistema sys = Sistema.getInstance();
	private UsuarioRegistrado u1 = new UsuarioRegistrado("carlos","6253", false,false);
	private Cancion c1;
	private Cancion c2;
	private Comentario comment;
	private Album album;
	
	/**
	 * Metodo que se ejecuta antes de ejecutar cualquier 
	 * test, guardando independencia entre estos
	 * @throws IOException 
	 */
	@Before
	public void setUp() throws Exception {
		sys.login("ADMIN", "soyadmin");
		File ruta=new File("cancionesSistema/chicle3.mp3");
		ruta.delete();
		File ruta1=new File("cancionesSistema/hive-1.mp3");
		ruta1.delete();
		c1 = new Cancion ("Cancion 1", u1, "chicle3.mp3");
		c2 = new Cancion("Cancion 2", u1, "hive-1.mp3");
		comment = new Comentario("INCREIBLE", sys.getUsuarioEnSesion(), LocalDate.now(), 8);
		album = new Album ("Album 1", sys.getUsuarioEnSesion());
	}

	/**
	 * Test de reproducir un album entero
	 */
	@Test
	public void testReproducirAlbum()throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		album.aniadirCancionesAlbum(c1);
		album.aniadirCancionesAlbum(c2);
		int a= Sistema.getInstance().getUsuarioEnSesion().getReproducciones();
		album.reproducir();
		int b= Sistema.getInstance().getUsuarioEnSesion().getReproducciones();
		assertTrue(b>a);
	}
	
	/**
	 * Test de aniadir una cancion a un album
	 */
	@Test
	public void testAniadirCancionAlbum() throws FileNotFoundException {
		assertTrue(album.aniadirCancionAlbum(c1));
		assertTrue(album.aniadirCancionAlbum(c2));
		assertFalse(album.aniadirCancionAlbum(c1));
		assertFalse(album.aniadirCancionAlbum(c2));
	}
	
	/**
	 * Test de borrar cancion de un album
	 */
	@Test
	public void testBorrarCancionAlbum() throws FileNotFoundException {
		album.aniadirCancionAlbum(c1);
		album.aniadirCancionAlbum(c2);
		assertTrue(album.borrarCancionesAlbum(c1));
		assertTrue(album.borrarCancionesAlbum(c2));
		assertFalse(album.getCanciones().contains(c1));
		assertFalse(album.getCanciones().contains(c2));
		assertFalse(album.borrarCancionAlbum(c1));
		assertFalse(album.borrarCancionAlbum(c2));
	}
	
	/**
	 * Test de anadir comentario a album correctamente
	 */
	@Test
	public void testAniadirComentarioAlbum() {
		assertTrue(album.anadirComentario(comment));
		assertTrue(album.getComentarios().contains(comment));
	}
	

}
