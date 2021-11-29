package com.raytracer.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.raytracer.engine.Factory;
import com.raytracer.engine.Operation;
import com.raytracer.engine.Tuple;

public class Tests {
	
	/*
	 * Dummy test.
	 */
	@Test
	public void testDummy() {
		assertTrue(true);
	}
	
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
		boolean result = Operation.equalsEpsilon(value1, value2);
		
		assertEquals(true, result, "Values are not equals!");
		
		// Create 2 floats with an unacceptable difference
		value1 = 1.0f;
	    value2 = 1.00002f;
		
		// Compare them
		result = Operation.equalsEpsilon(value1, value2);
		
		assertEquals(false, result, "Values are not equals!");
		
	}

}
