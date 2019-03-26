package es.uam.padsof.objetoreproducible;
import java.io.FileNotFoundException;
import java.io.IOException;
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
public class ListaReproducciones extends ObjetoReproducible{
	
	/** The Lista canciones. */
	private ArrayList <Cancion> ListaCanciones;
	
	/** The Lista albumes. */
	private ArrayList <Album> ListaAlbumes;
	
	/** The Listas. */
	private ArrayList <ListaReproducciones> Listas;
	
	
	/**Numero de canciones de la lista*/
	private int numeroDeCanciones;
	
	/**
	 * Metodo constructor de lista de reproducciones
	 * @param titulo
	 * @param autor
	 * @throws IOException
	 * @throws Mp3PlayerException
	 */
	public ListaReproducciones (String titulo, UsuarioRegistrado autor)throws IOException, Mp3PlayerException {
		super(titulo, autor, null);
		this.ListaCanciones=null;
		this.ListaAlbumes=null;
		this.Listas=null;
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
		if(this.ListaCanciones.contains(c))
			return false;
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
		for(int i=0;i<a.getGetTamanioAlbum();i++) {
			if(a.getCanciones().contains(this.ListaCanciones.get(i))) {
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
		if(this.Listas.contains(l))
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


	/**
	 * Metodo parar reprod
	 */
	public void pararReproduccion()throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		for(int i=0;i<this.ListaCanciones.size();i++)	
			if(Mp3Player.isValidMp3File(ruta)==true) {
				player.add(this.ruta);
				player.stop();
			}
	}

	
}
