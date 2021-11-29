package com.raytracer.engine;

/*
 * Perform some Operations (comparison, maths, ...) to number ant Tuples.
 */
public class Operation {

	// Acceptable error
	private static final float EPSILON = 0.00001f;
	
	/*
	 * Test for equality, using Epsilon as acceptable error.
	 */
	public static boolean equalsEpsilon(float value1, float value2) {
		if (Math.abs(value1 - value2) < EPSILON) {
			return true;
		}
		
		return false;
	}
	
	/*
	 * Adds 2 Tuples.
	 */
	public static void add() {
		// TODO
	}
	
}
