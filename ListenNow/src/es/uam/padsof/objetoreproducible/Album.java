package es.uam.padsof.objetoreproducible;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.exceptions.Mp3PlayerException;
//import es.uam.padsof.usuario.*;

/**
 * @author Juli�n Espada, Pablo Borrelli y Carlos Miret
 *
 * Esta clase se encarga de gestionar el objeto Album
 */
public class Album extends ObjetoReproducible{
	/*private time duracion;*/
	
	/** Canciones del album*/
	private ArrayList<Cancion> canciones;
	
	public ArrayList<Comentario> comentarios;
	
	/**
	 * 
	 * @param nCan Es el n�mero de canciones totales que tiene el album
	 * @param canciones Es el array que tendr� las canciones de dicho �lbum
	 * 
	 * Este m�todo es el constructor del objeto Album
	 */
	public Album (String titulo, UsuarioRegistrado autor)throws Mp3PlayerException, FileNotFoundException {
		super(titulo, autor, null);
		this.canciones=new ArrayList<Cancion>();
	}
	
	/**
	 * 
	 * @return canciones.size() Es un m�todo que se encarga de devolver el tama�o del array canciones
	 * 
	 * Este m�todo devuelve el n�mero de canciones que tiene el �lbum
	 */
	public int getnCanciones() {
		return canciones.size();
	}
	
	
	/**
	 * 
	 * @return canciones Es el array que tiene todas las canciones de un �lbum
	 * 
	 * Este m�todo se encarga de devolver el array con las canciones que tendr� un �lbum
	 */
	public ArrayList<Cancion> getCanciones() {
		return canciones;
	}
	
	/**
	 * 
	 * @param canciones Es el nuevo array que tendr� el �lbum a modificar
	 */
	public void setCanciones(ArrayList<Cancion> canciones) {
		this.canciones = canciones;
	}
	
	/**
	 * 
	 * @param c Canci�n a a�adir
	 * 
	 * Este m�todo se encargar� de a�adir una canci�n al �lbum
	 */
	public void aniadirCancionAlbum(Cancion c) {
		int i=0;
		ArrayList<Cancion> canciones = this.getCanciones();
		for(i=0; i<canciones.size(); i++) {
			if(canciones.get(i)==c) {
				return;
			}
		}
		canciones.add(c);
		return;
	}
	
	/**
	 * 
	 * @param c Canci�n a borrar
	 * 
	 * Este m�todo se encarga de borrar la canci�n que se le pasa como argumento del �lbum
	 */
	public void borrarCancionAlbum(Cancion c) {
		canciones.remove(c);
		return;
	}
	
	/**
	 * 
	 * @param c Canciones a a�adir
	 * 
	 * Este m�todo se encarga de a�adir masivamente varias canciones al �lbum
	 */
	public void aniadirCancionesAlbum(Cancion...c) {
		int i=0;
		while(i<c.length) {
			aniadirCancionAlbum(c[i]);
			i++;
		}
		return;
	}
	
	/**
	 * 
	 * @param c
	 */
	public void borrarCancionesAlbum(Cancion...c) {
		int i=0;
		while(i<c.length) {
			borrarCancionAlbum(c[i]);
			i++;
		}
		return;
	}

	public ArrayList<Comentario> getComentarios() {
		return comentarios;
	}
	
	public void reproducir() throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		for(int i=0;i<this.getGetTamanioAlbum();i++) {
			this.getNCancion(i).reproducir();
		}
	}
	
	public void pararReproduccion()throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		for(int i=0;i<this.getGetTamanioAlbum();i++) {
			this.getNCancion(i).pararReproduccion();
		}
	}
	
	
	public Cancion getNCancion(int i) {
		return this.canciones.get(i);
	}
	
	public Comentario getNComentario(int i) {
		return this.comentarios.get(i);
	}
	
	public int getGetTamanioAlbum() {
		return this.canciones.size();
	}
	
	/* IMPORTANTE COMPROBAR SI YA EXISTE LA CANCION EN EL ALBUM AL A�ADIR O SI NO EXISTE AL BORRAR O SI NO ES DEL AUTOR*/
	
}