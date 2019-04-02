package es.uam.padsof.pruebas;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import es.uam.padsof.objetoreproducible.Cancion;
import es.uam.padsof.objetoreproducible.ObjetoReproducible;
import es.uam.padsof.sistema.Sistema;
import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.exceptions.Mp3PlayerException;


/**
 * Clase test del objeto comentable, que prueba las funciones de la clase Objeto Comentable
 * @author Carlos Miret, Julian Espada y Pablo Borrelli
 *
 */
public class ObjetoReproducibleTest {
	Sistema sys=Sistema.getInstance();
	private UsuarioRegistrado u1;
	private  ObjetoReproducible obj_B;
	
	/**
	 * Metodo principal del test de objeto reproducible
	 * @throws Mp3PlayerException 
	 * @throws IOException 
	 * @throws java.lang.Exception
	 */
	@Before
	public void testObjetoReproducible() throws Mp3PlayerException, IOException{
		sys.registrarse(u1);
		File ruta=new File("cancionesSistema/hive-1.mp3");
		ruta.delete();
		obj_B=new Cancion("Cancion93282", sys.getAdmin(), "hive-1.mp3");
		sys.login("ADMIN", "soyadmin");

		sys.anadirReproducible(obj_B);
		((Cancion)obj_B).validarCancion();

	}


	/**
	 * @throws FileNotFoundException
	 * @throws Mp3PlayerException
	 * @throws InterruptedException
	 */
	@Test
	public void testReproducir() throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		assertTrue(sys.getCancionesValidadas().contains((Cancion)obj_B));
		obj_B.reproducir();
	}

	
	/**
	 * @throws FileNotFoundException
	 * @throws Mp3PlayerException
	 * @throws InterruptedException
	 */
	@Test
	public void testPararReproduccion() throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		assertTrue(sys.getCancionesValidadas().contains((Cancion)obj_B));
		obj_B.pararReproduccion();
	}

}
