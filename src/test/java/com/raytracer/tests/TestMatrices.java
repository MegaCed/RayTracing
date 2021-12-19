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
import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.operation.MatrixOperations;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestMatrices {
	
	private static Logger logger = LoggerFactory.getLogger(TestMatrices.class);
	
	/*
	 * Constructing and inspecting Matrices.
	 */
	@Test
	@Order(1)
	public void testCreateMatrix() {
		logger.info("----- Testing Matrix...");
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
		assertEquals(5.5f, aMatrix.getElement(1,0), "Wrong value!");
		assertEquals(7.5f, aMatrix.getElement(1,2), "Wrong value!");
		assertEquals(11, aMatrix.getElement(2,2), "Wrong value!");
		assertEquals(13.5f, aMatrix.getElement(3,0), "Wrong value!");
		assertEquals(15.5f, aMatrix.getElement(3,2), "Wrong value!");
		
		// Create the elements
		float[][] values2 = {
				{-3, 5},
				{1, -2}
		};
		
		// Create a Matrix
		aMatrix = Factory.matrix(values2);
		
		assertEquals(-3, aMatrix.getElement(0,0), "Wrong value!");
		assertEquals(5, aMatrix.getElement(0,1), "Wrong value!");
		assertEquals(1, aMatrix.getElement(1,0), "Wrong value!");
		assertEquals(-2, aMatrix.getElement(1,1), "Wrong value!");
		
		// Create the elements
		float[][] values3 = {
				{-3, 5, 0},
				{1, -2, -7},
				{0, 1, 1}
		};
		
		// Create a Matrix
		aMatrix = Factory.matrix(values3);
		
		assertEquals(-3, aMatrix.getElement(0,0), "Wrong value!");
		assertEquals(-2, aMatrix.getElement(1,1), "Wrong value!");
		assertEquals(1, aMatrix.getElement(2,2), "Wrong value!");
		
		logger.info("-----");
	}
	
	/*
	 * Comparing matrices.
	 */
	@Test
	@Order(2)
	public void testCompareMatrix() {
		logger.info("----- Comparing Matrices...");
		logger.info("-----");
		
		// Create the elements
		float[][] values1 = {
				{1, 2, 3, 4},
				{5, 6, 7, 8},
				{9, 8, 7, 6},
				{5, 4, 3, 2}
		};
		
		// Create a Matrix
		Matrix matrix1 = Factory.matrix(values1);
		
		// Create other elements
		float[][] values2 = {
				{1, 2, 3, 4},
				{5, 6, 7, 8},
				{9, 8, 7, 6},
				{5, 4, 3, 2}
		};
		
		// Create another Matrix
		Matrix matrix2 = Factory.matrix(values2);
		
		// Compare them together
		boolean equality = MatrixOperations.equals(matrix1, matrix2);
		
		assertEquals(true, equality, "Matrices aren't equals!");
		
		// Create other elements
		float[][] values3 = {
				{2, 3, 4, 5},
				{6, 7, 8, 9},
				{8, 7, 6, 5},
				{4, 3, 2, 1}
		};
		
		// Create another Matrix
		Matrix matrix3 = Factory.matrix(values3);
		
		equality = MatrixOperations.equals(matrix1, matrix3);
		
		assertEquals(false, equality, "Matrices are equals!");
		
		logger.info("-----");
	}

	
	/*
	 * Test that describes what you expect to happen when you multiply two 4x4 matrices together.
	 */
	@Test
	@Order(3)
	public void testMultiplyMatrices() {
		logger.info("----- Multiplying Matrices...");
		logger.info("-----");
		
		// Create the elements
		float[][] values1 = {
				{1, 2, 3, 4},
				{5, 6, 7, 8},
				{9, 8, 7, 6},
				{5, 4, 3, 2}
		};
		
		// Create a Matrix
		Matrix matrix1 = Factory.matrix(values1);
		
		// Create other elements
		float[][] values2 = {
				{-2, 1, 2, 3},
				{3, 2, 1, -1},
				{4, 3, 6, 5},
				{1, 2, 7, 8}
		};
		
		// Create another Matrix
		Matrix matrix2 = Factory.matrix(values2);
		
		// Multiply them together
		Matrix result = MatrixOperations.mul(matrix1, matrix2);
		
		assertEquals(20, result.getElement(0,0), "Wrong value!");
		assertEquals(54, result.getElement(1,1), "Wrong value!");
		assertEquals(110, result.getElement(2,2), "Wrong value!");
		assertEquals(42, result.getElement(3,3), "Wrong value!");
		
		logger.info("-----");
	}
	
	/*
	 * Test that describes what you expect to happen when you multiply two 4x4 matrices together.
	 */
	@Test
	@Order(4)
	public void testMultiplyMatrixAndTuple() {
		logger.info("----- Multiplying Matrix and Tuple...");
		logger.info("-----");
		
		// Create the elements
		float[][] values = {
				{1, 2, 3, 4},
				{2, 4, 4, 2},
				{8, 6, 4, 1},
				{0, 0, 0, 1}
		};
		
		// Create a Matrix
		Matrix aMatrix = Factory.matrix(values);
		
		// Create a Tuple
		Tuple aTuple = new Tuple(1, 2, 3, 1);
		
		// Multiply them together
		Tuple result = MatrixOperations.mul(aMatrix, aTuple);
		
		assertEquals(18, result.getX(), "Wrong value!");
		assertEquals(24, result.getY(), "Wrong value!");
		assertEquals(33, result.getZ(), "Wrong value!");
		assertEquals(1, result.getW(), "Wrong value!");
		
		logger.info("-----");
	}
	
	/*
	 * The following tests illustrates the (non-)effect of multiplying by the identity matrix.
	 */
	@Test
	@Order(5)
	public void testIdentityMatrix() {
		logger.info("----- Multiplying Identity Matrix...");
		logger.info("-----");
		
		// Create the elements
		float[][] values = {
				{0, 1, 2, 4},
				{1, 2, 4, 8},
				{2, 4, 8, 10},
				{4, 8, 16, 32}
		};
		
		// Create a Matrix
		Matrix aMatrix = Factory.matrix(values);
		
		// Get the Identity Matrix
		Matrix identityMatrix = Factory.identityMatrix();
		
		// Multiply them together
		Matrix resultMatrix = MatrixOperations.mul(aMatrix, identityMatrix);
		
		assertEquals(0, resultMatrix.getElement(0,0), "Wrong value!");
		assertEquals(2, resultMatrix.getElement(1,1), "Wrong value!");
		assertEquals(8, resultMatrix.getElement(2,2), "Wrong value!");
		assertEquals(32, resultMatrix.getElement(3,3), "Wrong value!");
		
		// Create a Tuple
		Tuple aTuple = new Tuple(1, 2, 3, 4);
		
		// Multiply them together
		Tuple resultTuple = MatrixOperations.mul(identityMatrix, aTuple);
		
		assertEquals(1, resultTuple.getX(), "Wrong value!");
		assertEquals(2, resultTuple.getY(), "Wrong value!");
		assertEquals(3, resultTuple.getZ(), "Wrong value!");
		assertEquals(4, resultTuple.getW(), "Wrong value!");
		
		logger.info("-----");
	}
	
	/*
	 * Transposing a Matrix.
	 */
	@Test
	@Order(6)
	public void testTransposeMatrix() {
		logger.info("----- Transposing Matrix...");
		logger.info("-----");
		
		// Create the elements
		float[][] values = {
				{0, 9, 3, 0},
				{9, 8, 0, 8},
				{1, 8, 5, 3},
				{0, 0, 5, 8}
		};
		
		// Create a Matrix
		Matrix aMatrix = Factory.matrix(values);
		
		// Transpose it
		Matrix result = MatrixOperations.transpose(aMatrix);
		
		assertEquals(0, result.getElement(0,0), "Wrong value!");
		assertEquals(8, result.getElement(1,1), "Wrong value!");
		assertEquals(5, result.getElement(2,2), "Wrong value!");
		assertEquals(8, result.getElement(3,3), "Wrong value!");
		
		// And interestingly, the transpose of the identity matrix always gives you the identity 
		// matrix.
		Matrix identityMatrix = Factory.identityMatrix();
		
		// Transpose it
		result = MatrixOperations.transpose(identityMatrix);
		
		assertEquals(1, result.getElement(0,0), "Wrong value!");
		assertEquals(1, result.getElement(1,1), "Wrong value!");
		assertEquals(1, result.getElement(2,2), "Wrong value!");
		assertEquals(1, result.getElement(3,3), "Wrong value!");
		
		logger.info("-----");
	}
	
	/*
	 * Finding the Determinant of a Matrix.
	 */
	@Test
	@Order(7)
	public void testMatrixDeterminant() {
		logger.info("----- Matrix Determinant...");
		logger.info("-----");
		
		// Create the elements
		float[][] values = {
				{1, 5},
				{-3, 2}
		};
		
		// Create a Matrix
		Matrix aMatrix = Factory.matrix(values);
		
		// Transpose it
		float result = MatrixOperations.determinant(aMatrix);
		
		assertEquals(17, result, "Wrong result!");
	}
	
	/*
	 * Extracting submatrix.
	 */
	@Test
	@Order(8)
	public void testSubMatrix() {
		logger.info("----- Finding submatrix...");
		logger.info("-----");
		
		// Create the elements
		float[][] values = {
				{1, 5, 0},
				{-3, 2, 7},
				{0, 6, -3}
		};
		
		// Create a Matrix
		Matrix aMatrix = Factory.matrix(values);
		
		// Extract submatrix
		Matrix result = MatrixOperations.submatrix(aMatrix, 0, 2);
		
		// Returns a copy of the given matrix with the given row and column removed
		assertEquals(2, result.getRows(), "Wrong number of rows!");
		assertEquals(2, result.getColumns(), "Wrong number od columns!");
		assertEquals(-3, result.getElement(0,0), "Wrong value!");
		assertEquals(6, result.getElement(1,1), "Wrong value!");
		
		// Create the elements
		float[][] values2 = {
				{-6, 1, 1, 6},
				{-8, 5, 8, 6},
				{-1, 0, 8, 2},
				{-7, 1, -1, 1}
		};
		
		// Create a Matrix
		aMatrix = Factory.matrix(values2);
		
		// Extract submatrix
		result = MatrixOperations.submatrix(aMatrix, 2, 1);
		
		// Returns a copy of the given matrix with the given row and column removed
		assertEquals(3, result.getRows(), "Wrong number of rows!");
		assertEquals(3, result.getColumns(), "Wrong number od columns!");
		assertEquals(-6, result.getElement(0,0), "Wrong value!");
		assertEquals(8, result.getElement(1,1), "Wrong value!");
		assertEquals(1, result.getElement(2,2), "Wrong value!");
	}
	
}
