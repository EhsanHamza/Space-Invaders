package game;

/*
CLASS: YourGameNameoids
DESCRIPTION: Extending Game, YourGameName is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

 */
import java.awt.*;
import java.awt.event.*;

class GeometricalInvasion extends Game implements KeyListener {
	static int counter = 0;
	PlayerShip player;

	// Boolean variables for keyboard movement
	boolean left = false;
	boolean right = false;

	public GeometricalInvasion() {
		super("Geometrical Invasion",800,1200);
		this.setFocusable(true);
		this.addKeyListener(this);
		this.requestFocus();

		Point[] points = {
				new Point(0,0),
				new Point(50, 0),
				new Point(25, 25)
		};

		Point position = new Point(400, 300);
		double rotation = 0;
		player = new PlayerShip(3, "Player", 1, 1, points, position, rotation);
	}

	public void paint(Graphics brush) {
		brush.setColor(Color.black);
		brush.fillRect(0,0,width,height);

		// sample code for printing message for debugging
		// counter is incremented and this message printed
		// each time the canvas is repainted
		counter++;
		brush.setColor(Color.white);
		brush.drawString("Counter is " + counter,10,10);

		// Moves the player depending on which keys are pressed.
		if (left) {
			player.moveLeft();
		} else if (right) {
			player.moveRight();
		}

		player.paint(brush);
	}

	// Checks if the left and right keys are pressed
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if (keyCode == KeyEvent.VK_A) {
			left = true;
		} else if (keyCode == KeyEvent.VK_D) {
			right = true;
		}
		
		repaint();
	}

	// Checks if the left and right keys are released
	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if (keyCode == KeyEvent.VK_A) {
			left = false;
		} else if (keyCode == KeyEvent.VK_D) {
			right = false;
		}
		
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public static void main (String[] args) {
		GeometricalInvasion a = new GeometricalInvasion();
		a.repaint();
	}
}