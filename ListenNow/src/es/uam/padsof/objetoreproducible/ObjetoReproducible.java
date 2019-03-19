package es.uam.padsof.objetoreproducible;

import java.io.FileNotFoundException;
import java.util.*;

import pads.musicPlayer.Mp3Player;
import pads.musicPlayer.exceptions.Mp3PlayerException;

public abstract class ObjetoReproducible {
	
	private Mp3Player player;
	
	public ObjetoReproducible() throws FileNotFoundException, Mp3PlayerException {
		player  = new Mp3Player();
	}
	
	/**
	 * @param reproducible
	 */
	public void reproducirObjeto(ObjetoReproducible reproducible) throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		if (player.isValidMp3File(reproducible.getRutaFichero())) {
			player.play();
			Thread.sleep(Mp3Player.getDuration(reproducible.getRutaFichero()*1000));
			player.stop();
		}
	}

	/**
	 * @param reproducible
	 */
	public void pararReproduccion(ObjetoReproducible reproducible) {
		
	}
	
}