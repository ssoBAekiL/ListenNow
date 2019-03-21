package es.uam.padsof.objetoreproducible;

import java.io.FileNotFoundException;

import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.Mp3Player;
import pads.musicPlayer.exceptions.Mp3PlayerException;

public abstract class ObjetoReproducible {
	
	protected Mp3Player player;
	protected String ruta;
	protected String titulo;
	protected UsuarioRegistrado autor;
	
	/**
	 * @param titulo
	 * @param autor2
	 * @param ruta
	 * @throws FileNotFoundException
	 * @throws Mp3PlayerException
	 */
	public ObjetoReproducible(String titulo, UsuarioRegistrado autor2, String ruta) throws FileNotFoundException, Mp3PlayerException {
		this.titulo = titulo;
		this.autor = autor2;
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
		this.autor = autor;
	}
	
	
	
	
}