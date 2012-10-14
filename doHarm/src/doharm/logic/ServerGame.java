package doharm.logic;

import doharm.net.NetworkMode;
import doharm.net.server.Server;

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
		
		//TODO Create server
		//server = new Server(port);
		
		
	}
}
