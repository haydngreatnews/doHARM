package doharm.gui.view;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import doharm.rendering.WorldRenderer;

public class WorldCanvas extends JComponent
{
	private static final long serialVersionUID = 1L;
	private WorldRenderer renderer;
	
	public WorldCanvas(WorldRenderer renderer)
	{
		this.renderer = renderer;
		
	}
	
	@Override 
	public void paint(Graphics g)
	{
		Dimension canvasSize = new Dimension(getWidth(),getHeight());
		
		renderer.redraw(canvasSize);
		g.drawImage(renderer.getImage(), 0,0,canvasSize.width,canvasSize.height,null);
	}
	
}
