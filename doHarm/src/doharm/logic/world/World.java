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
import doharm.logic.physics.Vector;
import doharm.logic.world.tiles.Tile;
import doharm.storage.TilesetLoader;
import doharm.storage.WorldLoader;


public class World 
{
	Layer[] layers;  
	private Set<Player> players;
	private HumanPlayer humanPlayer;
	private Camera camera;
	private WorldLoader worldLoader;
	private int tileWidth;
	private int tileHeight;
	private int numRows;
	private int numCols;
	
	public World(String worldName)
	{
		
		players = new HashSet<Player>();
		try 
		{
			worldLoader = new WorldLoader(worldName);
			numRows = worldLoader.getNumTilesY();
			numCols = worldLoader.getNumTilesX();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		TilesetLoader tsl = worldLoader.getTilesetLoader();
		this.tileWidth = tsl.getTileWidth();
		this.tileHeight = tsl.getTileWidth();
		
		camera = new Camera(tileWidth, tileHeight);
		
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
	
	
	
	public void moveHumanPlayer(float x, float y) 
	{
		if (humanPlayer == null)
			return;
		
		
		humanPlayer.moveTo(x, y);
		
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

	public Camera getCamera() {
		return camera;
	}
	
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @param layer
	 * @return a rgb colour to draw a tile on the offscreen mouse picking image
	 * See OpenGL colour picking technique.
	 */
	
	public int getColour(int row, int col, int layer)
	{
		int colour = (row*numRows+col)+(layer*numRows*numCols);
		
		//System.out.println("row="+row +", col="+col + ", layer="+layer +", colour="+colour);
		
		return colour;
	}
	
	public Tile getTile(int colour)
	{
		int layerNumber = colour / (numRows*numCols);
		colour -= layerNumber * (numRows*numCols);
		int row = colour / numRows;
		colour -= row*numRows;
		int col = colour;
		
		
		Layer layer = getLayer(layerNumber);
		return layer.getTiles()[row][col];
	}

	public HumanPlayer getHumanPlayer() 
	{
		return humanPlayer;
	}

	public float getTileWidth() {
		return tileWidth;
	}
	public float getTileHeight() {
		return tileHeight;
	}
}
