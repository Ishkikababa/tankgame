import java.util.ArrayList;
import gpdraw.DrawingTool;
import gpdraw.SketchPad;

public class CantorSet
{
	SketchPad pad = new SketchPad(500, 500);
	DrawingTool pen = new DrawingTool(pad);
	
	public CantorSet()
	{
		pen.up();
		pen.move(-200, 200);
		pen.down();
		pen.setWidth(4);
		pen.setDirection(0);
	}
	
	public void drawC(double level, double length)
	{
		double cx = pen.getXPos();
		double cy = pen.getYPos();
		
		if (level > 0)
		{
			cx = pen.getXPos();
			cy = pen.getYPos();
			
			pen.up();
			pen.move(cx, cy-40);
			pen.setDirection(0);
			drawC(level-1, length/3);
			
			pen.up();
			pen.move(cx, cy);
			pen.setDirection(0);
			pen.down();
			pen.forward(length/3);
			
			pen.up();
			pen.forward(length/3);
			
			cx = pen.getXPos();
			cy = pen.getYPos();
			
			pen.up();
			pen.move(cx, cy-40);
			pen.setDirection(0);
			drawC(level-1, length/3);
			
			pen.up();
			pen.move(cx, cy);
			pen.setDirection(0);
			pen.down();
			pen.forward(length/3);
		}
		else
		{
			
		}
	}
}