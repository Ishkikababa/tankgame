package TankGame;

import java.awt.Image;

public class Bullet implements GlobalConst
{
	private double myX, myY, myWidth, myHeight; 
	protected int bouncesLeft;
	private int timeLeft;
	private double theta;
	protected double xInc = 0;
	protected double yInc = 0;
	private boolean amded = false;
	private String myOwner;
	
	public Bullet(double x, double y, double gtheta, String original)
	{
		myX = x;
		myY = y;
		myWidth = vars.missileImage.getWidth(null);
		myHeight = vars.missileImage.getHeight(null);
		bouncesLeft = NUM_BULLET_BOUNCES;
		timeLeft = 1000;
		theta = gtheta;
		if (theta != -1)
		{
			xInc = BULLET_SPEED * Math.cos(Math.toRadians(theta));
			yInc = BULLET_SPEED * Math.sin(Math.toRadians(theta));
		}
		
		myOwner = original;
	}
	
	public void moveBullet()
	{
		myX += xInc;
		myY += yInc;
		timeLeft--;
		if (timeLeft < 1)
			amded = true;
	}
	
	public void bounce(String d)
	{
		bouncesLeft--;
		if (d.equals("V"))
		{
			xInc *= -1;
		}
		else if (d.equals("H"))
		{
			yInc *= -1;
		}
		else
		{
			xInc *= -1;
			yInc *= -1;
		}
		
//		while(collidesWithWalls(this.getShape()) != null)
		this.moveBullet();
		
		if (bouncesLeft < 0)
		{
			amded = true;
		}
	}
	
	public double getX()
	{
		return myX;
	}
	
	public double getY()
	{
		return myY;
	}
	
	public double getWidth()
	{
		return myWidth;
	}
	
	public double getHeight()
	{
		return myHeight;
	}
	
	public boolean isDead()
	{
		return amded;
	}
	
	public void setDead(boolean s)
	{
		amded = s;
	}
	
	public myShape getShape()
	{
		Vector2D UR = new Vector2D(myX + myWidth, myY);
		Vector2D UL = new Vector2D(myX, myY);
		Vector2D DR = new Vector2D(myX + myWidth, myY + myHeight);
		Vector2D DL = new Vector2D(myX, myY + myHeight);

		return new myShape(UR, UL, DR, DL);
	}
	
	public String getOwner()
	{
		return myOwner;
	}
	
	public Image getImage()
	{
		return vars.bulletImage;
	}
	
	public Wall collidesWithWalls(myShape shape)
	{
		vars.badWall = null;
		for (int i=0;i<walls.size();i++)
		{
			if (walls.get(i).getShape().collidesWith(shape))
			{
				vars.badWall = walls.get(i).getShape();
				return walls.get(i);
			}
		}
		return null;
	}	
	
	public boolean isGhost()
	{
		return false;
	}
	
	public boolean isBomb()
	{
		return false;
	}
}