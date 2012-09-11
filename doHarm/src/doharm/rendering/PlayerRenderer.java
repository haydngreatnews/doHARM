package doharm.rendering;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import doharm.logic.Camera;
import doharm.logic.Game;
import doharm.logic.gameobjects.entities.players.Player;
import doharm.logic.physics.Vector;

public class PlayerRenderer {

	private Game game;

	public PlayerRenderer(Game game) 
	{
		this.game = game;
	}

	public void redraw(Graphics2D graphics) 
	{
		for (Player player: game.getPlayers())
		{
			drawPlayer(player,graphics);
		}
	}

	private void drawPlayer(Player player, Graphics2D graphics) 
	{
		Vector cameraPos = game.getCamera().getRenderPosition();
		
		Dimension size = player.getSize();
		Vector position = player.getPosition();
		
		graphics.setColor(Color.white);
		graphics.fillOval(position.getXi()+cameraPos.getXi(), position.getYi()+cameraPos.getYi(), size.width, size.height);
	}

}
