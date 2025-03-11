package game;


public interface ProjectileInterface {
	
	// Projectiles will move either up or down, depending if they are from the enemy or player.
	void move();
	
	// Projectiles will check if colliding with anything
	boolean colliding(Polygon polygon);
	
	// Projectile Class will help determine if the projectile itself is active or not.
	boolean isActive();
	
	// Projectile Class will set projectiles to be inactive once out of bounds or hit.
	void setInactive();
}
