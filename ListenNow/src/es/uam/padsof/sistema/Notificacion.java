package es.uam.padsof.sistema;

import java.io.Serializable;
import java.util.*;
import es.uam.padsof.objetoreproducible.*;
import es.uam.padsof.usuario.*;

/**
 * Clase notificacion, recoge todos las características e una notificacion, la cual puede ser de tres tipos
 * @author Carlos Miret, Pablo Borrelli y Julian Espada
 *
 */
public class Notificacion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public enum TipoNotificacion {PLAGIO, NUEVACANCION, NUEVOSEGUIDOR}
	
	private TipoNotificacion tipo = null; 
	private ArrayList<UsuarioRegistrado> usuariosNotificados = new ArrayList<UsuarioRegistrado>();
	private String texto;
	
	
	/**
	 * @param cancionNotificada
	 */
	public Notificacion (Cancion cancionNotificada) {
		tipo = TipoNotificacion.PLAGIO;
		texto = "La cancion " + cancionNotificada.getTitulo() + " del autor " + cancionNotificada.getAutor() + " ha sido notificada por plagio.";
		usuariosNotificados.add(Sistema.getInstance().getAdmin());	
	}
	
	
	
	/**
	 * Metodo constructor de un tipo de notificacion determinado
	 * @param cancionNotificada
	 * @param usuariosNotificados
	 */
	public Notificacion (Cancion cancionNotificada, ArrayList<UsuarioRegistrado> usuariosNotificados) {
		tipo = TipoNotificacion.NUEVACANCION;
		texto = "El usuario " + cancionNotificada.getAutor() + " ha añadido la cancion " + cancionNotificada.getTitulo();
		this.usuariosNotificados = usuariosNotificados;
	}
	
	
	
	
	/**
	 * Metodo constructor de un tipo de notificacion determinado
	 * @param seguidor
	 * @param usuarioNotificado
	 */
	public Notificacion (UsuarioRegistrado seguidor, UsuarioRegistrado usuarioNotificado) {
		tipo = TipoNotificacion.NUEVOSEGUIDOR;
		texto = "El usuario " + seguidor.getNombre() + " ha empezado a seguirte.";
		usuariosNotificados.add(usuarioNotificado);
	}

	/**
	 * Funcion getter de los usuarios notidficados
	 * @return todos los usuarios notificados hasta el momento
	 */
	public ArrayList<UsuarioRegistrado> getUsuariosNotificados() {
		return usuariosNotificados;
	}
	
	
	/**
	 * Metodo que se ocupa del imprimir los datos característicos de la notificacion
	 * 
	 */
	public String toString() {
		if (tipo == TipoNotificacion.PLAGIO)
			return "NOTIFICACION POR PLAGIO: " + texto;
		else if (tipo == TipoNotificacion.NUEVACANCION)
			return "NOTIFICACION NUEVA CANCION: " + texto;
		else if (tipo == TipoNotificacion.NUEVOSEGUIDOR)
			return "NOTIFICACION NUEVO SEGUIDOR: " + texto;
		return null;
	}



	public TipoNotificacion getTipo() {
		return tipo;
	}



	public void setTipo(TipoNotificacion tipo) {
		this.tipo = tipo;
	}



	public String getTexto() {
		return texto;
	}



	public void setTexto(String texto) {
		this.texto = texto;
	}



	public void setUsuariosNotificados(ArrayList<UsuarioRegistrado> usuariosNotificados) {
		this.usuariosNotificados = usuariosNotificados;
	}

}
