package doharm.logic;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import doharm.logic.gameobjects.entities.players.HumanPlayer;
import doharm.logic.gameobjects.entities.players.Player;
import doharm.logic.gameobjects.entities.players.PlayerFactory;
import doharm.logic.gameobjects.entities.players.PlayerType;

public class Game 
{
	private Camera camera;
	private Set<Player> players;
	private HumanPlayer humanPlayer;
	private boolean running;
	
	public Game()
	{
		camera = new Camera();
		running = true;
		
		
		players = new HashSet<Player>();
		
		humanPlayer = (HumanPlayer) PlayerFactory.createPlayer(0, PlayerType.HUMAN);
		players.add(humanPlayer);
		
	}
	
	public Camera getCamera()
	{
		return camera;
	}
	
	//public void addPlayer()
	
	public Collection<Player> getPlayers()
	{
		return Collections.unmodifiableCollection(players);
	}

	public void run() 
	{
		
	}

	public boolean isRunning() 
	{
		return running;
	}

	public void moveHumanPlayer(int x, int y) 
	{
		if (humanPlayer == null)
			return;
		humanPlayer.getPosition().setX(x-camera.getRenderPosition().getXi());
		humanPlayer.getPosition().setY(y-camera.getRenderPosition().getYi());
	}
}
