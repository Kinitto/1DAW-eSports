package es.studium.eSports;

import java.awt.Button;
import java.awt.Choice;
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

public class AltaJugador implements WindowListener, ActionListener {

	Frame frmAltaJugador = new Frame("Alta de Jugador");
	Label lblNombreJugador = new Label(" Nombre:  ");
	TextField txtNombreJugador = new TextField(15);
	Label lblNacionalidadJugador = new Label("Nacionalidad:");
	TextField txtNacionalidadJugador = new TextField(12);
	Label lblCapitaneado = new Label("Capitaneado");
	Choice choJugador = new Choice();
	Button btnAltaJugador = new Button("          Alta         ");
	Button btnCancelarAltaJugador = new Button("    Cancelar    ");

	Dialog dlgConfirmarAltaJugador = new Dialog(frmAltaJugador, "Alta Jugador", true);
	Label lblMensajeAltaJugador = new Label("Alta de Jugador Correcta");

	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	BaseDatoseSports bd = new BaseDatoseSports();

	public AltaJugador() {

		frmAltaJugador.setLayout(new FlowLayout());
		frmAltaJugador.add(lblNombreJugador);
		txtNombreJugador.setText("");
		frmAltaJugador.add(txtNombreJugador);
		frmAltaJugador.add(lblNacionalidadJugador);
		txtNacionalidadJugador.setText("");
		frmAltaJugador.add(txtNacionalidadJugador);
		btnAltaJugador.addActionListener(this);
		frmAltaJugador.add(lblCapitaneado);
		frmAltaJugador.add(choJugador);
		frmAltaJugador.add(btnAltaJugador);
		btnCancelarAltaJugador.addActionListener(this);
		frmAltaJugador.add(btnCancelarAltaJugador);
	

		frmAltaJugador.setSize(250, 190);
		frmAltaJugador.setResizable(false);
		frmAltaJugador.setLocationRelativeTo(null);
		frmAltaJugador.addWindowListener(this);
		txtNombreJugador.requestFocus();
		frmAltaJugador.setVisible(true);

		// Rellenar el Choice
		// Conectar
		connection = bd.conectar();
		sentencia = "SELECT * FROM jugadores";
		try {
			// Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Crear un objeto ResultSet para guardar lo obtenido
			// y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			choJugador.removeAll();
			while (rs.next()) {
				choJugador.add(
						rs.getInt("idJugador") + "-" + rs.getString("nombreJugador") + "-" + rs.getString("nacionalidadJugador")+ "-" + rs.getInt("serCapitanFK"));
			}

		} catch (SQLException sqle) {
		}
		
		choJugador.add("null");
		

	}

	@Override
	public void windowActivated(WindowEvent evento) {

		
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

	@Override
	public void actionPerformed(ActionEvent evento) {
		
		if (evento.getSource().equals(btnAltaJugador)) {

			connection = bd.conectar();
			try {
//Crear una sentencia
				
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				if (((txtNombreJugador.getText().length()) != 0) && ((txtNacionalidadJugador.getText().length()) != 0)) {
					String[] capitan = (choJugador.getSelectedItem()).split("-");
// clientes[0] --> idCliente
// clientes[1] --> nombre
// clientes[2] --> Cif

					sentencia = "INSERT INTO jugadores VALUES(null, '" + txtNombreJugador.getText() + "', '"
							+ txtNacionalidadJugador.getText()  + "', "
							+ capitan[0] + ")";
					
					statement.executeUpdate(sentencia);
					lblMensajeAltaJugador.setText("Alta de Jugador Correcta");
				} else {
					lblMensajeAltaJugador.setText("Faltan datos");
				}
			} catch (SQLException sqle) {
				lblMensajeAltaJugador.setText("Error en ALTA");
			} finally {
				dlgConfirmarAltaJugador.setLayout(new FlowLayout());
				dlgConfirmarAltaJugador.addWindowListener(this);
				dlgConfirmarAltaJugador.setSize(150, 100);
				dlgConfirmarAltaJugador.setResizable(false);
				dlgConfirmarAltaJugador.setLocationRelativeTo(null);
				dlgConfirmarAltaJugador.add(lblMensajeAltaJugador);
				dlgConfirmarAltaJugador.setVisible(true);

			}
			
			String usuario = LogineSports.txtUsuario.getText();
			Date fechaActual = new Date();
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
			try {
				FileWriter fw = new FileWriter("Log.log",true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter salida = new PrintWriter(bw);
				salida.print("["+formato.format(fechaActual)+"]");
				salida.print("["+usuario+"]");
				salida.println("["+sentencia+"]");
				salida.close();
				bw.close();
				fw.close();
			}
			catch(IOException i){
				System.out.println("Se produjo un error de archivo");
			}
		}

		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {

		if (frmAltaJugador.isActive()) {
			frmAltaJugador.setVisible(false);
		} else if (dlgConfirmarAltaJugador.isActive()) {
			txtNombreJugador.setText("");
			txtNacionalidadJugador.setText("");
			txtNombreJugador.requestFocus();
			dlgConfirmarAltaJugador.setVisible(false);
		}


	}

}
