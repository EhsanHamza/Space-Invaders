package game;

public interface ProjectileInterface {
	/**
	 * The Projectile Interface defines what the projectiles in the game does.
	 * Projectiles can move, check for collisions, and determine their active state.
	 */

	// Projectiles will move either up or down, depending if they are from the enemy or player.
	/**
     * Moves the projectile in a direction (up or down) based on its type.
     */
		void move();
		
		// Projectiles will check if colliding with anything
		/**
	     * Checks if the projectile is colliding with a given polygon (target).
	     *
	     * @param polygon The polygon to check for collision.
	     * @return true if the projectile is colliding with the polygon, else false.
	     */
		boolean colliding(Polygon polygon);
		
		// Projectile Class will help determine if the projectile itself is active or not.
		 /**
	     * Determines if the projectile is currently active.
	     *
	     * @return true if the projectile is active, else false.
	     */
		boolean isActive();
		
		// Projectile Class will set projectiles to be inactive once out of bounds or hit.
		/**
	     * Sets the projectile to inactive when it moves out of bounds or hits a target.
	     */
		void setInactive();
	}


