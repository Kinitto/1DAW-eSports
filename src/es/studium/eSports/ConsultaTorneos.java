package es.studium.eSports;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.PrintJob;
import java.awt.TextArea;
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

public class ConsultaTorneos extends Frame implements WindowListener, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Frame frmConsultaTorneos = new Frame("Consulta Clientes");

	TextArea listadoTorneos = new TextArea(4, 30);
	Button btnPdfTorneos = new Button("PDF");

	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	BaseDatoseSports bd = new BaseDatoseSports();

	public ConsultaTorneos() {

		frmConsultaTorneos.setLayout(new FlowLayout());
		listadoTorneos.setEditable(false);
		frmConsultaTorneos.add(listadoTorneos);
		frmConsultaTorneos.add(btnPdfTorneos);
		btnPdfTorneos.addActionListener(this);
		frmConsultaTorneos.setSize(250, 160);
		frmConsultaTorneos.setResizable(false);
		frmConsultaTorneos.setLocationRelativeTo(null);
		frmConsultaTorneos.addWindowListener(this);
		frmConsultaTorneos.setVisible(true);

		frmConsultaTorneos.setLayout(new FlowLayout());
		// Conectar
		bd = new BaseDatoseSports();
		connection = bd.conectar();
		sentencia = "SELECT * FROM torneos";
		// La información está en ResultSet
		// Recorrer el RS y por cada registro,
		// meter una línea en el TextArea

		String usuario = LogineSports.txtUsuario.getText();
		Date fechaActual = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
		try {
			FileWriter fw = new FileWriter("Log.log", true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter salida = new PrintWriter(bw);
			salida.print("[" + formato.format(fechaActual) + "]");
			salida.print("[" + usuario + "]");
			salida.println("[" + sentencia + "]");
			salida.close();
			bw.close();
			fw.close();
		} catch (IOException i) {
			System.out.println("Se produjo un error de archivo");
		}

		try {
			// Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Crear un objeto ResultSet para guardar lo obtenido
			// y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			listadoTorneos.selectAll();
			listadoTorneos.setText("");
			listadoTorneos.append("id\t Nombre\t        Pricepool\n");
			while (rs.next()) {
				listadoTorneos.append(rs.getInt("idTorneo") + "\t" + rs.getString("nombreTorneo") + "\t"
						+ rs.getInt("premioTorneo") + "\n");
			}
		} catch (SQLException sqle) {
			listadoTorneos.setText("Se ha producido un error en la consulta");
		} finally {

		}

		listadoTorneos.setEditable(false);
		frmConsultaTorneos.add(listadoTorneos);
		frmConsultaTorneos.add(btnPdfTorneos);

		frmConsultaTorneos.setSize(250, 160);
		frmConsultaTorneos.setResizable(false);
		frmConsultaTorneos.setLocationRelativeTo(null);
		frmConsultaTorneos.addWindowListener(this);
		frmConsultaTorneos.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent evento) {

		pack();
		// Se obtiene el objeto PrintJob
		PrintJob pjob = this.getToolkit().getPrintJob(this, "", null);
		// Se obtiene el objeto graphics sobre el que pintar
		Graphics pg = pjob.getGraphics();
		// Se fija la fuente de caracteres con la que escribir
		pg.setFont(new Font("Arial", Font.BOLD, 16));
		// Se escribe el mensaje línea a línea del Cuadro de Texto indicando
		// posición con respecto a la parte superior izquierda
		String texto = listadoTorneos.getText();
		String[] lineas = texto.split("\n");
		for (int i = 0; i < lineas.length; i++) {
			pg.drawString(lineas[i], 30, 45 + (i * 20));
		}
		// Se finaliza la página
		pg.dispose();
		// Se hace que la impresora termine el trabajo y suelte la página
		pjob.end();
		
		String usuario = LogineSports.txtUsuario.getText();
		Date fechaActual = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
		try {
			FileWriter fw = new FileWriter("Log.log", true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter salida = new PrintWriter(bw);
			salida.print("[" + formato.format(fechaActual) + "]");
			salida.print("[" + usuario + "]");
			salida.println("[ImprimirConsultaTorneo]");
			salida.close();
			bw.close();
			fw.close();
		} catch (IOException i) {
			System.out.println("Se produjo un error de archivo");
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

		if (frmConsultaTorneos.isActive()) {
			frmConsultaTorneos.setVisible(false);
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
