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
		logger.debug("-> Comparing 2 matrices...");
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
		
		logger.debug("--> Matrices are identical!");
		return true;
	}
	
	/*
	 * Multiplication is the tool you’ll use to perform transformations like scaling, rotation, and 
	 * translation. It’s certainly possible to apply them one at a time, sequentially, but in 
	 * practice you’ll often want to apply several transformations at once. Multiplying them 
	 * together is how you make that happen.
	 * 
	 * It takes two matrices and produces another matrix by multiplying their component elements 
	 * together in a specific way.
	 */
	public static Matrix mul(Matrix matrix1, Matrix matrix2) {
		logger.debug("-> Multiplying 2 matrices...");
		logger.debug("1st Matrix: " + matrix1);
		logger.debug("2nd Matrix: " + matrix2);

		// Multiply the 2 arrays of elements
		float[][] elements = mul(matrix1.getElements(), matrix2.getElements());
		
		Matrix result = new Matrix(elements);
		
		logger.debug("--> Result of multiplication: " + result);
		return result;
	}
	
	/*
	 * Matrices can actually be multiplied by tuples, in addition to other matrices. 
	 * Multiplying a matrix by a tuple produces another tuple.
	 * 
	 * The trick begins by treating the tuple as a really skinny (one column!) matrix.
	 */
	public static Tuple mul(Matrix aMatrix, Tuple aTuple) {
		logger.debug("-> Multiplying a Matrix by a Tuple...");
		logger.debug("Matrix: " + aMatrix);
		logger.debug("Tuple: " + aTuple);

		// Multiply the 2 arrays of elements
		float[][] elements = mul(aMatrix.getElements(), aTuple.asArray());

		Tuple result = new Tuple(elements);
		
		logger.debug("--> Result of multiplication: " + result);
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
		logger.debug("-> Transposing Matrix...");
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
		
		logger.debug("--> Result of transposition: " + result);
		return result;
	}
	
	/*
	 * The determinant is a number that is derived from the elements of a matrix.
	 * 
	 * The name comes from the use of matrices to solve systems of equations, where it’s used to 
	 * determine whether or not the system has a solution. If the determinant is zero, then the 
	 * corresponding system of equations has no solution.
	 */
	public static float determinant(Matrix aMatrix) {
		logger.debug("-> Determining  Matrix Determinant...");
		logger.debug("Matrix: " + aMatrix);
		
		// Determinant | a b | = ad - bc
		//             | c d |
		float determinant = 
				(aMatrix.getElement(0, 0) * aMatrix.getElement(1, 1))
				- (aMatrix.getElement(0, 1) * aMatrix.getElement(1, 0));
		
		
		logger.debug("--> Determinant: " + determinant);
		return determinant;
	}
	
	/*
	 * A submatrix is what is left when you delete a single row and column from a matrix. 
	 * 
	 * Because you’re always removing one row and one column, it effectively reduces the size of the 
	 * matrix by one. The submatrix of a 4x4 matrix is 3x3, and the submatrix of a 3x3 matrix is 
	 * 2x2.
	 */
	public static Matrix submatrix(Matrix aMatrix, int rowToDelete, int columnToDelete) {
		logger.debug("-> Extracting submatrix...");
		logger.debug("Matrix: " + aMatrix);
		logger.debug("Row to delete: " + rowToDelete + " -- Column to delete: " + columnToDelete);
		
		int rowsCount = aMatrix.getRows();
		int columnsCount = aMatrix.getColumns();
		
		// Reduced array of results
		float[][] elements = new float[rowsCount-1][columnsCount-1];
		
		// Fill the new array excluding forbidden row & column
		for (int row = 0, subRow = 0; row < rowsCount; row++) {
			if (row != rowToDelete) {
				for (int col = 0, subCol = 0; col < columnsCount; col++) {
					if (col != columnToDelete) {
						elements[subRow][subCol] = aMatrix.getElement(row, col);
						
						// Increment submatrix column index
						subCol++;
					}
				}
				
				// Increment submatrix row index
				subRow++;
			}
		}
		
		Matrix result = new Matrix(elements);
		
		logger.debug("--> Resulting submatrix: " + result);
		return result;
	}
	
	/*
	 * The minor of an element at row i and column j is the determinant of the submatrix at (i,j).
	 * 
	 * You find the submatrix at the given location, and then compute the determinant of that 
	 * submatrix. The answer is the minor.
	 * (You have to admit: “minor” is easier to say than “determinant of the submatrix.”)
	 */
	public static float minor(Matrix aMatrix, int row, int col) {
		logger.debug("-> Finding Matrix Minor...");
		logger.debug("Matrix: " + aMatrix);
		logger.debug("Row: " + row + " -- Column: " + col);
		
		// 1. Find the submatrix
		Matrix submatrix = submatrix(aMatrix, row, col);
		
		// 2. Find the determinant of the submatrix
		float minor = determinant(submatrix);
		
		logger.debug("--> Minor: " + minor);
		return minor;
	}
	
	/*
	 * Cofactors are minors that have (possibly) had their sign changed.
	 * 
	 * First you compute the minor at the given row and column. Then you consider that row and 
	 * column to determine whether or not to negate the result.
	 * 
	 * The following figure is helpful:
	 * |+ - +|
	 * |- + -|
	 * |+ - +|
	 * If the row and column identifies a spot with a +, then the minor’s sign doesn’t change. 
	 * If the row and column identifies a spot with a -, then you negate the minor.
	 */
	public static float cofactor(Matrix aMatrix, int row, int col) {
		logger.debug("-> Finding Matrix Cofactor...");
		logger.debug("Matrix: " + aMatrix);
		logger.debug("Row: " + row + " -- Column: " + col);
		
		// 1. Find the Minor
		float minor = minor(aMatrix, row, col);
		
		// If row + column is an odd number, then you negate the minor. Otherwise, you just return 
		// the minor as is
		float cofactor = minor;
		if (((int)cofactor & 1) != 0) {
			// You can use the modulus operator, but that can be slow.
			// The low bit will always be set on an odd number.
			cofactor = -cofactor;
		}
		
		logger.debug("--> Cofactor: " + cofactor);
		return cofactor;
	}
	
}
