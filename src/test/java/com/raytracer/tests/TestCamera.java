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
import com.raytracer.engine.misc.Constants;
import com.raytracer.engine.operation.MiscOperations;

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
		
		assertEquals(true, MiscOperations.equalsEpsilon(0.1, camera.getPixelSize()), "Wrong pixel size");
		
		// The pixel size for a vertical canvas
		camera = Factory.camera(125, 200, Math.PI / 2);
		
		assertEquals(true, MiscOperations.equalsEpsilon(0.1, camera.getPixelSize()), "Wrong pixel size");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}

}
