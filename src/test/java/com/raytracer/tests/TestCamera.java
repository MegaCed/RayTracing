package com.raytracer.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Camera;
import com.raytracer.engine.element.Matrix;
import com.raytracer.engine.element.Ray;
import com.raytracer.engine.misc.Constants;
import com.raytracer.engine.operation.MatrixOperations;
import com.raytracer.engine.operation.MiscOperations;
import com.raytracer.engine.operation.RayOperations;

/*
 * Testing Camera-related methods.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCamera {
	
	private static Logger logger = LoggerFactory.getLogger(TestCamera.class);
	
	/*
	 * Shows how a camera is constructed using a new function.
	 * It also shows that the default transform for a camera is the identity matrix.
	 */
	@Test
	@Order(1)
	public void testDefaultCamera() {
		logger.info(Constants.SEPARATOR_JUNIT + "Constructing a camera");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		double hSize = 160;
		double vSize = 120;
		double fieldOfView = Math.PI / 2;
		
		Camera camera = Factory.camera(hSize, vSize, fieldOfView);
		
		assertEquals(true, MiscOperations.equalsEpsilon(hSize, camera.gethSize()), "Wrong hSize");
		assertEquals(true, MiscOperations.equalsEpsilon(vSize, camera.getvSize()), "Wrong vSize");
		assertEquals(true, MiscOperations.equalsEpsilon(fieldOfView, camera.getFieldOfView()), "Wrong fieldOfView");
		assertEquals(Factory.identityMatrix(), camera.getTransform(), "Wrong transformation Matrix");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * The first step is to make sure the camera knows the size (in world-space units) of the pixels 
	 * on the canvas.
	 * The following two tests to show that the pixel size is calculated correctly for a canvas with 
	 * a horizontal aspect (hsize > vsize), and one with a vertical aspect (vsize > hsize).
	 */
	@Test
	@Order(2)
	public void testPixelSize() {
		logger.info(Constants.SEPARATOR_JUNIT + "The pixel size for a canvas");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// The pixel size for a horizontal canvas
		Camera camera = Factory.camera(200, 125, Math.PI / 2);
		
		assertEquals(true, MiscOperations.equalsEpsilon(0.01, camera.getPixelSize()), "Wrong pixel size");
		
		// The pixel size for a vertical canvas
		camera = Factory.camera(125, 200, Math.PI / 2);
		
		assertEquals(true, MiscOperations.equalsEpsilon(0.01, camera.getPixelSize()), "Wrong pixel size");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * The first two tests use an untransformed camera to cast rays through the center and corner of 
	 * the canvas, and the third tries a ray with a camera that has been translated and rotated.
	 */
	@Test
	@Order(3)
	public void testCameraRay() {
		logger.info(Constants.SEPARATOR_JUNIT + "Constructing rays");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		Camera camera = Factory.camera(201, 101, Math.PI / 2);
		
		// Constructing a ray through the center of the canvas
		Ray theRay = RayOperations.rayForPixel(camera, 100, 50);
		
		assertEquals(Factory.point(0, 0, 0), theRay.getOrigin(), "Wrong origin!");
		assertEquals(Factory.vector(0, 0, -1), theRay.getDirection(), "Wrong direction!");
		
		// Constructing a ray through a corner of the canvas
		theRay = RayOperations.rayForPixel(camera, 0, 0);
		
		assertEquals(Factory.point(0, 0, 0), theRay.getOrigin(), "Wrong origin!");
		assertEquals(Factory.vector(0.66519, 0.33259, -0.66851), theRay.getDirection(), "Wrong direction!");
		
		// Constructing a ray when the camera is transformed
		Matrix transformation = MatrixOperations.mul(Factory.yRotationMatrix(Math.PI / 4), Factory.translationMatrix(0, -2, 5));
		// Remember that the camera’s transformation describes how the world is moved relative to 
		// the camera.
		// Further, you’re transforming everything by the inverse of that transformation, so moving 
		// the world (0, -2, 5) is effectively the same as moving the ray’s origin in the opposite 
		// direction: (0, 2, -5).
		camera.setTransform(transformation);
		
		theRay = RayOperations.rayForPixel(camera, 100, 50);
		
		assertEquals(Factory.point(0, 2, -5), theRay.getOrigin(), "Wrong origin!");
		assertEquals(Factory.vector(Math.sqrt(2) / 2, 0, -Math.sqrt(2) / 2), theRay.getDirection(), "Wrong direction!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}

}
