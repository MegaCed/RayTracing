package com.raytracer.engine.element;

import java.util.Arrays;

import com.raytracer.engine.misc.Constants;
import com.raytracer.engine.operation.MiscOperations;

/*
 * A matrix is a grid of numbers that you can manipulate as a single unit. 
 * For example, here’s a 2x2 matrix. It has two rows and two columns:
 * |3 1|
 * |2 7|
 * 
 * For your ray tracer, you’ll use primarily 4x4 matrices.
 */
public class Matrix {
	
	// The elements of this Matrix
	private double[][] elements;
	
	/*
	 * Constructor.
	 */
	public Matrix(double[][] elements) {
		this.setElements(elements);
	}
	
	/*
	 * Returns the number of rows of this Matrix.
	 */
	public int getRows() {
		return elements.length;
	}
	
	/*
	 * Returns the number of columns of this Matrix.
	 */
	public int getColumns() {
		return elements[0].length;
	}
	
	/*
	 * Returns the element at Y (row), X (column).
	 */
	public double getElement(int y, int x) {
		if (x <= getColumns() && y < getRows()) {
			return elements[y][x];
		}
		
		// TODO: Throw exception instead??
		return 0;
	}
	
	/*
	 * Set an element at a specific location.
	 */
	public void setElement(double element, int y, int x) {
		if (x <= getColumns() && y < getRows()) {
			elements[y][x] = element;
		}
	}

	/*
	 * Prints this Matrix.
	 */
	@Override
	public String toString() {
		return toString(Constants.MATRIX_NUMBER_LENGTH, Constants.MATRIX_NUMBER_PRECISION);
	}

	/*
	 * Prints this Matrix.
	 * With a specified precision, for pretty printing.
	 */
	public String toString(int size, int precision) {
		int ySize = getRows();
		int xSize = getColumns();
		
		StringBuffer result = new StringBuffer();
		result.append("Matrix (X=" + xSize + ", Y=" + ySize + ")" + Constants.END_OF_LINE);
		
		for (int row = 0; row < ySize; row++) {
			for (int col = 0; col < xSize; col++) {
				// Format the number
				String value = String.format("%" + size + "." + precision + "f", elements[row][col]);
				
				result.append(Constants.MATRIX_SEPARATOR + value);
			}
			
			result.append(Constants.MATRIX_SEPARATOR);
			
			// Add an end of line, except for the very last line
			if (row != ySize-1) {
				result.append(Constants.END_OF_LINE);
			}
		}
		
		return result.toString();
	}
	
	/*
	 * Test the equality of 2 Matrices.
	 */
	@Override
	public boolean equals(Object anObject) {
		if (this == anObject) {
			return true;
		}
		
		if (anObject instanceof Matrix) {
			Matrix anotherMatrix = (Matrix)anObject;
			
			// Compare sizes
			if ((getRows() != anotherMatrix.getRows())
					|| (getColumns() != anotherMatrix.getColumns())) {
				return false;
				
			}
			
			for (int row = 0; row < getRows(); row++) {
				for (int col = 0; col < getColumns(); col++) {
					double element1 = getElement(row, col);
					double element2 = anotherMatrix.getElement(row, col);
							
					if (!MiscOperations.equalsEpsilon(element1, element2)) {
						return false;
					}
				}
			}	
		}
		
		return true;	
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + Arrays.deepHashCode(elements);
		
		return result;
	}
	
	public double[][] getElements() {
		return elements;
	}

	public void setElements(double[][] elements) {
		this.elements = elements;
	}
	
}
