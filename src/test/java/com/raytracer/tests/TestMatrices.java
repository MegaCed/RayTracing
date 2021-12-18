package com.raytracer.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Matrix;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestMatrices {
	
	private static Logger logger = LoggerFactory.getLogger(TestMatrices.class);
	
	/*
	 * Constructing and inspecting a 4x4 matrix
	 */
	@Test
	@Order(1)
	public void testCreateMatrix() {
		logger.info("-----");
		logger.info("Testing Matrix...");
		logger.info("-----");
		
		// Create the elements
		float[][] values = {
				{1, 2, 3, 4},
				{5.5f, 6.5f, 7.5f, 8.5f},
				{9, 10, 11, 12},
				{13.5f, 14.5f, 15.5f, 16.5f}
		};
		
		// Create a Matrix
		Matrix aMatrix = Factory.matrix(values);
		
		assertEquals(1, aMatrix.getElement(0,0), "Wrong value!");
		assertEquals(4, aMatrix.getElement(0,3), "Wrong value!");
		
		/*
		Then M[0,0] = 1
				And M[0,3] = 4
				And M[1,0] = 5.5
				And M[1,2] = 7.5
				And M[2,2] = 11
				And M[3,0] = 13.5
				And M[3,2] = 15.5
		*/
	
	}

}
