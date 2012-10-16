package doharm.gui.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import doharm.logic.AbstractGame;
import doharm.logic.world.World;
import doharm.logic.world.tiles.Tile;
import doharm.rendering.WorldRenderer;

public class MouseManager implements MouseListener, MouseMotionListener
{
	private AbstractGame game;
	private WorldRenderer renderer;

	public MouseManager(AbstractGame game, WorldRenderer renderer) 
	{
		this.game = game;
		this.renderer = renderer;
	}
	
	
	
	
	private void hover(int x, int y) 
	{
		int rgb = renderer.getPickColourAt(x, y);
		
		if (rgb == 0)
			return; //outside area
		
		World world = game.getWorld();
		
		Tile tile = world.getTile(rgb);
		
		world.getHumanPlayer().hover(tile);
	}
	
	private void click(int button, boolean down)
	{
		
		World world = game.getWorld();
		world.getHumanPlayer().click(button,down);
	}
	
	

	@Override
	public void mouseDragged(MouseEvent e) 
	{
		hover(e.getX(),e.getY());
		click(e.getButton(),true);
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		hover(e.getX(),e.getY());
		
	}

	

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		click(e.getButton(),true);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		click(e.getButton(),false);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
