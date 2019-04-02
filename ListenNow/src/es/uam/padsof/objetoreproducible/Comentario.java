package es.uam.padsof.objetoreproducible;
import java.io.Serializable;
import java.time.LocalDate;

import es.uam.padsof.usuario.UsuarioRegistrado;

/**
 * 
 * Clase comentario, que posee todos las caracteristicas propias 
 * de un comentario al igual que funciones que trabajan sobre este
 * @author Carlos Miret, Julian Espada y Pablo Borrelli
 * 
 */
public class Comentario implements Serializable {
	/**
	 * ID de serializacion
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Cuerpo o texto del comentario
	 */
	private String texto;

	/**
	 * autor de comentario
	 */
	private UsuarioRegistrado autor;

	/**
	 * fecha de
	 */
	private LocalDate fecha;

	/**
	 * valoracion de la cancion a traves de un comentario
	 */
	private int valoracion;
	


	/**
	 * Metodo constructor de la clase comentario
	 * @param texto
	 * @param autor
	 * @param fecha
	 * @param valoracion
	 */
	public Comentario(String texto, UsuarioRegistrado autor, LocalDate fecha, int valoracion) {
		this.texto=texto;
		this.autor=autor;
		this.fecha=fecha;
		this.valoracion=valoracion;
	}
	
	
	/**
	 * Funcion que muestra por pantalla todos los atributos caracteristicos de un comentario
	 * 
	 * @return cadena de caracteres
	 */
	@Override
	public String toString() {
		return "Comentario [texto=" + texto + ", autor=" + autor + ", fecha=" + fecha + ", valoracion=" + valoracion
				+ "]";
	}

	/**
	 * Funcion getter del texto del comentario
	 * @return the texto
	 */
	public String getTexto() {
		return texto;
	}


	/**
	 * Funcion getter del autor del comentario
	 * @return the autor
	 */
	public UsuarioRegistrado getAutor() {
		return autor;
	}



	/**
	 * Fecha en la que se publico el comentario
	 * @return the fecha
	 */
	public LocalDate getFecha() {
		return fecha;
	}


	/**
	 * Getter de valoracion del comentario
	 * @return the valoracion
	 */
	public int getValoracion() {
		return valoracion;
	}

	/**
	 * 
	 * @param valoracion the valoracion to set
	 */
	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}
   

}
