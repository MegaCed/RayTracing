package com.raytracer.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Intersections;
import com.raytracer.engine.element.Material;
import com.raytracer.engine.element.Ray;
import com.raytracer.engine.element.Sphere;
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
		Object[] objects = new Object[2];
		objects[0] = sphere1;
		objects[1] = sphere2;
		theWorld.setObjects(objects);
		
		assertEquals(Factory.pointLight(Factory.point(-10, 10, -10), Factory.color(1, 1, 1)), theWorld.getLight(), "Wrong Light for the World!");
		assertEquals(2, theWorld.getObjects().length, "Wrong number of objects for the World!");
		
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
		Object[] objects = new Object[2];
		objects[0] = sphere1;
		objects[1] = sphere2;
		theWorld.setObjects(objects);
		
		// Create a Ray
		Ray theRay = Factory.ray(Factory.point(0, 0, -5), Factory.vector(0, 0, 1));
		
		// In this case, since the ray passes through the origin (where both spheres are centered) 
		// it should intersect each sphere twice, for a total of four intersections.
		Intersections theIntersections = WorldOperations.intersectWorld(theWorld, theRay);
		
		assertEquals(4, theIntersections.getIntersections().length, "Wrong number of intersections for the World!");
		assertEquals(4, theIntersections.getIntersections()[0].getT(), "Wrong 1st ntersection for the World!");
		assertEquals(4.5, theIntersections.getIntersections()[1].getT(), "Wrong 2nd ntersection for the World!");
		assertEquals(5, theIntersections.getIntersections()[2].getT(), "Wrong 3rd ntersection for the World!");
		assertEquals(5.5, theIntersections.getIntersections()[3].getT(), "Wrong 4th ntersection for the World!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
}
