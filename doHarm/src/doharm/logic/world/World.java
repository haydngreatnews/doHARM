package doharm.logic.world;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import doharm.logic.camera.Camera;
import doharm.logic.gameobjects.entities.characters.players.HumanPlayer;
import doharm.logic.gameobjects.entities.characters.players.Player;
import doharm.logic.gameobjects.entities.characters.players.PlayerFactory;
import doharm.storage.WorldLoader;


public class World 
{
	Layer[] layers;  
	private Set<Player> players;
	private HumanPlayer humanPlayer;
	private Camera camera;
	private WorldLoader worldLoader;
	
	public World(String worldName, Camera camera)
	{
		this.camera = camera;
		players = new HashSet<Player>();
		try 
		{
			worldLoader = new WorldLoader(worldName);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		
		layers = new Layer[worldLoader.getNumLayers()];
		for (int i = 0; i < layers.length; i++)
			layers[i] = new Layer(worldLoader, i);
		
		
		humanPlayer = PlayerFactory.createHumanPlayer(layers[0].getTiles()[5][5],"Test player", 0);
		players.add(humanPlayer);
	}

	public void moveEntities() 
	{
		for (Player p: players)
		{
			p.move();
		}
		if (humanPlayer != null)
		{
			camera.setPosition(humanPlayer.getPosition().getX(), humanPlayer.getPosition().getY());
		}
	}
	
	public Collection<Player> getPlayers()
	{
		return Collections.unmodifiableCollection(players);
	}
	
	
	
	public void moveHumanPlayer(int x, int y) 
	{
		if (humanPlayer == null)
			return;
		humanPlayer.moveTo(x+camera.getRenderPosition().getXAsInt(), y+camera.getRenderPosition().getYAsInt());
		
	}

	public WorldLoader getWorldLoader() 
	{
		return worldLoader;
	}
	
	public Layer getLayer(int number)
	{
		return layers[number];
	}
	
	public Layer[] getLayers()
	{
		return layers;
	}
	
	public int getNumLayers()
	{
		return layers.length;
	}
}
