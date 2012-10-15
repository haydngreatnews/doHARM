package doharm.logic.entities.characters.classes;

public enum CharacterClassType 
{
	WIZARD, WARRIOR, RANGER, //Player classes
	
	TROLL, DRAGON; //, //Monster classes TODO

	
	
	public CharacterClassType getLastPlayerClass()
	{
		return RANGER;
	}
}
