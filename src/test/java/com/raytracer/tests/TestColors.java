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
import com.raytracer.engine.element.Material;
import com.raytracer.engine.element.PointLight;
import com.raytracer.engine.element.Tuple;
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
		
		// Create a Color
		Color aColor = new Color(0.2f, 0.3f, 0.4f);
		float scalar = 2f;
		
		// Multiply this Color
		Color result = ColorOperations.mul(aColor, scalar);
		
		// Compare result
		boolean redEquality = MiscOperations.equalsEpsilon(result.getRed(), 0.4f);
		boolean greenEquality = MiscOperations.equalsEpsilon(result.getGreen(), 0.6f);
		boolean blueEquality = MiscOperations.equalsEpsilon(result.getBlue(), 0.8f);
		
		assertEquals(true, redEquality, "Red has wrong value!");
		assertEquals(true, greenEquality, "Green has wrong value!");
		assertEquals(true, blueEquality, "Blue has wrong value!");
		
		// Create a Color
		aColor = Factory.color(1f, 0.2f, 0.4f);
		
		// Create a Color
		Color anotherColor = Factory.color(0.9f, 1f, 0.1f);
		
		// Multiply them
		result = ColorOperations.mul(aColor, anotherColor);
		
		// Compare result
		redEquality = MiscOperations.equalsEpsilon(result.getRed(), 0.9f);
		greenEquality = MiscOperations.equalsEpsilon(result.getGreen(), 0.2f);
		blueEquality = MiscOperations.equalsEpsilon(result.getBlue(), 0.04f);
		
		assertEquals(true, redEquality, "Red has wrong value!");
		assertEquals(true, greenEquality, "Green has wrong value!");
		assertEquals(true, blueEquality, "Blue has wrong value!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Demonstrate the attributes of a point light.
	 */
	@Test
	@Order(5)
	public void testPointLight() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing point light...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Color
		Color intensity = Factory.color(1, 1, 1);
		
		// Create a Point
		Tuple position = Factory.point(0, 0, 0);
		
		// A point light has a position and intensity
		PointLight light = Factory.pointLight(position, intensity);
		
		assertEquals(intensity, light.getIntensity(), "Wrong intensity!");
		assertEquals(position, light.getPosition(), "Wrong position!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	
	/*
	 * A series of tests, which will move the eye and light source around to exercise the lighting 
	 * function in different configurations.
	 * 
	 * For the first test, the eye is positioned directly between the light and the surface, with 
	 * the normal pointing at the eye.
	 * 
	 * In this case, you expect ambient, diffuse, and specular to all be at full strength. 
	 * This means that the total intensity should be 0.1 (the ambient value) + 0.9 (the diffuse 
	 * value) + 0.9 (the specular value), or 1.9.
	 */
	@Test
	@Order(6)
	public void testLighting1() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing lighting (1)...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Common information
		Material material = Factory.material();
		Tuple position = Factory.point(0, 0, 0);
		
		// Lighting with the eye between the light and the surface
		Tuple eyeVector = Factory.vector(0, 0, -1);
		Tuple normalVector = Factory.vector(0, 0, -1);
		PointLight light = Factory.pointLight(Factory.point(0, 0, -10), Factory.color(1, 1, 1));
		Color result = ColorOperations.lithting(material, light, position, eyeVector, normalVector);
		
		Color expectedResult = Factory.color(1.9f, 1.9f, 1.9f);
		assertEquals(expectedResult, result, "Wrong color for this lighting!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * In this next test, the surface and the light remain the same as before, but you’ll move the 
	 * eye to a point 45° off of the normal.
	 * 
	 * Here, the ambient and diffuse components should be unchanged (because the angle between the 
	 * light and normal vectors will not have changed), but the specular value should have fallen 
	 * off to (effectively) 0. Thus, the intensity should be 0.1 + 0.9 + 0, or 1.0.
	 */
	@Test
	@Order(7)
	public void testLighting2() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing lighting (2)...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Common information
		Material material = Factory.material();
		Tuple position = Factory.point(0, 0, 0);
		
		// Lighting with the eye between light and surface, eye offset 45°
		Tuple eyeVector = Factory.vector(0f, (float)Math.sqrt(2) / 2, (float)-Math.sqrt(2) / 2);
		Tuple normalVector = Factory.vector(0, 0, -1);
		PointLight light = Factory.pointLight(Factory.point(0, 0, -10), Factory.color(1, 1, 1));
		Color result = ColorOperations.lithting(material, light, position, eyeVector, normalVector);
		
		Color expectedResult = Factory.color(1, 1, 1);
		assertEquals(expectedResult, result, "Wrong color for this lighting!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Next, the eye is back to being directly opposite the surface, but the light is moved to a 
	 * position 45° off of the normal.
	 * 
	 * Because the angle between the light and normal vectors has changed, the diffuse component 
	 * becomes 0.9 × √2⁄2. The specular component again falls off to 0, so the total intensity 
	 * should be 0.1 + 0.9 × √2⁄2 + 0, or approximately 0.7364.
	 */
	@Test
	@Order(8)
	public void testLighting3() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing lighting (3)...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Common information
		Material material = Factory.material();
		Tuple position = Factory.point(0, 0, 0);
		
		// Lighting with eye opposite surface, light offset 45°
		Tuple eyeVector = Factory.vector(0, 0, -1);
		Tuple normalVector = Factory.vector(0, 0, -1);
		PointLight light = Factory.pointLight(Factory.point(0, 10, -10), Factory.color(1, 1, 1));
		Color result = ColorOperations.lithting(material, light, position, eyeVector, normalVector);
		
		Color expectedResult = Factory.color(0.7364f, 0.7364f, 0.7364f);
		assertEquals(expectedResult, result, "Wrong color for this lighting!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * For this next test, the light and normal vectors are the same as the previous test, but 
	 * you’ll move the eye directly into the path of the reflection vector.
	 * 
	 * This should cause the specular component to be at full strength, with ambient and diffuse the 
	 * same as the previous test. The total intensity should therefore be 0.1 + 0.9 × √2⁄2 + 0.9, or 
	 * approximately 1.6364.
	 */
	@Test
	@Order(9)
	public void testLighting4() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing lighting (4)...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Common information
		Material material = Factory.material();
		Tuple position = Factory.point(0, 0, 0);
		
		// Lighting with eye opposite surface, light offset 45°
		Tuple eyeVector = Factory.vector(0f, (float)-Math.sqrt(2) / 2, (float)-Math.sqrt(2) / 2);
		Tuple normalVector = Factory.vector(0, 0, -1);
		PointLight light = Factory.pointLight(Factory.point(0, 10, -10), Factory.color(1, 1, 1));
		Color result = ColorOperations.lithting(material, light, position, eyeVector, normalVector);
		
		Color expectedResult = Factory.color(1.6364f, 1.6364f, 1.6364f);
		assertEquals(expectedResult, result, "Wrong color for this lighting!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * For the final test, you move the light behind the surface.
	 * 
	 * As the light no longer illuminates the surface, the diffuse and specular components go to 0. 
	 * The total intensity should thus be the same as the ambient component, or 0.1.
	 */
	@Test
	@Order(10)
	public void testLighting5() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing lighting (5)...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Common information
		Material material = Factory.material();
		Tuple position = Factory.point(0, 0, 0);
		
		// Lighting with eye opposite surface, light offset 45°
		Tuple eyeVector = Factory.vector(0, 0, -1);
		Tuple normalVector = Factory.vector(0, 0, -1);
		PointLight light = Factory.pointLight(Factory.point(0, 0, 10), Factory.color(1, 1, 1));
		Color result = ColorOperations.lithting(material, light, position, eyeVector, normalVector);
		
		Color expectedResult = Factory.color(0.1f, 0.1f, 0.1f);
		assertEquals(expectedResult, result, "Wrong color for this lighting!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}

}
