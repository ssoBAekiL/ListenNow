package es.uam.padsof.objetoreproducible;

import java.io.FileNotFoundException;
import java.util.*;

import pads.musicPlayer.Mp3Player;
import pads.musicPlayer.exceptions.Mp3PlayerException;

public abstract class ObjetoReproducible {
	
	protected Mp3Player player;
	protected String ruta;
	protected String titulo;
	protected String autor;
	
	public ObjetoReproducible(String titulo, String autor) throws FileNotFoundException, Mp3PlayerException {
		titulo = titulo;
		autor = autor;
		player  = new Mp3Player();
	}
		
	
	/**
	 * @param reproducible
	 */
	public abstract void reproducir()throws FileNotFoundException, Mp3PlayerException, InterruptedException; 
		

	/**
	 * @param reproducible
	 */
	public void pararReproduccion()throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		if(Mp3Player.isValidMp3File(ruta)==true) {
			player.add(ruta);
			player.stop();
		}
	}
	
}