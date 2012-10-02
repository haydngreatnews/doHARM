package doharm.rendering;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import doharm.logic.physics.Vector;

public class RenderUtil {

	private static int imgIsoW = 0;
	private static int imgIsoH = 0;

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
	 * This does not currently work
	 * 
	 * 
	 * @param col
	 * @param row
	 * @return
	 */
	public static Vector convertCoordsToIso(float col, float row, float layer){
		//TODO FIX THIS
		float x = (-(col*(imgIsoW/2)))+(row*(imgIsoW/2));
		float y = (col*(imgIsoH/2))+(row*(imgIsoH/2)) +layer*imgIsoH;
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
}
