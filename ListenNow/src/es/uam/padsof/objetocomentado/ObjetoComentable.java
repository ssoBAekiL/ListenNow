package es.uam.padsof.objetocomentado;

import java.io.FileNotFoundException;
import java.util.*;

import es.uam.padsof.objetoreproducible.ObjetoReproducible;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * Esta clase es la clase padre que
 * @author carlosmiret
 *
 */
public class ObjetoComentable extends ObjetoReproducible{
	ArrayList<Comentario> comentarios;

	public ObjetoComentable() throws FileNotFoundException, Mp3PlayerException {
		super();
	}

	/**
	 * Metodo que anyade un comentario a un objeto comentable, ya sea album o cancion
	 * @param c
	 */
	public void anadirComentario(Comentario c) {
			comentarios.add(c);
	}
	
	
	
}
