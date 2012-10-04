package doharm.rendering;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Stack;

import doharm.logic.Game;
import doharm.logic.entities.characters.Character;
import doharm.logic.entities.characters.players.HumanPlayer;
import doharm.logic.entities.characters.players.Player;
import doharm.logic.entities.characters.players.PlayerType;
import doharm.logic.physics.Vector;
import doharm.logic.world.World;
import doharm.logic.world.tiles.Tile;

public class PlayerRenderer {

	private Game game;
	private World world;

	public PlayerRenderer(Game game) 
	{
		this.game = game;
		this.world = game.getWorld();
	}

	public void redraw(Graphics2D graphics, int imgIsoW, int imgIsoH) 
	{
		for (Player player: world.getPlayers())
		{
			drawPlayer(player,graphics, imgIsoW, imgIsoH);
		}
	}

	private void drawPlayer(Player player, Graphics2D graphics, int tileW, int tileH) 
	{
		Dimension size = player.getSize();
		//Tile tile = player.getCurrentTile();
		//Layer layer = player.getCurrentLayer();
		//Vector relative = player.getPositionRelativeToTile();
		Vector position = player.getPosition();
		float row = position.getY()/tileH;
		float col = position.getX()/tileW;
		
		
		
		if (player.getPlayerType() == PlayerType.HUMAN)
			graphics.setColor(Color.white);
		else if (player.getPlayerType() == PlayerType.AI)
			graphics.setColor(Color.RED);
		else if (player.getPlayerType() == PlayerType.NETWORK)
			graphics.setColor(Color.GRAY);
		else
			throw new UnsupportedOperationException(player.getPlayerType() + " not implemented");
		
		
		//TODO
		Vector v = RenderUtil.convertCoordsToIso(col, row, player.getCurrentLayer().getLayerNumber());
		
		//float x = (-(row*(imgIsoW/2-1)))+(col*(imgIsoW/2-1));
		//float y = (row*(imgIsoH/2-1))+(col*(imgIsoH/2-1))-(layer.getLayerNumber()*imgIsoH);
		
		int x = (int)v.getX()-size.width/2;
		int y = (int)v.getY()-size.height/4;
		
		graphics.fillOval(x, y, size.width, size.height/2);
		
		
		
		graphics.setColor(new Color(1-player.getHealthRatio(),player.getHealthRatio(),0,1));
		graphics.fillRect(x, y-5, (int)(size.width*player.getHealthRatio()), 3);
		
		
		//Path
		Collection<Tile> path = player.getPath();
		graphics.setColor(Color.white);
		for (Tile tile: path)
		{
			row = tile.getY()/world.getTileHeight();
			col = tile.getX()/world.getTileWidth();
			v = RenderUtil.convertCoordsToIso(col, row, player.getCurrentLayer().getLayerNumber());
			graphics.fillOval((int)v.getX()-size.width/8, (int)v.getY()-size.height/16, size.width/4, size.height/8);
		}
		
		
		//Goal
		graphics.setColor(Color.red);
		Vector goal = player.getGoal();
		row = goal.getY()/world.getTileHeight();
		col = goal.getX()/world.getTileWidth();
		v = RenderUtil.convertCoordsToIso(col, row, player.getCurrentLayer().getLayerNumber());
		graphics.fillOval((int)v.getX()-size.width/8, (int)v.getY()-size.height/16, size.width/4, size.height/8);
		
		
		if (player.getPlayerType() == PlayerType.HUMAN)
		{
			
			graphics.setColor(Color.orange);
			Tile t = ((HumanPlayer)player).getHoverTile();
			if (t != null)
			{
				row = t.getY()/world.getTileHeight();
				col = t.getX()/world.getTileWidth();
				v = RenderUtil.convertCoordsToIso(col, row, player.getCurrentLayer().getLayerNumber());
				graphics.fillOval((int)v.getX()-size.width/8, (int)v.getY()-size.height/16, size.width/4, size.height/8);
			}
			
		}
		
		
		
		
		
		//graphics.fillOval(position.getXAsInt()-size.width/2, position.getYAsInt()-size.height/2, size.width, size.height/2);
	}

}
