package com.raytracer.engine.element;

import com.raytracer.engine.operation.MiscOperations;

/*
 * A tuple is just an ordered list of things, like numbers.
 * 
 * A Tuple can represent either:
 * - a point
 * - a vector
 */
public class Tuple {

	// X coordinate
	private double x;
	
	// Y coordinate
	private double y;
	
	// Z coordinate
	private double z;
	
	// Used to differentiate points from vectors
	// W = 0 -> Vector
	// W = 1 -> Point
	private double w;

	/*
	 * Constructor.
	 */
	public Tuple(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	/*
	 * Constructor.
	 * Creates a Tuple from an array.
	 */
	public Tuple(double[][] elements) {
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
	@Override
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
	 * A Tuple can be seen as an array of 4 doubles.
	 * Threat the tuple as a really skinny (one column!) matrix.
	 * Four rows. One column.
	 */
	public double[][] asArray() {
		double[][] array = new double[4][1];
		
		array[0][0] = x;
		array[1][0] = y;
		array[2][0] = z;
		array[3][0] = w;
		
		return array;
	}
	
	/*
	 * Test the equality of 2 Tuples.
	 */
	@Override
	public boolean equals(Object anObject) {
		if (this == anObject) {
			return true;
		}
		
		if (anObject instanceof Tuple) {
			Tuple anotherTuple = (Tuple)anObject;
			
			if (MiscOperations.equalsEpsilon(getX(), anotherTuple.getX())
					&& MiscOperations.equalsEpsilon(getY(), anotherTuple.getY())
					&& MiscOperations.equalsEpsilon(getZ(), anotherTuple.getZ())
					&& MiscOperations.equalsEpsilon(getW(), anotherTuple.getW())) {
				return true;
			}			
		}
		
		return false;	
	}
	
	@Override
	public final int hashCode() {
		final int prime = 31;
		
	    int result = (int) (getX() + getY() + getZ() + getW()) * prime;
	    
	    return result;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}
	
}
