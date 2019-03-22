package es.uam.padsof.objetoreproducible;

import java.io.FileNotFoundException;
import java.util.*;

import es.uam.padsof.sistema.Sistema;
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
		comentarios=new ArrayList<Comentario>();
	}

	
	/**
	 * @return the comentarios
	 */
	public ArrayList<Comentario> getComentarios() {
		return comentarios;
	}

	/**
	 * Metodo que anyade un comentario a un objeto comentable, ya sea album o cancion
	 * @param c
	 */
	public void anadirComentario(UsuarioRegistrado usr, Comentario c) {
		//if(Sistema.getInstance().getUsuarioEnSesion().equals(usr)) {
			comentarios.add(c);
			//return true;
		//}
		//return false;
	}

	
}
