package com.raytracer.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Color;
import com.raytracer.engine.ColorOperation;
import com.raytracer.engine.Factory;
import com.raytracer.engine.Tuple;
import com.raytracer.engine.TupleOperation;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestColors {
	
	private static Logger logger = LoggerFactory.getLogger(TestColors.class);
	
	/*
	 * Test the creation of a new Color.
	 */
	@Test
	@Order(1)
	public void testCreateColor() {
		logger.info("-----");
		logger.info("Testing colors...");
		logger.info("-----");
		
		// Create a Color
		Color aColor = Factory.color(-0.5f, 0.4f, 1.7f);
		
		assertEquals(-0.5f, aColor.getRed(), "Wrong red value!");
		assertEquals(0.4f, aColor.getGreen(), "Wrong green value!");
		assertEquals(1.7f, aColor.getBlue(), "Wrong blue value!");		
	}
	
	/*
	 * Test the addition of Colors.
	 */
	@Test
	@Order(2)
	public void testAddition() {
		logger.info("-----");
		logger.info("Testing addition...");
		logger.info("-----");
		
		// Create a Color
		Color aColor = Factory.color(0.9f, 0.6f, 0.75f);
		
		// Create a Color
		Color anotherColor = Factory.color(0.7f, 0.1f, 0.25f);
		
		// Add them
		Color result = ColorOperation.add(aColor, anotherColor);
		
		// Compare them
		boolean redEquality = TupleOperation.equalsEpsilon(result.getRed(), 1.6f);
		boolean greenEquality = TupleOperation.equalsEpsilon(result.getGreen(), 0.7f);
		boolean blueEquality = TupleOperation.equalsEpsilon(result.getBlue(), 1.0f);
		
		assertEquals(true, redEquality, "Red has wrong value!");
		assertEquals(true, greenEquality, "Green has wrong value!");
		assertEquals(true, blueEquality, "Blue has wrong value!");
	}

}
