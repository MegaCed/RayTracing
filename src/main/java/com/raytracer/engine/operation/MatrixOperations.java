package com.raytracer.engine.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.element.Color;
import com.raytracer.engine.element.Matrix;
import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.misc.Constants;

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
		
		// Supposing identical sizes
		int size = matrix1.getRows();
		
		// Array of results
		float[][] elements = new float[size][size];
		
		// Matrix multiplication computes the dot product of every row-column combination in the two 
		// matrices!
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				float value = 0;
				
				for (int x = 0; x < size; x++) {
					value += matrix1.getElement(row, x) * matrix2.getElement(x, col);
				}
				
				elements[row][col] = value;
			}
		}

		Matrix result = new Matrix(elements);
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
		
		int size = aMatrix.getRows();
		
		// Array of results
		float[] result = new float[size];
		
		// Transform the Tuple to an array
		float[] tupleElements = aTuple.asArray();
		
		for (int row = 0; row < size; row++) {
			float value = 0;
			
			for (int x = 0; x < size; x++) {
				value += aMatrix.getElement(row, x) * tupleElements[x];
			}
			
			result[row] = value;
		}

		Tuple resultTuple = new Tuple(result);
		return resultTuple;
	}
	
}
