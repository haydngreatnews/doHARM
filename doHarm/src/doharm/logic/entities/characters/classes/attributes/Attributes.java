package doharm.logic.entities.characters.classes.attributes;

public class Attributes 
{
	//passive attributes
	private int resistance = 0;
	private int intelligence = 0;
	private int strength = 0;
	private int dexterity = 0;
	
	private float maxHealth = 100;
	private float maxMana = 0;
	private float healthRegeneration = 0.1f;
	private float manaRegeneration = 0.1f;
	
	public Attributes()
	{
		
	}

	//radar attributes
	public void levelup(LevelupAttributes attributes) 
	{
		
		
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
