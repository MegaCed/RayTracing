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
import com.raytracer.engine.operation.MiscOperations;

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
		
		// Find determinant
		float determinant = MatrixOperations.determinant(aMatrix);
		
		assertEquals(17, determinant, "Wrong result!");
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
	
	/*
	 * Finding a Minor.
	 */
	@Test
	@Order(9)
	public void testMinor() {
		logger.info("----- Matrix Minor...");
		logger.info("-----");
		
		// Create the elements
		float[][] values = {
				{3, 5, 0},
				{2, -1, -7},
				{6, -1, 5}
		};
		
		// Create a Matrix
		Matrix aMatrix = Factory.matrix(values);
		
		// Find Minor
		float result = MatrixOperations.minor(aMatrix, 1, 0);
		
		assertEquals(25, result, "Wrong value!");
	}
	
	/*
	 * Finding a Cofactor.
	 */
	@Test
	@Order(10)
	public void testCofactor() {
		logger.info("----- Matrix Cofactor...");
		logger.info("-----");
		
		// Create the elements
		float[][] values = {
				{3, 5, 0},
				{2, -1, -7},
				{6, -1, 5}
		};
		
		// Create a Matrix
		Matrix aMatrix = Factory.matrix(values);
		
		// Find Cofactor
		float result = MatrixOperations.cofactor(aMatrix, 0, 0);
		
		assertEquals(-12, result, "Wrong value!");
		
		// Find Cofactor
		result = MatrixOperations.cofactor(aMatrix, 1, 0);
		
		assertEquals(-25, result, "Wrong value!");
	}
	
	/*
	 * Finding the Determinant of larger Matrices.
	 */
	@Test
	@Order(11)
	public void testLargeMatrixDeterminant() {
		logger.info("----- Matrix Determinant of larger matrices...");
		logger.info("-----");
		
		// Create the elements (3x3)
		float[][] values3x3 = {
				{1, 2, 6},
				{-5, 8, -4},
				{2, 6, 4}
		};
		
		// Create a Matrix
		Matrix aMatrix = Factory.matrix(values3x3);
		
		// Find determinant
		float determinant = MatrixOperations.determinant(aMatrix);
		
		assertEquals(-196, determinant, "Wrong result!");
		
		// Create the elements (4x4)
		float[][] values4x4 = {
				{-2, -8, 3, 5},
				{-3, 1, 7, 3},
				{1, 2, -9, 6},
				{-6, 7, 7, -9}
		};
		
		// Create a Matrix
		aMatrix = Factory.matrix(values4x4);
		
		// Find determinant
		determinant = MatrixOperations.determinant(aMatrix);
		
		assertEquals(-4071, determinant, "Wrong result!");
	}
	
	/*
	 * Testing an invertible matrix for invertibility.
	 */
	@Test
	@Order(12)
	public void testInvertibility() {
		logger.info("----- Matrix invertibility...");
		logger.info("-----");
		
		// Create the elements
		float[][] values = {
				{6, 4, 4, 4},
				{5, 5, 7, 6},
				{4, -9, 3, -7},
				{9, 1, 7, -6}
		};
		
		// Create a Matrix
		Matrix aMatrix = Factory.matrix(values);
		
		// Testing an invertible matrix for invertibility
		float determinant = MatrixOperations.determinant(aMatrix);
		
		assertEquals(-2120, determinant, "Wrong value!");
		
		// Create the elements
		float[][] values2 = {
				{-4, 2, -2, 3},
				{9, 6, 2, 6},
				{0, -5, 1, -5},
				{0, 0, 0, 0}
		};
		
		// Create a Matrix
		aMatrix = Factory.matrix(values2);
		
		// Testing a noninvertible matrix for invertibility
		determinant = MatrixOperations.determinant(aMatrix);
		
		assertEquals(0, determinant, "Wrong value!");
	}
	
	/*
	 * Calculating the inverse of a matrix.
	 */
	@Test
	@Order(13)
	public void testInverse() {
		logger.info("----- Matrix inversion...");
		logger.info("-----");
		
		// Create the elements
		float[][] values = {
				{-5, 2, 6, -8},
				{1, -5, 1, 8},
				{7, 7, -6, -7},
				{1, -3, 7, 4}
		};
		
		// Create a Matrix
		Matrix aMatrix = Factory.matrix(values);
		
		// Check Determinant
		float determinant = MatrixOperations.determinant(aMatrix);
		
		assertEquals(532, determinant, "Wrong determinant!");
		
		// Check Cofactor
		float cofactor = MatrixOperations.cofactor(aMatrix, 2, 3);
		
		assertEquals(-160, cofactor, "Wrong cofactor!");
		
		// Check Cofactor
		cofactor = MatrixOperations.cofactor(aMatrix, 3, 2);
		
		assertEquals(105, cofactor, "Wrong cofactor!");
		
		// Calculate inverse
		Matrix inverse = MatrixOperations.inverse(aMatrix);
		
		// Compare result
		boolean row0col0 = MiscOperations.equalsEpsilon(inverse.getElement(0, 0), 0.21805f);
		boolean row1col1 = MiscOperations.equalsEpsilon(inverse.getElement(1, 1), -1.45677f);
		boolean row2col2 = MiscOperations.equalsEpsilon(inverse.getElement(2, 2), -0.05263f);
		boolean row3col3 = MiscOperations.equalsEpsilon(inverse.getElement(3, 3), 0.30639f);
		
		assertEquals(true, row0col0, "Wrong value!");
		assertEquals(true, row1col1, "Wrong value!");
		assertEquals(true, row2col2, "Wrong value!");
		assertEquals(true, row3col3, "Wrong value!");
	}
	
}
