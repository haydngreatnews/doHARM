package doharm.logic;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import doharm.logic.gameobjects.entities.players.HumanPlayer;
import doharm.logic.gameobjects.entities.players.Player;

public class Game 
{
	private Camera camera;
	private Set<Player> players;
	private boolean running;
	
	public Game()
	{
		camera = new Camera();
		running = true;
		
		
		players = new HashSet<Player>();
		
		Player p = new HumanPlayer();
		players.add(p);
		
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
}
