package TankGame;

public class Axis
{
	private double myX, myY;

	public Axis(int gX, int gY)
	{
		myX = gX;
		myY = gY;
	}
	
	public Axis()
	{
		myX = 0;
		myY = 0;
	}
	
	public double getX()
	{
		return myX;
	}
	
	public void setX(double gx)
	{
		myX = gx;
	}
	
	public double getY()
	{
		return myY;
	}
	
	public void setY(double gy)
	{
		myY = gy;
	}
}
