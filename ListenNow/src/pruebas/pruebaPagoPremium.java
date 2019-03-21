package pruebas;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import es.uam.eps.padsof.telecard.FailedInternetConnectionException;
import es.uam.eps.padsof.telecard.InvalidCardNumberException;
import es.uam.eps.padsof.telecard.OrderRejectedException;
import es.uam.padsof.usuario.UsuarioRegistrado;

public class pruebaPagoPremium {

	public static void main(String[] args) throws IOException, InvalidCardNumberException, FailedInternetConnectionException, OrderRejectedException {
		File file= new File("registro_pagos.txt");
		UsuarioRegistrado u1=new UsuarioRegistrado("1234567891234567","carlos","6253", false,false);
		u1.contratarPremium(file);
	}

}
