package es.uam.padsof.sistema;

import java.util.*;
import es.uam.padsof.objetoreproducible.*;
import es.uam.padsof.usuario.*;

public class Notificacion {
	
	public enum TipoNotificacion {PLAGIO, NUEVACANCION, NUEVOALBUM}
	
	private TipoNotificacion tipo;
	private ArrayList<String> texto = new ArrayList<String>();
	private Cancion cancionNotificada;
	private Album albumNotificado;
	private ArrayList<UsuarioRegistrado> usuariosNotificados = new ArrayList<UsuarioRegistrado>();
	
	public Notificacion (TipoNotificacion tipo, Cancion cancionNotificada) {
		this.tipo = tipo;
		if (tipo == tipo.PLAGIO)
			texto.add("La cancion " + cancionNotificada.getTitulo() + " ha sido notificada por plagio. ");
		else if (tipo == tipo.NUEVACANCION)
			texto.add("El usuario " + cancionNotificada.getAutor() + " ha añadido una nueva cancion con titulo: " + cancionNotificada.getTitulo() + ". ");
		
		this.cancionNotificada = cancionNotificada;
	}
	public Notificacion (TipoNotificacion tipo, Album albumNotificado) {
		this.tipo = tipo;
		if (tipo == tipo.NUEVOALBUM)
			texto.add("El usuario " + albumNotificado.getAutor() + " ha añadido un nuevo album con titulo: " + albumNotificado.getTitulo() + ". ");
		this.albumNotificado = albumNotificado;
	}

}
