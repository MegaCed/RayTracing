package com.raytracer.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Color;
import com.raytracer.engine.element.Computations;
import com.raytracer.engine.element.Intersection;
import com.raytracer.engine.element.Intersections;
import com.raytracer.engine.element.Material;
import com.raytracer.engine.element.PointLight;
import com.raytracer.engine.element.Ray;
import com.raytracer.engine.element.Sphere;
import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.element.World;
import com.raytracer.engine.misc.Constants;
import com.raytracer.engine.operation.WorldOperations;

/*
 * Testing World-related methods.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestWorld {
	
	private static Logger logger = LoggerFactory.getLogger(TestWorld.class);
	
	/*
	 * Test the default World.
	 */
	@Test
	@Order(1)
	public void testDefaultWorld() {
		logger.info(Constants.SEPARATOR_JUNIT + "Creating a default World");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Sphere
		Sphere sphere1 = Factory.sphere();
		Material material1 = Factory.material();
		material1.setColor(Factory.color(0.8, 1, 0.6));
		material1.setDiffuse(0.7);
		material1.setSpecular(0.2);
		sphere1.setMaterial(material1);
		
		// Create another Sphere
		Sphere sphere2 = Factory.sphere();
		sphere2.setTransform(Factory.scalingMatrix(0.5, 0.5, 0.5));
		
		// Create a World
		World theWorld = Factory.world();
		List objects = new ArrayList();
		objects.add(sphere1);
		objects.add(sphere2);
		theWorld.setObjects(objects);
		
		assertEquals(Factory.pointLight(Factory.point(-10, 10, -10), Factory.color(1, 1, 1)), theWorld.getLight(), "Wrong Light for the World!");
		assertEquals(2, theWorld.getObjects().size(), "Wrong number of objects for the World!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Intersect a world with a ray.
	 */
	@Test
	@Order(2)
	public void testWorldIntersections() {
		logger.info(Constants.SEPARATOR_JUNIT + "World intersections");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Sphere
		Sphere sphere1 = Factory.sphere();
		Material material1 = Factory.material();
		material1.setColor(Factory.color(0.8, 1, 0.6));
		material1.setDiffuse(0.7);
		material1.setSpecular(0.2);
		sphere1.setMaterial(material1);
		
		// Create another Sphere
		Sphere sphere2 = Factory.sphere();
		sphere2.setTransform(Factory.scalingMatrix(0.5, 0.5, 0.5));
		
		// Create a World
		World theWorld = Factory.world();
		List objects = new ArrayList();
		objects.add(sphere1);
		objects.add(sphere2);
		theWorld.setObjects(objects);
		
		// Create a Ray
		Ray theRay = Factory.ray(Factory.point(0, 0, -5), Factory.vector(0, 0, 1));
		
		// In this case, since the ray passes through the origin (where both spheres are centered) 
		// it should intersect each sphere twice, for a total of four intersections.
		Intersections theIntersections = WorldOperations.intersectWorld(theWorld, theRay);
		
		assertEquals(4, theIntersections.getIntersections().size(), "Wrong number of intersections for the World!");
		assertEquals(4, theIntersections.getIntersections().get(0).getT(), "Wrong 1st intersection for the World!");
		assertEquals(4.5, theIntersections.getIntersections().get(1).getT(), "Wrong 2nd intersection for the World!");
		assertEquals(5.5, theIntersections.getIntersections().get(2).getT(), "Wrong 3rd intersection for the World!");
		assertEquals(6, theIntersections.getIntersections().get(3).getT(), "Wrong 4th intersection for the World!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Shows that prepareComputations() precomputes the point (in world space) where the 
	 * intersection occurred, the eye vector (pointing back toward the eye, or camera), and the 
	 * normal vector.
	 */
	@Test
	@Order(3)
	public void testComputations() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing computations");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Ray
		Ray theRay = Factory.ray(Factory.point(0, 0, -5), Factory.vector(0, 0, 1));
		
		// Create a Sphere
		Sphere aSphere = Factory.sphere();
		
		// Create an Intersection
		Intersection anIntersection = Factory.intersection(4, aSphere);
		
		// Precomputing the state of an intersection
		Computations computations = WorldOperations.prepareComputations(anIntersection, theRay);
		
		Tuple expectedPoint = Factory.point(0, 0, -1);
		Tuple expectedEyeVector = Factory.vector(0, 0, -1);
		Tuple expectedNormalVector = Factory.vector(0, 0, -1);
		
		assertEquals(anIntersection.getObject(), computations.getObject(), "Wrong Object for the Computations!");
		assertEquals(expectedPoint, computations.getPoint(), "Wrong Point for the Computations!");
		assertEquals(expectedEyeVector, computations.getEyeVector(), "Wrong Eye Vector for the Computations!");
		assertEquals(expectedNormalVector, computations.getNormalVector(), "Wrong Normal Vector for the Computations!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Show that prepareComputations() sets a fourth attribute: inside.
	 */
	@Test
	@Order(4)
	public void testInsideOutside() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing inside/outside");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Ray
		Ray theRay = Factory.ray(Factory.point(0, 0, -5), Factory.vector(0, 0, 1));
		
		// Create a Sphere
		Sphere aSphere = Factory.sphere();
		
		// Create an Intersection
		Intersection anIntersection = Factory.intersection(4, aSphere);
		
		// The hit, when an intersection occurs on the outside
		Computations computations = WorldOperations.prepareComputations(anIntersection, theRay);
		
		assertEquals(false, computations.isInside(), "Wrong Inside value for the Computations!");
		
		// Create a Ray
		theRay = Factory.ray(Factory.point(0, 0, 0), Factory.vector(0, 0, 1));
		
		// Create a Sphere
		aSphere = Factory.sphere();
		
		// Create an Intersection
		anIntersection = Factory.intersection(1, aSphere);
		
		// The hit, when an intersection occurs on the inside
		computations = WorldOperations.prepareComputations(anIntersection, theRay);
		
		Tuple expectedPoint = Factory.point(0, 0, 1);
		Tuple expectedEyeVector = Factory.vector(0, 0, -1);
		Tuple expectedNormalVector = Factory.vector(0, 0, -1);
		
		assertEquals(anIntersection.getObject(), computations.getObject(), "Wrong Object for the Computations!");
		assertEquals(expectedPoint, computations.getPoint(), "Wrong Point for the Computations!");
		assertEquals(expectedEyeVector, computations.getEyeVector(), "Wrong Eye Vector for the Computations!");
		assertEquals(expectedNormalVector, computations.getNormalVector(), "Wrong Normal Vector for the Computations!");
		assertEquals(true, computations.isInside(), "Wrong Inside value for the Computations!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Shading intersections.
	 */
	@Test
	@Order(5)
	public void testShading() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing shading");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a World
		World theWorld = Factory.world();
		
		// Create a Ray
		Ray theRay = Factory.ray(Factory.point(0, 0, -5), Factory.vector(0, 0, 1));
		
		// Create a shape: the first object in the World
		Sphere aShape = Factory.sphere();
		theWorld.getObjects().add(aShape);
		
		// Create an Intersection
		Intersection anIntersection = Factory.intersection(4, aShape);
		
		// Precomputing the state of an intersection
		Computations computations = WorldOperations.prepareComputations(anIntersection, theRay);
		
		// Shading an intersection
		Color aColor = WorldOperations.shadeHit(theWorld, computations);
		
		Color expectedColor = Factory.color(0.38066, 0.47583, 0.2855);
		
		assertEquals(expectedColor, aColor, "Wrong shaded Color!");
		
		// Creating a light		
		PointLight light = Factory.pointLight(Factory.point(0, 0.25, 0), Factory.color(1, 1, 1));
		theWorld.setLight(light);
		
		// Create a Ray
		theRay = Factory.ray(Factory.point(0, 0, 0), Factory.vector(0, 0, 1));
		
		// Create a shape: the second object in the World
		Sphere anotherShape = Factory.sphere();
		theWorld.getObjects().add(anotherShape);
		
		// Create an Intersection
		anIntersection = Factory.intersection(0.5, anotherShape);
		
		// Precomputing the state of an intersection
		computations = WorldOperations.prepareComputations(anIntersection, theRay);
		
		// Shading an intersection from the inside
		aColor = WorldOperations.shadeHit(theWorld, computations);
		
		expectedColor = Factory.color(0.90498, 0.90498, 0.90498);
		
		assertEquals(expectedColor, aColor, "Wrong shaded Color!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
}
