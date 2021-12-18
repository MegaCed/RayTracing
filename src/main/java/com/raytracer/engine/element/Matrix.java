package com.raytracer.engine.element;

import java.text.DecimalFormat;

import com.raytracer.engine.misc.Constants;

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
	private float[][] elements;
	
	/*
	 * Constructor.
	 */
	public Matrix(float[][] elements) {
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
	public float getElement(int y, int x) {
		if (x <= getColumns() && y < getRows()) {
			return elements[y][x];
		}
		
		// TODO: Throw exception instead??
		return 0;
	}

	/*
	 * Prints this Matrix.
	 */
	public String toString() {
		int ySize = getRows();
		int xSize = getColumns();
		
		StringBuffer result = new StringBuffer();
		result.append("Matrix (X=" + xSize + ", Y=" + ySize + ")" + Constants.END_OF_LINE);
		for (int row = 0; row < ySize; row++) {
			for (int col = 0; col < xSize; col++) {
				result.append(Constants.MATRIX_SEPARATOR + elements[row][col]);
			}
			result.append(Constants.MATRIX_SEPARATOR + Constants.END_OF_LINE);
		}
		
		return result.toString();
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
			result.append(Constants.MATRIX_SEPARATOR + Constants.END_OF_LINE);
		}
		
		return result.toString();
	}
	
	public float[][] getElements() {
		return elements;
	}

	public void setElements(float[][] elements) {
		this.elements = elements;
	}
	
}
