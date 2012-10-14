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
	private Direction direction;
	
	public Camera(int tileWidth, int tileHeight)
	{
		position = new Vector();
		renderPosition = new Vector();
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		direction = Direction.NORTH;
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
		
		
		
		renderPosition = RenderUtil.convertCoordsToIso(renderPosition.getX(), renderPosition.getY(), 0, this);
		
		
		renderPosition.addX(-canvasDimensions.width/2);
		renderPosition.addY(-canvasDimensions.height/2);
		return renderPosition;
	}

	public void turnLeft(){
		int next = direction.ordinal()-1;
		if (next < 0)
			next = Direction.values().length-1;
		direction = Direction.values()[next];
	}
	
	public void turnRight() 
	{
		int next = (direction.ordinal()+1)%Direction.values().length;
		direction = Direction.values()[next];
	}
	
	public Direction getDirection()
	{
		return direction;
	}
}
