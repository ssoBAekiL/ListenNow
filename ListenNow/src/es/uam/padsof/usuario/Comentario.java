package es.uam.padsof.usuario;
import java.util.*;

/**
 * 
 */
public class Comentario {
	
	/**
	 * 
	 */
	private String texto;

	/**
	 * 
	 */
	private UsuarioRegistrado autor;

	/**
	 * 
	 */
	private Date fecha;

	/**
	 * 
	 */
	private int valoracion;

	/**
	 * Default constructor
	 */
	public Comentario(String texto, UsuarioRegistrado autor, Date fecha, int valoracion) {
		this.texto=texto;
		this.autor=autor;
		this.fecha=fecha;
		this.valoracion=valoracion;
	}






}