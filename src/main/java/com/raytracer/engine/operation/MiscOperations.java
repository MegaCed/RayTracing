package com.raytracer.engine.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.misc.Constants;

/*
 * Perform some Operations (comparisons, operations, ...) on numbers and various elements.
 */
public class MiscOperations {
	
	private static Logger logger = LoggerFactory.getLogger(MiscOperations.class);
	
	// Acceptable error
	private static final double EPSILON = 0.00001;
	
	/*
	 * Test for equality, using Epsilon as acceptable error.
	 * 
	 * When you need to test two floating point numbers for equivalence, compare their difference. 
	 * If the absolute value of their difference is less than some value (called EPSILON), you can 
	 * consider them equal.
	 */
	public static boolean equalsEpsilon(double value1, double value2) {
		boolean result = false;
		
		if (Math.abs(value1 - value2) < EPSILON) {
			result = true;
		}
		
		logger.debug(Constants.SEPARATOR_OPERATION + "Comparing: " 
				+ String.format(Constants.NUMBER_BIG, value1) + " & " + String.format(Constants.NUMBER_BIG, value2)
				+ " (Epsilon = " + String.format("%f", EPSILON) + ") "
				+ Constants.SEPARATOR_RESULT + result);

		return result;
	}

}
