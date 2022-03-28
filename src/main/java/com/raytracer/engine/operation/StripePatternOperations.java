package com.raytracer.engine.operation;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Color;
import com.raytracer.engine.element.Pattern;
import com.raytracer.engine.element.Tuple;

/*
 * 
 */
public class StripePatternOperations extends PatternOperations {
	
	/*
	 * It takes the given point and returns a new color where the color’s red/green/blue components 
	 * are set to the point’s x/y/z components. You can then use the color to see that the point was 
	 * transformed!
	 */
	@Override
	public Color patternAt(Pattern aPattern, Tuple aPoint) {
		// TODO: Temporary implementation!
//		return Factory.color(aPoint.getX(), aPoint.getY(), aPoint.getZ());
		
		// As the x coordinate changes, the pattern alternates between the two colors. The other two 
		// dimensions, y and z, have no effect on it.
		double x = aPoint.getX();

		// That is to say, if the x coordinate is between 0 and 1, return the first color. If 
		// between 1 and 2, return the second, and so forth, alternating between the two.
		if (Math.floor(x) % 2 == 0) {
			return aPattern.getA();
		} else {
			return aPattern.getB();
		}
	}
	
}
