package es.studium.eSports;

import java.awt.Button;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
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


public class AltaParticipantes2 implements WindowListener, ActionListener
{
	int idTorneoFK;
	Frame ventana = new Frame("Alta Participantes (2/2)");
	Label lblNombreJugador = new Label("Elegir Jugador:");
	Choice choJugadores = new Choice();
	Button btnAnadir = new Button("Añadir");
	TextArea txtaJugadores = new TextArea(5,30);
	Button btnFinalizar = new Button("Finalizar");

	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	BaseDatoseSports bd = new BaseDatoseSports();

	public AltaParticipantes2(int idParticipantesFK)
	{
		this.idTorneoFK = idParticipantesFK;
		ventana.setLayout(new FlowLayout());
		// Conectar BD
		connection = bd.conectar();
	
		sentencia = "SELECT * FROM jugadores;";
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			choJugadores.removeAll();
			while(rs.next())
			{
				choJugadores.add(rs.getInt("idJugador")
						+"-"+rs.getString("nombreJugador")
						+"-"+rs.getString("nacionalidadJugador"));
			}
		}
		catch (SQLException sqle)
		{
			System.out.println(sqle.getMessage());
		}

		ventana.add(choJugadores);
		btnAnadir.addActionListener(this);
		ventana.add(btnAnadir);
		actualizarTextArea();
		ventana.add(txtaJugadores);
		btnFinalizar.addActionListener(this);
		ventana.add(btnFinalizar);
		
		ventana.setSize(300,200);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.addWindowListener(this);
		ventana.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(btnAnadir))
		{
			int idJugador = Integer.parseInt(choJugadores.getSelectedItem().split("-")[0]);
			sentencia = "INSERT INTO participar VALUES(null,"+idJugador+","+ idTorneoFK+")";
			connection = bd.conectar();
			try
			{
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				statement.executeUpdate(sentencia);
				actualizarTextArea();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
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

	private void actualizarTextArea()
	{
		sentencia = "SELECT idParticipar, idJugadorFK, idTorneoFK, idJugador, nombreJugador, nacionalidadJugador "
				+ "FROM participar, jugadores WHERE "
				+ "participar.idJugadorFK = jugadores.idJugador AND "
				+ "idTorneoFK = "+idTorneoFK;
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			txtaJugadores.setText("");
			while(rs.next())
			{
				txtaJugadores.append(rs.getInt("idParticipar")
						+"-"+rs.getString("nombreJugador")
						+"-"+rs.getString("nacionalidadJugador")+"\n");
			}
		}
		catch (SQLException sqle)
		{
			System.out.println(sqle.getMessage());
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

