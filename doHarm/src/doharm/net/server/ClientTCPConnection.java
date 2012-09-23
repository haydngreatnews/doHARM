package doharm.net.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientTCPConnection extends Thread {

	private Socket socket;
	private Server server;
	
	public ClientTCPConnection(Server server, Socket socket)
	{
		this.socket = socket;
		this.server = server;
	}
	
	public void run()
	{
		try
		{
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
			
			output.println("STATIC MAP DATAS");
			
			output.println("###DONE");
			
			socket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			//server.tcpFinished(this);
		}
	}
	
}
