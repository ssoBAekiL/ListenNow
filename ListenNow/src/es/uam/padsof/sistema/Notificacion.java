package es.uam.padsof.sistema;

import java.util.*;
import es.uam.padsof.objetoreproducible.*;
import es.uam.padsof.usuario.*;

public class Notificacion {
	
	public enum TipoNotificacion {PLAGIO, NUEVACANCION, NUEVOSEGUIDOR}
	
	private ArrayList<UsuarioRegistrado> usuariosNotificados = new ArrayList<UsuarioRegistrado>();
	
	
	/*NOTIFICAR SOBRE UNA NUEVA CANCION SUBIDA*/
	public Notificacion (TipoNotificacion tipo, Cancion cancionNotificada, UsuarioRegistrado usr) {
		if (tipo == TipoNotificacion.PLAGIO)
			System.out.println("La cancion " + cancionNotificada.getTitulo() + " ha sido notificada como plagio. ");
		else if (tipo == TipoNotificacion.NUEVACANCION)
			System.out.println("El usuario " + cancionNotificada.getAutor() + " ha añadido una nueva cancion con titulo: " + cancionNotificada.getTitulo() + ". ");
		
		Sistema.getInstance().getCancionesNotificadas().add(cancionNotificada);
		//this.usuariosNotificados = usuariosNotificados;
	}
	
	
	/*NOTIFICACION DE LA ACCION DE SEGUIMIENTO*/
	public Notificacion (TipoNotificacion tipo, UsuarioRegistrado u) {
		if(tipo== TipoNotificacion.NUEVOSEGUIDOR)
			System.out.println(u+" tienes un nuevo seguidor!");
	}

	
//	public String mostrarNotificacion() {
//		return "NOTIFICACION: " + texto;
//	}
	
	/**
	 * @return
	 */
	public ArrayList<UsuarioRegistrado> getUsuariosNotificados() {
		return usuariosNotificados;
	}


	
	
	

}
