package TankGame;

public class Wall implements GlobalConst
{
	private int myX, myY, myWidth, myHeight;
	private String myOrientation;

	public Wall(int gX, int gY, int gW, int gH)
	{
		myX = gX;
		myY = gY;
		myWidth = gW;
		myHeight = gH;
		
		if (myWidth > myHeight)
		{
			myOrientation = "H";
		}
		else if (myWidth < myHeight)
		{
			myOrientation = "V";
		}
		else if (myWidth == myHeight)
		{
			myOrientation = "C";
		}
	}
	
	public Wall(int gX, int gY, int gW, int gH, String or)
	{
		myX = gX;
		myY = gY;
		myWidth = gW;
		myHeight = gH;
		myOrientation = or;
	}
	
	public int getX()
	{
		return myX;
	}
	
	public int getY()
	{
		return myY;
	}
	
	public int getWidth()
	{
		return myWidth;
	}
	
	public int getHeight()
	{
		return myHeight;
	}
	
	public myShape getShape()
	{
		Vector2D UR = new Vector2D(myX + myWidth, myY);
		Vector2D UL = new Vector2D(myX, myY);
		Vector2D DR = new Vector2D(myX + myWidth, myY + myHeight);
		Vector2D DL = new Vector2D(myX, myY + myHeight);

		return new myShape(UL, UR, DR, DL); //REAL
		//return new myShape(DL, DR, UR, UL);
	}
	
	public String getOrientation()
	{
		return myOrientation;
	}
	
	public boolean isEdgeWall()
	{
		return false;
	}
}