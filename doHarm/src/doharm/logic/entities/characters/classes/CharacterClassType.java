package doharm.logic.entities.characters.classes;

public enum CharacterClassType 
{
	WIZARD, WARRIOR, RANGER, //Player classes
	
	TROLL, DRAGON, SPIDER;  //Monster classes

	
	
	public static CharacterClassType getLastPlayerClass()
	{
		return RANGER;
	}

	public static CharacterClassType getRandomMonsterClass() 
	{
		int firstMonsterOrdinal = getLastPlayerClass().ordinal()+1;
		int numMonsterClasses = CharacterClassType.values().length-firstMonsterOrdinal;
		
		
		return CharacterClassType.values()[firstMonsterOrdinal + (int)(Math.random()*numMonsterClasses)];
	}
}
