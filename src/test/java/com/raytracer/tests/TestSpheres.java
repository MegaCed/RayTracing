package com.raytracer.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Intersection;
import com.raytracer.engine.element.Intersections;
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
		Intersection[] theIntersections = SphereOperations.intersects(aSphere, aRay);
		
		assertEquals(2, theIntersections.length, "Wrong interactions size!");
		assertEquals(4, theIntersections[0].getT(), "Wrong 1st interaction!");
		assertEquals(6, theIntersections[1].getT(), "Wrong 2nd interaction!");
		
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
		Intersection[] theIntersections = SphereOperations.intersects(aSphere, aRay);
		
		// Even though it truly intersects at only a single point, for simplicity’s sake you’ll have 
		// your code return two intersections, with the same point at each
		assertEquals(2, theIntersections.length, "Wrong interactions size!");
		assertEquals(5, theIntersections[0].getT(), "Wrong 1st interaction!");
		assertEquals(5, theIntersections[1].getT(), "Wrong 2nd interaction!");
		
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
		Intersection[] theIntersections = SphereOperations.intersects(aSphere, aRay);
		
		assertEquals(0, theIntersections.length, "Wrong interactions size!");
		
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
		Intersection[] theIntersections = SphereOperations.intersects(aSphere, aRay);
		
		// Even though it truly intersects at only a single point, for simplicity’s sake you’ll have 
		// your code return two intersections, with the same point at each
		assertEquals(2, theIntersections.length, "Wrong interactions size!");
		assertEquals(-1, theIntersections[0].getT(), "Wrong 1st interaction!");
		assertEquals(1, theIntersections[1].getT(), "Wrong 2nd interaction!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * If the sphere is completely behind the ray, you should still see two intersections—both with 
	 * a negative t value. With both intersections occurring behind the ray’s origin.
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
		Intersection[] theIntersections = SphereOperations.intersects(aSphere, aRay);
		
		// Even though it truly intersects at only a single point, for simplicity’s sake you’ll have 
		// your code return two intersections, with the same point at each
		assertEquals(2, theIntersections.length, "Wrong interactions size!");
		assertEquals(-6, theIntersections[0].getT(), "Wrong 1st interaction!");
		assertEquals(-4, theIntersections[1].getT(), "Wrong 2nd interaction!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * An intersection encapsulates t and object.
	 */
	@Test
	@Order(6)
	public void testImprovedIntersection() {
		logger.info(Constants.SEPARATOR_JUNIT + "Rays-Spheres intersection");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Sphere
		Sphere aSphere = Factory.sphere();
		
		// Create an Intersection
		Intersection anIntersection = Factory.intersection(3.5f, aSphere);
		
		assertEquals(3.5f, anIntersection.getT(), "Wrong t for the intersection!");
		assertEquals(aSphere, anIntersection.getObject(), "Wrong object for the intersection!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Aggregating intersections.
	 */
	@Test
	@Order(7)
	// TODO: Delete this!
	public void testImprovedIntersections() {
		logger.info(Constants.SEPARATOR_JUNIT + "Rays-Spheres intersections");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Sphere
		Sphere aSphere = Factory.sphere();
		
		// Create 2 Intersections
		Intersection intersection1 = Factory.intersection(1f, aSphere);
		Intersection intersection2 = Factory.intersection(2f, aSphere);
		
		// Aggregate them
		Intersections theIntersections = Factory.intersections(intersection1, intersection2);
		
		assertEquals(2, theIntersections.getIntersections().length, "Wrong intersections size!");
		assertEquals(1f, theIntersections.getIntersections()[0].getT(), "Wrong t for the 1st intersection!");
		assertEquals(2f, theIntersections.getIntersections()[1].getT(), "Wrong t for the 2nd intersection!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Show how hit should behave in a few different situations.
	 */
	@Test
	@Order(8)
	// TODO: Delete this!
	public void testhit() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing hit() method");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Sphere
		Sphere aSphere = Factory.sphere();
		
		// Create 2 Intersections
		Intersection intersection1 = Factory.intersection(1f, aSphere);
		Intersection intersection2 = Factory.intersection(2f, aSphere);
		
		// The hit, when all intersections have positive t
		Intersection theHit = SphereOperations.hit(intersection2, intersection1);
		
		assertEquals(intersection1, theHit, "Wrong hit!");
		
		// Create 2 Intersections
		intersection1 = Factory.intersection(-1f, aSphere);
		intersection2 = Factory.intersection(1f, aSphere);
		
		// The hit, when some intersections have negative t
		theHit = SphereOperations.hit(intersection2, intersection1);
		
		assertEquals(intersection2, theHit, "Wrong hit!");
		
		// Create 2 Intersections
		intersection1 = Factory.intersection(-2f, aSphere);
		intersection2 = Factory.intersection(-1f, aSphere);
		
		// The hit, when some intersections have negative t
		theHit = SphereOperations.hit(intersection2, intersection1);
		
		assertEquals(null, theHit, "Wrong hit!");
		
		// Create many Intersections
		intersection1 = Factory.intersection(5f, aSphere);
		intersection2 = Factory.intersection(7f, aSphere);
		Intersection intersection3 = Factory.intersection(-3f, aSphere);
		Intersection intersection4 = Factory.intersection(2f, aSphere);
		
		// The hit, when some intersections have negative t
		theHit = SphereOperations.hit(intersection1, intersection2, intersection3, intersection4);
		
		assertEquals(intersection4, theHit, "Wrong hit!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
		

}
