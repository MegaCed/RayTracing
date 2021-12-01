package com.raytracer.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Creates objects used by the application.
 */
public class Factory {

	private static Logger logger = LoggerFactory.getLogger(Factory.class);
	
	/*
	 * Creates a Point (a Tuple with w=1).
	 */
	public static Tuple point(float x, float y, float z) {
		Tuple aTuple =  new Tuple(x, y, z, 1);
		
		logger.debug("Creating new Point: " + aTuple);
		
		return aTuple;
	}
	
	/*
	 *  Creates a Vector (a Tuple with w=0).
	 */
	public static Tuple vector(float x, float y, float z) {
		Tuple aTuple = new Tuple(x, y, z, 0);
		
		logger.debug("Creating new Vector: " + aTuple);
		
		return aTuple;
	}
		
}
