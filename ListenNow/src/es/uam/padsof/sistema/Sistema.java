package es.uam.padsof.sistema;

import java.time.LocalDate;
import java.util.*;
import es.uam.padsof.objetoreproducible.*;
import es.uam.padsof.usuario.*;

/**
 * 
 */
public class Sistema {

	/**
	 * 
	 */
	public final static Sistema sistema = new Sistema();

	/**
	 * Default constructor
	 */
	private Sistema() {
		
	}
	/**
	 * Contador de lar reproducciones utilizadas por los usuarios no registrados
	 */
	private int reproducciones;

	/**
	 * Indica si hay una sesion abierta
	 */
	private boolean conectado = false;

	/**
	 * Indica si hay una sesion de administrador abierta
	 */
	private boolean adminConectado = false;
	
	/**
	 * Indica el numero maximo de reproducciones que un usuario no registrado puede realizar.
	 * Este parametro puede ser modificado por el administrador
	 */
	private int nRepAnonimas;
	
	

	/**
	 * Indica el numero maximo de reproducciones que un usuario registrado no premium puede realizar.
	 * Este parametro puede ser modificado por el administrador
	 */
	private int nRepRegistrado;

	/**
	 * Indica el numero de reproducciones que un usuario tiene que recibir para recibir una cuenta premium gratuitamente.
	 * Este parametro puede ser modificado por el administrador
	 */
	private int nRepRecompensa;

	/**
	 * Lista de usuarios registrados
	 */
	private ArrayList<UsuarioRegistrado> usuarios =  new ArrayList<UsuarioRegistrado>();
	
	/**
	 * Lista de canciones en el sistema que tienen que ser validadas
	 */
	private ArrayList<Cancion> cancionesValidar =  new ArrayList<Cancion>();
	
	/**
	 * Lista de albums en el sistema
	 */
	private ArrayList<Album> albums =  new ArrayList<Album>();
	
	/**
	 * Lista de canciones que han sido validadas por el administrador
	 */
	private ArrayList<Cancion> cancionesValidadas =  new ArrayList<Cancion>();
	
	/**
	 * Lista de canciones que han sido rechazadas por el administrador
	 */
	private ArrayList<Cancion> cancionesRechazadas =  new ArrayList<Cancion>();
	
	/**
	 * Lista de notificaciones para el usuario que ha realizado el login
	 */
	private ArrayList<Notificacion> notificaciones =  new ArrayList<Notificacion>();
	
	/**
	 * Contiene al usuario administrador
	 */
	private UsuarioRegistrado admin = null;
	
	/**
	 * Contiene al usuario que esta utilizando la sesion
	 */
	private UsuarioRegistrado usuarioEnSesion;
	
	
	/**
	 * 
	 */
	public void inicializarSistema() {
		
	}
	
	/**
	 * @param titulo de la cancion que se quiere buscar
	 * @return cancion buscada si se encuentra en la lista, null si no se encuentra
	 */
	public Cancion buscarCancion(String titulo) {
		for (Cancion c: cancionesValidadas) {
			if (c.getTitulo() == titulo)
				return c;
		}
		return null;
	}

	/**
	 * @param album 
	 * @return
	 */
	public Album buscarAlbum(String titulo) {
		for (Album a: albums) {
			if (a.getTitulo() == titulo)
				return a;
		}
		return null;
	}

	/**
	 * @param autor 
	 * @return
	 */
	public ArrayList<Cancion> buscarAutor(String autor) {
		for (UsuarioRegistrado u: usuarios)
			if (u.getNombre() == autor)
				return u.getCanciones();
		return null;
	}

	/**
	 * @param reproducible
	 */
	public void reproducirObjeto(ObjetoReproducible reproducible) {
		// TODO implement here
	}

	/**
	 * @param reproducible
	 */
	public void pararReproduccion(ObjetoReproducible reproducible) {
		// TODO implement here
	}

	/**
	 * @param usuario
	 */
	public void registrarse(UsuarioRegistrado usuario) {
		usuarios.add(usuario);
	}

	/**
	 * @param usuario 
	 * @param contrasena
	 */
	public boolean login(String usuario, String contrasena) {
		if(admin.getNombre() == usuario && admin.getContrasena() == contrasena)
			adminConectado = true;
		for (UsuarioRegistrado u: usuarios) {
			if (usuarios.getNombre() == usuario && usuarios.getContrasena() == contrasena) {
				usuarioEnSesion = u;
				conectado = true;
				return true;
				}
			else
				return false;
		}
	}

	/**
	 * 
	 */
	public void logout() {
		usuarioEnSesion = null;
		adminConectado = false;
		conectado = false;
	}

	/**
	 * @param reproducible
	 */
	public void anadirReproducible(ObjetoReproducible reproducible) {
		if (reproducible instanceof Cancion)
			cancionesValidar.add((Cancion) reproducible);
		else if (reproducible instanceof Album)
			albums.add((Album) reproducible);
	}

	/**
	 * @param reproducible
	 */
	public void borrarReproducible(ObjetoReproducible reproducible) {
		if (reproducible instanceof Cancion) {
			if (cancionesValidadas.contains(reproducible))
				cancionesValidadas.remove(reproducible);
			else if (cancionesValidar.contains(reproducible))
				cancionesValidar.remove(reproducible);
			else if (cancionesRechazadas.contains(reproducible))
				cancionesRechazadas.remove(reproducible);
		}
		else if (reproducible instanceof Album) {
			albums.remove(reproducible);
		}
	}

	/**
	 * @param usuario
	 */
	public void recompensaPremium(UsuarioRegistrado usuario) {
		Calendar fecha = Calendar.getInstance();
		int reproducciones = 0;
		for (Cancion c: usuario.getCanciones()) {
			reproducciones += c.getNreproducciones();
		}
		if (reproducciones >= nRepRecompensa && usuario.EsPremium() == false) {
			usuario.setEsPremium(true);
			usuario.setFechaPremium(fecha.getTime());
		}
	}

	/**
	 * 
	 */
	public void caducaPremium() {
		LocalDate fecha = LocalDate.now().minusDays(30);
		for (UsuarioRegistrado u: usuarios) {
			if(fecha.isBefore(u.getFechaPremium())) {
				u.setEsPremium(false);
			}
		}
	}

	/**
	 * @param usuario
	 */
	public void bloquearUsuario(UsuarioRegistrado usuario) {
		usuario.setBloqueado(true);
	}

	/**
	 * @param usuario
	 */
	public void desbloquearUsuario(UsuarioRegistrado usuario) {
		usuario.setBloqueado(false);
	}
	
	/**
	 * @param usuario
	 */
	public void darDeBaja(UsuarioRegistrado usuario) {
		usuarios.remove(usuario);
	}

	/**
	 * 
	 */
	public void mostrarNotificacion() {
		// TODO implement here
	}

	public int getnRepAnonimas() {
		return nRepAnonimas;
	}

	public void setnRepAnonimas(int nRepAnonimas) {
		this.nRepAnonimas = nRepAnonimas;
	}

	public int getnRepRegistrado() {
		return nRepRegistrado;
	}

	public void setnRepRegistrado(int nRepRegistrado) {
		this.nRepRegistrado = nRepRegistrado;
	}

	public int getnRepRecompensa() {
		return nRepRecompensa;
	}

	public void setnRepRecompensa(int nRepRecompensa) {
		this.nRepRecompensa = nRepRecompensa;
	}
	

}