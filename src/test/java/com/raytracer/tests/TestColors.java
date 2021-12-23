package com.raytracer.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Color;
import com.raytracer.engine.misc.Constants;
import com.raytracer.engine.operation.ColorOperations;
import com.raytracer.engine.operation.MiscOperations;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestColors {
	
	private static Logger logger = LoggerFactory.getLogger(TestColors.class);
	
	/*
	 * Test the creation of a new Color.
	 */
	@Test
	@Order(1)
	public void testCreateColor() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing Colors...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Color
		Color aColor = Factory.color(-0.5f, 0.4f, 1.7f);
		
		assertEquals(-0.5f, aColor.getRed(), "Wrong red value!");
		assertEquals(0.4f, aColor.getGreen(), "Wrong green value!");
		assertEquals(1.7f, aColor.getBlue(), "Wrong blue value!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Test the addition of Colors.
	 */
	@Test
	@Order(2)
	public void testAddition() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing addition...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Color
		Color aColor = Factory.color(0.9f, 0.6f, 0.75f);
		
		// Create a Color
		Color anotherColor = Factory.color(0.7f, 0.1f, 0.25f);
		
		// Add them
		Color result = ColorOperations.add(aColor, anotherColor);
		
		// Compare result
		boolean redEquality = MiscOperations.equalsEpsilon(result.getRed(), 1.6f);
		boolean greenEquality = MiscOperations.equalsEpsilon(result.getGreen(), 0.7f);
		boolean blueEquality = MiscOperations.equalsEpsilon(result.getBlue(), 1.0f);
		
		assertEquals(true, redEquality, "Red has wrong value!");
		assertEquals(true, greenEquality, "Green has wrong value!");
		assertEquals(true, blueEquality, "Blue has wrong value!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Test the subtraction of Colors.
	 */
	@Test
	@Order(3)
	public void testSubtraction() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing subtraction...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Color
		Color aColor = Factory.color(0.9f, 0.6f, 0.75f);
		
		// Create a Color
		Color anotherColor = Factory.color(0.7f, 0.1f, 0.25f);
		
		// Add them
		Color result = ColorOperations.sub(aColor, anotherColor);
		
		// Compare result
		boolean redEquality = MiscOperations.equalsEpsilon(result.getRed(), 0.2f);
		boolean greenEquality = MiscOperations.equalsEpsilon(result.getGreen(), 0.5f);
		boolean blueEquality = MiscOperations.equalsEpsilon(result.getBlue(), 0.5f);
		
		assertEquals(true, redEquality, "Red has wrong value!");
		assertEquals(true, greenEquality, "Green has wrong value!");
		assertEquals(true, blueEquality, "Blue has wrong value!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Test the multiplication of a Color.
	 */
	@Test
	@Order(4)
	public void testMultiplication() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing multiplication...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Tuple
		Color aColor = new Color(0.2f, 0.3f, 0.4f);
		float scalar = 2f;
		
		// Multiply this Color
		ColorOperations.mul(aColor, scalar);
		
		// Compare result
		boolean redEquality = MiscOperations.equalsEpsilon(aColor.getRed(), 0.4f);
		boolean greenEquality = MiscOperations.equalsEpsilon(aColor.getGreen(), 0.6f);
		boolean blueEquality = MiscOperations.equalsEpsilon(aColor.getBlue(), 0.8f);
		
		assertEquals(true, redEquality, "Red has wrong value!");
		assertEquals(true, greenEquality, "Green has wrong value!");
		assertEquals(true, blueEquality, "Blue has wrong value!");
		
		// Create a Color
		aColor = Factory.color(1f, 0.2f, 0.4f);
		
		// Create a Color
		Color anotherColor = Factory.color(0.9f, 1f, 0.1f);
		
		// Multiply them
		Color result = ColorOperations.mul(aColor, anotherColor);
		
		// Compare result
		redEquality = MiscOperations.equalsEpsilon(result.getRed(), 0.9f);
		greenEquality = MiscOperations.equalsEpsilon(result.getGreen(), 0.2f);
		blueEquality = MiscOperations.equalsEpsilon(result.getBlue(), 0.04f);
		
		assertEquals(true, redEquality, "Red has wrong value!");
		assertEquals(true, greenEquality, "Green has wrong value!");
		assertEquals(true, blueEquality, "Blue has wrong value!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}

}
