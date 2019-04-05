package es.uam.padsof.interfaz;

import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class RegistrarseGUI extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RegistrarseGUI() {
		this.setLayout(new SpringLayout());
		
		JLabel l_nombre = new JLabel("Nombre:");
		JTextField nombre = new JTextField(20);
		JLabel l_fechaNac = new JLabel("Fecha de nacimiento:");
		JTextField fechaNac = new JTextField(10);
//		UtilDateModel model = new UtilDateModel();
//		JDatePanelImpl datePanel = new JDatePanelImpl(model, null);
//		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, null);
		JLabel l_contrasena = new JLabel("Contrasena:");
		JPasswordField contrasena = new JPasswordField(20);
		JLabel l_confContrasena = new JLabel("Confirmar contrasena:");
		JPasswordField confContrasena = new JPasswordField(20);
		JCheckBox cuentaPremium = new JCheckBox("Quiero una subscripcion premium.");
		
		
		
		JButton b_registrarse = new JButton("Registrarse");
		
		this.add(l_nombre);
		this.add(l_fechaNac);
		this.add(nombre);
		this.add(fechaNac);
		this.add(b_registrarse);
		this.add(confContrasena);
		this.add(contrasena);
		this.add(l_confContrasena);
		this.add(l_contrasena);
		this.add(cuentaPremium);
	}

}
