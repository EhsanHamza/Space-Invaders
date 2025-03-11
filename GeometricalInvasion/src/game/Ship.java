package game;

import java.awt.Graphics;
/**
 * The Ship class serves as an abstract class for all ships in the game,
 * like the player and enemy ships. It extends the Polygon class and 
 * contains attributes such as health, damage, and shape.
 */

public abstract class Ship extends Polygon {
	protected int health;
	private String name;
	private int damage;
	private int fireRate;
	private Point[] shape;
	protected Point position;
	private double rotation;

	// Constructor for the Ship instance variables.
	 /**
     * Constructs a Ship with specified attributes.
     *
     * @param health   The health of the ship.
     * @param name     The name of the ship.
     * @param damage   The damage dealt by the ship.
     * @param fireRate The rate of fire of the ship.
     * @param shape    The shape of the ship represented by an array of points.
     * @param position The position of the ship on the game field.
     * @param rotation The rotation of the ship.
     */
	public Ship(int health, String name, int damage, int fireRate, Point[] shape,
			Point position, double rotation) {
		super(shape, position, rotation);
		this.health = health;
		this.name = name;
		this.damage = damage;
		this.fireRate = fireRate;

		// Checks if position is null or not, if it is, create a new point at 0,0.
		this.position = (position != null) ? position : new Point(0, 0);
	}

	// An abstract method that will help paint the Enemy and Player's ships.
	/**
     * An abstract method that must be implemented by subclasses to define
     * how the ship is drawn on the screen.
     *
     * @param brush Draw all the game elements.
     */
	public abstract void paint(Graphics brush);

	// Getter for ship health
	 /**
     * Gets the current health of the ship.
     *
     * @return The ships health.
     */
	public int getHealth() {
		return health;
	}
	
	// Setter for ship health
	 /**
     * Sets the ship's health.
     *
     * @param health The value the health has been set to.
     */
	public void setHealth(int health) {
		this.health = health;
	}

	// Getter for name
	/**
     * Gets the name of the ship.
     *
     * @return The ship's name.
     */
	public String getName() {
		return name;
	}

	// Getter for damage
	 /**
     * Gets the damage of the ship.
     *
     * @return The ship's damage.
     */
	public int getDamage() {
		return damage;
	}
	
	// Setter for damage
	 /**
     * Sets the damage value of the ship.
     *
     * @param damage The new damage value.
     */
	public void setDamage(int damage) {
		this.damage = damage;
	}

	// Getter for fireRate
	 /**
     * Gets the fire rate of the ship.
     *
     * @return The ship's fire rate.
     */
	public int getFireRate() {
		return fireRate;
	}

	// Getter for shape
	 /**
     * Gets the shape of the ship as an array of points.
     *
     * @return The shape of the ship.
     */
	public Point[] getShape() {
		return shape;
	}

	// Getter for position
	 /**
     * Gets the position of the ship.
     *
     * @return The ship's position.
     */
	public Point getPosition() {
		return position;
	}

	// Getter for rotation
	/**
     * Gets the rotation of the ship.
     *
     * @return The ship's rotation in degrees.
     */
	public double getRotation() {
		return rotation;
	}
}
