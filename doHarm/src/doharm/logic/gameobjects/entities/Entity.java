package doharm.logic.gameobjects.entities;

import java.awt.Dimension;

import doharm.logic.gameobjects.GameObject;
import doharm.logic.physics.Vector;

public abstract class Entity implements GameObject 
{
	protected Vector position;
	protected Vector destination;
	protected Vector velocity;
	private Dimension size;
	private float angle;
	private float friction = 0.98f;
	

	public Entity()
	{
		position = new Vector();
		destination = new Vector();
		velocity = new Vector();
		size = new Dimension(32,32);
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
		else if (!position.equals(destination))
		{
			position.set(destination);
			velocity.reset();
		}
		
		position.add(velocity);
		velocity.multiply(friction);
		
		
		
		
		
	}
	
	protected abstract void abstractMove();
	
	
	public void moveTo(float x, float y) 
	{
		destination.set(x,y);
	}
}
