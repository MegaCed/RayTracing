package com.raytracer.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Intersection;
import com.raytracer.engine.element.Intersections;
import com.raytracer.engine.element.Material;
import com.raytracer.engine.element.Matrix;
import com.raytracer.engine.element.Ray;
import com.raytracer.engine.element.Sphere;
import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.misc.Constants;
import com.raytracer.engine.operation.MatrixOperations;
import com.raytracer.engine.operation.SphereOperations;
import com.raytracer.engine.operation.TupleOperations;

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
		List<Intersection> theIntersections = SphereOperations.intersects(aSphere, aRay);
		
		assertEquals(2, theIntersections.size(), "Wrong interactions size!");
		assertEquals(4, theIntersections.get(0).getT(), "Wrong 1st interaction!");
		assertEquals(6, theIntersections.get(1).getT(), "Wrong 2nd interaction!");
		
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
		List<Intersection> theIntersections = SphereOperations.intersects(aSphere, aRay);
		
		// Even though it truly intersects at only a single point, for simplicity’s sake you’ll have 
		// your code return two intersections, with the same point at each
		assertEquals(2, theIntersections.size(), "Wrong interactions size!");
		assertEquals(5, theIntersections.get(0).getT(), "Wrong 1st interaction!");
		assertEquals(5, theIntersections.get(1).getT(), "Wrong 2nd interaction!");
		
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
		List<Intersection> theIntersections = SphereOperations.intersects(aSphere, aRay);
		
		assertEquals(0, theIntersections.size(), "Wrong interactions size!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * What happens if your ray originates inside the sphere? Well, there should be one intersection 
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
		List<Intersection> theIntersections = SphereOperations.intersects(aSphere, aRay);
		
		// Even though it truly intersects at only a single point, for simplicity’s sake you’ll have 
		// your code return two intersections, with the same point at each
		assertEquals(2, theIntersections.size(), "Wrong interactions size!");
		assertEquals(-1, theIntersections.get(0).getT(), "Wrong 1st interaction!");
		assertEquals(1, theIntersections.get(1).getT(), "Wrong 2nd interaction!");
		
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
		List<Intersection> theIntersections = SphereOperations.intersects(aSphere, aRay);
		
		// Even though it truly intersects at only a single point, for simplicity’s sake you’ll have 
		// your code return two intersections, with the same point at each
		assertEquals(2, theIntersections.size(), "Wrong interactions size!");
		assertEquals(-6, theIntersections.get(0).getT(), "Wrong 1st interaction!");
		assertEquals(-4, theIntersections.get(1).getT(), "Wrong 2nd interaction!");
		
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
		Intersection anIntersection = Factory.intersection(3.5, aSphere);
		
		assertEquals(3.5, anIntersection.getT(), "Wrong t for the intersection!");
		assertEquals(aSphere, anIntersection.getObject(), "Wrong object for the intersection!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Aggregating intersections.
	 */
	@Test
	@Order(7)
	public void testImprovedIntersections() {
		logger.info(Constants.SEPARATOR_JUNIT + "Rays-Spheres intersections");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Sphere
		Sphere aSphere = Factory.sphere();
		
		// Create 2 Intersections
		Intersection intersection1 = Factory.intersection(1, aSphere);
		Intersection intersection2 = Factory.intersection(2, aSphere);
		
		// Aggregate them
		Intersections theIntersections = Factory.intersections(Arrays.asList(intersection1, intersection2));
		
		assertEquals(2, theIntersections.getIntersections().size(), "Wrong intersections size!");
		assertEquals(1, theIntersections.getIntersections().get(0).getT(), "Wrong t for the 1st intersection!");
		assertEquals(2, theIntersections.getIntersections().get(1).getT(), "Wrong t for the 2nd intersection!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Show how hit should behave in a few different situations.
	 */
	@Test
	@Order(8)
	public void testHit() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing hit() method");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Sphere
		Sphere aSphere = Factory.sphere();
		
		// Create 2 Intersections
		Intersection intersection1 = Factory.intersection(1, aSphere);
		Intersection intersection2 = Factory.intersection(2, aSphere);
		
		// Aggregate them
		Intersections theIntersections = Factory.intersections(Arrays.asList(intersection1, intersection2));
		
		// The hit, when all intersections have positive t
		Intersection theHit = SphereOperations.hit(theIntersections);
		
		assertEquals(intersection1, theHit, "Wrong hit!");
		
		// Create 2 Intersections
		intersection1 = Factory.intersection(-1, aSphere);
		intersection2 = Factory.intersection(1, aSphere);
		
		// Aggregate them
		theIntersections = Factory.intersections(Arrays.asList(intersection1, intersection2));
		
		// The hit, when some intersections have negative t
		theHit = SphereOperations.hit(theIntersections);
		
		assertEquals(intersection2, theHit, "Wrong hit!");
		
		// Create 2 Intersections
		intersection1 = Factory.intersection(-2, aSphere);
		intersection2 = Factory.intersection(-1, aSphere);
		
		// Aggregate them
		theIntersections = Factory.intersections(Arrays.asList(intersection1, intersection2));
		
		// The hit, when some intersections have negative t
		theHit = SphereOperations.hit(theIntersections);
		
		assertEquals(null, theHit, "Wrong hit!");
		
		// Create many Intersections
		intersection1 = Factory.intersection(5, aSphere);
		intersection2 = Factory.intersection(7, aSphere);
		Intersection intersection3 = Factory.intersection(-3, aSphere);
		Intersection intersection4 = Factory.intersection(2, aSphere);
		
		// Aggregate them
		theIntersections = Factory.intersections(Arrays.asList(intersection1, intersection2, intersection3, intersection4));
		
		// The hit, when some intersections have negative t
		theHit = SphereOperations.hit(theIntersections);
		
		assertEquals(intersection4, theHit, "Wrong hit!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Spheres transformations.
	 */
	@Test
	@Order(9)
	public void testhit() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing Spheres transformations");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Sphere
		Sphere aSphere = Factory.sphere();
		
		// A sphere's default transformation
		Matrix identityMatrix = Factory.identityMatrix();
		
		assertEquals(identityMatrix, aSphere.getTransform(), "Wrong default transformation!");
		
		// Change sphere's transformation
		Matrix translationMatrix = Factory.translationMatrix(2, 3, 4);
		
		// Changing a sphere's transformation
		aSphere.setTransform(translationMatrix);
		
		assertEquals(translationMatrix, aSphere.getTransform(), "Wrong updated transformation!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Make it so that your intersect function transforms the ray before doing the calculation.
	 */
	@Test
	@Order(10)
	public void testIntersectionsScaled() {
		logger.info(Constants.SEPARATOR_JUNIT + "Rays-Spheres interaction (scaled)");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create the Ray's origin
		Tuple origin = Factory.point(0, 0, -5);
		
		// Create the Ray's direction
		Tuple direction = Factory.vector(0, 0, 1);
		
		// Create the Ray
		Ray aRay = Factory.ray(origin, direction);
		
		// Create a Sphere
		Sphere aSphere = Factory.sphere();
		
		// Update Sphere's transformation
		aSphere.setTransform(Factory.scalingMatrix(2, 2, 2));
		
		// Intersecting a scaled sphere with a ray
		List<Intersection> theIntersections = SphereOperations.intersects(aSphere, aRay);
		
		assertEquals(2, theIntersections.size(), "Wrong intersections size!");
		assertEquals(3, theIntersections.get(0).getT(), "Wrong 1st intersection!");
		assertEquals(7, theIntersections.get(1).getT(), "Wrong 2nd intersection!");
		
		// Update Sphere's transformation
		aSphere.setTransform(Factory.translationMatrix(5, 0, 0));
		
		// Intersecting a translated sphere with a ray
		theIntersections = SphereOperations.intersects(aSphere, aRay);
		
		assertEquals(0, theIntersections.size(), "Wrong intersections size!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Computing the normal at various points on a sphere.
	 */
	@Test
	@Order(11)
	public void testNormals() {
		logger.info(Constants.SEPARATOR_JUNIT + "The normal on a sphere at a point");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Sphere
		Sphere aSphere = Factory.sphere();
		
		// Get a Point
		Tuple aPoint = Factory.point(1, 0, 0);
		
		// The normal on a sphere at a point on the x axis
		Tuple normal = SphereOperations.normalAt(aSphere, aPoint);
		
		Tuple expectedResult = Factory.vector(1, 0, 0);
		assertEquals(expectedResult, normal, "Wrong normal for the X axis!");
		
		// Get a Point
		aPoint = Factory.point(0, 1, 0);
		
		// The normal on a sphere at a point on the y axis
		normal = SphereOperations.normalAt(aSphere, aPoint);
		
		expectedResult = Factory.vector(0, 1, 0);
		assertEquals(expectedResult, normal, "Wrong normal for the Y axis!");
		
		// Get a Point
		aPoint = Factory.point(0, 0, 1);
		
		// The normal on a sphere at a point on the z axis
		normal = SphereOperations.normalAt(aSphere, aPoint);
		
		expectedResult = Factory.vector(0, 0, 1);
		assertEquals(expectedResult, normal, "Wrong normal for the Z axis!");
		
		// Get a Point
		aPoint = Factory.point(Math.sqrt(3) / 3, Math.sqrt(3) / 3, Math.sqrt(3) / 3);
		
		// The normal on a sphere at a nonaxial point
		normal = SphereOperations.normalAt(aSphere, aPoint);
		
		expectedResult = Factory.vector(Math.sqrt(3) / 3, Math.sqrt(3) / 3, Math.sqrt(3) / 3);
		assertEquals(expectedResult, normal, "Wrong normal for the point!");
		
		// The normal is a normalized vector
		Tuple normalized = TupleOperations.normalize(normal);
		
		assertEquals(normal, normalized, "The result isn't normalized!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Computing the normal first on a translated sphere and then on a scaled and rotated sphere.
	 */
	@Test
	@Order(12)
	public void testTransformedNormals() {
		logger.info(Constants.SEPARATOR_JUNIT + "The transformed normal on a sphere at a point");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Sphere
		Sphere aSphere = Factory.sphere();
		
		// Get a transformation Matrix
		Matrix translation = Factory.translationMatrix(0, 1, 0);
		
		// Set the Sphere's transformation
		aSphere.setTransform(translation);
		
		// Get a Point
		Tuple aPoint = Factory.point(0, 1.70711, -0.70711);
		
		// Computing the normal on a translated sphere
		Tuple normal = SphereOperations.normalAt(aSphere, aPoint);
		
		Tuple expectedResult = Factory.vector(0, 0.70711, -0.70711);
		assertEquals(expectedResult, normal, "Wrong normal for the translated Sphere!");
		
		// Get a transformation Matrix
		Matrix scaling = Factory.scalingMatrix(1, 0.5, 1);
		Matrix rotation = Factory.zRotationMatrix(Math.PI / 5);
		Matrix transformation = MatrixOperations.mul(scaling, rotation);
		
		// Set the Sphere's transformation
		aSphere.setTransform(transformation);
		
		// Get a Point
		aPoint = Factory.point(0, Math.sqrt(2) / 2, -Math.sqrt(2) / 2);
		
		// Computing the normal on a transformed sphere
		normal = SphereOperations.normalAt(aSphere, aPoint);
		
		expectedResult = Factory.vector(0, 0.97014, -0.24254);
		assertEquals(expectedResult, normal, "Wrong normal for the transformed Sphere!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Shows the default values of each of the material’s attributes.
	 */
	@Test
	@Order(13)
	public void testMaterial() {
		logger.info(Constants.SEPARATOR_JUNIT + "The default material");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create the Material
		Material aMaterial = Factory.material();
		
		// The default material
		assertEquals(Factory.color(1, 1, 1), aMaterial.getColor(), "Wrong color for the Material!");
		assertEquals(0.1, aMaterial.getAmbient(), "Wrong ambient for the Material!");
		assertEquals(0.9, aMaterial.getDiffuse(), "Wrong diffuse for the Material!");
		assertEquals(0.9, aMaterial.getSpecular(), "Wrong specular for the Material!");
		assertEquals(200, aMaterial.getShininess(), "Wrong shininess for the Material!");
		
		// A sphere has a default material
		Sphere aSphere = Factory.sphere();
		
		assertEquals(Factory.material(), aSphere.getMaterial(), "Wrong Material for the Sphere!");
		
		// A sphere may be assigned a material
		aMaterial.setAmbient(1);
		aSphere.setMaterial(aMaterial);
		
		assertEquals(aMaterial, aSphere.getMaterial(), "Wrong Material for the Sphere!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}

}
