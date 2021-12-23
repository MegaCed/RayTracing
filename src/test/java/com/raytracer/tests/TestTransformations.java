package com.raytracer.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Matrix;
import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.misc.Constants;
import com.raytracer.engine.operation.MatrixOperations;
import com.raytracer.engine.operation.TupleOperations;

/*
 * Testing transformations operations.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestTransformations {

	private static Logger logger = LoggerFactory.getLogger(TestTransformations.class);
	
	/*
	 * Translation is a transformation that moves a point.
	 */
	@Test
	@Order(1)
	public void testTranslationMatrix() {
		logger.info(Constants.SEPARATOR_JUNIT + "Translation Matrix");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Get the Translation Matrix
		Matrix translationMatrix = Factory.translationMatrix(5, -3, 2);
		
		// Get a Point
		Tuple aPoint = Factory.point(-3, 4, 5);
		
		// Multiplying by a translation matrix
		Tuple result = MatrixOperations.mul(translationMatrix, aPoint);
		
		assertEquals(2, result.getX(), "Wrong X!");
		assertEquals(1, result.getY(), "Wrong Y!");
		assertEquals(7, result.getZ(), "Wrong Z!");
		
		// If you take the inverse of a translation matrix, you get another translation matrix that 
		// moves points in reverse
		Matrix inverse = MatrixOperations.inverse(translationMatrix);
		
		// Multiplying by the inverse of a translation matrix
		result = MatrixOperations.mul(inverse, aPoint);
		
		assertEquals(-8, result.getX(), "Wrong X!");
		assertEquals(7, result.getY(), "Wrong Y!");
		assertEquals(3, result.getZ(), "Wrong Z!");
		
		// Multiplying a translation matrix by a vector should not change the vector!
		// Remember, a vector is just an arrow. Moving it around in space does not change the 
		// direction it points.
		Tuple aVector = Factory.vector(-3, 4, 5);
		
		// Translation does not affect vectors
		result = MatrixOperations.mul(translationMatrix, aVector);
		
		boolean isEquals = TupleOperations.equals(aVector, result);
		
		assertEquals(true, isEquals, "Vectors are different!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
}
