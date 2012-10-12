package doharm.logic.world.tiles;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import doharm.logic.entities.AbstractEntity;
import doharm.logic.physics.Vector;
import doharm.logic.world.Layer;
import doharm.logic.world.World;
import doharm.rendering.RenderUtil;
import doharm.storage.FloorTileData;
import doharm.storage.WallTileData;

public class Tile implements Comparable<Tile>
{
	private FloorTileData floorData;
	private WallTileData[] walls;
	
	private BufferedImage pickImage;
	
	private Vector position;
	private Layer layer;
	private int row;
	private int col;
	private int imageNumber;
	private int switchImageTimer;
	private int width;
	private int height;
	
	/** calculated by map editor */
	private float staticLight;
	private float dynamicLight; //calculated by lights ingame.
	
	private boolean visible; //whether or not this tile is invisible (eg. "air")
	
	private World world;
	
	//Pathfinding variables
	private boolean visited;
	private float heuristic;
	private List<Tile> neighbours;
	private Tile parent;
	private float pathLength;
	private boolean nextToWall;
	private Set<AbstractEntity> entities;
	private Tile roof;
	
	
	public Tile(Layer layer, int row, int col, int width, int height, Vector position, FloorTileData data, int colour) 
	{
		this.width = width;
		this.height = height;
		this.row = row;
		this.col = col;
		this.layer = layer;
		this.position = position;
		this.floorData = data;
		
		world = layer.getWorld();
		
		dynamicLight = 0;
		staticLight = 0.3f;
		
		visible = floorData.getType() != 2;
		
		int red = 0xFF & ( colour >> 16);
		int green = 0xFF & (colour >> 8 );
		int blue = 0xFF & (colour >> 0 );
		

		Color color = new Color(red,green,blue);
		
		if (isWalkable())
			this.pickImage = RenderUtil.generateIsoImage(color, width,height);
		
		switchImageTimer = floorData.getNumFramesPerImage();
		neighbours = new ArrayList<Tile>();
		
		walls = new WallTileData[Direction.values().length];
		entities = new HashSet<AbstractEntity>();
	}
	
	/**
	 * @return a number between 0 and 9 inclusive, where 0 is pitch black and 9 is fully lit.
	 */
	public float getLight()
	{
		float light = 0.99f * world.getTime().getLight();//Math.max(staticLight + dynamicLight,1);

		
		light = Math.min(Math.max(light, 0),1);
		
		return light;
	}
	
	
	public BufferedImage getPickImage()
	{
		return pickImage;
	}
	

	public int getImageID() 
	{
		if (switchImageTimer == 0)
		{
			switchImageTimer = floorData.getNumFramesPerImage();
			imageNumber = (imageNumber + 1) % floorData.getNumImages();
		}
		else
			switchImageTimer--;
		
		
		return floorData.getImageID(imageNumber); //TODO
	}

	public int getX()
	{
		return position.getXAsInt();
	}
	public int getY()
	{
		return position.getYAsInt();
	}
	
	/*public int getMidX()
	{
		return position.getXAsInt()+width/2;
	}
	
	public int getMidY()
	{
		return position.getYAsInt()+height/2;
	}*/
	
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}

	/*public Vector getPosition() 
	{
		return position;
	}*/
	
	public Layer getLayer()
	{
		return layer;
	}

	public int getRow() 
	{
		return row;
	}
	public int getCol() 
	{
		return col;
	}
	
	public boolean isWalkable()
	{
		return floorData.getType() == 1; //TODO
	}
	
	
	
	
	@Override
	public int compareTo(Tile t)
	{
		float value = (heuristic+pathLength) - (t.heuristic+t.pathLength);
		if (value < 0)
			value = -1;
		else if (value > 0)
			value = 1;
		
		return (int)value;
	}

	public boolean isNextToWall()
	{
		return nextToWall;
	}
	public boolean isVisited() 
	{
		return visited;
	}
	public void setVisited(boolean visited)
	{
		this.visited = visited;
	}

	public float getHeuristic() 
	{
		return heuristic;
	}

	public void calculateHeuristic(Tile goal) 
	{
		//omg need multi-layer A* algorithm and heuristic
		heuristic = distanceToTile(goal);
	}
	
	public float distanceToTile(Tile goal)
	{
		int r = goal.row-row;
		int c = goal.col-col;
		int l = goal.layer.getLayerNumber() - layer.getLayerNumber();
		
		return (float) Math.sqrt(r*r + c*c + l*l);
				
				//(float)Math.hypot(goal.getX()-getX(), goal.getY()-getY());
	}

	public List<Tile> getNeighbours() 
	{
		return Collections.unmodifiableList(neighbours);	
	}
	public void addNeighbour(Tile neighbour) 
	{
		for (Tile n: neighbours)
		{
			if (n.col == col && n.row == row && n.layer.getLayerNumber() == layer.getLayerNumber())
				return;
		}
		
		if (!neighbour.isWalkable())// && neighbour.col == col && neighbour.row == row)
			nextToWall = true;
		
		neighbours.add(neighbour);
	}

	public void setParent(Tile parent) 
	{
		this.parent = parent;
	}
	
	public Tile getParent() 
	{
		return this.parent;
	}

	public float getPathLength() 
	{
		return pathLength;
	}

	public void setPathLength(float pathLength) 
	{
		this.pathLength = pathLength;
	}


	public void setWall(Direction direction, WallTileData data) 
	{
		walls[direction.ordinal()] = data;
	}


	public int getWallImageID(Direction direction) 
	{
		return 0;//walls[direction.ordinal()].;
	}


	public Set<AbstractEntity> getEntities() 
	{
		return entities;
	}


	public boolean isEmpty() 
	{
		return entities.isEmpty();
	}


	public void removeEntity(AbstractEntity entity) 
	{
		entities.remove(entity);
	}

	public void addEntity(AbstractEntity entity) 
	{
		entities.add(entity);
	}

	public void setRoof(Tile tile) {
		roof = tile;
	}
	public Tile getRoof()
	{
		return roof;
	}

	public boolean isVisible() {
		return visible;
	}
	

	
	
	
}
