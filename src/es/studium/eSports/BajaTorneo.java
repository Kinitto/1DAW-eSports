package es.studium.eSports;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
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

public class BajaTorneo implements WindowListener, ActionListener {
	
	Frame frmBajaTorneo = new Frame("Baja de Torneo");
	Label lblMensajeBajaTorneo = new Label("Seleccionar el Torneo:");
	Choice choTorneo = new Choice();
	Button btnBorrarTorneo = new Button("        Borrar         ");
	Dialog dlgSeguroTorneo = new Dialog(frmBajaTorneo, "¿Seguro?", true);
	Label lblSeguroTorneo = new Label("¿Está seguro de borrar?");
	Button btnSiSeguroTorneo = new Button("Sí");
	Button btnNoSeguroTorneo = new Button("No"); 
	Dialog dlgConfirmacionBajaTorneo = new Dialog(frmBajaTorneo, "Baja Torneo", true);
	Label lblConfirmacionBajaTorneo = new Label("Baja de cliente correcta");
	
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	BaseDatoseSports bd = new BaseDatoseSports();
	
	public BajaTorneo() {
		
		
		frmBajaTorneo.setLayout(new FlowLayout());
		frmBajaTorneo.add(lblMensajeBajaTorneo);
		frmBajaTorneo.add(choTorneo);
		btnBorrarTorneo.addActionListener(this);
		frmBajaTorneo.add(btnBorrarTorneo);

		frmBajaTorneo.setSize(250, 140);
		frmBajaTorneo.setResizable(false);
		frmBajaTorneo.setLocationRelativeTo(null);
		frmBajaTorneo.addWindowListener(this);
		frmBajaTorneo.setVisible(true);
			
			// Rellenar el Choice
			// Conectar
			connection = bd.conectar();
			sentencia = "SELECT * FROM torneos";
	try {
	// Crear una sentencia
	statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	// Crear un objeto ResultSet para guardar lo obtenido
	// y ejecutar la sentencia SQL
	rs = statement.executeQuery(sentencia);
	choTorneo.removeAll();
	while (rs.next()) {
		choTorneo.add(rs.getInt("idTorneo") + "-" + rs.getString("nombreTorneo") + "-"
				+ rs.getInt("premioTorneo"));
	}
	
	} catch (SQLException sqle) {
	}
		
	}
	@Override
	public void actionPerformed(ActionEvent evento) {
	
		if (evento.getSource().equals(btnBorrarTorneo)) {
			
			dlgSeguroTorneo.setLayout(new FlowLayout());
			dlgSeguroTorneo.addWindowListener(this);
			dlgSeguroTorneo.setSize(150, 100);
			dlgSeguroTorneo.setResizable(false);
			dlgSeguroTorneo.setLocationRelativeTo(null);
			dlgSeguroTorneo.add(lblSeguroTorneo);
			btnSiSeguroTorneo.addActionListener(this);
			dlgSeguroTorneo.add(btnSiSeguroTorneo);
			btnNoSeguroTorneo.addActionListener(this);
			dlgSeguroTorneo.add(btnNoSeguroTorneo);
			dlgSeguroTorneo.setVisible(true);
		} else if (evento.getSource().equals(btnNoSeguroTorneo)) {
			dlgSeguroTorneo.setVisible(false);
		} else if (evento.getSource().equals(btnSiSeguroTorneo)) {
			// Conectar
			connection = bd.conectar();
			// Hacer un DELETE FROM clientes WHERE idCliente = X
			String[] elegido = choTorneo.getSelectedItem().split("-");
			sentencia = "DELETE FROM torneos WHERE idTorneo = " + elegido[0];
			try {
				// Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				statement.executeUpdate(sentencia);
				lblConfirmacionBajaTorneo.setText("Baja de Torneo Correcta");
			} catch (SQLException sqle) {
				lblConfirmacionBajaTorneo.setText("Error en Baja");
			} finally {
				dlgConfirmacionBajaTorneo.setLayout(new FlowLayout());
				dlgConfirmacionBajaTorneo.addWindowListener(this);
				dlgConfirmacionBajaTorneo.setSize(180, 100);
				dlgConfirmacionBajaTorneo.setResizable(false);
				dlgConfirmacionBajaTorneo.setLocationRelativeTo(null);
				dlgConfirmacionBajaTorneo.add(lblConfirmacionBajaTorneo);
				dlgConfirmacionBajaTorneo.setVisible(true);
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
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent arg0) {
		if (frmBajaTorneo.isActive()) {
			frmBajaTorneo.setVisible(false);
		} else if (dlgSeguroTorneo.isActive()) {
			dlgSeguroTorneo.setVisible(false);
		} else if (dlgConfirmacionBajaTorneo.isActive()) {
			dlgConfirmacionBajaTorneo.setVisible(false);
			dlgSeguroTorneo.setVisible(false);
			frmBajaTorneo.setVisible(false);
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
