package com.raytracer.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Intersection;
import com.raytracer.engine.element.Plane;
import com.raytracer.engine.element.Ray;
import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.misc.Constants;

/*
 * Testing the engine's Planes.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class testPlanes {
	
	private static Logger logger = LoggerFactory.getLogger(TestShapes.class);
	
	/*
	 * Check the expected normal vector for a few arbitrary points on the plane.
	 */
	@Test
	@Order(1)
	public void testNormalPlane() {
		logger.info(Constants.SEPARATOR_JUNIT + "The normal of a plane is constant everywhere");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		Plane aPlane = Factory.plane();
		
		Tuple normal1 = aPlane.getOperations().localNormalAt(aPlane, Factory.point(0, 0, 0));
		assertEquals(Factory.vector(0, 1, 0), normal1, "Wrong normal!");
		
		
		Tuple normal2 = aPlane.getOperations().localNormalAt(aPlane, Factory.point(10, 0, -10));
		assertEquals(Factory.vector(0, 1, 0), normal2, "Wrong normal!");
		
		Tuple normal3 = aPlane.getOperations().localNormalAt(aPlane, Factory.point(-5, 0, 150));
		assertEquals(Factory.vector(0, 1, 0), normal3, "Wrong normal!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Sets up a plane and a ray with a direction parallel to the plane.
	 */
	@Test
	@Order(2)
	public void testParallelRays() {
		logger.info(Constants.SEPARATOR_JUNIT + "Plane-Ray intersections (parallel)");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		Plane aPlane = Factory.plane();
		
		// Intersect with a ray parallel to the plane
		Ray aRay = Factory.ray(Factory.point(0, 10, 0), Factory.vector(0, 0, 1));
		List<Intersection> theIntersections = aPlane.getOperations().localIntersect(aPlane, aRay);
		
		// No intersections
		assertEquals(0, theIntersections.size(), "Wrong intersections!");
		
		// Intersect with a coplanar ray
		aRay = Factory.ray(Factory.point(0, 0, 0), Factory.vector(0, 0, 1));
		theIntersections = aPlane.getOperations().localIntersect(aPlane, aRay);
		
		// No intersections
		assertEquals(0, theIntersections.size(), "Wrong intersections!");
		
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * The first checks the case of a ray intersecting a plane from above, and the second checks an 
	 * intersection from below.
	 */
	@Test
	@Order(3)
	public void testIntersections() {
		logger.info(Constants.SEPARATOR_JUNIT + "Plane-Ray intersections");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		Plane aPlane = Factory.plane();
		
		// A ray intersecting a plane from above
		Ray aRay = Factory.ray(Factory.point(0, 1, 0), Factory.vector(0, -1, 0));
		List<Intersection> theIntersections = aPlane.getOperations().localIntersect(aPlane, aRay);
		
		// 1 intersection
		assertEquals(1, theIntersections.size(), "Wrong intersections!");
		assertEquals(1, theIntersections.get(0).getT(), "Wrong intersection!");
		assertEquals(aPlane, theIntersections.get(0).getObject(), "Wrong object!");
		
		// A ray intersecting a plane from below
		aRay = Factory.ray(Factory.point(0, -1, 0), Factory.vector(0, 1, 0));
		theIntersections = aPlane.getOperations().localIntersect(aPlane, aRay);
		
		// No intersections
		assertEquals(1, theIntersections.size(), "Wrong intersections!");
		assertEquals(1, theIntersections.get(0).getT(), "Wrong intersection!");
		assertEquals(aPlane, theIntersections.get(0).getObject(), "Wrong object!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}

}
