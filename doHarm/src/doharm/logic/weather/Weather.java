package doharm.logic.weather;

/**
 * Dynamic weather class. Depending on the weather type different things may happen.
 * 
 * @author bewickrola
 */

public class Weather 
{
	private static final float CHANGEABILITY = 0.001f;
	private static final float LIGHT_MULTIPLIER = 0.5f;
	private static final float TREND_OPPOSITION = 0.05f;
	private float conditions; // between 0 & 1, 0 = fine, 1 = stormy as bro
	private float trend;
	private WeatherType weatherType;
	
	
	public Weather()
	{
		conditions = 0.5f;
		trend = 0;
		calculateWeatherType();
	}
	
	public void process()
	{
		if (trend >= 0)
			trend += (Math.random()-0.5f-TREND_OPPOSITION)*CHANGEABILITY;
		else
			trend += (Math.random()-0.5f+TREND_OPPOSITION)*CHANGEABILITY;
		
		
		trend = Math.min(Math.max(trend, -1),1);
		
		conditions += trend;
		conditions = Math.min(Math.max(conditions, 0),1);

		calculateWeatherType();
	}
	
	private void calculateWeatherType() 
	{
		int type = (int) (conditions * (WeatherType.values().length-1));
		
		weatherType = WeatherType.values()[type];
	}
	
	public void setConditions(float conditions)
	{
		this.conditions = conditions;
		calculateWeatherType();
	}

	public float getLight()
	{
		return 1 - (conditions*LIGHT_MULTIPLIER);
	}
	
	public float getConditions()
	{
		return conditions;
	}
	
	public WeatherType getWeatherType()
	{
		return weatherType;
	}
	
}
