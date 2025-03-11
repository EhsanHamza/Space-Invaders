package game;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The PlayerShip class represents the player's ship in the game.
 * It extends the Ship class and includes movement, firing, health system, 
 * and power-up abilities.
 */

public class PlayerShip extends Ship {
	private int moveSpeed = 3;
	private long fireRate = 1;
	private boolean alive;


	// Constructor for the PlayerShip

    /**
     * Constructs a PlayerShip with specified attributes.
     *
     * @param health    The initial health of the player's ship.
     * @param name      The name of the player's ship.
     * @param damage    The damage the player's ship does.
     * @param fireRate  The rate at which the player can fire.
     * @param shape     The shape representation of the player's ship.
     * @param position  The initial position of the player's ship.
     * @param rotation  The rotation of the player's ship.
     */
	public PlayerShip(int health, String name, int damage, int fireRate, Point[] shape, Point position,
			double rotation) {
		super(health, name, damage, fireRate, shape, position, rotation);
		this.fireRate = fireRate * 500;
		this.alive = true;
	}

	// Returns the fireRate of the playerShip.

    /**
     * Gets the cooldown fire rate for the player
     *
     * @return The fire rate cooldown
     */
	public long getCooldownFireRate() {
		return fireRate;
	}

	// Draws and places the ship.
	 /**
     * Paints the player's ship on the screen.
     *
     * @param brush Draws the graphics for the game
     */
	public void paint(Graphics brush) {
		int x = (int) position.x;
		int y = (int) position.y;

		int[] xPoints = {x, x + 50, x + 25};
		int[] yPoints = {y + 600, y + 600, y + 550};
		brush.setColor(Color.GREEN);
		brush.fillPolygon(xPoints, yPoints, 3);
	}

	// Moves left according to the moveSpeed
	/**
     * Moves the player ship to the left according to its movement speed.
     */
	public void moveLeft() {
		if (position.x > 0) {
			position.x -= moveSpeed;
		}
	}

	// Moves right according to the moveSpeed
	/**
     * Moves the player ship to the right according to its movement speed.
     */
	public void moveRight() {
		if (position.x < 730) {
			position.x += moveSpeed;
		}
	}

	// If player sustains a hit, reduce HP by 1.
	/**
     * Reduces the player's health when damage is taken.
     * If player health reaches zero, the player is dead.
     */
	public void damageTaken() {
		health--;

		if(health <= 0) {
			dead();
		}
	}

	// If the player's HP reaches 0, end the game.
	public void dead() {
		alive = false;
	}

	// returns if the player is alive or not.
	/**
     * Checks if the player is still alive.
     *
     * @return true if the player is alive, else false.
     */
	public boolean isAlive() {
		return alive;
	}

	// A method in the inner class that temporarily increases the stats of the player.
	 /**
     * Temporarily increases the player's stats based on the color of the power-up.
     * 
     * @param color The color of the power-up determines the stat increase.
     */
	public void increaseStats(Color color) {
		switch (color.toString()) {

		// The different colors that the powerUps can offer.
		case "RED":
			setDamage(getDamage() + 1);
			break;

		case "GREEN":
			setHealth(3);
			break;

		case "BLUE":
			moveSpeed += 2;
			break;
		}

		// The power-ups will last for 5 seconds. (5000 milliseconds)
		new Timer().schedule(new TimerTask() {
			public void run() {
				resetStats(color);
			}

		}, 5000);
	}


	// A method that returns the player's stats back to normal after getting a powerUp.
	 /**
     * Resets the player's stats back to normal after the effect of power-up weares off.
     *
     * @param color The color of the power-up which determines which stat to reset.
     */
	public void resetStats(Color color) {
		switch (color.toString()) {

		// Resets the stats of the player back to normal.
		case "RED":
			setDamage(getDamage() - 1);
			break;

		case "BLUE":
			moveSpeed -= 2;
			break;
		}
	}

	// Inner class that implements PowerUps, where it will increase the player's stats temporarily.
	/**
     * The PowerUps inner class represents power-ups in the game that temporarily increase the player's stats.
     */
	static class PowerUps {
		private Polygon shape;
		private Color powerColor;
		private boolean active = true;
		private Point position;

		// Constructor for the inner class
		/**
         * Constructs a power-up at a specified position with a random color effect.
         *
         * @param position The initial position of the power-up.
         */
		public PowerUps(Point position) {
			this.position = new Point(position.x, position.y);

			// Create an array of possible colors. PowerColor will be randomly generated.
			Color[] colorList = {Color.RED, Color.BLUE, Color.GREEN};
			this.powerColor = colorList[new Random().nextInt(colorList.length)];

			// Create the square and initialize it in the Polygon
			Point[] square = {
					new Point(-10, -10), new Point(10, -10),
					new Point(10, 10), new Point(-10, 10)
			};

			this.shape = new Polygon(square, position, 0);

			// Rotates the polygon 5 degrees every frame
			new Timer().scheduleAtFixedRate(new TimerTask() {
				public void run() {
					shape.rotate(5);
				}
			}, 0, 50);

		}

		// Getter for active
		/**
         * Checks if the power-up is still active.
         *
         * @return true if the power-up is active, false otherwise.
         */
		public boolean powerActive() {
			return active;
		}

		// Setter for active, to false.

        /**
         * Deactivates the power-up.
         */
		public void powerInactive() {
			this.active = false;
		}

		// Moves the powerUp downwards.
		 /**
         * Moves the power-up downwards on the screen.
         */
		public void projectileMove() {
			position.y += 2;
			shape = new Polygon(shape.getPoints(), position, 0);
		}

		// Getter for position.
		  /**
         * Gets the current position of the power-up.
         *
         * @return The position of the power-up.
         */
		public Point getPosition() {
			return position;
		}

		// Getter for the powerColor.
		 /**
         * Gets the color of the power-up, which determines its effect.
         *
         * @return The color of the power-up.
         */
		public Color getColor() {
			return powerColor;
		}

		// Paint method for the inner class of PowerUps
		public void paint(Graphics brush) {
			if (!active) return;

			brush.setColor(powerColor);
			Point[] rotatePoints = shape.getPoints();

			// Get the x points from the coordinates
			int[] xPoints = {
					(int) rotatePoints[0].getX(), (int) rotatePoints[1].getX(),
					(int) rotatePoints[2].getX(), (int) rotatePoints[3].getX()
			};

			// Get the y points from the coordinates
			int[] yPoints = {
					(int) rotatePoints[0].getY(), (int) rotatePoints[1].getY(),
					(int) rotatePoints[2].getY(), (int) rotatePoints[3].getY()
			};

			brush.fillPolygon(xPoints, yPoints, 4);
		}
	}
	/**
	 * prints out a message Game Over if the game ends
	 * @param brush Draws the Game Over message in the game
	 */
	public void paintGameOver(Graphics brush) {
	    brush.setColor(Color.WHITE);
	    brush.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 50)); 
	    String message = "GAME OVER";
	    brush.drawString(message, 250, 300); // This will display the text at the center of the screen.
	}
	
}

