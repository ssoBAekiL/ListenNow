package es.uam.padsof.ventanas;

import java.awt.Container;

import javax.swing.JFrame;

import es.uam.padsof.interfaz.LoginGUI;

public class ventanaLogin extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	LoginGUI panelLogin = new LoginGUI();
	JFrame ventanaLogin = new JFrame ();
	Container contenedor = ventanaLogin.getContentPane();
	
	public ventanaLogin() {
		contenedor.add(panelLogin);
		ventanaLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventanaLogin.setSize(800, 400);
		ventanaLogin.setVisible(true);
	}
	
	
	
}
