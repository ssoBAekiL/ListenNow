package es.uam.padsof.sistema;

import java.util.*;
import es.uam.padsof.objetoreproducible.*;
import es.uam.padsof.usuario.*;

public class Notificacion {
	
	public enum TipoNotificacion {PLAGIO, NUEVACANCION, NUEVOSEGUIDOR}
	
	private TipoNotificacion tipo = null; 
	private ArrayList<UsuarioRegistrado> usuariosNotificados = new ArrayList<UsuarioRegistrado>();
	private String texto;
	
	
	public Notificacion (Cancion cancionNotificada) {
		tipo = TipoNotificacion.PLAGIO;
		texto = "La cancion " + cancionNotificada.getTitulo() + " del autor " + cancionNotificada.getAutor() + " ha sido notificada por plagio.";
		usuariosNotificados.add(Sistema.getInstance().getAdmin());	
	}
	
	public Notificacion (Cancion cancionNotificada, ArrayList<UsuarioRegistrado> usuariosNotificados) {
		tipo = TipoNotificacion.NUEVACANCION;
		texto = "El usuario " + cancionNotificada.getAutor() + " ha añadido la cancion " + cancionNotificada.getTitulo();
		this.usuariosNotificados = usuariosNotificados;
	}
	
	public Notificacion (UsuarioRegistrado seguidor, UsuarioRegistrado usuarioNotificado) {
		tipo = TipoNotificacion.NUEVOSEGUIDOR;
		texto = "El usuario " + seguidor.getNombre() + " ha empezado a seguirte.";
		usuariosNotificados.add(usuarioNotificado);
	}

	/**
	 * @return
	 */
	public ArrayList<UsuarioRegistrado> getUsuariosNotificados() {
		return usuariosNotificados;
	}
	
	public String toString() {
		if (tipo == TipoNotificacion.PLAGIO)
			return "NOTIFICACION POR PLAGIO: " + texto;
		else if (tipo == TipoNotificacion.NUEVACANCION)
			return "NOTIFICACION NUEVA CANCION: " + texto;
		else if (tipo == TipoNotificacion.NUEVOSEGUIDOR)
			return "NOTIFICACION NUEVO SEGUIDOR: " + texto;
		return null;
	}

}
