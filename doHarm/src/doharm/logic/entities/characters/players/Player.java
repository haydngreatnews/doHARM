package doharm.logic.entities.characters.players;


import java.awt.Color;

import doharm.logic.entities.characters.Character;
import doharm.logic.entities.characters.CharacterType;
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
		setAngle(u.angle);
		setHealth(u.health);
		setPosition(u.posX, u.posY, getWorld().getLayer(u.layer));
	}	
}
