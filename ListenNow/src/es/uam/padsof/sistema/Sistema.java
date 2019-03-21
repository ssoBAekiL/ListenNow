package es.uam.padsof.sistema;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

	
	/**
	 * Contador de lar reproducciones utilizadas por los usuarios no registrados
	 */
	private  int reproducciones;

	/**
	 * Indica si hay una sesion abierta
	 */
	private  boolean conectado;

	/**
	 * Indica si hay una sesion de administrador abierta
	 */
	private  boolean adminConectado;
	/////??????????
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

	
	private Sistema() {
		this.reproducciones = 0;
		this.conectado = true;
		this.adminConectado = true;
		this.nRepAnonimas = 0;
		this.nRepRegistrado = 0;
		this.nRepRecompensa = 0;
		this.cancionesValidar = new ArrayList<Cancion>();
		this.albunes = new ArrayList<Album>();
		this.cancionesValidadas = new ArrayList<Cancion>();
		this.cancionesNotificadas = new ArrayList<Cancion>();
		this.notificaciones = new ArrayList<Notificacion>();
		this.usuarios = new ArrayList<UsuarioRegistrado>();
		this.admin=(new UsuarioRegistrado("0000000000000000"/*num tarjeta*/, "ADMIN"/*nombre*/, "soyadmin"/*contrasena*/, true/*premium*/, true));
		this.addUsuario(admin);
	}
	

	//tener lista con usuarios potenciales y una refrencia a usuario ; inicio sesion en sistema, recorro los usuarios y si coinccide con algun usuario y si no compruebo con usua
	//administrador
	
	
	
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
	

    
    

	
	//private Mp3Player player = new Mp3Player();
	
	
	
	/**
	 * 
	 */
	public void inicializarSistema() {
		for (UsuarioRegistrado u: usuarios) {
			if (u.getBloqueado() == true && u.getBloqueoPermanente() == false)
				u.setBloqueado(false);
			if (u.EsPremium() == true)
				caducaPremium();
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
	 * Metodo que da la capacidad a un usuario a borrar un album
	 * @param album
	 */
	public void borrarAlbum(Album album) {
		if(Sistema.getInstance().adminConectado==true)
			Sistema.getInstance().borrarReproducible(album);
	}


	/**
	 * Metodo que se encarga de anadir una cancion al conjunto de canciones del usuario
	 * @param cancion
	 */
	public void borrarCancion(Cancion cancion) {
		if(Sistema.getInstance().adminConectado==true)
			Sistema.getInstance().borrarReproducible(cancion); 
	}
	

	/**
	 * @param usuario 
	 * @param contrasena
	 */
	public boolean login(String usuario, String contrasena) {
		for (UsuarioRegistrado u: usuarios) {
			if (u.getNombre().equals(usuario) && u.getContrasena() == contrasena && u.getBloqueado() == false) {
				usuarioEnSesion = u;
				conectado = true;
				if (u.isAdmin() == true)
					adminConectado = true;
				mostrarNotificacion();
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
		if (reproducible instanceof Cancion) {
			cancionesValidar.add((Cancion) reproducible);
		}
		else if (reproducible instanceof Album)
			albunes.add((Album) reproducible);
		/**hwehbofhbvqhcdhbhSEGUIRRRRRRR///
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
			albunes.remove(reproducible);
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

	public ArrayList<Album> getAlbunes() {
		return albunes;
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
	public void bloquearUsuario(UsuarioRegistrado usuario, boolean permanente) {
		if (permanente = false) {
			usuario.setBloqueado(true);
			usuario.setFechaBloqueo(LocalDate.now());
		}
		else
			usuario.setBloqueoPermanente();
	}

	/**
	 * @param usuario
	 */
	public void desbloquearUsuario(UsuarioRegistrado usuario) {
		if (LocalDate.now().isAfter(usuario.getFechaBloqueo().plusDays(30))) {
			usuario.setBloqueado(false);
		}
		else if (adminConectado == true)
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
				System.out.println(n);
		}
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
	
	public void setCancionValidada(Cancion cancionValidada) {
		this.cancionesValidadas.add(cancionValidada);
	}
	
	/**
	 * Metodo que incrementa el numero de reproducciones
	 */
	public void incremetaReproducciones() {
		this.reproducciones++;
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
	
	public boolean esAdmin() {
		return adminConectado;
	}


	public UsuarioRegistrado getAdmin() {
		return admin;
	}


	public void setAdmin(UsuarioRegistrado admin) {
		this.admin = admin;
	}

}