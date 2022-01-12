package com.raytracer.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.misc.Constants;
import com.raytracer.engine.operation.MiscOperations;
import com.raytracer.engine.operation.TupleOperations;

import org.junit.jupiter.api.MethodOrderer;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestTuples {
	
	private static Logger logger = LoggerFactory.getLogger(TestTuples.class);
	
	/*
	 * Test the creation of a new Point.
	 */
	@Test
	@Order(1)
	public void testIsPoint() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing points...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a tuple
		Tuple aPoint = Factory.point(1, 1, 1);
		
		assertEquals(1, aPoint.getW(), "W must be equals to 1 for a Point!");
		assertEquals(true, aPoint.isPoint(), "This Tuple is not a Point!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Test the creation of a new Vector.
	 */
	@Test
	@Order(2)
	public void testIsVector() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing vectors...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a tuple
		Tuple aPoint = Factory.vector(1, 1, 1);
		
		assertEquals(0, aPoint.getW(), "W must be equals to 0 for a Vector!");
		assertEquals(true, aPoint.isVector(), "This Tuple is not a Vector!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Test if 2 values are in equals (using an Epsilon tolerance).
	 */
	@Test
	@Order(3)
	public void testEqualsEpsilon() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing equality...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create 2 floats with an acceptable difference
		float value1 = 1.0f;
		float value2 = 1.000001f;
		
		// Compare them
		boolean result = MiscOperations.equalsEpsilon(value1, value2);
		
		assertEquals(true, result, "Values are not equals!");
		
		// Create 2 floats with an unacceptable difference
		value1 = 1.0f;
	    value2 = 1.00002f;
		
		// Compare them
		result = MiscOperations.equalsEpsilon(value1, value2);
		
		assertEquals(false, result, "Values are not equals!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Test the addition of Tuples.
	 */
	@Test
	@Order(4)
	public void testAddition() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing addition...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Point
		Tuple aPoint = Factory.point(3, -2, 5);
		
		// Create a Vector
		Tuple aVector = Factory.vector(-2, 3, 1);
		
		// Add them
		Tuple result = TupleOperations.add(aPoint, aVector);
		
		assertEquals(1, result.getX(), "X has wrong value!");
		assertEquals(1, result.getY(), "Y has wrong value!");
		assertEquals(6, result.getZ(), "Z has wrong value!");
		assertEquals(true, result.isPoint(), "Result must be a point!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Test the subtraction of Tuples.
	 */
	@Test
	@Order(5)
	public void testSubtraction() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing subtraction...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Point
		Tuple aPoint = Factory.point(3, 2, 1);
		
		// Create a Point
		Tuple anotherPoint = Factory.point(5, 6, 7);
		
		// Subtract them
		Tuple result = TupleOperations.sub(aPoint, anotherPoint);
		
		assertEquals(-2, result.getX(), "X has wrong value!");
		assertEquals(-4, result.getY(), "Y has wrong value!");
		assertEquals(-6, result.getZ(), "Z has wrong value!");
		assertEquals(true, result.isVector(), "Result must be a vector!");
		
		// Create a Point
		aPoint = Factory.point(3, 2, 1);
		
		// Create a Vector
		Tuple aVector = Factory.vector(5, 6, 7);
		
		// Subtract a Vector from a Point
		result = TupleOperations.sub(aPoint, aVector);
		
		assertEquals(-2, result.getX(), "X has wrong value!");
		assertEquals(-4, result.getY(), "Y has wrong value!");
		assertEquals(-6, result.getZ(), "Z has wrong value!");
		assertEquals(true, result.isPoint(), "Result must be a point!");
		
		// Create a Vector
		aVector = Factory.vector(3, 2, 1);
		
		// Create another Vector
		Tuple anotherVector = Factory.vector(5, 6, 7);
		
		// Subtract a Vector from a Vector
		result = TupleOperations.sub(aVector, anotherVector);
		
		assertEquals(-2, result.getX(), "X has wrong value!");
		assertEquals(-4, result.getY(), "Y has wrong value!");
		assertEquals(-6, result.getZ(), "Z has wrong value!");
		assertEquals(true, result.isVector(), "Result must be a vector!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Test the negation of a Tuple.
	 */
	@Test
	@Order(6)
	public void testNegation() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing negation...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Tuple
		Tuple aTuple = new Tuple(1, -2, 3, -4);
		
		// Negate this Tuple
		Tuple result = TupleOperations.neg(aTuple);
		
		assertEquals(-1, result.getX(), "X has wrong value!");
		assertEquals(2, result.getY(), "Y has wrong value!");
		assertEquals(-3, result.getZ(), "Z has wrong value!");
		assertEquals(4, result.getW(), "W has wrong value!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Test the multiplication of a Tuple.
	 */
	@Test
	@Order(7)
	public void testMultiplication() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing multiplication...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Tuple
		Tuple aTuple = new Tuple(1, -2, 3, -4);
		float scalar = 3.5f;
		
		// Multiply this Tuple
		Tuple result = TupleOperations.mul(aTuple, scalar);
		
		assertEquals(3.5f, result.getX(), "X has wrong value!");
		assertEquals(-7f, result.getY(), "Y has wrong value!");
		assertEquals(10.5f, result.getZ(), "Z has wrong value!");
		assertEquals(-14f, result.getW(), "W has wrong value!");
		
		// Create a Tuple
		aTuple = new Tuple(1, -2, 3, -4);
		scalar = 0.5f;
		
		// Multiply this Tuple
		result = TupleOperations.mul(aTuple, scalar);
		
		assertEquals(0.5f, result.getX(), "X has wrong value!");
		assertEquals(-1f, result.getY(), "Y has wrong value!");
		assertEquals(1.5f, result.getZ(), "Z has wrong value!");
		assertEquals(-2f, result.getW(), "W has wrong value!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Test the division of a Tuple.
	 */
	@Test
	@Order(8)
	public void testDivision() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing division...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Tuple
		Tuple aTuple = new Tuple(1, -2, 3, -4);
		float scalar = 2f;
		
		// Divide this Tuple
		Tuple result = TupleOperations.div(aTuple, scalar);
		
		assertEquals(0.5f, result.getX(), "X has wrong value!");
		assertEquals(-1f, result.getY(), "Y has wrong value!");
		assertEquals(1.5f, result.getZ(), "Z has wrong value!");
		assertEquals(-2f, result.getW(), "W has wrong value!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Test the magnitude of a Tuple.
	 */
	@Test
	@Order(9)
	public void testMagnitude() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing magnitude...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Vector
		Tuple aVector = Factory.vector(1, 0, 0);
		
		// Get the magnitude of this Vector
		float result = TupleOperations.magnitude(aVector);
		
		assertEquals(1f, result, "Wrong magnitude for the Vector!");
		
		// Create a Vector
		aVector = Factory.vector(0, 1, 0);
		
		// Get the magnitude of this Vector
		result = TupleOperations.magnitude(aVector);
		
		assertEquals(1f, result, "Wrong magnitude for the Vector!");
		
		// Create a Vector
		aVector = Factory.vector(0, 0, 1);
		
		// Get the magnitude of this Vector
		result = TupleOperations.magnitude(aVector);
		
		assertEquals(1f, result, "Wrong magnitude for the Vector!");
		
		// Create a Vector
		aVector = Factory.vector(1, 2, 3);
		
		// Get the magnitude of this Vector
		result = TupleOperations.magnitude(aVector);
		
		// Compare the result (using Epsilon)
		boolean equalsEpsilon = MiscOperations.equalsEpsilon(result, (float)Math.sqrt(14));
		assertEquals(true, equalsEpsilon, "Wrong magnitude for the Vector!");
		
		// Create a Vector
		aVector = Factory.vector(-1, -2, -3);
		
		// Get the magnitude of this Vector
		result = TupleOperations.magnitude(aVector);
		
		// Compare the result (using Epsilon)
		equalsEpsilon = MiscOperations.equalsEpsilon(result, (float)Math.sqrt(14));
		
		assertEquals(true, equalsEpsilon, "Wrong magnitude for the Vector!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Test the normalization of a Tuple.
	 */
	@Test
	@Order(10)
	public void testNormalization() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing normalization...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Vector
		Tuple initialVector = Factory.vector(4, 0, 0);
		
		// Normalize this Vector
		Tuple aVector = TupleOperations.normalize(initialVector);
		
		assertEquals(1f, aVector.getX(), "X has wrong value!");
		assertEquals(0, aVector.getY(), "Y has wrong value!");
		assertEquals(0, aVector.getZ(), "Z has wrong value!");
		
		// Create a Vector
		aVector = Factory.vector(1, 2, 3);
		
		// Normalize this Vector
		aVector = TupleOperations.normalize(aVector);
		
		// Compare the result (using Epsilon)
		boolean xEpsilon = MiscOperations.equalsEpsilon(aVector.getX(), 1 / (float)Math.sqrt(14));
		boolean yEpsilon = MiscOperations.equalsEpsilon(aVector.getY(), 2 / (float)Math.sqrt(14));
		boolean zEpsilon = MiscOperations.equalsEpsilon(aVector.getZ(), 3 / (float)Math.sqrt(14));
		
		assertEquals(true, xEpsilon, "X has wrong value!");
		assertEquals(true, yEpsilon, "Y has wrong value!");
		assertEquals(true, zEpsilon, "Z has wrong value!");
		
		// Confirm that the length of a normalized vector is 1
		
		// Create a Vector
		aVector = Factory.vector(1, 2, 3);
		
		// Normalize this Vector
		aVector = TupleOperations.normalize(aVector);
		
		// Get the magnitude of this Vector
		float magnitude = TupleOperations.magnitude(aVector);
		
		// Compare the result (using Epsilon)
		boolean magnitudeEpsilon = MiscOperations.equalsEpsilon(magnitude, 1);
		
		assertEquals(true, magnitudeEpsilon, "Wrong magnitude for the normalized Vector!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Test the dot product of 2 vectors.
	 */
	@Test
	@Order(11)
	public void testDotProduct() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing dot product...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Vector
		Tuple vector1 = Factory.vector(1, 2, 3);
		Tuple vector2 = Factory.vector(2, 3, 4);
		
		// Get dot product
		float result = TupleOperations.dot(vector1, vector2);
		
		assertEquals(20, result, "Wrong dot product!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Test the cross product of 2 vectors.
	 */
	@Test
	@Order(12)
	public void testCrosstProduct() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing cross product...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create 2 Vectors
		Tuple vector1 = Factory.vector(1, 2, 3);
		Tuple vector2 = Factory.vector(2, 3, 4);
		
		// Get cross product
		Tuple result = TupleOperations.cross(vector1, vector2);
		
		assertEquals(-1, result.getX(), "X has wrong value!");
		assertEquals(2, result.getY(), "Y has wrong value!");
		assertEquals(-1, result.getZ(), "Z has wrong value!");
		
		// Note that if you change the order of the operands, you change the direction of the 
		// resulting vector. Keep this in mind as you use the cross product: order matters!
		
		// Get cross product
		result = TupleOperations.cross(vector2, vector1);
		
		assertEquals(1, result.getX(), "X has wrong value!");
		assertEquals(-2, result.getY(), "Y has wrong value!");
		assertEquals(1, result.getZ(), "Z has wrong value!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}

}
