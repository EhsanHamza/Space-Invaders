package game;

import java.awt.Graphics;

public abstract class Ship extends Polygon {
	private int health;
	private String name;
	private int damage;
	private int fireRate;
	private Point[] shape;
	protected Point position;
	private double rotation;

	// Constructor for the Ship instance variables.
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
	public abstract void paint(Graphics brush);

	// Getter for ship health
	public int getHealth() {
		return health;
	}

	// Getter for name
	public String getName() {
		return name;
	}

	// Getter for damage
	public int getDamage() {
		return damage;
	}

	// Getter for fireRate
	public int getFireRate() {
		return fireRate;
	}

	// Getter for shape
	public Point[] getShape() {
		return shape;
	}

	// Getter for position
	public Point getPosition() {
		return position;
	}

	// Getter for rotation
	public double getRotation() {
		return rotation;
	}
}
