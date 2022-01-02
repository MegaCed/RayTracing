package com.raytracer.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Ray;
import com.raytracer.engine.element.Sphere;
import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.misc.Constants;
import com.raytracer.engine.operation.SphereOperations;

/*
 * Testing the engine's Spheres.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestSpheres {
	
	private static Logger logger = LoggerFactory.getLogger(TestSpheres.class);
	
	/*
	 * A ray intersects a sphere at two points.
	 */
	@Test
	@Order(1)
	public void testIntersections() {
		logger.info(Constants.SEPARATOR_JUNIT + "Rays-Spheres interactions");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create the Ray's origin
		Tuple origin = Factory.point(0, 0, -5);
		
		// Create the Ray's direction
		Tuple direction = Factory.vector(0, 0, 1);
		
		// Create the Ray
		Ray aRay = Factory.ray(origin, direction);
		
		// Create a Sphere
		Sphere aSphere = Factory.sphere();
		
		// Retrieve the interactions
		float[] interactions = SphereOperations.intersects(aSphere, aRay);
		
		assertEquals(2, interactions.length, "Wrong interactions size!");
		assertEquals(4, interactions[0], "Wrong 1st interaction!");
		assertEquals(6, interactions[1], "Wrong 2nd interaction!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * A ray intersects a sphere at a tangent.
	 * 
	 * The ray will be tangent to the sphere. It will intersect at one point, just glancing off the 
	 * edge.
	 */
	@Test
	@Order(2)
	public void testIntersectionsTangent() {
		logger.info(Constants.SEPARATOR_JUNIT + "Rays-Spheres tangent interaction");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create the Ray's origin
		Tuple origin = Factory.point(0, 1, -5);
		
		// Create the Ray's direction
		Tuple direction = Factory.vector(0, 0, 1);
		
		// Create the Ray
		Ray aRay = Factory.ray(origin, direction);
		
		// Create a Sphere
		Sphere aSphere = Factory.sphere();
		
		// Retrieve the interactions
		float[] interactions = SphereOperations.intersects(aSphere, aRay);
		
		// Even though it truly intersects at only a single point, for simplicity’s sake you’ll have 
		// your code return two intersections, with the same point at each
		assertEquals(2, interactions.length, "Wrong interactions size!");
		assertEquals(5, interactions[0], "Wrong 1st interaction!");
		assertEquals(5, interactions[1], "Wrong 2nd interaction!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * A ray misses a sphere.
	 * 
	 * Now move your ray’s starting point just a bit more along the positive y direction. The ray 
	 * should miss the sphere entirely, passing above the sphere and not intersecting it at all.
	 */
	@Test
	@Order(3)
	public void testIntersectionsMiss() {
		logger.info(Constants.SEPARATOR_JUNIT + "Rays-Spheres interaction miss");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create the Ray's origin
		Tuple origin = Factory.point(0, 2, -5);
		
		// Create the Ray's direction
		Tuple direction = Factory.vector(0, 0, 1);
		
		// Create the Ray
		Ray aRay = Factory.ray(origin, direction);
		
		// Create a Sphere
		Sphere aSphere = Factory.sphere();
		
		// Retrieve the interactions
		float[] interactions = SphereOperations.intersects(aSphere, aRay);
		
		assertEquals(0, interactions.length, "Wrong interactions size!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * what happens if your ray originates inside the sphere? Well, there should be one intersection 
	 * in front of the ray, and another behind it
	 * 
	 * When the ray starts at the center of a sphere, the first intersection is behind the ray’s 
	 * origin, and the second is in front of it.
	 */
	@Test
	@Order(4)
	public void testIntersectionsInside() {
		logger.info(Constants.SEPARATOR_JUNIT + "Rays-Spheres interaction (inside)");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create the Ray's origin
		Tuple origin = Factory.point(0, 0, 0);
		
		// Create the Ray's direction
		Tuple direction = Factory.vector(0, 0, 1);
		
		// Create the Ray
		Ray aRay = Factory.ray(origin, direction);
		
		// Create a Sphere
		Sphere aSphere = Factory.sphere();
		
		// Retrieve the interactions
		float[] interactions = SphereOperations.intersects(aSphere, aRay);
		
		// Even though it truly intersects at only a single point, for simplicity’s sake you’ll have 
		// your code return two intersections, with the same point at each
		assertEquals(2, interactions.length, "Wrong interactions size!");
		assertEquals(-1, interactions[0], "Wrong 1st interaction!");
		assertEquals(1, interactions[1], "Wrong 2nd interaction!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * If the sphere is completely behind the ray, you should still see two intersections—both with 
	 * a negative t value.
	 * With both intersections occurring behind the ray’s origin.
	 */
	@Test
	@Order(5)
	public void testIntersectionsOutside() {
		logger.info(Constants.SEPARATOR_JUNIT + "Rays-Spheres interaction (outside)");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create the Ray's origin
		Tuple origin = Factory.point(0, 0, 5);
		
		// Create the Ray's direction
		Tuple direction = Factory.vector(0, 0, 1);
		
		// Create the Ray
		Ray aRay = Factory.ray(origin, direction);
		
		// Create a Sphere
		Sphere aSphere = Factory.sphere();
		
		// Retrieve the interactions
		float[] interactions = SphereOperations.intersects(aSphere, aRay);
		
		// Even though it truly intersects at only a single point, for simplicity’s sake you’ll have 
		// your code return two intersections, with the same point at each
		assertEquals(2, interactions.length, "Wrong interactions size!");
		assertEquals(-6, interactions[0], "Wrong 1st interaction!");
		assertEquals(-4, interactions[1], "Wrong 2nd interaction!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}

}
