package TankGame;

import java.util.ArrayList;

// This interface provides global content to the entire game; note that
//  the keyword 'final' used below is, technically, not necessary, since
//  all "variables" in an interface are actually static constants (because
//  interfaces cannot be instantiated, so no instances of the "variables"
//  exist to be re-assigned by program code)

// Note, also, that this file is defined as an interface (as opposed to an
//  abstract class) so that the other classes in this program can implement
//  it (as opposed to extending it); this frees up those classes to extend
//  other classes

// Finally, note that even though some of the constants below are used in
//  only one file, having all of the game constants in one place (here)
//  makes them more accessible to anyone viewing or editing the game code

public interface GlobalConst
{
	final int NODE_LENGTH = 100;
	
	final int TIMER_SPEED = 10;
	final int TIMER_DELAY = 0;//750;
	
	final int MAX_BULLETS = 5;
	final int NUM_BULLET_BOUNCES = 4;
	final int BULLET_SPEED = 3;
	final int TANK_SPEED = 1 ;
	final double DIRECTION_INCREMENTER = .75 *2;
	
	final int WALL_UNIT_WIDTH = 10;
	final int WALL_LENGTH = 4;
	
	final double SPEED_MOD = 1.75;

	// global lists of objects
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	ArrayList<Wall> walls = new ArrayList<Wall>();
	
	ArrayList<Explosion> explosions = new ArrayList<Explosion>();
	ArrayList<Powerup> powerups = new ArrayList<Powerup>();
	
	// Allow global variables (as opposed to global constants) from the
	//  'GlobalVar' class to be accessed and modified from multiple files 
	GlobalVar vars = new GlobalVar();
}
