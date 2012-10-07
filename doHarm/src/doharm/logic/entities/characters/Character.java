package doharm.logic.entities.characters;

import java.util.Collection;
import java.util.Collections;
import java.util.Stack;

import doharm.logic.entities.AbstractEntity;
import doharm.logic.entities.EntityType;
import doharm.logic.entities.characters.alliances.Alliance;
import doharm.logic.entities.characters.classes.CharacterClass;
import doharm.logic.entities.characters.classes.CharacterClassType;
import doharm.logic.entities.characters.classes.warrior.Warrior;
import doharm.logic.entities.inventory.Inventory;
import doharm.logic.physics.Vector;
import doharm.logic.world.tiles.PathFinder;
import doharm.logic.world.tiles.Tile;

public abstract class Character extends AbstractEntity
{
	//private static final float MIN_DISTANCE = 2;
	private String name;
	private CharacterClass characterClass;

	private Inventory inventory;
	private Stack<Tile> path;
	
	private Vector goal;
	private Vector destination;
	
	private float movementSpeed = 3f;//1.7f;
	private float stopFriction = 0.1f;
	
	private float health;
	private float mana;
	private float rage;
	private Alliance alliance;
	private Action currentAction;
	
	
	protected Character() 
	{
		super(EntityType.CHARACTER);
		inventory = new Inventory();
		currentAction = Action.IDLE;
	}
	
	public void setCharacterClass(CharacterClassType classType)
	{
		switch(classType)
		{
		case WARRIOR:
			characterClass = new Warrior(this,classType);
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
		
		
		if (!path.isEmpty())
		{
			currentAction = Action.MOVING;
		}
		else if (currentAction == Action.MOVING)
			currentAction = Action.IDLE;
		
		if (distanceToDestination < movementSpeed)
		{
			if (!path.isEmpty())
				nextNodeInPath();
		}
		
		
		if (distanceToDestination > movementSpeed)
		{
			direction.multiply(1, 2);
			direction.normalize();
			direction.multiply(movementSpeed);
			velocity.add(direction);
		}
		else if (path.isEmpty())
		{
			velocity.multiply(stopFriction);
		}
		
		setVelocity(velocity);
		super.move();
	}
	
	public Action getCurrentAction()
	{
		return currentAction;
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
		health = getMaxHealth();
	}
	
	public Collection<Tile> getPath()
	{
		return Collections.unmodifiableCollection(path);
	}
	
	public void moveTo(Tile goal) 
	{
		
		Stack<Tile> path = PathFinder.calculatePath(getWorld(), getCurrentTile(), goal);
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
			
			//if (path.size()== 1)
			{
				Tile next = path.pop();
				destination.set(next.getX(), next.getY());
			}
			/*else// if (path.size()== 2)
			{
				//average between next target and the one after
				Tile next = path.pop();
				Tile nextNext = path.peek();
				float x = (next.getX()+nextNext.getX())*0.5f;
				float y = (next.getY()+nextNext.getY())*0.5f;
				destination.set(x, y);
			}*/
			/*else
			{
				//average between next target and the one after
				Tile next = path.pop();
				Tile nextNext = path.pop();
				Tile nextNextNext = path.peek();
				float x = (next.getX()+nextNext.getX()*2 + nextNextNext.getX())/4;
				float y = (next.getY()+nextNext.getY()*2 + nextNextNext.getY())/4;
				destination.set(x, y);
				path.push(nextNext);
			}*/
		}
	}
	
	public Vector getDestination() 
	{
		return new Vector(destination);
	}
	
	public float getHealth()
	{
		return health;
	}
	public void setHealth(float health)
	{
		this.health = health;
	}
	
	public float getHealthRatio() 
	{
		return health / getMaxHealth();
	}
	
	public float getMaxHealth()
	{
		return characterClass.getAttributes().getMaxHealth();
	}
	
	public float getRage() {
		return rage;
	}
	public void setRage(float rage)
	{
		this.rage = rage;
	}
	
	public float getMana() {
		return mana;
	}
	public void setMana(float mana)
	{
		this.mana = mana;
	}
	
	public void attack(AbstractEntity e)
	{
		//getW
		currentAction = Action.ATTACK;
	}

	public Alliance getAlliance() {
		return alliance;
	}
	
	public void setAlliance(Alliance alliance)
	{
		this.alliance = alliance;
	}
	
	
}
