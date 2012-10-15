package doharm.rendering;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import doharm.logic.AbstractGame;
import doharm.logic.entities.characters.players.Player;
import doharm.logic.entities.items.Item;
import doharm.logic.physics.Vector;
import doharm.logic.world.World;

public class ItemRenderer {


	private AbstractGame game;
	private World world;

	public ItemRenderer(AbstractGame game) 
	{
		this.game = game;
		this.world = game.getWorld();
	}

	public void redrawPlayer(Item item, Graphics2D graphics, int fTileW, int wTileH) {

		drawPlayer(item ,graphics, fTileW, wTileH);

	}

	private void drawPlayer(Item item, Graphics2D graphics, int tileW, int tileH) {

		Dimension size = item.getSize();

		Vector position = item.getPosition();
		float row = position.getY()/tileH;
		float col = position.getX()/tileW;


		//TODO get item colour or image
		graphics.setColor(item.getQuality().getColour());

		Vector v = RenderUtil.convertCoordsToIso(col, row, item.getCurrentLayer().getLayerNumber(), game.getCamera());


		int x = (int)v.getX()-size.width/2;
		int y = (int)v.getY()-size.height/4;

		graphics.fillOval(x, y, size.width, size.height/2);




	}
}
