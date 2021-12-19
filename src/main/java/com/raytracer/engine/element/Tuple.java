package com.raytracer.engine.element;

/*
 * A tuple is just an ordered list of things, like numbers.
 * 
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
	 * Constructor.
	 * Creates a Tuple from an array.
	 */
	public Tuple(float[][] elements) {
		this.x = elements[0][0];
		this.y = elements[1][0];
		this.z = elements[2][0];
		this.w = elements[3][0];
	}
	
	/*
	 * Tells if the Tuple is a point.
	 */
	public boolean isPoint() {
		return w == 1;
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
		String type = null;
		
		if (isPoint()) {
			type = "Point";
		} else if (isVector()) {
			type = "Vector";
		} else {
			type = "Tuple";
		}
		
		String result = type + " (x=" + x + ", y=" + y + ", z=" + z + ", w=" + w + ")";
		return result;
	}
	
	/*
	 * Returns this Tuple as an array.
	 * A Tuple can be seen as an array of 4 floats.
	 * Threat the tuple as a really skinny (one column!) matrix.
	 */
	public float[][] asArray() {
		float[][] array = new float[4][1];
		
		array[0][0] = x;
		array[1][0] = y;
		array[2][0] = z;
		array[3][0] = w;
		
		return array;
	}
	
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
	
}
