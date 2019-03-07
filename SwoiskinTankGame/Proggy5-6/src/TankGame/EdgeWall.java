package TankGame;

public class EdgeWall extends Wall
{

	public EdgeWall(int gX, int gY, int gW, int gH)
	{
		super(gX, gY, gW, gH);
	}
	
	public boolean isEdgeWall()
	{
		return true;
	}

}
