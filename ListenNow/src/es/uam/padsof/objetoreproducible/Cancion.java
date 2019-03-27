package es.uam.padsof.objetoreproducible;

import java.nio.file.*;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;

import es.uam.padsof.sistema.Notificacion;
import es.uam.padsof.sistema.Sistema;
import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.Mp3Player;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * @author Julian Espada, Pablo Borrelli y Carlos Miret
 * 
 * Esta clase se encarga de gestionar todas
 * las caracteristicas referentes a cancion
 */
public class Cancion extends ObjetoComentable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ID de la cancion
	 */
	private long id;
	/**
	 * Numero de reproducciones
	 */
	private int nreproducciones;
	/**
	 * Boolean que devuelve true si es aceptada +18
	 */
	private boolean aceptada_mas18;
	/**
	 * Flag de cancion notificada como plagio
	 */
	private boolean notificada_plagio;
	/**
	 * Flag de cancion que chequea si esta pendiente de verificacion
	 */
	private boolean pendiente_verificacion;
	/**
	 * Aceptada
	 */
	private boolean aceptada;
	/**
	 * Rechazada
	 */
	private boolean rechazada;
	
	/**
	 * Duracion de la cancion
	 */
	private double duracion;
	/**
	 * Marcada como plagio (flag)
	 */
	private boolean marcada_plagio;
	

	/**
	 * Metodo constructor de la clase Cancion
	 * 
	 * @param titulo
	 * @param autor
	 * @param ruta
	 * @throws IOException
	 * @throws Mp3PlayerException
	 */
	public Cancion (String titulo, UsuarioRegistrado autor, String ruta)throws IOException, Mp3PlayerException {
		super(titulo, autor,ruta);
		this.id= Sistema.getInstance().getGenerador().getAndIncrement();
		this.nreproducciones=0;
		this.setAceptada_mas18(false);
		this.setAceptada(false);
		this.setNotificada_plagio(false);
		this.setPendiente_verificacion(false);
		this.setRechazada(false);
		this.fechaRechazo=null;
		this.setMarcada_plagio(false);
		this.ruta=ruta;
		this.duracion=Mp3Player.getDuration(this.ruta);
		//meter copiar cancion
	}

	
	
	/**
	 * Fecha de rechazo de la cancion
	 */
	private LocalDate fechaRechazo;
	
	
	/**
	 * Metodo getter de la fecha de rechazo
	 * @return fecha del rechazo
	 */
	public LocalDate getFechaRechazo() {
		return this.fechaRechazo;
	}

	/**
	 * Esta clase se encarga de devolver el id de una cancion
	 * @return id 
	 */
	public long getId() {
		return id;
	}
	
	
	/**
	 * Metodo getter de la duracion de la cancion 
	 * @return duracion de la cancion
	 */
	public double getDuracion(){
		return this.duracion;
	}
	
	/**
	 * Metodo que incrementa las reproducciones de una cancion de
	 */
	public void incrementaReproducciones() {
		this.nreproducciones++;
	}

	/**
	 * Esta clase modificara el id de la cancion
	 * @param id El id nuevo que queremos que tenga la cancion
	 * @return void
	 * 
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Este metodo devuelve el numero de reproducciones de la cancion
	 * @return nreproducciones
	 * 
	 */
	public int getNreproducciones() {
		return nreproducciones;
	}
	
	/**
	 * Funcion que cumple el requisito de borrado tras ser rechazada hace 3 dias
	 */
	public void borradoTrasTercerDia(){
		LocalDate fecha1 = LocalDate.now().minusDays(3);
		if(this.rechazada==true && fecha1.isAfter(this.fechaRechazo) ) {
			Sistema.getInstance().borrarReproducible(this);
		}
	}
	
	/**
	 * Metodo rechazar, que actuara sobre el objeto cancion y actualizara los diferentes arrays de
	 * estados de canciones en el sistema 
	 * @return true en caso correcto
	 */
	public boolean rechazar() {
		if(Sistema.getInstance().getUsuarioEnSesion()==Sistema.getInstance().getAdmin()) {
			this.rechazada=true;
			this.fechaRechazo=LocalDate.now();
			Sistema.getInstance().getCancionesValidar().remove(this);
			if (Sistema.getInstance().getCancionesRechazadas().contains(this) == false)
				Sistema.getInstance().getCancionesRechazadas().add(this);
			return true;
		}
		return false;
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
	 * Este metodo devuelve el boolean que tiene la cancion que indica si es apto para mayores de 18
	 * @return aceptada_mas18
	 */
	public boolean isAceptada_mas18() {
		return aceptada_mas18;
	}

	/**
	 * Este metodo modifica el boolean aceptada_mas18 de la clase Cancion
	 * @param aceptada_mas18 el nuevo boolean que tendra el atributo aceptada_mas18 de la cancion
	 * 
	 */
	public void setAceptada_mas18(boolean aceptada_mas18) {
		this.aceptada_mas18 = aceptada_mas18;
	}
	
	/**
	 * @return the comentarios
	 */
	public ArrayList<Comentario> getComentarios() {
		return comentarios;
	}

	
	/**
	 * Metodo que permite al usuario admin validar + 18 una cancion pasada por parametro
	 * @param cancion
	 * @return true en caso correcto
	 */
	public boolean validarCancion18() {
		if(validarCancion() == true) {
			aceptada_mas18 = true;
			return true;
		}
		return false;
	}
	
	/**
	 * Metodo que permite al usuario admin validar una cancion y unicamente al admin
	 * @return true en caso correcto
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
	
	
	


	/**
	 * Metodo que reproduce una cancion, dicha cancion sera reproducida si 
	 * satisface diferentes condiciones (estrictas)
	 */
	public void reproducir() throws FileNotFoundException, Mp3PlayerException, InterruptedException{
		if(Mp3Player.isValidMp3File(ruta)==true && this.marcada_plagio==false) {
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
	
	
	
	/**
	 * Metodo que para una reproduccion
	 */
	public void pararReproduccion()throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		if(Mp3Player.isValidMp3File(ruta)==true) {
			player.add(ruta);
			player.stop();
		}
	}
	
	
	/**
	 * Metodo que permite a un usuario notificar ccomo plagio una cancion
	 */
	public void notificarPlagio() {
		if(Sistema.getInstance().getUsuarioEnSesion().equals(Sistema.getInstance().getAdmin())) {
			this.setNotificada_plagio(true);
			Sistema.getInstance().getCancionesNotificadas().add(this);
			Sistema.getInstance().setNotificaciones(new Notificacion(this));
		}
	}
	
	
	/**
	 * Metodo que permite la revision de una cancion
	 * @return
	 */
	public boolean revisarNotificacion() {
		if(Sistema.getInstance().getUsuarioEnSesion().equals(Sistema.getInstance().getAdmin())) {
			this.setNotificada_plagio(false);
			return true;
		}
		return false;
	}
	
	
	/**
	 * Funcion que copia la cancion que tratamos en el sistema principal
	 * @return true en caso correcto
	 * @throws IOException
	 */
	public boolean copiarCancionASistema() throws IOException {
		File original=new File (this.getRuta());
		File destino=new File("hola.mp3");
//		Path FROM = FileSystems.getDefault().getPath("", this.getRuta());
//		Path TO = FileSystems.getDefault().getPath("cancionesSistema", this.getRuta());
		Files.copy(original.toPath(), destino.toPath());
	return false;
	}
	
	/**
	 * Funcion que unicamente permite al usuario ADMIN marcar una funcion como plagio
	 * @return true en caso correcto
	 */
	public boolean marcarComoPlagio() {
		/*SI EL USUARIO EN SESION ES EL ADMIN*/
		if(Sistema.getInstance().getUsuarioEnSesion().equals(Sistema.getInstance().getAdmin())) {
			if (Sistema.getInstance().getCancionesRechazadas().contains(this) == false)
				Sistema.getInstance().getCancionesRechazadas().add(this);
				this.setMarcada_plagio(true);
				this.autor.bloquearUsuario(false);
				return true;
		}
		return false;
	}
	
	
	
	
	/**
	 * Funcion que permite anadir un comentario al objeto comentable y reproducible cancion
	 * @return true en caso correcto
	 */
	public boolean anadirComentario(Comentario c) {
		if(Sistema.getInstance().getUsuarioEnSesion().puedeComentar()) {
			super.comentarios.add(c);
			return true;
		}
		return false;
	}
	
	
	
	
	
	/**************************************/

	/**
	 * 
	 * @return
	 */
	public boolean isNotificada_plagio() {
		return notificada_plagio;
	}
	
	public void setNotificada_plagio(boolean notificada_plagio) {
		this.notificada_plagio = notificada_plagio;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isPendiente_verificacion() {
		return pendiente_verificacion;
	}

	public void setPendiente_verificacion(boolean pendiente_verificacion) {
		this.pendiente_verificacion = pendiente_verificacion;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isAceptada() {
		return aceptada;
	}

	public void setAceptada(boolean aceptada) {
		this.aceptada = aceptada;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isMarcada_plagio() {
		return marcada_plagio;
	}

	public void setMarcada_plagio(boolean marcada_plagio) {
		this.marcada_plagio = marcada_plagio;
	}

	/**
	 * Funcion getter de rechazada
	 * 
	 * @return rechazada
	 */
	public boolean isRechazada() {
		return rechazada;
	}

	/**
	 * Funcion setter de rechazada
	 */
	public void setRechazada(boolean rechazada) {
		this.rechazada = rechazada;
	}

	
	
	/**
	 * Funcion que muestra por pantalla todos los atributos caracteristicos de una cancion
	 */
	public String toString(){
		return "Autor: "+this.getAutor()+"\n"+"Titulo: "+this.getTitulo()+"\n";
	}



	@Override
	public boolean moverCancionASistema() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}



	
	
}