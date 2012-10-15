

package doharm.rendering;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import doharm.logic.camera.Camera;
import doharm.logic.camera.Direction;
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
	public static Vector convertCoordsToIso(float col, float row, float layer, Camera c){
		Direction d = c.getDirection();
		float x = 0;
		float y = 0;

		switch(d){
		case NORTH : 
			x = -((row*(imgIsoW/2)))+(col*(imgIsoW/2));
			y = (row*(imgIsoH/2))+(col*(imgIsoH/2)) -layer*imgIsoH ;
			break;
		case EAST : 
			x = ((row*(imgIsoW/2)))+(col*(imgIsoW/2));
			y = (row*(imgIsoH/2))-(col*(imgIsoH/2)) -layer*imgIsoH ;
			break;
		case WEST : 
			x = -((row*(imgIsoW/2)))-(col*(imgIsoW/2));
			y = -(row*(imgIsoH/2))+(col*(imgIsoH/2)) -layer*imgIsoH ;
			break;
		case SOUTH : 
			x = +((row*(imgIsoW/2)))-(col*(imgIsoW/2));
			y = -(row*(imgIsoH/2))-(col*(imgIsoH/2)) -layer*imgIsoH ;
			break;
		}


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
		int x = (width/2)-1;
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
		int layerNum = player.getCurrentLayer().getLayerNumber() + 1;

		//		Layer next = layers[layerNum];
		//		Tile[][] tiles = next.getTiles();

		int row = y;
		int col = x;

		while(layerNum < layers.length){

			Layer next = layers[layerNum];
			Tile[][] tiles = next.getTiles();
			switch(world.getCamera().getDirection()){
			case NORTH :  row++; col++;break;
			case EAST : row--;col++;break;
			case SOUTH : row--; col--; break;
			case WEST : row++;col--;break;
			}

			if(row < 0){
				row = 0;
				System.out.println("row set to 0");
			}
			if(col < 0){
				col = 0;
				System.out.println("col set to 0");
			}
			if(row >= tiles.length){
				row = tiles.length - 1;
				System.out.println("row set to tiles length");
			}
			if(col >= tiles.length){
				col = tiles[0].length - 1;
				System.out.println("col set to tiles length");
			}

			if(tiles[col][row].getImageID() != 2){
				return true;
			}
			layerNum++;
		}
		return false;
	}

	public static BufferedImage generateLeftWallImage(Color color, int fTileH, int wTileW, int wTileH) {
		// TODO Auto-generated method stub
		BufferedImage img = new BufferedImage(wTileW, wTileH, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)img.getGraphics();
		g.setColor(color);
		int lineLength = 1;
		int x = 0;
		int row = 0;
		for(; row < fTileH/2; row++){

			g.drawLine(x, row, x+lineLength, row);

			lineLength+=2;
		}
		lineLength-=2;
		x+=2;

		for(; row < wTileH-(fTileH/2); row++){

			g.drawLine(x, row, x+lineLength, row);

		}

		x+=2;
		for(; row < wTileH; row++){

			g.drawLine(x, row, x+lineLength, row);

			x+=2;

		}
		return img;
	}

	public static BufferedImage generateRightWallImage(Color color, int fTileH, int wTileW, int wTileH) {
		// TODO Auto-generated method stub
		BufferedImage img = new BufferedImage(wTileW, wTileH, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)img.getGraphics();
		g.setColor(color);
		int lineLength = wTileW;
		int x = wTileW-1;
		int row = 0;

		for(; row >=0; row--){

			g.drawLine(x, row, x+lineLength, row);

			x-=2;

		}


		for(; row < fTileH/2; row++){

			g.drawLine(x, row, x+lineLength, row);

			lineLength+=2;
		}
		lineLength-=2;
		x+=2;

		for(; row < wTileH-(fTileH/2); row++){

			g.drawLine(x, row, x+lineLength, row);

		}

		for(; row < fTileH/2; row++){

			g.drawLine(x, row, x+lineLength, row);

			lineLength+=2;
		}

		x+=2;
		return img;
	}
	}




