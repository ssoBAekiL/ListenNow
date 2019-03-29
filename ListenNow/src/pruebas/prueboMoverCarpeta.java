package pruebas;

import java.io.IOException;

import es.uam.padsof.objetoreproducible.Cancion;
import es.uam.padsof.sistema.Sistema;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * 
 * @author carlosmiret
 *
 */
public class prueboMoverCarpeta {

	/**
	 * Funcion main
	 * @param args
	 * @throws Mp3PlayerException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, Mp3PlayerException {
		// TODO Auto-generated method stub
		Cancion c1 = new Cancion("Cancion 1", Sistema.getInstance().getUsuarioEnSesion(), "chicle3.mp3");
		c1.moverCancionASistema();
		System.out.println("CORRECTO\n");
	}

}
