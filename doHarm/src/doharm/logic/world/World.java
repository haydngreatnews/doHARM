package doharm.logic.world;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import doharm.logic.camera.Camera;
import doharm.logic.gameobjects.entities.characters.players.HumanPlayer;
import doharm.logic.gameobjects.entities.characters.players.Player;
import doharm.logic.gameobjects.entities.characters.players.PlayerFactory;


public class World 
{
	Layer[] levels;  
	private Set<Player> players;
	private HumanPlayer humanPlayer;
	private Camera camera;
	
	public World(Camera camera)
	{
		this.camera = camera;
		players = new HashSet<Player>();
		
		
		humanPlayer = PlayerFactory.createHumanPlayer("Test player", 0);
		players.add(humanPlayer);
	}

	public void moveEntities() 
	{
		for (Player p: players)
		{
			p.move();
		}
	}
	
	public Collection<Player> getPlayers()
	{
		return Collections.unmodifiableCollection(players);
	}
	
	
	
	public void moveHumanPlayer(int x, int y) 
	{
		if (humanPlayer == null)
			return;
		humanPlayer.moveTo(x-camera.getRenderPosition().getXAsInt(), y-camera.getRenderPosition().getYAsInt());
		
	}
}
