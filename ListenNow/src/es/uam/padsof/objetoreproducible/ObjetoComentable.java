package es.uam.padsof.objetoreproducible;

import java.io.FileNotFoundException;
import java.util.*;

import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * Esta clase es la clase padre que
 * @author carlosmiret
 *
 */
public abstract class ObjetoComentable extends ObjetoReproducible{
	
	ArrayList<Comentario> comentarios;
	
	/**
	 * @param titulo
	 * @param autor
	 * @param ruta
	 * @throws FileNotFoundException
	 * @throws Mp3PlayerException
	 */
	public ObjetoComentable(String titulo, UsuarioRegistrado autor,String ruta) throws FileNotFoundException, Mp3PlayerException {
		super(titulo, autor, ruta);
	}

	/**
	 * Metodo que anyade un comentario a un objeto comentable, ya sea album o cancion
	 * @param c
	 */
	public void anadirComentario(Comentario c) {
			comentarios.add(c);
	}

	
	
	
}
