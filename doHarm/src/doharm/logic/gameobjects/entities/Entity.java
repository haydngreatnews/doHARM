package doharm.logic.gameobjects.entities;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import doharm.logic.gameobjects.GameObject;
import doharm.logic.physics.Vector;
import doharm.logic.world.Layer;
import doharm.logic.world.tiles.Tile;

public abstract class Entity implements GameObject 
{
	private static final float MIN_DISTANCE = 2;
	protected Vector position;
	protected Vector destination;
	protected Vector velocity;
	private Dimension size;
	/** Angle this entity is facing */
	private float angle;
	private float friction = 0.6f;
	private float movementSpeed = 1.7f;
	private float stopFriction = 0.1f;
	
	private Layer currentLayer;
	private Tile currentTile;
	private Stack<Tile> path;
	private Vector goal;
	

	public Entity(Tile spawnTile)
	{
		this.currentTile = spawnTile;
		currentLayer = currentTile.getLayer();
		position = new Vector(spawnTile.getX(), spawnTile.getY());
		
		goal = new Vector(position);
		destination = new Vector(position);
		velocity = new Vector();
		size = new Dimension(32,32);
		angle = 0;
		path = new Stack<Tile>();
	}
	
	public Stack<Tile> getPath()
	{
		Stack<Tile> temp = new Stack<Tile>();
		for (Tile tile: path)
			temp.push(tile);
		
		return temp;
	}
	
	public Dimension getSize()
	{
		return new Dimension(size);
	}
	
	public Vector getPosition() 
	{
		return new Vector(position);
	}
	
	public Vector getGoal() 
	{
		return new Vector(goal);
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
		
		float distanceToDestination = direction.getLength();
		if (distanceToDestination > MIN_DISTANCE)
		{
			direction.normalize();
			direction.multiply(movementSpeed);
			velocity.add(direction);
		}
		else
		{
			if (!path.isEmpty())
				nextNodeInPath();
			else
			{
				velocity.multiply(stopFriction);
			}
			
				
		}
		
		
		position.add(velocity);
		
		
		
		//Tile newTile = currentLayer.getTileAt(position.getX()+velocity.getX(),position.getY()+velocity.getY());
		
		
		
			
		
			

		currentTile = currentLayer.getTileAt(position.getX(), position.getY());
		currentLayer = currentTile.getLayer();
		
		
		checkCollisions();
		
		
		velocity.multiply(friction);
		
		
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
	
	private void nextNodeInPath() 
	{
		if (!path.isEmpty())
		{
			
			if (path.size()== 1)
			{
				Tile next = path.pop();
				destination.set(next.getX(), next.getY());
			}
			else if (path.size()== 2)
			{
				//average between next target and the one after
				Tile next = path.pop();
				Tile nextNext = path.peek();
				float x = (next.getX()+nextNext.getX())*0.5f;
				float y = (next.getY()+nextNext.getY())*0.5f;
				destination.set(x, y);
			}
			else
			{
				//average between next target and the one after
				Tile next = path.pop();
				Tile nextNext = path.pop();
				Tile nextNextNext = path.peek();
				float x = (next.getX()+nextNext.getX() + nextNextNext.getX())/3;
				float y = (next.getY()+nextNext.getY() + nextNextNext.getY())/3;
				destination.set(x, y);
				path.push(nextNext);
			}
		}
	}

	private void checkCollisions() 
	{
		//TODO
		
	}

	protected abstract void abstractMove();
	
	
	/**
	 * move to a tile on the world
	 * World.resetTiles(); must be called before this method so that the tiles haven't been visited!
	 * @param goal the tile to move to
	 */
	public void moveTo(Tile goal) 
	{
		if (!goal.isWalkable())
		{
			int tries = 0;
			//find closest walkable tile
			Queue<Tile> possibilities = new LinkedList<Tile>();
			
			possibilities.offer(goal);
			
			int maxTries = 100;
			while(tries < maxTries)
			{
				Tile possibility = possibilities.poll();
				
				if (possibility.isWalkable())
				{
					goal = possibility;
					break;
				}
				
				for (Tile neighbour: possibility.getNeighbours())	
					possibilities.offer(neighbour);
				
				
				tries++;
			}
			
		
			
			if (!goal.isWalkable())
				return;
		}
		
		
		//calculate path...
		
		
		PriorityQueue<Tile> queue = new PriorityQueue<Tile>();
		
		queue.add(currentTile);

		//boolean foundGoal = false;
		//System.out.println("Goal: " + goal.getRow() +","+goal.getCol());
		while (!queue.isEmpty())
		{
			Tile node = queue.poll();
			//System.out.println("Current: " + node.getRow()+","+node.getCol());
			
			/*System.out.println("Neighbours of " + node.getRow() +","+node.getCol()+":");
			
			for (Tile n: node.getNeighbours())
			{
				System.out.println(n.getRow() +","+n.getCol());
			}*/
			
			
			if (node == goal)
			{
				path.clear();
				this.goal.set(goal.getX(), goal.getY());
				//foundGoal = true;
				while (node != currentTile)
				{
					path.push(node);
					node = node.getParent();
				}
				
				break;
			}
			
			


			for (Tile neighbour: node.getNeighbours())
			{
				
				if (!neighbour.isVisited() && neighbour.isWalkable() && !neighbour.isNextToWall())
				{
					
					
					neighbour.setVisited(true);
					neighbour.setParent(node);
					
					neighbour.setPathLength(node.getPathLength() + node.distanceToTile(neighbour));
					queue.offer(neighbour);
				}
			}
			
		}
		//System.out.println("Found goal: "+foundGoal);
		

		nextNodeInPath();
		
		
		//destination.set(goal.getMidX(),goal.getMidY());
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
