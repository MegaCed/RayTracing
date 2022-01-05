package com.raytracer.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.element.Canvas;
import com.raytracer.engine.element.Color;
import com.raytracer.engine.element.Intersection;
import com.raytracer.engine.element.Intersections;
import com.raytracer.engine.element.Matrix;
import com.raytracer.engine.element.Ray;
import com.raytracer.engine.element.Sphere;
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
	
	/*
	 * Returns a 4x4 'X rotation' Matrix.
	 * 
	 * Multiplying a tuple by a rotation matrix will rotate that tuple around an axis.
	 * 
	 * The rotation will appear to be clockwise around the corresponding axis when viewed along that 
	 * axis, toward the negative end.
	 * 
	 * The transformation matrix for rotating r radians around the x axis is constructed like this:
	 * |1     0      0 0|
	 * |0 cos r -sin r 0|
	 * |0 sin r  cos r 0|
	 * |0     0      0 1|
	 * 
	 * Angles will be given in radians:
	 * - a full circle (360 degrees) consists of 2PI radians
	 * - a half circle (180 degrees) is PI radians
	 * - a quarter circle (90 degrees) is PI⁄2 radians
	 */
	public static Matrix xRotationMatrix(float radians) {
		// Get the Identity Matrix
		Matrix rotationMatrix = Factory.identityMatrix();
		
		// Set correctly the 4 elements
		rotationMatrix.setElement((float)Math.cos(radians), 1, 1);
		rotationMatrix.setElement(-(float)Math.sin(radians), 1, 2);
		rotationMatrix.setElement((float)Math.sin(radians), 2, 1);
		rotationMatrix.setElement((float)Math.cos(radians), 2, 2);
		
		logger.debug(Constants.SEPARATOR_CREATION + "Returning X Rotation Matrix: " + rotationMatrix);
		return rotationMatrix;
	}
	
	/*
	 * The y axis rotation works just like the x axis rotation, only changing the axis.
	 * 
	 * The transformation matrix for rotating r radians around the y axis is constructed like this:
	 * | cos r 0 sin r 0|
	 * |     0 1     0 0|
	 * |-sin r 0 cos r 0|
	 * |     0 0     0 1|
	 */
	public static Matrix yRotationMatrix(float radians) {
		// Get the Identity Matrix
		Matrix rotationMatrix = Factory.identityMatrix();
		
		// Set correctly the 4 elements
		rotationMatrix.setElement((float)Math.cos(radians), 0, 0);
		rotationMatrix.setElement((float)Math.sin(radians), 0, 2);
		rotationMatrix.setElement(-(float)Math.sin(radians), 2, 0);
		rotationMatrix.setElement((float)Math.cos(radians), 2, 2);
		
		logger.debug(Constants.SEPARATOR_CREATION + "Returning Y Rotation Matrix: " + rotationMatrix);
		return rotationMatrix;
	}
	
	/*
	 * The z axis rotation. It works just like the other rotations.
	 * 
	 * The transformation matrix itself is this:
	 * | cos r -sin r 0 0|
	 * | sin r  cos r 0 0|
	 * |     0      0 1 0|
	 * |     0      0 0 1|
	 */
	public static Matrix zRotationMatrix(float radians) {
		// Get the Identity Matrix
		Matrix rotationMatrix = Factory.identityMatrix();
		
		// Set correctly the 4 elements
		rotationMatrix.setElement((float)Math.cos(radians), 0, 0);
		rotationMatrix.setElement(-(float)Math.sin(radians), 0, 1);
		rotationMatrix.setElement((float)Math.sin(radians), 1, 0);
		rotationMatrix.setElement((float)Math.cos(radians), 1, 1);
		
		logger.debug(Constants.SEPARATOR_CREATION + "Returning Z Rotation Matrix: " + rotationMatrix);
		return rotationMatrix;
	}
	
	/*
	 * A shearing (or skew) transformation has the effect of making straight lines slanted.
	 * 
	 * When applied to a tuple, a shearing transformation changes each component of the tuple in 
	 * proportion to the other two components.
	 * So the x component changes in proportion to y and z, y changes in proportion to x and z, and 
	 * z changes in proportion to x and y.
	 * 
	 * In three dimensions each component may be affected by either of the other two components, so 
	 * there are a total of six parameters that may be used to define the shear transformation:
	 * - x in proportion to y
	 * - x in proportion to z
	 * - y in proportion to x
	 * - y in proportion to z
	 * - z in proportion to x
	 * - z in proportion to y
	 * 
	 * The transformation matrix for a shear transformation is given in the following figure, where 
	 * (for instance) xy means “x moved in proportion to y,” and represents the amount by which to 
	 * multiply y before adding it to x:
	 * |1  xy xz 0|
	 * |yx  1 yz 0|
	 * |zx zy  1 0|
	 * |0   0  0 1|
	 */
	public static Matrix shearingMatrix(float xy, float xz, float yx, float yz, float zx, float zy) {
		// Get the Identity Matrix
		Matrix shearingMatrix = Factory.identityMatrix();
		
		// Set correctly the 4 elements
		shearingMatrix.setElement(xy, 0, 1);
		shearingMatrix.setElement(xz, 0, 2);
		shearingMatrix.setElement(yx, 1, 0);
		shearingMatrix.setElement(yz, 1, 2);
		shearingMatrix.setElement(zx, 2, 0);
		shearingMatrix.setElement(zy, 2, 1);
		
		logger.debug(Constants.SEPARATOR_CREATION + "Returning Shearing Matrix: " + shearingMatrix);
		return shearingMatrix;
	}
	
	/*
	 * Creates a Ray.
	 */
	public static Ray ray(Tuple origin, Tuple direction) {
		Ray aRay = new Ray(origin, direction);
		
		logger.debug(Constants.SEPARATOR_CREATION + "Creating new Ray: " + aRay);
		
		return aRay;
	}
	
	/*
	 * Creates a Sphere.
	 */
	public static Sphere sphere() {
		Sphere aSphere = new Sphere();
		
		logger.debug(Constants.SEPARATOR_CREATION + "Creating new Sphere: " + aSphere);
		
		return aSphere;
	}
	
	/*
	 * Creates an Intersection.
	 */
	public static Intersection intersection(float t, Object o) {
		Intersection anIntersection = new Intersection(t, o);
		
		logger.debug(Constants.SEPARATOR_CREATION + "Creating new Intersection: " + anIntersection);
		
		return anIntersection;
	}
	
	/*
	 * Creates an Intersections object.
	 */
	// TODO: Delete this!
	public static Intersections intersections(Intersection... intersections) {
		Intersections theIntersections = new Intersections(intersections);
		
		logger.debug(Constants.SEPARATOR_CREATION + "Creating new Intersections: " + theIntersections);
		
		return theIntersections;
	}
	
}
