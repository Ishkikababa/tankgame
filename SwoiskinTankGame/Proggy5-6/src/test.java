import java.util.Scanner;

public class test
{
	public static void main(String[] args)
	{
		Fractal f = new Fractal();

//		for (int i=0;i<8;i++)
//		f.drawDragon(12, 2, "r", "GRAY");
//		f.drawDragon(12, 2, "r", "RED");
//		f.drawDragon(12, 2, "r", "ORANGE");
//		f.drawDragon(12, 2, "r", "YELLOW");
//		f.drawDragon(12, 2, "r", "GREEN");
//		f.drawDragon(12, 2, "r", "BLUE");
//		f.drawDragon(12, 2, "r", "MAGENTA");
//		f.drawDragon(12, 2, "r", "BLACK");

		f.drawWeirdSierpinski(100000, new Point[]{new Point(-300, 0), new Point(500, -200), new Point(0, 100)});
		
//		CantorSet c = new CantorSet();
//		c.drawC(10, 400);
	}
}