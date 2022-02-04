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
import com.raytracer.engine.element.Matrix;
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
	 * Creates a "default world".
	 */
	private static World getDefaultWorld() {
		// Create a shape: the first object in the World
		Sphere sphere1 = Factory.sphere();
		Material material1 = Factory.material();
		material1.setColor(Factory.color(0.8, 1, 0.6));
		material1.setDiffuse(0.7);
		material1.setSpecular(0.2);
		sphere1.setMaterial(material1);
		
		// Create a shape: the second object in the World
		Sphere sphere2 = Factory.sphere();
		sphere2.setTransform(Factory.scalingMatrix(0.5, 0.5, 0.5));
		
		// Create a World
		World theWorld = Factory.world();
		List objects = new ArrayList();
		objects.add(sphere1);
		objects.add(sphere2);
		theWorld.setObjects(objects);
		
		return theWorld;
	}
	
	/*
	 * Test the default World.
	 */
	@Test
	@Order(1)
	public void testDefaultWorld() {
		logger.info(Constants.SEPARATOR_JUNIT + "Creating a default World");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Get the default World
		World theWorld = getDefaultWorld();
		
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
		
		// Get the default World
		World theWorld = getDefaultWorld();
		
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
		
		// Get the default World
		World theWorld = getDefaultWorld();

		// Create a Ray
		Ray theRay = Factory.ray(Factory.point(0, 0, -5), Factory.vector(0, 0, 1));
		
		// Create an Intersection
		Intersection anIntersection = Factory.intersection(4, theWorld.getObjects().get(0));
		
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
		
		// Create an Intersection
		anIntersection = Factory.intersection(0.5, theWorld.getObjects().get(1));
		
		// Precomputing the state of an intersection
		computations = WorldOperations.prepareComputations(anIntersection, theRay);
		
		// Shading an intersection from the inside
		aColor = WorldOperations.shadeHit(theWorld, computations);
		
		expectedColor = Factory.color(0.90498, 0.90498, 0.90498);
		
		assertEquals(expectedColor, aColor, "Wrong shaded Color!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * The test shows that when the ray fails to intersect anything, the color that is returned 
	 * should be black.
	 */
	@Test
	@Order(6)
	public void testColorMiss() {
		logger.info(Constants.SEPARATOR_JUNIT + "The color when a ray misses");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Get the default World
		World theWorld = getDefaultWorld();
		
		// Create a Ray
		Ray theRay = Factory.ray(Factory.point(0, 0, -5), Factory.vector(0, 1, 0));
		
		// Get the Color
		Color result = WorldOperations.colorAt(theWorld, theRay);
		
		assertEquals(Constants.COLOR_BLACK, result, "Wrong Color!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * This test shows that the shading should be computed appropriately when the ray intersects an 
	 * object—in this case, the outermost sphere in the default world.
	 */
	@Test
	@Order(7)
	public void testColorHit() {
		logger.info(Constants.SEPARATOR_JUNIT + "The color when a ray hits");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Get the default World
		World theWorld = getDefaultWorld();
		
		// Create a Ray
		Ray theRay = Factory.ray(Factory.point(0, 0, -5), Factory.vector(0, 0, 1));
		
		// Get the Color
		Color result = WorldOperations.colorAt(theWorld, theRay);
		
		Color expectedColor = Factory.color(0.38066, 0.47583, 0.2855);
		
		assertEquals(expectedColor, result, "Wrong Color!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * The test shows that we expect colorAt() to use the hit when computing the color. 
	 * Here, we put the ray inside the outer sphere, but outside the inner sphere, and pointing at 
	 * the inner sphere. We expect the hit to be on the inner sphere, and thus return its color.
	 */
	@Test
	@Order(8)
	public void testColorBehind() {
		logger.info(Constants.SEPARATOR_JUNIT + "The color with an intersection behind the ray");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a World
		World theWorld = Factory.world();
		
		// Create a shape: the first object in the World
		Sphere outer = Factory.sphere();
		Material material = Factory.material();
		material.setAmbient(1);
		outer.setMaterial(material);
		
		// Create a shape: the second object in the World
		Sphere inner = Factory.sphere();
		inner.setMaterial(material);
		
		List objects = new ArrayList();
		objects.add(inner);
		objects.add(outer);
		theWorld.setObjects(objects);
		
		// Create a Ray
		Ray theRay = Factory.ray(Factory.point(0, 0, 0.75), Factory.vector(0, 0, -1));
		
		// Get the Color
		Color result = WorldOperations.colorAt(theWorld, theRay);
		
		assertEquals(inner.getMaterial().getColor(), result, "Wrong Color!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * The following test demonstrates this and shows that the orientation looks from the origin 
	 * along the z axis in the negative direction, with up in the positive y direction.
	 */
	@Test
	@Order(9)
	public void testDefaultTransformationMatrix() {
		logger.info(Constants.SEPARATOR_JUNIT + "The transformation matrix for the default orientation");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		Tuple from = Factory.point(0, 0, 0);
		Tuple to = Factory.point(0, 0, -1);
		Tuple up = Factory.vector(0, 1, 0);
		
		// Get the world’s default orientation
		Matrix t = WorldOperations.viewTransform(from, to, up);
		
		// The default orientation is the matrix you get if your view parameters (from, to, and up) 
		// don’t require anything to be scaled, rotated, or translated. 
		// In other words, the default orientation is the identity matrix!
		assertEquals(Factory.identityMatrix(), t, "Wrong Matrix retrieved!");		
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Turning around and looking in the positive z direction is like looking in a mirror: 
	 * front and back are swapped, and left and right are swapped.
	 */
	@Test
	@Order(10)
	public void testMirrorTransformationMatrix() {
		logger.info(Constants.SEPARATOR_JUNIT + "A view transformation matrix looking in positive z direction");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		Tuple from = Factory.point(0, 0, 0);
		Tuple to = Factory.point(0, 0, 1);
		Tuple up = Factory.vector(0, 1, 0);
		
		// The view transformation in this case should be exactly the same as reflecting across the 
		// z (front-to-back) and x (left-to-right) axes
		Matrix t = WorldOperations.viewTransform(from, to, up);
		
		// Reflection is the same as scaling by a negative value, so you would expect the view 
		// transformation here to be the same as scaling by (-1, 1, -1)
		assertEquals(Factory.scalingMatrix(-1, 1, -1), t, "Wrong Matrix retrieved!");		
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Shows that the view transformation really does move the world and not the eye.
	 */
	@Test
	@Order(11)
	public void testMoveWorld() {
		logger.info(Constants.SEPARATOR_JUNIT + "The view transformation moves the world");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		Tuple from = Factory.point(0, 0, 8);
		Tuple to = Factory.point(0, 0, 0);
		Tuple up = Factory.vector(0, 1, 0);
		
		// The test positions the eye at a point 8 units along the z axis, and points the eye back 
		// at the origin
		Matrix t = WorldOperations.viewTransform(from, to, up);
		
		// As you can see, the resulting translation moves everything backward 8 units along the z 
		// axis, effectively pushing the world away from an eye positioned at the origin!
		assertEquals(Factory.translationMatrix(0, 0, -8), t, "Wrong Matrix retrieved!");		
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * One more test for the view transformation, this time looking in some arbitrary direction. 
	 * It should produce a matrix that is a combination of shearing, scaling, and translation.
	 */
	@Test
	@Order(12)
	public void testArbitraryTransformation() {
		logger.info(Constants.SEPARATOR_JUNIT + "An arbitrary view transformation");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		Tuple from = Factory.point(1, 3, 2);
		Tuple to = Factory.point(4, -2, 8);
		Tuple up = Factory.vector(1, 1, 0);
		
		// Get the view transformation
		Matrix t = WorldOperations.viewTransform(from, to, up);
		
		double[][] values = {
				{-0.50709, 0.50709, 0.67612, -2.36643},
				{0.76772, 0.60609, 0.12122, -2.82843},
				{-0.35857, 0.59761, -0.71714, 0},
				{0, 0, 0, 1}
		};
		Matrix expectedResult = Factory.matrix(values);
		
		assertEquals(expectedResult, t, "Wrong Matrix retrieved!");	
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
}
