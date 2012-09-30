package doharm.rendering;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
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
import doharm.logic.world.World;
import doharm.logic.world.tiles.Tile;
import doharm.storage.TilesetLoader;
import doharm.storage.WorldLoader;

public class WorldRenderer 
{
	private BufferedImage worldImage;
	private Graphics2D graphics;
	
	private BufferedImage pickImage;
	private Graphics2D pickGraphics;
	
	private Dimension canvasSize;
	//private BufferedImage[] images;
	
	private BufferedImage[] floorImages;
	private BufferedImage[] wallImages;
	
	private AffineTransform transform;
	private Game game;

	private PlayerRenderer playerRenderer;
	
	
	private int imgSize;//Tiles assumed to be square.
	
	
	private  int fTileW;
	private  int fTileH;

	private int wTileW;
	private  int wTileH;
	
	public WorldRenderer(Game game)
	{
		this.game = game;
		playerRenderer = new PlayerRenderer(game);
		canvasSize = new Dimension();
		transform = new AffineTransform();
		
		//TODO update for new tile reading.
		RenderUtil.setImgDimensions(fTileW, wTileH);
		
		
		newLoadTileSets();
		
	}
	


	public void createImage(Dimension canvasSize)
	{
		this.canvasSize = canvasSize;

		worldImage = new BufferedImage(canvasSize.width, canvasSize.height, BufferedImage.TYPE_INT_ARGB);
		graphics = worldImage.createGraphics();
		
		pickImage = new BufferedImage(canvasSize.width, canvasSize.height, BufferedImage.TYPE_INT_ARGB);
		pickGraphics = pickImage.createGraphics();
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
		pickGraphics.setTransform(transform);
		
		//clear the screen
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, canvasSize.width, canvasSize.height);
		
		
		//clear the mouse pick image
		pickGraphics.setColor(Color.black);
		pickGraphics.fillRect(0, 0, canvasSize.width, canvasSize.height);
		
		//transform.translate(v.getX(),v.getY());
		transform.translate(-camera.getRenderPosition().getX(), -camera.getRenderPosition().getY());
		graphics.setTransform(transform);
		pickGraphics.setTransform(transform);
		//draw the current game, based on the camera, etc.
	
		
		renderWorldIso();
	
		playerRenderer.redraw(graphics, fTileW, fTileH);
		
		
		//TODO REMOVE
		transform.setToIdentity();
		graphics.setTransform(transform);
		
		Composite old = graphics.getComposite();
		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		
		graphics.drawImage(pickImage, 0, 0, null);
		graphics.setComposite(old);
	}

	public int getPickColourAt(int mouseX, int mouseY)
	{
		return pickImage.getRGB(mouseX, mouseY);
	}
	
	
	private void renderWorldIso(){
		World world = game.getWorld();
		
		Layer[] layers = world.getLayers();
		for(int layerCount = 0; layerCount < layers.length; layerCount++){

			Tile[][] tiles = layers[layerCount].getTiles();
			
			boolean isTransparent = false;
			//TODO this must be changed when camera views are implemented.
			//if(tile above the player with respect to the isometric view, 
			//ie. the tile(s) obscuring view of the player, is not an invisible tile, make this entire layer transparent.
			//and dont draw any subsequent layers.
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			for(int row = 0; row < tiles.length; row++)
			{
				for(int col = 0; col < tiles[0].length; col++)
				{
					Tile tile = tiles[row][col];
					
					//TODO update since tile reading changed
					BufferedImage image = floorImages[tile.getImageID()];
					
					Vector v = RenderUtil.convertCoordsToIso(col, row);
					
					
					
					if (!tile.isWalkable()) //can't move to non walkable tiles
						continue;
					
					
					if (!tile.isWalkable()) //can't move to non walkable tiles
						continue;
					
					

					//graphics.drawImage(image,v.getXAsInt(),v.getYAsInt(), null);
					
					int x = (-(col*(fTileW/2)))+(row*(fTileW/2));
					int y = (col*(fTileH/2))+(row*(fTileH/2));
					graphics.drawImage(image,x,y, null);
					//Don't think walls can be drawn yet, need some changes to the Tile class

					
					
					
					
					graphics.drawImage(image,v.getXAsInt(),v.getYAsInt(), null);
					
					
					
					int rgb = world.getColour(row, col, layerCount);
					
					
					// to extract the components into individual ints.
					
					int red = 0xFF & ( rgb >> 16);
					int green = 0xFF & (rgb >> 8 );
					int blue = 0xFF & (rgb >> 0 );
					

					//System.out.println("row="+row +", col="+col + ", layer="+layerCount +", r="+red+", g="+green+", b="+blue);
					// to recreate the argb
					//int argb = (alpha << 24) | (red << 16 ) | (green<<8) | blue;


					
					
					
					
					
					
					Color colour = new Color(red,green,blue);
					
					pickGraphics.setColor(colour);
					pickGraphics.fillRect(v.getXAsInt(),v.getYAsInt(),image.getWidth(), image.getHeight());
				
				}
			}
		
		}
		
	}
	
	
	private void newLoadTileSets(){
		World world = game.getWorld();
		BufferedImage tileSet = null;
		WorldLoader wl = world.getWorldLoader();
		
		
		TilesetLoader tsl = wl.getTilesetLoader();
		
		fTileW = tsl.getFloorTileWidth();
		fTileH = tsl.getFloorTileHeight();
		
		wTileW = tsl.getWallTileWidth();
		wTileH = tsl.getWallTileHeight();
		
		
		
		floorImages = new BufferedImage[tsl.getNumFloorTiles()];
		wallImages = new BufferedImage[tsl.getNumWallTiles()];
		
		try{
			tileSet = ImageIO.read(new File("res/tilesets/"+tsl.getFloorTileSetImage()));
			
			
			/*
				BufferedImage transparentImage = new BufferedImage(tileSet.getWidth(),tileSet.getHeight(),BufferedImage.TYPE_INT_ARGB);
				Graphics2D transparentGraphics = transparentImage.createGraphics();
				transparentGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
				transparentGraphics.drawImage(tileSet, 0,0,null);
			*/
			/*
			 * 
			 * 	Composite old = backbufferGraphics.getComposite();
				backbufferGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
				backbufferGraphics.drawImage(boardImage, 0, 0,boardImage.getWidth(),boardImage.getHeight(),null);	
				backbufferGraphics.setComposite(old);
				drawBoard();
				backbufferGraphics.setComposite(old);
			 * 
			 * 
			 */
			
			for(int r = 0; r < tileSet.getHeight()/fTileH; r++)
			{
				for(int c = 0; c < tileSet.getWidth()/fTileW; c++)
				{
					BufferedImage n = new BufferedImage(fTileW, fTileH, BufferedImage.TYPE_INT_ARGB);
					Graphics2D g = n.createGraphics();
					g.drawImage(tileSet,0, 0,fTileW, fTileH, c*fTileW, r*fTileH, fTileW*(c+1), (r+1)*fTileH, null);
					
					floorImages[((tileSet.getHeight()/fTileH)*r) + c] = n;
				}
			}
			
			
			//load the wall tiles
			tileSet = ImageIO.read(new File("res/tilesets/"+tsl.getWallTileSetImage()));
			
			for(int r = 0; r < tileSet.getHeight()/wTileH; r++)
			{
				for(int c = 0; c < tileSet.getWidth()/wTileW; c++)
				{
					BufferedImage n = new BufferedImage(wTileH, wTileW, BufferedImage.TYPE_INT_ARGB);
					Graphics2D g = n.createGraphics();
					g.drawImage(tileSet,0, 0,wTileW, wTileH, c*wTileW, r*wTileH, wTileW*(c+1), (r+1)*wTileH, null);
					
					floorImages[((tileSet.getHeight()/wTileH)*r) + c] = n;
				}
			}
			
		}catch(Exception e){}
		
	}

	
}

