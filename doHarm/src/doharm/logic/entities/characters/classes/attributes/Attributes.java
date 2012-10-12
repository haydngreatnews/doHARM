package doharm.logic.entities.characters.classes.attributes;

import doharm.logic.chat.Message;
import doharm.logic.chat.MessagePart;
import doharm.logic.entities.characters.Character;
import doharm.logic.maths.MathUtils;
import doharm.logic.world.World;

public class Attributes 
{
	//BASE ATTRIBUTES
	private int resistance = 0;
	private int intelligence = 0;
	private int strength = 0;
	private int dexterity = 0;
	
	//
	private float maxRage = 100;
	private float maxHealth = 100;
	private float maxMana = 0;
	private float healthRegeneration = 0.1f;
	private float manaRegeneration = 0.1f;
	
	public Attributes()
	{
		
	}

	//radar attributes
	public void levelup(LevelupAttributes attributes, Character character) 
	{
		healthRegeneration += attributes.getHealthRegenerationIncrease();
		healthRegeneration *= attributes.getHealthRegenerationMultiplier();
	
		manaRegeneration += attributes.getManaRegenerationIncrease();
		manaRegeneration *= attributes.getManaRegenerationMultiplier();
		
		float healthBefore = maxHealth;
		maxHealth += attributes.getMaxHealthIncrease();
		maxHealth *= attributes.getMaxHealthIncrease();
		
		float manaBefore = maxMana;
		maxMana += attributes.getMaxManaIncrease();
		maxMana *= attributes.getMaxManaIncrease();
		
		float rageBefore = maxRage;
		maxRage += attributes.getMaxRageIncrease();
		maxRage *= attributes.getMaxRageIncrease();
		
		float hc = MathUtils.toDP(maxHealth - healthBefore,1);
		float mc = MathUtils.toDP(maxMana - manaBefore,1);
		float rc = MathUtils.toDP(maxRage - rageBefore,1);
		
		World world = character.getWorld();
		int id = character.getID();
		String text = "LEVEL UP! [";
		
		if (hc > 0) text += "Health +"+hc+" ";
		if (mc > 0) text += "Mana +"+mc+" ";
		if (rc > 0) text += "Rage +"+rc+" ";
		
		text = text.substring(0, text.length()-1);
		
		text += "]";
		
		world.addMessage(new Message(id, new MessagePart(text)));
		
	}

	public int getResistance() {
		return resistance;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public int getStrength() {
		return strength;
	}

	public int getDexterity() {
		return dexterity;
	}

	public float getMaxHealth() {
		return maxHealth;
	}

	public float getMaxMana() {
		return maxMana;
	}
	
	public float getMaxRage() {
		return maxRage;
	}

	public float getHealthRegeneration() {
		return healthRegeneration;
	}

	public float getManaRegeneration() {
		return manaRegeneration;
	}

	public void setResistance(int resistance) {
		this.resistance = resistance;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}

	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
	}

	public void setMaxMana(float maxMana) {
		this.maxMana = maxMana;
	}

	public void setHealthRegeneration(float healthRegeneration) {
		this.healthRegeneration = healthRegeneration;
	}

	public void setManaRegeneration(float manaRegeneration) {
		this.manaRegeneration = manaRegeneration;
	}

	

	



	
	
	
}
