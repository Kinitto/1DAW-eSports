package es.studium.eSports;

import java.awt.Button;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class AltaParticipantes implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Alta Participantes (1/2)");
	Label lblNombreTorneo = new Label("Elegir Torneo:");
	Choice choTorneo = new Choice();
	Button btnSiguiente = new Button("Siguiente...");

	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	BaseDatoseSports bd = new BaseDatoseSports();

	public AltaParticipantes()
	{
		ventana.setLayout(new FlowLayout());
		ventana.add(lblNombreTorneo);
		// Conectar BD
		connection = bd.conectar();
		// Obtener todos los torneos
		// Hacer un SELECT * FROM torneos
		sentencia = "SELECT * FROM Torneos;";
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			choTorneo.removeAll();
			while(rs.next())
			{
				choTorneo.add(rs.getInt("idTorneo")
						+"-"+rs.getString("nombreTorneo"));
			}
		}
		catch (SQLException sqle)
		{
		}

		ventana.add(choTorneo);
		btnSiguiente.addActionListener(this);
		ventana.add(btnSiguiente);
		ventana.setSize(230,120);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.addWindowListener(this);
		ventana.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		int idTorneo = Integer.parseInt(choTorneo.getSelectedItem().split("-")[0]);
		new AltaParticipantes2(idTorneo);
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