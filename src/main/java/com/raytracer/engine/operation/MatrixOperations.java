package com.raytracer.engine.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.element.Matrix;
import com.raytracer.engine.element.Tuple;

/*
 * Performs various operations on matrices.
 */
public class MatrixOperations {

	private static Logger logger = LoggerFactory.getLogger(MatrixOperations.class);
	
	/*
	 * Comparing matrices.
	 * 
	 * Critical part of your matrix implementation is matrix comparison. You’ll be comparing 
	 * matrices a lot.
	 */
	public static boolean equals(Matrix matrix1, Matrix matrix2) {
		logger.debug("Comparing 2 matrices...");
		logger.debug("1st Matrix: " + matrix1);
		logger.debug("2nd Matrix: " + matrix2);
		
		// Compare sizes
		if ((matrix1.getRows() != matrix2.getRows())
				|| (matrix1.getColumns() != matrix2.getColumns())) {
			return false;
			
		}
		
		for (int row = 0; row < matrix1.getRows(); row++) {
			for (int col = 0; col < matrix1.getColumns(); col++) {
				float element1 = matrix1.getElement(row, col);
				float element2 = matrix2.getElement(row, col);
						
				if (!MiscOperations.equalsEpsilon(element1, element2)) {
					return false;
				}
			}
		}
		
		logger.debug("Matrices are identical!");
		return true;
	}
	
	/*
	 * Multiplying matrices.
	 * 
	 * Multiplication is the tool you’ll use to perform transformations like scaling, rotation, and 
	 * translation. It’s certainly possible to apply them one at a time, sequentially, but in 
	 * practice you’ll often want to apply several transformations at once. Multiplying them 
	 * together is how you make that happen.
	 * 
	 * It takes two matrices and produces another matrix by multiplying their component elements 
	 * together in a specific way.
	 */
	public static Matrix mul(Matrix matrix1, Matrix matrix2) {
		logger.debug("Multiplying 2 matrices...");
		logger.debug("1st Matrix: " + matrix1);
		logger.debug("2nd Matrix: " + matrix2);

		// Multiply the 2 arrays of elements
		float[][] elements = mul(matrix1.getElements(), matrix2.getElements());
		
		Matrix result = new Matrix(elements);
		
		logger.debug("Result of multiplication: " + result);
		return result;
	}
	
	/*
	 * Matrices can actually be multiplied by tuples, in addition to other matrices. 
	 * Multiplying a matrix by a tuple produces another tuple.
	 * 
	 * The trick begins by treating the tuple as a really skinny (one column!) matrix.
	 */
	public static Tuple mul(Matrix aMatrix, Tuple aTuple) {
		logger.debug("Multiplying a Matrix by a Tuple...");
		logger.debug("Matrix: " + aMatrix);
		logger.debug("Tuple: " + aTuple);

		// Multiply the 2 arrays of elements
		float[][] elements = mul(aMatrix.getElements(), aTuple.asArray());

		Tuple result = new Tuple(elements);
		
		logger.debug("Result of multiplication: " + result);
		return result;
	}
	
	/*
	 * Common method for multiplying arrays.
	 */
	private static float[][] mul(float[][] array1, float[][] array2) {
		// # of lines is always the same for the 2 arrays
		int lines = array1.length;
		
		// # of columns can be different, because the 2nd array could be a Tuple
		int columns = array2[0].length;
		
		// Array of results
		float[][] elements = new float[lines][columns];
		
		for (int row = 0; row < lines; row++) {
			for (int col = 0; col < columns; col++) {
				float value = 0;
				
				// Matrix multiplication computes the dot product of every row-column combination in 
				// the two matrices!
				for (int x = 0; x < lines; x++) {
					// elements[row][col] = 
					// array1[row][0] * array2[0][col] +
					// array1[row][1] * array2[1][col] +
					// array1[row][2] * array2[2][col] +
					// array1[row][3] * array2[3][col]
					value += array1[row][x] * array2[x][col];
				}
				
				elements[row][col] = value;
			}
		}

		return elements;
	}
	
	/*
	 * When you transpose a matrix, you turn its rows into columns and its columns into rows.
	 * 
	 * Matrix transposition will come in handy when you get to Light and Shading. You’ll use it when 
	 * translating certain vectors (called normal vectors) between object space and world space.
	 */
	public static Matrix transpose(Matrix aMatrix) {
		logger.debug("Transposing Matrix...");
		logger.debug("Matrix: " + aMatrix);
		
		int rows = aMatrix.getRows();
		int columns = aMatrix.getColumns();
		
		// Array of results
		float[][] elements = new float[rows][columns];
		
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				// Transposing a matrix turns the first row into the first column, the second row 
				// into the second column, and so forth.
				elements[col][row] = aMatrix.getElement(row, col);
			}
		}
		
		Matrix result = new Matrix(elements);
		
		logger.debug("Result of transposition: " + result);
		return result;
	}
	
}
