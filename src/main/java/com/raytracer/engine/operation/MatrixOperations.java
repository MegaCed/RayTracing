package com.raytracer.engine.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.element.Matrix;
import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.misc.Constants;

/*
 * Performs various operations on matrices.
 * One of the pillars of linear algebra.
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
		logger.debug(Constants.SEPARATOR_OPERATION + "Comparing 2 matrices...");
		logger.debug("1st Matrix: " + matrix1);
		logger.debug("2nd Matrix: " + matrix2);
		
		boolean result = matrix1.equals(matrix2);
		
		if (result) {
			logger.debug(Constants.SEPARATOR_RESULT + "Matrices are identical!");
		} else {
			logger.debug(Constants.SEPARATOR_RESULT + "Matrices are different!");
		}
		
		return result;
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
		logger.debug(Constants.SEPARATOR_OPERATION + "Multiplying 2 matrices...");
		logger.debug("1st Matrix: " + matrix1);
		logger.debug("2nd Matrix: " + matrix2);

		// Multiply the 2 arrays of elements
		double[][] elements = mul(matrix1.getElements(), matrix2.getElements());
		
		Matrix result = new Matrix(elements);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Result of multiplication = " + result);
		return result;
	}
	
	/*
	 * Matrices can actually be multiplied by tuples, in addition to other matrices. 
	 * Multiplying a matrix by a tuple produces another tuple.
	 * 
	 * The trick begins by treating the tuple as a really skinny (one column!) matrix.
	 */
	public static Tuple mul(Matrix aMatrix, Tuple aTuple) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Multiplying a Matrix by a Tuple...");
		logger.debug("Matrix: " + aMatrix);
		logger.debug("Tuple: " + aTuple);

		// Multiply the 2 arrays of elements
		double[][] elements = mul(aMatrix.getElements(), aTuple.asArray());

		Tuple result = new Tuple(elements);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Result of multiplication = " + result);
		return result;
	}
	
	/*
	 * Common method for multiplying arrays.
	 */
	private static double[][] mul(double[][] array1, double[][] array2) {
		// # of lines is always the same for the 2 arrays
		int lines = array1.length;
		
		// # of columns can be different, because the 2nd array could be a Tuple
		int columns = array2[0].length;
		
		// Array of results
		double[][] elements = new double[lines][columns];
		
		for (int row = 0; row < lines; row++) {
			for (int col = 0; col < columns; col++) {
				double value = 0;
				
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
		logger.debug(Constants.SEPARATOR_OPERATION + "Transposing Matrix...");
		logger.debug("Matrix: " + aMatrix);
		
		int rows = aMatrix.getRows();
		int columns = aMatrix.getColumns();
		
		// Array of results
		double[][] elements = new double[rows][columns];
		
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				// Transposing a matrix turns the first row into the first column, the second row 
				// into the second column, and so forth.
				elements[col][row] = aMatrix.getElement(row, col);
			}
		}
		
		Matrix result = new Matrix(elements);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Result of transposition = " + result);
		return result;
	}
	
	/*
	 * The determinant is a number that is derived from the elements of a matrix.
	 * 
	 * The name comes from the use of matrices to solve systems of equations, where it’s used to 
	 * determine whether or not the system has a solution. If the determinant is zero, then the 
	 * corresponding system of equations has no solution.
	 */
	public static double determinant(Matrix aMatrix) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Determining  Matrix Determinant...");
		logger.debug("Matrix: " + aMatrix);
		
		double determinant = 0;
		
		if (aMatrix.getRows() == 2) {
			// 2x2 Matrix
			determinant = determinantSmallMatrix(aMatrix);
		} else if (aMatrix.getRows() > 2) {
			// 3x3 or 4x4 Matrix
			determinant = determinantBigMatrix(aMatrix);
		}
		
		if (determinant == 0) {
			logger.debug("Matrix is NOT invertible");
		} else {
			logger.debug("Matrix IS invertible");
		}
		
		logger.debug(Constants.SEPARATOR_RESULT + "Determinant = " + determinant);
		return determinant;
	}
	
	/*
	 * Find the Determinant of small matrices (2x2).
	 */
	private static double determinantSmallMatrix(Matrix aMatrix) {
		logger.debug("(Determinant for small matrices)");
		
		// Determinant | a b | = ad - bc
		//             | c d |
		double determinant = 
				(aMatrix.getElement(0, 0) * aMatrix.getElement(1, 1))
				- (aMatrix.getElement(0, 1) * aMatrix.getElement(1, 0));
		
		return determinant;
	}
	
	/*
	 * Finding the determinant of matrices larger than 2x2 works recursively.
	 * To find the determinant, look at any one of the rows or columns. It really doesn’t matter
	 * which
	 * Then, for each of those elements, you’ll multiply the element by its cofactor, and add the 
	 * products together
	 * And that’s the determinant!
	 */
	private static double determinantBigMatrix(Matrix aMatrix) {
		logger.debug("(Determinant for big matrices)");
		
		double determinant = 0;
		
		// You only need to look at a single row or column, so let’s choose the first row
		int firstRow = 0;
		
		for (int col=0; col<aMatrix.getColumns(); col++) {
			// Then, multiply each element by its cofactor, and add the results.
			determinant += aMatrix.getElement(firstRow, col) * cofactor(aMatrix, firstRow, col);
		}
		
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
		logger.debug(Constants.SEPARATOR_OPERATION + "Extracting submatrix...");
		logger.debug("Matrix: " + aMatrix);
		logger.debug("Row to delete: " + rowToDelete + " - Column to delete: " + columnToDelete);
		
		int rowsCount = aMatrix.getRows();
		int columnsCount = aMatrix.getColumns();
		
		// Reduced array of results
		double[][] elements = new double[rowsCount-1][columnsCount-1];
		
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
		
		logger.debug(Constants.SEPARATOR_RESULT + "Submatrix = " + result);
		return result;
	}
	
	/*
	 * The minor of an element at row i and column j is the determinant of the submatrix at (i,j).
	 * 
	 * You find the submatrix at the given location, and then compute the determinant of that 
	 * submatrix. The answer is the minor.
	 * (You have to admit: “minor” is easier to say than “determinant of the submatrix.”)
	 */
	public static double minor(Matrix aMatrix, int row, int col) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Finding Matrix Minor...");
		logger.debug("Matrix: " + aMatrix);
		logger.debug("Row: " + row + " - Column: " + col);
		
		// 1. Find the submatrix
		Matrix submatrix = submatrix(aMatrix, row, col);
		
		// 2. Find the determinant of the submatrix
		double minor = determinant(submatrix);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Minor = " + minor);
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
	public static double cofactor(Matrix aMatrix, int row, int col) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Finding Matrix Cofactor...");
		logger.debug("Matrix: " + aMatrix);
		logger.debug("Row: " + row + " - Column: " + col);
		
		// 1. Find the Minor
		double minor = minor(aMatrix, row, col);
		double cofactor = minor;
		
		// If row + column is an odd number, then you negate the minor.
		// Otherwise, you just return the minor as is
		// Also, don't negate '0'
		if ((((row + col) & 1) != 0) && (cofactor != 0)) {
			// You can use the modulus operator, but that can be slow.
			// The low bit will always be set on an odd number.
			cofactor = -cofactor;
		}
		
		logger.debug(Constants.SEPARATOR_RESULT + "Cofactor = " + cofactor);
		return cofactor;
	}
	
	/*
	 * inversion is the operation that allows you to reverse the effect of multiplying by a matrix. 
	 * It’ll be crucial to the transformation of shapes in your ray tracer, allowing you to move 
	 * shapes around, make them bigger or smaller, rotate them, and more.
	 * 
	 * One of the tricky things about matrix inversion is that not every matrix is invertible.
	 * 
	 * 1. you create a matrix that consists of the cofactors of each of the original elements
	 * 2. then, transpose that cofactor matrix
	 * 3. Finally, divide each of the resulting elements by the determinant of the original matrix
	 */
	public static Matrix inverse(Matrix aMatrix) {
		// Fail if the Matrix is not invertible
		double determinant = determinant(aMatrix);
		if (determinant == 0) {
			// An exception would be better...
			return null;
		}
		
		// New matrix of same size
		double[][] elements = new double[aMatrix.getRows()][aMatrix.getColumns()];
		
		for (int row=0; row<aMatrix.getRows(); row++) {
			for (int col=0; col<aMatrix.getColumns(); col++) {
				double cofactor = cofactor(aMatrix, row, col);
				
				// Note that "col, row" here, instead of "row, col" accomplishes the transpose 
				// operation!
				elements[col][row] = cofactor / determinant;
			}
		}
		
		Matrix inverse = new Matrix(elements);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Matrix inverse = " + inverse);
		return inverse;
	}
	
}
