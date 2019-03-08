/**
 * @author Julián Espada, Pablo Borrelli y Carlos Miret
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
	 * @param id Este es el id que tendrá la canción
	 * @param ruta Este es el nombre de la ruta del fichero que contendrá la canción
	 * @param nrep Este es el número de reproducciones de la canción
	 * @param m18 Este es el boolean que marcará si la canción es apta para mayores de 18
	 * @param val Este es el boolean que marcará la canción como válida o no
	 * @param plag Este es el boolean que marcará la canción como plagio
	 * 
	 * Este método es el constructor del objeto Cancion
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
	 * @param id El id nuevo que queremo que tenga la canción
	 * @return void
	 * 
	 * Esta clase modificará el id de la canción
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return rutaFichero 
	 * 
	 * Este método se encarga de devolver la ruta del fichero donde se encuentra la canción
	 */
	public String getRutaFichero() {
		return rutaFichero;
	}

	/**
	 * 
	 * @param rutaFichero Es la ruta del nuevo fichero al que va a pertenecer la canción
	 * @return void
	 * 
	 * Este método modificará la ruta del fichero donde se encontrará la canción
	 */
	public void setRutaFichero(String rutaFichero) {
		this.rutaFichero = rutaFichero;
	}

	/**
	 * 
	 * @return nreproducciones
	 * 
	 * Este método devuelve el número de reproducciones de la canción
	 */
	public int getNreproducciones() {
		return nreproducciones;
	}

	/**
	 * 
	 * @param nreproducciones Este es el nuevo número de reproducciones para la canción
	 * @return void
	 * 
	 * Este método se encarga de modificar el número de reproducciones de la canción
	 */
	public void setNreproducciones(int nreproducciones) {
		this.nreproducciones = nreproducciones;
	}

	/**
	 * 
	 * @return mas18
	 * 
	 * Este método devuelve el boolean que tiene la canción que indica si es apto para mayores de 18
	 */
	public boolean isMas18() {
		return mas18;
	}

	/**
	 * 
	 * @param mas18 el nuevo boolean que tendrá el atributo mas18 de la cancion
	 * 
	 * Este método modifica el boolean mas18 de la clase Cancion
	 */
	public void setMas18(boolean mas18) {
		this.mas18 = mas18;
	}

	/**
	 * 
	 * @return validar 
	 * 
	 * Este método devuelve el boolean de la cancion que indica si está validada o no
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
	 * Este método devuelve el boolean plagio de la cancion que indica si la canción es un plagio o no
	 */
	public boolean isPlagio() {
		return plagio;
	}

	/**
	 * 
	 * @param plagio Este es el nuevo boolean plagio que tendrá la canción
	 * 
	 * Este método modifica el boolean plagio de la canción por el que se pasa por argumento
	 */
	public void setPlagio(boolean plagio) {
		this.plagio = plagio;
	} 
	
}