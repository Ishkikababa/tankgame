package TankGame; // 3/14/17 this one's gonna be big

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tank implements GlobalConst
{
	private double x, y, w, h, direction;
	private double rcos, rsin;
	private myShape mShape, tempShape;
	private double moveBuffer = 1.2; // a buffer (multiplier) for the "hitbox" around the tank
	
	private String color; // this tank's color
	private Color drawColor;
	
	private Vector2D ul, ur, dr, dl, center; // corner coordinates
	private ArrayList<Vector2D> points = new ArrayList<Vector2D>();
	
	private int bulletsLeft;
	private boolean hasFired; // used so the player can't hold down the shoot key
	
	private BufferedImage myImage, myDeadImage; // images to display
	
	// stores whether the player has a powerup
	private ArrayList<TankPower> powers = new ArrayList<TankPower>();
	private double speedModifier = 1, angleModifier = 1;
	private boolean massacre = false, invuln = false, ghost = false, bomb = false, deathrattle = false;
	
	private boolean isDead;
	private int score; // my score
	
	public Tank(double nx, double ny, double dir, String c)
	{
		x = nx;
		y = ny;
		direction = dir;
		color = c;
		bulletsLeft = MAX_BULLETS;
		hasFired = false;
		isDead = false;
		score = 0;
		
		// set color
		switch(c)
		{
			case "RED":
				myImage = vars.redTankImage;
				myDeadImage = vars.redTankDeadImage;
				drawColor = Color.RED;
				break;
			case "GREEN":
				myImage = vars.greenTankImage;
				myDeadImage = vars.greenTankDeadImage;
				Color myGreen = new Color(64, 186, 14);
				drawColor = myGreen;
				break;
			case "ORANGE":
				myImage = vars.orangeTankImage;
				myDeadImage = vars.orangeTankDeadImage;
				Color myOrange = new Color(241, 109, 14);
				drawColor = myOrange;
				break;
			case "BLUE":
				myImage = vars.blueTankImage;
				myDeadImage = vars.blueTankDeadImage;
				drawColor = Color.BLUE;
				break;
			default: 
				myImage = vars.redTankImage;
				myDeadImage = vars.redTankDeadImage;
				drawColor = Color.BLACK;
				break;
		}
		
		w = myImage.getWidth();
		h = myImage.getHeight();
		center = new Vector2D(x+w/2, y+h/2);
		
		updateShape(); // updates the hitbox
	}
	
	// basic getters
	public double x() {return x;}
	public double y() {return y;}
	public double w() {return w;}
	public double h() {return h;}
	public double direction() {return direction;}
	public Vector2D getCenter() {return center;}
	
	public int getNumBulletsLeft() {return bulletsLeft;}
	public boolean hasFired() {return hasFired;}
	
	public ArrayList<TankPower> getPowers() {return powers;}
	public boolean isInvuln() {return invuln;}
	
	public String color() {return color;}
	public Color drawColor() {return drawColor;}
	
	public myShape shape() {return mShape;}
	public Vector2D UL() {return ul;}
	public Vector2D UR() {return ur;}
	public Vector2D DL() {return dl;}
	public Vector2D DR() {return dr;}
	
	public boolean isDead() {return isDead;}
	
	public int getScore() {return score;}
	
	// basic setters
	public void setX(double nx) {x = nx; updateShape();}
	public void setY(double ny) {y = ny; updateShape();}
	public void setDirection (double d) {direction = d; updateShape();}
	public void setBulletsLeft(int n) {bulletsLeft = n;}
	public void setHasFired(boolean f) {hasFired = f;}
	public void addPower(TankPower t) {powers.add(t);}
	public void setDead(boolean d) {isDead = d;}
	public void addPoint(int n) {score += n;}
	public void setScore(int n) {score = n;}
	
	// advanced-er methods
	
	// the image to display
	public BufferedImage image()
	{
		if (isDead)
			return myDeadImage;
		
		return myImage;
	}
	
	// creates basic, unrotated hitbox
	public void setShape()
	{
		points.clear();
		ur = new Vector2D(x + w, y);
		ul = new Vector2D(x, y);
		dr = new Vector2D(x + w, y + h);
		dl = new Vector2D(x, y + h);
		points.add(ul);
		points.add(ur);
		points.add(dr);
		points.add(dl);
		mShape = new myShape(ul, ur, dr, dl);
	}
	
	// updates hitbox
	public void updateShape()
	{
		setShape();
		center = new Vector2D(x+w/2, y+h/2);
		
		//rotate to angle
		for (Vector2D p : points)
		{
			double tempX, tempY, newX, newY;
			
			tempX = p.getX() - center.getX();
			tempY = p.getY() - center.getY();

			newX = tempX*Math.cos(Math.toRadians(direction)) - tempY*Math.sin(Math.toRadians(direction));
			newY = tempX*Math.sin(Math.toRadians(direction)) + tempY*Math.cos(Math.toRadians(direction));

			p.setX(newX + center.getX());
			p.setY(newY + center.getY());
		}
		
		mShape = new myShape(ul, ur, dr, dl);
		
		// keeps directions between 0 and 360
		if (direction > 360)
			direction = 0;
		if (direction < 0)
			direction = 360;
		
		// how much to move the tank by (if needed)
		rcos = Math.cos(Math.toRadians(direction)) * TANK_SPEED * speedModifier;
		rsin = Math.sin(Math.toRadians(direction)) * TANK_SPEED * speedModifier;
	}
	
	// called by main class to begin movement
	// this is a separate method to prevent infinite recursion when adjusting position
	public void doMovement(String doing)
	{
		updateShape();
		if (doing.equals("FORWARD"))
			moveForward();
		else if (doing.equals("BACKWARD"))
			moveBackward();
		else if (doing.equals("LEFT"))
		{
			rotateLeft();
//			rotateLeft();
		}
		else if (doing.equals("RIGHT"))
		{
			rotateRight();
//			rotateRight();
		}

		if (outOfBounds())
		{
			x = vars.fieldWidth/2 + w/2;
			y = vars.fieldHeight/2 + h/2;
		}
	}
	
	private void moveForward()
	{
		updateShape(); // update the hitbox first
		tempShape = new myShape(mShape);
		moveTempFB(1); // moves the hitbox before the actual tank
		
		if (collidesWithWalls(tempShape) == null)
		{
			x += rcos;
			y += rsin;
		}
		else
		{
			adjustMovement(); // adjusts movement to slide along walls
		}
	}
	
	private void moveBackward()
	{
		updateShape();
		tempShape = new myShape(mShape);
		moveTempFB(-1);
		
		if (collidesWithWalls(tempShape) == null)
		{
			x -= rcos;
			y -= rsin;
		}
		else
		{
			adjustMovement();
		}
	}
	
	private void rotateRight()
	{
		updateShape();
		tempShape = new myShape(mShape);
		rotateShape((DIRECTION_INCREMENTER * angleModifier)/2 + speedModifier); // rotate hitbox before actual tank
		
		if (collidesWithWalls(tempShape) == null)
			direction+=(DIRECTION_INCREMENTER * angleModifier);
		else
			adjustRotation();
	}
	
	private void rotateLeft()
	{
		updateShape();
		tempShape = new myShape(mShape);
		rotateShape(-(DIRECTION_INCREMENTER * angleModifier)/2);
		
		if (collidesWithWalls(tempShape) == null)
			direction-=(DIRECTION_INCREMENTER * angleModifier);
		else
			adjustRotation();
	}
	
	private void moveTempFB(int d) // FB = Forward/Backward
	{
		// update corners of hitbox
		for (int i=0;i<4;i++) {
			tempShape.getVector2D(i).setX(tempShape.getVector2D(i).getX() + d*rcos*moveBuffer);
			tempShape.getVector2D(i).setY(tempShape.getVector2D(i).getY() + d*rsin*moveBuffer);
		};
	}	
	
	// adjusts movement so that the player can slide along walls
	private void adjustMovement()
	{
		Wall tempWall;
		do
		{
			tempWall = collidesWithWalls(tempShape);
			if (tempWall == null)
				break;
			if (tempWall.getOrientation().equals("V") || tempWall.getOrientation().equals("C"))
			{
				if (direction < 90 || (180 < direction && direction < 270))
				{
					rotateRight();
					rotateRight(); // does this twice so that the player doesn't get stuck if the player tries turning into a wall while sliding
				}
				else
				{
					rotateLeft();
					rotateLeft();
				}
			}
			else if (tempWall.getOrientation().equals("H") || tempWall.getOrientation().equals("C"))
			{
				if (direction < 90 || (180 < direction && direction < 270))
				{
					rotateLeft();
					rotateLeft();
				}
				else
				{
					rotateRight();
					rotateRight();
				}
			}
		} while (tempWall != null);
	}
	
	private void adjustRotation()
	{
		int xMod = 0, yMod = 0;
		Wall tempWall;
		do
		{
			tempWall = collidesWithWalls(tempShape);
			if (tempWall == null)
				break;
			if (tempWall.getOrientation().equals("V") || tempWall.getOrientation().equals("C"))
			{
				if (tempWall.getX() <= center.getX())
				{
					tempShape.translateX(1);
					xMod++;
				}
				if (tempWall.getX() > center.getX())
				{
					tempShape.translateX(-1);
					xMod--;
				}
			}
			if (tempWall.getOrientation().equals("H") || tempWall.getOrientation().equals("C"))
			{
				if (tempWall.getY() <= center.getY())
				{
					tempShape.translateY(1);
					yMod++;
				}
				if (tempWall.getY() > center.getY())
				{
					tempShape.translateY(-1);
					yMod--;
				}
			}
		} while(tempWall != null);
		
		x += xMod;
		y += yMod;
	}
	
	public boolean outOfBounds()
	{
		if (x < 0 || x + w - 16> vars.fieldWidth || // 16 adjusts for weird JFrame framing
			 y < 0 || y + h > vars.fieldHeight)
			return true;
		
		return false;
	}
	
	// rotates the hitbox
	private void rotateShape(double d)
	{
		d = Math.toRadians(d);
		for (int i=0;i<4;i++) {
			Vector2D p = tempShape.getVector2D(i);
			
			double tempX, tempY, newX, newY;
			
			tempX = p.getX() - center.getX();
			tempY = p.getY() - center.getY();

			newX = tempX*Math.cos(d) - tempY*Math.sin(d);
			newY = tempX*Math.sin(d) + tempY*Math.cos(d);

			p.setX(newX + center.getX());
			p.setY(newY + center.getY());
		}
	}

	// shoots bullets (and powerup bullets) from the muzzle tip
	public boolean shoot()
	{
		double extra = 1.5;
		if ((bulletsLeft > 0 || massacre) && !hasFired)
		{
			if (ghost)
				bullets.add(new GhostBullet(center.getX() + Math.cos(Math.toRadians(direction)) * (w/2) * extra,
							  center.getY() + Math.sin(Math.toRadians(direction)) * (w/2) * extra, 
							  direction, color));
			else if (bomb)
			{
				bullets.add(new BombBullet(center.getX() + Math.cos(Math.toRadians(direction)) * (w/2) * extra,
							  center.getY() + Math.sin(Math.toRadians(direction)) * (w/2) * extra, 
							  direction, color));
			}
			else
				bullets.add(new Bullet(center.getX() + Math.cos(Math.toRadians(direction)) * (w/2) * extra,
							  center.getY() + Math.sin(Math.toRadians(direction)) * (w/2) * extra, 
							  direction, color));
			if (!massacre)
				bulletsLeft--;
			
			hasFired = true;
			return true;
		}
		return false;
	}
	
	// returns true if the player is allowed to shoot
	public boolean canShoot()
	{
		return (bulletsLeft > 0 || massacre) && !hasFired;
	}
	
	// updates the number of bullets remaining
	public void changeBulletsLeft(int n) 
	{
		bulletsLeft += n; 
		if (bulletsLeft > MAX_BULLETS)
			bulletsLeft = MAX_BULLETS;
		if (bulletsLeft < 0)
			bulletsLeft = 0;
	}
	
	public void clearPowerups()
	{
		powers.clear();
		speedModifier = 1;
		invuln = massacre = false;
	}
	
	public void updatePowerups()
	{
		try
		{
			for (TankPower t : powers)
				if (t.getTimeRemaining() > 0)
				{
					t.tickDown(1);
					if (t.getTimeRemaining() < 1)
					{
						powers.remove(t);
					}
				} 
				else
					powers.remove(t);
			
			//TODO remove similar powerups with shorter time (in the event of duplicate powerps
			for (int i=powers.size()-1;i>=0;i--)
			{
				TankPower max = powers.get(i);
				for (int j=i;j>=0;j--)
				{
					if (powers.get(j).getType().equals(max.getType()))
					{
						if (powers.get(j).getTimeRemaining() >= max.getTimeRemaining())
						{
							powerups.remove(max);
							max = powers.get(j);
						}
						else
						{
							powers.remove(j);
						}
					}
				}
			}
			
			if (hasPower("speed"))
			{
				speedModifier = SPEED_MOD;
				angleModifier = 1.2;
			}
			else
			{
				speedModifier = 1;
				angleModifier = 1;
			}
			
			if (hasPower("invulnerable"))
				invuln = true;
			else
				invuln = false;
			
			if (hasPower("massacre"))
				massacre = true;
			else
				massacre = false;
			
			if (hasPower("ghost"))
				ghost = true;
			else
				ghost = false;			
			
			if (hasPower("bomb"))
				bomb = true;
			else
				bomb = false;
			
			if (hasPower("deathrattle"))
				deathrattle = true;
			else
				deathrattle = false;
			
		}
		catch (Exception e){}
	}
	
	public boolean hasPower(String power) 
	{
		for (TankPower tp : powers)
			if (tp.getType().equals(power))
				return true;
		
		return false;
	}

	public Wall collidesWithWalls(myShape shape)
	{
		for (int i=0;i<walls.size();i++)
			if (walls.get(i).getShape().collidesWith(tempShape))
				return walls.get(i);

		return null;
	}
}