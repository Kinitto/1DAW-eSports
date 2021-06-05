package es.studium.eSports;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrincipaleSports implements WindowListener, ActionListener {

	// Ventana Principal
	Frame ventana = new Frame("eSports Menú");
	MenuBar mnBar = new MenuBar();

	Menu mnuJugadores = new Menu("Jugadores");
	MenuItem mniAltaJugadores = new MenuItem("Alta");
	MenuItem mniBajaJugadores = new MenuItem("Baja");
	MenuItem mniModificacionJugadores = new MenuItem("Modificación");
	MenuItem mniConsultaJugadores = new MenuItem("Consulta");

	Menu mnuTorneos = new Menu("Torneos");
	MenuItem mniAltaTorneos = new MenuItem("Alta");
	MenuItem mniBajaTorneos = new MenuItem("Baja");
	MenuItem mniModificacionTorneos = new MenuItem("Modificación");
	MenuItem mniConsultaTorneos = new MenuItem("Consulta");

	Menu mnuParticipar = new Menu("Participantes");
	MenuItem mniAltaParticipar = new MenuItem("Alta");
	MenuItem mniConsultaParticipar = new MenuItem("Consulta");
	
	Menu mnuAyuda = new Menu("Ayuda");
	MenuItem mniAyuda = new MenuItem("Ayuda");

	public PrincipaleSports(int tipo) {

		ventana.setLayout(new FlowLayout());
		
		mnuTorneos.add(mniAltaTorneos);
		mniAltaTorneos.addActionListener(this);
		if (tipo == 0) {

			mnuTorneos.add(mniBajaTorneos);
			mniBajaTorneos.addActionListener(this);
			mnuTorneos.add(mniModificacionTorneos);
			mniModificacionTorneos.addActionListener(this);
			mnuTorneos.add(mniConsultaTorneos);
			mniConsultaTorneos.addActionListener(this);
		}
		mnBar.add(mnuTorneos);
		
		
		mnuJugadores.add(mniAltaJugadores);
		mniAltaJugadores.addActionListener(this);
		if (tipo == 0) {
			mnuJugadores.add(mniConsultaJugadores);
			mniConsultaJugadores.addActionListener(this);
		}
		mnBar.add(mnuJugadores);


		mnuParticipar.add(mniAltaParticipar);
		mniAltaParticipar.addActionListener(this);
		if (tipo == 0) {
			mnuParticipar.add(mniConsultaParticipar);
			mniConsultaParticipar.addActionListener(this);
		}
		mnBar.add(mnuParticipar);
		
	
		mniAyuda.addActionListener(this);
		mnuAyuda.add(mniAyuda);
		mnBar.add(mnuAyuda);

		ventana.setMenuBar(mnBar);
		ventana.setLayout(new FlowLayout());
		ventana.addWindowListener(this);
		ventana.setSize(320, 160);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}
	
	

	public static void main(String[] args) {

		new LogineSports();
	}

	@Override
	public void actionPerformed(ActionEvent evento) {

		if (evento.getSource().equals(mniAltaTorneos)) {
			new AltaTorneo();
		} else if (evento.getSource().equals(mniBajaTorneos)) {
			new BajaTorneo();
		} else if (evento.getSource().equals(mniModificacionTorneos)) {
			new ModificacionTorneo();
		} else if (evento.getSource().equals(mniConsultaTorneos)) {
			new ConsultaTorneos();
		}
		else if (evento.getSource().equals(mniAltaJugadores)) {
			new AltaJugador();
		}
		else if (evento.getSource().equals(mniAltaParticipar)) {
			new AltaParticipantes();
		}
		else if (evento.getSource().equals(mniConsultaJugadores)) {
			new ConsultaJugadores();
		}
		else if (evento.getSource().equals(mniConsultaParticipar)) {
			new ConsultaParticipantes();
		}
		else if (evento.getSource().equals(mniAyuda)) {
			
				try {
					Runtime.getRuntime().exec("hh.exe Ayuda.chm");
				} catch (IOException e) {
					e.printStackTrace();
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
		
		
		String usuario = LogineSports.txtUsuario.getText();
		Date fechaActual = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
		try {
			FileWriter fw = new FileWriter("Log.log",true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter salida = new PrintWriter(bw);
			salida.print("["+formato.format(fechaActual)+"]");
			salida.print("["+usuario+"]");
			salida.println("Salida del Sistema");
			salida.close();
			bw.close();
			fw.close();
		}
		catch(IOException i){
			System.out.println("Se produjo un error de archivo");
		}
		System.exit(0);

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