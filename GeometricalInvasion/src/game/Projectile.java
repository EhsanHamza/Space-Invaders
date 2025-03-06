package game;

import java.awt.Color;
import java.awt.Graphics;

public class Projectile {
	private double x, y;
	private int speed = 10;
	private int size = 5;
	private boolean active = true;
	
	public Projectile(double startX, double startY) {
		this.x = startX;
		this.y = startY;
	}
	
	public void move() {
		// The projectile constantly moves up.
		y -= speed;
		
		// If the position of the projectile is higher than the boundaries, it disappears.
		if (y < 0) {
			active = false;
		}
	}
	
	// Creates the player's projectile itself.
	public void paint(Graphics brush) {
		if (active) {
			brush.setColor(Color.GREEN);
			brush.fillOval((int) x, (int) y, size, size + 10);
		}
	}
	
	// Returns if projectile is active or not.
	public boolean isActive() {
		return active;
	}
	
}
