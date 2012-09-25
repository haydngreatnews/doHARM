package doharm.logic.gameobjects.entities;

import java.awt.Dimension;
import java.util.PriorityQueue;

import doharm.logic.gameobjects.GameObject;
import doharm.logic.physics.Vector;
import doharm.logic.world.Layer;
import doharm.logic.world.tiles.Tile;

public abstract class Entity implements GameObject 
{
	protected Vector position;
	protected Vector destination;
	protected Vector velocity;
	private Dimension size;
	private float angle;
	private float friction = 0.98f;
	
	private Layer currentLayer;
	private Tile currentTile;
	

	public Entity(Tile spawnTile)
	{
		this.currentTile = spawnTile;
		currentLayer = currentTile.getLayer();
		position = new Vector(spawnTile.getX(), spawnTile.getY());
		
		destination = new Vector(position);
		velocity = new Vector();
		size = new Dimension(32,32); //urgh
		angle = 0;
	}
	
	public Dimension getSize()
	{
		return new Dimension(size);
	}
	
	public Vector getPosition() 
	{
		return new Vector(position);
	}

	public Vector getVelocity() 
	{
		return new Vector(velocity);
	}
	
	public Vector getDestination() 
	{
		return new Vector(destination);
	}

	public float getAngle() 
	{
		return angle;
	}
	
	public void move()
	{
		abstractMove();
		
		
		Vector direction = destination.subtract(position);
		if (direction.getLength() > 3)
		{
			direction.normalize();
			velocity.set(direction);
			velocity.multiply(5);
		}
		else //if (!position.equals(destination))
		{
			//position.set(destination);
			velocity.reset();
		}
		
		
		
		//Tile newTile = currentLayer.getTileAt(position.getX()+velocity.getX(),position.getY()+velocity.getY());
		
		
		position.add(velocity);

		currentTile = currentLayer.getTileAt(position.getX(), position.getY());
		currentLayer = currentTile.getLayer();
		
		
		checkCollisions();
		
		
		
		/*if (newTile.isWalkable())
		{
			
			
			velocity.multiply(friction);
			
			relativePosition.set();
			
		}
		else
		{
			velocity.reset();
		}*/
		
		//System.out.println(currentTile.getRow()+","+ currentTile.getCol());
		
	}
	
	private void checkCollisions() 
	{
		
		
	}

	protected abstract void abstractMove();
	
	
	public void moveTo(Tile goal) 
	{
		//calculate path...
		PriorityQueue<Tile> queue = new PriorityQueue<Tile>();
		
		queue.add(currentTile);
		
		
		
		
		while (!queue.isEmpty())
		{
			Tile node = queue.poll();
			
			if (node == goal)
				break;

		}
		
		
		destination.set(goal.getMidX(),goal.getMidY());
	}
	
	public Layer getCurrentLayer()
	{
		return currentTile.getLayer();
	}
	
	public Tile getCurrentTile()
	{
		return currentTile;
	}
	
	
}
