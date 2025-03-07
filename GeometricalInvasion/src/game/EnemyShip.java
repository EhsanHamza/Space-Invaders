package game;

import java.awt.Color;
import java.awt.Graphics;

public class EnemyShip extends Ship {
	private int moveSpeed = 5;
	private boolean movingRight = true;

	public EnemyShip(int health, String name, int damage, int fireRate, Point[] shape, Point position,
			double rotation) {
		super(health, name, damage, fireRate, shape, position, rotation);
	}

	public void paint(Graphics brush) {
		int x = (int) position.x;
		int y = (int) position.y;

		int[] xPoints = {x, x + 50, x + 50, x};
		int [] yPoints = {y, y, y + 50, y + 50};
		brush.setColor(Color.RED);
		brush.fillPolygon(xPoints, yPoints, 4);
		
		moveDown();
		moveHorizontal();

	}
	private void moveDown() {
		position.y += moveSpeed;
	}
	
	private void moveHorizontal() {
		if (movingRight) {
			position.x += moveSpeed;
			
			if(position.x > 750) {
				movingRight = false;
			}	
			
		} else {
			position.x -= moveSpeed;
			
			if(position.x < 50) {
				movingRight = true;
			}
		}
	}

}
