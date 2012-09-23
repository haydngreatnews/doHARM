package doharm.rendering;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import doharm.logic.Game;
import doharm.logic.gameobjects.entities.characters.Character;
import doharm.logic.physics.Vector;
import doharm.logic.world.Layer;
import doharm.logic.world.Tile;
import doharm.logic.world.World;

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
		for (Character player: world.getPlayers())
		{
			drawPlayer(player,graphics, imgIsoW, imgIsoH);
		}
	}

	private void drawPlayer(Character player, Graphics2D graphics, int imgIsoW, int imgIsoH) 
	{
		Dimension size = player.getSize();
		Tile tile = player.getCurrentTile();
		Layer layer = player.getCurrentLayer();
		Vector relative = player.getPositionRelativeToTile();
		
		float row = tile.getRow() + relative.getY()/tile.getHeight();
		float col = tile.getCol() + relative.getX()/tile.getWidth();
		
		graphics.setColor(Color.white);
		
		float x = (-(row*(imgIsoW/2-1)))+(col*(imgIsoW/2-1));
		float y = (row*(imgIsoH/2-1))+(col*(imgIsoH/2-1))-(layer.getLayerNumber()*imgIsoH);
		
		graphics.fillOval((int)x, (int)y, size.width, size.height/2);
		
		//graphics.fillOval(position.getXAsInt()-size.width/2, position.getYAsInt()-size.height/2, size.width, size.height/2);
	}

}
