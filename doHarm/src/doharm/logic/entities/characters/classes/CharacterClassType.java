package doharm.logic.entities.characters.classes;

public enum CharacterClassType 
{
	WIZARD, WARRIOR, RANGER, //Player classes
	
	TROLL, DRAGON, SPIDER;  //Monster classes

	
	
	public static CharacterClassType getLastPlayerClass()
	{
		return RANGER;
	}
}
