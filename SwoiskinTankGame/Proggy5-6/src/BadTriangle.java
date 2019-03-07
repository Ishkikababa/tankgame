import gpdraw.DrawingTool;
import gpdraw.SketchPad;

public class BadTriangle
{
	SketchPad pad = new SketchPad(500, 500);
	DrawingTool pen = new DrawingTool(pad);
	
	public BadTriangle()
	{
		pen.up();
		pen.move(200, 200);
		pen.down();
		pen.setDirection(0);
	}
	
	public void drawT(int level, int length)
	{
		for (int i=0;i<3;i++)
		{
			pen.forward(length/2);
			if (level > 1)
			{
				pen.turnLeft(120);
				drawT(level-1, length/2);
			}
			pen.forward(length/2);
			if (level%2==0)
				pen.turnLeft(120);
			else
				pen.turnRight(120);
		}
	}
}
