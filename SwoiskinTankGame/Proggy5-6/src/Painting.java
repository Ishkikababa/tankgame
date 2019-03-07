import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Painting
{
	private int frameWidth = 600, frameHeight = 450;

	public static void main(String[] args)
	{
		new Painting();
	}

	// Constructor
	public Painting()
	{
		JFrame frame = new JFrame("Painting Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameWidth, frameHeight);
		frame.setLocationRelativeTo(null);

		frame.add(new DrawingPane());
		frame.setVisible(true);
	}

	public class DrawingPane extends JPanel
	{
		private BufferedImage foreground;

		// Constructor
		public DrawingPane()
		{
			setBackground(Color.WHITE); // Set the background color
			foreground = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_ARGB);

			MouseAdapter mouseHandler = new MouseAdapter()
			{
				private Point startPoint;

				public void mousePressed(MouseEvent e)
				{
					startPoint = e.getPoint();
				}

				public void mouseDragged(MouseEvent e)
				{
					Point endPoint = e.getPoint();
					Graphics2D g2D = foreground.createGraphics();

					Point from = new Point(startPoint);
					Point to = new Point(endPoint);

					g2D.setColor(Color.RED); // Set the foreground (pen) color

					// Set the line width (in pixels), along with other drawing features
					g2D.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

					// Draw a single pixel
					g2D.draw(new Line2D.Float(from, to));
					g2D.dispose();

					startPoint = endPoint;
					repaint();
				}
			};

			addMouseListener(mouseHandler);
			addMouseMotionListener(mouseHandler);
		}

		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			Graphics2D g2D = (Graphics2D) g.create();

			// Set the stroke (line) transparency (0 = totally transparent, 1 = opaque)
			g2D.setComposite(AlphaComposite.SrcOver.derive(0.5f));

			// Display the newly-drawn pixel
			g2D.drawImage(foreground, 0, 0, this);
			g2D.dispose();
		}
	}
}