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
import com.raytracer.engine.element.Material;
import com.raytracer.engine.element.Matrix;
import com.raytracer.engine.element.Ray;
import com.raytracer.engine.element.Shape;
import com.raytracer.engine.element.Sphere;
import com.raytracer.engine.misc.Constants;
import com.raytracer.engine.operation.SphereOperations;

/*
 * Testing the engine's Shapes.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestShapes {
	
	private static Logger logger = LoggerFactory.getLogger(TestShapes.class);
	
	/*
	 * Creates a default Object, for testing purpose.
	 */
	private Shape getTestShape() {
		return Factory.sphere();
	}
	
	/*
	 * A Shape has a default transformation and that the transformation is assignable.
	 */
	@Test
	@Order(1)
	public void testDefaultShape() {
		logger.info(Constants.SEPARATOR_JUNIT + "Default transformation for Shapes");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		Shape aShape = getTestShape();
		
		// The default transformation
		assertEquals(Factory.identityMatrix(), aShape.getTransform(), "Wrong default transformation!");
		
		// Change Shaoe's transformation
		Matrix translationMatrix = Factory.translationMatrix(2, 3, 4);
		
		// Assigning a transformation
		aShape.setTransform(translationMatrix);
		
		assertEquals(translationMatrix, aShape.getTransform(), "Wrong updated transformation!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Shows the default values of each of the material’s attributes.
	 */
	@Test
	@Order(2)
	public void testMaterial() {
		logger.info(Constants.SEPARATOR_JUNIT + "The default material for Shapes");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		Shape aShape = getTestShape();
		
		// Create the Material
		Material defaultMaterial = aShape.getMaterial();
		
		// The default material
		assertEquals(Factory.color(1, 1, 1), defaultMaterial.getColor(), "Wrong color for the Material!");
		assertEquals(0.1, defaultMaterial.getAmbient(), "Wrong ambient for the Material!");
		assertEquals(0.9, defaultMaterial.getDiffuse(), "Wrong diffuse for the Material!");
		assertEquals(0.9, defaultMaterial.getSpecular(), "Wrong specular for the Material!");
		assertEquals(200, defaultMaterial.getShininess(), "Wrong shininess for the Material!");
		
		assertEquals(Factory.material(), aShape.getMaterial(), "Wrong Material for the Shape!");
		
		// Create another Material
		Material aMaterial = Factory.material();
		
		// A sphere may be assigned a material
		aMaterial.setAmbient(1);
		aShape.setMaterial(aMaterial);
		
		assertEquals(aMaterial, aShape.getMaterial(), "Wrong Material for the Shape!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Intersecting a scaled sphere with a ray
	 */
	@Test
	@Order(3)
	public void testScaledIntersection() {
		logger.info(Constants.SEPARATOR_JUNIT + "Intersecting a scaled shape with a ray");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		Ray aRay = Factory.ray(Factory.point(0, 0, -5), Factory.vector(0, 0, 1));
		
		Shape aShape = getTestShape();
		
		Matrix scalingMatrix = Factory.scalingMatrix(2, 2, 2);
		aShape.setTransform(scalingMatrix);
		
		List<Intersection> theIntersections = aShape.getOperations().intersects(aShape, aRay);
		
		// All you need to know is whether the localRay parameter to localIntersect() has been 
		// transformed appropriately
		assertEquals(Factory.point(0, 0, -2.5), aShape.getSavedRay().getOrigin(), "Wrong origin for the saved Ray!");
		assertEquals(Factory.vector(0, 0, 0.5), aShape.getSavedRay().getDirection(), "Wrong direction for the saved Ray!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Intersecting a translated shape with a ray
	 */
	@Test
	@Order(4)
	public void testTranslatedIntersection() {
		logger.info(Constants.SEPARATOR_JUNIT + "Intersecting a translated shape with a ray");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		Ray aRay = Factory.ray(Factory.point(0, 0, -5), Factory.vector(0, 0, 1));
		
		Shape aShape = getTestShape();
		
		Matrix translationMatrix = Factory.translationMatrix(5, 0, 0);
		aShape.setTransform(translationMatrix);
		
		List<Intersection> theIntersections = aShape.getOperations().intersects(aShape, aRay);
		
		// All you need to know is whether the localRay parameter to localIntersect() has been 
		// transformed appropriately
		assertEquals(Factory.point(-5, 0, -5), aShape.getSavedRay().getOrigin(), "Wrong origin for the saved Ray!");
		assertEquals(Factory.vector(0, 0, 1), aShape.getSavedRay().getDirection(), "Wrong direction for the saved Ray!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
}
