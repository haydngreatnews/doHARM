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
import doharm.logic.entities.characters.states.CharacterState;
import doharm.logic.entities.characters.states.CharacterStateType;
import doharm.logic.entities.characters.states.IdleState;
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
	
	
	
	
	private float movementSpeed = 3f;//1.7f;
	
	
	private float health;
	private float mana;
	private float rage;
	private Alliance alliance;
	
	private CharacterState state;
	private double spawnTime;
	
	
	protected Character() 
	{
		super(EntityType.CHARACTER);
		inventory = new Inventory();
		state = new IdleState();
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
		characterClass.process();
		state.process(this);
		
		super.move();
	}
	
	public CharacterState getState()
	{
		return state;
	}
	public CharacterStateType getStateType()
	{
		return state.getStateType();
	}

	public void setName(String name) 
	{
		this.name = name;
	}
	
	
	
	
	
	public void spawn(Tile spawnTile)
	{
		super.spawn(spawnTile);
		
		//Vector position = getPosition();
		
		health = getMaxHealth();
	}
	
	
	
	
	/*public void moveTo(Tile goal) 
	{
		
		
	}*/
	
	
	
	/*public Vector getDestination() 
	{
		return new Vector(destination);
	}*/
	
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
	
	public float getExperienceRatio() 
	{
		return characterClass.getExperienceRatio();
	}
	
	public CharacterClass getCharacterClass()
	{
		return characterClass;
	}
	

	public Alliance getAlliance() {
		return alliance;
	}
	
	public void setAlliance(Alliance alliance)
	{
		this.alliance = alliance;
	}

	public void setState(CharacterState state) 
	{
		this.state = state;
	}

	public float getMovementSpeed() 
	{
		return movementSpeed; //err. TODO
	}

	public void receiveDamage(float damage, Character attacker) 
	{
		if (!isAlive())
			return;
		
		health -= damage;
		if (health <= 0)
		{
			//kill me now
			health = 0;
			
			//drop all the character's items and gold
			
			
			
			int exp = calculateExperience();
			//attacker gets double exp
			
			
			attacker.addExperience(exp);
			if (attacker.getAlliance() != null)
			{
				attacker.getAlliance().addExperience(exp);
				
			}
			
			spawnTime = System.currentTimeMillis() + 1000*(Math.pow(characterClass.getLevel(), 2));
			
			die();
			
		}
		
	}

	private int calculateExperience() 
	{
		return (int)(characterClass.getExperience() * 0.5f);
	}

	public void addExperience(int exp) 
	{
		characterClass.addExperience(exp);
	}
	public int getLevel() 
	{
		return characterClass.getLevel();
	}

	public void tryRespawn() 
	{
		if (System.currentTimeMillis() > spawnTime)
		{
			spawn(getWorld().getRandomEmptyTile());
		}
	}
	
}
