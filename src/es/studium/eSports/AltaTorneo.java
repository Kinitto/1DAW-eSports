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

public class AltaTorneo implements WindowListener, ActionListener {
	Frame frmAltaTorneo = new Frame("Alta de Torneo");
	Label lblNombreTorneo = new Label(" Nombre:  ");
	TextField txtNombreTorneo = new TextField(15);
	Label lblPremioTorneo = new Label("Pricepool:");
	TextField txtPremioTorneo = new TextField(15);
	Button btnAltaTorneo = new Button("     Alta     ");
	Button btnCancelarAltaTorneo = new Button("Cancelar");

	Dialog dlgConfirmarAltaTorneo = new Dialog(frmAltaTorneo, "Alta Torneo", true);
	Label lblMensajeAltaTorneo = new Label("Alta de Torneo Correcta");

	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	BaseDatoseSports bd = new BaseDatoseSports();

	public AltaTorneo() {
		frmAltaTorneo.setLayout(new FlowLayout());
		frmAltaTorneo.add(lblNombreTorneo);
		txtNombreTorneo.setText("");
		frmAltaTorneo.add(txtNombreTorneo);
		frmAltaTorneo.add(lblPremioTorneo);
		txtPremioTorneo.setText("");
		frmAltaTorneo.add(txtPremioTorneo);
		btnAltaTorneo.addActionListener(this);
		frmAltaTorneo.add(btnAltaTorneo);
		btnCancelarAltaTorneo.addActionListener(this);
		frmAltaTorneo.add(btnCancelarAltaTorneo);

		frmAltaTorneo.setSize(250, 140);
		frmAltaTorneo.setResizable(false);
		frmAltaTorneo.setLocationRelativeTo(null);
		frmAltaTorneo.addWindowListener(this);
		txtNombreTorneo.requestFocus();
		frmAltaTorneo.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent evento) {
		if (evento.getSource().equals(btnAltaTorneo)) {
			connection = bd.conectar();
			try {
				// Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				// Crear un objeto ResultSet para guardar lo obtenido
				// y ejecutar la sentencia SQL
				if (((txtNombreTorneo.getText().length()) != 0) && ((txtPremioTorneo.getText().length()) != 0)) {
					sentencia = "INSERT INTO torneos VALUES (null, '" + txtNombreTorneo.getText() + "', '"
							+ txtPremioTorneo.getText() + "')";
					statement.executeUpdate(sentencia);
					lblMensajeAltaTorneo.setText("Alta de Torneo Correcta");
				} else {
					lblMensajeAltaTorneo.setText("Faltan datos");
				}
			} catch (SQLException sqle) {
				lblMensajeAltaTorneo.setText("Error en ALTA");
				System.out.println(sqle.getMessage());
			} finally {
				dlgConfirmarAltaTorneo.setLayout(new FlowLayout());
				dlgConfirmarAltaTorneo.addWindowListener(this);
				dlgConfirmarAltaTorneo.setSize(200, 120);
				dlgConfirmarAltaTorneo.setResizable(false);
				dlgConfirmarAltaTorneo.setLocationRelativeTo(null);
				dlgConfirmarAltaTorneo.add(lblMensajeAltaTorneo);
				dlgConfirmarAltaTorneo.setVisible(true);
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
		} // TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		if (frmAltaTorneo.isActive()) {
			frmAltaTorneo.setVisible(false);
		} else if (dlgConfirmarAltaTorneo.isActive()) {
			txtNombreTorneo.setText("");
			txtPremioTorneo.setText("");
			txtNombreTorneo.requestFocus();
			dlgConfirmarAltaTorneo.setVisible(false);
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