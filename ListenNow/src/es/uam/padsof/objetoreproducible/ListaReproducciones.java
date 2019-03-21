package es.uam.padsof.objetoreproducible;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

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
	
	/**
	 * Instantiates a new lista reproducciones.
	 *
	 * @param c Array de canciones
	 * @param a Array de albumes
	 * @param l Array de lista de reproducciones
	 */
	public ListaReproducciones (String titulo, String autor, ArrayList<Cancion> c, ArrayList<Album> a, ArrayList<ListaReproducciones> l)throws IOException, Mp3PlayerException {
		super(titulo, autor, null);
		this.ListaCanciones=c;
		this.ListaAlbumes=a;
		this.Listas=l;
	}
	
	public ListaReproducciones (String titulo, String autor, ArrayList<Cancion> c, ArrayList<Album> a)throws IOException, Mp3PlayerException {
		super(titulo, autor, null);
		this.ListaCanciones=c;
		this.ListaAlbumes=a;
		this.Listas=null;
	}

	/**
	 * Devuelve las canciones.
	 *
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
	 * Devuelve el array de albumes que tendr� la lista
	 *
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
	public void aniadirCancionALista(Cancion c) {
		int i;
		for(i=0; i<ListaCanciones.size(); i++) {
			if(ListaCanciones.get(i)==c) {
				return;
			}
		}
		ListaCanciones.add(c);
		return;
	}
	
	/**
	 * A�ade un album a la lista de reproduccion 
	 * 
	 * @param a El album nuevo a introducir en la lista de reproduccion
	 */
	public void aniadirAlbumALista(Album a) {
		int i;
		for(i=0; i<ListaAlbumes.size(); i++) {
			if(ListaAlbumes.get(i)==a) {
				return;
			}
		}
		ListaAlbumes.add(a);
		return;
	}
	
	/**
	 * A�ade una lista a la lista de reproduccion 
	 * 
	 * @param l
	 */
	public void aniadirListaALista(ListaReproducciones l) {
		int i;
		for(i=0; i<Listas.size(); i++) {
			if(Listas.get(i)==l) {
				return;
			}
		}
		Listas.add(l);
		return;
	}
	
	/**
	 * Elimina una cancion de una lista de reproduccion
	 * 
	 * @param c Cancion a borrar
	 */
	public void borrarCancionALista(Cancion c) {
		ListaCanciones.remove(c);
		return;
	}
	
	/**
	 * Elimina un album de una lista de reproduccion
	 * @param a Album a borrar
	 */
	public void borrarAlbumALista(Album a) {
		ListaAlbumes.remove(a);
		return;
	}
	
	/**
	 * Elimina una lista a una lista de reproduccion
	 * @param l Lista a borrar
	 */
	public void borrarListaALista(ListaReproducciones l) {
		Listas.remove(l);
		return;
	}
	
	public void reproducir() throws FileNotFoundException, Mp3PlayerException, InterruptedException{
		for(Cancion c: this.ListaCanciones) {
			c.reproducirObjeto();
		}
		
		for(Album a: this.ListaAlbumes) {
			a.reproducirObjeto();
		}
	}

	
}
