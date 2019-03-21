package es.uam.padsof.sistema;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.*;

import es.uam.padsof.objetoreproducible.*;
import es.uam.padsof.sistema.Notificacion.TipoNotificacion;
import es.uam.padsof.usuario.*;
import pads.musicPlayer.Mp3Player;
import pads.musicPlayer.exceptions.Mp3PlayerException;
import java.io.*;

/**
 * 
 */
public class Sistema {

	/**
	 * Variable Sistema
	 */
	public static Sistema sistema = null;

	//tener lista con usuarios potenciales y una refrencia a usuario ; inicio sesion en sistema, recorro los usuarios y si coinccide con algun usuario y si no compruebo con usua
	//administrador
	/**
	 * Default constructor
	 */
	private Sistema() {
		
		
	}
	
	
	//nombre de la clase.getInstance//Esta compartido por todasse puede obtener desde cualquier pare del codigo. estatico, se reserva memoria automatica, va a existir antes de su instanciacion
    private synchronized static void createInstance() {
        if (sistema == null) { 
            sistema = new Sistema();
        }
    }

	//garantizamos que solo exista una instancia del sistema
    public static Sistema getInstance() {
        if (sistema == null) createInstance();
        return sistema;
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
	private static ArrayList<UsuarioRegistrado> usuarios =  new ArrayList<UsuarioRegistrado>();
	
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
	private ArrayList<Cancion> cancionesValidadas;

	/**
	 * Lista de notificaciones para el usuario que ha realizado el login
	 */
	private ArrayList<Notificacion> notificaciones;
	
	/**
	 * Contiene al usuario administrador
	 */
	private UsuarioRegistrado admin = null;
	
	/**
	 * Contiene al usuario que esta utilizando la sesion
	 */
	private UsuarioRegistrado usuarioEnSesion;
	
	private Mp3Player player = new Mp3Player();
	
	
	
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
		for (UsuarioRegistrado u: usuarios) {
			if (u.getNombre() == usuario && u.getContrasena() == contrasena) {
				usuarioEnSesion = u;
				conectado = true;
				if (u.isAdmin() == true)
					adminConectado = true;
				return true;
			}
		}
		return false;
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
		}
		else if (reproducible instanceof Album) {
			albums.remove(reproducible);
		}
	}

	/**
	 * @param usuario
	 */
	public void recompensaPremium(UsuarioRegistrado usuario) {
		LocalDate fecha = LocalDate.now();
		int reproducciones = 0;
		for (Cancion c: usuario.getCanciones()) {
			reproducciones += c.getNreproducciones();
		}
		if (reproducciones >= nRepRecompensa && usuario.EsPremium() == false) {
			usuario.setEsPremium(true);
			usuario.setFechaPremium(fecha);
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
		for (Notificacion n: notificaciones) {
			if(n.getUsuariosNotificados().contains(usuarioEnSesion))
				n.mostrarNotificacion();
		}
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

	/**
	 * @return the cancionesValidadas
	 */
	public ArrayList<Cancion> getCancionesValidadas() {
		return cancionesValidadas;
	}

	/**
	 * @param cancionesValidadas the cancionesValidadas to set
	 */
	public void setCancionesValidadas(ArrayList<Cancion> cancionesValidadas) {
		this.cancionesValidadas = cancionesValidadas;
	}
	
	public UsuarioRegistrado getUsuario(int i) {
		return usuarios.get(i);
	}


	public static int getNumUsuarios() {
		return usuarios.size();
	}


	public ArrayList<Cancion> getCancionesValidar() {
		return cancionesValidar;
	}
	
	public void setNotificaciones(TipoNotificacion tipo, Cancion cancion, ArrayList<UsuarioRegistrado> usuarios) {
		notificaciones.add(new Notificacion(tipo, cancion, usuarios));
	}
	
	public int getNumeroCanciones() {
		return cancionesValidadas.size();
	}
	
	public boolean esAdmin() {
		return adminConectado;
	}

}