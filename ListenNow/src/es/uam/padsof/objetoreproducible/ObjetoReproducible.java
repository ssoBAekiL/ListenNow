package es.uam.padsof.objetoreproducible;

import java.io.FileNotFoundException;
import java.io.Serializable;

import es.uam.padsof.sistema.Sistema;
import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.Mp3Player;
import pads.musicPlayer.exceptions.Mp3PlayerException;

public abstract class ObjetoReproducible implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected transient Mp3Player player; 
	protected String ruta;
	protected String titulo;
	protected UsuarioRegistrado autor;
	
	/**
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
	 * @param reproducible
	 */
	public abstract void reproducir()throws FileNotFoundException, Mp3PlayerException, InterruptedException; 
		

	/**
	 * @param reproducible
	 */
	public abstract void pararReproduccion()throws FileNotFoundException, Mp3PlayerException, InterruptedException;

	/**
	 * @return the player
	 */
	public Mp3Player getPlayer() {
		return player;
	}


	/**
	 * @param player the player to set
	 */
	public void setPlayer(Mp3Player player) {
		this.player = player;
	}


	/**
	 * @return the ruta
	 */
	public String getRuta() {
		return ruta;
	}


	/**
	 * @param ruta the ruta to set
	 */
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}


	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}


	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		if(this instanceof Cancion && Sistema.getInstance().getCancionesValidadas().contains(this))
			return;
		else
			this.titulo = titulo;
	}


	/**
	 * @return the autor
	 */
	public UsuarioRegistrado getAutor() {
		return autor;
	}


	/**
	 * @param autor the autor to set
	 */
	public void setAutor(UsuarioRegistrado autor) {
		if(this instanceof Cancion && Sistema.getInstance().getCancionesValidadas().contains(this))
			return;
		else
			this.autor = autor;
	}
	
	
	
	
}