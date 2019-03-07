package TankGame;

import java.awt.Image;

public class GhostBullet extends Bullet
{
	public GhostBullet(double x, double y, double gtheta, String original)
	{
		super(x, y, gtheta, original);
	}
	
	public boolean isGhost()
	{
		return true;
	}
	
	public Image getImage()
	{
		return vars.ghostBulletImage;
	}
}
