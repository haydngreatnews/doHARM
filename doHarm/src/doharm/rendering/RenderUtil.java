package doharm.rendering;

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
	
	/**convers "square" world coordinates to their corresponding isometric coordinates.
	 * 
	 * @param col
	 * @param row
	 * @return
	 */
	public static Vector convertCoordsToIso(float col, float row){
		//TODO FIX THIS
		float x = (-(col*(imgIsoW/2-1)))+(row*(imgIsoW/2-1));
		float y = (col*(imgIsoH/2-1))+(row*(imgIsoH/2-1));
		return new Vector(x, y);
		
	}
	
	static void setImgDimensions(int imgW, int imgH){
		imgIsoW = imgW;
		imgIsoH = imgH;
	}
}
