package doharm.logic.entities.characters.classes.attributes;

/**
 * Player attributes will be increased and multiplied by these values when they level up.
 * 
 * Note BASE attributes (eg. strength) are not increased, 
 * these are controllable by the user!
 * 
 * @author bewickrola
 */

public class LevelupAttributes 
{
	private float maxHealthIncrease = 1;
	private float maxHealthMultiplier = 1.1f;

	private float maxManaIncrease = 0;
	private float maxManaMultiplier = 1.3f;
	
	private float maxRageIncrease = 1;
	private float maxRageMultiplier = 1.1f;
	
	private float healthRegenerationIncrease = 0.1f;
	private float healthRegenerationMultiplier = 1.3f;

	private float manaRegenerationIncrease = 0;
	private float manaRegenerationMultiplier = 1.3f;
	
	
	
	public float getMaxHealthIncrease() {
		return maxHealthIncrease;
	}
	public void setMaxHealthIncrease(float maxHealthIncrease) {
		this.maxHealthIncrease = maxHealthIncrease;
	}
	public float getMaxHealthMultiplier() {
		return maxHealthMultiplier;
	}
	public void setMaxHealthMultiplier(float maxHealthMultiplier) {
		this.maxHealthMultiplier = maxHealthMultiplier;
	}
	public float getMaxManaIncrease() {
		return maxManaIncrease;
	}
	public void setMaxManaIncrease(float maxManaIncrease) {
		this.maxManaIncrease = maxManaIncrease;
	}
	public float getMaxManaMultiplier() {
		return maxManaMultiplier;
	}
	public void setMaxManaMultiplier(float maxManaMultiplier) {
		this.maxManaMultiplier = maxManaMultiplier;
	}
	public float getMaxRageIncrease() {
		return maxRageIncrease;
	}
	public void setMaxRageIncrease(float maxRageIncrease) {
		this.maxRageIncrease = maxRageIncrease;
	}
	public float getMaxRageMultiplier() {
		return maxRageMultiplier;
	}
	public void setMaxRageMultiplier(float maxRageMultiplier) {
		this.maxRageMultiplier = maxRageMultiplier;
	}
	public float getHealthRegenerationIncrease() {
		return healthRegenerationIncrease;
	}
	public void setHealthRegenerationIncrease(float healthRegenerationIncrease) {
		this.healthRegenerationIncrease = healthRegenerationIncrease;
	}
	public float getHealthRegenerationMultiplier() {
		return healthRegenerationMultiplier;
	}
	public void setHealthRegenerationMultiplier(float healthRegenerationMultiplier) {
		this.healthRegenerationMultiplier = healthRegenerationMultiplier;
	}
	public float getManaRegenerationIncrease() {
		return manaRegenerationIncrease;
	}
	public void setManaRegenerationIncrease(float manaRegenerationIncrease) {
		this.manaRegenerationIncrease = manaRegenerationIncrease;
	}
	public float getManaRegenerationMultiplier() {
		return manaRegenerationMultiplier;
	}
	public void setManaRegenerationMultiplier(float manaRegenerationMultiplier) {
		this.manaRegenerationMultiplier = manaRegenerationMultiplier;
	}
}
