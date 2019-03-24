package es.uam.padsof.objetoreproducible;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import es.uam.padsof.sistema.Notificacion;
import es.uam.padsof.sistema.Sistema;
import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.Mp3Player;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * @author Juli�n Espada, Pablo Borrelli y Carlos Miret
 * 
 * Esta clase se encarga de gestionar el objeto Cancion 
 */
public class Cancion extends ObjetoComentable{
	private int id;
	private int nreproducciones;
	private boolean mas18;
	private boolean notificada_plagio;
	private boolean pendiente_verificacion;
	private boolean aceptada;
	private boolean rechazada;
	private boolean marcada_plagio;
	
	/**
	 * 
	 * @param id Este es el id que tendr� la canci�n
	 * @param ruta Este es el nombre de la ruta del fichero que contendr� la canci�n
	 * @param nrep Este es el n�mero de reproducciones de la canci�n
	 * @param m18 Este es el boolean que marcar� si la canci�n es apta para mayores de 18
	 * @param val Este es el boolean que marcar� la canci�n como v�lida o no
	 * @param plag Este es el boolean que marcar� la canci�n como plagio
	 * Este m�todo es el constructor del objeto Cancion
	 */
	public Cancion (String titulo, UsuarioRegistrado autor, String ruta)throws IOException, Mp3PlayerException {
		super(titulo, autor,ruta);
		this.id=Sistema.getInstance().getNumeroCanciones()+1;
		this.nreproducciones=0;
		this.mas18=false;
		this.setAceptada(false);
		this.setNotificada_plagio(false);
		this.setPendiente_verificacion(false);
		this.setRechazada(false);
		this.fechaRechazo=null;
		this.setMarcada_plagio(false);
		Sistema.getInstance().getCancionesValidar().add(this);
	}
	
	
	/**
	 * 
	 */
	private LocalDate fechaRechazo;
	
	
	/**
	 * @return
	 */
	public LocalDate getFechaRechazo() {
		return this.fechaRechazo;
	}

	/**
	 * 
	 * @return id 
	 * 
	 * Esta clase se encarga de devolver el id de una cancion
	 */
	public int getId() {
		return id;
	}
	
	
	public void incrementaReproducciones() {
		this.nreproducciones++;
	}

	/**
	 * 
	 * @param id El id nuevo que queremo que tenga la canci�n
	 * @return void
	 * 
	 * Esta clase modificar� el id de la canci�n
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return nreproducciones
	 * 
	 * Este m�todo devuelve el n�mero de reproducciones de la canci�n
	 */
	public int getNreproducciones() {
		return nreproducciones;
	}
	
	/**
	 * 
	 */
	public void borradoTrasTercerDia(){
		LocalDate fecha1 = LocalDate.now().minusDays(3);
		if(this.rechazada=true && fecha1.isAfter(this.fechaRechazo)) {
			Sistema.getInstance().getCancionesRechazadas().remove(this);
			Sistema.getInstance().borrarReproducible(this);
		}
	}
	
	/**
	 * 
	 */
	public void rechazar() {
		if(this.marcada_plagio) {
			this.rechazada=true;
			this.fechaRechazo=LocalDate.now();
			Sistema.getInstance().getCancionesValidar().remove(this);
			Sistema.getInstance().getCancionesRechazadas().add(this);
		}
	}

	/**
	 * Metodo utilizado para el test, para comprobar 
	 * el correcto funcionamiento de el borrado de una 
	 * cancion tras 3 dias
	 */
	public void modificarFechaRechazo() {
		this.fechaRechazo=LocalDate.now().minusDays(5);
	}
	
	/**
	 * 
	 * @param nreproducciones Este es el nuevo n�mero de reproducciones para la canci�n
	 * @return void
	 * 
	 * Este m�todo se encarga de modificar el n�mero de reproducciones de la canci�n
	 */
	public void setNreproducciones(int nreproducciones) {
		this.nreproducciones = nreproducciones;
	}

	/**
	 * 
	 * @return mas18
	 * 
	 * Este m�todo devuelve el boolean que tiene la canci�n que indica si es apto para mayores de 18
	 */
	public boolean isMas18() {
		return mas18;
	}

	/**
	 * 
	 * @param mas18 el nuevo boolean que tendr� el atributo mas18 de la cancion
	 * 
	 * Este m�todo modifica el boolean mas18 de la clase Cancion
	 */
	public void setMas18(boolean mas18) {
		this.mas18 = mas18;
	}
	
	/**
	 * @return the comentarios
	 */
	public ArrayList<Comentario> getComentarios() {
		return comentarios;
	}

	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentarios(ArrayList<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
	/**
	 * Metodo que permite al usuario admin validar + 18 una cancion pasada por parametro
	 * @param cancion
	 */
	public boolean validarCancion18() {
		/*for(int i=0;i<Sistema.getNumUsuarios();i++) {
			if(Sistema.getInstance().getUsuario(i).isAdmin()==true) {
=======
	public void validarCancion18(Cancion cancion) {
		for(int i=0;i<Sistema.getInstance().getNumUsuarios();i++) {
			if(Sistema.getInstance().getUsuarioItera(i).isAdmin()==true) {
>>>>>>> branch 'master' of https://github.com/ssoBAekiL/ListenNow.git
				this.setMas18(true);
				Sistema.getInstance().getCancionesValidadas().add(cancion);
			}
		}
		return;*/
		if(validarCancion() == true) {
			mas18 = true;
			return true;
		}
		return false;
	}
	
	/**
	 * Metodo que permite al usuario admin validar una cancion
	 * @param cancion
	 */
	public boolean validarCancion() {
		/*********CHEQUEO SI EL USUARIO EN SESION ES EL ADMIN*******/
		if(Sistema.getInstance().getUsuarioEnSesion().equals(Sistema.getInstance().getAdmin()) == true &&
				 Sistema.getInstance().getCancionesValidar().contains(this)) {
			Sistema.getInstance().getCancionesValidadas().add(this);
			Sistema.getInstance().getCancionesValidar().remove(this);
			aceptada = true;
			pendiente_verificacion = false;
			Sistema.getInstance().setNotificaciones(new Notificacion(this, this.autor.getSeguidores()));
			autor.anadirCancion(this);
			return true;
		}
		return false;
	}
	
	
	

	/* (non-Javadoc)
	 * @see es.uam.padsof.objetoreproducible.ObjetoReproducible#reproducir()
	 */
	public void reproducir() throws FileNotFoundException, Mp3PlayerException, InterruptedException{
		if(Mp3Player.isValidMp3File(ruta)==true) {
			if(Sistema.getInstance().getConectado()==true &&(Sistema.getInstance().getUsuarioEnSesion().puedeReproducir())) {
				player.add(ruta);
				player.play();/*Reproducimos la cancion*/
				Sistema.getInstance().getUsuarioEnSesion().incrementaReproducciones();/*se incrementa el numero de reproducciones del usuario en sesion*/
				this.autor.incrementaNumReproduccionesPropias();/*Se incrementa el numero de reproducciones del autor*/
				if(this.autor.EsPremium()==false && this.autor.getNumReproduccionDeCancionesPropias()>100)
					this.autor.promocionarUsuario();/*PROMOCIONAMOS AL USUARIO EN CASO DE SUPERAR LAS 100 REPRODUCCIONES*/
				Thread.sleep((long)Mp3Player.getDuration(ruta)*1000);
				this.nreproducciones++;/*AUMENTAMOS EL NUMERO DE REPRODUCCIONES DE ESTA CANCION*/
			}
			else if(Sistema.getInstance().getConectado()==false && Sistema.getInstance().getReproduccionesNoRegistrados()<Sistema.getInstance().getnRepAnonimas()){
				player.add(ruta);
				player.play();/*Reproducimos la cancion*/
				Thread.sleep((long)Mp3Player.getDuration(ruta)*1000);
				this.autor.incrementaNumReproduccionesPropias();/*Se incrementa el numero de reproducciones del autor, para poder determinar una futura suscripcion Premium*/
				if(this.autor.EsPremium()==false && this.autor.getNumReproduccionDeCancionesPropias()>100)
					this.autor.promocionarUsuario();/*PROMOCIONAMOS AL USUARIO EN CASO DE SUPERAR LAS 100 REPRODUCCIONES*/
				Sistema.getInstance().incremetareproduccionesNoRegistrados();
				this.nreproducciones++;/*AUMENTAMOS EL NUMERO DE REPRODUCCIONES DE ESTA CANCION*/
			}
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see es.uam.padsof.objetoreproducible.ObjetoReproducible#pararReproduccion()
	 */
	public void pararReproduccion()throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		if(Mp3Player.isValidMp3File(ruta)==true) {
			player.add(ruta);
			player.stop();
		}
	}
	
	
	/**
	 * Metodo que permite a un usuario notificar ccomo plagio una cancion
	 * @param cancion
	 */
	public void notificarPlagio() {
		this.setNotificada_plagio(true);
		Sistema.getInstance().getCancionesNotificadas().add(this);
		Sistema.getInstance().setNotificaciones(new Notificacion(this));
	}
	
	public boolean revisarNotificacion() {
		if(Sistema.getInstance().getUsuarioEnSesion().equals(Sistema.getInstance().getAdmin())) {
			this.setNotificada_plagio(false);
			return true;
		}
		return false;
	}
	
	
	/**
	 * @return
	 */
	public boolean marcarComoPlagio() {
		/*SI EL USUARIO EN SESION ES EL ADMIN*/
		if(Sistema.getInstance().getUsuarioEnSesion().equals(Sistema.getInstance().getAdmin())) {
				Sistema.getInstance().getCancionesNotificadas().add(this);
				this.setMarcada_plagio(true);
				return true;
		}
		return false;
	}
	
	
	/* (non-Javadoc)
	 * @see es.uam.padsof.objetoreproducible.ObjetoComentable#anadirComentario(es.uam.padsof.objetoreproducible.Comentario)
	 */
	public boolean anadirComentario(Comentario c) {
		if(Sistema.getInstance().getUsuarioEnSesion().puedeComentar()) {
			super.comentarios.add(c);
			return true;
		}
		return false;
	}
	
	
	
	
	
	/**************************************/

	public boolean isNotificada_plagio() {
		return notificada_plagio;
	}

	public void setNotificada_plagio(boolean notificada_plagio) {
		this.notificada_plagio = notificada_plagio;
	}

	public boolean isPendiente_verificacion() {
		return pendiente_verificacion;
	}

	public void setPendiente_verificacion(boolean pendiente_verificacion) {
		this.pendiente_verificacion = pendiente_verificacion;
	}

	public boolean isAceptada() {
		return aceptada;
	}

	public void setAceptada(boolean aceptada) {
		this.aceptada = aceptada;
	}

	public boolean isMarcada_plagio() {
		return marcada_plagio;
	}

	public void setMarcada_plagio(boolean marcada_plagio) {
		this.marcada_plagio = marcada_plagio;
	}

	public boolean isRechazada() {
		return rechazada;
	}

	public void setRechazada(boolean rechazada) {
		this.rechazada = rechazada;
	}

	public String toString(){
		return "Autor: "+this.getAutor()+"\n"+"Titulo: "+this.getTitulo()+"\n";
	}
	
	/************************************************/
	public void setReproucciones(int reproducciones) {
		this.nreproducciones = reproducciones;
	}

	
}