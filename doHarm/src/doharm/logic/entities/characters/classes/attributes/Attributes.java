package doharm.logic.entities.characters.classes.attributes;

import doharm.logic.chat.Message;
import doharm.logic.chat.MessagePart;
import doharm.logic.entities.characters.Character;
import doharm.logic.maths.MathUtils;
import doharm.logic.world.World;

public class Attributes 
{
	//BASE ATTRIBUTES
	private int intelligence = 0;
	private int strength = 0;
	private int dexterity = 0;
	private int vitality = 0;
	
	private float maxRage = 100;
	private float maxHealth = 100;
	private float maxMana = 0;
	private float healthRegeneration = 0.1f;
	private float manaRegeneration = 0.1f;
	
	private float movementSpeed = 2;
	
	public Attributes()
	{
		
	}


	public void levelup(LevelupAttributes attributes, Character character) 
	{
		healthRegeneration += attributes.getHealthRegenerationIncrease();
		healthRegeneration *= attributes.getHealthRegenerationMultiplier();
	
		manaRegeneration += attributes.getManaRegenerationIncrease();
		manaRegeneration *= attributes.getManaRegenerationMultiplier();
		
		float healthBefore = maxHealth;
		maxHealth += attributes.getMaxHealthIncrease();
		maxHealth *= attributes.getMaxHealthMultiplier();
		
		float manaBefore = maxMana;
		maxMana += attributes.getMaxManaIncrease();
		maxMana *= attributes.getMaxManaMultiplier();
		
		float rageBefore = maxRage;
		maxRage += attributes.getMaxRageIncrease();
		maxRage *= attributes.getMaxRageMultiplier();
		
		float hc = MathUtils.toDP(maxHealth - healthBefore,1);
		float mc = MathUtils.toDP(maxMana - manaBefore,1);
		float rc = MathUtils.toDP(maxRage - rageBefore,1);
		
		
		if (!character.isHumanPlayer())
			return;
		
		World world = character.getWorld();
		int id = character.getID();
		String text = "LEVEL UP! [";
		
		if (hc > 0) text += "Health +"+hc+" ";
		if (mc > 0) text += "Mana +"+mc+" ";
		if (rc > 0) text += "Rage +"+rc+" ";
		
		text = text.substring(0, text.length()-1);
		
		text += "]";
		
		
		world.addMessage(new Message(id,false, new MessagePart(text)));
		
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
		return maxHealth + vitality*10;
	}

	public float getMaxMana() {
		return maxMana+intelligence*10;
	}
	
	public float getMaxRage() {
		return maxRage + intelligence * 10;
	}

	public float getHealthRegeneration() {
		return healthRegeneration;
	}

	public float getManaRegeneration() {
		return manaRegeneration;
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

	public void increaseDexterity() 
	{
		dexterity++;
	}

	public void increaseIntelligence() {
		intelligence++;
	}

	public void increaseStrength() {
		strength++;
	}

	public void increaseVitality() {
		setVitality(getVitality() + 1);
	}

	public int getVitality() {
		return vitality;
	}

	public void setVitality(int vitality) {
		this.vitality = vitality;
	}

	public int getAttr(AttributeType t){
		switch(t){
		case DEXTERITY:
			return getDexterity();
		case INTELLIGENCE:
			return getIntelligence();
		case STRENGTH:
			return getStrength();
		case VITALITY:
			return getVitality();
		}
		throw new UnsupportedOperationException("The enum type "+t.toString()+" is not yet supported");
	}

	public void setMovementSpeed(float movementSpeed)
	{
		this.movementSpeed = movementSpeed;
	}
	public float getMovementSpeed() 
	{
		return movementSpeed + dexterity*0.3f;
	}

	



	
	
	
}
