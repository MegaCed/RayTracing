package com.raytracer.engine.element;

import com.raytracer.engine.operation.RingPatternOperations;

/*
 * A ring pattern depends on two dimensions, x and z, to decide which color to return. 
 * It works similarly to stripes, but instead of testing the distance of the point in just x, it 
 * tests the distance of the point in both x and z, which results in this pattern of concentric 
 * circles.
 */
public class RingPattern extends Pattern {
	
	private static final String NAME = "Ring Pattern";
	
	/*
	 * Constructor.
	 */
	public RingPattern(Color a, Color b) {
		super(a, b);
		
		// Set the shape's operations to be used
		setOperations(new RingPatternOperations());
		
		setName(NAME);
	}

}
