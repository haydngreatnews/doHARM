package doharm.rendering;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import doharm.logic.Game;
import doharm.logic.camera.Camera;
import doharm.logic.physics.Vector;
import doharm.logic.world.Layer;
import doharm.logic.world.Tile;
import doharm.logic.world.World;
import doharm.storage.TilesetLoader;
import doharm.storage.WorldLoader;

public class WorldRenderer 
{
	private BufferedImage worldImage;
	private Graphics2D graphics;
	private Dimension canvasSize;
	private BufferedImage[] images;
	private AffineTransform transform;
	private Game game;
	
	//
	
	private PlayerRenderer playerRenderer;
	
	public WorldRenderer(Game game)
	{
		this.game = game;
		playerRenderer = new PlayerRenderer(game);
		canvasSize = new Dimension();
		transform = new AffineTransform();
		loadTileSets();
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
		
		
		
		
		Camera camera = game.getCamera();
		
		//give the camera the canvas size so we can calculate the centre of the screen
		camera.setCanvasDimensions(canvasSize);
		
		transform.setToIdentity();
		graphics.setTransform(transform);
		
		//clear the screen
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, canvasSize.width, canvasSize.height);
		
		transform.translate(-camera.getRenderPosition().getX(), -camera.getRenderPosition().getY());
		graphics.setTransform(transform);
		//draw the current game, based on the camera, etc.
		
		
		
		renderTiles();
		
		
		
		playerRenderer.redraw(graphics);
		
		
		
		
		
	}

	
	private void renderTiles(){
		//TODO tileRenderer	
		Vector position = game.getCamera().getRenderPosition();
		
		World world = game.getWorld();
		Layer layer = world.getLayer(0);
		
		Tile[][] tiles = layer.getTiles();
		
		for(int r = 0; r < tiles[0].length; r++)
		{
			for(int c = 0; c < tiles.length; c++)
			{
				Tile tile = tiles[c][r];
				graphics.drawImage(images[tile.getImageID()], c*32, r*32, null);
			}
		}
		
		/*
		 *
		 * 
		 */
		
	}
	
	
	private void loadTileSets(){
		World world = game.getWorld();
		BufferedImage tileSet = null;
		WorldLoader wl = world.getWorldLoader();
		
		TilesetLoader tsl = wl.getTilesetLoader();
		images = new BufferedImage[tsl.getTileNames().size()];
		
		int width = tsl.getTileWidth();
		int height = tsl.getTileHeight();
		
		try{
			tileSet = ImageIO.read(new File("res/tilesets/"+tsl.getTileSetImage())); 
			
			for(int r = 0; r < tileSet.getHeight()/height; r++)
			{
				for(int c = 0; c < tileSet.getWidth()/width; c++)
				{
					BufferedImage n = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
					Graphics2D g = n.createGraphics();
					g.drawImage(tileSet,0, 0,width, height,	c*width, r*height, width*(c+1), (r+1)*height, null);
					
					images[((tileSet.getHeight()/height)*r) + c] = n;
				}
			}
			
		}catch(Exception e){}
		
	}
	

	
	
}
