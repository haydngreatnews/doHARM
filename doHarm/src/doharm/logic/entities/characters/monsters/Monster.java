package doharm.logic.entities.characters.monsters;
import doharm.logic.entities.characters.Character;
import doharm.logic.entities.characters.CharacterType;
import doharm.logic.entities.characters.classes.CharacterClass;

public class Monster extends Character
{
	private static final double MAX_SPAWN_TIME = 60;

	public Monster()
	{
		super(CharacterType.MONSTER);
		spawnEventually();
	}


	private void spawnEventually() 
	{
		setSpawnTime(System.currentTimeMillis() + (int)(Math.random()*MAX_SPAWN_TIME) * 1000);
	}


	@Override
	public void process()
	{
		if (!isAlive())
			return;
		super.process();
	}
	
	@Override 
	public void die()
	{
		super.die();
		getCharacterClass().addExperience(getCharacterClass().getNextLevelExperience()-getCharacterClass().getExperience());
		spawnEventually();
	}
	
}
