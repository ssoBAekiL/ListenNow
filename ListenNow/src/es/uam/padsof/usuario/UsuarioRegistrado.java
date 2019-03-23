package es.uam.padsof.usuario;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import es.uam.eps.padsof.telecard.*;

import java.util.*;

import es.uam.padsof.objetoreproducible.*;
import es.uam.padsof.sistema.*;

/**
 * Esta clase proporciona funcionalidades referentes a un usuario al igual que sus características principales
 * @author Carlos Miret, Pablo Borrelli y Julian Espada
 *
 */

public class UsuarioRegistrado {

	/**
	 * Metodo constructor de UsuarioRegistrado
	 * @param numTarjeta
	 * @param nombre
	 * @param contrasena
	 * @param esPremium
	 * @param isAdmin
	 */
	public UsuarioRegistrado(String numTarjeta, String nombre, String contrasena, boolean esPremium,
			boolean isAdmin) {
		this.seguidos = new ArrayList<UsuarioRegistrado>();
		this.seguidores = new ArrayList<UsuarioRegistrado>();;
		this.lista_reproducciones = new ArrayList<ListaReproducciones>();
		this.canciones = new ArrayList<Cancion>();
		this.albunes = new ArrayList<Album>();
		this.saldo=100;
		this.numTarjeta = numTarjeta;
		this.nombre = nombre;
		this.contrasena = contrasena;
		this.esPremium = esPremium;
		this.fechaPremium = null;
		this.isAdmin = isAdmin;
		this.reproducciones = 0;
		this.bloqueado = false;
		this.bloqueoPermanente = false;
		this.fechaBloqueo = null;
	}
	
	
    /**
     * Saldo inicial del usuario, para poder contratar el paquete premium
     */
    private double saldo;
    
	/**
	 * Numero de tarjeta del usuario
	 */
	private String numTarjeta;
	
	/**
	 * Nombre del usuario
	 */
	private String nombre;

	/**
	 * Contrasena del usuario
	 */
	private String contrasena;

	/**
	 * Atributo que permite identificar el estado de suscripción del usuario
	 */
	private boolean esPremium;

	/**
	 * Fecha en la que se ha contratado el paquete premium
	 */
	private LocalDate fechaPremium;

	/**
	 * Array de seguidos que posee el usuario 
	 */
	private ArrayList<UsuarioRegistrado> seguidos;
	
	
	/**
	 * ADMIN
	 */
	private boolean isAdmin;

	/**
	 * Array de seguidores que posee el usuario 
	 */
	private ArrayList<UsuarioRegistrado> seguidores;
	
	/**
	 * Array de canciones del usuario
	 */
	private ArrayList<Cancion> canciones;
	
	/**
	 * Array de albunes del usuario
	 */
	private ArrayList<Album> albunes;
	
	/**
	 * Array de lista de reproducciones del usuario
	 */
	private ArrayList<ListaReproducciones> lista_reproducciones;

	/**
	 * Reproducciones que lleva un usuario determinado
	 */
	private int reproducciones;

	/**
	 * bloqueado
	 */
	private boolean bloqueado;
	
	private boolean bloqueoPermanente;
	
	private LocalDate fechaBloqueo;
	
	/*******************************GETTERS Y SETTERS**************************/
	
	/**
	 * Metodo que permite distinguir si el usuario es ADMIn o no
	 * @return the isAdmin
	 */
	public boolean isAdmin() {
		return isAdmin;
	}





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
	 * Metodo que se ocupa de incrementar las reproducciones del usuario
	 */
	public void incrementaReproducciones() {
		this.reproducciones++;
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
	 * Metodo cuya funcionaidad es contratar premium, y posteriormente lo imprime en un fichero de texto
	 * @throws OrderRejectedException 
	 * @throws FailedInternetConnectionException 
	 * @throws InvalidCardNumberException 
	 */
	public boolean contratarPremium(String numTarjeta) throws InvalidCardNumberException, FailedInternetConnectionException, OrderRejectedException {
			File file = new File("registro_pagos.txt");
			FileWriter fw = null;	
			TeleChargeAndPaySystem.charge(this.numTarjeta,"Contratacion Premium",10);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MM. yyyy");
			if(saldo<10){
				//System.out.println("NO EXISTEN FONDOS SUFICIENTES PARA REALIZAR LA OPERACION\n");
			}
			else {
				this.saldo=saldo-10;
				this.setFechaPremium(LocalDate.now());
				this.setEsPremium(true);
			}
	        try
	        {
	            fw = new FileWriter(file,true);
				fw.append(this.getNumTarjeta()+"| "+this.getNombre()+"|"+" PAGO DE 10 EUR |"+this.getFechaPremium().format(formatter)+"\n");
				fw.close();
				return true;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			return false;
	}
	
	


	public String getNumTarjeta() {
		return numTarjeta;
	}

	/**
	 * Metodo que permite a un usuario notificar ccomo plagio una cancion
	 * @param cancion
	 */
	public void notificarPlagio(Cancion cancion) {
		cancion.setNotificada_plagio(true);
		Sistema.getInstance().getCancionesNotificadas().add(cancion);
		Sistema.getInstance().setNotificaciones(new Notificacion(cancion));
	}

	/*********************************************/
	
	/**
	 * Metodo que añade al array de canciones del usuario una nueva cancion
	 * @param cancion
	 */
	public void anadirCancion(Cancion cancion) {
		this.getCanciones().add(cancion);
	}
	
	/**
	 * Metodo que añade al array de canciones del usuario una nueva cancion
	 * @param album
	 */
	public void anadirAlbum(Album album) {
		this.getAlbunes().add(album);
	}
	
	/**
	 * Metodo que añade al array de canciones del usuario una nueva cancion
	 * @param album
	 */
	public void anadirListaReproduccion(ListaReproducciones lista) {
		this.getLista_reproducciones().add(lista);
	}
	
	/**
	 * Metodo que añade al array de canciones del usuario una nueva cancion
	 * @param cancion
	 */
	public void borrarCancion(Cancion cancion) {
		Sistema.getInstance().borrarReproducible(cancion);
	}
	
	/**
	 * Metodo que añade al array de canciones del usuario una nueva cancion
	 * @param album
	 */
	public void borrarAlbum(Album album) {
		Sistema.getInstance().borrarReproducible(album);
	}
	

	/**
	 * Metodo que da la capacidad a un usuario de crear una lista de reproducciones propia
	 * @param album
	 */
	public void borrarListaReproduccion(ListaReproducciones lista) {
		Sistema.getInstance().borrarReproducible(lista);
	}
	
	
	/**************************************************/
	
	/**
	 * Metodo que permite a un usuario, seguir a otro
	 * @param UsuarioRegistrado seguido
	 * @return true en caso de poder realizar la accion de forma correcta
	 */
	@SuppressWarnings("unused")
	public boolean follows(UsuarioRegistrado seguido) {
		if (Sistema.getInstance().getUsuarios().contains(seguido) == true) {
			this.seguidos.add(seguido);
			seguido.seguidores.add(this);
			Notificacion n = new Notificacion(seguido, this);
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UsuarioRegistrado [" + nombre + "]: ";
	}
	
	public void setCanciones(Cancion cancion) {
		canciones.add(cancion);
	}
	
	public LocalDate getFechaBloqueo() {
		return fechaBloqueo;
	}
	
	public void setFechaBloqueo(LocalDate fecha) {
		fechaBloqueo = fecha;
	}
	
	public void setBloqueoPermanente() {
		bloqueado = true;
		bloqueoPermanente = true;
	}
	
	public boolean getBloqueado() {
		return bloqueado;
	}
	
	public boolean getBloqueoPermanente() {
		return bloqueoPermanente;
	}
}
