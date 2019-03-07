// Aaron Swoiskin
// Redwood High School
// APCS
// September 18, 2015
// KochCurve.java

import gpdraw.DrawingTool;
import gpdraw.SketchPad;

public class KochCurve
{
	SketchPad pad = new SketchPad(500, 500);
	DrawingTool pen = new DrawingTool(pad);
	
	public KochCurve()
	{
		pen.up();
		pen.move(0, 0);
		pen.down();
	}

	void drawKochCurve(int depth, int width)
	{	
		if (depth < 1)
			pen.forward(width);
		else
		{
			drawKochCurve(depth-1, width/3);
			pen.turnLeft(60);
			drawKochCurve(depth-1, width/3);
			pen.turnRight(120);
			drawKochCurve(depth-1, width/3);
			pen.turnLeft(60);
			drawKochCurve(depth-1, width/3);
		}
	}
	
	void drawSnowFlake(int depth, int width)
	{
		drawKochCurve(depth, width);
		pen.turnRight(120);
		drawKochCurve(depth, width);
		pen.turnRight(120);
		drawKochCurve(depth, width);
		pen.turnRight(120);
	}
}