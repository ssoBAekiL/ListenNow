package pruebas;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import es.uam.padsof.objetoreproducible.Album;
import es.uam.padsof.objetoreproducible.Cancion;
import es.uam.padsof.objetoreproducible.Comentario;
import es.uam.padsof.objetoreproducible.ListaReproducciones;
import es.uam.padsof.sistema.Sistema;

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
		c1 = new Cancion("Cancion 1", sys.getUsuarioEnSesion(), "chicle.mp3");
		c2 = new Cancion("Cancion 2", sys.getUsuarioEnSesion(), "hive.mp3");
		c3 = new Cancion("Cancion 3", sys.getUsuarioEnSesion(), "np.mp3");
		l = new ListaReproducciones("Lista 1", sys.getUsuarioEnSesion());
		comment = new Comentario("INCREIBLE", sys.getUsuarioEnSesion(), LocalDate.now(), 8);
	}

	/*
	 * Test de añadir cancion a lista de reproduccion
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
	
	@Test
	public void testBorrarAlbumLista() {
		
	}

}
