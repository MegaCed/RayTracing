package com.raytracer.engine.operation;

import com.raytracer.engine.element.Color;
import com.raytracer.engine.element.Pattern;
import com.raytracer.engine.element.Tuple;

/*
 * Operations for 3d Checker Patterns.
 */
public class CheckerPatternOperations extends PatternOperations {
	
	/*
	 * The function for this pattern is very much like that for stripes, but instead of relying on a 
	 * single dimension, it relies on the sum of all three dimensions, x, y, and z.
	 */
	@Override
	public Color patternAt(Pattern aPattern, Tuple aPoint) {
		double dimensions = Math.floor(aPoint.getX()) + Math.floor(aPoint.getY()) + Math.floor(aPoint.getZ());
		
		if (dimensions % 2 == 0) {
			return aPattern.getA();
		} else {
			return aPattern.getB();
		}
	}
	
}
