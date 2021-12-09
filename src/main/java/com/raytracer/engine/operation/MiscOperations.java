package com.raytracer.engine.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Perform some Operations (comparisons, operations, ...) on numbers and various elements.
 */
public class MiscOperations {
	
	private static Logger logger = LoggerFactory.getLogger(MiscOperations.class);
	
	// Acceptable error
	private static final float EPSILON = 0.00001f;
	
	/*
	 * Test for equality, using Epsilon as acceptable error.
	 * 
	 * When you need to test two floating point numbers for equivalence, compare their difference. 
	 * If the absolute value of their difference is less than some value (called EPSILON), you can 
	 * consider them equal.
	 */
	// TODO: remove this from this class??
	public static boolean equalsEpsilon(float value1, float value2) {
		logger.debug("Comparing 2 values with an Epsilon of: " + String.format("%f", EPSILON));
		logger.debug("1st value: " + value1);
		logger.debug("2nd value: " + value2);
		
		if (Math.abs(value1 - value2) < EPSILON) {
			return true;
		}
		
		return false;
	}

}
