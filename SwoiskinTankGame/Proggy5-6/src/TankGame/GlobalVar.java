package TankGame;

import java.awt.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import com.sun.javafx.geom.Vec2d;


// This class, with a default (empty) constructor, sets up global variables
//  (as opposed to global constants, which are defined in a different file)
//  that can be accessed (and modified) from multiple files; the variables
//  are accessed through an instance of this class, which is created in the
//  'GlobalConst' interface 

// Allowing direct access to variables in this class may not be the best
//  programming practice; some will argue (perhaps correctly) that the variables
//  should all be made private and then accessed through public accessor and
//  modifier methods (to maintain the programming concept of information hiding)

// Regardless, programmatically this approach is almost certainly better than
//  defining the variables in the 'Game' class and making them all static,
//  since doing so would associate the variables with the classes themselves,
//  not with instances of the classes (which can cause unintended side effects)
public class GlobalVar
{
//	double redTankX, redTankY, greenTankX, greenTankY;
//	double redDirection = 0, greenDirection = 180;
//	double redBulletsLeft = 5, greenBulletsLeft = 5;

	boolean startScreenActive = true, gameOn = false;
	ArrayList<String> tanksToAdd = new ArrayList<String>(); // used in start screen
	int startTimer = 300;
	
	Tank redTank, greenTank, blueTank, orangeTank;
	ArrayList<Tank> tanks = new ArrayList<Tank>();
	
	int fieldWidth = 400;
	int fieldHeight = 400;
	
	int scoreBoardWidth = 400;
	int scoreBoardHeight = 300;
	
	String winner = "";
	int deathTime = 0, deathStart = 300, continueTime = 0, continueStart = 300; // timer variables. "___Time" set to "___Start"
	boolean winTextOn = false;
	int powerupCooldown = 0, powerupSpawnRate = 500; //cooldown is set to spawnrate
	boolean powerupsOn = true;
	
	//u, l, d, r, shoot commands
	boolean pressedLeft = false, pressedRight = false, pressedUp = false, pressedDown = false, pressedZero = false,//green
						 pressedA = false, pressedD = false, pressedW = false, pressedS = false, pressedSpace = false,//red
						 pressedI = false, pressedJ = false, pressedK = false, pressedL = false, pressedSemi = false, //orange
						 pressed4 = false, pressed2 = false, pressed5 = false, pressed8 = false, pressedEnter = false, // blue
						 //shoot
				       paused = false, pressedControlKey = false, pressedBackspace = false;
	
	// strings to make playing sounds easier (stores file names which are long, see main class)
	boolean soundEnabled = true, memesEnabled = false;
	String pointSoundFile, bounceSoundFile, tankSelectSoundFile, tickingSoundFile, 
	 		 pauseSoundFile, shootSoundFile, startSound, deathSound, powerupSpawnSoundFile, powerupPickupSoundFile,
	 		 memeSoundFile;
	ArrayList<Clip> allSounds = new ArrayList<Clip>();
	
	// temporary variables for testing
	myShape badWall = null;
	myShape tankTemp = null;
	Vector2D tempCenter = null;
	
	boolean gameOver = false;

	// The Images below are, technically, constants, since their values are not
	//  changed (new graphics are not loaded from disk) once they have been
	//  initialized, but they are defined in this class (as opposed to the
	//  interface that contains global constants) because the 'getClass' method
	//  below cannot be used in an interface; while it is true that the Images
	//  could be loaded without the use of the 'getClass' (and 'getResource')
	//  methods, using those two methods allows all of the files that make up this
	//  program (the .CLASS files and the graphics files) to be put into a single
	//  .JAR file and then loaded and run directly from that (executable) file;
	//  also, a benefit of using the 'ImageIcon' class is that, unlike some of the
	//  other file-loading classes, the 'ImageIcon' class fully loads the Image
	//  when the object is created, making it possible to immediately determine
	//  and use the dimensions of the Image
	
	// old background
	//Image backgroundImage = new ImageIcon(getClass().getResource("space.gif")).getImage();
	
	// all of the images used
	Image backgroundImage = new ImageIcon(getClass().getResource("pvzfield.jpg")).getImage();
	
	Image shooterImage = new ImageIcon(getClass().getResource("shooter.png")).getImage();
	Image alienImage = new ImageIcon(getClass().getResource("alien.png")).getImage();
	Image missileImage = new ImageIcon(getClass().getResource("missile.png")).getImage();
	Image bulletImage = new ImageIcon(getClass().getResource("bullet.png")).getImage();
	Image ghostBulletImage = new ImageIcon(getClass().getResource("ghostBullet.fw.png")).getImage();
	Image bombBulletImage = new ImageIcon(getClass().getResource("bombBullet.fw.png")).getImage();
	Image powerupImage = new ImageIcon(getClass().getResource("powerup.fw.png")).getImage();
	
	Image redtemp = new ImageIcon(getClass().getResource("redTank.fw.png")).getImage();
	Image greenTemp = new ImageIcon(getClass().getResource("greenTank.fw.png")).getImage();
	Image orangeTemp = new ImageIcon(getClass().getResource("orangeTank.fw.png")).getImage();
	Image blueTemp = new ImageIcon(getClass().getResource("blueTank.fw.png")).getImage();
	
	Image redDeadtemp = new ImageIcon(getClass().getResource("redTankDead.fw.png")).getImage();
	Image greenDeadtemp = new ImageIcon(getClass().getResource("greenTankDead.fw.png")).getImage();
	Image orangeDeadtemp = new ImageIcon(getClass().getResource("orangeTankDead.fw.png")).getImage();
	Image blueDeadtemp = new ImageIcon(getClass().getResource("blueTankDead.fw.png")).getImage();
	
	BufferedImage redTankImage = toBufferedImage(redtemp);
	BufferedImage greenTankImage = toBufferedImage(greenTemp);
	BufferedImage orangeTankImage = toBufferedImage(orangeTemp);
	BufferedImage blueTankImage = toBufferedImage(blueTemp);
	
	BufferedImage redTankDeadImage = toBufferedImage(redDeadtemp);
	BufferedImage greenTankDeadImage = toBufferedImage(greenDeadtemp);
	BufferedImage orangeTankDeadImage = toBufferedImage(orangeDeadtemp);
	BufferedImage blueTankDeadImage = toBufferedImage(blueDeadtemp);

	// converts an Image to a BufferedImage (not sure if this is needed but whatever)
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }
	    
	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
}