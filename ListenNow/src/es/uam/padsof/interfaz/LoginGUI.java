package es.uam.padsof.interfaz;

import java.awt.event.ActionListener;

import javax.swing.*;

public class LoginGUI extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JTextField NombreUsuario;
	private JTextField Contrasena;
	private JButton login;

	public LoginGUI() {
		this.setLayout(new SpringLayout());
		JLabel IntroduzcaNombre = new JLabel("Introduzca su nombre de usuario: ");
		NombreUsuario = new JTextField(15);
		JLabel IntroduzcaContrasena = new JLabel("Introdzca su contraseña: ");
		Contrasena = new JTextField(15);
		login = new JButton("Login");
		
		this.add(IntroduzcaNombre);
		this.add(NombreUsuario);
		this.add(IntroduzcaContrasena);
		this.add(Contrasena);
		this.add(login);
	}
	
	public void setControlador(ActionListener c) {
		login.addActionListener(c);
	}
	
	public String getNombreUsuario() {
		return NombreUsuario.getText();
	}
	
	public String getContrasena() {
		return Contrasena.getText();
	}
	
	

}
