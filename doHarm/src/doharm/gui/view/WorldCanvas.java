package doharm.gui.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import doharm.logic.AbstractGame;
import doharm.rendering.WorldRenderer;

public class WorldCanvas extends JPanel
{
	private static final long serialVersionUID = 1L;
	private WorldRenderer renderer;
	private AbstractGame game;
	
	public WorldCanvas(AbstractGame game, WorldRenderer renderer)
	{
		super();
		this.game = game;
		this.renderer = renderer;
	}
	
	@Override 
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Dimension canvasSize = new Dimension(getWidth(),getHeight());		
		game.run();
		renderer.redraw(canvasSize);
		g.drawImage(renderer.getImage(), 0,0,canvasSize.width,canvasSize.height,null);
		//paintChildren(g);
	}
	

}
