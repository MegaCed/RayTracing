package com.raytracer.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.element.Color;
import com.raytracer.engine.element.Tuple;

/*
 * Creates objects used by the application.
 */
public class Factory {

	private static Logger logger = LoggerFactory.getLogger(Factory.class);
	
	// TODO: make it a Singleton!?
	
	/*
	 * Creates a Point (a Tuple with w=1).
	 */
	public static Tuple point(float x, float y, float z) {
		Tuple aPoint =  new Tuple(x, y, z, 1);
		
		logger.debug("Creating new Tuple: " + aPoint);
		
		return aPoint;
	}
	
	/*
	 * Creates a Vector (a Tuple with w=0).
	 */
	public static Tuple vector(float x, float y, float z) {
		Tuple aVector = new Tuple(x, y, z, 0);
		
		logger.debug("Creating new Tuple: " + aVector);
		
		return aVector;
	}
	
	/*
	 * Created a Color.
	 */
	public static Color color(float red, float green, float blue) {
		Color aColor = new Color(red, green, blue);
		
		logger.debug("Creating new Color: " + aColor);
		
		return aColor;
	}
		
}
