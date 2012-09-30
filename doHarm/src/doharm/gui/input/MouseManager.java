package doharm.gui.input;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import doharm.logic.Game;
import doharm.logic.world.World;
import doharm.logic.world.tiles.Tile;
import doharm.rendering.WorldRenderer;

public class MouseManager implements MouseListener, MouseMotionListener
{
	private Game game;
	private WorldRenderer renderer;

	public MouseManager(Game game, WorldRenderer renderer) 
	{
		this.game = game;
		this.renderer = renderer;
	}

	@Override
	public void mouseDragged(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		int imageRGB = renderer.getPickColourAt(e.getX(), e.getY());
		Color colour = new Color(imageRGB);
		
		int rgb = (colour.getRed() << 16 ) | (colour.getGreen()<<8) | colour.getBlue();
		
		if (rgb == 0)
			return; //outside area
		
		World world = game.getWorld();
		
		Tile tile = world.getTile(rgb);
		
		world.resetTiles(tile);
		world.getHumanPlayer().moveTo(tile);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
