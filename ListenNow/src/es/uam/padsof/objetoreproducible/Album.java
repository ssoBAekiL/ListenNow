package es.uam.padsof.objetoreproducible;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.exceptions.Mp3PlayerException;
//import es.uam.padsof.usuario.*;

/**
 * @author Juliï¿½n Espada, Pablo Borrelli y Carlos Miret
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
	 * @param nCan Es el nï¿½mero de canciones totales que tiene el album
	 * @param canciones Es el array que tendrï¿½ las canciones de dicho ï¿½lbum
	 * 
	 * Este mï¿½todo es el constructor del objeto Album
	 */
	public Album (String titulo, UsuarioRegistrado autor, String ruta, ArrayList<Cancion> canciones)throws Mp3PlayerException, FileNotFoundException {
		super(titulo, autor, ruta);
		this.canciones=canciones;
	}
	
	/**
	 * 
	 * @return canciones.size() Es un mï¿½todo que se encarga de devolver el tamaï¿½o del array canciones
	 * 
	 * Este mï¿½todo devuelve el nï¿½mero de canciones que tiene el ï¿½lbum
	 */
	public int getnCanciones() {
		return canciones.size();
	}
	
	
	/**
	 * 
	 * @return canciones Es el array que tiene todas las canciones de un ï¿½lbum
	 * 
	 * Este mï¿½todo se encarga de devolver el array con las canciones que tendrï¿½ un ï¿½lbum
	 */
	public ArrayList<Cancion> getCanciones() {
		return canciones;
	}
	
	/**
	 * 
	 * @param canciones Es el nuevo array que tendrï¿½ el ï¿½lbum a modificar
	 */
	public void setCanciones(ArrayList<Cancion> canciones) {
		this.canciones = canciones;
	}
	
	/**
	 * 
	 * @param c Canciï¿½n a aï¿½adir
	 * 
	 * Este mï¿½todo se encargarï¿½ de aï¿½adir una canciï¿½n al ï¿½lbum
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
	 * @param c Canciï¿½n a borrar
	 * 
	 * Este mï¿½todo se encarga de borrar la canciï¿½n que se le pasa como argumento del ï¿½lbum
	 */
	public void borrarCancionAlbum(Cancion c) {
		canciones.remove(c);
		return;
	}
	
	/**
	 * 
	 * @param c Canciones a aï¿½adir
	 * 
	 * Este mï¿½todo se encarga de aï¿½adir masivamente varias canciones al ï¿½lbum
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
	
	public String toString() {
		for(int i=0;i<this.getGetTamanioAlbum();i++) {
			System.out.println("Cancion "+(i+1)+":\n"+ this.getNCancion(i).toString()+ "\n");
		}
		return "Autor de álbum:"+this.getAutor()+"\n"+"Titulo álbum: "+this.getTitulo();
	}
	
	/* IMPORTANTE COMPROBAR SI YA EXISTE LA CANCION EN EL ALBUM AL Aï¿½ADIR O SI NO EXISTE AL BORRAR O SI NO ES DEL AUTOR*/
	
}