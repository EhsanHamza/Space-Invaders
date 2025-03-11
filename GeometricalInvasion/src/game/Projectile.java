package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.function.Supplier;

public class Projectile implements ProjectileInterface {
	private double x, y;
	private int speed = 15;
	private int size = 5;
	private boolean active = true;
	private boolean enemyProjectile;
	
	public Projectile(double x, double y, boolean enemyProjectile) {
		this.x = x;
		this.y = y;
		this.enemyProjectile = enemyProjectile;
		
		// Supplier Integer is an interface, returns an Integer.
		// If enemyProjectile is true, return 5, projectiles move down.
		// If enemyProjectile is false, return -10, projectiles move up.
		this.speed = ((Supplier<Integer>) () -> enemyProjectile ? 5 : -10).get();
	}
	
	public void move() {
		// The projectile supports direction control, depending on the speed value.
		y += speed;
		
		// If the position of the projectile is higher or lower than the set boundaries, disable the projectile.
		if ((enemyProjectile && y > 1200) || (!enemyProjectile && y < 0)) {
			active = false;
		}
	}
	
	// Creates the projectiles itself.
	public void paint(Graphics brush) {
		if (active) {
			brush.setColor(enemyProjectile ? Color.RED : Color.GREEN);
			brush.fillOval((int) x, (int) y, size + 3, size + 10);
		}
	}
	
	// Method for checking if projectiles are colliding with something.
	public boolean colliding(Polygon target) {
		if (target == null) {
			return false;
		}
		
	    return target.contains(new Point(x, y));
	}
	
	// Returns if projectile is active or not.
	public boolean isActive() {
		return active;
	}
	
	// Sets active to be Inactive, disabling the projectile.
	public void setInactive() {
		this.active = false;
	}
	
	
	
}
