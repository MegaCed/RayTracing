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
	 * Point - Point = Vector (w=0)
	 * Point - Vector = Point (w=1)
	 * Vector - Vector = Vector (w=0)
	 * Vector - Point = nonsense!
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
	 * To retrieve the opposite of a vector...
	 */
	public static Tuple neg(Tuple aTuple) {
		Float x = aTuple.getX() * -1;
		Float y = aTuple.getY() * -1;
		Float z = aTuple.getZ() * -1;
		Float w = aTuple.getW() * -1;
		
		aTuple.setX(x);
		aTuple.setY(y);
		aTuple.setZ(z);
		aTuple.setW(w);
		
		logger.debug("Result of negation: " + aTuple);
		
		return aTuple;
	}
	
	/*
	 * Multiply a Tuple by a scalar (or a fraction).
	 */
	public static Tuple mul(Tuple aTuple, Float scalar) {
		Float x = aTuple.getX() * scalar;
		Float y = aTuple.getY() * scalar;
		Float z = aTuple.getZ() * scalar;
		Float w = aTuple.getW() * scalar;
		
		aTuple.setX(x);
		aTuple.setY(y);
		aTuple.setZ(z);
		aTuple.setW(w);
		
		logger.debug("Result of multiplication: " + aTuple);
		
		return aTuple;
	}
	
}
