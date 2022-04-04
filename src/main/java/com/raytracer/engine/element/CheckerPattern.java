package com.raytracer.engine.element;

import com.raytracer.engine.operation.CheckerPatternOperations;

/*
 * This three-dimensional checker pattern is the one you’ll implement here.
 * You get a pattern of alternating cubes, where two cubes of the same color are never adjacent.
 */
public class CheckerPattern extends Pattern {
	
	private static final String NAME = "3d Checker Pattern";
	
	/*
	 * Constructor.
	 */
	public CheckerPattern(Color a, Color b) {
		super(a, b);
		
		// Set the shape's operations to be used
		setOperations(new CheckerPatternOperations());
		
		setName(NAME);
	}
	
}
