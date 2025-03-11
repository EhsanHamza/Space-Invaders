package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * The EnemyShip class represents enemy ships in the game.
 * It extends the Ship class and includes movement, firing behavior, and health system.
 */
public class EnemyShip extends Ship {
	private Random random = new Random();
	private boolean enemyAlive = true;

	/**
     * Consturcts an enemy ship with specified attributes.
     *
     * @param health    The initial health of the enemy ship.
     * @param name      The name of the enemy ship.
     * @param damage    The damage the enemy ship projectiles does.
     * @param fireRate  The rate at which the enemy can fire.
     * @param shape     The shape of the enemy ship.
     * @param position  The initial position of the enemy ship.
     * @param rotation  The rotation of the enemy ship.
     */
	public EnemyShip(int health, String name, int damage, int fireRate, Point[] shape, Point position,
			double rotation) {
		super(health, name, damage, fireRate, shape, position, rotation);
	}

	// The paint method for the enemyShips.
	 /**
     * Paints the enemy ship on the screen.
     *
     * @param brush Drawing the graphics
     */
	public void paint(Graphics brush) {
		int x = (int) position.x;
		int y = (int) position.y;

		int[] xPoints = {x, x + 50, x + 50, x};
		int [] yPoints = {y, y, y + 50, y + 50};
		brush.setColor(Color.RED);
		brush.fillPolygon(xPoints, yPoints, 4);
	}

	// 20% of firing every movement they do.
	/**
     * Determines whether the enemy ship should fire a projectile.
     *
     * @return true if the enemy fires, else false.
     */
	public boolean enemyFire() {
		return random.nextDouble() < 0.2;
	}

	// Constructs an enemy projectile, and fires it.
	/**
     * Fires a projectile from the enemy ship.
     *
     * @return A new projectile.
     */
	public Projectile fireProjectile() {
		return new Projectile(position.x + 25, position.y + 50, true);
	}

	// Handles horizontal movement of the ship.
	/**
     * Moves the enemy ship horizontally.
     *
     * @param moveRight If true, the enemy moves right, else, it moves left.
     */
	public void move(boolean moveRight) {
		if (moveRight == true) {
			position.x += 20;
		} else {
			position.x -= 20;
		}
	}

	// Moves down after hitting the boundaries.
	 /**
     * Moves the enemy ship downward after hitting the screen boundary.
     */
	public void moveDown() {
		position.y += 40;
	}

	// Reduces Enemy health by 1.
	/**
     * Decreases the enemy's health when it takes damage.
     *
     * @param damage The amount of damage enemy ship has taken.
     */
	public void enemyDamageTaken(int damage) {
		health -= damage;
		
		if (health <= 0) {
			health = 0;
			enemyAlive = false;
		}
	}

	// Despawns the enemy if their HP is gone.
	 /**
     * Checks if the enemy ship has been killed (health is zero or less).
     *
     * @return true if the enemy ship is dead, else false.
     */
	public boolean enemyDead() {
		return health <= 0;
	}
}
