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
	
	public Notificacion (TipoNotificacion tipo, Cancion cancionNotificada, ArrayList<UsuarioRegistrado> usuariosNotificados) {
		this.tipo = tipo;
		if (tipo == TipoNotificacion.PLAGIO)
			texto.add("La cancion " + cancionNotificada.getTitulo() + " ha sido notificada por plagio. ");
		else if (tipo == TipoNotificacion.NUEVACANCION)
			texto.add("El usuario " + cancionNotificada.getAutor() + " ha añadido una nueva cancion con titulo: " + cancionNotificada.getTitulo() + ". ");
		
		this.cancionNotificada = cancionNotificada;
		this.usuariosNotificados = usuariosNotificados;
	}
	public Notificacion (TipoNotificacion tipo, Album albumNotificado) {
		this.tipo = tipo;
		if (tipo == TipoNotificacion.NUEVOALBUM)
			texto.add("El usuario " + albumNotificado.getAutor() + " ha añadido un nuevo album con titulo: " + albumNotificado.getTitulo() + ". ");
		this.albumNotificado = albumNotificado;
	}
	
	public String mostrarNotificacion() {
		return "NOTIFICACION: " + texto;
	}
	
	public ArrayList<UsuarioRegistrado> getUsuariosNotificados() {
		return usuariosNotificados;
	}

}
