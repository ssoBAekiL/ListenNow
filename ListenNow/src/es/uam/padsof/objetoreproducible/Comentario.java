package es.uam.padsof.objetoreproducible;
import java.time.LocalDate;
import java.util.*;

import es.uam.padsof.usuario.UsuarioRegistrado;

/**
 * 
 */
public class Comentario    {
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
	private LocalDate fecha;

	/**
	 * 
	 */
	private int valoracion;
	


	/**
	 * Default constructor
	 */
	public Comentario(String texto, UsuarioRegistrado autor, LocalDate fecha, int valoracion) {
		this.texto=texto;
		this.autor=autor;
		this.fecha=fecha;
		this.valoracion=valoracion;
	}

	
	/**
	 * @return the texto
	 */
	public String getTexto() {
		return texto;
	}

	/**
	 * @param texto the texto to set
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}

	/**
	 * @return the autor
	 */
	public UsuarioRegistrado getAutor() {
		return autor;
	}

	/**
	 * @param autor the autor to set
	 */
	public void setAutor(UsuarioRegistrado autor) {
		this.autor = autor;
	}

	/**
	 * @return the fecha
	 */
	public LocalDate getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the valoracion
	 */
	public int getValoracion() {
		return valoracion;
	}

	/**
	 * @param valoracion the valoracion to set
	 */
	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}

    


}