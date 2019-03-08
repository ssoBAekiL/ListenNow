import java.util.ArrayList;

/**
 * @author Julián Espada, Pablo Borrelli y Carlos Miret
 *
 * Esta clase se encarga de gestionar el objeto Album
 */
public class Album extends ObjetoReproducible{
	/*private time duracion;*/
	
	/** Canciones del album*/
	private ArrayList<Cancion> canciones;
	
	/**
	 * 
	 * @param nCan Es el número de canciones totales que tiene el album
	 * @param canciones Es el array que tendrá las canciones de dicho álbum
	 * 
	 * Este método es el constructor del objeto Album
	 */
	public Album (ArrayList<Cancion> canciones, String autor) {
		this.canciones=canciones;
	}
	
	/**
	 * 
	 * @return canciones.size() Es un método que se encarga de devolver el tamaño del array canciones
	 * 
	 * Este método devuelve el número de canciones que tiene el álbum
	 */
	public int getnCanciones() {
		return canciones.size();
	}
	
	
	/**
	 * 
	 * @return canciones Es el array que tiene todas las canciones de un álbum
	 * 
	 * Este método se encarga de devolver el array con las canciones que tendrá un álbum
	 */
	public ArrayList<Cancion> getCanciones() {
		return canciones;
	}
	
	/**
	 * 
	 * @param canciones Es el nuevo array que tendrá el álbum a modificar
	 */
	public void setCanciones(ArrayList<Cancion> canciones) {
		this.canciones = canciones;
	}
	
	/**
	 * 
	 * @param c Canción a añadir
	 * 
	 * Este método se encargará de añadir una canción al álbum
	 */
	public void aniadirCancionAlbum(Cancion c) {
		int i=0;
		ArrayList<Cancion> canciones = this.getCanciones();
		for(i=0; i<canciones.size(); i++) {
			if(canciones.get(i)==c) {
				return;
			}
		}
		canciones.add(c);
		return;
	}
	
	/**
	 * 
	 * @param c Canción a borrar
	 * 
	 * Este método se encarga de borrar la canción que se le pasa como argumento del álbum
	 */
	public void borrarCancionAlbum(Cancion c) {
		canciones.remove(c);
		return;
	}
	
	/**
	 * 
	 * @param c Canciones a añadir
	 * 
	 * Este método se encarga de añadir masivamente varias canciones al álbum
	 */
	public void aniadirCancionesAlbum(Cancion...c) {
		int i=0;
		while(i<c.length) {
			aniadirCancionAlbum(c[i]);
			i++;
		}
		return;
	}
	
	/**
	 * 
	 * @param c
	 */
	public void borrarCancionesAlbum(Cancion...c) {
		int i=0;
		while(i<c.length) {
			borrarCancionAlbum(c[i]);
			i++;
		}
		return;
	}
	
	/* IMPORTANTE COMPROBAR SI YA EXISTE LA CANCION EN EL ALBUM AL AÑADIR O SI NO EXISTE AL BORRAR O SI NO ES DEL AUTOR*/
	
}