package doharm.logic.gameobjects.entities;

import java.awt.Dimension;

import doharm.logic.gameobjects.GameObject;
import doharm.logic.physics.Vector;

public abstract class Entity implements GameObject 
{
	private Vector position;
	private Vector velocity;
	private Dimension size;
	private float angle;

	public Entity()
	{
		position = new Vector();
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
	
}
