package es.studium.eSports;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.List;
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

public class ModificacionTorneo implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Editar Torneo");
	Label lblTorneo = new Label("Elegir Torneo");
	List listTorneo = new List(8, false);
	Button btnEditar = new Button("Editar");
	Button btnCancelar = new Button("Cancelar");

	Frame ventanaEdicion = new Frame("Editando Jugador");
	Label lblidTorneo = new Label("idJugador:");
	Label lblNombreTorneo = new Label("Nombre:");
	Label lblPremioTorneo = new Label("Pricepool:");
	TextField txtId = new TextField(20);
	TextField txtNombre = new TextField(20);
	TextField txtPremio = new TextField(20);
	Button btnAceptar = new Button("Aceptar");
	Button btnCancelar2 = new Button("Cancelar");

	Dialog dlgMensajeModificacionTorneo = new Dialog(ventanaEdicion, 
			"Confirmación", true);
	Label lblMensaje = new Label("Modificación de Torneo Correcta");

	BaseDatoseSports bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public ModificacionTorneo()
	{
		ventana.setLayout(new FlowLayout());
		// Listeners
		ventana.addWindowListener(this);
		btnEditar.addActionListener(this);
		btnCancelar.addActionListener(this);
		ventanaEdicion.addWindowListener(this);
		btnAceptar.addActionListener(this);
		btnCancelar2.addActionListener(this);
		dlgMensajeModificacionTorneo.addWindowListener(this);
		
		ventana.add(lblTorneo);
		// Conectar
		bd = new BaseDatoseSports();
		connection = bd.conectar();
		sentencia = "SELECT * FROM torneos";
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			listTorneo.removeAll();
			while(rs.next())
			{
				listTorneo.add(rs.getInt("idTorneo")
						+"-"+rs.getString("nombreTorneo")
						+"-"+rs.getInt("premioTorneo"));
			}
		}
		catch (SQLException sqle)
		{

		}
		ventana.add(listTorneo);
		ventana.add(btnEditar);
		ventana.add(btnCancelar);
		ventana.setSize(200,250);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent evento)
	{
		// TODO Auto-generated method stub
		if(evento.getSource().equals(btnCancelar))
		{
			ventana.setVisible(false);
		}
		else if(evento.getSource().equals(btnCancelar2))
		{
			ventana.setVisible(false);
			ventanaEdicion.setVisible(false);
		}
		else if(evento.getSource().equals(btnEditar))
		{
			ventanaEdicion.setLayout(new FlowLayout());
			
			// Capturar los datos del elemento elegido del List
			String[] valores = listTorneo.getSelectedItem().split("-");
			ventanaEdicion.add(lblidTorneo);
			txtId.setEnabled(false);
			txtId.setText(valores[0]);
			ventanaEdicion.add(txtId);
			ventanaEdicion.add(lblNombreTorneo);
			txtNombre.setText(valores[1]);
			ventanaEdicion.add(txtNombre);
			ventanaEdicion.add(lblPremioTorneo);
			txtPremio.setText(valores[2]);
			ventanaEdicion.add(txtPremio);
			ventanaEdicion.add(btnAceptar);
			ventanaEdicion.add(btnCancelar2);

			ventanaEdicion.setSize(200,250);
			ventanaEdicion.setResizable(false);
			ventanaEdicion.setLocationRelativeTo(null);
			
			ventanaEdicion.setVisible(true);
		}
		else if(evento.getSource().equals(btnAceptar))
		{
			// Conectar
			bd = new BaseDatoseSports();
			connection = bd.conectar();
			
			sentencia = "UPDATE torneos SET nombreTorneo='"
					+txtNombre.getText()+"', premioTorneo='"
					+txtPremio.getText()+"' WHERE idTorneo="
					+txtId.getText();
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				//Crear un objeto ResultSet para guardar lo obtenido
				//y ejecutar la sentencia SQL
				statement.executeUpdate(sentencia);
				lblMensaje.setText("Actualización de Torneo Correcta");
			}
			catch (SQLException sqle)
			{
				System.out.println(sentencia);
				lblMensaje.setText("Error");
			}
			finally
			{
				dlgMensajeModificacionTorneo.setLayout(new FlowLayout());
				
				dlgMensajeModificacionTorneo.setSize(220,100);
				dlgMensajeModificacionTorneo.setResizable(false);
				dlgMensajeModificacionTorneo.setLocationRelativeTo(null);
				dlgMensajeModificacionTorneo.add(lblMensaje);
				dlgMensajeModificacionTorneo.setVisible(true);
				bd.desconectar(connection);
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
	public void windowActivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		ventana.setVisible(false);
		ventanaEdicion.setVisible(false);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0)
	{
		// TODO Auto-generated method stub

	}

}