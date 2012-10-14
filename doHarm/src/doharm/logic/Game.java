package doharm.logic;

import java.awt.Color;

import doharm.gui.view.MainWindow;
import doharm.logic.camera.Camera;
import doharm.logic.entities.characters.classes.CharacterClassType;
import doharm.logic.time.Clock;
import doharm.logic.time.Time;
import doharm.logic.weather.Weather;
import doharm.logic.world.World;
import doharm.logic.world.tiles.Tile;
import doharm.net.NetworkMode;
import doharm.net.client.Client;

public class Game 
{
	private Camera camera;
	private World world;
	private NetworkMode networkMode;
	
	private Clock clock;
	private Client client;
	
	
	
	private Game(NetworkMode mode)
	{
		this.networkMode = mode;
		
		
		String worldName = "world1";
		
		Time time = new Time();
		Weather weather = new Weather();
		
		world = new World(worldName, networkMode,time,weather);
		camera = world.getCamera();
		
		
		clock = new Clock(this);
		clock.start();
		
	}
	
	/**
	 * Create game for SERVER
	 * @param port
	 */
	public Game(int port)
	{
		this(NetworkMode.SERVER);
		
		//TODO Create server
		
		
		
	}
	
	/**
	 * Create game for testing purposes only!
	 */
	public Game()
	{
		this(NetworkMode.OFFLINE);
	}
	
	/**
	 * Create game for CLIENT
	 * @param window
	 * @param ip
	 * @param port
	 * @param type
	 * @param playerName
	 * @param playerColour
	 */
	public Game(MainWindow window, String ip, int port, CharacterClassType type, String playerName, Color playerColour, Client client)
	{
		this(NetworkMode.CLIENT);
		clock.setWindow(window);
		this.client = client;
		
		
		//TODO COnnect to server
		
		//AND check player name, get spawn position, etc.
		Tile spawnPos = null; //TODO!
		
		
		spawnPos = world.getLayers()[0].getTiles()[5][5]; //TODO REMOVE.
		
		world.createHumanPlayer(spawnPos, type, playerName, playerColour);
		
	}
	
	
	
	public Camera getCamera()
	{
		return camera;
	}
	

	public void run() 
	{
		world.process();
	}

	public World getWorld()
	{
		return world;
	}

	public NetworkMode getNetworkMode()
	{
		return networkMode;
	}
}
