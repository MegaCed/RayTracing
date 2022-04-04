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
import com.raytracer.engine.element.Pattern;
import com.raytracer.engine.element.PointLight;
import com.raytracer.engine.element.Shape;
import com.raytracer.engine.element.Sphere;
import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.misc.Constants;
import com.raytracer.engine.operation.ColorOperations;
import com.raytracer.engine.operation.MiscOperations;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestColors {
	
	private static Logger logger = LoggerFactory.getLogger(TestColors.class);
	
	/*
	 * This function's job will be to help you test the behaviors of the abstract pattern superclass 
	 * by returning a special implementation used only for the tests.
	 */
	private Pattern getTestPattern() {
		return Factory.stripePattern(Constants.COLOR_BLACK, Constants.COLOR_WHITE);
	}
	
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
		Shape aShape = Factory.sphere();
		Color result = ColorOperations.lithting(material, aShape, light, position, eyeVector, normalVector, inShadow);
		
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
		Shape aShape = Factory.sphere();
		Color result = ColorOperations.lithting(material, aShape, light, position, eyeVector, normalVector, inShadow);
		
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
		Shape aShape = Factory.sphere();
		Color result = ColorOperations.lithting(material, aShape, light, position, eyeVector, normalVector, inShadow);
		
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
		Shape aShape = Factory.sphere();
		Color result = ColorOperations.lithting(material, aShape, light, position, eyeVector, normalVector, inShadow);
		
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
		Shape aShape = Factory.sphere();
		Color result = ColorOperations.lithting(material, aShape, light, position, eyeVector, normalVector, inShadow);
		
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
		Shape aShape = Factory.sphere();
		Color result = ColorOperations.lithting(material, aShape, light, position, eyeVector, normalVector, inShadow);
		
		Color expectedResult = Factory.color(0.1, 0.1, 0.1);
		assertEquals(expectedResult, result, "Wrong color for this lighting!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Test some colors for the given pattern and point.
	 */
	@Test
	@Order(12)
	public void testStripePattern() {
		logger.info(Constants.SEPARATOR_JUNIT + "Stripe patterns");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Creating a stripe pattern
		Pattern aPattern = Factory.stripePattern(Constants.COLOR_WHITE, Constants.COLOR_BLACK);
		
		assertEquals(Constants.COLOR_WHITE, aPattern.getA(), "Wrong color for this pattern!");
		assertEquals(Constants.COLOR_BLACK, aPattern.getB(), "Wrong color for this pattern!");
		
		// A stripe pattern is constant in y
		assertEquals(Constants.COLOR_WHITE, ColorOperations.stripeAt(aPattern, Factory.point(0, 0, 0)), "Wrong color for this location!");
		assertEquals(Constants.COLOR_WHITE, ColorOperations.stripeAt(aPattern, Factory.point(0, 1, 0)), "Wrong color for this location!");
		assertEquals(Constants.COLOR_WHITE, ColorOperations.stripeAt(aPattern, Factory.point(0, 2, 0)), "Wrong color for this location!");
		
		// A stripe pattern is constant in z
		assertEquals(Constants.COLOR_WHITE, ColorOperations.stripeAt(aPattern, Factory.point(0, 0, 0)), "Wrong color for this location!");
		assertEquals(Constants.COLOR_WHITE, ColorOperations.stripeAt(aPattern, Factory.point(0, 0, 1)), "Wrong color for this location!");
		assertEquals(Constants.COLOR_WHITE, ColorOperations.stripeAt(aPattern, Factory.point(0, 0, 2)), "Wrong color for this location!");
		
		// A stripe pattern alternates in x
		assertEquals(Constants.COLOR_WHITE, ColorOperations.stripeAt(aPattern, Factory.point(0, 0, 0)), "Wrong color for this location!");
		assertEquals(Constants.COLOR_WHITE, ColorOperations.stripeAt(aPattern, Factory.point(0.9, 0, 0)), "Wrong color for this location!");
		assertEquals(Constants.COLOR_BLACK, ColorOperations.stripeAt(aPattern, Factory.point(1, 0, 0)), "Wrong color for this location!");
		assertEquals(Constants.COLOR_BLACK, ColorOperations.stripeAt(aPattern, Factory.point(-0.1, 0, 0)), "Wrong color for this location!");
		assertEquals(Constants.COLOR_BLACK, ColorOperations.stripeAt(aPattern, Factory.point(-1, 0, 0)), "Wrong color for this location!");
		assertEquals(Constants.COLOR_WHITE, ColorOperations.stripeAt(aPattern, Factory.point(-1.1, 0, 0)), "Wrong color for this location!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Show that the lighting() function (from The Phong Reflection Model) returns the color from 
	 * the pattern.
	 */
	@Test
	@Order(13)
	public void testLightningPattern() {
		logger.info(Constants.SEPARATOR_JUNIT + "Lightning patterns");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Lighting with a pattern applied
		Pattern pattern = Factory.stripePattern(Constants.COLOR_WHITE, Constants.COLOR_BLACK);
		
		// the test uses a material with only ambient illumination. This is a handy trick for making 
		// sure the lighting() function returns an easily predictable color, since the color won’t 
		// be affected by angles, normals, or lights
		Material material = Factory.material();
		material.setPattern(pattern);
		material.setAmbient(1);
		material.setDiffuse(0);
		material.setSpecular(0);
		
		Tuple eyeVector = Factory.vector(0, 0, -1);
		Tuple normalVector = Factory.vector(0, 0, -1);
		PointLight light = Factory.pointLight(Factory.point(0, 0, -10), Constants.COLOR_WHITE);
		
		Shape aShape = Factory.sphere();
		
		Color color1 = ColorOperations.lithting(material, aShape, light, Factory.point(0.9, 0, 0), eyeVector, normalVector, false);
		Color color2 = ColorOperations.lithting(material, aShape, light, Factory.point(1.1, 0, 0), eyeVector, normalVector, false);
		
		assertEquals(Constants.COLOR_WHITE, color1, "Wrong color for this light!");
		assertEquals(Constants.COLOR_BLACK, color2, "Wrong color for this light!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Test the Patterns transformations.
	 */
	@Test
	@Order(14)
	public void testPatternTransformations() {
		logger.info(Constants.SEPARATOR_JUNIT + "Stripes patterns");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Stripes with an object transformation
		Sphere sphere1 = Factory.sphere();
		sphere1.setTransform(Factory.scalingMatrix(2, 2, 2));
		
		Pattern pattern = Factory.stripePattern(Constants.COLOR_WHITE, Constants.COLOR_BLACK);
		
		Color stripeAtObject = ColorOperations.stripeAtObject(pattern, sphere1, Factory.point(1.5, 0, 0));
		
		assertEquals(Constants.COLOR_WHITE, stripeAtObject, "Wrong color for this object!");
		
		// Stripes with a pattern transformation
		Sphere sphere2 = Factory.sphere();
		
		Pattern pattern2 = Factory.stripePattern(Constants.COLOR_WHITE, Constants.COLOR_BLACK);
		pattern2.setTransform(Factory.scalingMatrix(2, 2, 2));
		
		Color stripeAtObject2 = ColorOperations.stripeAtObject(pattern2, sphere2, Factory.point(1.5, 0, 0));
		
		assertEquals(Constants.COLOR_WHITE, stripeAtObject2, "Wrong color for this object!");
		
		// Stripes with both an object and a pattern transformation
		Sphere sphere3 = Factory.sphere();
		sphere3.setTransform(Factory.scalingMatrix(2, 2, 2));
		
		Pattern pattern3 = Factory.stripePattern(Constants.COLOR_WHITE, Constants.COLOR_BLACK);
		pattern3.setTransform(Factory.translationMatrix(0.5, 0, 0));
		
		Color stripeAtObject3 = ColorOperations.stripeAtObject(pattern3, sphere3, Factory.point(2.5, 0, 0));
		
		assertEquals(Constants.COLOR_WHITE, stripeAtObject3, "Wrong color for this object!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Basic tests on abstract Patterns.
	 */
	@Test
	@Order(15)
	public void testPattern() {
		logger.info(Constants.SEPARATOR_JUNIT + "Test patterns");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// The default pattern transformation
		Pattern aPattern = getTestPattern();
		
		assertEquals(Factory.identityMatrix(), aPattern.getTransform(), "Wrong transformation matrix for this pattern!");
		
		// Next, show that the pattern’s transformation can be assigned
		aPattern.setTransform(Factory.translationMatrix(1, 2, 3));
		
		assertEquals(Factory.translationMatrix(1, 2, 3), aPattern.getTransform(), "Wrong translation matrix for this pattern!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * The following tests replace the ones you wrote earlier in the chapter, testing the stripe 
	 * pattern’s transformations.
	 */
//	@Deprecated
//	@Order(16)
//	public void testPatternTransformations2() {
//		logger.info(Constants.SEPARATOR_JUNIT + "Patterns transformations");
//		logger.info(Constants.SEPARATOR_JUNIT);
//		
//		// Test the patternAtShape() function to see that it correctly transforms the points before 
//		// calling the concrete function
//		
//		// A pattern with an object transformation
//		Shape shape = Factory.sphere();
//		shape.setTransform(Factory.scalingMatrix(2, 2, 2));
//		Pattern pattern = getTestPattern();
//		
//		Color color = pattern.getOperations().patternAtShape(pattern, shape, Factory.point(2, 3, 4));
//		
//		assertEquals(Factory.color(1, 1.5, 2), color, "Wrong color for this shape!");
//		
//		// A pattern with a pattern transformation
//		Shape shape2 = Factory.sphere();
//		Pattern pattern2 = getTestPattern();
//		pattern2.setTransform(Factory.scalingMatrix(2, 2, 2));
//		
//		Color color2 = pattern.getOperations().patternAtShape(pattern2, shape2, Factory.point(2, 3, 4));
//		
//		assertEquals(Factory.color(1, 1.5, 2), color2, "Wrong color for this shape!");
//		
//		// A pattern with both an object and a pattern transformation
//		Shape shape3 = Factory.sphere();
//		shape3.setTransform(Factory.scalingMatrix(2, 2, 2));
//		Pattern pattern3 = getTestPattern();
//		pattern3.setTransform(Factory.translationMatrix(0.5, 1, 1.5));
//		
//		Color color3 = pattern3.getOperations().patternAtShape(pattern3, shape3, Factory.point(2.5, 3, 3.5));
//		
//		assertEquals(Factory.color(0.75, 0.5, 0.25), color3, "Wrong color for this shape!");
//		
//		logger.info(Constants.SEPARATOR_JUNIT);
//	}
	
	/*
	 * The following test show how a basic linear gradient pattern ought to work.
	 */
	@Test
	@Order(17)
	public void testGradientPattern() {
		logger.info(Constants.SEPARATOR_JUNIT + "A gradient linearly interpolates between colors");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		Pattern gradientPattern = Factory.gradientPattern(Constants.COLOR_WHITE, Constants.COLOR_BLACK);
		
		Color result = gradientPattern.getOperations().patternAt(gradientPattern, Factory.point(0, 0, 0));
		assertEquals(Constants.COLOR_WHITE, result, "Wrong color for this Pattern!");
		
		result = gradientPattern.getOperations().patternAt(gradientPattern, Factory.point(0.25, 0, 0));
		assertEquals(Factory.color(0.75, 0.75, 0.75), result, "Wrong color for this Pattern!");
		
		result = gradientPattern.getOperations().patternAt(gradientPattern, Factory.point(0.5, 0, 0));
		assertEquals(Factory.color(0.5, 0.5, 0.5), result, "Wrong color for this Pattern!");
		
		result = gradientPattern.getOperations().patternAt(gradientPattern, Factory.point(0.75, 0, 0));
		assertEquals(Factory.color(0.25, 0.25, 0.25), result, "Wrong color for this Pattern!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * You’re checking to make sure that these rings extend in both x and z.
	 */
	@Test
	@Order(18)
	public void testRingPattern() {
		logger.info(Constants.SEPARATOR_JUNIT + "A ring should extend in both x and z");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		Pattern ringPattern = Factory.ringPattern(Constants.COLOR_WHITE, Constants.COLOR_BLACK);
		
		Color result = ringPattern.getOperations().patternAt(ringPattern, Factory.point(0, 0, 0));
		assertEquals(Constants.COLOR_WHITE, result, "Wrong color for this Pattern!");
		
		result = ringPattern.getOperations().patternAt(ringPattern, Factory.point(1, 0, 0));
		assertEquals(Constants.COLOR_BLACK, result, "Wrong color for this Pattern!");
		
		result = ringPattern.getOperations().patternAt(ringPattern, Factory.point(0, 0, 1));
		assertEquals(Constants.COLOR_BLACK, result, "Wrong color for this Pattern!");
		
		result = ringPattern.getOperations().patternAt(ringPattern, Factory.point(0.708, 0, 0.708));
		assertEquals(Constants.COLOR_BLACK, result, "Wrong color for this Pattern!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * The following tests for the 3D checkers, shows that the pattern does indeed repeat in all 
	 * three dimensions.
	 */
	@Test
	@Order(19)
	public void testCheckerPattern() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing 3d Checker Patterns");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		Pattern checkerPattern = Factory.checkerPattern(Constants.COLOR_WHITE, Constants.COLOR_BLACK);
		
		// Checkers should repeat in x
		Color result = checkerPattern.getOperations().patternAt(checkerPattern, Factory.point(0, 0, 0));
		assertEquals(Constants.COLOR_WHITE, result, "Wrong color for this Pattern!");
		
		result = checkerPattern.getOperations().patternAt(checkerPattern, Factory.point(0.99, 0, 0));
		assertEquals(Constants.COLOR_WHITE, result, "Wrong color for this Pattern!");
		
		result = checkerPattern.getOperations().patternAt(checkerPattern, Factory.point(1.01, 0, 0));
		assertEquals(Constants.COLOR_BLACK, result, "Wrong color for this Pattern!");
		
		// Checkers should repeat in y
		result = checkerPattern.getOperations().patternAt(checkerPattern, Factory.point(0, 0, 0));
		assertEquals(Constants.COLOR_WHITE, result, "Wrong color for this Pattern!");
		
		result = checkerPattern.getOperations().patternAt(checkerPattern, Factory.point(0, 0.99, 0));
		assertEquals(Constants.COLOR_WHITE, result, "Wrong color for this Pattern!");
		
		result = checkerPattern.getOperations().patternAt(checkerPattern, Factory.point(0, 1.01, 0));
		assertEquals(Constants.COLOR_BLACK, result, "Wrong color for this Pattern!");
		
		// Checkers should repeat in z
		result = checkerPattern.getOperations().patternAt(checkerPattern, Factory.point(0, 0, 0));
		assertEquals(Constants.COLOR_WHITE, result, "Wrong color for this Pattern!");
		
		result = checkerPattern.getOperations().patternAt(checkerPattern, Factory.point(0, 0, 0.99));
		assertEquals(Constants.COLOR_WHITE, result, "Wrong color for this Pattern!");
		
		result = checkerPattern.getOperations().patternAt(checkerPattern, Factory.point(0, 0, 1.01));
		assertEquals(Constants.COLOR_BLACK, result, "Wrong color for this Pattern!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
		
}
