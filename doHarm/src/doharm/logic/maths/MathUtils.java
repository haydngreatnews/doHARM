package doharm.logic.maths;

public class MathUtils 
{
	/**
	 * Reduce a float value to a certain amount of decimal places
	 * @param value the value to decrease precision
	 * @param dp the amount of decimal places
	 * @return the reduced value 
	 */
	public static float toDP(float value, int dp) 
	{
		int temp=(int)((value*Math.pow(10,dp)));
		return (float)(temp/Math.pow(10,dp));
	}
}
