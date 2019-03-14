package pruebas;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class TestFechas {

	public static void main(String[] args) {
		LocalDate fechaCaduca = LocalDate.now();
		LocalDate fecha = LocalDate.now();
		
		System.out.println(fecha);
		System.out.println(fechaCaduca.minusDays(45));
	
		if(fecha.minusDays(30).isBefore(fechaCaduca))
			System.out.println("Ha caducado");
		else
			System.out.println("No ha caducado");
	}

}
