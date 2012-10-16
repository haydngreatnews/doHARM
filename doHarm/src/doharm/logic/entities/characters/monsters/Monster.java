package doharm.logic.entities.characters.monsters;
import doharm.logic.entities.characters.Character;
import doharm.logic.entities.characters.CharacterType;


public class Monster extends Character
{
	private static final double MAX_SPAWN_TIME = 60;
	private static final double LEVELS = 4;

	public Monster()
	{
		super(CharacterType.MONSTER);
		
	}


	public void spawnEventually() 
	{
		setSpawnTime(System.currentTimeMillis() + (int)(Math.random()*MAX_SPAWN_TIME) * 1000);
		levelup();
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
		spawnEventually();
	}


	private void levelup() 
	{
		int levels = (int)(Math.random()*LEVELS) + 1;
		for (int i = 0; i < levels; i++)
			getCharacterClass().addExperience(getCharacterClass().getNextLevelExperience()-getCharacterClass().getExperience());
	}
	
}
