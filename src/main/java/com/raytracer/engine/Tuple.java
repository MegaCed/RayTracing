package com.raytracer.engine;

/*
 * A Tuple can represent either:
 * - a point
 * - a vector
 */
public class Tuple {

	// X coordinate
	private float x;
	
	// Y coordinate
	private float y;
	
	// Z coordinate
	private float z;
	
	// Used to differentiate points from vectors
	// W = 0 -> Vector
	// W = 1 -> Point
	private float w;

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}
	
	/*
	 * Constructor.
	 */
	public Tuple(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	/*
	 * Tells if the Tuple is a point.
	 */
	public boolean isPoint() {
		return w != 0;
	}
	
	/*
	 * Tells if the Tuple is a vector.
	 */
	public boolean isVector() {
		return w == 0;
	}
	
	/*
	 * Prints this Tuple.
	 */

	public String toString() {
		String type = isPoint() ? "point" : "vector";
		String result = "Tuple(x=" + x + ", y=" + y + ", z=" + z + ", w=" + w + ") -> " + type;
		
		return result;
	}
	
	
}
