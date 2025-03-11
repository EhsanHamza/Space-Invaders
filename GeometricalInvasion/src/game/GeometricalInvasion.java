package game;

/*
CLASS: YourGameNameoids
DESCRIPTION: Extending Game, YourGameName is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

 */
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import game.PlayerShip.PowerUps;

class GeometricalInvasion extends Game implements KeyListener {

	// Instance Variables
	static int counter = 0;
	PlayerShip player;
	EnemyShip enemy;
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	private ArrayList<Projectile> enemyProjectiles = new ArrayList<>();
	private ArrayList<EnemyShip> enemies = new ArrayList<>();
	private ArrayList<PowerUps> powerUps = new ArrayList<>();
	private Timer enemyTimeMove = new Timer();
	private long lastFireTime = 0;
	private boolean moveRight = true;


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

		// Initialize the positions and rotations.
		double playerRotation = 0;
		double enemyRotation = 45;

		// Initialize the enemies and the player.
		player = new PlayerShip(3, "Player", 1, 1, points, new Point(400, 300), playerRotation);
		enemies.add(new EnemyShip(3, "Enemy1", 1, 1, points, new Point(450, 75), enemyRotation));
		enemies.add(new EnemyShip(3, "Enemy2", 1, 1, points, new Point(375, 75), enemyRotation));
		enemies.add(new EnemyShip(3, "Enemy3", 1, 1, points, new Point(300, 75), enemyRotation));
		enemies.add(new EnemyShip(3, "Enemy4", 1, 1, points, new Point(225, 75), enemyRotation));
		enemies.add(new EnemyShip(3, "Enemy5", 1, 1, points, new Point(150, 75), enemyRotation));
		enemies.add(new EnemyShip(3, "Enemy6", 1, 1, points, new Point(300, 150), enemyRotation));
		enemies.add(new EnemyShip(3, "Enemy7", 1, 1, points, new Point(225, 150), enemyRotation));
		enemies.add(new EnemyShip(3, "Enemy8", 1, 1, points, new Point(375, 150), enemyRotation));
		enemies.add(new EnemyShip(3, "Enemy9", 1, 1, points, new Point(300, 225), enemyRotation));

		// Handles the enemies' movement. Based on specific intervals.
		enemyTimeMove.schedule(new TimerTask() {
			public void run() {
				moveEnemies();
			}
		}, 0, 1000);
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

		// Updates the player's actions depending on what key is pressed.
		if (left) {
			player.moveLeft();
		} else if (right) {
			player.moveRight();
		}

		// Creates the player.
		if(player.isAlive()) {
			player.paint(brush);
		} else {
			player.paintGameOver(brush);
		}

		// Creates the enemy and spawns it on the field.
		for (EnemyShip enemy : enemies) {
			enemy.paint(brush);
		}

		// Creates the projectile and fires it.
		for (Projectile bullet : projectiles) {
			bullet.paint(brush);
		}

		// Creates enemy projectiles and fires it.
		for (Projectile enemyBullet : enemyProjectiles) {
			enemyBullet.paint(brush);
		}

		// Creates powerUps for the player.
		for (PowerUps power : powerUps) {
			power.paint(brush);
		}
	}

	// Checks if the left and right keys are pressed
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (player.isAlive()) {
			if (key == KeyEvent.VK_A) {
				left = true;

			} else if (key == KeyEvent.VK_D) {
				right = true;

			} else if (key == KeyEvent.VK_SPACE) {
				fireProjectile();
			}

			repaint();
		}
	}

	// Checks if the left and right keys are released
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_A) {
			left = false;

		} else if (key == KeyEvent.VK_D) {
			right = false;
		}

		repaint();
	}

	// Is not used throughout the game, but is required for the class.
	@Override
	public void keyTyped(KeyEvent e) {

	}

	// Fires the projectiles, and controls it. Accounts for the fire rate of the playerShip.
	private void fireProjectile() {
		long time = System.currentTimeMillis();

		// Checks if enough time has passed for the fireRate.
		if (time - lastFireTime >= player.getCooldownFireRate()) {
			double projectileX = player.position.x + 25;
			double projectileY = player.position.y + 530;

			// Creates the new projectile if time - lastFireTime is greater than the fire rate.
			projectiles.add(new Projectile(projectileX, projectileY, false));

			// Sets lastFireTime to time.
			lastFireTime = time;
		}
	}

	// Controls the enemies' movement. Shifts directions and moves down when needed.
	private void moveEnemies() {
		boolean onEdge = false;

		// Checks if at least ONE enemy is at the edge. If true, then sets atEdge to true, and breaks loop.
		for (EnemyShip enemy: enemies) {
			if ((moveRight && enemy.position.x >= 700) || (!moveRight && enemy.position.x <= 150)) {
				onEdge = true;
				break;
			}
		}

		// If true, then all enemies move down, then switch direction.
		// If false, then continue moving.
		if (onEdge) {
			for (EnemyShip enemies : enemies) {
				enemies.moveDown();
			}
			moveRight = !moveRight;

		} else {
			for (EnemyShip enemies: enemies) {
				enemies.move(moveRight);

				// Calls the enemyFire method. If true, then it fires, at a 20% chance.
				if (enemies.enemyFire()) {
					enemyProjectiles.add(enemies.fireProjectile());
				}
			}
		}

		repaint();
	}

	// A private void helper method that checks the collisions of projectiles
	// to players or enemies.
	private void checkCollision() {
		
		// Damages the player if hit by enemy projectiles.
		Iterator<Projectile> redProjectiles = enemyProjectiles.iterator();
		while (redProjectiles.hasNext()) {
			Projectile EBullet = redProjectiles.next();
			
			// If the bullet collides with the player, they take damage.
			if (EBullet.colliding(player)) {
				player.damageTaken();
				EBullet.setInactive();
			}
			
			// Removes the enemy projectiles.
			if (!EBullet.isActive()) {
				redProjectiles.remove();
			}
		}
		// Damages the enemy if it is hit by player's projectiles.
		Iterator<Projectile> greenProjectiles = projectiles.iterator();
		while (greenProjectiles.hasNext()) {
			Projectile PBullet = greenProjectiles.next();
			
			// Iterate through playerProjectiles and enemies, checking if any collide together.
			Iterator<EnemyShip> enemyIterator = enemies.iterator();
			while (enemyIterator.hasNext()) {
				EnemyShip enemy = enemyIterator.next();
				
				// Damages the enemy if it collides.
				if (PBullet.colliding(enemy)) {
					enemy.enemyDamageTaken(1);
					PBullet.setInactive();
					
					// If dead, remove the enemy.
					if (!enemy.enemyDead()) {
						enemyIterator.remove();
						
						// 30% chance of spawning a power up.
						if (new Random().nextDouble() < 0.9) {
							powerUps.add(new PowerUps(enemy.position.clone()));
						}
					}
					break;
				}
			}
			
			// Remove Player's bullet if inactive.
			if (!PBullet.isActive()) {
				greenProjectiles.remove();
			}
		}

		// Checks if the player has collided with a powerUp.
		for (int p = 0; p < powerUps.size(); p++) {
			PowerUps powerUp = powerUps.get(p);
			powerUp.projectileMove();

			if (powerCollision(player, powerUp)) {
				player.increaseStats(powerUp.getColor());
				powerUp.powerInactive();
				powerUps.remove(p);
				p--;
				
			} else if (powerUp.getPosition().y > height) {
				powerUps.remove(p);
				p--;
			}
		}
	}

	// Checks if the powerUp collides with the player.
	private boolean powerCollision(PlayerShip player, PowerUps power) {
		Rectangle playerHitbox = new Rectangle((int) player.position.x, (int) player.position.y, 50, 50);
		Rectangle powerUpHitbox = new Rectangle((int) power.getPosition().x, (int) power.getPosition().y, 20, 20);

		boolean collision = playerHitbox.intersects(powerUpHitbox);
		return collision;
	}

	// Overrides the Game's update method to account for the player's controls.
	@Override
	public void update(Graphics brush) {
		if (left) {
			player.moveLeft();
		} 

		if (right) {
			player.moveRight();
		}

		// This ensures that the projectile moves, and removes the projectiles if they aren't active.
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).move();

			if (!projectiles.get(i).isActive()) {
				projectiles.remove(i);
				i--;
			}
		}

		// This ensures that the enemy projectiles moves, and is removed accordingly if not active.
		for (int i = 0; i < enemyProjectiles.size(); i++) {
			enemyProjectiles.get(i).move();

			if (!enemyProjectiles.get(i).isActive()) {
				enemyProjectiles.remove(i);
				i--;
			}
		}

		checkCollision();
		super.update(brush);
	}

	// Main method
	public static void main (String[] args) {
		GeometricalInvasion a = new GeometricalInvasion();
		a.repaint();
	}
}
