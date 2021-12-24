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
	
	/*
	 * Where translation moves a point by adding to it, scaling moves it by multiplication.
	 */
	@Test
	@Order(2)
	public void testScalingMatrix() {
		logger.info(Constants.SEPARATOR_JUNIT + "Scaling Matrix");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Get the Translation Matrix
		Matrix scalingMatrix = Factory.scalingMatrix(2, 3, 4);
		
		// Get a Point
		Tuple aPoint = Factory.point(-4, 6, 8);
		
		// A scaling matrix applied to a point
		Tuple result = MatrixOperations.mul(scalingMatrix, aPoint);
		
		assertEquals(-8, result.getX(), "Wrong X!");
		assertEquals(18, result.getY(), "Wrong Y!");
		assertEquals(32, result.getZ(), "Wrong Z!");
		
		// Get a Vector
		Tuple aVector = Factory.vector(-4, 6, 8);
		
		// Unlike translation, scaling applies to vectors as well, changing their length
		result = MatrixOperations.mul(scalingMatrix, aVector);
		
		assertEquals(-8, result.getX(), "Wrong X!");
		assertEquals(18, result.getY(), "Wrong Y!");
		assertEquals(32, result.getZ(), "Wrong Z!");
		
		// Get the inverse of a scaling matrix
		Matrix inverse = MatrixOperations.inverse(scalingMatrix);
		
		// Multiplying a tuple by the inverse of a scaling matrix will scale the tuple in the 
		// opposite way (shrinking instead of growing, or vice versa)
		result = MatrixOperations.mul(inverse, aVector);
		
		assertEquals(-2, result.getX(), "Wrong X!");
		assertEquals(2, result.getY(), "Wrong Y!");
		assertEquals(2, result.getZ(), "Wrong Z!");
	}
	
	/*
	 * Reflection is scaling by a negative value.
	 * 
	 * Reflection is a transformation that takes a point and reflects it — moving it to the other 
	 * side of an axis. It can be useful when you have an object in your scene that you want to flip 
	 * (or mirror) in some direction.
	 */
	@Test
	@Order(3)
	public void testReflection() {
		logger.info(Constants.SEPARATOR_JUNIT + "Scaling Matrix for reflection");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Get the Translation Matrix
		Matrix scalingMatrix = Factory.scalingMatrix(-1, 1, 1);
		
		// Get a Point
		Tuple aPoint = Factory.point(2, 3, 4);
		
		// Reflection is essentially the same thing as scaling by a negative value
		Tuple result = MatrixOperations.mul(scalingMatrix, aPoint);
		
		// Just like that, the point was moved from the positive side of the x axis, to the negative
		assertEquals(-2, result.getX(), "Wrong X!");
		assertEquals(3, result.getY(), "Wrong Y!");
		assertEquals(4, result.getZ(), "Wrong Z!");
	}
	
}
