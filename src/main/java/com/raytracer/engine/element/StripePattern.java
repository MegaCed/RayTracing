package com.raytracer.engine.element;

import com.raytracer.engine.operation.StripePatternOperations;

/*
 * 
 */
public class StripePattern extends Pattern {
	
	/*
	 * Constructor.
	 */
	public StripePattern(Color a, Color b) {
		super(a, b);
		
		// Set the shape's operations to be used
		setOperations(new StripePatternOperations());
	}

}
