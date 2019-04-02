package es.uam.padsof.objetoreproducible;

import java.io.FileNotFoundException;

import java.util.ArrayList;

import es.uam.padsof.sistema.Sistema;
import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * 
 * Clase album, que posee todos las caracteristicas propias 
 * de un album al igual que funciones que trabajan sobre este
 * @author Julian Espada, Pablo Borrelli y Carlos Miret
 *
 */
public class Album extends ObjetoComentable {
	/**
	 * ID de serializacion
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Duracion Acumulada del album
	 */
	private double duracionAcumulada;
	
	/** 
	 * Canciones del album
	 */
	private ArrayList<Cancion> canciones;
	
	
	/**
	 * 
 	 * Este metodo es el constructor del Album
	 * 
	 * @param String Titulo de la cancion
	 * @param UsuarioRegistrado usuario autor del album
	 * 
	 */
	public Album (String titulo, UsuarioRegistrado autor)throws Mp3PlayerException, FileNotFoundException {
		super(titulo, autor,null);
		this.canciones=new ArrayList<Cancion>();
		this.duracionAcumulada = 0;
	}
	
	/**
	 * Metodo que anade un comentario a un album determinado
	 * @return true en caso de todo correcto
	 */
	public boolean anadirComentario(Comentario c) {
		if(Sistema.getInstance().getConectado() == true && Sistema.getInstance().getUsuarioEnSesion().puedeComentar()) {
			this.getComentarios().add(c);
			return true;
		}
		return false;
	}
	
	
	
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
	 * Getter de canciones del album
	 * @return canciones Es el array que tiene todas las canciones de un album
	 */
	public ArrayList<Cancion> getCanciones() {
		return canciones;
	}
	
	/**
	 * Setter de cancion
	 * @param canciones Es el nuevo array que tendrï¿½ el ï¿½lbum a modificar
	 */
	public void setCanciones(ArrayList<Cancion> canciones) {
		this.canciones = canciones;
	}
	
	/**
	 * 
	 * Este metodo se encargara de anadir una cancion al album
	 * @param c Cancion a anadir 
	 */
	public boolean aniadirCancionAlbum(Cancion c) {
		if(this.getCanciones().contains(c))
			return false;
		this.canciones.add(c);
		this.duracionAcumulada += c.getDuracion();
		return true;
	}
	
	/**
	 * Este metodo se encarga de borrar la cancion que se le pasa como argumento del album
	 * @param c Cancion a borrar una cancion de un album determinado
	 * 
	 */
	public boolean borrarCancionAlbum(Cancion c) {
		if(this.canciones.contains(c)) {
			canciones.remove(c);
			return true;		
		}
		return false;
	}
	
	/**
	 * Este metodo se encarga de anadir masivamente varias canciones al album
	 * @param c Canciones a anadir
	 */
	public void aniadirCancionesAlbum(Cancion...c) {
		int i=0;
		while(i<c.length) {
			aniadirCancionAlbum(c[i]);
			this.duracionAcumulada += c[i].getDuracion();
			i++;
		}
		return;
	}
	
	
	/**
	 * Funcion que borra las canciones presentes en un album
	 * 
	 * @param c
	 */
	public boolean borrarCancionesAlbum(Cancion...c) {
		int i=0;
		while(i<c.length) {
			borrarCancionAlbum(c[i]);
			i++;
		}
		return true;
	}

	
	/**
	 * Funcion reproducir, que recorre las diferentes canciones de un album una a una
	 * @see es.uam.padsof.objetoreproducible.ObjetoReproducible#reproducir()
	 */
	public boolean reproducir() throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		for(int i=0;i<this.getGetTamanioAlbum();i++) {
			this.getNCancion(i).reproducir();
		}
		return true;
	}
	
//	/**
//	 * 
//	 * @see es.uam.padsof.objetoreproducible.ObjetoReproducible#pararReproduccion()
//	 */
//	public void pararReproduccion()throws FileNotFoundException, Mp3PlayerException, InterruptedException {
//		for(int i=0;i<this.getGetTamanioAlbum();i++) {
//			this.getNCancion(i).pararReproduccion();
//		}
//	}
	
	
	
	/** Metodo toString de la clase Album
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Album [duracionAcumulada=" + this.duracionAcumulada + "		\n canciones=" + canciones + "]";
	}

	
	/**
	 * Metodo que devuelve una cancion determinada de un album
	 * @param i indice de la cancion dentro del array de canciones
	 * @return cancion cancion determinada por iterador i
	 */
	public Cancion getNCancion(int i) {
		return this.canciones.get(i);
	}
	
	
	/**
	 * Metodo que devuelve el comentario indicado por el indice i pasado por parametro
	 * @param i
	 * @return
	 */
	public Comentario getNComentario(int i) {
		return this.comentarios.get(i);
	}
	
	
	/**
	 * Metodo que devuelve el tamanio de un album
	 * @return int valor tamaño del album, es decir el numero de canciones continentes
	 */
	public int getGetTamanioAlbum() {
		return this.canciones.size();
	}

	/**
	 * Metodo que devuelve la duracion acumulada de un album
	 * @return double Duracion acumulada
	 * @throws FileNotFoundException
	 */
	public double getDuracionAcumulada() throws FileNotFoundException {
		return duracionAcumulada;
	}
	
	
	/**
	 * Metetodo que aniade una cancion al objeto reproducible Cancion
	 * @return boolean true en caso de todo correcto
	 */
	public boolean anadirCancion(ObjetoComentable c) {
		this.aniadirCancionAlbum((Cancion) c);
		return false;
	}

	@Override
	public void pararReproduccion() throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		// TODO Auto-generated method stub
		
	}

	
	
}
