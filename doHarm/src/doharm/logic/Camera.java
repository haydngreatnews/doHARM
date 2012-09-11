package doharm.logic;

import java.awt.Dimension;

import doharm.logic.physics.Vector;

public class Camera 
{
	private Vector position;
	private Vector renderPosition;
	public Dimension canvasDimensions;
	
	public Camera()
	{
		position = new Vector();
		renderPosition = new Vector();
	}
	
	public void setCanvasDimensions(Dimension dimension)
	{
		canvasDimensions = dimension;
	}

	public Vector getPosition() 
	{
		return position;
	}
	
	public Vector getRenderPosition() 
	{
		renderPosition.setX(position.getX() + canvasDimensions.width/2);
		renderPosition.setY(position.getY() + canvasDimensions.height/2);
		return renderPosition;
	}
}
