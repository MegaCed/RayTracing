package com.raytracer.engine.element;

import com.raytracer.engine.operation.StripePatternOperations;

/*
 * Stripe Pattern.
 */
public class StripePattern extends Pattern {
	
	private static final String NAME = "Stripe Pattern";
	
	/*
	 * Constructor.
	 */
	public StripePattern(Color a, Color b) {
		super(a, b);
		
		// Set the shape's operations to be used
		setOperations(new StripePatternOperations());
		
		setName(NAME);
	}

}
