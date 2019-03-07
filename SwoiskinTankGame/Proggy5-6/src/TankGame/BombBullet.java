package TankGame;

import java.awt.Image;

public class BombBullet extends Bullet
{
	public BombBullet(double x, double y, double gtheta, String original)
	{
		super(x, y, gtheta, original);
	}
	
	public boolean isBomb()
	{
		return true;
	}
	
	public Image getImage()
	{
		return vars.bombBulletImage;
	}
}