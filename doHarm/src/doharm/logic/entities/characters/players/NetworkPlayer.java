package doharm.logic.entities.characters.players;

/**
 * A simple network player. They are controlled by clients.
 * @author Roland
 */

public class NetworkPlayer extends Player
{
	protected NetworkPlayer() 
	{
		super(PlayerType.NETWORK);
	}
	
	@Override
	public void process()
	{
		if (!isAlive())
			return;
		super.process();
	}
}
