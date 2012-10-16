package doharm.logic;

import java.util.ArrayList;

import doharm.net.NetworkMode;
import doharm.net.server.ConnectedClient;
import doharm.net.server.Server;

/**
 * 
 * @author Roland Bewick and Adam McLaren (300248714)
 *
 */
public class ServerGame extends AbstractGame
{
	private Server server;
	
	/**
	 * Create game for SERVER
	 * @param port the port we want to host on
	 */
	public ServerGame(int port)
	{
		super(NetworkMode.SERVER);
		server = new Server(port, getWorld());
	}
	
	
	@Override
	public void run()
	{
		server.processIncomingPackets();
		server.tick();
		server.moveClients();
		// TODO get client actions; update pos based on client fields, and then perform commands.
		
		super.run();
		
		server.checkClients();
		server.dispatchSnapshots();
		
	}
}
