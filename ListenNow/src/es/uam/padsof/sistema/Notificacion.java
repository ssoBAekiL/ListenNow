package es.uam.padsof.sistema;

import java.io.Serializable;
import java.util.*;

import es.uam.padsof.objetoreproducible.*;
import es.uam.padsof.usuario.*;

/**
 * Clase notificacion, recoge todos las características de una notificacion, la cual puede ser de tres tipos
 * @author Carlos Miret, Pablo Borrelli y Julian Espada
 *
 */
public class Notificacion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public enum TipoNotificacion {PLAGIO, NUEVACANCION, NUEVOSEGUIDOR}
	/**
	 * Tipo de notificacion que es un enumerado
	 */
	private TipoNotificacion tipo = null; 
	/**
	 * Array de usuarios notificados
	 */
	private ArrayList<UsuarioRegistrado> usuariosNotificados = new ArrayList<UsuarioRegistrado>();
	/**
	 * Cuerpo de la notificacion, es decir el texto
	 */
	private String texto;
	
	

	/**
	 * Metodo constructor para una cancion otificada como plagio
	 * @param cancionNotificada
	 */
	public Notificacion (Cancion cancionNotificada) {
		tipo = TipoNotificacion.PLAGIO;
		texto = "La cancion " + cancionNotificada.getTitulo() + " del autor " + cancionNotificada.getAutor() + " ha sido notificada por plagio.";
		usuariosNotificados.add(Sistema.getInstance().getAdmin());	
	}
	
	
	
	/**
	 * Metodo constructor de un tipo de notificacion determinada, la de nueva cancion que avisa a los seguidores del autor de la cancion
	 * @param cancionNotificada
	 * @param usuariosNotificados
	 */
	public Notificacion (Cancion cancionNotificada, ArrayList<UsuarioRegistrado> usuariosNotificados) {
		tipo = TipoNotificacion.NUEVACANCION;
		texto = "El usuario " + cancionNotificada.getAutor() + " ha añadido la cancion " + cancionNotificada.getTitulo();
		this.usuariosNotificados = usuariosNotificados;
	}
	
	
	
	
	/**
	 * Metodo constructor de un tipo de notificacion determinada, la de nueva cancion que avisa al seguido de que tiene un nuevo seguidor
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
	 * @return String Datos caracteristicos de la clase Notificacion
	 */
	public String toString() {
		if (tipo == TipoNotificacion.PLAGIO)
			return "NOTIFICACION POR PLAGIO: " + texto + "\n";
		else if (tipo == TipoNotificacion.NUEVACANCION)
			return "NOTIFICACION NUEVA CANCION: " + texto + "\n";
		else if (tipo == TipoNotificacion.NUEVOSEGUIDOR)
			return "NOTIFICACION NUEVO SEGUIDOR: " + texto + "\n";
		return null;
	}



	/**
	 * MEtodo getter del tipo de notificacion
	 * @return
	 */
	public TipoNotificacion getTipo() {
		return tipo;
	}



	/**
	 * Metodo setter del tipo de notificacion
	 * @param tipo
	 */
	public void setTipo(TipoNotificacion tipo) {
		this.tipo = tipo;
	}



	/**
	 * Metodo getter del texto de la notificacion
	 * @return
	 */
	public String getTexto() {
		return texto;
	}



	/**
	 * @param texto
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}



	/**
	 * Metodo setter de los usuarios notificados
	 * @param usuariosNotificados
	 */
	public void setUsuariosNotificados(ArrayList<UsuarioRegistrado> usuariosNotificados) {
		this.usuariosNotificados = usuariosNotificados;
	}

}
