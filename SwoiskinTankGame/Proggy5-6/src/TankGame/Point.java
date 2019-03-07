package TankGame;

public class Point
{
	private double myX, myY;
	
	public Point(double gx, double gy)
	{
		myX = gx;
		myY = gy;
	}
	
	public void setX(double gx)
	{
		myX = gx;
	}
	
	public void changeX(double gx)
	{
		myX += gx;
	}
	
	public void setY(double gy)
	{
		myY = gy;
	}
	
	public void changeY(double gy)
	{
		myY += gy;
	}
	
	public double x()
	{
		return myX;
	}
	
	public double y()
	{
		return myY;
	}
}