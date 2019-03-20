package es.uam.padsof.usuario;

import java.io.FileNotFoundException;
import java.io.IOException;

import es.uam.padsof.objetoreproducible.Cancion;
import es.uam.padsof.sistema.Sistema;
import pads.musicPlayer.Mp3Player;
import pads.musicPlayer.exceptions.Mp3PlayerException;

public class pruebaFollow {

	public static void main(String[] args) throws Mp3PlayerException, IOException {
		
		Mp3Player player = new Mp3Player();
		Cancion c = new Cancion("este es titulo","este es autor","hive.mp3");
		
		player.add(c.getRuta());
		player.play();
		
		
		/*CREAMOS UN UNICO SISTEMA*/
		Sistema sis=Sistema.getInstance();
		
		UsuarioRegistrado u1=new UsuarioRegistrado("12aas3","carlos","hola",true,null,false);
		sis.addUsuario(u1);
		UsuarioRegistrado u2=new UsuarioRegistrado("12sddddds3","pablo","hola",true,null,false);
		sis.addUsuario(u2);

		u1.follows(u2);
			
	}

}
