package TankGame;

import java.awt.*;
import java.awt.event.*;
import java.io.Console;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.concurrent.TimeUnit;

/*
to-do list:
	X make score board for any number of tanks
	X draw orange and blue tanks
	X draw death images
	X fix background color for field/scoreboard (USE CUSTOM RGB COLORS)
	X make start screen
		play before match starts
		determines which tanks are in the game (>=2)
	X Fix bullet reset on new game
	X Add continue button
	X Add timer after death and continue click
	X Add ghost bullets with ghost powerup
	X Fix death images order/orientation
	
	X Fix bullets going through walls
	X Fix wall edges being too short
	
	X Add sound
	X Fix invuln // spell invulnerablility right you dumbass
	X Change ghost bullet color
	
	X add bombBullet powerup
	X add deathrattle powerup
	
	X add help page
	
	???
	profit
*/

public class Game extends KeyAdapter implements GlobalConst, ActionListener
{
	private JFrame gameFrame;
	private GamePanel battlePanel;
	private Timer timer;
	private JMenuBar menuBar;
	private JMenu file, options, sound, powerupsmenu, help;
	private JMenuItem newGame, about, exit, rules, powerupslist;
	private JCheckBoxMenuItem soundEnabled, memesEnabled, powerupsEnabled;
	private JButton startButton, continueButton;
	
	private Clip tickingSound;

	public static void main(String[] args) throws LineUnavailableException, UnsupportedAudioFileException, IOException
	{
		new Game();
	}

	// Constructor
	public Game() throws LineUnavailableException, UnsupportedAudioFileException, IOException
	{
		gameFrame = new JFrame();
		battlePanel = new GamePanel();
		battlePanel.setLayout(null);
		gameFrame.getContentPane().add(battlePanel);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setSize(vars.fieldWidth + 16, vars.fieldHeight + 16 + 100); // 16 for weird jframe buffer
		gameFrame.setTitle("Definitely Not Tank Trouble");
		gameFrame.setLocationRelativeTo(null);
		//gameFrame.setResizable(false);
		gameFrame.setFocusable(true);
		
		menuBar = new JMenuBar();
		menuBar.setVisible(true);
		
		file = new JMenu("File");
		
		newGame = new JMenuItem("New Game");
		newGame.setActionCommand("New Game");
		newGame.addActionListener(this);
		newGame.setVisible(true);
				
		about = new JMenuItem("About");
		about.setActionCommand("About");
		about.addActionListener(this);
		about.setVisible(true);
		
		exit = new JMenuItem("Exit");
		exit.setActionCommand("Exit");
		exit.addActionListener(this);
		exit.setVisible(true);

		file.add(newGame);
		file.add(about);
		file.addSeparator();
		file.add(exit);
		
		menuBar.add(file);
		
		options = new JMenu("Options");
		
		sound = new JMenu("Sound");
		sound.addActionListener(this);
		sound.setVisible(true);
		
		soundEnabled = new JCheckBoxMenuItem("Enabled");
		soundEnabled.addItemListener(new ItemListener() { // thanks stackoverflow
		    @Override
		    public void itemStateChanged(ItemEvent e) {
		        if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
		           vars.soundEnabled = true;
		        } else {
		      	  vars.soundEnabled = false;
		        };
		    }
		});
		soundEnabled.addActionListener(this);
		soundEnabled.setActionCommand("SoundEnabled");
		soundEnabled.setVisible(true);
		soundEnabled.setSelected(true);
		
		memesEnabled = new JCheckBoxMenuItem("Memes?");
		memesEnabled.addItemListener(new ItemListener() {
		    @Override
		    public void itemStateChanged(ItemEvent e) {
		        if(e.getStateChange() == ItemEvent.SELECTED) {
		           vars.memesEnabled = true;
		        } else {
		      	  vars.memesEnabled = false;
		        };
		    }
		});
		memesEnabled.addActionListener(this);
		memesEnabled.setActionCommand("MemesEnabled");
		memesEnabled.setVisible(true);
		memesEnabled.setSelected(false);
		
		sound.add(soundEnabled);
		sound.add(memesEnabled);
		
		powerupsmenu = new JMenu("Powerups");
		powerupsmenu.addActionListener(this);
		powerupsmenu.setVisible(true);
		
		powerupsEnabled = new JCheckBoxMenuItem("Enabled");
		powerupsEnabled.addItemListener(new ItemListener() {
		    @Override
		    public void itemStateChanged(ItemEvent e) {
		        if(e.getStateChange() == ItemEvent.SELECTED) {
		           vars.powerupsOn = true;
		        } else {
		      	  vars.powerupsOn = false;
		      	  powerups.clear();
		        };
		    }
		});
		powerupsEnabled.addActionListener(this);
		powerupsEnabled.setActionCommand("powerupsEnabled");
		powerupsEnabled.setVisible(true);
		powerupsEnabled.setSelected(true);
				
		powerupsmenu.add(powerupsEnabled);
		
		options.add(sound);
		options.add(powerupsmenu);
		
		menuBar.add(options);
		
		help = new JMenu("Help");

		rules = new JMenuItem("Rules");
		rules.setActionCommand("Rules");
		rules.addActionListener(this);
		rules.setVisible(true);
		
		powerupslist = new JMenuItem("List of Powerups");
		powerupslist.setActionCommand("powerupslist");
		powerupslist.addActionListener(this);
		powerupslist.setVisible(true);
		
		help.add(rules);
		help.add(powerupslist);
		
		menuBar.add(help);
		
		gameFrame.setJMenuBar(menuBar);
		
		startButton = new JButton("START (BACKSPACE)");
		startButton.setSize(240, 40);
		startButton.setLocation(80, 416);
		startButton.setBackground(Color.DARK_GRAY);
		startButton.setForeground(Color.WHITE);
		startButton.setActionCommand("Start");
		startButton.setFocusable(false);
		startButton.addActionListener(this);
		startButton.setVisible(false);
		
		continueButton = new JButton("PRESS ANY KEY TO CONTINUE");
		continueButton.setSize(240, 40);
		continueButton.setBackground(Color.DARK_GRAY);
		continueButton.setForeground(Color.WHITE);
		continueButton.setActionCommand("Continue");
		continueButton.setFocusable(false);
		continueButton.addActionListener(this);
		continueButton.setVisible(false);
		
		battlePanel.add(continueButton);
		battlePanel.add(startButton);
		
		gameFrame.addKeyListener(this);
		gameFrame.setVisible(true);
		
		vars.pointSoundFile = "A-Tone-His_Self-1266414414.wav";
		vars.tickingSoundFile = "Tick Tock-SoundBible.com-1165545065.wav";
		vars.tankSelectSoundFile = "Checkout Scanner Beep-SoundBible.com-593325210.wav";
		vars.bounceSoundFile = "Click2-Sebastian-759472264.wav";
		vars.pauseSoundFile = "Pling-KevanGC-1485374730.wav";
		vars.shootSoundFile = "Pop Banner-SoundBible.com-641783855.wav";
		vars.startSound = "Bell Sound Ring-SoundBible.com-181681426.wav";
		vars.deathSound = "Mirror Shattering-SoundBible.com-1752328245.wav"; //TODO find better death sound
		vars.powerupSpawnSoundFile = "Woosh-Mark_DiAngelo-4778593.wav";
		vars.powerupPickupSoundFile = "Robot_blip-Marianne_Gagnon-120342607.wav";
		
		vars.memeSoundFile = "PINGAS-Richard-89282878.wav";
		
		AudioInputStream clip;
		clip = AudioSystem.getAudioInputStream(getClass().getResource(vars.tickingSoundFile));
		tickingSound = AudioSystem.getClip();
		tickingSound.open(clip);
		
		// Set (and start) a new Swing Timer to fire every 'TIMER_SPEED' milliseconds,
		//  after an initial delay of 'TIMER_DELAY' milliseconds; this Timer, along
		//  with the distance (number of pixels) that the aliens, missiles, and shooter
		//  move with each cycle, controls how fast the objects move on the playing
		//  field; note that if adding a "pause/unpause" feature to this game, the
		//  value of the 'TIMER_DELAY' constant should probably be set to zero
		timer = new Timer(TIMER_SPEED, this);
		timer.setInitialDelay(TIMER_DELAY);
		timer.start();
	}
	
	public void start()
	{
		makeMaze();
		setUpTanks();
		resetNumbers();
		resetPowerups();
	}
	
	public void reset()
	{
		makeMaze();
		resetTanks();
		resetNumbers();
		resetPowerups();
	}
	
	public void resetTanks()
	{
		int w = vars.fieldWidth/NODE_LENGTH;
		int h = vars.fieldHeight/NODE_LENGTH;
		
		// make a list of all possible spots and randomly assign to tanks so that they don't stack
		ArrayList<Vector2D> spots = new ArrayList<Vector2D>();
		
		for (int i=0;i<w-1;i++)
			for (int j=0;j<h-1;j++)
				spots.add(new Vector2D(i * NODE_LENGTH, j * NODE_LENGTH));
		
		Collections.shuffle(spots);
		
		int c = 0;
		for (Tank t : vars.tanks)
		{
			Vector2D spot = spots.get(c);
			c++;
			t.setX(spot.getX() + NODE_LENGTH/2 - t.image().getWidth()/2);
			t.setY(spot.getY() + NODE_LENGTH/2 - t.image().getHeight()/2);
			t.setDirection(rand(0, 360));
			
			t.setHasFired(false);
			t.setDead(false);
		}
	}
	
	public void resetPowerups()
	{
		powerups.clear();
		for (Tank t : vars.tanks)
			t.clearPowerups();
		vars.powerupCooldown = vars.powerupSpawnRate;
	}
	
	public void resetNumbers()
	{
		bullets.clear();
		
		for (Tank t : vars.tanks)
			t.setBulletsLeft(MAX_BULLETS);
		vars.winTextOn = false;
		vars.deathTime = -1;
		vars.continueTime = -1;
		vars.winner = "";
		tickingSound.stop();
	}
	
	public void setUpTanks()
	{
		vars.tanks.clear();
	
		for (String s : vars.tanksToAdd)
		{
			if (s.equals("RED"))
			{
				vars.redTank = new Tank(0, 0, 0, "RED");
				vars.tanks.add(vars.redTank);
			}
			
			if (s.equals("GREEN"))
			{
				vars.greenTank = new Tank(0, 0, 0, "GREEN");
				vars.tanks.add(vars.greenTank);
			}
			
			if (s.equals("ORANGE"))
			{
				vars.orangeTank = new Tank(0, 0, 0, "ORANGE");
				vars.tanks.add(vars.orangeTank);		
			}
			
			if (s.equals("BLUE"))
			{
				vars.blueTank = new Tank(0, 0, 0, "BLUE");
				vars.tanks.add(vars.blueTank);
			}
		}
			
		resetTanks();
	}

	public void actionPerformed(ActionEvent e)
	{
		battlePanel.repaint();
		
		if (e.getActionCommand() != null)
			switch(e.getActionCommand())
			{
				case "New Game":
					continueButton.doClick();
					start();
					break;
				case "Exit":
					gameFrame.dispose();
					System.exit(0);
					break;
				case "About":
					String aboutMessage = "Made by Aaron Swoiskin, Programming 5-6 2016-17.\n"
										+ "Based off of Dave Goldsmith's Space Shooter game.\n"
										+ "I do not apologize for bad code. Best of luck to future editors.\n"
										+ "For feedback, email aswoiskin@gmail.com";
					aboutMessage = "Made in Programming 3-6\nAnonymous test version";
					JOptionPane.showMessageDialog(null, aboutMessage, "About", JOptionPane.INFORMATION_MESSAGE);
					break;
				case "Rules":
					String rulesMessage = "Control tanks with the controls displayed on the start page.\n"
								+ "Press the tank's shoot key in the start page to lock in your choice.\n\n"
								+ "Each player has 5 bullets that each get 5 bounces.\n"
								+ "Bullets are refunded to the player when the bullet dies or at the beginning of each round.\n\n"
								+ "After all or all but 1 player dies, there are 3 seconds until points are awarded.\n"
								+ "There is no point limit.\n\n"
								+ "Powerups spawn periodically and can be collected by touching them.\n"
								+ "Active powerups and their remaining time are displayed for each player at the bottom of the screen.";
					JOptionPane.showMessageDialog(null, rulesMessage, "About", JOptionPane.INFORMATION_MESSAGE);
					break;
				case "powerupslist":
					String powerupslistMessage = 
								  "Speed.................................................................................................Move faster\n"
								+ "Massacre.................................................................................Unlimited bullets\n"
								+ "Invulnerablilty........................................................................You are unkillable\n"
								+ "Ghost...............................................................Shoot bullets that ignore walls\n"
								+ "Deathrattle........ Dying causes a ring of shrapnel to expand around you\n"
								+ "Bomb.......................................................................Shoot bullets that explode\n";
					JOptionPane.showMessageDialog(null, powerupslistMessage, "About", JOptionPane.INFORMATION_MESSAGE);
					break;
				case "Start":
					vars.startScreenActive = false;
					break;
				case "Continue":
					vars.winTextOn = false;
					continueButton.setVisible(false);
					vars.continueTime = vars.continueStart;
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					vars.fieldWidth = 400;
					vars.fieldHeight = 400;
					gameFrame.setSize(vars.fieldWidth + 16, vars.fieldHeight);
					if (vars.tanksToAdd.size() < 3)
						gameFrame.setSize(vars.fieldWidth + 16, vars.fieldHeight + vars.scoreBoardHeight/2);
					gameFrame.setLocation((int)screenSize.getWidth()/2 - vars.fieldWidth/2,(int) screenSize.getHeight()/2 - (vars.fieldHeight + vars.scoreBoardHeight)/2);
					break;
				case "SoundEnabled":
					vars.soundEnabled = soundEnabled.isSelected();
					break;
			}
		
		if (!vars.gameOn)
			newGame.setEnabled(false);
		else
			newGame.setEnabled(true);
		
		if (vars.tanksToAdd.size() >= 2 && vars.startScreenActive)
			startButton.setVisible(true);
		else
			startButton.setVisible(false);
		
		if (vars.continueTime > 0)
		{
			vars.continueTime--;
			if (vars.continueTime < 1)
			{
				reset();
				playSound(vars.startSound);
			}
			else
				return;
		}
		
		if (vars.paused || vars.startScreenActive || vars.winTextOn)
			return;
		
		if (!vars.gameOn)
		{
			if (vars.startTimer > 0)
			{
				vars.startTimer--;
				return;
			}
			else
			{
				start();
				vars.gameOn = true;
				playSound(vars.startSound);
			}
		}
		
		if (vars.deathTime > 0)
		{
			vars.deathTime--;
			if (vars.deathTime < 1)
				setWinOn();
		}
		
		checkTankMovement();
		checkShooting();
		moveBullets();
			
		checkPowerups();
		checkCollisions();
		checkWinners();
	}

	// Checks which tanks exist and if so, have tried to shoot
	public void checkShooting()
	{
		if (vars.redTank != null && !vars.redTank.isDead() && vars.pressedSpace)
		{
			if (vars.redTank.shoot())
				playSound(vars.shootSoundFile);
		}
		
		if (vars.greenTank != null && !vars.greenTank.isDead() && vars.pressedZero)
		{
			if (vars.greenTank.shoot())
				playSound(vars.shootSoundFile);
		}
		
		if (vars.orangeTank != null && !vars.orangeTank.isDead() && vars.pressedSemi)
		{
			if (vars.orangeTank.shoot())
				playSound(vars.shootSoundFile);
		}
		
		if (vars.blueTank != null && !vars.blueTank.isDead() && vars.pressedEnter)
		{
			if (vars.blueTank.shoot())
				playSound(vars.shootSoundFile);
		}
	}
	
	// spawns powerups and updates each tanks' powerup timer
	public void checkPowerups()
	{
		// checks if a powerup should be spawned
		if (vars.powerupCooldown > 0)
		{
			vars.powerupCooldown--;
			if (vars.powerupCooldown < 1)
			{
				vars.powerupCooldown = vars.powerupSpawnRate;
				spawnPowerup();
			}
		}
		
		ArrayList<Powerup> removeMes = new ArrayList<Powerup>();
		for (Powerup p : powerups)
		{
			if (p.getLabelTime() > 1)
			{
				p.changeLabelTime(-1);
				if (p.getLabelTime() <= 1)
					removeMes.add(p);
			}
		}
		
		for (Powerup p: removeMes)
		{
			powerups.remove(p);
		}
		
		for (Tank t : vars.tanks)
			t.updatePowerups();
	}
	
	// creates a random powerup at a random location
	public void spawnPowerup()
	{
		if (!vars.powerupsOn)
			return;
		
		Powerup temp;
		
		int c = 0;
		do
		{
			// sometimes spawns off screen
			int x = rand(0, (vars.fieldWidth/NODE_LENGTH)-1)*NODE_LENGTH + NODE_LENGTH/2;
			int y = rand(0, (vars.fieldHeight/NODE_LENGTH)-1)*NODE_LENGTH + NODE_LENGTH/2;
			
			temp = new Powerup(x,y);
			//do-while to not collide with dudes 
			c++;
		} while((collidesWithTanks(temp.getShape()) || collidesWithPowerups(temp.getShape())) &&
					c < (vars.fieldWidth/NODE_LENGTH) * (vars.fieldHeight/NODE_LENGTH));
		
		if (c < (vars.fieldWidth/NODE_LENGTH) * (vars.fieldHeight/NODE_LENGTH))
		{
			powerups.add(temp);
			playSound(vars.powerupSpawnSoundFile);
		}
	}
	
	// returns true if the shape collides with any powerup
	public boolean collidesWithPowerups(myShape temp)
	{
		for (Powerup p : powerups)
			if (shapeCollision(temp, p.getShape()))
				return true;
		
		return false;
	}
	
	// checks if a tank exists and is living, then moves it based on keyboard inputs
	public void checkTankMovement()
	{
		// move red tank
		if (vars.redTank != null && !vars.redTank.isDead())
		{
			if (vars.pressedW)
				vars.redTank.doMovement("FORWARD");
			if (vars.pressedS)
				vars.redTank.doMovement("BACKWARD");
			if (vars.pressedD)
				vars.redTank.doMovement("RIGHT");
			if (vars.pressedA)
				vars.redTank.doMovement("LEFT");
		}
		
		if (vars.greenTank != null && !vars.greenTank.isDead())
		{
			if (vars.pressedUp)
				vars.greenTank.doMovement("FORWARD");
			if (vars.pressedDown)
				vars.greenTank.doMovement("BACKWARD");
			if (vars.pressedLeft)
				vars.greenTank.doMovement("LEFT");
			if (vars.pressedRight)
				vars.greenTank.doMovement("RIGHT");
		}
		
		if (vars.orangeTank != null && !vars.orangeTank.isDead())
		{
			if (vars.pressedI)
				vars.orangeTank.doMovement("FORWARD");
			if (vars.pressedK)
				vars.orangeTank.doMovement("BACKWARD");
			if (vars.pressedJ)
				vars.orangeTank.doMovement("LEFT");
			if (vars.pressedL)
				vars.orangeTank.doMovement("RIGHT");
		}		
		
		if (vars.blueTank != null && !vars.blueTank.isDead())
		{
			if (vars.pressed4)
				vars.blueTank.doMovement("FORWARD");
			if (vars.pressed5)
				vars.blueTank.doMovement("BACKWARD");
			if (vars.pressed2)
				vars.blueTank.doMovement("LEFT");
			if (vars.pressed8)
				vars.blueTank.doMovement("RIGHT");
		}
		
	}
	
	// moves the bullets and updates a tank's remaining bullets when it dies. 
	public void moveBullets()
	{
		for (int i = 0; i< bullets.size(); i++)
		{
			//System.out.println(bullets.size());
			Bullet bullet = bullets.get(i);
			if (bullet.isDead())
			{
				for (Tank t : vars.tanks)
					if (bullet.getOwner().equals(t.color()))
						t.changeBulletsLeft(1);
				
				if (bullet.isBomb())
					createExplosion(bullet.getX(), bullet.getY());
					
				bullets.remove(i);
			}
			else
			{
				bullet.moveBullet();
			}
		}
	}
	
	// creates a ring of shrapnel (extension of "Bullet" class)
	private void createExplosion(double x, double y)
	{
		for (int i=0;i<16;i++)
		{
			bullets.add(new Shrapnel(x, y, Math.toDegrees(Math.PI * i/8), ""));
		}
	}

	// returns the wall that the shape is colliding with or null if it doesn't
	public Wall collidesWithWalls(myShape shape)
	{
		vars.badWall = null;
		for (int i=0;i<walls.size();i++)
		{
			if (shapeCollision(walls.get(i).getShape(), shape))
			{
				vars.badWall = walls.get(i).getShape();
				return walls.get(i);
			}
		}
		return null;
	}		

	// returns true if the shape collides with any tank
	public boolean collidesWithTanks(myShape shape)
	{
		for (Tank t : vars.tanks)
		{
			if (shapeCollision(t.shape(), shape))
				return true;
		}
		return false;
	}
	
	// checks if a tank got hit by a bullet, bounces bullets off walls, and gives tanks powerups if they touch one
	public void checkCollisions()
	{		
		for (int i=0;i<bullets.size();i++)
		{
			Bullet bullet = bullets.get(i);
			myShape bulletShape = bullet.getShape();
			
			for (Tank t : vars.tanks)
			{
				if (shapeCollision(bulletShape, t.shape()) && !t.isDead())
				{
					bullet.setDead(true);
					if (!t.hasPower("invulnerable"))
					{
						t.setDead(true);
						if (t.hasPower("deathrattle"))
							createExplosion(t.x(), t.y());
						playSound(vars.deathSound);
					}
				}
			}
			
			Wall tempWall = collidesWithWalls(bullet.getShape());
			if (tempWall != null)
			{
				if (bullet.isGhost())
				{
					if (tempWall.isEdgeWall())
						bullet.setDead(true);
				}
				else
				{
					bullet.bounce(tempWall.getOrientation());
				}
				playSound(vars.bounceSoundFile);
			}
		}
		
		try
		{
			for (Powerup p : powerups)
				for (Tank t: vars.tanks)
					if (shapeCollision(p.getShape(), t.shape()) && p.getLabelTime() < 0)
					{
						t.addPower(new TankPower(p.getType(), p.getTime()));
						//powerups.remove(p);
						p.setColor(t.drawColor());
						p.setLabelTime(200);
						playSound(vars.powerupPickupSoundFile);
					}
		}
		catch(ConcurrentModificationException e){}
	}
	
	// checks if one or fewer tanks are alive and if so, begin the end sequence ITS THE FINAL COUNTDOWN
	public void checkWinners()
	{
		if (vars.deathTime > 0)
			return;
		
		ArrayList<Tank> living = new ArrayList<Tank>();
		living.clear();
		for (Tank t : vars.tanks)
			if (t.isDead()) 
			{
				t.clearPowerups();
				t.setDirection(0);
			}
			else
				living.add(t);
		
		if (living.size() <= 1 && vars.deathTime == -1)
		{
			vars.deathTime = (living.size() == 1) ? vars.deathStart : 10;
			playSound(vars.tickingSoundFile);
		}
		
		if (vars.deathTime == 0)
		{
			if (living.size() == 0)
			{
				vars.winner = "NOBODY";
				vars.deathTime = 1;
			}
			else if (living.size() == 1)
			{
				vars.winner = living.get(0).color() + " TANK";
				living.get(0).addPoint(1);
				vars.deathTime = vars.deathStart;
			}
			vars.deathTime = -1;
		}
	}
	
	// displays the winning screen and continue button
	public void setWinOn()
	{
		vars.winTextOn = true;
		continueButton.setLocation(vars.fieldWidth/2 - continueButton.getWidth()/2, vars.fieldHeight/2 + continueButton.getHeight()/2 + 100);
		continueButton.setVisible(true);
		tickingSound.stop();
		tickingSound.setFramePosition(0);
		
		playSound(vars.pointSoundFile);
	}
	
	// http://weblog.jamisbuck.org/2011/1/3/maze-generation-kruskal-s-algorithm
	// creates a maze
	public void makeMaze()
	{
		walls.clear();
		ArrayList<Wall> tempWalls = new ArrayList<Wall>();
		
		vars.fieldWidth = 300 + 100*rand(0, 4);
		vars.fieldHeight = 300 + 100*rand(0, 4);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		gameFrame.setSize(vars.fieldWidth + 16, vars.fieldHeight + vars.scoreBoardHeight);
		if (vars.tanksToAdd.size() < 3)
			gameFrame.setSize(vars.fieldWidth + 16, vars.fieldHeight + vars.scoreBoardHeight/2);
		
		gameFrame.setLocation((int)screenSize.getWidth()/2 - vars.fieldWidth/2,(int) screenSize.getHeight()/2 - (vars.fieldHeight + vars.scoreBoardHeight)/2);
		
		int w = vars.fieldWidth/NODE_LENGTH;
		int h = vars.fieldHeight/NODE_LENGTH;
		int[][] nodes = new int[w][h];
		
		boolean[][] vwalls = new boolean[w][h];
		boolean[][] hwalls = new boolean[w][h];
		
		int index = 0;
		for (int i=0;i<w;i++)
		{
			for (int j=0;j<h;j++)
			{
				//make a temp wall everywhere in between nodes in an array
				vwalls[i][j] = true;
				hwalls[i][j] = true;
				nodes[i][j] = index;
				index++;
				
				vwalls[0][j] = false;//don't draw borders until end
				hwalls[i][0] = false;
			}
		}
		
		while(!mazeDone(nodes))
		{
			int rx = (int)(Math.random()*(w));
			int ry = (int)(Math.random()*(h));
			int rd = (int)(Math.random()*4); // random direction (n,s,e,w) to compare to

			try
			{
				int a = nodes[rx][ry], b = -1;
				switch(rd)
				{
					case 0:
						b = nodes[rx][ry + 1];
						break;
					case 1:
						b = nodes[rx][ry - 1];
						break;
					case 2:
						b = nodes[rx + 1][ry];
						break;
					case 3:
						b = nodes[rx - 1][ry];
						break;
				}
				
				if (a != b)
				{
					if (rd == 0)
						hwalls[rx][ry+1] = false;					
					if (rd == 1)
						hwalls[rx][ry] = false;					
					if (rd == 2)
						vwalls[rx+1][ry] = false;					
					if (rd == 3)
						vwalls[rx][ry] = false;
					
					updateBags(nodes, a, b);
				}
				
			}
			catch(Exception e)
			{}
		}
		
		//randomly remove t walls to prevent choke points 
		int r = (int) w*h/6; // arbitrary strategery 
		while (r > 0)
		{
			int rx = (int)(Math.random()*(w));
			int ry = (int)(Math.random()*(h));
			int which = rand(0, 1);
			
			if (which == 0)
			{
				if (vwalls[rx][ry])
				{
					vwalls[rx][ry] = false;
					r--;
				}
			}
			else
			{
				if (hwalls[rx][ry])
				{
					hwalls[rx][ry] = false;
					r--;
				}
			}
		}
		
		hwalls[w-1][h-1] = false; //lazy way of fixing bug where the bottom right corner can be cut off
		
		// add temp walls to real walls
		for (int i=0;i<w;i++)
		{
			for (int j=0;j<h;j++)
			{
				if (vwalls[i][j])
					tempWalls.add(new Wall(i*NODE_LENGTH, j*NODE_LENGTH, 10, NODE_LENGTH + 10));
				if (hwalls[i][j])
					tempWalls.add(new Wall(i*NODE_LENGTH, j*NODE_LENGTH, NODE_LENGTH + 10, 10));
			}
		}
		
		int b = 2; // buffer
		
		//makes corners
		for (int i=0;i<tempWalls.size();i++)
		{
			Wall t = tempWalls.get(i);
			if (t.getOrientation().equals("V"))
			{
				walls.add(new Wall(t.getX(), t.getY() + b, t.getWidth(), t.getHeight()-2*b)); //"main"
				
				walls.add(new Wall(t.getX() + b, t.getY(), t.getWidth() - b, b, "H")); //top	
				walls.add(new Wall(t.getX() + b, t.getY() + t.getHeight() - b, t.getWidth() - b, b, "H")); //bottom
				
				walls.add(new Wall(t.getX(), t.getY(), b, b)); //top's corners
				walls.add(new Wall(t.getX() + t.getWidth() - b, t.getY(), b, b));
				
				walls.add(new Wall(t.getX(), t.getY() + t.getHeight() - b, b, b)); //bottom's corners
				walls.add(new Wall(t.getX() + t.getWidth() - b, t.getY() + t.getHeight() - b, b, b));
			}
			if (t.getOrientation().equals("H"))
			{
				walls.add(new Wall(t.getX() + b, t.getY(), t.getWidth() - b, t.getHeight())); //"main"
				
				walls.add(new Wall(t.getX(), t.getY() + b, b, t.getHeight() - b, "V")); //left
				walls.add(new Wall(t.getX() + t.getWidth() - b, t.getY() + b, b, t.getHeight() - b, "V")); //right 
				
				walls.add(new Wall(t.getX(), t.getY(), b, b)); //left's corners
				walls.add(new Wall(t.getX(), t.getY() + t.getHeight() - b, b, b));
				
				walls.add(new Wall(t.getX() + t.getWidth() - b, t.getY(), b, b)); //right's corners
				walls.add(new Wall(t.getX() + t.getWidth() - b, t.getY() + t.getHeight() - b, b, b));
			}
		}
		
		// border
		walls.add(new EdgeWall(0, 0, vars.fieldWidth, 10));
		walls.add(new EdgeWall(0, 0, 10, vars.fieldHeight));
		walls.add(new EdgeWall(0, vars.fieldHeight, vars.fieldWidth + 10, 10));
		walls.add(new EdgeWall(vars.fieldWidth, 0, 10, vars.fieldHeight));
	}
	
	// part of maze generation
	private void updateBags(int[][] n, int old, int nw)
	{		
		for (int i=0;i<n.length;i++)
			for (int j=0;j<n[0].length;j++)
				if (n[i][j] == old)
					n[i][j] = nw;
	}

	// part of maze generation
	private boolean mazeDone(int[][] n)
	{
		for (int i=0;i<n.length-1;i++)
			for (int j=0;j<n[0].length-1;j++)
				if (n[i][j] != n[i+1][j] ||
					 n[i][j] != n[i][j+1])
					return false;
		
		return true;
	}
	
	// not mine thanks internet
	boolean shapeCollision(myShape a, myShape b)
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
	
	// plays a sound based on the shorthand String variable
	private void playSound(String whichSound)
	{
		if (!vars.soundEnabled)
			return;
		
		if (vars.memesEnabled)
			whichSound = vars.memeSoundFile;
		
		if (whichSound.equals(vars.tickingSoundFile))
			tickingSound.start();
		else
		{
			try
			{
				AudioInputStream clip;
				clip = AudioSystem.getAudioInputStream(getClass().getResource(whichSound));
				Clip sound = AudioSystem.getClip();
				sound.open(clip);
				sound.start();
			}
			catch (IOException e) {}
			catch (LineUnavailableException e) {}
			catch (UnsupportedAudioFileException e) {}
		}
	}
	
	public int rand(int l, int h)
	{
		//inclusive
		return (int)(Math.random()*((h+1)-l)) +l;
	}
	
	public void keyPressed(KeyEvent event)
	{
		//65: a
		//68: d
		//83: s
		//87: w
		
		//47: fZero
		
		int key = event.getKeyCode();
		
		// "press any key to continue" on continue button
		if (vars.winTextOn)
			continueButton.doClick();

		if (key == KeyEvent.VK_CONTROL) // CONTROL key
			vars.pressedControlKey = true;

		if ((key == 88) && (vars.pressedControlKey)) // CONTROL + X
		{
			gameFrame.dispose();
			System.exit(0);
		}
		
		if ((key == 78) && (vars.pressedControlKey))
		{
			start();
		}
		
		//red controls wasd(space), green arrows + numpad_zero, orange ijkl;, blue 4258enter
		
		switch(key)
		{
			case 65:
				vars.pressedA = true;
				break;
			case 68:
				vars.pressedD = true;
				break;
			case 83:
				vars.pressedS = true;
				break;
			case 87:
				vars.pressedW = true;
				break;
			case KeyEvent.VK_SPACE:
				vars.pressedSpace = true;
				break;
				
			case KeyEvent.VK_UP:
				vars.pressedUp = true;
				break;
			case KeyEvent.VK_DOWN:
				vars.pressedDown = true;
				break;
			case KeyEvent.VK_LEFT:
				vars.pressedLeft = true;
				break;
			case KeyEvent.VK_RIGHT:
				vars.pressedRight = true;
				break;
			case KeyEvent.VK_NUMPAD0:
				vars.pressedZero = true;
				break;
				
			case 73:
				vars.pressedI = true;
				break;
			case 74:
				vars.pressedJ = true;
				break;
			case 75:
				vars.pressedK = true;
				break;
			case 76: 
				vars.pressedL = true;
				break;
			case 59:
				vars.pressedSemi = true;
				break;
				
			case KeyEvent.VK_NUMPAD4:
				vars.pressed4 = true;
				break;
			case KeyEvent.VK_NUMPAD2:
				vars.pressed2 = true;
				break;
			case KeyEvent.VK_NUMPAD5:
				vars.pressed5 = true;
				break;
			case KeyEvent.VK_NUMPAD8:
				vars.pressed8 = true;
				break;
			case KeyEvent.VK_ENTER:
				vars.pressedEnter = true;
				break;
				
			// also triggers start button
			case 8:
				vars.pressedBackspace = true;
				if (vars.tanksToAdd.size() >= 2)
					startButton.doClick();
				break;
		}
	}
	
	public void keyReleased(KeyEvent event)
	{
		int key = event.getKeyCode();

		if (key == KeyEvent.VK_CONTROL) // CONTROL key
			vars.pressedControlKey = false;
		
		switch(key)
		{
			case 65:
				vars.pressedA = false;
				break;
			case 68:
				vars.pressedD = false;
				break;
			case 83:
				vars.pressedS = false;
				break;
			case 87:
				vars.pressedW = false;
				break;
			case KeyEvent.VK_SPACE:
				vars.pressedSpace = false;
				if (vars.redTank != null) {vars.redTank.setHasFired(false);}
				break;
				
			case KeyEvent.VK_UP:
				vars.pressedUp = false;
				break;
			case KeyEvent.VK_DOWN:
				vars.pressedDown = false;
				break;
			case KeyEvent.VK_LEFT:
				vars.pressedLeft = false;
				break;
			case KeyEvent.VK_RIGHT:
				vars.pressedRight = false;
				break;
			case KeyEvent.VK_NUMPAD0:
				vars.pressedZero = false;
				if (vars.greenTank != null) {vars.greenTank.setHasFired(false);}
				break;
				
			case 73:
				vars.pressedI = false;
				break;
			case 74:
				vars.pressedJ = false;
				break;
			case 75:
				vars.pressedK = false;
				break;
			case 76: 
				vars.pressedL = false;
				break;
			case 59:
				vars.pressedSemi = false;
				if (vars.orangeTank != null) {vars.orangeTank.setHasFired(false);}
				break;
				
			case KeyEvent.VK_NUMPAD4:
				vars.pressed4 = false;
				break;
			case KeyEvent.VK_NUMPAD2:
				vars.pressed2 = false;
				break;
			case KeyEvent.VK_NUMPAD5:
				vars.pressed5 = false;
				break;
			case KeyEvent.VK_NUMPAD8:
				vars.pressed8 = false;
			case KeyEvent.VK_ENTER:
				vars.pressedEnter = false;
				if (vars.blueTank != null) {vars.blueTank.setHasFired(false);}
				break;
				
			// pause
			case 27:
				if (vars.gameOn)
				{
					vars.paused = !vars.paused;
					playSound(vars.pauseSoundFile);
				}
				break;
		}
	}
}