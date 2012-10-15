package doharm.rendering;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Collection;

import doharm.logic.AbstractGame;
import doharm.logic.entities.characters.players.HumanPlayer;
import doharm.logic.entities.characters.players.Player;
import doharm.logic.entities.characters.players.PlayerType;
import doharm.logic.entities.characters.states.CharacterStateType;
import doharm.logic.entities.characters.states.MoveState;
import doharm.logic.physics.Vector;
import doharm.logic.world.World;
import doharm.logic.world.tiles.Tile;

public class PlayerRenderer {

	private AbstractGame game;
	private World world;

	public PlayerRenderer(AbstractGame game) 
	{
		this.game = game;
		this.world = game.getWorld();
	}

	/*public void redraw(Graphics2D graphics, int imgIsoW, int imgIsoH) 
	{
		for (Player player: world.getPlayerFactory().getEntities())
		{
			drawPlayer(player,graphics, imgIsoW, imgIsoH);
		}
	}*/
	
	public void drawInfo(Player player, Graphics2D graphics, int tileW, int tileH)
	{
		if (!player.isAlive())
			return;
		
		Vector position = player.getPosition();
		float row = position.getY()/tileH;
		float col = position.getX()/tileW;
		
		
		Vector v = RenderUtil.convertCoordsToIso(col, row, player.getCurrentLayer().getLayerNumber(), game.getCamera());
		
		Dimension size = player.getSize();
		
		int x = (int)v.getX()-size.width/2;
		int y = (int)v.getY()-size.height/4;
		
		
		graphics.setColor(new Color(1-player.getHealthRatio(),player.getHealthRatio(),0,1));
		graphics.fillRect(x, y-10, (int)(size.width*player.getHealthRatio()), 3);
		
		graphics.setColor(new Color(1f,0,1));
		graphics.fillRect(x, y-5, (int)(size.width*player.getExperienceRatio()), 3);
		
		
		if (player.getPlayerType() == PlayerType.HUMAN)
		{
			HumanPlayer hp = (HumanPlayer)player;
			graphics.setColor(Color.magenta.darker().darker());
			graphics.drawString("Icon: " + hp.getMouseIcon().toString(), x, y-35);
		}
		
		graphics.setColor(Color.white);
		graphics.drawString("State: " + player.getStateType().toString(), x, y-15);
		
		graphics.setColor(Color.white);
		graphics.drawString("Level: "+ player.getLevel(), x, y-55);
		
		
	}

	private void drawPlayer(Player player, Graphics2D graphics, int tileW, int tileH) 
	{
		if (!player.isAlive())
			return;
		
		Dimension size = player.getSize();
		//Tile tile = player.getCurrentTile();
		//Layer layer = player.getCurrentLayer();
		//Vector relative = player.getPositionRelativeToTile();
		Vector position = player.getPosition();
		float row = position.getY()/tileH;
		float col = position.getX()/tileW;
		
		
		
		/*if (player.getPlayerType() == PlayerType.HUMAN)
			graphics.setColor(Color.white);
		else if (player.getPlayerType() == PlayerType.AI)
			graphics.setColor(Color.RED);
		else if (player.getPlayerType() == PlayerType.NETWORK)
			graphics.setColor(Color.GRAY);
		else
			throw new UnsupportedOperationException(player.getPlayerType() + " not implemented");
		*/
		
		graphics.setColor(player.getColour());

		Vector v = RenderUtil.convertCoordsToIso(col, row, player.getCurrentLayer().getLayerNumber(), game.getCamera());
		
	
		
		int x = (int)v.getX()-size.width/2;
		int y = (int)v.getY()-size.height/4;
		
		
		//draw the player
		graphics.fillOval(x, y, size.width, size.height/2);
		
		
		
		
		
		
		/*if (player.getPlayerType() == PlayerType.HUMAN)
		{
			if (player.getStateType() == CharacterStateType.MOVE)
			{
				MoveState state = (MoveState)player.getState();
				
				//Path
				Collection<Tile> path = state.getPath();
				graphics.setColor(Color.white);
				for (Tile tile: path)
				{
					row = tile.getY()/world.getTileHeight();
					col = tile.getX()/world.getTileWidth();
					v = RenderUtil.convertCoordsToIso(col, row, player.getCurrentLayer().getLayerNumber(), game.getCamera());
					graphics.fillOval((int)v.getX()-size.width/8, (int)v.getY()-size.height/16, size.width/4, size.height/8);
				}
				
				
				//Goal
				graphics.setColor(Color.red);
				Vector goal = state.getDestination();
				row = goal.getY()/world.getTileHeight();
				col = goal.getX()/world.getTileWidth();
				v = RenderUtil.convertCoordsToIso(col, row, player.getCurrentLayer().getLayerNumber(), game.getCamera());
				graphics.fillOval((int)v.getX()-size.width/8, (int)v.getY()-size.height/16, size.width/4, size.height/8);
			}
		
		
			HumanPlayer hp = (HumanPlayer)player;
			graphics.setColor(Color.orange);
			Tile t = hp.getHoverTile();
			if (t != null)
			{
				row = t.getY()/world.getTileHeight();
				col = t.getX()/world.getTileWidth();
				v = RenderUtil.convertCoordsToIso(col, row, player.getCurrentLayer().getLayerNumber(), game.getCamera());
				graphics.fillOval((int)v.getX()-size.width/8, (int)v.getY()-size.height/16, size.width/4, size.height/8);
			}
		}*/
		
		
		
		
	}

	public void redrawPlayer(Player player, Graphics2D graphics, int fTileW,
			int wTileH) {
		// TODO Auto-generated method stub
		drawPlayer(player,graphics, fTileW, wTileH);
		
	}

}
