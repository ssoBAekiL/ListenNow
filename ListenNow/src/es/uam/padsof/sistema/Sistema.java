package es.uam.padsof.sistema;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import es.uam.padsof.objetoreproducible.*;
import es.uam.padsof.usuario.*;
import java.io.*;

/**
 * Clase sistema (principal)
 */
/**
 * @author pablo
 *
 */
/**
 * @author pablo
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
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 * Funcion que se ejecuta al inicializar el sistema y carga tdos los datos guadrados en la sesion anterior.
	 * A continuacion borra las canciones que hayan sido rechazadas hace mas de 3 dias, desbloquea a los usuarios
	 * que llevan mas de 30 dias bloqueados y quita el premium a los usuarios que lo tengan desde mas de 30 dias
	 */
	public void inicializarSistema() throws ClassNotFoundException, IOException {
		readObject();
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
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * 
	 * Funcion que lee de un fichero datos guardados de la clase sistema y los carga en el sistema
	 */
	public void readObject() throws IOException, ClassNotFoundException {
		try {
		FileInputStream is = new FileInputStream("guardarSistema.dat");
		ObjectInputStream ois = new ObjectInputStream(is);
		Sistema s = (Sistema) ois.readObject();
		
		this.reproduccionesNoRegistrados = s.reproduccionesNoRegistrados;
		this.nRepAnonimas = s.nRepAnonimas;
		this.nRepRecompensa = s.nRepRecompensa;
		this.nRepRegistrado = s.nRepRegistrado;
		this.usuarios = s.usuarios;
		System.out.println(s.usuarios.get(0));
		this.cancionesNotificadas = s.cancionesNotificadas;
		this.cancionesRechazadas = s.cancionesRechazadas;
		this.cancionesValidadas = s.cancionesValidadas;
		this.cancionesValidar = s.cancionesValidar;
		this.albunes = s.albunes;
		this.notificaciones = s.notificaciones;		
		
		ois.close();
		is.close();
		}
		catch (IOException e) {e.printStackTrace();}
	}
	
	/**
	 * @throws FileNotFoundException
	 * @throws IOException
	 * 
	 * Funcion que guarda en un fichero todos los datos de la instancia actual de sistema
	 */
	public void guardarSistema() throws FileNotFoundException, IOException {
		try {
			FileOutputStream fos = new FileOutputStream("guardarSistema.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			// write object to file
			oos.writeObject(this);
			// closing resources
			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		 
	/**
	 * @param titulo de la cancion que se quiere buscar
	 * 
	 * Funcion que busca una cancion a partir de su titulo
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
	 * @param titulo del album que se quiere buscar
	 * 	
	 *  Funcion que busca un album a partir de su titulo 
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
	 * @param autor 
	 * 
	 * Funcion que busca un array de canciones a partir de su autor
	 * 
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
	 * @param usuario
	 * 
	 * Funcion que permite a un usuario registrarse anadiendolo al array de usuarios registrados.
	 */
	public void registrarse(UsuarioRegistrado usuario) {
		usuarios.add(usuario);
	}
	

	/**
	 * @param usuario 
	 * @param contrasena
	 * 
	 * Funcion que realiza el login comparando el nombre de usuario y la contrasena introducidos con el array
	 * de usuarios registrados
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
	 * @param reproducible
	 * @throws IOException
	 * 
	 * Funcion que anade un reproducible al sistema.
	 * Si se trata de una cancion la anade al array de canciones en espera de ser validadas y copia el archivo mp3 a la carpeta del programa
	 * Si se trata de un album lo anade al array de albunes
	 *
	 */
	public void anadirReproducible(ObjetoReproducible reproducible) throws IOException {
		if (reproducible instanceof Cancion && conectado == true) {
			cancionesValidar.add((Cancion) reproducible);
			//reproducible.copiarCancionASistema();
		}
		else if (reproducible instanceof Album && conectado == true)
			albunes.add((Album) reproducible);
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

			File fichero = new File(reproducible.getRuta());
				if(fichero.delete())
					return true;
			}
			else if (reproducible instanceof Album) {
				albunes.remove(reproducible);
				return true;
			}
		}
		return false;

	}
	
	/**
	 * @param usuario
	 * 
	 * Funcion que elimina a un usuario del sistema
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
				if(n.getUsuariosNotificados().contains(usuarioEnSesion))
					notificaciones.add(n);
			}
			return notificaciones;
		}
		return null;
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
	 * @param usuarios the usuarios to set
	 * 
	 * Funcion seter de usuarios que anade un nuevo usuario al array de usuarios
	 */
	public void setUsuarios(ArrayList<UsuarioRegistrado> usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * Funcion geter del numero maximo de reproducciones anonimas que es posible realizar
	 * 
	 * @return nRepAnonimas
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
	 * @param nRepAnonimas
	 * 
	 * Funcion seter del limite maximo de reproducciones anonimas que es posible realizar
	 * Esta funcion solo se ejecuta para el usuario admin
	 */
	public void setnRepAnonimas(int nRepAnonimas) {
		if(this.getUsuarioEnSesion().equals(this.admin))
			this.nRepAnonimas = nRepAnonimas;
	}

	/**
	 * @param nRepRegistrado
	 * 
	 * Funcion seter del limite maximo de reproducciones que un usuario registrado puede realizar
	 * Esta funcion solo se ejecuta para el usuario admin
	 */
	public void setnRepRegistrado(int nRepRegistrado) {
		if(this.getUsuarioEnSesion().equals(this.admin))
			this.nRepRegistrado = nRepRegistrado;
	}

	/**
	 * @param nRepRecompensa
	 * 
	 * Funcion seter del tope minimo de reproducciones que hay que recibir para recibir la recompensa de premium
	 * Esta funcion solo se ejecuta para el usuario admin
	 */
	public void setnRepRecompensa(int nRepRecompensa) {
		if(this.getUsuarioEnSesion().equals(this.admin))
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
	 * @param i
	 * 
	 * Funcion geter que devuelve un usuario registrado dentro del Sistema de la aplicacion
	 * 
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
	 * Funcion seter que anade el UsuarioRegistrado u al array de usuarios
	 */
	public void addUsuario(UsuarioRegistrado u) {
		this.usuarios.add(u);
	}

	/**
	 * Funcion geter del array canciones por validar
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