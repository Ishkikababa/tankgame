package TankGame;

import java.awt.Image;

public class Shrapnel extends Bullet
{

	public Shrapnel(double x, double y, double gtheta, String original)
	{
		super(x, y, gtheta, original);
		this.bouncesLeft = 0;
	}
	
	public Image getImage()
	{
		return vars.bombBulletImage;
	}
}
