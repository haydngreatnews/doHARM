package doharm.logic.gameobjects.entities;

import java.awt.Dimension;

import doharm.logic.gameobjects.GameObject;
import doharm.logic.physics.Vector;
import doharm.logic.world.Layer;
import doharm.logic.world.Tile;

public abstract class Entity implements GameObject 
{
	protected Vector position;
	protected Vector relativePosition;
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
		relativePosition = new Vector();
		
		destination = new Vector(position);
		velocity = new Vector();
		size = new Dimension(32,32); //urgh
		angle = 0;
	}
	
	public Dimension getSize()
	{
		return size;
	}
	
	public Vector getPosition() 
	{
		return position;
	}

	public Vector getVelocity() 
	{
		return velocity;
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
		
		
		
		 
		
		
		
		Tile newTile = currentLayer.getTileAt(position.getX()+velocity.getX(),position.getY()+velocity.getY());
		if (newTile.isWalkable())
		{
			currentTile = newTile;
			position.add(velocity);
			velocity.multiply(friction);
			
			relativePosition.set(position.getX()-currentTile.getX(),position.getY()-currentTile.getY());
			currentLayer = currentTile.getLayer();
		}
		else
		{
			velocity.reset();
		}
		
		//System.out.println(currentTile.getRow()+","+ currentTile.getCol());
		
	}
	
	protected abstract void abstractMove();
	
	
	public void moveTo(float x, float y) 
	{
		destination.set(x,y);
	}
	
	public Layer getCurrentLayer()
	{
		return currentTile.getLayer();
	}
	
	public Tile getCurrentTile()
	{
		return currentTile;
	}
	
	public Vector getPositionRelativeToTile()
	{
		return relativePosition;
	}
}
