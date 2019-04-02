package es.uam.padsof.pruebas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Ejemplo1 {

	public static void main(String[] args) {
		
		//constructir de la ventana instancionamos lo paneles. si el contenedor de la ventan tiene cardlayout. mostrar_registrado, mostrar. al tener una ventana hacer singleton
		JFrame ventana = new JFrame("ListenNow!");//tener una ventana--> tener una clase que herede de JFrame
		// obtener contenedor, asignar layout
		
		Container contenedor = ventana.getContentPane();
		contenedor.setLayout(new FlowLayout());
		// crear componentes
		JLabel etiqueta = new JLabel("BIENVENIDO. DISFRUTA DE LA MUSICA QUE MAS TE GUSTA");
		//paneles con cardlayout
		final JTextField campo = new JTextField(10);
		
		JButton boton = new JButton("Haz click para buscar");
		
		JButton boton_registrarse = new JButton("Haz click para registrarte");
		
		JButton boton_premium = new JButton("Haz click para contratar premium");
		
		
		final JPanel pantalla1 = new JPanel(); 
		JButton siguiente_boton = new JButton("siguiente"); 
		pantalla1.add(siguiente_boton); 
		pantalla1.setVisible(true);
		
		
		
		// asociar acciones a componentes
		boton.addActionListener(
		           new ActionListener() {
		                 public void actionPerformed(ActionEvent e) {
		                      JOptionPane.showMessageDialog(null, campo.getText());
		                      pantalla1.setVisible(true);
		                 }
		           }
		       );
		
		
		// a�adir componentes al contenedor
		contenedor.add(etiqueta);
		contenedor.add(campo);
		contenedor.add(pantalla1);
		contenedor.add(boton_registrarse);
		
		
		contenedor.add(boton);
		contenedor.add(boton_premium);

		
		// mostrar ventana
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setSize(800,400);
		ventana.setVisible(true);	
	}

}