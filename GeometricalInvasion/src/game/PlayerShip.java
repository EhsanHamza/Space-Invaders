package game;
import java.awt.Color;
import java.awt.Graphics;

public class PlayerShip extends Ship {
	private int moveSpeed = 3;
	private long fireRate = 1;
	private boolean alive;

	// Constructor for the PlayerShip
	public PlayerShip(int health, String name, int damage, int fireRate, Point[] shape, Point position,
			double rotation) {
		super(health, name, damage, fireRate, shape, position, rotation);
		this.fireRate = fireRate * 500;
		this.alive = true;
	}

	// Returns the fireRate of the playerShip.
	public long getCooldownFireRate() {
		return fireRate;
	}

	// Draws and places the ship.
	public void paint(Graphics brush) {
		int x = (int) position.x;
		int y = (int) position.y;

		int[] xPoints = {x, x + 50, x + 25};
		int[] yPoints = {y + 600, y + 600, y + 550};
		brush.setColor(Color.GREEN);
		brush.fillPolygon(xPoints, yPoints, 3);
	}

	// Moves left according to the moveSpeed
	public void moveLeft() {
		if (position.x > 0) {
			position.x -= moveSpeed;
		}
	}

	// Moves right according to the moveSpeed
	public void moveRight() {
		if (position.x < 730) {
			position.x += moveSpeed;
		}
	}

	// If player sustains a hit, reduce HP by 1.
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
	public boolean isAlive() {
		return alive;
	}



}
