package doharm.logic.entities.characters;

import doharm.logic.chat.Message;
import doharm.logic.chat.MessagePart;
import doharm.logic.chat.Taunts;
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
	private long spawnTime;
	private Character attackedBy;
	private CharacterType characterType;
	
	protected Character(CharacterType characterType) 
	{
		super(EntityType.CHARACTER);
		this.characterType = characterType;
		inventory = new Inventory();
		
	}
	
	public Taunts getTaunts() 
	{
		return characterClass.getTaunts();
	}
	
	public void setCharacterClass(CharacterClassType classType)
	{
		switch(classType)
		{
		case WARRIOR:
			characterClass = new Warrior(this);
			break;
		default:
			throw new UnsupportedOperationException("Character class type not implemented: " + classType);
		}
	}
	

	public Inventory getInventory()
	{
		return inventory;
	}
	
	public void resetAttackedBy()
	{
		attackedBy = null;
	}
	public Character getAttackedBy()
	{
		return attackedBy;
	}
	
	@Override
	public void process()
	{
		if (!isAlive())
			return;
		characterClass.process();
		
		health += characterClass.getAttributes().getHealthRegeneration();
		health = Math.min(health, getMaxHealth());
		
		
		state.process(this);
		
		super.process();
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
	
	
	
	
	@Override
	public void spawn(Tile spawnTile)
	{
		super.spawn(spawnTile);
		
		state = new IdleState();
		
		//Vector position = getPosition();
		resetAttackedBy();
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
	
	public float getRage() 
	{
		return rage;
	}
	
	public float getRageRatio()
	{
		return rage / getMaxRage();
	}
	public float getMaxRage()
	{
		return characterClass.getAttributes().getMaxRage();
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
	
	public float getManaRatio()
	{
		if (getMaxMana() == 0)
			return 0;
		
		return rage / getMaxMana();
	}
	
	private float getMaxMana() {
		return characterClass.getAttributes().getMaxMana();
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
		attackedBy = attacker;
		
		if (!isAlive())
			return;
		
		health -= damage;
		rage += damage;
		if (health <= 0)
		{
			//kill me now
			health = 0;
			
			//drop all the character's items and gold
			
			
			
			int exp = calculateExperience();
			
			
			
			attacker.addExperience(exp);//attacker gets double exp
			
			if (attacker.getAlliance() != null)
			{
				for (Character ally: attacker.getAlliance().getCharacters())
				{
					ally.setState(new IdleState());
					ally.addExperience(exp);
				}
				
			}
			
			spawnTime = System.currentTimeMillis() + Math.max((int)(1000*(Math.pow(characterClass.getLevel(), 2))),5000);
			
			getWorld().addMessage(new Message(-1, new MessagePart(attacker.getName() + " killed " + getName()+".")));
			
			die();
			
		}
		
	}
	
	public String getName()
	{
		return name;
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
		if (getTimeTillSpawn() == 0)
		{
			spawn(getWorld().getRandomEmptyTile());
		}
	}

	public int getTimeTillSpawn() 
	{
		return Math.max((int)(spawnTime - System.currentTimeMillis()),0);
	}

	public boolean isHumanPlayer() 
	{
		return this == getWorld().getHumanPlayer();
	}

	public CharacterType getCharacterType()
	{
		return characterType;
	}
	
}
