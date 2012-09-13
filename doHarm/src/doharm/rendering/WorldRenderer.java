package doharm.rendering;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import doharm.logic.Game;
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
	
	private Game game;
	
	//
	
	private PlayerRenderer playerRenderer;
	
	public WorldRenderer(Game game)
	{
		this.game = game;
		playerRenderer = new PlayerRenderer(game);
		canvasSize = new Dimension();
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
		
		//clear the screen
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, canvasSize.width, canvasSize.height);
		
		//give the camera the canvas size so we can calculate the centre of the screen
		game.getCamera().setCanvasDimensions(canvasSize);
		
		
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
		System.out.println(tiles.length);
		
		for(int r = 0; r < tiles[0].length; r++){
			for(int c = 0; c < tiles.length; c++){
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
		
		TilesetLoader t = wl.getTileset();
		images = new BufferedImage[t.getTileNames().size()];
		
		try{
			tileSet = ImageIO.read(new File("res/tilesets/"+t.getTileSetImage())); 
			
			for(int r = 0; r < tileSet.getHeight()/t.getTileHeight(); r++){
				for(int c = 0; c < tileSet.getWidth()/t.getTileWidth(); c++){
					BufferedImage n = new BufferedImage(t.getTileWidth(), t.getTileHeight(), BufferedImage.TYPE_INT_ARGB);
					Graphics2D g = n.createGraphics();
					g.drawImage(tileSet,0, 0,t.getTileWidth(), t.getTileHeight(),
							c*t.getTileWidth(), r*t.getTileHeight(), t.getTileWidth()*(c+1), (r+1)*t.getTileHeight(), null);
					
					images[((tileSet.getHeight()/t.getTileHeight())*r) + c] = n;
					
				}
			}
			
		}catch(Exception e){}
		
	}
	

	
	
}
