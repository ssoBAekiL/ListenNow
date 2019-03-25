package pruebas;

import java.io.IOException;

import es.uam.padsof.objetoreproducible.Album;
import es.uam.padsof.objetoreproducible.Cancion;
import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.exceptions.Mp3PlayerException;

public class PruebaAlbum {
	public static void main(String[] args) throws IOException, Mp3PlayerException, InterruptedException{
		UsuarioRegistrado u = new UsuarioRegistrado("1234567891234567", "Julichan", "zaqwedcxsw", true, false);
		Cancion c1 = new Cancion("Girls Like you", u,"maroon-5-girls-like-you-lyrics-ft-cardi-b.mp3");
		Cancion c2 = new Cancion("Un año", u, "sebastian-yatra-reik-un-ano.mp3");
		Cancion c3 = new Cancion("Digno de alabar", u, "athenas-jesus-eres-digno-de-alabar.mp3");
		Album a = new Album("AlbumDePrueba", u);
		a.aniadirCancionAlbum(c1);
		a.aniadirCancionAlbum(c2);
		a.aniadirCancionAlbum(c3);
		System.out.println("Album creado:\n");
		System.out.println(a.toString());
		a.reproducir();
	}
}