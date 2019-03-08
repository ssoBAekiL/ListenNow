/**
 * @author Juli�n Espada, Pablo Borrelli y Carlos Miret
 * 
 * Esta clase se encarga de gestionar el objeto Cancion 
 */
public class Cancion extends ObjetoReproducible{
	private int id;
	private String rutaFichero;
	/*private time duracion; */
	private int nreproducciones;
	private boolean mas18;
	private boolean validar;
	private boolean plagio;
	
	/**
	 * 
	 * @param id Este es el id que tendr� la canci�n
	 * @param ruta Este es el nombre de la ruta del fichero que contendr� la canci�n
	 * @param nrep Este es el n�mero de reproducciones de la canci�n
	 * @param m18 Este es el boolean que marcar� si la canci�n es apta para mayores de 18
	 * @param val Este es el boolean que marcar� la canci�n como v�lida o no
	 * @param plag Este es el boolean que marcar� la canci�n como plagio
	 * 
	 * Este m�todo es el constructor del objeto Cancion
	 */
	public Cancion (int id, String ruta, int nrep, boolean m18, boolean val, boolean plag) {
		this.id=id;
		this.rutaFichero=ruta;
		this.nreproducciones=nrep;
		this.mas18=m18;
		this.validar=val;
		this.plagio=plag;
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
	 * @return rutaFichero 
	 * 
	 * Este m�todo se encarga de devolver la ruta del fichero donde se encuentra la canci�n
	 */
	public String getRutaFichero() {
		return rutaFichero;
	}

	/**
	 * 
	 * @param rutaFichero Es la ruta del nuevo fichero al que va a pertenecer la canci�n
	 * @return void
	 * 
	 * Este m�todo modificar� la ruta del fichero donde se encontrar� la canci�n
	 */
	public void setRutaFichero(String rutaFichero) {
		this.rutaFichero = rutaFichero;
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
	 * 
	 * @return validar 
	 * 
	 * Este m�todo devuelve el boolean de la cancion que indica si est� validada o no
	 */
	public boolean isValidar() {
		return validar;
	}

	/**
	 * 
	 * @param validar Este es el nuevo boolean validar por el que cambiaremos
	 * 
	 * 
	 */
	public void setValidar(boolean validar) {
		this.validar = validar;
	}

	/**
	 * 
	 * @return plagio 
	 * 
	 * Este m�todo devuelve el boolean plagio de la cancion que indica si la canci�n es un plagio o no
	 */
	public boolean isPlagio() {
		return plagio;
	}

	/**
	 * 
	 * @param plagio Este es el nuevo boolean plagio que tendr� la canci�n
	 * 
	 * Este m�todo modifica el boolean plagio de la canci�n por el que se pasa por argumento
	 */
	public void setPlagio(boolean plagio) {
		this.plagio = plagio;
	}

	
}