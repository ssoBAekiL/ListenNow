package es.uam.padsof.objetoreproducible;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import es.uam.padsof.sistema.Sistema;
import pads.musicPlayer.Mp3Player;
import pads.musicPlayer.exceptions.Mp3PlayerException;

public class probandoMusica {
	public static void main(String args[]) throws Mp3PlayerException, IOException {
		Mp3Player player = new Mp3Player();
		ObjetoReproducible c1 = new Cancion("Girls Like you",Sistema.getInstance().getUsuarioEnSesion(),"chicle.mp3");
		Cancion c2 = new Cancion("Un a�o", Sistema.getInstance().getUsuarioEnSesion(), "sebastian-yatra-reik-un-ano.mp3");
		ArrayList<Cancion> c = new ArrayList<Cancion>();
		Cancion c3 = new Cancion("Digno de alabar", Sistema.getInstance().getUsuarioEnSesion(), "athenas-jesus-eres-digno-de-alabar.mp3");
		c.add(c3);
		c.add(c2);
		c.add((Cancion) c1);
		
		player.add(c1.getRuta(), c2.getRuta());

//		Album a = new Album("AlbumDePrueba",Sistema.getInstance().getUsuarioEnSesion());
//		System.out.println("Album creado:\n");
//		System.out.println(a.toString());
//		a.reproducir();
	}
}
