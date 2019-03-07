package TankGame;

public class Explosion
{
	private double x, y, radius, maxRadius, growthRate;
	
	public Explosion(double gx, double gy, double gr)
	{
		x = gx;
		y = gy;
		maxRadius = gr;
		radius = 0;
		growthRate = 10;
	}
	
	public double getRadius() {return radius;}
	public double getMaxRaidus() {return maxRadius;}
	public double getX() {return x;}
	public double getY() {return y;}
	public double getGrowthRate() {return growthRate;}
	
	public void setRadius(double newR) {radius = newR;}
	public void changeRadius(double change) {radius += change;}
	public void growRadius() {radius += growthRate;}
}
