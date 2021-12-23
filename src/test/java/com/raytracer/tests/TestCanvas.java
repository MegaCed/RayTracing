package com.raytracer.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Canvas;
import com.raytracer.engine.element.Color;
import com.raytracer.engine.element.PortablePixmap;
import com.raytracer.engine.misc.Constants;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCanvas {
	
	private static Logger logger = LoggerFactory.getLogger(TestCanvas.class);
	
	/*
	 * Test the creation of a new Canvas.
	 */
	@Test
	@Order(1)
	public void testCreateCanvas() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing Canvas creation...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Create a Canvas
		Canvas aCanvas = Factory.canvas(10, 20);
		
		assertEquals(10, aCanvas.getWidth(), "Wrong width!");
		assertEquals(20, aCanvas.getHeight(), "Wrong height!");
		
		// Every pixel in the canvas should be initialized to black (color(0, 0, 0)
		for (int x = 0; x < aCanvas.getWidth(); x++) {
			for (int y = 0; y < aCanvas.getHeight(); y++) {
				Color pixelColor = aCanvas.pixelAt(x, y);
				
				assertEquals(0, pixelColor.getRed(), "Wrong red value!");
				assertEquals(0, pixelColor.getGreen(), "Wrong regreend value!");
				assertEquals(0, pixelColor.getBlue(), "Wrong blue value!");
			}
		}
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Test the modification of a Canvas.
	 */
	@Test
	@Order(2)
	public void testUpdateCanvas() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing Canvas update...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Canvas properties
		int width = 10;
		int height = 20;
		
		// Pixel properties
		int x = 2;
		int y = 3;
		
		// Create a Canvas
		Canvas aCanvas = Factory.canvas(width, height);
		
		// Create a color
		Color red = Factory.color(1, 0, 0);
		
		// Update the Canvas
		aCanvas.writePixel(x, y, red);
		
		// Retrieve the Color
		Color pixelColor = aCanvas.pixelAt(x, y);
		
		assertEquals(1, pixelColor.getRed(), "Wrong red value!");
		assertEquals(0, pixelColor.getGreen(), "Wrong regreend value!");
		assertEquals(0, pixelColor.getBlue(), "Wrong blue value!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Test the creation of a PPM file.
	 */
	@Test
	@Order(3)
	public void testPPM() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing PPM files...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Canvas properties
		int width = 5;
		int height = 3;
		
		// Create a Canvas
		Canvas aCanvas = Factory.canvas(width, height);
		
		// Create some colors
		Color color1 = Factory.color(1.5f, 0, 0);
		Color color2 = Factory.color(0, 0.5f, 0);
		Color color3 = Factory.color(-0.5f, 0, 1);
		
		// Update the Canvas
		aCanvas.writePixel(0, 0, color1);
		aCanvas.writePixel(2, 1, color2);
		aCanvas.writePixel(4, 2, color3);
		
		// Create a PPM
		PortablePixmap ppmFile = aCanvas.canvasToPPM();
		
		// Check the header
		String header = "P3\n"
				+ "5 3\n"
				+ "255\n";
		
		assertEquals(header, ppmFile.getHeader(), "Wrong PPM header!");
		
		String data = "255 0 0 0 0 0 0 0 0 0 0 0 0 0 0 \n"
				+ "0 0 0 0 0 0 0 128 0 0 0 0 0 0 0 \n"
				+ "0 0 0 0 0 0 0 0 0 0 0 0 0 0 255 \n";
		
		assertEquals(data, ppmFile.getData(), "Wrong PPM data!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}
	
	/*
	 * Test big PPM files creation.
	 */
	@Test
	@Order(4)
	public void testBigPPM() {
		logger.info(Constants.SEPARATOR_JUNIT + "Testing big PPM files...");
		logger.info(Constants.SEPARATOR_JUNIT);
		
		// Canvas properties
		int width = 10;
		int height = 2;
		
		// Create a Canvas
		Canvas aCanvas = Factory.canvas(width, height);
		
		// Create a color
		Color aColor = Factory.color(1, 0.8f, 0.6f);
		
		// Initialize the Canvas
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				aCanvas.writePixel(x, y, aColor);
			}
		}
		
		// Create a PPM
		PortablePixmap ppmFile = aCanvas.canvasToPPM();
		
		String data = "255 204 153 255 204 153 255 204 153 255 204 153 255 204 153 255 204\n"
				+ " 153 255 204 153 255 204 153 255 204 153 255 204 153 \n"
				+ "255 204 153 255 204 153 255 204 153 255 204 153 255 204 153 255 204\n"
				+ " 153 255 204 153 255 204 153 255 204 153 255 204 153 \n";
		
		assertEquals(data, ppmFile.getData(), "Wrong PPM data!");
		
		logger.info(Constants.SEPARATOR_JUNIT);
	}

}
