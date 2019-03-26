package es.uam.padsof.objetoreproducible;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import es.uam.padsof.sistema.Sistema;
import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.Mp3Player;
import pads.musicPlayer.exceptions.Mp3PlayerException;
//import es.uam.padsof.usuario.*;

/**
 * @author Juli�n Espada, Pablo Borrelli y Carlos Miret
 *
 * Esta clase se encarga de gestionar el objeto Album
 */
public class Album extends ObjetoComentable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double duracionAcumulada;
	
	/** Canciones del album*/
	private ArrayList<Cancion> canciones;
	
	
	/**
	 * 
	 * @param nCan Es el numero de canciones totales que tiene el album
	 * @param canciones Es el array que tendra las canciones de dicho �lbum
	 * 
	 * Este metodo es el constructor del objeto Album
	 */
	public Album (String titulo, UsuarioRegistrado autor)throws Mp3PlayerException, FileNotFoundException {
		super(titulo, autor,null);
		this.canciones=new ArrayList<Cancion>();
	}
	
	/**
	 * Metodo que anade un comentario a un album determinado
	 * @return true en caso de todo correcto
	 */
	public boolean anadirComentario(Comentario c) {
		if(Sistema.getInstance().getUsuarioEnSesion().puedeComentar()) {
			super.comentarios.add(c);
			return true;
		}
		return false;
	}
	
	/********************************/
	
	
	/**
	 * 
	 * Este metodo devuelve el numero de canciones que tiene el album
	 * 
	 * @return canciones.size() Es un metodo que se encarga de devolver el tamanio del array canciones
	 * 
	 */
	public int getNumCanciones() {
		return canciones.size();
	}
	
	
	/**
	 * getter de cancion
	 * @return canciones Es el array que tiene todas las canciones de un album
	 */
	public ArrayList<Cancion> getCanciones() {
		return canciones;
	}
	
	/**
	 * Setter de cancion
	 * @param canciones Es el nuevo array que tendr� el �lbum a modificar
	 */
	public void setCanciones(ArrayList<Cancion> canciones) {
		this.canciones = canciones;
	}
	
	/**
	 * 
	 * @param c Cancion a anadir
	 * 
	 * Este metodo se encargara de anadir una cancion al album
	 */
	public boolean aniadirCancionAlbum(Cancion c) {
		if(this.getCanciones().contains(c))
			return false;
		canciones.add(c);
		return true;
	}
	
	/**
	 * 
	 * @param c Cancion a borrar
	 * 
	 * Este metodo se encarga de borrar la cancion que se le pasa como argumento del album
	 */
	public boolean borrarCancionAlbum(Cancion c) {
		if(this.canciones.contains(c)) {
			canciones.remove(c);
			return true;		
		}
		return false;
	}
	
	/**
	 * 
	 * @param c Canciones a anadir
	 * 
	 * Este metodo se encarga de anadir masivamente varias canciones al album
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
	 * Funcion que borra las canciones presentes en un album
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

	
	/**
	 * Funcion reproducir, que recorre las diferentes canciones de un album una a una
	 * @see es.uam.padsof.objetoreproducible.ObjetoReproducible#reproducir()
	 */
	public void reproducir() throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		for(int i=0;i<this.getGetTamanioAlbum();i++) {
			this.getNCancion(i).reproducir();
		}
	}
	
	/**
	 * 
	 * @see es.uam.padsof.objetoreproducible.ObjetoReproducible#pararReproduccion()
	 */
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

	public double getDuracionAcumulada() {
		return duracionAcumulada;
	}

	public void setDuracionAcumulada() throws FileNotFoundException {
		double dur;
		for(Cancion c: canciones) {
			dur=Mp3Player.getDuration(c.getRuta());
			duracionAcumulada=duracionAcumulada+dur;
		}
	}

	@Override
	public boolean moverCancionASistema() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}
	
	/* IMPORTANTE COMPROBAR SI YA EXISTE LA CANCION EN EL ALBUM AL A�ADIR O SI NO EXISTE AL BORRAR O SI NO ES DEL AUTOR*/
	
}