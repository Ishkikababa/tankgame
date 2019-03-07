//package TankGame;
//
//public class MoveFunctionLegacy
//{
//	// I'm not sorry for the length of this method
//	public void moveTank(String t, String d, double speed)
//	{
//		int buffer = 2;
//		double angleBuffer = 1;
//		myShape temp;
//		Vector2D c;
//		double smoothBounceSpeed = 1;
//		
//		if (t.equals("red"))
//		{
//			double rcos = Math.cos(Math.toRadians(vars.redDirection)) * speed * vars.redSpeedModifier;
//			double rsin = Math.sin(Math.toRadians(vars.redDirection)) * speed * vars.redSpeedModifier;
//			temp = new myShape(vars.rul, vars.rur, vars.rdr, vars.rdl);
//			c = findCenter(temp);
//			vars.tankTemp = temp;
//			vars.tempCenter = c;
//			double tempAngle = vars.redDirection/180 + DIRECTION_INCREMENTER + angleBuffer;
//			
//			switch(d)
//			{
////				case "w":
////					if (moveShapeOk(temp, "f", rcos*buffer, rsin*buffer))
////					// pass "rcos * buffer" as speed
////					{
////						vars.redTankX += rcos;
////						vars.redTankY += rsin;
////					}
////					else
////					{
////						//moveShapeOk(temp, "b", rcos*buffer);
////						try
////						{
////						if (0 < vars.redDirection && vars.redDirection < 90 ||
////							 270 > vars.redDirection && vars.redDirection > 180)
////						{
////							if (collidesWithWalls(temp) != null && collidesWithWalls(temp).getOrientation().equals("H"))
////							{////////////////////////////////////////////////////////	
////								if (moveShapeOk(temp, "b", rcos*buffer, rsin*buffer))
////									moveTank("red", "s", smoothBounceSpeed);
////								if (rotateShapeOk(temp, "l", tempAngle))
////									moveTank("red", "a", smoothBounceSpeed);
////							}
////							else if (collidesWithWalls(temp) != null && collidesWithWalls(temp).getOrientation().equals("V"))
////							{
////								if (moveShapeOk(temp, "b", rcos*buffer, rsin*buffer))
////									moveTank("red", "s", smoothBounceSpeed);
////								if (rotateShapeOk(temp, "r", tempAngle))
////									moveTank("red", "d", smoothBounceSpeed);
////							}
////						}
////						else if (vars.redDirection > 270 || 180 > vars.redDirection && vars.redDirection > 90)
////						{
////							if (collidesWithWalls(temp) != null && collidesWithWalls(temp).getOrientation().equals("H"))
////							{
////								if (moveShapeOk(temp, "b", rcos*buffer, rsin*buffer))
////									moveTank("red", "s", smoothBounceSpeed);
////								if (rotateShapeOk(temp, "r", tempAngle))
////									moveTank("red", "d", smoothBounceSpeed);
////							}
////							else if (collidesWithWalls(temp) != null && collidesWithWalls(temp).getOrientation().equals("V"))
////							{
////								if (moveShapeOk(temp, "b", rcos*buffer, rsin*buffer))
////									moveTank("red", "s", smoothBounceSpeed);
////								if (rotateShapeOk(temp, "l", tempAngle))
////									moveTank("red", "a", smoothBounceSpeed);
////							}
////						}
////						}
////						catch(Error e)
////						{
////							System.out.println("nope");
////							while (collidesWithWalls(temp) != null)
////							{
////								for (int i=0;i<4;i++)
////								{
////									temp.getVector2D(i).setX(temp.getVector2D(i).getX() + rcos * buffer);
////									temp.getVector2D(i).setY(temp.getVector2D(i).getY() + rsin * buffer);
////								}
////								vars.redTankX -= rcos;
////								vars.redTankY -= rsin;
////							}
////						}
////					}
////					break; 
//				case "s":
////					for (int i=0;i<4;i++) {
////						temp.getVector2D(i).setX(temp.getVector2D(i).getX() - rcos * buffer);
////						temp.getVector2D(i).setY(temp.getVector2D(i).getY() - rsin * buffer);
////					};
//					// pass "rcos * buffer" as speed
//					if (moveShapeOk(temp, "b", rcos*buffer, rsin*buffer))
//					{
//						vars.redTankX -= rcos;
//						vars.redTankY -= rsin;
//					}
//					else if (collidesWithWalls(temp) != null)
//					{
//						try
//						{
//						if (0 + 1 < vars.redDirection && vars.redDirection < 90 - 1 ||
//							 270  - 1 > vars.redDirection && vars.redDirection > 180 + 1)
//						{
//							if (collidesWithWalls(temp).getOrientation().equals("H"))
//							{
//								if (moveShapeOk(temp, "f", rcos*buffer, rsin*buffer))
//									moveTank("red", "w", smoothBounceSpeed);
//								if (rotateShapeOk(temp, "l", tempAngle))
//									moveTank("red", "a", smoothBounceSpeed);
//							}
//							else if (collidesWithWalls(temp).getOrientation().equals("V"))
//							{
//								if (moveShapeOk(temp, "f", rcos*buffer, rsin*buffer))
//									moveTank("red", "w", smoothBounceSpeed);
//								if (rotateShapeOk(temp, "r", tempAngle))
//									moveTank("red", "d", smoothBounceSpeed);
//							}
//						}
//						else if (vars.redDirection > 270 + 1 || 180 + 1 > vars.redDirection && vars.redDirection > 90 + 1)
//						{
//							if (collidesWithWalls(temp).getOrientation().equals("H"))
//							{
//								if (moveShapeOk(temp, "f", rcos*buffer, rsin*buffer))
//									moveTank("red", "w", smoothBounceSpeed);
//								if (rotateShapeOk(temp, "r", tempAngle))
//									moveTank("red", "d", smoothBounceSpeed);
//							}
//							else if (collidesWithWalls(temp).getOrientation().equals("V"))
//							{
//								if (moveShapeOk(temp, "f", rcos*buffer, rsin*buffer))
//									moveTank("red", "w", smoothBounceSpeed);
//								if (rotateShapeOk(temp, "l", tempAngle))
//									moveTank("red", "a", smoothBounceSpeed);
//							}
//						}
//						}
//						catch(Error e)
//						{
//							System.out.println("nope");
//							while(collidesWithWalls(temp) != null)
//							{
//								for (int i=0;i<4;i++) {
//									temp.getVector2D(i).setX(temp.getVector2D(i).getX() - rcos * buffer);
//									temp.getVector2D(i).setY(temp.getVector2D(i).getY() - rsin * buffer);
//								};
//								vars.redTankX += rcos;
//								vars.redTankY += rsin;
//							}
//						}
//					}
//					break; 
//				case "a":
//					temp = new myShape(vars.rul, vars.rur, vars.rdr, vars.rdl);
//					for (int i=0;i<4;i++)
//					{
//						temp.getVector2D(i).setPoint(rotatePoint(temp.getVector2D(i), findCenter(temp), Math.toRadians(-tempAngle)));
//					}
//					//replace
//					if (collidesWithWalls(temp) == null)
//					{
//						vars.redDirection -= DIRECTION_INCREMENTER * Math.sqrt(vars.redSpeedModifier);
//					}
//					else
//					{
//						int corner = findClosestCorner(temp, collidesWithWalls(temp));
//						String dr = collidesWithWalls(temp).getOrientation();
//						
//						//x accommodation
//						int xacc = 0, yacc = 0;
//						double tdr = vars.redDirection;
//						
//						System.out.println(corner + collidesWithWalls(temp).getOrientation());
//						// not quite, works sometimes 
//						if (dr.equals("H"))
//						{
//							if (0 <= tdr && tdr <= 180)
//								yacc = -1;
//							else
//								yacc = 1;
//							
//							if (corner == 0 || corner == 3)
//								yacc *= -1;
//						}
//						else if (dr.equals("V"))
//						{
//							if (90 <= tdr && tdr <= 270)
//								xacc = 1;
//							else
//								xacc = -1;
//							if (corner == 1 || corner == 1)
//								xacc *= -1;
//						}
//						
//						for (int i=0;i<4;i++)
//						{
//							temp.getVector2D(i).setPoint(new Vector2D(temp.getVector2D(i).getX() + smoothBounceSpeed*xacc, temp.getVector2D(i).getY() + smoothBounceSpeed*yacc));
//						}
//						if (collidesWithWalls(temp) == null)
//						{
//
//							// do some trig boyyyy
//							// the tank x/y will translate to one of the corners, depending on which angle/wall/corner
//							// ^^ not true usually; make a new point, translate, set x/y to it's x/y
//							
//							// doesn't quite work if the back corner collides with the wall
//							if (dr.equals("V"))
//								vars.redTankX += smoothBounceSpeed * xacc;
//							else if (dr.equals("H"))
//								vars.redTankY -= smoothBounceSpeed * yacc;
//							
//							vars.redDirection -= DIRECTION_INCREMENTER * Math.sqrt(vars.redSpeedModifier);
//							//sqrt arbitrary
//						}
//					}
//					break;
//				case "d":
//					temp = new myShape(vars.rul, vars.rur, vars.rdr, vars.rdl);
//					for (int i=0;i<4;i++)
//					{
//						temp.getVector2D(i).setPoint(rotatePoint(temp.getVector2D(i), c, Math.toRadians(tempAngle)));
//					}
//					if (collidesWithWalls(temp) == null)
//					{
//						vars.redDirection += DIRECTION_INCREMENTER * Math.sqrt(vars.redSpeedModifier);
//					}
//					else
//					{
//						// WEIRD ////////////////////////////////////////////////////////////////////////
//						int corner = findClosestCorner(temp, collidesWithWalls(temp));
//						String dr = collidesWithWalls(temp).getOrientation();
//						
//						//x accommodation
//						int xacc = 0, yacc = 0;
//						double tdr = vars.redDirection;
//						if (dr.equals("H"))
//						{
//							if (0 <= tdr && tdr <= 180)
//							{
//								yacc = -1;
//							}
//							else
//							{
//								yacc = 1;
//							}
//							
//							if (corner == 0 || corner == 3)
//							{
//								yacc *= -1;
//							}
//						}
//						else if (dr.equals("V"))
//						{
//							if (90 < tdr && tdr <= 270)
//							{
//								xacc = -1;
//							}
//							else
//							{
//								xacc = 1;
//							}
//							if (corner == 0 || corner == 3)
//							{
//								xacc *= -1;
//							}
//						}
//						
//						for (int i=0;i<4;i++)
//						{
//							temp.getVector2D(i).setPoint(new Vector2D(temp.getVector2D(i).getX() + smoothBounceSpeed*xacc, temp.getVector2D(i).getY() + smoothBounceSpeed*yacc));
//						}
//						if (collidesWithWalls(temp) == null)
//						{
//							// do some trig boyyyy
//							// the tank x/y will translate to one of the corners, depending on which angle/wall/corner
//							// ^^ not true usually; make a new point, translate, set x/y to it's x/y
//							// translate directly away from wall (!)
//							
//							// doesn't quite work if the back corner collides with the wall
//							if (dr.equals("V"))
//								vars.redTankX -= smoothBounceSpeed * xacc;
//							else if (dr.equals("H"))
//								vars.redTankY += smoothBounceSpeed * yacc;
//							
//							vars.redDirection += DIRECTION_INCREMENTER * Math.sqrt(vars.redSpeedModifier);
//							//sqrt arbitrary
//						}
//					}
//					break;
//			}
//			if (vars.redDirection > 360) vars.redDirection = 0;
//			if (vars.redDirection < 0)	vars.redDirection = 360;
//		}
//		if (t.equals("green"))
//		{
//			double gcos = Math.cos(Math.toRadians(vars.greenDirection)) * speed * vars.greenSpeedModifier;
//			double gsin = Math.sin(Math.toRadians(vars.greenDirection)) * speed * vars.greenSpeedModifier;
//			temp = new myShape(vars.gul, vars.gur, vars.gdr, vars.gdl);
//			c = findCenter(temp);
//			double tempAngle = vars.greenDirection/180 + DIRECTION_INCREMENTER + angleBuffer;
//			
//			switch(d)
//			{
//				case "up":
//					for (int i=0;i<4;i++) {
//						temp.getVector2D(i).setX(temp.getVector2D(i).getX() + gcos * buffer);
//						temp.getVector2D(i).setY(temp.getVector2D(i).getY() + gsin * buffer);
//					};
//					
//					if (!shapeCollision(vars.redTankShape, temp) && collidesWithWalls(temp) == null)
//					{
//						vars.greenTankX += gcos;
//						vars.greenTankY += gsin;
//					}
//					break; 
//				case "down":
//					for (int i=0;i<4;i++) {
//						temp.getVector2D(i).setX(temp.getVector2D(i).getX() - gcos * buffer);
//						temp.getVector2D(i).setY(temp.getVector2D(i).getY() - gsin * buffer);
//					};
//					
//					if (!shapeCollision(vars.redTankShape, temp) && collidesWithWalls(temp) == null)
//					{
//						vars.greenTankX -= gcos;
//						vars.greenTankY -= gsin;
//					}
//					break;
//				case "left":
//					for (int i=0;i<4;i++)
//					{
//						temp.getVector2D(i).setPoint(rotatePoint(temp.getVector2D(i), c, Math.toRadians(tempAngle)));
//					}
//					if (!shapeCollision(temp, vars.redTankShape) && collidesWithWalls(temp) == null)
//					{
//						vars.greenDirection -= DIRECTION_INCREMENTER * Math.sqrt(vars.greenSpeedModifier);
//					}
//					break;
//				case "right":
//					for (int i=0;i<4;i++)
//					{
//						temp.getVector2D(i).setPoint(rotatePoint(temp.getVector2D(i), c, Math.toRadians(tempAngle)));
//					}
//					if (!shapeCollision(temp, vars.redTankShape) && collidesWithWalls(temp) == null)
//					{
//						vars.greenDirection += DIRECTION_INCREMENTER * Math.sqrt(vars.greenSpeedModifier);
//					}
//			}
//			if (vars.greenDirection > 360) vars.greenDirection = 0;
//			if (vars.greenDirection < 0) vars.greenDirection = 360;
//		}
//	}
//	
//}
