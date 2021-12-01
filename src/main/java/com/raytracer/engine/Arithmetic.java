package com.raytracer.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Perform some Operations (comparisons, operations, ...) on number and Tuples.
 */
public class Arithmetic {
	
	private static Logger logger = LoggerFactory.getLogger(Factory.class);

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
	public static Tuple add(Tuple tuple1, Tuple tuple2) {
		Float x = tuple1.getX() + tuple2.getX();
		Float y = tuple1.getY() + tuple2.getY();
		Float z = tuple1.getZ() + tuple2.getZ();
		
		// Adding a Point to a Point doesn't makes sense (W would be equals to 2!)
		Float w = tuple1.getW() + tuple2.getW();
		
		Tuple result = new Tuple(x, y, z, w);
		
		logger.debug("Result of addition: " + result);
		
		return result;
	}
	
	/*
	 * Subtracts 2 Tuples.
	 */
	public static Tuple sub(Tuple tuple1, Tuple tuple2) {
		Float x = tuple1.getX() - tuple2.getX();
		Float y = tuple1.getY() - tuple2.getY();
		Float z = tuple1.getZ() - tuple2.getZ();
		
		// Adding a Point to a Point doesn't makes sense (W would be equals to 2!)
		Float w = tuple1.getW() - tuple2.getW();
		
		Tuple result = new Tuple(x, y, z, w);
		
		logger.debug("Result of subtraction: " + result);
		
		return result;
	}
	
	/*
	 * Negating a Tuple.
	 */
	public static Tuple neg(Tuple aTuple) {
		// TODO
		return null;
	}
	
}
