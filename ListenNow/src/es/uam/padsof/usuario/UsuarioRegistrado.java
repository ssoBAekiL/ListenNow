package es.uam.padsof.usuario;
import java.util.*;

import es.uam.padsof.sistema.Sistema;


/**
 * Esta clase proporciona funcionalidades referentes a un usuario al igual que sus caracter�sticas principales
 * @author Carlos Miret, Pablo Borrelli y Julian Espada
 *
 */
public class UsuarioRegistrado {

    /************sistema se pone aqui??????, como accedo a sistema siendo el admin**************/
	/**
	 * Metodo constructor de la clase UsuarioRegistrado. Es importante destacar que al construir un objeto 
	 * UsuarioRegistrado, este no tendr� seguidores ni seguidos, por lo que no se incluyen en los par�metros de esta funcion,
	 * sino que ser,a inicializados a null en el cuerpo de este constructor
	 * A�ADIR GENERATE ELEMENT COMMENT EN ESTE CONSTRUCTOR
	 */

	public UsuarioRegistrado(String nombre, String contrasena, boolean esPremium, Date fechaPremium,int reproducciones, boolean bloqueado) {
		this.nombre=nombre;
		this.contrasena=contrasena;
		this.esPremium=esPremium;
		this.fechaPremium=fechaPremium;
		this.seguidos=null;      					//todo se guarda en archivo
		this.seguidores=null;
		this.canciones=null;
		this.albunes=null;
		this.lista_reproducciones=null;//preguntarrrrrrrrrrr
		this.reproducciones=reproducciones;
		this.bloqueado=bloqueado;
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
	private Date fechaPremium;

	/**
	 * seguidos
	 */
	private ArrayList<UsuarioRegistrado> seguidos;

	/**
	 * seguidores
	 */
	private ArrayList<UsuarioRegistrado> seguidores;
	
	/**
	 * canciones
	 */
	private ArrayList<Cancion> canciones;
	
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
	public Date getFechaPremium() {
		return fechaPremium;
	}

	/**
	 * Funcion setter FECHAPREMIUM
	 * @param fechaPremium
	 */
	public void setFechaPremium(Date fechaPremium) {
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
	 * Metodo cuya funcionaidad es contratar premium
	 */
	public void contratarPremium() {
		    Calendar c = Calendar.getInstance();
		    this.fechaPremium=c.getTime();
			this.esPremium=true;
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
		Sistema.sistema.anadirReproducible(reproducible); //sistema.borrarReproducible(cancion);????????preguntamosssss
	}
	


	/**
	 * Metodo que da la capacidad al usuario de anadir un comentario a una cancion
	 * @param cancion
	 * @param comentario
	 */
	public void anadirComentarioAcancion(Cancion cancion, String comentario) {
		
	}
	
	/**
	 * Metodo que da la capacidad al usuario de anadir un comentario a una cancion
	 * @param cancion
	 * @param comentario
	 */
	public void anadirComentarioAalbum(Album album, String comentario) {
		// TODO implement here
	}

	/**
	 * Metodo que permite a un usuario valorar un comentario
	 * @param valoracion
	 */
	public void valorarComentario(int valoracion) {
		// TODO implement here
	}

	/**
	 * Metodo que permite a un usuario notificar ccomo plagio una cancion
	 * @param cancion
	 */
	public void notificarPlagio(Cancion cancion) {
		// TODO implement here
	}

	/**
	 * Metodo que permite al usuario crear un Album y por consiguiente almacenarlo en el conjunto de diferentes albunes de dicho usuario
	 * @param album
	 */
	public void crearAlbum(Album album) {
		albunes.add(album);
	}

	/**
	 * Metodo que da la capacidad a un usuario a borrar un album
	 * @param album
	 */
	public void borrarAlbum(Album album) {
		// TODO implement here
		sistema.borrarReproducible(album);
	}

	/**
	 * Metodo que da la capacidad a un usuario de crear una lista de reproducciones propia
	 * @param album
	 */
	public void crearListaReproduccion(ListaReproducciones lista) {
		// TODO implement here
	}

	/**
	 * Metodo que da la capacidad a un usuario de crear una lista de reproducciones propia
	 * @param album
	 */
	public void borrarListaReproduccion(ListaReproducciones lista) {
		// TODO implement here
	}
	
	/*************************************************************************************************/
	/*************************************************************************************************/
	/*************************************************************************************************/
	/******************************METODOS PROPIOS DEL ADMINISTRADOR**********************************/
	/*************************************************************************************************/
	/*************************************************************************************************/
	/*************************************************************************************************/
	
	/**
	 * Metodo que permite al usuario admin validar una cancion
	 * @param cancion
	 */
	public void validarCancion(Cancion cancion) {
		// TODO implement here
	}

	/**
	 * Metodo que permite al usuario admin validar + 18 una cancion pasada por parametro
	 * @param cancion
	 */
	public void validarCancion18(Cancion cancion) {
		// TODO implement here
	}

	/**
	 * Metodo que permite al usuario admin verificar un plagio
	 * @param cancion
	 */
	public void verificarPlagio(Cancion cancion) {
		// TODO implement here
	}

	/**
	 * Metodo que permite al usuario admin rechazar una cancion
	 * @param cancion
	 */
	public void rechazarCancion(Cancion cancion) {
		// TODO implement here
	}

	/**
	 * Metodo que permite al usuario admin modificar el numero de reproducciones anonimas almacenadas en sistema
	 * @param limite
	 */
	public void modificarNRepAnonimas(int limite) {
			sistema.setNRepAnonimas(limite);
	}

	/**
	 * Metodo que permite al usuario admin modificar el numero de reproducciones premium almacenadas en sistema
	 * @param limite
	 */
	public void modificarNRepPremium(int limite) {
			sistema.setNRepPremium(limite);//�??????????????????????????????????????????????????????????????????????????????????????????????????
	}

	/**
	 * Metodo que permite al usuario admin modificar el numero de reproducciones recompensa almacenadas en sistema
	 * @param limite
	 */
	public void modificarNRepRecompensa(int limite) {
			sistema.setNRepRecompensa(limite);
	}

}
