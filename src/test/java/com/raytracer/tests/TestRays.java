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
import com.raytracer.engine.element.Ray;
import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.misc.Constants;
import com.raytracer.engine.operation.RayOperations;
import com.raytracer.engine.operation.TupleOperations;

/*
 * Testing the engine's Rays.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestRays {
	
	private static Logger logger = LoggerFactory.getLogger(TestRays.class);
	
	/*
	 * Creating and querying a ray.
	 */
	@Test
	@Order(1)
	public void testCreateRay() {
		logger.info(Constants.SEPARATOR_JUNIT + "Creating a Ray");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create the Ray's origin
		Tuple origin = Factory.point(1, 2, 3);
		
		// Create the Ray's direction
		Tuple direction = Factory.vector(4, 5, 6);
		
		// Create the Ray
		Ray aRay = Factory.ray(origin, direction);
		
		boolean sameOrigin = TupleOperations.equals(aRay.getOrigin(), origin);
		assertEquals(true, sameOrigin, "Origins are different!");
		
		boolean sameDirection = TupleOperations.equals(aRay.getDirection(), direction);
		assertEquals(true, sameDirection, "Directions are different!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Computing a point from a distance.
	 */
	@Test
	@Order(2)
	public void testComputeDistance() {
		logger.info(Constants.SEPARATOR_JUNIT + "Compute a distance");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create the Ray's origin
		Tuple origin = Factory.point(2, 3, 4);
		
		// Create the Ray's direction
		Tuple direction = Factory.vector(1, 0, 0);
		
		// Create the Ray
		Ray aRay = Factory.ray(origin, direction);
		
		// Get some new positions
		Tuple position1 = RayOperations.position(aRay, 0);
		Tuple position2 = RayOperations.position(aRay, 1);
		Tuple position3 = RayOperations.position(aRay, -1);
		Tuple position4 = RayOperations.position(aRay, 2.5f);
		
		Tuple expected1 = Factory.point(2, 3, 4);
		Tuple expected2 = Factory.point(3, 3, 4);
		Tuple expected3 = Factory.point(1, 3, 4);
		Tuple expected4 = Factory.point(4.5f, 3, 4);
		
		assertEquals(expected1, position1, "Wrong position 1!");
		assertEquals(expected2, position2, "Wrong position 2!");
		assertEquals(expected3, position3, "Wrong position 3!");
		assertEquals(expected4, position4, "Wrong position 4!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Translating a ray.
	 */
	@Test
	@Order(3)
	public void testTranslateRay() {
		logger.info(Constants.SEPARATOR_JUNIT + "Translate a Ray");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create the Ray's origin
		Tuple origin = Factory.point(1, 2, 3);
		
		// Create the Ray's direction
		Tuple direction = Factory.vector(0, 1, 0);
		
		// Create the Ray
		Ray aRay = Factory.ray(origin, direction);
		
		// Get a Translation Matrix
		Matrix translationMatrix = Factory.translationMatrix(3, 4, 5);
		
		// Translate the Ray
		Ray result = RayOperations.transform(aRay, translationMatrix);
		
		Tuple expectedOrigin = Factory.point(4, 6, 8);
		Tuple expectedDirection = Factory.vector(0, 1, 0);
		
		assertEquals(expectedOrigin, result.getOrigin(), "Wrong origin!");
		assertEquals(expectedDirection, result.getDirection(), "Wrong direction!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Scaling a ray.
	 */
	@Test
	@Order(4)
	public void testScaleRay() {
		logger.info(Constants.SEPARATOR_JUNIT + "Scale a Ray");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create the Ray's origin
		Tuple origin = Factory.point(1, 2, 3);
		
		// Create the Ray's direction
		Tuple direction = Factory.vector(0, 1, 0);
		
		// Create the Ray
		Ray aRay = Factory.ray(origin, direction);
		
		// Get a Scaling Matrix
		Matrix scalingMatrix = Factory.scalingMatrix(2, 3, 4);
		
		// Scale the Ray
		Ray result = RayOperations.transform(aRay, scalingMatrix);
		
		Tuple expectedOrigin = Factory.point(2, 6, 12);
		Tuple expectedDirection = Factory.vector(0, 3, 0);
		
		assertEquals(expectedOrigin, result.getOrigin(), "Wrong origin!");
		assertEquals(expectedDirection, result.getDirection(), "Wrong direction!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
		
}
