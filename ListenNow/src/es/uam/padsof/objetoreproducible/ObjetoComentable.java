package es.uam.padsof.objetoreproducible;

import java.io.FileNotFoundException;
import java.util.*;

import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * Clase ObjetoComentable, que posee todos las caracteristicas propias 
 * de un objeto comentable al igual que funciones que trabajan sobre este
 * @author Julian Espada, Carlos Miret y Pablo Borrelli
 *
 */
public abstract class ObjetoComentable extends ObjetoReproducible {	//podemos observar como esta clase hereda de ObjetoReproducible
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Array de comentarios que tiene un objeto comentable
	 */
	protected ArrayList<Comentario> comentarios;
	
	/**
	 * Metodo constructor de la clase ObjetoComentable
	 * @param titulo
	 * @param autor
	 * @param ruta
	 * @throws FileNotFoundException
	 * @throws Mp3PlayerException
	 */
	public ObjetoComentable(String titulo, UsuarioRegistrado autor,String ruta) throws FileNotFoundException, Mp3PlayerException {
		super(titulo, autor, ruta);
		comentarios=new ArrayList<Comentario>();
	}


	/**
	 * Metodo que anyade un comentario a un objeto comentable, ya sea album o cancion
	 * @param c
	 */
	public abstract boolean anadirComentario(Comentario c);

	
	/**
	 * Metodo que anyade una cancion a un objeto comentable (album)
	 * @param c
	 */
	public abstract boolean anadirCancion(ObjetoComentable c);
	
	
	/**
	 * Funcion getter de comentarios
	 * @return ArrayList comentarios
	 */
	public ArrayList<Comentario> getComentarios() {
		return comentarios;
	}
	
}
