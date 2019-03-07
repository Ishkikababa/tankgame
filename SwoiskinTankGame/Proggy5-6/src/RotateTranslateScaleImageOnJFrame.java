import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import javax.swing.*;

public class RotateTranslateScaleImageOnJFrame extends JFrame implements ActionListener
{
	private Image image = new ImageIcon("redTank.fw.png").getImage();
	private int frameWidth = 400, frameHeight = 500;
	private int timerSpeed = 20, degreesToTurn = 1;
	private double imageOrientation = 0, sizeX = 1.5, sizeY = 1.5;
	private double offsetX = frameWidth / 2 - image.getWidth(null) * sizeX / 2;
	private double offsetY = frameHeight / 2 - image.getHeight(null) * sizeY / 2;

	public static void main(String[] args)
	{
		new RotateTranslateScaleImageOnJFrame();
	}

	// Constructor
	public RotateTranslateScaleImageOnJFrame()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(frameWidth, frameHeight);
		setTitle("Rotate, Translate, and Scale an Image on a JFrame");
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);

		// Set up and start a Timer to fire every 'timerSpeed' milliseconds;
		//  this value, along with 'degreesToTurn', determines the "speed"
		//  at which the Image will rotate
		Timer timer = new Timer(timerSpeed, this);
		timer.start();
	}

	// This method is called every time the Timer fires
	public void actionPerformed(ActionEvent event)
	{
		// Determine the new angle at which the Image should be redrawn
		imageOrientation += Math.toRadians(degreesToTurn);

		// Since there are (2 * PI) radians in every circle, after every
		//  complete rotation of the Image, reset 'imageOrientation' back
		//  to zero so that it does not get too large
		imageOrientation %= Math.PI * 2;

		// Call the drawing/painting method to display the updated Image
		repaint();
	}

	public void paint(Graphics g)
	{
		// This line causes graphics and text to be rendered with anti-aliasing
		//  turned on, making the overall display look smoother and cleaner
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Draw the JFrame background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, frameWidth, frameHeight);

		// The 'Graphics2D' class provides for greater control and manipulation
		//  of two-dimensional images, shapes, and text
		Graphics2D g2D = (Graphics2D) g;

		// Set the point around which the Image is to be rotated, and then rotate
		//  the Image to the appropriate angle and position based on that point;
		//  note that for this example program, the Image is being centered on
		//  the JFrame (using 'offsetX' and 'offsetY'), and also scaled (using
		//  'sizeX' and 'sizeY'), both of which must be taken into account when
		//  determining the single point around which the Image is being rotated
		AffineTransform at = AffineTransform.getRotateInstance(imageOrientation,
                                                             image.getWidth(null) * sizeX / 2 + offsetX,
                                                             image.getHeight(null) * sizeY / 2 + offsetY);

		// Position the Image at the desired location on the JFrame; by default,
		//  the Image is located at coordinate (0, 0), which is the upper-left
		//  of the JFrame; note that if the goal is to have the Image rotate
		//  around its center-point, then 'offsetX' and 'offsetY' must be taken
		//  into account when using the 'getRotateInstance' method (above)
		at.translate(offsetX, offsetY);

		// Scale the Image (change its size); to make the Image larger, use
		//  values greater than "1"; for example, a value of "2" will double
		//  the Image size, a value of "3" will triple the size, a value of
		//  "4" will quadruple the size, and so on; a value less than "1"
		//  will make the Image smaller; for example, "0.5" halves the size
		at.scale(sizeX, sizeY);

		// Draw the updated image
		g2D.drawImage(image, at, this);

		// This line synchronizes the graphics state by flushing buffers containing
		//  graphics events and forcing the frame drawing to happen now; otherwise,
		//  it can sometimes take a few extra milliseconds for the drawing to take
		//  place, which can result in jerky graphics movement; this line ensures
		//  that the display is up-to-date; it is useful for animation, since it
		//  reduces or eliminates flickering
		Toolkit.getDefaultToolkit().sync();
	}
}