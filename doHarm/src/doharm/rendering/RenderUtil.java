package doharm.rendering;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import doharm.logic.camera.Camera;
import doharm.logic.entities.characters.players.Player;
import doharm.logic.physics.Vector;
import doharm.logic.world.Layer;
import doharm.logic.world.World;
import doharm.logic.world.tiles.Tile;

public class RenderUtil {

	private static int imgIsoW = 0;
	private static int imgIsoH = 0;
	
	private static Camera camera;

	/**
	 * Scales the img parameter to the specified width and height.
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage scaleImage(BufferedImage img, int width, int height){

		BufferedImage scaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = scaled.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(img, 0, 0, width, height, null);
		return scaled;
	}

	/**
	 * A world renderer must have been initialized before this method is called, to yield valid results.
	 * converts "square" world coordinates to their corresponding isometric coordinates.
	 * 
	 * 
	 * @param col
	 * @param row
	 * @return
	 */
	public static Vector convertCoordsToIso(float col, float row, float layer){
		
		
		
		float x = -((row*(imgIsoW/2)))+(col*(imgIsoW/2));
		float y = (row*(imgIsoH/2))+(col*(imgIsoH/2)) -layer*imgIsoH ;
		
		
		return new Vector(x, y);

	}


	/**
	 * 
	 * 
	 * 
	 * @param c
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage generateIsoImage(Color c, int width, int height){
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)img.getGraphics();
		g.setColor(c);
		int lineLength = 2;
		int x = width/2;
		for(int row = 0; row < height/2; row++){

			g.drawLine(x, row, x+lineLength, row);
			x-=2;
			lineLength+=4;
		}

		lineLength-=4;
		x+=2;
		for(int row = (height/2); row < height; row++){

			g.drawLine(x, row, x+lineLength, row);

			x+=2;
			lineLength-=4;
		}
		return img;
	}

	static void setImgDimensions(int imgW, int imgH){
		imgIsoW = imgW;
		imgIsoH = imgH;
	
		
	}
	
	
	/**
	 * Sets the Camera the Renderer will use to decide which orientation to 
	 * render from, this must be set before the world is rendered.
	 * @param c
	 */
	public static void setCamera(Camera c){
		camera = c;
	}
	
	public static boolean isObscured(Player player, World world){
		Layer[] layers = world.getLayers();
		int x = player.getCurrentTile().getRow();
		int y = player.getCurrentTile().getCol();
		int layerNum = player.getCurrentLayer().getLayerNumber();
		
		if(layers.length-1 == layerNum){
			return false;
		}
		Layer next = layers[layerNum];
		Tile[][] tiles = next.getTiles();
		
		
		int range = 3;
		int xStart = x-1;
		int yStart = y-1;
		int xRange = range;
		int yRange = range;
		
		if(x >= tiles[0].length){
			xRange -= range/2;
		}
		if(y >= tiles.length){
			yRange -= range/2;
		}
		
		if(x < 0){
			xRange -= range/2;
		}
		if(y >= tiles.length){
			yRange -= range/2;
		}
		
		
		for(int col = yStart-yRange/2; col < yStart+yRange/2; col++){
			for(int row = xStart-xRange/2; row < xStart+xRange/2; row++){
				if(tiles[col][row].getImageID() != 2){
					return true;
				}
				
			}
		}
		
		
		return false;
	}
}
