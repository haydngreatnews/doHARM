package doharm.logic.entities.characters.monsters;
import doharm.logic.entities.characters.Character;
import doharm.logic.entities.characters.CharacterType;

public abstract class Monster extends Character
{
	public Monster()
	{
		super(CharacterType.MONSTER);
	}
}
