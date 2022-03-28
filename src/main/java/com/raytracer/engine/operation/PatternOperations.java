package com.raytracer.engine.operation;

import com.raytracer.engine.element.Color;
import com.raytracer.engine.element.Pattern;
import com.raytracer.engine.element.Shape;
import com.raytracer.engine.element.Tuple;

/*
 * 
 */
public abstract class PatternOperations {

	/*
	 * Function that transforms the point and delegates to the concrete function.
	 */
	public Color patternAtShape(Pattern aPattern, Shape aShape, Tuple worldPoint) {
		// Multiply the given world-space point by the inverse of the object’s transformation 
		// matrix, to convert the point to object space
		Tuple objectPoint = MatrixOperations.mul(MatrixOperations.inverse(aShape.getTransform()), worldPoint);
		
		// Then, multiply the object-space point by the inverse of the pattern’s transformation 
		// matrix to convert that point to pattern space
		Tuple patterntPoint = MatrixOperations.mul(MatrixOperations.inverse(aPattern.getTransform()), objectPoint);
		
		// Call concrete method
		return patternAt(aPattern, patterntPoint);
	}
	
	/*
	 * The function to be implemented by each Pattern.
	 */
	public abstract Color patternAt(Pattern aPattern, Tuple aPoint);
	
}
