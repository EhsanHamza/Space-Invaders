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
import java.util.Timer;
import java.util.TimerTask;

class GeometricalInvasion extends Game implements KeyListener {
	static int counter = 0;
	PlayerShip player;
	EnemyShip enemy;
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	private ArrayList<Projectile> enemyProjectiles = new ArrayList<>();
	private ArrayList<EnemyShip> enemies = new ArrayList<>();
	private Timer enemyTimer = new Timer();
	private boolean movingRight = true;

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
		Point playerPosition = new Point(400, 300);
		Point enemyPosition = new Point(400, 100);
		double playerRotation = 0;
		double enemyRotation = 45;

		// Initialize the enemies and the players.
		player = new PlayerShip(3, "Player", 1, 1, points, playerPosition, playerRotation);
		enemies.add(new EnemyShip(3, "Enemy1", 1, 1, points, enemyPosition, enemyRotation));
		enemies.add(new EnemyShip(3, "Enemy2", 1, 1, points, new Point(325, 100), enemyRotation));

		// Handles the enemies' movement. Based on specific intervals.
		enemyTimer.schedule(new TimerTask() {
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
		player.paint(brush);

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
	}

	// Checks if the left and right keys are pressed
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_A) {
			left = true;
		} else if (keyCode == KeyEvent.VK_D) {
			right = true;
		} else if (keyCode == KeyEvent.VK_SPACE) {
			fireProjectile();
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

	// Fires the projectiles, and controls it.
	private void fireProjectile() {
		double projectileX = player.position.x + 25;
		double projectileY = player.position.y + 540;

		projectiles.add(new Projectile(projectileX, projectileY, false));
	}

	// Controls the enemies' movement. Shifts directions and moves down when needed.
	private void moveEnemies() {
		boolean atEdge = false;

		// Checks if at least ONE enemy is at the edge. If true, then sets atEdge to true, and breaks loop.
		for (EnemyShip enemy: enemies) {
			if ((movingRight && enemy.position.x >= 700) || !movingRight && enemy.position.x <= 150) {
				atEdge = true;
				break;
			}
		}

		// If true, then all enemies move down, then switch direction.
		// If false, then continue moving.
		if (atEdge) {
			for (EnemyShip enemies : enemies) {
				enemies.moveDown();
			}
			movingRight = !movingRight;

		} else {
			for (EnemyShip enemies: enemies) {
				enemies.move(movingRight);

				// Calls the enemyFire method. If true, then it fires, at a 20% chance.
				if (enemies.enemyFire()) {
					enemyProjectiles.add(enemies.fireProjectile());
				}
			}
		}

		repaint();
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
		super.update(brush);
	}

	// Main method
	public static void main (String[] args) {
		GeometricalInvasion a = new GeometricalInvasion();
		a.repaint();
	}
}