package doharm.logic.world.tiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import doharm.logic.physics.Vector;
import doharm.logic.world.Layer;
import doharm.storage.TileData;

public class Tile implements Comparable<Tile>
{
	public TileData tileData;
	Vector position;
	private Layer layer;
	private int row;
	private int col;
	private int imageNumber;
	private int switchImageTimer;
	private int width;
	private int height;
	private boolean visited;
	private float heuristic;
	private List<Tile> neighbours;
	private Tile parent;
	private float pathLength;
	private boolean nextToWall;
	
	public Tile(Layer layer, int row, int col, int width, int height, Vector position, TileData data) 
	{
		this.width = width;
		this.height = height;
		this.row = row;
		this.col = col;
		this.layer = layer;
		this.position = position;
		this.tileData = data;
		switchImageTimer = tileData.getNumFramesPerImage();
		neighbours = new ArrayList<Tile>();
	}

	public int getImageID() 
	{
		if (switchImageTimer == 0)
		{
			switchImageTimer = tileData.getNumFramesPerImage();
			imageNumber = (imageNumber + 1) % tileData.getNumImages();
		}
		else
			switchImageTimer--;
		
		
		return tileData.getImageID(imageNumber); //TODO
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
		return tileData.getType() == 1; //TODO
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
		return (float)Math.hypot(goal.getX()-getX(), goal.getY()-getY());
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
		
		if (!neighbour.isWalkable())
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

	

	
	
	
}
