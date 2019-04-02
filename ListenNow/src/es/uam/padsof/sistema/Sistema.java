package es.uam.padsof.sistema;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import es.uam.padsof.objetoreproducible.*;
import es.uam.padsof.usuario.*;
import java.io.*;


/**
 * Clase Sistema, que posee todos las caracteristicas propias 
 * de un sistema (principal clase del proyecto) al igual que funciones que trabajan sobre este
 * @author Carlos Miret, Pablo Borrelli y Julian Espada
 *
 */
public class Sistema implements Serializable {
	/**
	 * Identificador serial utilizado para serializar la clase
	 */
	private static final long serialVersionUID = 1L;


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
	 * Lista de listas de reproducciones en el sistema
	 */
	private ArrayList<ListaReproducciones> listas;
	
	/**
	 * Lista de albunes en el sistema
	 */
	private ArrayList<Album> albunes;
	
	/**
	 * Lista de canciones que han sido validadas por el administrador
	 */
	
	private ArrayList<Cancion> cancionesValidadas;

	/**
	 * Lista de tadas las notificaciones del sistema
	 */
	private ArrayList<Notificacion> notificaciones;
	
	
	/**
	 * Canciones notificadas como plagio
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
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	private Sistema() {
		if (new File("datosSistema.dat").exists()) {
			try {
				inicializarSistema();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
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
		this.listas = new ArrayList<ListaReproducciones>();
		this.admin=(new UsuarioRegistrado("ADMIN"/*nombre*/, "soyadmin"/*contrasena*/, true/*premium*/, true));
		this.admin.setFechaPremium(LocalDate.now());
		this.addUsuario(admin);
		}
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
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 * Funcion que se ejecuta al inicializar el sistema y carga todos los datos guardados en la sesion anterior.
	 * A continuacion borra las canciones que hayan sido rechazadas hace mas de 3 dias, desbloquea a los usuarios
	 * que llevan mas de 30 dias bloqueados y quita el premium a los usuarios que lo tengan desde mas de 30 dias
	 */
	public void inicializarSistema() throws ClassNotFoundException, IOException {
		leerSistema();
		for (Cancion c: cancionesRechazadas) {
			c.borradoTrasTercerDia();
		}
		for (UsuarioRegistrado u: usuarios) {
			if (u.getBloqueado() == true && u.getBloqueoPermanente() == false)
				u.desbloquearUsuario();
			if(u.EsPremium() == true)
				u.caducaPremium();
		}
	}
	
	/**
	 * Funcion que lee de un fichero datos guardados de la clase sistema y los carga en el sistema
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void leerSistema() throws IOException, ClassNotFoundException {
		try {
		FileInputStream is = new FileInputStream("datosSistema.dat");
		ObjectInputStream ois = new ObjectInputStream(is);
		Sistema s = (Sistema) ois.readObject();
		
		this.reproduccionesNoRegistrados = s.reproduccionesNoRegistrados;
		this.nRepAnonimas = s.nRepAnonimas;
		this.nRepRecompensa = s.nRepRecompensa;
		this.nRepRegistrado = s.nRepRegistrado;
		this.usuarios = s.usuarios;
		this.listas = s.listas;
		this.cancionesNotificadas = s.cancionesNotificadas;
		this.cancionesRechazadas = s.cancionesRechazadas;
		this.cancionesValidadas = s.cancionesValidadas;
		this.cancionesValidar = s.cancionesValidar;
		this.albunes = s.albunes;
		this.notificaciones = s.notificaciones;
		this.conectado = false;
		this.admin=(new UsuarioRegistrado("ADMIN"/*nombre*/, "soyadmin"/*contrasena*/, true/*premium*/, true));
		this.admin.setFechaPremium(LocalDate.now());
		
		ois.close();
		is.close();
		}
		catch (IOException e) {e.printStackTrace();}
	}
	
	/**
	 * Funcion que se encarga de cerrar la aplicacion/sistema y que a su vez guarda los datos de
	 * clase principal
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void cerrarSistema() throws FileNotFoundException, IOException {
		if (this.conectado == true)
			logout();
		guardarSistema();
	}
	
	/**
	 * @throws FileNotFoundException
	 * @throws IOException
	 * 
	 * Funcion que guarda en un fichero todos los datos de la instancia actual de sistema
	 */
	public void guardarSistema() throws FileNotFoundException, IOException {
		try {
			FileOutputStream fos = new FileOutputStream("datosSistema.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		 
	/**
	 * Funcion que busca una cancion a partir de su titulo
	 * @param titulo de la cancion que se quiere buscar
	 * 
	 * @return cancion buscada si se encuentra en la lista, null si no se encuentra
	 */
	public Cancion buscarCancion(String titulo) {
		for (Cancion c: cancionesValidadas) {
			if (c.getTitulo().equals(titulo))
				return c;
		}
		return null;
	}

	/**
	 * Funcion que busca un album a partir de su titulo 
	 * @param titulo del album que se quiere buscar
	 *  
	 * @return album buscado si se encuentra en la lista, null si no se encuentra
	 */
	public Album buscarAlbum(String titulo) {
		for (Album a: albunes) {
			if (a.getTitulo().equals(titulo))
				return a;
		}
		return null;
	}
	
	
	/**
	 * Funcion que busca un array de canciones a partir de su autor
	 * @param author autor al que se quiere buscar
	 * @return array de canciones si el autor tiene alguna, null si no
	 */
	public ArrayList<Cancion> buscarAutor(String autor) {
		ArrayList<Cancion> cancionesAutor = new ArrayList<Cancion>();
		for (Cancion c: cancionesValidadas)
			if (c.getAutor().getNombre().equals(autor)) {
				cancionesAutor.add(c);
				return cancionesAutor;
			}
		return null;
	}


	/**
	 * Funcion que permite a un usuario registrarse anadiendolo al array de usuarios registrados.
	 * @param usuario Usuario que se registra
	 * 
	 */
	public boolean registrarse(UsuarioRegistrado usuario) {
		if (this.usuarios.contains(usuario) == false) { // control no puede registrarse un usuario si ya existe uno con su mismo nombre
			usuarios.add(usuario);
			return true;
		}
		return false;
	}
	

	/**
	 * Funcion que realiza el login comparando el nombre de usuario y la contrasena introducidos con el array
	 * de usuarios registrados	 * @param usuario 
	 * @param contrasena
	 * 
	 * 
	 * @return true en caso de exito, false si no
	 */
	public boolean login(String usuario, String contrasena) {
		if (conectado == false) {
			for (UsuarioRegistrado u: usuarios) {
				if (u.getNombre().equals(usuario) && u.getContrasena().equals(contrasena) && u.getBloqueado() == false) {
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
	 * Funcion que desconecta a un usuario registrado
	 */
	public void logout() {
		usuarioEnSesion = null;
		conectado = false;
	}

	/**
	 * Funcion que anade un reproducible al sistema.
	 * Si se trata de una cancion la anade al array de canciones en espera de ser validadas y copia el archivo mp3 a la carpeta del programa
	 * Si se trata de un album lo anade al array de albunes	 * @param reproducible
	 * 
	 * @throws IOException
	 */
	public boolean anadirReproducible(ObjetoReproducible reproducible) throws IOException {
		if (reproducible instanceof Cancion && conectado == true) {
			cancionesValidar.add((Cancion) reproducible);
			return true;
		}
		else if (reproducible instanceof Album && conectado == true) {
			albunes.add((Album) reproducible);
			usuarioEnSesion.anadirAlbum((Album) reproducible);
			return true;
		}
		else if (reproducible instanceof ListaReproducciones && conectado == true) {
			listas.add((ListaReproducciones) reproducible);
			usuarioEnSesion.anadirListaReproduccion((ListaReproducciones) reproducible);
			return true;
		}
		return false;
	}

	/**
	 * @param reproducible
	 * 
	 * Funcion que borra un reproducible del sistema.
	 * Si se trata de una cancion la busca en cada array de canciones y si la encuentra la borra y a continuacion borra el archivo mp3
	 * Si se trata de un album lo borra del array de albunes
	 * 
	 * @return true en caso de exito, false si no
	 */
	public boolean borrarReproducible(ObjetoReproducible reproducible ) {
		if(conectado == true && (usuarioEnSesion.getNombre().equals(reproducible.getAutor().getNombre()) || usuarioEnSesion.getNombre().equals(admin.getNombre()))) {
			if (reproducible instanceof Cancion) {
				if (cancionesValidadas.contains(reproducible))
					cancionesValidadas.remove(reproducible);
				if (cancionesValidar.contains(reproducible))
					cancionesValidar.remove(reproducible);
				if(cancionesNotificadas.contains(reproducible))
					cancionesNotificadas.remove(reproducible);
				if(cancionesRechazadas.contains(reproducible)) 
					cancionesRechazadas.remove(reproducible);
				usuarioEnSesion.getCanciones().remove(reproducible);

			File fichero = new File(reproducible.getRuta());
				if(fichero.delete())
					return true;
			}
			else if (reproducible instanceof Album) {
				albunes.remove(reproducible);
				usuarioEnSesion.getAlbunes().remove(reproducible);
				return true;
			}
			else if (reproducible instanceof ListaReproducciones) {
				listas.remove(reproducible);
				usuarioEnSesion.getLista_reproducciones().remove(reproducible);
				return true;
			}
		}
		return false;

	}
	
	/**
	 * Funcion que elimina a un usuario del sistema
	 * @param usuario
	 * 
	 */
	public void darDeBaja(UsuarioRegistrado usuario) {
		usuarios.remove(usuario);
	}

	/**
	 * Funcion que recorre las notificaciones y recoge en un array las que van dirigidas al usuario que se encuentra en sesion
	 * 
	 * @return array con las notificaciones para el usuario en sesion, null si no existe ninguna
	 */
	public ArrayList<Notificacion> mostrarNotificacion() {
		if(conectado == true) {
			ArrayList<Notificacion> notificaciones = new ArrayList<Notificacion>();
			for (Notificacion n: this.notificaciones) {
				for (UsuarioRegistrado u: n.getUsuariosNotificados())
					if (this.usuarioEnSesion.getNombre().equals(u.getNombre()))
						notificaciones.add(n);
			}
			return notificaciones;
		}
		return null;
	}

	/**
	 * Funcion que reocge todos los datos caracteristicos de Sistema y lo devuelve en forma de String
	 * @see java.lang.Object#toString()
	 * @return String con los datos caracteristicos
	 */
	@Override
	public String toString() {
		return "Sistema [reproduccionesNoRegistrados=" + reproduccionesNoRegistrados + "	\nRepAnonimas=" + nRepAnonimas
				+ "		\nRepRegistrado=" + nRepRegistrado + "		\nRepRecompensa=" + nRepRecompensa + "	\nusuarios=" + usuarios
				+ "	\ncancionesValidar=" + cancionesValidar + "	\ncancionesRechazadas=" + cancionesRechazadas
				+ "	\nlistas=" + listas + ", albunes=" + albunes + "	\ncancionesValidadas=" + cancionesValidadas
				+ " \ncancionesNotificadas=" + cancionesNotificadas+"]";
	}


	/**
	 * Funcion geter del array de usuarios
	 * 
	 * @return usuarios
	 */
	public ArrayList<UsuarioRegistrado> getUsuarios() {
		return usuarios;
	}

	/**
	 * @param usuarios Funcion setter del array de usuarios
	 * 
	 * Funcion seter de usuarios que anade un nuevo usuario al array de usuarios
	 */
	public void setUsuarios(ArrayList<UsuarioRegistrado> usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * Funcion getter del numero maximo de reproducciones anonimas que es posible realizar
	 * 
	 * @return nRepAnonima
	 */
	public int getnRepAnonimas() {
		return nRepAnonimas;
	}

	/**
	 * Funcion geter del numero maximo de reproducciones que un usuario registrado puede realizar
	 * 
	 * @return nRepRegistrado
	 */
	public int getnRepRegistrado() {
		return nRepRegistrado;
	}
	
	/**
	 * Funcion geter del numero minimo de reproducciones que hay que recibir para recibir la recompensa de premium
	 * 
	 * @return nRepRecompensa
	 */
	public int getnRepRecompensa() {
		return nRepRecompensa;
	}
	
	
	/**
	 * Funcion geter de generador
	 * 
	 * @return generador
	 */
	public AtomicLong getGenerador() {
		return generador;
	}
	
	/**
	 * Funcion seter del limite maximo de reproducciones anonimas que es posible realizar
	 * Esta funcion solo se ejecuta para el usuario admin
	 * @param nRepAnonimas
	 * 
	 */
	public void setnRepAnonimas(int nRepAnonimas) {
		if(this.getUsuarioEnSesion().getNombre().equals(this.admin.getNombre()))
			this.nRepAnonimas = nRepAnonimas;
	}

	/**
	 * Funcion seter del limite maximo de reproducciones que un usuario registrado puede realizar
	 * Esta funcion solo se ejecuta para el usuario admin
	 * @param nRepRegistrado
	 * 
	 */
	public void setnRepRegistrado(int nRepRegistrado) {
		if(this.getUsuarioEnSesion().getNombre().equals(this.admin.getNombre()))
			this.nRepRegistrado = nRepRegistrado;
	}

	/**
	 * Funcion setter del tope minimo de reproducciones que hay que recibir para recibir la recompensa de premium
	 * Esta funcion solo se ejecuta para el usuario admin
	 * @param nRepRecompensa
	 * 
	 */
	public void setnRepRecompensa(int nRepRecompensa) {
		if(this.getUsuarioEnSesion().getNombre().equals(this.admin.getNombre()))
			this.nRepRecompensa = nRepRecompensa;
	}


	/**
	 * Funcion geter del array de canciones que ya han sido validadas
	 * 
	 * @return the cancionesValidadas
	 */
	public ArrayList<Cancion> getCancionesValidadas() {
		return cancionesValidadas;
	}

	/**
	 * @param cancionesValidadas
	 * 
	 * Funcion seter del array cancionesValidadas
	 */
	public void setCancionesValidadas(ArrayList<Cancion> cancionesValidadas) {
		this.cancionesValidadas = cancionesValidadas;
	}
	
	/**
	 * @param cancionValidada
	 * 
	 * Funcion seter que anade una cancion al array cancionesValidadas
	 */
	public void setCancionValidada(Cancion cancionValidada) {
		this.cancionesValidadas.add(cancionValidada);
	}
	
	/**
	 * Metodo que incrementa el numero de reproducciones realizadas por usuarios no registrados
	 */
	public void incremetareproduccionesNoRegistrados() {
		this.reproduccionesNoRegistrados++;
	}
	
	
	/**
	 * Funcion geter del array de canciones que han sido notificadas como plagio
	 * 
	 * @return cancionesNotificadas
	 */
	public ArrayList<Cancion> getCancionesNotificadas() {
		return cancionesNotificadas;
	}


	/**
	 * Funcion getter que devuelve un usuario registrado dentro del Sistema de la aplicacion
	 * @param i iterador
	 * @return el UsuarioRegistrado del array usuarios que se encuentra en la posicion i
	 */
	public UsuarioRegistrado getUsuarioItera(int i) {
		return usuarios.get(i);
	}
	
	/**
	 * @param i
	 * 
	 * Funcion geter que devuelve una cancion dentro del Sistema de la aplicacion
	 * 
	 * @return el UsuarioRegistrado del array usuarios que se encuentra en la posicion i 
	 */
	public Cancion getCancionItera(int i) {
		return this.cancionesValidadas.get(i);
	}
	
	

	/**
	 * Funcion geter del array de canciones rechazadas
	 * 
	 * @return cancionesRechazadas
	 */
	public ArrayList<Cancion> getCancionesRechazadas() {
		return cancionesRechazadas;
	}


	/**
	 * Funcion geter de la dimension del array usuarios
	 * 
	 * @return numero de elementos en el array usuarios
	 */
	public int getNumUsuarios() {
		return this.usuarios.size();
	}

	/**
	 * @param u
	 * 
	 * Funcion setter que anade el UsuarioRegistrado u al array de usuarios
	 */
	public void addUsuario(UsuarioRegistrado u) {
		this.usuarios.add(u);
	}

	/**
	 * Funcion getter del array canciones por validar
	 * 
	 * @return cancionesValidar
	 */
	public ArrayList<Cancion> getCancionesValidar() {
		return cancionesValidar;
	}
	
	/**
	 * @param notificacion
	 * 
	 * Funcion seter que anade la notificacion notificacion al array de notificaciones
	 */
	public void setNotificaciones(Notificacion notificacion) {
		notificaciones.add(notificacion);
	}
	
	/**
	 * Funcion geter de la dimension del array cancionesValidadas
	 * 
	 * @return numero de elementos en el array cancionesValidadas
	 */
	public int getNumeroCanciones() {
		return cancionesValidadas.size();
	}

	/**
	 * Funcion geter del UsuarioRegistrado admin
	 * 
	 * @return admin
	 */
	public UsuarioRegistrado getAdmin() {
		return admin;
	}
	
	/**
	 * @param album
	 * 
	 * Funcion seter del array de albunes
	 */
	public void setAlbum(Album album) {
		albunes.add(album);
	}
	
	/**
	 * Funcion geter del array de notificaciones del sistema
	 * 
	 * @return the notificaciones
	 */
	public ArrayList<Notificacion> getNotificaciones() {
		return notificaciones;
	}


	/**
	 * Funcion geter del array de albunes
	 * 
	 * @return albunes
	 */
	public ArrayList<Album> getAlbunes() {
		return albunes;
	}
	
	/**
	 * Funcion geter del array de listas de reproducciones
	 * 
	 * @return listas
	 */
	public ArrayList<ListaReproducciones> getListas() {
		return listas;
	}


	//**************************************************
	
	
	/**
	 * Funcion geter de UsuarioEnSesion
	 * 
	 * @return usuarioEnSesion
	 */
	public UsuarioRegistrado getUsuarioEnSesion() {
		return usuarioEnSesion;
	}
	

	/**
	 * @param usuarioEnSesion
	 * 
	 * Funcion seter de UsuarioEnSesion
	 */
	public void setUsuarioEnSesion(UsuarioRegistrado usuarioEnSesion) {
		this.usuarioEnSesion = usuarioEnSesion;
	}


	/**
	 * Funcion geter del boolean conectado
	 * 
	 * @return conectado
	 */
	public boolean getConectado() {
		return conectado;
	}
	
	/**
	 * Funcion geter de reproduccionesNoRegistrados
	 * 
	 * @return reproduccionesNoRegistrados
	 */
	public int getReproduccionesNoRegistrados() {
		return reproduccionesNoRegistrados;
	}
	
	/**
	 * Funcion que realiza un reset de todos los datos contenidos en el sistema
	 */
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