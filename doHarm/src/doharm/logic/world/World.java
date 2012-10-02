package doharm.logic.world;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import doharm.logic.camera.Camera;
import doharm.logic.entities.Entity;
import doharm.logic.entities.characters.classes.CharacterClassType;
import doharm.logic.entities.characters.players.AIPlayer;
import doharm.logic.entities.characters.players.HumanPlayer;
import doharm.logic.entities.characters.players.Player;
import doharm.logic.entities.characters.players.PlayerFactory;
import doharm.logic.entities.characters.players.PlayerType;
import doharm.logic.entities.items.Item;
import doharm.logic.world.tiles.Direction;
import doharm.logic.world.tiles.Tile;
import doharm.storage.FloorTileData;
import doharm.storage.TilesetLoader;
import doharm.storage.WallTileData;
import doharm.storage.WorldLoader;


public class World 
{
	private Layer[] layers;  
	private Set<Player> players;
	private Set<Character> characters;
	private Set<Entity> entities;
	private Set<Item> items;
	
	private PlayerFactory playerFactory;
	
	
	private HumanPlayer humanPlayer;
	private Camera camera;
	private WorldLoader worldLoader;
	private int tileWidth;
	private int tileHeight;
	private int numRows;
	private int numCols;
	
	public World(String worldName)
	{
		playerFactory = new PlayerFactory(this);
		
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
		this.tileWidth = tsl.getFloorTileWidth();
		this.tileHeight = tsl.getFloorTileHeight();
		
		camera = new Camera(tileWidth, tileHeight);
		
		layers = new Layer[worldLoader.getNumLayers()];
		for (int i = 0; i < layers.length; i++)
			layers[i] = new Layer(this, i);
		
		linkTiles();
		
		
		
		//TEST STUFF TODO REMOVE
		humanPlayer = (HumanPlayer)playerFactory.createPlayer(layers[0].getTiles()[5][5],"Test player",CharacterClassType.WARRIOR, 0,PlayerType.HUMAN);
		players.add(humanPlayer);
		
		
		//Add some noob AIs
		for (int i = 0; i < 3; i++)
		{
			Tile tile = null;
			do
			{
				int r = (int)(Math.random()*numRows-2);
				int c = (int)(Math.random()*numCols-2);
				if (r < 2) r = 2;
				if (c < 2) c = 2;
				tile = layers[0].getTiles()[r][c];
			} while(!tile.isWalkable());
			
			
			AIPlayer ai = (AIPlayer)playerFactory.createPlayer(tile, "AI"+(i+1), CharacterClassType.WARRIOR, i+1,PlayerType.AI);
			players.add(ai);
		}
		
		
		//TODO add some random items!!!!!!!!
		
		
		for (int i = 0; i < 3; i++)
		{
			Tile tile = null;
			do
			{
				int r = (int)(Math.random()*numRows-2);
				int c = (int)(Math.random()*numCols-2);
				if (r < 2) r = 2;
				if (c < 2) c = 2;
				tile = layers[0].getTiles()[r][c];
			} while(!tile.isWalkable());
			
			
			
			//players.add(ai);
		}
		
		
	}

	private void linkTiles() 
	{
		TilesetLoader tilesetLoader = worldLoader.getTilesetLoader();
		WallTileData tempWallData = tilesetLoader.getWallTileData(0);
		
		for (Layer layer: layers)
		{
			Tile[][] tiles = layer.getTiles();
			for (int row = 0; row < tiles.length; row++)
			{
				for (int col = 0; col < tiles[0].length; col++)
				{
					for (int x = -1; x <= 1; x++)
					{
						for (int y = -1; y <= 1; y++)
						{
							//if (layer == 0)
							{
								tiles[row][col].setWall(Direction.UP, tempWallData);
								tiles[row][col].setWall(Direction.RIGHT, tempWallData);
								tiles[row][col].setWall(Direction.DOWN, tempWallData);
								tiles[row][col].setWall(Direction.LEFT, tempWallData);
								
							}
							
							
							if (x == 0 && y == 0)
								continue; //TODO check upper/lower levels
							
							if (row + y >= 0 && row + y < tiles.length && 
								col + x >= 0 && col + x < tiles[0].length)
							{
								tiles[row][col].addNeighbour(tiles[row+y][col+x]);
							}
							
						}
					}
				}
			}
			
			
			
			
		}
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

	public float getTileWidth() 
	{
		return tileWidth;
	}
	public float getTileHeight() 
	{
		return tileHeight;
	}

	public void resetTiles(Tile goal) 
	{
		for (Layer layer: layers)
		{
			for (Tile[] row: layer.getTiles())
			{
				for (Tile t: row)
				{
					t.setVisited(false);
					t.calculateHeuristic(goal);
					t.setPathLength(0);
					t.setParent(null);
				}
			}
		}
		
	}
}
