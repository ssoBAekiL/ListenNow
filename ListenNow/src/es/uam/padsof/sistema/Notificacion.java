package es.uam.padsof.sistema;

import java.util.*;
import es.uam.padsof.objetoreproducible.*;
import es.uam.padsof.usuario.*;

public class Notificacion {
	
	public enum TipoNotificacion {PLAGIO, NUEVACANCION, NUEVOSEGUIDOR}
	
	private TipoNotificacion tipo;
	private ArrayList<String> texto = new ArrayList<String>();
	private ArrayList<Cancion> cancionesNotificadas = new ArrayList<Cancion>();
	private ArrayList<UsuarioRegistrado> usuariosNotificados = new ArrayList<UsuarioRegistrado>();
	
	/*NOTIFICAR SOBRE UNA NUEVA CANCION SUBIDA*/
	public Notificacion (TipoNotificacion tipo, Cancion cancionNotificada, ArrayList<UsuarioRegistrado> usuariosNotificados) {
		this.tipo = tipo;
		if (tipo == TipoNotificacion.PLAGIO)
			texto.add("La cancion " + cancionNotificada.getTitulo() + " ha sido notificada como plagio. ");
		else if (tipo == TipoNotificacion.NUEVACANCION)
			texto.add("El usuario " + cancionNotificada.getAutor() + " ha añadido una nueva cancion con titulo: " + cancionNotificada.getTitulo() + ". ");
		
		cancionesNotificadas.add(cancionNotificada);
		this.usuariosNotificados = usuariosNotificados;
	}
	
	public Notificacion (TipoNotificacion tipo, UsuarioRegistrado u) {
		this.tipo = tipo;
		if (tipo == TipoNotificacion.PLAGIO)
			texto.add("La cancion " + cancionNotificada.getTitulo() + " ha sido notificada como plagio. ");
		else if (tipo == TipoNotificacion.NUEVACANCION)
			texto.add("El usuario " + cancionNotificada.getAutor() + " ha añadido una nueva cancion con titulo: " + cancionNotificada.getTitulo() + ". ");
		else if(tipo== TipoNotificacion.NUEVOSEGUIDOR)
			texto.add(u+" tienes un nuevo seguidor!");
	}

	
	public String mostrarNotificacion() {
		return "NOTIFICACION: " + texto;
	}
	
	/**
	 * @return
	 */
	public ArrayList<UsuarioRegistrado> getUsuariosNotificados() {
		return usuariosNotificados;
	}
	/**
	 * @return the cancionesNotificadas
	 */
	public ArrayList<Cancion> getCancionesNotificadas() {
		return cancionesNotificadas;
	}
	/**
	 * @param cancionesNotificadas the cancionesNotificadas to set
	 */
	public void setCancionesNotificadas(ArrayList<Cancion> cancionesNotificadas) {
		this.cancionesNotificadas = cancionesNotificadas;
	}
	
	
	

}
