package doharm.logic.entities.characters.players;


import doharm.logic.entities.characters.Character;
import doharm.logic.entities.characters.CharacterType;
import doharm.net.packets.Action;
import doharm.net.packets.PlayerState;
import doharm.net.packets.PlayerStateFull;
import doharm.net.packets.entityinfo.CharacterUpdate;

/**
 * A player is a type of character that is able to win the game.
 * 
 * @author Roland
 */

public abstract class Player extends Character
{
	private PlayerType playerType;

	protected Player(PlayerType playerType)
	{
		super(CharacterType.PLAYER);
		this.playerType = playerType;
	}

	public PlayerType getPlayerType()
	{
		return playerType;
	}
	
	
	public void process()
	{
		if (!isAlive())
			return;
		super.process();
	}

	public void update(CharacterUpdate u)
	{
		System.out.println("Updating " + this.getID());
		setAngle(u.angle);
		setHealth(u.health);
		setPosition(u.posX, u.posY, getWorld().getLayer(u.layer));
	}
	
	public void updatePlayerState(PlayerState state)
	{
		if (state == null)
			return;
		
		this.setHealth(state.health);
		this.setMana(state.mana);
		this.setRage(state.rage);
		//state.exp;
		
		if (state instanceof PlayerStateFull)
		{
			PlayerStateFull full = (PlayerStateFull) state;
			//this.set
			//full.maxHealth;
			//full.maxMana;
			//full.maxRage;
		}
	}

	public void updateFromAction(Action action)
	{
		setAngle(action.angle);
		setPosition(action.posX, action.posY, getWorld().getLayer(action.layer));
	}
}
