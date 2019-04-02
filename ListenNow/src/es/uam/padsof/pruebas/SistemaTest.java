package es.uam.padsof.pruebas;

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


/**
 * Clase test del Sistema, que prueba las funciones de la clase Sistema
 * @author Carlos Miret, Julian Espada y Pablo Borrelli
 *
 */
public class SistemaTest {
	/*variables accesibles para los TEST*/
	Sistema sys = Sistema.getInstance() ;
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
	
	/**
	 * Metodo que se ejecuta antes de ejecutar cualquier 
	 * test, guardando independencia entre estos
	 * @throws IOException
	 * @throws Mp3PlayerException
	 */
	@Before
	public void setUp() throws IOException, Mp3PlayerException {
		u1 = new UsuarioRegistrado("usuario1", "pass", false, false);
		u2 = new UsuarioRegistrado("usuario2", "pass123", false, false);
		sys.addUsuario(u1);
		sys.addUsuario(u2);
		c1 = new Cancion("Cancion 1", u1, "hola.mp3");
		c2 = new Cancion("Cancion 2", u2, "hive.mp3");
		c3 = new Cancion("Cancion 3", u1, "chicle3.mp3");
		c4 = new Cancion("Cancion 4", u2, "GB 10sec video.mp3");
		sys.setCancionValidada(c1);//procedemos a validar las diferentes canciones
		sys.setCancionValidada(c2);
		sys.setCancionValidada(c3);
		u1.setCanciones(c1);
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
		sys.setNotificaciones(n1);//anadimos las diferentes notificaciones
		sys.setNotificaciones(n2);
		sys.setNotificaciones(n3);
	}
	
	/**
	 * Metodo que se ejecutara despues de cada test
	 */
	@After
	public void reset() {
		sys.reset();
	}
	
	/**
	 * Metodo que prueba inicializacion del sistema
	 * En primer lugar, el administrador hace login en el sistema
	 * Se recogen los datos del sistema y se modifican; se hace logout+guardar
	 * Volvemos a leer el sistema a ver si se han guardado los datos correctamente 
	 * y comprobamos que las fechas y el estado de bloqueado han quedado correctamente guardados y almacenados
	 * @throws InvalidCardNumberException
	 * @throws FailedInternetConnectionException
	 * @throws OrderRejectedException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@Test
	public void testInicializarSistema() throws InvalidCardNumberException, FailedInternetConnectionException, OrderRejectedException, ClassNotFoundException, IOException {
		assertTrue(sys.login("ADMIN", "soyadmin"));
		sys.getUsuarios().get(2).bloquearUsuario(false);
		sys.getUsuarios().get(1).setEsPremium(true);
		sys.getUsuarios().get(1).setFechaPremium(LocalDate.now().minusDays(10));
		sys.getUsuarios().get(2).setFechaBloqueo(LocalDate.now().minusDays(10));
		sys.logout();
		sys.guardarSistema();//guardamos todos los datos hasta ahora del sistema
		sys.reset();
		sys.inicializarSistema();
		assertTrue(sys.getUsuarios().get(1).EsPremium());
		assertTrue(sys.getUsuarios().get(2).getBloqueado());
		assertTrue(sys.login("ADMIN", "soyadmin"));//ADMINISTRADOR REALIZA LOGIN
		sys.getUsuarios().get(2).bloquearUsuario(false);
		sys.getUsuarios().get(1).setEsPremium(true);
		sys.getUsuarios().get(1).setFechaPremium(LocalDate.now().minusDays(30));
		sys.getUsuarios().get(2).setFechaBloqueo(LocalDate.now().minusDays(30));
		sys.logout();						//ES IMPORTANTE RESALTAR QUE GUARDAMOS E INICIALIZAMOS CONTINUAMENTE EL SISTEMA
		sys.guardarSistema();
		sys.inicializarSistema();
		assertFalse(sys.getUsuarios().get(1).EsPremium());
		assertFalse(sys.getUsuarios().get(2).getBloqueado());
		
	}
	
	
	
	/**
	 * Se comprueba el correcto funcionamiento de la lectura del sistema tras guardarlo
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void testLeerSistema() throws FileNotFoundException, IOException, ClassNotFoundException {
		assertTrue(sys.getUsuarios().size() > 1);
		sys.guardarSistema();
		reset();
		assertTrue(sys.getUsuarios().size() == 1);
		sys.leerSistema();
		assertTrue(sys.getUsuarios().size() > 1);
	}

	/**
	 * Se comprueba que la busqueda de la cancion se realiza correctamente
	 */
	@Test
	public void testBuscarCancion() {
		assertEquals(c1.getAutor().getNombre(), sys.buscarCancion("Cancion 1").getAutor().getNombre());
		assertEquals(c1.getTitulo(), sys.buscarCancion("Cancion 1").getTitulo());
		assertNotSame(c1, sys.buscarCancion("Cancion 2"));
		assertNull(sys.buscarCancion("Abc def"));
	}

	/**
	 * Se comprueba que la busqueda del album se realiza correctamente
	 */
	@Test
	public void testBuscarAlbum() {
		assertEquals(a1.getTitulo(), sys.buscarAlbum("Album 1").getTitulo());
	}

	/**
	 * Se comprueba que la busqueda del autor se realiza correctamente
	 */
	@Test
	public void testBuscarAutor() { 
		assertTrue(sys.buscarAutor("usuario1").containsAll(sys.getUsuarios().get(0).getCanciones()));
	}

	/**
	 * Se comprueba que registrarse funciona correctamente
	 */
	@Test
	public void testRegistrarse() {
		UsuarioRegistrado u3 = new UsuarioRegistrado("Usuario3", "p123", true, false);
		assertTrue(sys.registrarse(u3));
		assertTrue(sys.getUsuarios().contains(u3));//verificamos que el usuario se encuentra dentro del array de usuarios del sistema
		assertFalse(sys.registrarse(u3));
	}


	/**
	 * Se comprueba que se puede realizar la accion de login correctamente
	 */
	@Test
	public void testLogin() {
		assertFalse(sys.login("usuario1", "Pass"));//metemos una contrasena no correcta
		assertTrue(sys.login("usuario1", "pass"));//metemos una contrasena correcta
		assertEquals("usuario1", sys.getUsuarioEnSesion().getNombre());
		assertEquals("pass", sys.getUsuarioEnSesion().getContrasena());
		assertTrue(sys.getConectado());
		sys.logout();
		assertTrue(sys.login("ADMIN", "soyadmin"));
		assertNotNull(sys.getUsuarioEnSesion());//vemos que realmente exista un usuario en sesion
		assertEquals("ADMIN", sys.getUsuarioEnSesion().getNombre());
		assertEquals("soyadmin", sys.getUsuarioEnSesion().getContrasena());
		assertTrue(sys.getConectado());
		sys.logout();
	}

	/**
	 * Se comprueba que podemos realizar logout, una vez hecho el login
	 */
	@Test
	public void testLogout() {
		sys.login("usuario1", "pass");
		sys.logout();
		assertNull(sys.getUsuarioEnSesion());//una vez realizado el logout, no quedaran usuarios en la sesion
		assertFalse(sys.getConectado());
		sys.login("ADMIN", "soyadmin");//probamos una vez mas con el administrador
		sys.logout();
		assertNull(sys.getUsuarioEnSesion());
		assertFalse(sys.getConectado());
	}

	/**
	 * Test para comprobar que realmente de aniaden reproducibles al sistema
	 * @throws IOException
	 * @throws Mp3PlayerException
	 */
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

	/**
	 * Probamos a borra el reproducible del sistema, 
	 * @throws Mp3PlayerException
	 * @throws IOException
	 */
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
		assertFalse(sys.getUsuarios().get(1).getAlbunes().contains(a2));
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


	/**
	 * Test. El sistema llevara a cabo dar de baja a un usuario
	 */
	@Test
	public void testDarDeBaja() {
		sys.darDeBaja(u1);
		assertFalse(sys.getUsuarios().contains(u1));//en el sistema ya no existira rastro del usuario
	}

	/**
	 * Test que se encarga de mostrar la notificacion, haciendo login siendo admin 
	 * y haciendo login con diferentes usuarios y diferentes estados de cara a esta aplicacion
	 */
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
	
	/**
	 * Test que comprueba que se han incrementado las reproducciones de los usuarios no registrados
	 */
	@Test
	public void testIncremetaReproducciones() {
		sys.incremetareproduccionesNoRegistrados();
		assertEquals(1, sys.getReproduccionesNoRegistrados());
		sys.incremetareproduccionesNoRegistrados();
		assertEquals(2, sys.getReproduccionesNoRegistrados());
	}


}
