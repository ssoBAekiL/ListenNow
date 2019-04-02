package es.uam.padsof.pruebas;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import es.uam.eps.padsof.telecard.FailedInternetConnectionException;
import es.uam.eps.padsof.telecard.InvalidCardNumberException;
import es.uam.eps.padsof.telecard.OrderRejectedException;
import es.uam.padsof.objetoreproducible.Album;
import es.uam.padsof.objetoreproducible.Cancion;
import es.uam.padsof.objetoreproducible.ListaReproducciones;
import es.uam.padsof.sistema.Sistema;
import es.uam.padsof.usuario.UsuarioRegistrado;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * Clase Desmostrador, que posee todos las caracteristicas propias 
 * de la aplicacion y muestra su funcionalidad paso a paso guardando los datos y leyendo estos de datosSistema.dat 
 * @author Julian Espada, Carlos Miret y Pablo Borrelli
 * 
 */
public class Demostrador {	
	Cancion cancion;
	/**
	 * Funcion principal de la clase demostrador
	 * @param args
	 * @throws IOException
	 * @throws Mp3PlayerException
	 * @throws InterruptedException
	 * @throws ClassNotFoundException
	 * @throws InvalidCardNumberException
	 * @throws FailedInternetConnectionException
	 * @throws OrderRejectedException
	 */
	public static void main(String[] args) throws IOException, Mp3PlayerException, InterruptedException, ClassNotFoundException, InvalidCardNumberException, FailedInternetConnectionException, OrderRejectedException {
		Cancion cancion;
		/* inicializacion de datos para el demostrador */
		if (new File("datosSistema.dat").exists()) {
				File f = new File ("datosSistema.dat");
				f.delete();
		}
		/*SINGLETON PATTERN*/
		Sistema sys = Sistema.getInstance();
		UsuarioRegistrado u1;
		UsuarioRegistrado u2;
		Cancion c1;
		Cancion c2; 
		Cancion c3;
		Cancion c4;
		/*CREAMOS LOS DIFERENTES USUARIOS*/
		u1 = new UsuarioRegistrado("usuario1", "pass", false, false);
		u2 = new UsuarioRegistrado("usuario2", "pass123", false, false);
		/*ANIADO LOS DIFERENTES USUARIOS AL SISTEMA*/
		sys.addUsuario(u1);
		sys.addUsuario(u2);
		/*EL ADMIN HACE LOGIN*/
		sys.login("ADMIN", "soyadmin");
		c1 = new Cancion("Cancion 1", u1, "GB 10sec video.mp3");
		c2 = new Cancion("Cancion 2", u2, "hive.mp3");
		c3 = new Cancion("Cancion 3", u1, "chicle3.mp3");
		c4 = new Cancion("Cancion 4", u2, "hola.mp3");
		sys.anadirReproducible(c1);
		sys.anadirReproducible(c2);
		sys.anadirReproducible(c3);
		sys.anadirReproducible(c4);
		/* Los datos se guardan en el archivo datosSistema.dat */
		sys.cerrarSistema();
		
		/* Se inicializa el sistema leyendo los datos y empieza el demostrador */
		sys.inicializarSistema();
		
		if(	sys.login("ADMIN", "soyadmin"))
			System.out.println("Login llevado a cabo correctamente.");
		System.out.println("\nCanciones por validar:\n" + sys.getCancionesValidar());
		sys.getCancionesValidar().get(0).validarCancion(); // Validar cancion1 de u1
		sys.getCancionesValidar().get(0).validarCancion(); // Validar cancion2 de u2
		sys.getCancionesValidar().get(0).validarCancion18(); // Validar cancion3 como no apta a menores
		sys.getCancionesValidar().get(0).rechazar(); // Rechazar cancion4
		System.out.println("\nCanciones validadas:\n" + sys.getCancionesValidadas());
		System.out.println("\nCanciones rechazadas:\n" + sys.getCancionesRechazadas());
		System.out.println("\nLimite reproducciones anonimas: " + sys.getnRepAnonimas());
		System.out.println("Reproducciones obtener premium: " + sys.getnRepRecompensa());
		System.out.println("Limite reproducciones usuarios registrados: " + sys.getnRepRegistrado());
		System.out.println("\nCambiando limites...\n");
		sys.setnRepAnonimas(3); // Cambio del limite de reproducciones de los usuarios no registrados
		sys.setnRepRecompensa(2); // Cambio del numero de reproducciones que un usuario tiene que recibir para ser recompensado el premium
		sys.setnRepRegistrado(4); // Cambio del limite de reproducciones de los usuarios registrados no premium
		System.out.println("Limite reproducciones anonimas: " + sys.getnRepAnonimas());
		System.out.println("Reproducciones obtener premium: " + sys.getnRepRecompensa());
		System.out.println("Limite reproducciones usuarios registrados: " + sys.getnRepRegistrado());
		sys.logout(); // Logout del administrador
		System.out.println("\nSesion admin desconectada\nUsusario no registrado en sesion");
		/* Reproducciones anonimas hasta llegar al limite preestablecido */
		cancion = sys.buscarCancion("Cancion 1");
		System.out.println("Reproduciendo: [" + cancion.getId() + "]: " + cancion.getTitulo());
		cancion.reproducir();
		cancion = sys.buscarCancion("Cancion 2");
		System.out.println("Reproduciendo: [" + cancion.getId() + "]: " + cancion.getTitulo());
		cancion.reproducir();
		cancion = sys.buscarCancion("Cancion 1");
		System.out.println("Reproduciendo: [" + cancion.getId() + "]: " + cancion.getTitulo());
		cancion.reproducir();
		cancion = sys.buscarCancion("Cancion 2");
		if (cancion.reproducir() == false) // Se ha llegado al limite de reproducciones
			System.out.println("Los usuarios no registrados han terminado sus reproducciones");
		if (sys.login("usuario1", "pass")) // Login del usuario 1
			System.out.println("\nLogin del usuario1 llevado a cabo correctamente.");
		Album album1 = new Album("Album 1", sys.getUsuarioEnSesion());
		ListaReproducciones lista_2= new ListaReproducciones ("Lista _2",sys.getUsuarioEnSesion());
		ListaReproducciones lista_3= new ListaReproducciones ("Lista _3",sys.getUsuarioEnSesion());
		/*Aniadimos canciones al album*/
		album1.anadirCancion(sys.getUsuarioEnSesion().getCanciones().get(0));
		album1.anadirCancion(sys.getUsuarioEnSesion().getCanciones().get(1));
		/*Anadimos una lista a una lista*/
		lista_2.aniadirAlbumALista(album1);
		lista_3.aniadirListaALista(lista_2);
		
		if (sys.anadirReproducible(album1)) // Album que contiene cancion1 y cancion3
			System.out.println("Album 1 anadido con exito.");
		System.out.println(sys.getUsuarioEnSesion().getAlbunes());
		sys.getAlbunes().get(0).reproducir();
		
		if(sys.anadirReproducible(lista_2))
			System.out.println("Lista _2 ha sido anadida con exito");
		
		if(sys.anadirReproducible(lista_3))//lista_3 contiene lista_2 que a su vez contiene album1
			System.out.println("Lista _3 ha sido anadida con exito, (LISTA DENTRO DE LISTA)");
		
		System.out.println(sys.getUsuarioEnSesion().getLista_reproducciones());
		sys.getListas().get(0).reproducir();
			
		System.out.println("\nEl ususario1 ha recibido una cuenta premium como recompensa por sus reproducciones");
		System.out.println(sys.getUsuarios().get(1));
		sys.getUsuarioEnSesion().follows(sys.getUsuarios().get(2)); // El ususario1 empieza a seguir al ususario2
		System.out.println("El ususario1 ha empezado a seguir al ususario2");
		System.out.println("\nSeguidores de ususario2: " + sys.getUsuarios().get(2).getSeguidores());
		sys.getCancionesValidadas().get(1).notificarPlagio();
		System.out.println("\nLa cancion2 ha sido notificada como plagio");
		System.out.println(sys.getCancionesNotificadas());
		sys.logout();
		System.out.println("\nEl usuario1 se ha desconectado");
		if (sys.login("usuario2", "pass123")) // Login del usuario 2
			System.out.println("\nLogin del usuario1 llevado a cabo correctamente.");
		if (sys.anadirReproducible(new Cancion("Cancion 5", sys.getUsuarios().get(2), "hive-1.mp3"))) // Se copia el archivo de la cancion en la carpeta cancionesSistema
																									  // es decir la accion de subida de un archivo mp3 se realiza al crear una nueva cancion
			System.out.println("\nSe ha anadido la cancion: " + sys.getCancionesValidar().get(0) + " y esta pendiente de ser validada.");
		ListaReproducciones lista1 = new ListaReproducciones("Lista 1", sys.getUsuarioEnSesion());
		lista1.aniadirAlbumALista(sys.getAlbunes().get(0));
		lista1.aniadirCancionALista(sys.getCancionesValidadas().get(0)); // No se anade, ya se encuentra en el album
		lista1.aniadirCancionALista(sys.getCancionesValidadas().get(1));
		if (sys.anadirReproducible(lista1)) // Lista que contiene album1 y cancion2
			System.out.println("\nSe ha anadido la lista: " + sys.getListas().get(0));
		System.out.println("Reproduciendo: " + sys.getListas().get(0));
		sys.getListas().get(0).reproducir();
		sys.logout();
		System.out.println("\nEl usuario2 se ha desconectado");
		sys.registrarse(new UsuarioRegistrado("usuario3", "123pass", false, false));
		if (sys.login("usuario3", "123pass")) // Login del usuario 3
			System.out.println("\nLogin del usuario3 llevado a cabo correctamente.");
		/* Reproducciones anonimas hasta llegar al limite preestablecido */
		cancion = sys.buscarCancion("Cancion 1");
		System.out.println("Reproduciendo: [" + cancion.getId() + "]: " + cancion.getTitulo());
		cancion.reproducir();
		sys.buscarAlbum("Album 1").reproducir();
		cancion = sys.buscarCancion("Cancion 2");
		System.out.println("Reproduciendo: [" + cancion.getId() + "]: " + cancion.getTitulo());
		cancion.reproducir();
		cancion = sys.buscarCancion("Cancion 2");
		if (cancion.reproducir() == false) // Se ha llegado al limite de reproducciones
			System.out.println("\nEl usuario ha terminado sus reproducciones gratuitas");
		if (sys.getUsuarioEnSesion().contratarPremium("1234567899876543"))
			System.out.println("El ususario3 ha contratado una cuenta premium,\nel output de la transacion se encuentra en registro_pagos.txt");
		System.out.println(sys.getUsuarioEnSesion());
		System.out.println("Reproduciendo: " + sys.buscarAlbum("Album 1"));
		sys.buscarAlbum("Album 1").reproducir(); // Se puede seguir reproduciendo cacniones gracias a la cuenta premium
		sys.getUsuarioEnSesion().follows(sys.getUsuarios().get(2)); // El ususario3 empieza a seguir al ususario2
		System.out.println("El ususario3 ha empezado a seguir al ususario2");
		System.out.println("\nSeguidores de ususario2: " + sys.getUsuarios().get(2).getSeguidores());
		sys.getCancionesValidadas().get(0).notificarPlagio(); // Notifica plagio en cancion1 de ususario1
		System.out.println("\nLa cancion1 ha sido notificada como plagio");
		System.out.println(sys.getCancionesNotificadas());
		sys.getCancionesValidadas().get(2).notificarPlagio(); // Notifica plagio en cancion3 de ususario2
		System.out.println("\nLa cancion3 ha sido notificada como plagio");
		System.out.println(sys.getCancionesNotificadas());
		sys.logout();
		System.out.println("\nEl usuario2 se ha desconectado");
		if (sys.login("usuario1", "pass")) // Login del usuario 1
			System.out.println("\nLogin del usuario1 llevado a cabo correctamente.");
		System.out.println("Notificaciones de usuario1:");
		System.out.println(sys.mostrarNotificacion());
		sys.logout();
		System.out.println("\nEl usuario1 se ha desconectado");
		if (sys.login("usuario2", "pass123")) // Login del usuario 2
			System.out.println("\nLogin del usuario2 llevado a cabo correctamente.");
		System.out.println("Notificaciones de usuario2:");
		System.out.println(sys.mostrarNotificacion());
		sys.logout();
		System.out.println("\nEl usuario2 se ha desconectado");
		if (sys.login("usuario3", "123pass")) // Login del usuario 3
			System.out.println("\nLogin del usuario3 llevado a cabo correctamente.");
		System.out.println("Notificaciones de usuario3:");
		System.out.println(sys.mostrarNotificacion());
		sys.logout();
		System.out.println("\nEl usuario3 se ha desconectado");
		if (sys.login("ADMIN", "soyadmin")) // Login del administrador
			System.out.println("\nLogin del administrador llevado a cabo correctamente.");
		System.out.println("Notificaciones de ADMIN:");
		System.out.println(sys.mostrarNotificacion());
		sys.getCancionesNotificadas().get(0).marcarComoPlagio();// El administrador revisa la notificacion de plagio y determina que es plagio
		System.out.println("La cancion " + sys.getCancionesNotificadas().get(0) + " ha sido marcada como plagio");
		sys.getCancionesNotificadas().get(1).marcarComoPlagio(); // El administrador revisa la notificacion de plagio y determina que es plagio
		sys.getCancionesNotificadas().get(1).getAutor().bloquearUsuario(true); // Ademas decide bloquear permanentemente al autor de la cancion
		System.out.println("La cancion " + sys.getCancionesNotificadas().get(0) + " ha sido marcada como plagio y el ususario1 bloqueado permanentemente");
		sys.getCancionesNotificadas().get(2).revisarNotificacion(); // El administrador revisa la notificacion de plagio y determina que no es plagio
		System.out.println("La cancion " + sys.getCancionesNotificadas().get(0) + " no ha sido marcada como plagio");
		System.out.println("\nDesconectando al usuario en sesion... Guardando los datos del sistema... Cerrando el sistema");
		sys.cerrarSistema();
		
		/*volvemos a inicializar el sistema*/
		sys.inicializarSistema();
		System.out.println("\nLos datos en sistema son:");
		System.out.println(sys); // Imprime todos los datos leidos desde el fichero datosSistema.dat
		/* SIMULACION PASO DE 30 DIAS */
		System.out.println("Simulando el paso de 30 dias...");
		sys.getUsuarios().get(2).setFechaBloqueo(LocalDate.now().minusDays(30));
		sys.getUsuarios().get(2).setFechaPremium(LocalDate.now().minusDays(30));
		sys.getUsuarios().get(3).setFechaPremium(LocalDate.now().minusDays(30));
		System.out.println("\nDesconectando al usuario en sesion... Guardando los datos del sistema... Cerrando el sistema");
		sys.cerrarSistema();
		
		sys.inicializarSistema(); // Al haber pasado 30 dias las cuentas premium habran caducado y se habran desbloqueado los usuarios
		System.out.println("Se ha simulado el paso de 30 dias\nLa inicializacion del sistema ha desbloqueado a usuario2 y ha desactivado la cuenta premium de ususario3");
		System.out.println(sys.getUsuarios().get(2)); // El ususario2 ha sido desbloqueado y le ha caducado el premium
		System.out.println(sys.getUsuarios().get(3)); // Al ususario 3 le ha caducado el premium
	}
}

