package doharm.logic.entities.characters.players;



public class NetworkPlayer extends Player
{
	protected NetworkPlayer() 
	{
		super(PlayerType.AI);
	}
	
	@Override
	public void process()
	{
		if (!isAlive())
			return;
		super.process();
	}
}
