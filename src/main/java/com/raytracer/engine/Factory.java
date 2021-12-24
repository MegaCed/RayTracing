package com.raytracer.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.element.Canvas;
import com.raytracer.engine.element.Color;
import com.raytracer.engine.element.Matrix;
import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.misc.Constants;

/*
 * Creates objects used by the application.
 */
public class Factory {

	private static Logger logger = LoggerFactory.getLogger(Factory.class);
	
	// TODO: make it a Singleton!?
	
	/*
	 * Creates a Point (a Tuple with w=1).
	 */
	public static Tuple point(float x, float y, float z) {
		Tuple aPoint =  new Tuple(x, y, z, 1);
		
		logger.debug(Constants.SEPARATOR_CREATION + "Creating new Tuple: " + aPoint);
		
		return aPoint;
	}
	
	/*
	 * Creates a Vector (a Tuple with w=0).
	 */
	public static Tuple vector(float x, float y, float z) {
		Tuple aVector = new Tuple(x, y, z, 0);
		
		logger.debug(Constants.SEPARATOR_CREATION + "Creating new Tuple: " + aVector);
		
		return aVector;
	}
	
	/*
	 * Creates a Color.
	 */
	public static Color color(float red, float green, float blue) {
		Color aColor = new Color(red, green, blue);
		
		logger.debug(Constants.SEPARATOR_CREATION + "Creating new Color: " + aColor);
		
		return aColor;
	}
	
	/*
	 * Creates a Canvas.
	 */
	public static Canvas canvas(int width, int height) {
		Canvas aCanvas = new Canvas(width, height);
		
		logger.debug(Constants.SEPARATOR_CREATION + "Creating new Canvas: " + aCanvas);
		
		return aCanvas;
	}
	
	/*
	 * Creates a Matrix.
	 */
	public static Matrix matrix(float[][] elements) {
		Matrix aMatrix = new Matrix(elements);
		
		logger.debug(Constants.SEPARATOR_CREATION + "Creating new Matrix: " + aMatrix);
		
		return aMatrix;
	}
	
	/*
	 * Returns the 'Identity' Matrix.
	 * 
	 * You know that you can multiply any number by 1 and get the original number.
	 * The number 1 is called the multiplicative identity for that reason. Well, the identity matrix 
	 * is like the number 1, but for matrices. If you multiply any matrix or tuple by the identity 
	 * matrix, you get back the matrix or tuple you started with.
	 */
	public static Matrix identityMatrix() {
		// The identity matrix is all zeros, except for those elements along the diagonal, which are 
		// each set to 1
		float[][] elements = {
				{1, 0, 0, 0},
				{0, 1, 0, 0},
				{0, 0, 1, 0},
				{0, 0, 0, 1}
		};
		
		Matrix aMatrix = new Matrix(elements);
		
		logger.debug(Constants.SEPARATOR_CREATION + "Returning Identity Matrix: " + aMatrix);
		return aMatrix;
	}
	
	/*
	 * Returns the (4x4) 'translation' Matrix.
	 * 
	 * Start with an identity matrix t, and then add the desired x, y, and z values to 
	 * (respectively) the t03, t13, and t23 elements:
	 * |1 0 0 x|
	 * |0 1 0 y|
	 * |0 0 1 z|
	 * |0 0 0 1|
	 * 
 	 * Translation is a transformation that moves a point.
	 * 
	 * It changes the coordinates of the point by adding to or subtracting from them. For example, 
	 * if the point had an x coordinate of 3, and you moved it 4 units in x, it would wind up with 
	 * an x coordinate of 7.
	 */
	public static Matrix translationMatrix(float x, float y, float z) {
		// Get the Identity Matrix
		Matrix translationMatrix = Factory.identityMatrix();
		
		// Set correctly the 3 elements
		translationMatrix.setElement(x, 0, 3);
		translationMatrix.setElement(y, 1, 3);
		translationMatrix.setElement(z, 2, 3);
		
		logger.debug(Constants.SEPARATOR_CREATION + "Returning Translation Matrix: " + translationMatrix);
		return translationMatrix;
	}
	
	/*
	 * Returns a 4x4 'scaling' Matrix.
	 * 
	 * To construct a scaling matrix, take an identity matrix t and change the values at t00, t11, 
	 * and t22 to be (respectively) the x, y, and z scaling values:
	 * |x 0 0 0|
	 * |0 y 0 0|
	 * |0 0 z 0|
	 * |0 0 0 1|
	 * 
	 * When applied to an object centered at the origin, this transformation scales all points on 
	 * the object, effectively making it larger (if the scale value is greater than 1) or smaller 
	 * (if the scale value is less than 1).
	 */
	public static Matrix scalingMatrix(float x, float y, float z) {
		// Get the Identity Matrix
		Matrix scalingMatrix = Factory.identityMatrix();
		
		// Set correctly the 3 elements
		scalingMatrix.setElement(x, 0, 0);
		scalingMatrix.setElement(y, 1, 1);
		scalingMatrix.setElement(z, 2, 2);
		
		logger.debug(Constants.SEPARATOR_CREATION + "Returning Scaling Matrix: " + scalingMatrix);
		return scalingMatrix;
	}
	
}
