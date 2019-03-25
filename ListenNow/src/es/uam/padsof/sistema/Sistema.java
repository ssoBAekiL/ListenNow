package es.uam.padsof.sistema;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import es.uam.padsof.objetoreproducible.*;
import es.uam.padsof.sistema.Notificacion.TipoNotificacion;
import es.uam.padsof.usuario.*;
import pads.musicPlayer.Mp3Player;
import pads.musicPlayer.exceptions.Mp3PlayerException;
import java.io.*;

/**
 * Clase sistema (principal)
 */
public class Sistema {
	/**
	 * Variable Sistema
	 */
	public static Sistema sistema = null;

	
	/**
	 * Contador de lar reproducciones utilizadas por los usuarios no registrados
	 */
	private  int reproduccionesNoRegistrados;

	/**
	 * Indica si hay una sesion abierta
	 */
	private  boolean conectado;

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
	private ArrayList<UsuarioRegistrado> usuarios;
	
	/**
	 * Lista de canciones en el sistema que tienen que ser validadas
	 */
	private ArrayList<Cancion> cancionesValidar;
	
	/**
	 * Lista de canciones en el sistema rechazadas
	 */
	private ArrayList<Cancion> cancionesRechazadas;
	
	/**
	 * Lista de albunes en el sistema
	 */
	private ArrayList<Album> albunes;
	
	/**
	 * Lista de canciones que han sido validadas por el administrador
	 */
	
	private ArrayList<Cancion> cancionesValidadas;

	/**
	 * Lista de notificaciones para el usuario que ha realizado el login
	 */
	private ArrayList<Notificacion> notificaciones;
	
	
	/**
	 * Canciones notificadas
	 */
	private ArrayList<Cancion> cancionesNotificadas;

	
	/**
	 * Contiene al usuario administrador
	 */
	private UsuarioRegistrado admin;
	
	/**
	 * Contiene al usuario que esta utilizando la sesion
	 */
	private UsuarioRegistrado usuarioEnSesion;

	/**
	 * Variable para poder generar ids de forma automatica
	 */
	private AtomicLong generador=new AtomicLong(1);
	
	
	
	/**
	 * Metodo constructor de sistema
	 */
	private Sistema() {
		this.reproduccionesNoRegistrados = 0;
		this.conectado = false;
		this.nRepAnonimas = 10;
		this.nRepRegistrado = 10;
		this.nRepRecompensa = 10;
		this.cancionesValidar = new ArrayList<Cancion>();
		this.albunes = new ArrayList<Album>();
		this.cancionesValidadas = new ArrayList<Cancion>();
		this.cancionesRechazadas=new ArrayList<Cancion>();
		this.cancionesNotificadas = new ArrayList<Cancion>();
		this.notificaciones = new ArrayList<Notificacion>();
		this.usuarios = new ArrayList<UsuarioRegistrado>();
		this.admin=(new UsuarioRegistrado("ADMIN"/*nombre*/, "soyadmin"/*contrasena*/, true/*premium*/, true));
		this.admin.setFechaPremium(LocalDate.now());
		this.addUsuario(admin);
	}

	
    /**
     * Funcion que crea la instancia Sistema, para poder ser accesible desde otras clases
     */
    private synchronized static void createInstance() {
        if (sistema == null) { 
            sistema = new Sistema();
        }
    }
    
    
    /**
     * Funcion getter de la instancia
     * @return la instancia de Sistema ---> sistema
     */
    public static Sistema getInstance() {
        if (sistema == null) createInstance();
        return sistema;
    }	
	
	
	
	/**
	 * 
	 */
	public void inicializarSistema() {
		for (UsuarioRegistrado u: usuarios) {
			if (u.getBloqueado() == true && u.getBloqueoPermanente() == false)
				desbloquearUsuario(u);
			if (u.EsPremium() == true) {
				caducaPremium(u);
			}
		}
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
		for (Album a: albunes) {
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
		ArrayList<Cancion> cancionesAutor = new ArrayList<Cancion>();
		for (Cancion c: cancionesValidadas)
			if (c.getAutor().getNombre() == autor)
				cancionesAutor.add(c);
		/*for (UsuarioRegistrado u: usuarios)
			if (u.getNombre() == autor)
				return u.getCanciones();*/
		return cancionesAutor;
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
		if (conectado == false) {
			for (UsuarioRegistrado u: usuarios) {
				if (u.getNombre().equals(usuario) && u.getContrasena() == contrasena && u.getBloqueado() == false) {
					usuarioEnSesion = u;
					conectado = true;
					mostrarNotificacion();
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 */
	public void logout() {
		usuarioEnSesion = null;
		conectado = false;
	}

	/**
	 * @param reproducible
	 */
	public void anadirReproducible(ObjetoReproducible reproducible) {
		if (reproducible instanceof Cancion && conectado == true) {
			cancionesValidar.add((Cancion) reproducible);
		}
		else if (reproducible instanceof Album && conectado == true)
			albunes.add((Album) reproducible);
	}

	/**
	 * @param reproducible
	 */
	public void borrarReproducible(ObjetoReproducible reproducible) {
		if (reproducible instanceof Cancion && (usuarioEnSesion == reproducible.getAutor() || usuarioEnSesion.equals(Sistema.getInstance().getAdmin()))) {
			if (cancionesValidadas.contains(reproducible))
				cancionesValidadas.remove(reproducible);
			else if (cancionesValidar.contains(reproducible))
				cancionesValidar.remove(reproducible);
			else if(cancionesNotificadas.contains(reproducible))
				cancionesNotificadas.remove(reproducible);
			else if(cancionesRechazadas.contains(reproducible)) 
				cancionesRechazadas.contains(reproducible);
		}
		else if (reproducible instanceof Album && usuarioEnSesion == reproducible.getAutor()) {
			albunes.remove(reproducible);
		}
		
	}

	/**
	 * 
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
	public void caducaPremium(UsuarioRegistrado usuario) {
		LocalDate fecha = LocalDate.now().minusDays(29);
		if(fecha.isAfter(usuario.getFechaPremium())) {
			usuario.setEsPremium(false);
			usuario.setFechaPremium(null);
		}
	}
	
	
	

	/**
	 * @param usuario
	 */
	public void bloquearUsuario(UsuarioRegistrado usuario, boolean permanente) {
		if(usuarioEnSesion == admin) {
			if (permanente == false) {
				usuario.setBloqueado(true);
				usuario.setFechaBloqueo(LocalDate.now());
			}
			else
				usuario.setBloqueoPermanente();
		}
	}

	/**
	 * Metodo que permite desbloquear un usuario
	 * @param usuario
	 */
	public void desbloquearUsuario(UsuarioRegistrado usuario) {
		LocalDate fecha = LocalDate.now().minusDays(29);
		if (usuario.getBloqueado() == true && usuario.getBloqueoPermanente() == false) {
			if (fecha.isAfter(usuario.getFechaBloqueo())) {
				usuario.setBloqueado(false);
			}
			else if (usuarioEnSesion == admin)
				usuario.setBloqueado(false);
		}
		else return;
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
	public ArrayList<Notificacion> mostrarNotificacion() {
		if(conectado == true) {
			ArrayList<Notificacion> notificaciones = new ArrayList<Notificacion>();
			for (Notificacion n: this.notificaciones) {
				if(n.getUsuariosNotificados().contains(usuarioEnSesion))
					notificaciones.add(n);
			}
			return notificaciones;
		}
		return null;
	}

	/**
	 * @return the usuarios
	 */
	public ArrayList<UsuarioRegistrado> getUsuarios() {
		return usuarios;
	}

	/**
	 * @param usuarios the usuarios to set
	 */
	public void setUsuarios(ArrayList<UsuarioRegistrado> usuarios) {
		this.usuarios = usuarios;
	}

	public int getnRepAnonimas() {
		return nRepAnonimas;
	}

	public int getnRepRegistrado() {
		return nRepRegistrado;
	}
	
	
	public int getnRepRecompensa() {
		return nRepRecompensa;
	}
	
	
	/**
	 * @return the generador
	 */
	public AtomicLong getGenerador() {
		return generador;
	}



	/************************************************/
	public void setnRepAnonimas(int nRepAnonimas) {
		if(this.getUsuarioEnSesion().equals(this.admin))
			this.nRepAnonimas = nRepAnonimas;
	}

	public void setnRepRegistrado(int nRepRegistrado) {
		if(this.getUsuarioEnSesion().equals(this.admin))
			this.nRepRegistrado = nRepRegistrado;
	}

	public void setnRepRecompensa(int nRepRecompensa) {
		if(this.getUsuarioEnSesion().equals(this.admin))
			this.nRepRecompensa = nRepRecompensa;
	}
	/************************************************/



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
	
	public void setCancionValidada(Cancion cancionValidada) {
		this.cancionesValidadas.add(cancionValidada);
	}
	
	/**
	 * Metodo que incrementa el numero de reproducciones
	 */
	public void incremetareproduccionesNoRegistrados() {
		this.reproduccionesNoRegistrados++;
	}
	
	
	/**
	 * @return the cancionesNotificadas
	 */
	public ArrayList<Cancion> getCancionesNotificadas() {
		return cancionesNotificadas;
	}


	/**
	 * Metodo que devuelve un usuario registrado dentro del Sistema de la aplicacion
	 * @param i
	 * @return 
	 */
	public UsuarioRegistrado getUsuarioItera(int i) {
		return usuarios.get(i);
	}
	
	/**
	 * Metodo que devuelve un usuario registrado dentro del Sistema de la aplicacion
	 * @param i
	 * @return 
	 */
	public Cancion getCancionItera(int i) {
		return this.cancionesValidadas.get(i);
	}
	
	

	/**
	 * @return the cancionesRechazadas
	 */
	public ArrayList<Cancion> getCancionesRechazadas() {
		return cancionesRechazadas;
	}


	public int getNumUsuarios() {
		return this.usuarios.size();
	}

	public void addUsuario(UsuarioRegistrado u) {
		this.usuarios.add(u);
	}

	public ArrayList<Cancion> getCancionesValidar() {
		return cancionesValidar;
	}
	
	public void setNotificaciones(Notificacion notificacion) {
		notificaciones.add(notificacion);
	}
	
	public int getNumeroCanciones() {
		return cancionesValidadas.size();
	}

	public UsuarioRegistrado getAdmin() {
		return admin;
	}


	public void setAdmin(UsuarioRegistrado admin) {
		this.admin = admin;
	}
	
	public void setAlbum(Album album) {
		albunes.add(album);
	}
	
	
	
	/**
	 * @return the notificaciones
	 */
	public ArrayList<Notificacion> getNotificaciones() {
		return notificaciones;
	}


	public ArrayList<Album> getAlbunes() {
		return albunes;
	}


	//**************************************************
	public UsuarioRegistrado getUsuarioEnSesion() {
		return usuarioEnSesion;
	}
	

	/**
	 * @param usuarioEnSesion the usuarioEnSesion to set
	 */
	public void setUsuarioEnSesion(UsuarioRegistrado usuarioEnSesion) {
		this.usuarioEnSesion = usuarioEnSesion;
	}


	public boolean getConectado() {
		return conectado;
	}
	
	public int getReproduccionesNoRegistrados() {
		return reproduccionesNoRegistrados;
	}
	
	public void reset() {
		this.reproduccionesNoRegistrados = 0;
		this.conectado = false;
		this.nRepAnonimas = 10;
		this.nRepRegistrado = 10;
		this.nRepRecompensa = 10;
		this.cancionesValidar.clear();
		this.albunes.clear();
		this.cancionesValidadas.clear();
		this.cancionesNotificadas.clear();
		this.notificaciones.clear();
		this.usuarios.clear();
		this.addUsuario(admin);
	}

}