package pruebas;

import java.io.FileNotFoundException;
import java.io.IOException;

import es.uam.padsof.objetoreproducible.Cancion;
import es.uam.padsof.sistema.Sistema;
import pads.musicPlayer.Mp3Player;
import pads.musicPlayer.exceptions.Mp3PlayerException;

public class Mp3PlayerDemo {

	public static void main(String[] args) throws Mp3PlayerException, InterruptedException, IOException {
		Cancion c1=new Cancion("inewiw",Sistema.getInstance().getUsuarioEnSesion(),"chicle3.mp3");
		System.out.println("Es mp3 valido : "+Mp3Player.isValidMp3File("invalid.mp3"));
		System.out.println("Es mp3 valido : "+Mp3Player.isValidMp3File(c1.getRuta()));
		Mp3Player player = new Mp3Player();
		player.add("hive.mp3", "chicle3.mp3");
		System.out.println("Duracion : "+Mp3Player.getDuration("chicle3.mp3")+" secs");
		player.play();
		Thread.sleep(12000);
		player.stop();
		System.out.println("Stopped!");
		System.out.println("Playing again! (from the beginning)");
		player.play();
		Thread.sleep(2000);
		player.stop();
		System.out.println("Finished!");
	}

}
