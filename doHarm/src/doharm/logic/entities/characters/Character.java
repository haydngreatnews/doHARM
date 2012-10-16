package doharm.logic.entities.characters;

import java.awt.Color;

import doharm.logic.chat.Message;
import doharm.logic.chat.MessagePart;
import doharm.logic.chat.Taunts;
import doharm.logic.entities.AbstractEntity;
import doharm.logic.entities.EntityType;
import doharm.logic.entities.characters.alliances.Alliance;
import doharm.logic.entities.characters.classes.CharacterClass;
import doharm.logic.entities.characters.classes.CharacterClassType;
import doharm.logic.entities.characters.classes.dragon.Dragon;
import doharm.logic.entities.characters.classes.ranger.Ranger;
import doharm.logic.entities.characters.classes.spider.Spider;
import doharm.logic.entities.characters.classes.troll.Troll;
import doharm.logic.entities.characters.classes.warrior.Warrior;
import doharm.logic.entities.characters.classes.wizard.Wizard;
import doharm.logic.entities.characters.states.CharacterState;
import doharm.logic.entities.characters.states.CharacterStateType;
import doharm.logic.entities.characters.states.IdleState;
import doharm.logic.entities.items.usable.UsableItem;
import doharm.logic.inventory.Inventory;
import doharm.logic.world.tiles.Tile;

/**
 * A character is any entity with a name, health and class.
 * @author Roland
 */

public abstract class Character extends AbstractEntity
{
	
	private String name;
	private CharacterClass characterClass;

	private Inventory inventory;
	
	private Taunts taunts;
	
	private float health;
	private float mana;
	private float rage;
	private Alliance alliance;
	
	private CharacterState state;
	private long spawnTime;
	private Character attackedBy;
	private CharacterType characterType;
	private Color colour = Color.white;
	private float frame;
	
	protected Character(CharacterType characterType) 
	{
		super(EntityType.CHARACTER);
		this.characterType = characterType;
		inventory = new Inventory();
		taunts = new Taunts(this);
		frame = 0;
	}
	
	
	public Taunts getTaunts() {
		return taunts;
	}
	
	public void setCharacterClass(CharacterClassType classType)
	{
		switch(classType)
		{
		//Player classes
		case WARRIOR:
			characterClass = new Warrior(this);
			break;
		case RANGER:
			characterClass = new Ranger(this);
			break;
		case WIZARD:
			characterClass = new Wizard(this);
			break;
		//Monster classes
		case DRAGON:
			characterClass = new Dragon(this);
			break;
		case TROLL:
			characterClass = new Troll(this);
			break;
		case SPIDER:
			characterClass = new Spider(this);
			break;
			
		default:
			throw new UnsupportedOperationException("Character class type not implemented: " + classType);
		}
		setSize(characterClass.getSize());
	}
	
	
	public Color getColour()
	{
		return colour;
	}
	public void setColour(Color colour)
	{
		this.colour = colour;
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
		decreaseRage();
		
		state.process(this);
		
		frame += getVelocity().getLength() * 0.1f;
		
		super.process();
	}
	
	private void decreaseRage() {
		rage -= characterClass.getAttributes().getMaxRage() *0.001f;
		if (rage < 0) rage = 0;
	}


	public void increaseRage() 
	{
		rage += characterClass.getAttributes().getMaxRage() *0.002f;
		if (rage > getMaxRage()) rage = getMaxRage();
	}
	
	public float getFrame()
	{
		return frame;
	}
	
	public void useItem(UsableItem item) 
	{
		item.useItem(this);
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
		mana = getMaxMana();
		rage = 0;
		
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
	
	public float getMaxMana() {
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
		return getCharacterClass().getAttributes().getMovementSpeed();
	}
	
	

	public void receiveDamage(float damage, Character attacker) 
	{
		attackedBy = attacker;
		
		if (!isAlive())
			return;
		
		health -= damage;
		rage += damage;
		if (rage > getMaxRage())
			rage = getMaxRage();
		
		if (health <= 0)
		{
			//kill me now
			
			
			die(attacker);
			
		}
		
	}
	
	public void die(Character attacker) 
	{
		health = 0;
		
		//drop all the character's items and gold
		Tile dropTile = getCurrentTile();
		inventory.dropAll(dropTile);
		

		int exp = calculateExperience();

		attacker.addExperience(exp);//attacker gets double exp
		
		if (attacker.getAlliance() != null)
		{
			for (Character ally: attacker.getAlliance().getCharacters())
			{
				ally.setState(new IdleState());
				ally.addExperience(exp);
				ally.halveRage();
			}
			
		}
		
		spawnTime = System.currentTimeMillis() + Math.max((int)(1000*(Math.pow(characterClass.getLevel(), 2))),5000);
		
		getWorld().addMessage(new Message(-1, true, new MessagePart(attacker.getName() + " killed " + getName()+".")));
		
		die();
	}
	
	private void halveRage() 
	{
		rage *= 0.5f;
	}


	public void setSpawnTime(long time)
	{
		spawnTime = time;
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

	public int getNumDragonBalls() 
	{
		return inventory.getStash().getNumDragonBalls();
	}

	

	public void increaseHealth(float increase) 
	{
		health += increase;
		health = Math.min (health, getMaxHealth());
	}
	public void increaseMana(float increase) 
	{
		health += increase;
		health = Math.min (health, getMaxHealth());
	}
	
	public void levelUp()
	{
		characterClass.addExperience(characterClass.getNextLevelExperience()-characterClass.getExperience());
	}


	public float getLightIntensity() 
	{
		float lightIntensity = 1.0f;
		
		if (inventory.hasLight())
		{
			lightIntensity *= 2;
		}
		
		return lightIntensity;
	}


	
	
	
	
}
