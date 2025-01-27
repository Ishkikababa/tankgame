package TankGame;

import java.awt.Image;

public class TankPower implements GlobalConst
{
	private String type;
	private int time;
	
	public TankPower(String type, int time)
	{
		this.type = type;
		this.time = time;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public int getTimeRemaining()
	{
		return this.time;
	}
	
	public void tickDown(int d)
	{
		this.time -= d;
	}
	
	public String toString()
	{
		return type.toUpperCase() + " (" + ((int)time/100 + 1)  + ")";
	}
}