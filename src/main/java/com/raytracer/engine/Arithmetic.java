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
		float x = tuple1.getX() + tuple2.getX();
		float y = tuple1.getY() + tuple2.getY();
		float z = tuple1.getZ() + tuple2.getZ();
		
		// Adding a Point to a Point doesn't makes sense (W would be equals to 2!)
		float w = tuple1.getW() + tuple2.getW();
		
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
		float x = tuple1.getX() - tuple2.getX();
		float y = tuple1.getY() - tuple2.getY();
		float z = tuple1.getZ() - tuple2.getZ();
		
		// Adding a Point to a Point doesn't makes sense (W would be equals to 2!)
		float w = tuple1.getW() - tuple2.getW();
		
		Tuple result = new Tuple(x, y, z, w);
		
		logger.debug("Result of subtraction: " + result);
		
		return result;
	}
	
	/*
	 * Negating a Tuple.
	 * To retrieve the opposite of a vector...
	 */
	public static Tuple neg(Tuple aTuple) {
		logger.debug("Negating Tuple: " + aTuple);
		
		float x = aTuple.getX() * -1;
		float y = aTuple.getY() * -1;
		float z = aTuple.getZ() * -1;
		float w = aTuple.getW() * -1;
		
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
		float x = aTuple.getX() * scalar;
		float y = aTuple.getY() * scalar;
		float z = aTuple.getZ() * scalar;
		float w = aTuple.getW() * scalar;
		
		aTuple.setX(x);
		aTuple.setY(y);
		aTuple.setZ(z);
		aTuple.setW(w);
		
		logger.debug("Multiply Tuple: " + aTuple + " by " + scalar);
		logger.debug("Result of multiplication: " + aTuple);
		
		return aTuple;
	}
	
	/*
	 * Divide a Tuple by a scalar.
	 */
	public static Tuple div(Tuple aTuple, Float scalar) {
		float x = aTuple.getX() / scalar;
		float y = aTuple.getY() / scalar;
		float z = aTuple.getZ() / scalar;
		float w = aTuple.getW() / scalar;
		
		aTuple.setX(x);
		aTuple.setY(y);
		aTuple.setZ(z);
		aTuple.setW(w);
		
		logger.debug("Dividing Tuple: " + aTuple + " by " + scalar);
		logger.debug("Result of division: " + aTuple);
		
		return aTuple;
	}
	
	/*
	 * Retrieve the magnitude of a Vector.
	 * (The distance from one end to another.)
	 */
	public static float magnitude(Tuple aVector) {
		float x2 = (float) Math.pow(aVector.getX(), 2);
		float y2 = (float) Math.pow(aVector.getY(), 2);
		float z2 = (float) Math.pow(aVector.getZ(), 2);
		float w2 = (float) Math.pow(aVector.getW(), 2);
		
		float result = (float) Math.sqrt(x2+y2+z2+w2);
		
		logger.debug("Magnitude of the vector: " + aVector  + " = " + result);
				
		return result;
	}
	
	/*
	 * Normalize a Vector.
	 * You normalize a Tuple by dividing each of its components by its magnitude.
	 * 
	 */
	public static Tuple normalize(Tuple aTuple) {
		// Retrieve the magnitude of the Tuple
		float magnitude = magnitude(aTuple);
		
		float x = aTuple.getX() / magnitude;
		float y = aTuple.getY() / magnitude;
		float z = aTuple.getZ() / magnitude;
		float w = aTuple.getW() / magnitude;
		
		Tuple result = new Tuple(x, y, z, w);
		
		logger.debug("Normalizing Tuple: " + aTuple);
		logger.debug("Result of normalization: " + result);
		
		return result;
	}
	
}
