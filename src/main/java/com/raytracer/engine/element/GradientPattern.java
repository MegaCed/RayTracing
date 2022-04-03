package com.raytracer.engine.element;

import com.raytracer.engine.operation.GradientPatternOperations;

/*
 * A gradient pattern is like stripes, but instead of discrete steps from one color to the next, the 
 * function returns a blend of the two colors, linearly interpolating from one to the other as the x 
 * coordinate changes.
 */
public class GradientPattern extends Pattern {
	
	private static final String NAME = "Gradient Pattern";

	/*
	 * Constructor.
	 */
	public GradientPattern(Color a, Color b) {
		super(a, b);
		
		// Set the shape's operations to be used
		setOperations(new GradientPatternOperations());
		
		setName(NAME);
	}
	
}
