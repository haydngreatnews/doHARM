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
	public int getXAsInt() 
	{
		return (int)x;
	}
	
	/**
	 * @return an integer version of this vector's y component
	 */
	public int getYAsInt() 
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
	
	public void set(float x, float y) 
	{
		this.x = x;
		this.y = y;
		updateLength();
	}
	public void set(Vector v) 
	{
		set(v.x,v.y);
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

	public void add(Vector v) 
	{
		x += v.x;
		y += v.y;
		updateLength();
	}

	public Vector subtract(Vector v) 
	{
		return new Vector(x-v.x,y-v.y);
	}

	@Override 
	public boolean equals(Object other)
	{
		if (other == null || other.getClass() != getClass())
			return false;
		Vector v = (Vector)other;
		if (v.x != x || v.y != y)
			return false;
		
		return true;
	}

	public void reset() 
	{
		x = 0;
		y = 0;
		updateLength();
	}

	

	
	
	
}
