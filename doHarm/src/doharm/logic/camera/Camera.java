package doharm.logic.camera;

import java.awt.Dimension;

import doharm.logic.physics.Vector;
import doharm.rendering.RenderUtil;

/**
 * The camera determines how the world will be displayed to the player, who is always in the centre of the screen.
 * You can turn the camera to face four different directions.
 * 
 * @author Roland
 */

public class Camera 
{
	private Vector position;
	private Vector renderPosition;
	public Dimension canvasDimensions;
	private int tileWidth;
	private int tileHeight;
	private CameraDirection direction;
	
	/**
	 * Create a new Camera.
	 * Tile dimensions needed to find the row and column of the player.
	 * @param tileWidth the width of a tile
	 * @param tileHeight the height of a tile
	 */
	public Camera(int tileWidth, int tileHeight)
	{
		position = new Vector();
		renderPosition = new Vector();
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		direction = CameraDirection.NORTH;
	}
	
	/**
	 * Camera needs to put the player in the centre of the canvas, so we need to know
	 * how big the canvas is!
	 * @param dimension the size of the canvas.
	 */
	public void setCanvasDimensions(Dimension dimension)
	{
		canvasDimensions = dimension;
	}

	/**
	 * @return the ACTUAL position of the camera.
	 */
	public Vector getPosition() 
	{
		return position;
	}
	
	/**
	 * Set the ACTUAL position of the camera.
	 * @param x
	 * @param y
	 */
	public void setPosition(float x, float y)
	{
		position.set(x,y);
	}
	
	
	/**
	 * The render position is different to the actual position of the camera,
	 * As it has to convert the position to isometric and subtract half the canvas size
	 * So that the player is centred on the screen.
	 * 
	 * @return the render position of the camera
	 */
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

	/**
	 * Turn the camera anti-clockwise
	 */
	public void turnLeft(){
		int next = direction.ordinal()-1;
		if (next < 0)
			next = CameraDirection.values().length-1;
		direction = CameraDirection.values()[next];
	}
	
	/**
	 * Turn the camera clockwise
	 */
	public void turnRight() 
	{
		int next = (direction.ordinal()+1)%CameraDirection.values().length;
		direction = CameraDirection.values()[next];
	}
	
	public CameraDirection getDirection()
	{
		return direction;
	}
}
