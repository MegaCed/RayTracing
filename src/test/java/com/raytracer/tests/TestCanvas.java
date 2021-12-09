package com.raytracer.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.element.Canvas;
import com.raytracer.engine.element.Color;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCanvas {
	
	private static Logger logger = LoggerFactory.getLogger(TestCanvas.class);
	
	/*
	 * Test the creation of a new Canvas.
	 */
	@Test
	@Order(1)
	public void testCreateCanvas() {
		logger.info("-----");
		logger.info("Testing Canvas...");
		logger.info("-----");
		
		// Create a Canvas
		Canvas aCanvas = new Canvas(10, 20);
		
		assertEquals(10, aCanvas.getWidth(), "Wrong width!");
		assertEquals(20, aCanvas.getHeight(), "Wrong height!");
		
		// Every pixel in the canvas should be initialized to black (color(0, 0, 0)
		for (int x = 0; x < aCanvas.getWidth(); x++) {
			for (int y = 0; y < aCanvas.getHeight(); y++) {
				Color pixelColor = aCanvas.getColorAt(x, y);
				
				assertEquals(0, pixelColor.getRed(), "Wrong red value!");
				assertEquals(0, pixelColor.getGreen(), "Wrong regreend value!");
				assertEquals(0, pixelColor.getBlue(), "Wrong blue value!");
			}
		}
	}

}
