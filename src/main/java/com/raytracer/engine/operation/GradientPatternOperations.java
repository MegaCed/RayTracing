package com.raytracer.engine.operation;

import com.raytracer.engine.element.Color;
import com.raytracer.engine.element.Pattern;
import com.raytracer.engine.element.Tuple;

/*
 * Operations for Gradient Patterns.
 */
public class GradientPatternOperations extends PatternOperations {

	/*
	 * This is a blending function. This is a function that takes two values and interpolates the 
	 * values between them.
	 * A basic linear interpolation looks like this:
	 * color(p, ca , cb) = ca + (cb − ca) ∗ (px − floor(px))
	 */
	@Override
	public Color patternAt(Pattern aGradient, Tuple aPoint) {
		// This takes the distance between the two colors, multiplies it by the fractional portion 
		// of the x coordinate, and adds the product to the first color.
		Color distance = ColorOperations.sub(aGradient.getB(), aGradient.getA());
		double fraction = aPoint.getX() - Math.floor(aPoint.getX());
		
		// The result is a smooth, linear transition from the first color to the second.
		Color result = ColorOperations.add(aGradient.getA(), ColorOperations.mul(distance, fraction));
		return result;
	}
	
}
