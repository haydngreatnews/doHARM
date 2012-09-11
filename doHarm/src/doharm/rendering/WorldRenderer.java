package doharm.rendering;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import doharm.logic.Game;

public class WorldRenderer 
{
	private BufferedImage worldImage;
	private Graphics2D graphics;
	private Dimension canvasSize;
	
	private Game game;
	
	private PlayerRenderer playerRenderer;
	
	public WorldRenderer(Game game)
	{
		this.game = game;
		playerRenderer = new PlayerRenderer(game);
		canvasSize = new Dimension();
	}
	
	public void createImage(Dimension canvasSize)
	{
		this.canvasSize = canvasSize;

		worldImage = new BufferedImage(canvasSize.width, canvasSize.height, BufferedImage.TYPE_INT_ARGB);
		graphics = worldImage.createGraphics();
	}

	public BufferedImage getImage() 
	{
		return worldImage;
	}

	public void redraw(Dimension canvasSize) 
	{
		if (!this.canvasSize.equals(canvasSize))
			createImage(canvasSize); //resize the canvas
		
		//clear the screen
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, canvasSize.width, canvasSize.height);
		
		game.getCamera().setCanvasDimensions(canvasSize);
		
		playerRenderer.redraw(graphics);
		
		
		//draw the current game, based on the camera, etc.
		
		
	}

	
	
}
