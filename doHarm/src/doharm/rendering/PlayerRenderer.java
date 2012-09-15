package doharm.rendering;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import doharm.logic.Game;
import doharm.logic.gameobjects.entities.characters.Character;
import doharm.logic.physics.Vector;
import doharm.logic.world.World;

public class PlayerRenderer {

	private Game game;
	private World world;

	public PlayerRenderer(Game game) 
	{
		this.game = game;
		this.world = game.getWorld();
	}

	public void redraw(Graphics2D graphics) 
	{
		for (Character player: world.getPlayers())
		{
			drawPlayer(player,graphics);
		}
	}

	private void drawPlayer(Character player, Graphics2D graphics) 
	{
		Dimension size = player.getSize();
		Vector position = player.getPosition();
		
		graphics.setColor(Color.white);
		graphics.fillOval(position.getXAsInt()-size.width/2, position.getYAsInt()-size.height/2, size.width, size.height/2);
	}

}
