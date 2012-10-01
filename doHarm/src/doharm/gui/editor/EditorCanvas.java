package doharm.gui.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import doharm.rendering.RenderUtil;

public class EditorCanvas extends JPanel{
	ArrayList<int[][]> layers = new ArrayList<int[][]>();
	int currentLayer = 0;
	public EditorCanvas(){
		super();
		setBackground(Color.black);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage below = null;
		if (currentLayer > 0 && layers.get(currentLayer-1) != null){
			below = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
			drawLayer(layers.get(currentLayer-1), below.createGraphics());
		}
		//Do scaling
		if (below != null){
			RenderUtil.scaleImage(below, (int)(getWidth()*.9), (int) (getHeight()*.9));
		}
		//g.
	}

	private void drawLayer(int[][] layer, Graphics2D g) {
		// TODO Auto-generated method stub
		
	}	
}
