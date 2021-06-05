package es.studium.eSports;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogineSports implements WindowListener, ActionListener {

	Frame ventanaLogin = new Frame("Login");
	Dialog dialogoLogin = new Dialog(ventanaLogin, "error", true);

	Label lblUsuario = new Label("Usuario");
	Label lblClave = new Label("Clave");
	Label lblError = new Label("¡Credenciales incorrectas!");
	static TextField txtUsuario = new TextField(20);
	TextField txtClave = new TextField(20);
	Button btnAceptar = new Button("Acceder");
	Button btnLimpiar = new Button("Limpiar");

	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public LogineSports() {

		ventanaLogin.setLayout(new FlowLayout());

		btnLimpiar.addActionListener(this);
		btnAceptar.addActionListener(this);
		ventanaLogin.addWindowListener(this);
		ventanaLogin.add(lblUsuario);
		ventanaLogin.add(txtUsuario);
		ventanaLogin.add(lblClave);
		txtClave.setEchoChar('*');
		ventanaLogin.add(txtClave);
		ventanaLogin.add(btnAceptar);
		ventanaLogin.add(btnLimpiar);
		ventanaLogin.setSize(250, 135);
		ventanaLogin.setLocationRelativeTo(null);
		ventanaLogin.setResizable(false);
		ventanaLogin.setVisible(true);
		txtUsuario.setText("");
		txtClave.setText("");
	}

	public static void main(String[] args) {

		new LogineSports();

	}

	@Override
	public void actionPerformed(ActionEvent botonPulsado) {
		// TODO Auto-generated method stub
		if (botonPulsado.getSource().equals(btnLimpiar)) {

			txtUsuario.setText("");
			txtClave.setText("");
			txtUsuario.requestFocus();

		} else if (botonPulsado.getSource().equals(btnAceptar)) {

			// conectar a la bdd
			BaseDatoseSports bd = new BaseDatoseSports();
			connection = bd.conectar();
			// buscar lo que el uisuario escribio, si esxiste en bd mostrar menu principal.
			sentencia = "SELECT * FROM usuarios WHERE nombreUsuario='" + txtUsuario.getText()
					+ "' AND claveUsuario = SHA2('" + txtClave.getText() + "',256);";
			try {
				// Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = statement.executeQuery(sentencia);
				if (rs.next()) {
					// si existe en la bd mostrar el menu principal
					int tipo = rs.getInt("tipoUsuario");
					new PrincipaleSports(tipo);
					
					String usuario = LogineSports.txtUsuario.getText();
					Date fechaActual = new Date();
					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
					try {
						FileWriter fw = new FileWriter("Log.log",true);
						BufferedWriter bw = new BufferedWriter(fw);
						PrintWriter salida = new PrintWriter(bw);
						salida.print("["+formato.format(fechaActual)+"]");
						salida.print("["+usuario+"]");
						salida.println("[Acceso al sistema]");
						salida.close();
						bw.close();
						fw.close();
					}
					catch(IOException i){
						System.out.println("Se produjo un error de archivo");
					}
				} else {
					dialogoLogin.setLayout(new FlowLayout());
					dialogoLogin.add(lblError);
					dialogoLogin.addWindowListener(this);
					dialogoLogin.setSize(180, 75);
					dialogoLogin.setResizable(false);
					dialogoLogin.setLocationRelativeTo(null);
					dialogoLogin.setVisible(true);
				}
			} catch (SQLException sqle) {

			}
			
		
			
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		if (dialogoLogin.isActive()) {
			dialogoLogin.setVisible(false);
		} else {
			System.exit(0);
		}

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

}
