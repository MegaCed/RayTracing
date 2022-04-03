package com.raytracer.engine.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.element.Color;
import com.raytracer.engine.element.Pattern;
import com.raytracer.engine.element.Shape;
import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.misc.Constants;

/*
 * Operations for Patterns.
 */
public abstract class PatternOperations {
	
	private static Logger logger = LoggerFactory.getLogger(PatternOperations.class);

	/*
	 * Function that transforms the point and delegates to the concrete function.
	 */
	public Color patternAtShape(Pattern aPattern, Shape aShape, Tuple worldPoint) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Retrieving Pattern at Shape for Pattern=" + aPattern + ", Shape=" + aShape + ", and point" + worldPoint);
		
		// Multiply the given world-space point by the inverse of the object’s transformation 
		// matrix, to convert the point to object space
		Tuple objectPoint = MatrixOperations.mul(MatrixOperations.inverse(aShape.getTransform()), worldPoint);
		
		// Then, multiply the object-space point by the inverse of the pattern’s transformation 
		// matrix to convert that point to pattern space
		Tuple patterntPoint = MatrixOperations.mul(MatrixOperations.inverse(aPattern.getTransform()), objectPoint);
		
		// Call concrete method
		Color result = patternAt(aPattern, patterntPoint);
		logger.debug(Constants.SEPARATOR_RESULT + "Pattern at Shape = " + result);
		return result;
	}
	
	/*
	 * The function to be implemented by each Pattern.
	 */
	public abstract Color patternAt(Pattern aPattern, Tuple aPoint);
	
	/*
	 * Default method for testing puropose.
	 */
//	public abstract Color patternAt(Pattern aPattern, Tuple aPoint) {
//		return Factory.color(aPoint.getX(), aPoint.getY(), aPoint.getZ());
//	}
	
}
