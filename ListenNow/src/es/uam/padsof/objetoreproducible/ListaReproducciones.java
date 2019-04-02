package es.uam.padsof.objetoreproducible;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * Clase ListaDeReproducciones, que posee todos las caracteristicas propias 
 * de una lista de reproducciones al igual que funciones que trabajan sobre este
 * @author Julian Espada, Carlos Miret y Pablo Borrelli
 * 
 */
public class ListaReproducciones extends ObjetoReproducible {
	
	/**
	 * ID de serializacion
	 */
	private static final long serialVersionUID = 1L;

	/** 
	 * Array de canciones que contiene una lista de reproducciones
	 */
	private ArrayList <Cancion> listaCanciones;
	
	/**
	 * Array de albunes que tiene una lista de reproducciones
	 */
	private ArrayList <Album> listaAlbunes;
	
	/**
	 * Array de listas que puede tener una lista de reproducciones
	 */
	private ArrayList <ListaReproducciones> Listas;
	
	/**
	 * Numero de canciones que tiene una lista de reprodcuccion
	 */
	private int numeroDeCanciones;
	
	
	/**
	 *	Duracion acumulada de una lista de reproducciones
	 */
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
		this.listaCanciones=new ArrayList<Cancion>();
		this.listaAlbunes=new ArrayList<Album>();
		this.Listas=new ArrayList<ListaReproducciones>();
		this.numeroDeCanciones=0;
		this.duracionAcumulada=0;
	}


	/**
	 * Devuelve las canciones de ua lista de reproducciones.
	 * @return listaCanciones Las canciones que tiene la lista de reproduccion
	 */
	public ArrayList<Cancion> getListaCanciones() {
		return listaCanciones;
	}


	/**
	 * Devuelve el array de Albunes que tendra la lista
	 * @return listaAlbunes El array de Albunes de la lista
	 */
	public ArrayList<Album> getListaAlbunes() {
		return listaAlbunes;
	}


	/**
	 * Devuelve las listas de reproduccion que tiene a su vez la lista de reproduccion principal.
	 *
	 * @return Listas El array de listas de reproducciones que tendra la lista de reproduccion principal
	 */
	public ArrayList<ListaReproducciones> getListas() {
		return Listas;
	}

	
	/**
	 * Aniade una cancion a la lista de reproduccion 
	 * 
	 * @param c La cancion nueva a introducir en la lista de reproduccion
	 * @return true en caso de todo correcto
	 */
	public boolean aniadirCancionALista(Cancion c) {
		int i, j;
		if(listaCanciones.contains(c)) {
			return false;
		}
		
		for(i=0; i<this.listaAlbunes.size(); i++) {
			for(j=0; j<this.listaAlbunes.get(i).getGetTamanioAlbum(); j++) {
				if(this.listaAlbunes.get(i).getCanciones().contains(c)) {
					return false;
				}
			}
		}
		listaCanciones.add(c);
		this.numeroDeCanciones++;
		this.duracionAcumulada += c.getDuracion();
		return true;
	}
	
	/**
	 * Anade un album a la lista de reproduccion 
	 * 
	 * @param a El album nuevo a introducir en la lista de reproduccion
	 * 
	 * @return true en caso de todo correcto
	 * @throws FileNotFoundException 
	 */
	public boolean aniadirAlbumALista(Album a) throws FileNotFoundException {
		if(this.listaAlbunes.contains(a)) {
			return false;	
		}
		for(int i=0;i<this.listaCanciones.size();i++) {
			if(a.getCanciones().contains(this.listaCanciones.get(i))==true) {
			/*forma indirecta de encontrar una cancion en una lista a traves de un album*/ 
				return false;
			}
		}
		listaAlbunes.add(a);
		this.duracionAcumulada += a.getDuracionAcumulada();
		return true;
	}
	
	
	
	/**
	 * Anade una lista a la lista de reproduccion 
	 * 
	 * @param l
	 * @return true en caso de todo correcto
	 * @throws FileNotFoundException 
	 */
	public boolean aniadirListaALista(ListaReproducciones l) throws FileNotFoundException {
		if(this.Listas.contains(l))
			return false;
		Listas.add(l);
		this.duracionAcumulada += l.getDuracionAcumulada();
		return true;
	}
	

	
	/**
	 * Elimina un album de una lista de reproduccion
	 * @param a Album a borrar
	 * @return true en caso de todo correcto
	 */
	public boolean borrarAlbumALista(Album a) {
		if(this.listaAlbunes.contains(a)) {
			listaAlbunes.remove(a);
			return true;
		}
		return false;
	}
	
	
	/**
	 * Elimina una cancion de una lista de reproduccion
	 * @param a cancion a borrar
	 */
	public boolean borrarCancionLista(Cancion c) {
		if(listaCanciones.contains(c)) {
			listaCanciones.remove(c);
			return true;
		}
		return false;
	}
	
	
	/**
	 * Funcion reproducir de la clase lista de reproducciones
	 * @see es.uam.padsof.objetoreproducible.ObjetoReproducible#reproducir()
	 */
	public boolean reproducir() throws FileNotFoundException, Mp3PlayerException, InterruptedException{
		for(Cancion c: this.listaCanciones) {
			c.reproducir();
		}
		for(Album a: this.listaAlbunes) {
			a.reproducir();
		}
		for(ListaReproducciones l: this.Listas) {
			l.reproducir();
		}
		return true;
	}
	
	/**
	 * Funcion getter del numero de canciones de una lista de reproduccion
	 * @return the numeroDeCanciones
	 */
	public int getNumeroDeCanciones() {
		return numeroDeCanciones;
	}

	
	
	/**
	 * Metodo parar reproduccion de un objeto reproducible que es la lista de reproduccion
	 * @see es.uam.padsof.objetoreproducible.ObjetoReproducible#pararReproduccion()
	 */
	@Override
	public void pararReproduccion() throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		for(Cancion c: this.listaCanciones) {
			c.pararReproduccion();
		}
		for(Album a: this.listaAlbunes) {
			a.pararReproduccion();
		}

	}


	/**
	 * Setter de la duracion acumulada en una lista de reproduccion
	 * @throws FileNotFoundException
	 * @return duracion acumulada de la lista de reproducciones
	 */
	public double getDuracionAcumulada() throws FileNotFoundException {
		return duracionAcumulada;
	}
	/**
	 * Metodo que devuelve todos los datos caracteristicos del objeto Lista de reproducciones
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "[Lista de reproducciones: " + this.titulo + "	\nduracion: " + this.duracionAcumulada+"]";
	}


	
}
