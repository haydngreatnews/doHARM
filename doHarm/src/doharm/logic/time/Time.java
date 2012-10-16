package doharm.logic.time;

/**
 * Very simple ingame date.
 * Just Year X, month Y, day Z.
 * 
 * A day takes 3 minutes - and night takes 3 minutes.
 * 
 * sky colour will interpolate between day and night. Also depends on weather, 
 * which is constantly changing.
 * 
 * 
 * 
 * No leap years, month/day names, etc...
 * @author bewickrola
 */
public class Time 
{
	private int year;
	private int month;
	private int day;
	private float timeOfDay; //0 - 3 day 3-6 night 0 = full day, 3 = full night.
	private DayType dayType;
	private float light;
	private static final float TIME_INTERVAL = 1000f / Clock.CLOCK_INTERVAL;
	private static final float DAY_LENGTH = 60*1000*3;//3 mins
	
	
	public Time()
	{
		this(1,1,1,0);
	}
	
	public Time(int year, int month, int day, float timeOfDay)
	{
		this.year = year;
		this.month = month;
		this.day = day;
		this.timeOfDay = timeOfDay;
		dayType = DayType.DAY;
	}
	
	public void process()
	{
		timeOfDay += TIME_INTERVAL;
		if (timeOfDay > DAY_LENGTH)
		{
			timeOfDay -= DAY_LENGTH;
			
			nextDay();
		}
		
		light = (timeOfDay / DAY_LENGTH);
		
		
		//at 0: light = 1;
		//at 0.5: light = 0;
		//at 1: light = 1;
		
		light = Math.abs(light - 0.5f)*2;
		
		
		dayType = (light > 0.5f)? DayType.DAY : DayType.NIGHT;
		
	}
	
	public void nextDay() 
	{
		day++;
		if (day > 30)
		{
			month++;
			day = 1;
			if (month > 12)
			{
				month = 1;
				year++;
			}
		}
	}

	public float getLight()
	{
		return light;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public float getTimeOfDay() {
		return timeOfDay;
	}

	public void setTimeOfDay(float timeOfDay) 
	{
		this.timeOfDay = timeOfDay;
	}

	public DayType getDayType() {
		return dayType;
	}
	
	
	
	
}
