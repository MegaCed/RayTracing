package com.raytracer.engine.operation;

import com.raytracer.engine.element.Color;
import com.raytracer.engine.element.Pattern;
import com.raytracer.engine.element.Tuple;

/*
 * Operations for Ring Patterns.
 */
public class RingPatternOperations extends PatternOperations {

	/*
	 * The function for a ring pattern.
	 */
	@Override
	public Color patternAt(Pattern aPattern, Tuple aPoint) {
		double thePoint = Math.sqrt(Math.pow(aPoint.getX(), 2) + Math.pow(aPoint.getZ(), 2));
		
		if (Math.floor(thePoint) % 2 == 0) {
			return aPattern.getA();
		} else {
			return aPattern.getB();
		}
	}
	
}
