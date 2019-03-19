package es.uam.padsof.usuario;
import java.time.LocalDate;
import java.util.*;

import es.uam.padsof.objetocomentado.Comentario;
import es.uam.padsof.objetoreproducible.*;
import es.uam.padsof.sistema.*;

/**
 * Esta clase proporciona funcionalidades referentes a un usuario al igual que sus caracter�sticas principales
 * @author Carlos Miret, Pablo Borrelli y Julian Espada
 *
 */
/**
 * @author carlosmiret
 *
 */
public class UsuarioRegistrado {

	//constructor de Usuario comun
	public UsuarioRegistrado(String nombre, String contrasena, boolean esPremium, LocalDate fechaPremium,int reproducciones, boolean bloqueado, boolean isAdmin) {
		this.nombre=nombre;
		this.contrasena=contrasena;
		this.esPremium=esPremium;
		this.fechaPremium=fechaPremium;
		this.seguidos=null;      					//todo se guarda en archivo
		this.seguidores=null;
		this.canciones=null;
		this.albunes=null;
		this.lista_reproducciones=null;
		this.reproducciones=reproducciones;
		this.bloqueado=bloqueado;
		this.isAdmin=false;
	}
	
	//constructor de Usuario admin
	/**
	 * CONSTRUCTOR DE USUARIO ADMIN
	 * @param nombre
	 * @param contrasena
	 * @param esPremium
	 * @param fechaPremium
	 * @param reproducciones
	 * @param bloqueado
	 */
	public UsuarioRegistrado(String nombre, String contrasena, boolean esPremium, LocalDate fechaPremium,int reproducciones, boolean bloqueado) {
		this.nombre=nombre;
		this.contrasena=contrasena;
		this.esPremium=esPremium;
		this.fechaPremium=fechaPremium;
		this.seguidos=null;      					//todo se guarda en archivo
		this.seguidores=null;
		this.canciones=null;
		this.albunes=null;
		this.lista_reproducciones=null;
		this.reproducciones=reproducciones;
		this.bloqueado=bloqueado;
		this.isAdmin=true;
	}


	/**
	 * Nombre
	 */
	private String nombre;

	/**
	 * Contrasena
	 */
	private String contrasena;

	/**
	 * esPremium
	 */
	private boolean esPremium;

	/**
	 * fechaPremium
	 */
	private LocalDate fechaPremium;

	/**
	 * seguidos
	 */
	private ArrayList<UsuarioRegistrado> seguidos;
	
	
	/**
	 * ADMIN
	 */
	private boolean isAdmin;

	/**
	 * seguidores
	 */
	private ArrayList<UsuarioRegistrado> seguidores;
	
	/**
	 * canciones
	 */
	private ArrayList<Cancion> canciones;
	
	/**
	 * @return the isAdmin
	 */
	public boolean isAdmin() {
		return isAdmin;
	}

	/**
	 * albunes
	 */
	private ArrayList<Album> albunes;
	
	/**
	 * lista de reproducciones
	 */
	private ArrayList<ListaReproducciones> lista_reproducciones;

	/**
	 * reproducciones
	 */
	private int reproducciones;

	/**
	 * bloqueado
	 */
	private boolean bloqueado;



	/*************************************************************************************************/
	/*************************************************************************************************/
	/*************************************************************************************************/
	/**
	 * Funcion getter NOMBRRE
	 * @return nombre del usuario
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Funcion setter NOMBRRE
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Funcion getter CONTRASENA
	 * @return contrasena del usuario
	 */
	public String getContrasena() {
		return contrasena;
	}

	/**
	 * Funcion setter CONTRASENA
	 * @param contrasena
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	/**
	 * Funcion getter ESPREMIUM
	 * @return true en el caso de que el usuario sea Premium
	 */
	public boolean EsPremium() {
		return esPremium;
	}

	/**
	 * Funcion setter ESPREMIUM
	 * @param esPremium
	 */
	public void setEsPremium(boolean esPremium) {
		this.esPremium = esPremium;
	}

	/**
	 * Funcion getter FECHAPREMIUM
	 * @return fecha en la que el usuario contrato la opcion premium
	 */
	public LocalDate getFechaPremium() {
		return fechaPremium;
	}

	/**
	 * Funcion setter FECHAPREMIUM
	 * @param fechaPremium
	 */
	public void setFechaPremium(LocalDate fechaPremium) {
		this.fechaPremium = fechaPremium;
	}

	/**
	 * Funcion getter SEGUIDOS
	 * @return seguidos del usuario
	 */
	public ArrayList<UsuarioRegistrado> getSeguidos() {
		return seguidos;
	}

	/**
	 * Funcion setter SEGUIDOS
	 * @param seguidos
	 */
	public void setSeguidos(ArrayList<UsuarioRegistrado> seguidos) {
		this.seguidos = seguidos;
	}

	/**
	 * Funcion getter SEGUIDORES
	 * @return seguidores del usuario
	 */
	public ArrayList<UsuarioRegistrado> getSeguidores() {
		return seguidores;
	}


	/**
	 * Funcion getter REPRODUCCIONES
	 * @return numero de reproducciones de un usuario
	 */
	public int getReproducciones() {
		return reproducciones;
	}
	

	/**
	 * Funcion getter BLOQUEADO
	 * @return true en el caso de que el usuario este bloquedo
	 */
	public boolean isBloqueado() {
		return bloqueado;
	}

	/**
	 * Funcion setter BLOQUEADO
	 * @param bloqueado
	 */
	public void setBloqueado(boolean nuevo_estado) {
		this.bloqueado = nuevo_estado;
	}
	
	
	/**
	 * Funcion getter CANCIONES
	 * @return las canciones que tenga el usuario en un momento determinado
	 */
	public ArrayList<Cancion> getCanciones() {
		return canciones;
	}


	/**
	 * Funcion getter ALBUNES
	 * @return los albunes que tenga el usuario en un momento determinado
	 */
	public ArrayList<Album> getAlbunes() {
		return albunes;
	}

	/**
	 * Funcion getter LISTAREPRODUCCIONES
	 * @return las listas de reproduccion que tenga el usuario en un momento determinado
	 */
	public ArrayList<ListaReproducciones> getLista_reproducciones() {
		return lista_reproducciones;
	}

	
	/*************************************************************************************************/
	/*************************************************************************************************/
	/*************************************************************************************************/


	
	
	
	/*************************************************************************************************/
	/*************************************************************************************************/
	/*************************************************************************************************/


	/**
	 * 
	 * Metodo cuya funcionaidad es contratar premium
	 */
	public void contratarPremium() {
		    this.fechaPremium=LocalDate.now();
			this.esPremium=true;
	}


	/**
	 * Metodo que da la capacidad al usuario de anadir un comentario a una cancion
	 * @param cancion
	 * @param comentario tipo comentario
	 */
	public void anadirComentarioAcancion(Cancion cancion, Comentario comentario) {
		cancion.getComentarios().add(comentario);
	}
	
	/**
	 * Metodo que da la capacidad al usuario de anadir un comentario a una cancion
	 * @param cancion
	 * @param comentario
	 */
	public void anadirComentarioAalbum(Album album, Comentario comentario) {
		album.getComentarios().add(comentario);
	}

	/**
	 * Metodo que permite a un usuario valorar un comentario
	 * @param valoracion
	 */
	public void valorarComentario(Comentario comentario, int valoracion) {
		comentario.setValoracion(valoracion);
	}

	/**
	 * Metodo que permite a un usuario notificar ccomo plagio una cancion
	 * @param cancion
	 */
	public void notificarPlagio(Cancion cancion) {
		cancion.setNotificada(true);
	}

	/**
	 * Metodo que permite al usuario crear un Album y por consiguiente almacenarlo en el conjunto de diferentes albunes de dicho usuario
	 * @param album
	 */
	public void anadirAlbum(Album album) {
		this.albunes.add(album);
	}

	/**
	 * Metodo que da la capacidad a un usuario a borrar un album
	 * @param album
	 */
	public void borrarAlbum(Album album) {
		if(this.isAdmin==true)
			Sistema.sistema.borrarReproducible(album);
	}


	/**
	 * Metodo que se encarga de anadir una cancion al conjunto de canciones del usuario
	 * @param cancion
	 */
	public void anadirCancion(Cancion cancion) {
		canciones.add(cancion);
	}

	/**
	 * Metodo que se encarga de anadir una cancion al conjunto de canciones del usuario
	 * @param cancion
	 */
	public void borrarCancion(Cancion cancion) {
		if(this.isAdmin==true)
			Sistema.getInstance().borrarReproducible(cancion); //sistema.borrarReproducible(cancion);????????preguntamosssss
	}
	
	
	/**
	 * Metodo que da la capacidad a un usuario de crear una lista de reproducciones propia
	 * @param album
	 */
	public void anadirListaReproduccion(ListaReproducciones lista) {
		this.lista_reproducciones.add(lista);
	}

	/**
	 * Metodo que da la capacidad a un usuario de crear una lista de reproducciones propia
	 * @param album
	 */
	public void borrarListaReproduccion(ListaReproducciones lista) {
		Sistema.getInstance().borrarReproducible(lista); //sistema.borrarReproducible(cancion);????????preguntamosssss
	}


}
