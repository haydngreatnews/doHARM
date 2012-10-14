package doharm.logic;

import java.awt.Color;

import doharm.gui.view.MainWindow;
import doharm.logic.entities.characters.classes.CharacterClassType;
import doharm.logic.world.tiles.Tile;
import doharm.net.NetworkMode;
import doharm.net.client.Client;

public class ClientGame extends AbstractGame
{
	private Client client;
	
	
	/**
	 * Create game for CLIENT
	 * @param window
	 * @param ip
	 * @param port
	 * @param type
	 * @param playerName
	 * @param playerColour
	 */
	public ClientGame(MainWindow window, CharacterClassType type, String playerName, Color playerColour, Client client)
	{
		super(NetworkMode.CLIENT);
		getClock().setWindow(window);
		this.client = client;
		
		
		//TODO COnnect to server
		
		//AND check player name, get spawn position, etc.
		Tile spawnPos = null; //TODO!
		
		
		spawnPos = getWorld().getLayers()[0].getTiles()[5][5]; //TODO REMOVE.
		
		
		//TODO make player but not spawn them until server sends spawn message?
		getWorld().createHumanPlayer(spawnPos, type, playerName, playerColour);
		
	}
	
}
