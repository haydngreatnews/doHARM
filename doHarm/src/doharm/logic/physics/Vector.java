package doharm.logic.physics;

public class Vector 
{
	private float x;
	private float y;
	private float length;
	
	public Vector(float x, float y) 
	{
		this.x = x;
		this.y = y;
		updateLength();
	}
	
	public Vector() 
	{
		this(0,0);
	}

	public void normalize()
	{
		x /= length;
		y /= length;
		updateLength();
	}

	private void updateLength() 
	{
		length = (float) Math.sqrt(x*x+y*y);
	}

	public float getX() 
	{
		return x;
	}
	
	public float getY() 
	{
		return y;
	}

	/**
	 * @return an integer version of this vector's x component
	 */
	public int getXi() 
	{
		return (int)x;
	}
	
	/**
	 * @return an integer version of this vector's y component
	 */
	public int getYi() 
	{
		return (int)y;
	}
	
	public void setX(float x) 
	{
		this.x = x;
		updateLength();
	}

	public void setY(float y) 
	{
		this.y = y;
		updateLength();
	}
	
	public void multiply(float value)
	{
		x *= value;
		y *= value;
		updateLength();
	}

	public float getLength() 
	{
		return length;
	}

	
	
	
}
