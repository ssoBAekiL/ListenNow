package es.uam.padsof.pruebas;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AlbumTest.class, CancionTest.class, ComentarioTest.class, ListasReproduccionesTest.class,
		ObjetoComentableTest.class, ObjetoReproducibleTest.class, SistemaTest.class, UsuarioRegistradoTest.class })

/**
 * Clase que ejecuta todos los test jUnit
 * @author Julian Espada, Carlos Miret y Pablo Borrelli
 *
 */
public class AllTests {

}
