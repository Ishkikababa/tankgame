package TankGame;

import java.util.ArrayList;

public class ripCollisions2016
{
	public boolean shapeCollision(myShape nshapeA, myShape nshapeB)
	{
		//Collisions: http://www.gamedev.net/page/resources/_/technical/game-programming/2d-rotated-rectangle-collision-r2604
		//Vec2d: http://toxiclibs.org/docs/core/toxi/geom/Vec2D.html
		//shape[]s should be 0: ul, 1:ur, 2: dr, 3: dl
		
		myShape shapeA = new myShape(nshapeA.UR(), nshapeA.UL(), nshapeA.DR(), nshapeA.DL());
		myShape shapeB = new myShape(nshapeB.UR(), nshapeB.UL(), nshapeB.DR(), nshapeB.DL());
		
		Vector2D axis1 = new Vector2D(shapeA.UR().getX() - shapeA.UL().getX(), shapeA.UR().getY() - shapeA.UL().getY());
		Vector2D axis2 = new Vector2D(shapeA.UR().getX() - shapeA.DR().getX(), shapeA.UR().getY() - shapeA.DR().getY());
		
		Vector2D axis3 = new Vector2D(shapeB.UL().getX() - shapeB.DL().getX(), shapeB.UL().getY() - shapeB.DL().getY());
		Vector2D axis4 = new Vector2D(shapeB.UL().getX() - shapeB.UR().getX(), shapeB.UL().getY() - shapeB.UR().getY());
		
		// "A's Points For Axis X"
		ArrayList<Vector2D> apfa1 = pointsOnAxis(shapeA, axis1);
		ArrayList<Vector2D> bpfa1 = pointsOnAxis(shapeB, axis1);
		
		ArrayList<Vector2D> apfa2 = pointsOnAxis(shapeA, axis2);
		ArrayList<Vector2D> bpfa2 = pointsOnAxis(shapeB, axis2);
		
		ArrayList<Vector2D> apfa3 = pointsOnAxis(shapeA, axis3);
		ArrayList<Vector2D> bpfa3 = pointsOnAxis(shapeB, axis3);
		
		ArrayList<Vector2D> apfa4 = pointsOnAxis(shapeA, axis4);
		ArrayList<Vector2D> bpfa4 = pointsOnAxis(shapeB, axis4);
		
		
		if (pointsIntersectOnAxis(apfa1, bpfa1, axis1) && 
			 pointsIntersectOnAxis(apfa2, bpfa2, axis2) && 
			 pointsIntersectOnAxis(apfa3, bpfa3, axis3) && 
			 pointsIntersectOnAxis(apfa4, bpfa4, axis4))
		{
			return true;
		}
		
		return false;
	}
	
	private boolean pointsIntersectOnAxis(ArrayList<Vector2D> listA, ArrayList<Vector2D> listB, Vector2D axis)
	{
		ArrayList<Double> pointsForA = new ArrayList<Double>();
		ArrayList<Double> pointsForB = new ArrayList<Double>();
		
		for (int i = 0; i < listA.size(); i++)
		{
			pointsForA.add(listA.get(i).dotProduct(axis));
			pointsForB.add(listB.get(i).dotProduct(axis));
		}
		
		double minA = pointsForA.get(0);
		double maxA = pointsForA.get(0);
		
		double minB = pointsForB.get(0);
		double maxB = pointsForB.get(0);
		
		for (int i=0;i<pointsForA.size(); i++)
		{
			if (pointsForA.get(i) < minA)
			{
				minA = pointsForA.get(i);
			}
			if (pointsForA.get(i) > maxA)
			{
				maxA = pointsForA.get(i);
			}
			
			if (pointsForB.get(i) < minB)
			{
				minB = pointsForB.get(i);
			}
			if (pointsForB.get(i) > maxB)
			{
				maxB = pointsForB.get(i);
			}
		}
		
		if (minB <= maxA && maxB >= minA)
			return true;

		return false;
	}

	public ArrayList<Vector2D> pointsOnAxis(myShape shape, Vector2D axis)
	{
		ArrayList<Vector2D> points = new ArrayList<Vector2D>();
		for (int i=0;i<4; i++)
		{
			Vector2D tempV = shape.getVector2D(i);
			double x = ( (tempV.getX()*axis.getX() + tempV.getY()*axis.getY()) /
						 (Math.pow(axis.getX(), 2) * Math.pow(axis.getY(), 2)) ) * axis.getX();
			double y = ( (tempV.getX()*axis.getX() + tempV.getY()*axis.getY()) /
						 (Math.pow(axis.getX(), 2) * Math.pow(axis.getY(), 2)) ) * axis.getY();
			
			points.add(new Vector2D(x, y));
		}
		
		return points;
	}

	//actually good
	boolean isPolygonsIntersecting(myShape a, myShape b)
	{
	    for (int x=0; x<2; x++)
	    {
	        myShape polygon = (x==0) ? a : b;
	       
	        for (int i1=0; i1<4; i1++)
	        {
	            int   i2 = (i1 + 1) % 4;
	            Vector2D p1 = polygon.getVector2D(i1);
	            Vector2D p2 = polygon.getVector2D(i2);
	           
	            Vector2D normal = new Vector2D(p2.getY() - p1.getY(), p1.getX() - p2.getX());
	           
	            double minA = Double.MAX_VALUE;
	            double maxA = Double.MIN_VALUE;
	           
	            for (int j = 0; j < 4; j++)
	            {
	            	Vector2D point = a.getVector2D(j);
	               double projected = normal.getX() * point.getX() + normal.getY() * point.getY();
	               
	               if (projected < minA)
	                   minA = projected;
	               if (projected > maxA)
	                   maxA = projected;
	            }
	           
	            double minB = Double.MAX_VALUE;
	            double maxB = Double.MIN_VALUE;
	           
	            for (int j = 0; j < 4; j++)
	            {
	            	Vector2D point = b.getVector2D(j);
	                double projected = normal.getX() * point.getX() + normal.getY() * point.getY();
	               
	                if (projected < minB)
	                    minB = projected;
	                if (projected > maxB)
	                    maxB = projected;
	            }
	           
	            if (maxA < minB || maxB < minA)
	                return false;
	        }
	    }
	   
	    return true;
	}
	
}
