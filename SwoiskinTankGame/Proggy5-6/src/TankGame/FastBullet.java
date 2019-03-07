package TankGame;

public class FastBullet extends Bullet implements GlobalConst
{
	public FastBullet(double x, double y, double gtheta, String original)
	{
		super(x, y, gtheta, original);
		this.xInc *= SPEED_MOD;
		this.yInc *= SPEED_MOD;
	}
}
