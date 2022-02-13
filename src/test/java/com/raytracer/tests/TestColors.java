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
		Color aColor = Factory.color(-0.5, 0.4, 1.7);
		
		assertEquals(-0.5, aColor.getRed(), "Wrong red value!");
		assertEquals(0.4, aColor.getGreen(), "Wrong green value!");
		assertEquals(1.7, aColor.getBlue(), "Wrong blue value!");
		
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
		Color aColor = Factory.color(0.9, 0.6, 0.75);
		
		// Create a Color
		Color anotherColor = Factory.color(0.7, 0.1, 0.25);
		
		// Add them
		Color result = ColorOperations.add(aColor, anotherColor);
		
		// Compare result
		boolean redEquality = MiscOperations.equalsEpsilon(result.getRed(), 1.6);
		boolean greenEquality = MiscOperations.equalsEpsilon(result.getGreen(), 0.7);
		boolean blueEquality = MiscOperations.equalsEpsilon(result.getBlue(), 1.0);
		
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
		Color aColor = Factory.color(0.9, 0.6, 0.75);
		
		// Create a Color
		Color anotherColor = Factory.color(0.7, 0.1, 0.25);
		
		// Add them
		Color result = ColorOperations.sub(aColor, anotherColor);
		
		// Compare result
		boolean redEquality = MiscOperations.equalsEpsilon(result.getRed(), 0.2);
		boolean greenEquality = MiscOperations.equalsEpsilon(result.getGreen(), 0.5);
		boolean blueEquality = MiscOperations.equalsEpsilon(result.getBlue(), 0.5);
		
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
		Color aColor = new Color(0.2, 0.3, 0.4);
		double scalar = 2;
		
		// Multiply this Color
		Color result = ColorOperations.mul(aColor, scalar);
		
		// Compare result
		boolean redEquality = MiscOperations.equalsEpsilon(result.getRed(), 0.4);
		boolean greenEquality = MiscOperations.equalsEpsilon(result.getGreen(), 0.6);
		boolean blueEquality = MiscOperations.equalsEpsilon(result.getBlue(), 0.8);
		
		assertEquals(true, redEquality, "Red has wrong value!");
		assertEquals(true, greenEquality, "Green has wrong value!");
		assertEquals(true, blueEquality, "Blue has wrong value!");
		
		// Create a Color
		aColor = Factory.color(1, 0.2, 0.4);
		
		// Create a Color
		Color anotherColor = Factory.color(0.9, 1, 0.1);
		
		// Multiply them
		result = ColorOperations.mul(aColor, anotherColor);
		
		// Compare result
		redEquality = MiscOperations.equalsEpsilon(result.getRed(), 0.9);
		greenEquality = MiscOperations.equalsEpsilon(result.getGreen(), 0.2);
		blueEquality = MiscOperations.equalsEpsilon(result.getBlue(), 0.04);
		
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
		boolean inShadow = false;
		Color result = ColorOperations.lithting(material, light, position, eyeVector, normalVector, inShadow);
		
		Color expectedResult = Factory.color(1.9, 1.9, 1.9);
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
		Tuple eyeVector = Factory.vector(0, Math.sqrt(2) / 2, -Math.sqrt(2) / 2);
		Tuple normalVector = Factory.vector(0, 0, -1);
		PointLight light = Factory.pointLight(Factory.point(0, 0, -10), Factory.color(1, 1, 1));
		boolean inShadow = false;
		Color result = ColorOperations.lithting(material, light, position, eyeVector, normalVector, inShadow);
		
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
		boolean inShadow = false;
		Color result = ColorOperations.lithting(material, light, position, eyeVector, normalVector, inShadow);
		
		Color expectedResult = Factory.color(0.7364, 0.7364, 0.7364);
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
		Tuple eyeVector = Factory.vector(0, -Math.sqrt(2) / 2, -Math.sqrt(2) / 2);
		Tuple normalVector = Factory.vector(0, 0, -1);
		PointLight light = Factory.pointLight(Factory.point(0, 10, -10), Factory.color(1, 1, 1));
		boolean inShadow = false;
		Color result = ColorOperations.lithting(material, light, position, eyeVector, normalVector, inShadow);
		
		Color expectedResult = Factory.color(1.6364, 1.6364, 1.6364);
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
		boolean inShadow = false;
		Color result = ColorOperations.lithting(material, light, position, eyeVector, normalVector, inShadow);
		
		Color expectedResult = Factory.color(0.1, 0.1, 0.1);
		assertEquals(expectedResult, result, "Wrong color for this lighting!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * It’s identical to the one titled “Lighting with the eye between the light and the surface”, 
	 * where the specular and diffuse components were both at their maximum values, but this time 
	 * you’re going to pass a new argument to the lighting() function indicating that the point is 
	 * in shadow. It should cause the diffuse and specular components to be ignored, resulting in 
	 * the ambient value alone contributing to the lighting.
	 */
	@Test
	@Order(11)
	public void testShadow() {
		logger.info(Constants.SEPARATOR_JUNIT + "Lighting with the surface in shadow");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Common information
		Material material = Factory.material();
		Tuple position = Factory.point(0, 0, 0);
		
		// Lighting with the eye between the light and the surface
		Tuple eyeVector = Factory.vector(0, 0, -1);
		Tuple normalVector = Factory.vector(0, 0, -1);
		PointLight light = Factory.pointLight(Factory.point(0, 0, -10), Factory.color(1, 1, 1));
		boolean inShadow = true;
		Color result = ColorOperations.lithting(material, light, position, eyeVector, normalVector, inShadow);
		
		Color expectedResult = Factory.color(0.1, 0.1, 0.1);
		assertEquals(expectedResult, result, "Wrong color for this lighting!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
		
}
