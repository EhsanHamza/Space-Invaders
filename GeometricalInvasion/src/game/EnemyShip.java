package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class EnemyShip extends Ship {
	private Random random = new Random();
	private boolean enemyAlive = true;

	// Constructor for the enemyShips.
	public EnemyShip(int health, String name, int damage, int fireRate, Point[] shape, Point position,
			double rotation) {
		super(health, name, damage, fireRate, shape, position, rotation);
	}

	// The paint method for the enemyShips.
	public void paint(Graphics brush) {
		int x = (int) position.x;
		int y = (int) position.y;

		int[] xPoints = {x, x + 50, x + 50, x};
		int [] yPoints = {y, y, y + 50, y + 50};
		brush.setColor(Color.RED);
		brush.fillPolygon(xPoints, yPoints, 4);
	}

	// 20% of firing every movement they do.
	public boolean enemyFire() {
		return random.nextDouble() < 0.2;
	}

	// Constructs an enemy projectile, and fires it.
	public Projectile fireProjectile() {
		return new Projectile(position.x + 25, position.y + 50, true);
	}

	// Handles horizontal movement of the ship.
	public void move(boolean moveRight) {
		if (moveRight == true) {
			position.x += 20;
		} else {
			position.x -= 20;
		}
	}

	// Moves down after hitting the boundaries.
	public void moveDown() {
		position.y += 40;
	}

	// Reduces Enemy health by 1.
	public void enemyDamageTaken(int damage) {
		health -= damage;
		
		if (health <= 0) {
			health = 0;
			enemyAlive = false;
		}
	}

	// Despawns the enemy if their HP is gone.
	public boolean enemyDead() {
		return health <= 0;
	}
}
