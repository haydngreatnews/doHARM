package doharm.logic.camera;

import java.awt.Dimension;

import doharm.logic.physics.Vector;
import doharm.rendering.RenderUtil;

public class Camera 
{
	private Vector position;
	private Vector renderPosition;
	public Dimension canvasDimensions;
	private int tileWidth;
	private int tileHeight;
	
	public Camera(int tileWidth, int tileHeight)
	{
		position = new Vector();
		renderPosition = new Vector();
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}
	
	public void setCanvasDimensions(Dimension dimension)
	{
		canvasDimensions = dimension;
	}

	public Vector getPosition() 
	{
		return position;
	}
	
	public void setPosition(float x, float y)
	{
		position.set(x,y);
	}
	
	
	/*public Vector getRenderPosition2() 
	{
		renderPosition.setX(position.getX()-canvasDimensions.width/2);
		renderPosition.setY(position.getY()-canvasDimensions.height/2);
		return renderPosition;
	}*/
	
	public Vector getRenderPosition() 
	{
		renderPosition.setX(position.getX());
		renderPosition.setY(position.getY());
		renderPosition.divide(tileWidth, tileHeight);
		
		
		
		renderPosition = RenderUtil.convertCoordsToIso(renderPosition.getX(), renderPosition.getY());
		
		
		renderPosition.addX(-canvasDimensions.width/2);
		renderPosition.addY(-canvasDimensions.height/2);
		return renderPosition;
	}
}
