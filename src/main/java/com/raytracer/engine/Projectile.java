package com.raytracer.engine;

/*
 * A Projectile has:
 * - a position (a point) 
 * - a velocity (a vector)
 */
public class Projectile {
	
	private Tuple position;
	private Tuple velocity;
	
	/*
	 * Constructor.
	 */
	public Projectile(Tuple position, Tuple velocity) {
		this.position = position;
		this.velocity = velocity;
	}

	public Tuple getPosition() {
		return position;
	}

	public void setPosition(Tuple position) {
		this.position = position;
	}

	public Tuple getVelocity() {
		return velocity;
	}

	public void setVelocity(Tuple velocity) {
		this.velocity = velocity;
	}
	
	/*
	 * Prints this Tuple.
	 */
	public String toString() {
		String result = "Projectile (position(" + position + ") - velocity(" + velocity +"))";

		return result;
	}

}
