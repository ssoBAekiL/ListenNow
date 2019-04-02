package es.uam.padsof.objetoreproducible;

import java.io.FileNotFoundException;
import java.io.Serializable;

import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.Mp3Player;
import pads.musicPlayer.exceptions.Mp3PlayerException;


/**
 * 
 * Clase ObjetoReproducible, que posee todos las caracteristicas propias 
 * de un objeto reproducible al igual que funciones que trabajan sobre este
 * @author Julian Espada, Carlos Miret y Pablo Borrelli
 *
 */
public abstract class ObjetoReproducible implements Serializable {
	
	/**
	 * ID de serializacion
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Reproductor del reproducible
	 */
	protected transient Mp3Player player; 
	
	/**
	 * Ruta del reproducible
	 */
	protected String ruta;
	
	/**
	 * Titulo del reproducible
	 */
	protected String titulo;
	
	/**
	 * Autor del reproducible
	 */
	protected UsuarioRegistrado autor;
	
	
	
	/**
	 * Metodo constructor del objeto reproducible
	 * @param titulo
	 * @param autor
	 * @param ruta
	 * @throws FileNotFoundException
	 * @throws Mp3PlayerException
	 */
	public ObjetoReproducible(String titulo, UsuarioRegistrado autor, String ruta) throws FileNotFoundException, Mp3PlayerException {
		this.titulo = titulo;
		this.autor = autor;
		this.ruta=ruta;
		this.player  = new Mp3Player();
	}
		
	
	/**
	 * Metodo que reproduce un objeto reproducible, haciendo uso de la libreria Mp3Player
	 */
	public abstract boolean reproducir()throws FileNotFoundException, Mp3PlayerException, InterruptedException; 
		

	/**
	 * Metodo que para la reproduccion de un objeto reproducible, haciendo uso de la libreria Mp3Player
	 */
	public abstract void pararReproduccion()throws FileNotFoundException, Mp3PlayerException, InterruptedException;

	/**
	 * metodo getter del reproductor de este objeto reproducible
	 * @return the player
	 */
	public Mp3Player getPlayer() {
		return player;
	}



	/**
	 * Metodo getter de la ruta del reproducible
	 * @return String ruta del archivo
	 */
	public String getRuta() {
		return ruta;
	}

	

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}


	/**
	 * Metodo getter del autor del objeto reproducible
	 * @return the autor
	 */
	public UsuarioRegistrado getAutor() {
		return autor;
	}
	
	
	
}
