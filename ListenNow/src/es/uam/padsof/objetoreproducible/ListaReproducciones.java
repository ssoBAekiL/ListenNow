package es.uam.padsof.objetoreproducible;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import es.uam.padsof.sistema.Sistema;
import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.Mp3Player;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * @author Julian Espada, Carlos Miret y Pablo Borrelli
 * 
 * Esta clase se encarga de gestionar el objeto ListaReproducciones
 */
public class ListaReproducciones extends ObjetoReproducible {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The Lista canciones. */
	private ArrayList <Cancion> ListaCanciones;
	
	/** The Lista albumes. */
	private ArrayList <Album> ListaAlbumes;
	
	/** The Listas. */
	private ArrayList <ListaReproducciones> Listas;
	
	
	/**Numero de canciones de la lista*/
	private int numeroDeCanciones;
	
	/** Duracion de la lista de reproduccion*/
	private double duracionAcumulada;
	
	/**
	 * Metodo constructor de lista de reproducciones
	 * @param titulo
	 * @param autor
	 * @throws IOException
	 * @throws Mp3PlayerException
	 */
	public ListaReproducciones (String titulo, UsuarioRegistrado autor)throws IOException, Mp3PlayerException {
		super(titulo, autor, null);
		this.ListaCanciones=new ArrayList<Cancion>();
		this.ListaAlbumes=new ArrayList<Album>();
		this.Listas=new ArrayList<ListaReproducciones>();
		this.numeroDeCanciones=0;
	}


	/**
	 * Devuelve las canciones que contiene la lista de reproducciones.
	 * @return ListaCanciones Las canciones que tiene la lista de reproduccion
	 */
	public ArrayList<Cancion> getListaCanciones() {
		return ListaCanciones;
	}

	/**
	 * Modifica la lista de canciones.
	 *
	 * @param listaCanciones La nueva lista de canciones
	 */
	public void setListaCanciones(ArrayList<Cancion> listaCanciones) {
		ListaCanciones = listaCanciones;
	}

	/**
	 * Devuelve el array de albumes que tendra la lista
	 * @return ListaAlbumes El array de albumes de la lista
	 */
	public ArrayList<Album> getListaAlbumes() {
		return ListaAlbumes;
	}

	/**
	 * Modifica el array de albumes de la lista.
	 *
	 * @param listaAlbumes Es el nuevo array de albumes 
	 */
	public void setListaAlbumes(ArrayList<Album> listaAlbumes) {
		ListaAlbumes = listaAlbumes;
	}

	/**
	 * Devuelve las listas de reproduccion que tiene a su vez la lista de reproduccion principal.
	 *
	 * @return Listas El array de listas de reproducciones que tendr� la lista de reproduccion principal
	 */
	public ArrayList<ListaReproducciones> getListas() {
		return Listas;
	}

	/**
	 * Modifica la lista de reproduccion.
	 *
	 * @param listas El nuevo array de lista de reproduccion
	 */
	public void setListas(ArrayList<ListaReproducciones> listas) {
		Listas = listas;
	}
	
	/**
	 * A�ade una cancion a la lista de reproduccion 
	 * 
	 * @param c La cancion nueva a introducir en la lista de reproduccion
	 */
	public boolean aniadirCancionALista(Cancion c) {
<<<<<<< HEAD
		int i, j;
		for(i=0; i<ListaCanciones.size(); i++) {
			if(ListaCanciones.get(i)==c) {
				return false;
			}
		}
		for(i=0; i<this.ListaAlbumes.size(); i++) {
			for(j=0; j<this.ListaAlbumes.get(i).getGetTamanioAlbum(); j++) {
				if(this.ListaAlbumes.get(i).getNCancion(j).equals(c)) {
					return false;
				}
			}
		}
=======
		if(this.ListaCanciones.contains(c))
			return false;
>>>>>>> branch 'v.2' of https://github.com/ssoBAekiL/ListenNow.git
		ListaCanciones.add(c);
		this.numeroDeCanciones++;
		return true;
	}
	
	/**
	 * Anade un album a la lista de reproduccion 
	 * 
	 * @param a El album nuevo a introducir en la lista de reproduccion
	 */
	public boolean aniadirAlbumALista(Album a) {
		if(this.ListaAlbumes.contains(a)) {
			return false;	
		}
		for(int i=0;i<this.ListaCanciones.size();i++) {
			if(a.getCanciones().contains(this.ListaCanciones.get(i))==true) {
			/*forma indirecta de encontrar una cancion en una lista a traves de un album*/ 
				return false;
			}
		}
		ListaAlbumes.add(a);
		return true;
	}
	
	
	
	/**
	 * Anade una lista a la lista de reproduccion 
	 * 
	 * @param l
	 */
	public boolean aniadirListaALista(ListaReproducciones l) {
		if(this.Listas.contains(l) || this.Listas.contains(this))
			return false;
		Listas.add(l);
		return true;
	}
	

	
	/**
	 * Elimina un album de una lista de reproduccion
	 * @param a Album a borrar
	 */
	public boolean borrarAlbumALista(Album a) {
		if(Sistema.getInstance().getUsuarioEnSesion().equals(a.getAutor())) {
			ListaAlbumes.remove(a);
			return true;
		}
		return false;
	}
	
	
	/**
	 * Metodo reproducir  --->  reproduce una lista de reproduccion
	 * @see es.uam.padsof.objetoreproducible.ObjetoReproducible#reproducir()
	 */
	public void reproducir() throws FileNotFoundException, Mp3PlayerException, InterruptedException{
		for(Cancion c: this.ListaCanciones) {
			c.reproducir();
		}
		for(Album a: this.ListaAlbumes) {
			a.reproducir();
		}
	}
	
	/**
	 * @return the numeroDeCanciones
	 */
	public int getNumeroDeCanciones() {
		return numeroDeCanciones;
	}

<<<<<<< HEAD

	/**
	 * Metodo parar reprod
	 */
	public void pararReproduccion()throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		for(int i=0;i<this.ListaCanciones.size();i++)	
			if(Mp3Player.isValidMp3File(ruta)==true) {
				player.add(this.ruta);
				player.stop();
			}
=======
//
//	/**
//<<<<<<< HEAD
//	 * Metodo parar reprod
//	 */
//	@Override
//	public void pararReproduccion() throws FileNotFoundException, Mp3PlayerException, InterruptedException {
//		for(Cancion c: this.ListaCanciones) {
//			c.pararReproduccion();
//		}
//		for(Album a: this.ListaAlbumes) {
//			a.pararReproduccion();
//=======
//	 * @see es.uam.padsof.objetoreproducible.ObjetoReproducible#pararReproduccion()
//	 */
	
	
	@Override
	public void pararReproduccion() throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		for(Cancion c: this.ListaCanciones) {
			c.pararReproduccion();
		}
		for(Album a: this.ListaAlbumes) {
			a.pararReproduccion();
		}
>>>>>>> branch 'v.2' of https://github.com/ssoBAekiL/ListenNow.git
	}


	public double getDuracionAcumulada() {
		return duracionAcumulada;
	}


	public void setDuracionAcumulada() throws FileNotFoundException {
		for(Cancion c: ListaCanciones) {
			duracionAcumulada=duracionAcumulada+Mp3Player.getDuration(c.ruta);
		}
		
		for(Album a: ListaAlbumes) {
			duracionAcumulada=duracionAcumulada+a.getDuracionAcumulada();
		}
	}

	
}
