package com.raytracer.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.raytracer.engine.Factory;
import com.raytracer.engine.Arithmetic;
import com.raytracer.engine.Tuple;

public class Tests {
	
	/*
	 * Test the creation of a new Point.
	 */
	@Test
	public void testIsPoint() {
		// Create a tuple
		Tuple aPoint = Factory.point(1, 1, 1);
		
		assertEquals(1, aPoint.getW(), "W must be equals to 1 for a Point!");
		assertEquals(true, aPoint.isPoint(), "This Tuple is not a Point!");
	}
	
	/*
	 * Test the creation of a new Vector.
	 */
	@Test
	public void testIsVector() {
		// Create a tuple
		Tuple aPoint = Factory.vector(1, 1, 1);
		
		assertEquals(0, aPoint.getW(), "W must be equals to 0 for a Vector!");
		assertEquals(true, aPoint.isVector(), "This Tuple is not a Vector!");
	}
	
	/*
	 * Test if 2 values are in equals (using an Epsilon tolerance).
	 */
	@Test
	public void testEqualsEpsilon() {
		// Create 2 floats with an acceptable difference
		float value1 = 1.0f;
		float value2 = 1.000001f;
		
		// Compare them
		boolean result = Arithmetic.equalsEpsilon(value1, value2);
		
		assertEquals(true, result, "Values are not equals!");
		
		// Create 2 floats with an unacceptable difference
		value1 = 1.0f;
	    value2 = 1.00002f;
		
		// Compare them
		result = Arithmetic.equalsEpsilon(value1, value2);
		
		assertEquals(false, result, "Values are not equals!");
	}
	
	/*
	 * Test the addition of Tuples.
	 */
	@Test
	public void testAddition() {
		// Create a Point
		Tuple aPoint = Factory.point(3, -2, 5);
		
		// Create a Vector
		Tuple aVector = Factory.vector(-2, 3, 1);
		
		// Add them
		Tuple result = Arithmetic.add(aPoint, aVector);
		
		assertEquals(1, result.getX(), "X has wrong value!");
		assertEquals(1, result.getY(), "Y has wrong value!");
		assertEquals(6, result.getZ(), "Z has wrong value!");
		assertEquals(true, result.isPoint(), "Result must be a point!");
	}
	
	/*
	 * Test the subtraction of Tuples.
	 */
	@Test
	public void testSubtraction() {
		// Create a Point
		Tuple aPoint = Factory.point(3, 2, 1);
		
		// Create a Point
		Tuple anotherPoint = Factory.point(5, 6, 7);
		
		// Subtract them
		Tuple result = Arithmetic.sub(aPoint, anotherPoint);
		
		assertEquals(-2, result.getX(), "X has wrong value!");
		assertEquals(-4, result.getY(), "Y has wrong value!");
		assertEquals(-6, result.getZ(), "Z has wrong value!");
		assertEquals(true, result.isVector(), "Result must be a vector!");
		
		// Create a Point
		aPoint = Factory.point(3, 2, 1);
		
		// Create a Vector
		Tuple aVector = Factory.vector(5, 6, 7);
		
		// Subtract a Vector from a Point
		result = Arithmetic.sub(aPoint, aVector);
		
		assertEquals(-2, result.getX(), "X has wrong value!");
		assertEquals(-4, result.getY(), "Y has wrong value!");
		assertEquals(-6, result.getZ(), "Z has wrong value!");
		assertEquals(true, result.isPoint(), "Result must be a point!");
		
		// Create a Vector
		aVector = Factory.vector(3, 2, 1);
		
		// Create another Vector
		Tuple anotherVector = Factory.vector(5, 6, 7);
		
		// Subtract a Vector from a Vector
		result = Arithmetic.sub(aVector, anotherVector);
		
		assertEquals(-2, result.getX(), "X has wrong value!");
		assertEquals(-4, result.getY(), "Y has wrong value!");
		assertEquals(-6, result.getZ(), "Z has wrong value!");
		assertEquals(true, result.isVector(), "Result must be a vector!");
	}
	
	/*
	 * Test the negation of a Tuple.
	 */
	@Test
	public void testNegation() {
		// Create a Tuple
		Tuple aTuple = new Tuple(1, -2, 3, -4);
		
		// Negate this Tuple
		Arithmetic.neg(aTuple);
		
		assertEquals(-1, aTuple.getX(), "X has wrong value!");
		assertEquals(2, aTuple.getY(), "Y has wrong value!");
		assertEquals(-3, aTuple.getZ(), "Z has wrong value!");
		assertEquals(4, aTuple.getW(), "W has wrong value!");
	}
	
	/*
	 * Test the multiplication of a Tuple.
	 */
	@Test
	public void testMultiplication() {
		// Create a Tuple
		Tuple aTuple = new Tuple(1, -2, 3, -4);
		Float scalar = 3.5f;
		
		// Negate this Tuple
		Arithmetic.mul(aTuple, scalar);
		
		assertEquals(3.5f, aTuple.getX(), "X has wrong value!");
		assertEquals(-7f, aTuple.getY(), "Y has wrong value!");
		assertEquals(10.5f, aTuple.getZ(), "Z has wrong value!");
		assertEquals(-14f, aTuple.getW(), "W has wrong value!");
		
		// Create a Tuple
		aTuple = new Tuple(1, -2, 3, -4);
		scalar = 0.5f;
		
		// Negate this Tuple
		Arithmetic.mul(aTuple, scalar);
		
		assertEquals(0.5f, aTuple.getX(), "X has wrong value!");
		assertEquals(-1f, aTuple.getY(), "Y has wrong value!");
		assertEquals(1.5f, aTuple.getZ(), "Z has wrong value!");
		assertEquals(-2f, aTuple.getW(), "W has wrong value!");
	}

}
