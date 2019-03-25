

import java.io.IOException;
import java.util.*;
import pads.musicPlayer.exceptions.Mp3PlayerException;

public class pruebaMusica {
	public static void main(String[] args) throws IOException, Mp3PlayerException, InterruptedException{
		Cancion c1 = new Cancion("Girls Like you","Maroon5","maroon-5-girls-like-you-lyrics-ft-cardi-b.mp3");
		Cancion c2 = new Cancion("Un año", "Sebastián Yatra", "sebastian-yatra-reik-un-ano.mp3");
		ArrayList<Cancion> c = new ArrayList<Cancion>();
		Cancion c3 = new Cancion("Digno de alabar", "Athenas", "athenas-jesus-eres-digno-de-alabar.mp3");
		c.add(c3);
		c.add(c2);
		c.add(c1);
		Album a = new Album("AlbumDePrueba", "Julichan", c);
		System.out.println("Album creado:\n");
		System.out.println(a.toString());
		a.reproducir();
	}
}
