package doharm.logic.entities.characters;

import java.util.Collection;
import java.util.Collections;
import java.util.Stack;

import doharm.logic.entities.AbstractEntity;
import doharm.logic.entities.characters.attributes.Level;
import doharm.logic.entities.characters.classes.CharacterClass;
import doharm.logic.entities.characters.classes.CharacterClassType;
import doharm.logic.entities.characters.classes.Warrior;
import doharm.logic.entities.inventory.Inventory;
import doharm.logic.physics.Vector;
import doharm.logic.world.tiles.PathFinder;
import doharm.logic.world.tiles.Tile;

public abstract class Character extends AbstractEntity
{
	private static final float MIN_DISTANCE = 2;
	private String name;
	private CharacterClass characterClass;
	private Level level;
	private Inventory inventory;
	private Stack<Tile> path;
	
	private Vector goal;
	private Vector destination;
	
	private float movementSpeed = 1.7f;
	private float stopFriction = 0.1f;
	
	
	protected Character() 
	{
		inventory = new Inventory();
	}
	
	public void setCharacterClass(CharacterClassType classType)
	{
		switch(classType)
		{
		case WARRIOR:
			characterClass = new Warrior();
			break;
		default:
			throw new UnsupportedOperationException("Character class type not implemented: " + classType);
		}
	}
	

	public Inventory getInventory()
	{
		return inventory;
	}
	
	@Override
	public void move()
	{
		Vector direction = destination.subtract(getPosition());
		Vector velocity = getVelocity();
		
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
		
		setVelocity(velocity);
		super.move();
	}

	public void setName(String name) 
	{
		this.name = name;
	}
	
	public Vector getGoal() 
	{
		return new Vector(goal);
	}
	
	public void spawn(Tile spawnTile)
	{
		super.spawn(spawnTile);
		
		Vector position = getPosition();
		goal = new Vector(position);
		destination = new Vector(position);
		path = new Stack<Tile>();
	}
	
	public Collection<Tile> getPath()
	{
		return Collections.unmodifiableCollection(path);
	}
	
	public void moveTo(Tile goal) 
	{
		Stack<Tile> path = PathFinder.calculatePath(getCurrentTile(), goal);
		if (path == null)
			return;
		
		this.path = path;
		goal = path.pop(); //get modified goal
		this.goal.set(goal.getX(), goal.getY());
		
		nextNodeInPath();
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
	
	public Vector getDestination() 
	{
		return new Vector(destination);
	}
}
