package com.raytracer.engine.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Camera;
import com.raytracer.engine.element.Matrix;
import com.raytracer.engine.element.Ray;
import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.misc.Constants;

/*
 * Performs various operations on Rays.
 */
public class RayOperations {
	
	private static Logger logger = LoggerFactory.getLogger(RayOperations.class);
	
	/*
	 * This function should compute the point at the given distance t along the ray.
	 * 
	 * You’ll make good use of this when you start turning intersections into actual surface 
	 * information. It’s part of the process of computing realistic shading for your scenes.
	 */
	public static Tuple position(Ray aRay, double distance) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Computing a distance: " + aRay + " by " + distance);
		
		// To find the position, you multiply the ray’s direction by t to find the total distance 
		// traveled
		Tuple updatedDistance = TupleOperations.mul(aRay.getDirection(), distance);
		
		// And then add that to the ray’s origin
		Tuple totalDistance = TupleOperations.add(aRay.getOrigin(), updatedDistance);
		
		logger.debug(Constants.SEPARATOR_RESULT + "New distance = " + totalDistance);
		return totalDistance;
	}
	
	/*
	 * Applies the given transformation matrix to the given ray, and returns a new ray with 
	 * transformed origin and direction. 
	 * 
	 * Multiplying a point in object space by a transformation matrix converts that point to world 
	 * space—scaling it, translating, rotating it, or whatever. 
	 * Multiplying a point in world space by the inverse of the transformation matrix converts that 
	 * point back to object space.
	 */
	public static Ray transform(Ray aRay, Matrix aMatrix) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Transforming Ray: " + aRay + " with the Matrix " + aMatrix);
		
		// Transform Ray's origin
		Tuple transformedOrigin = MatrixOperations.mul(aMatrix, aRay.getOrigin());
		
		// Transform Ray's direction
		Tuple transformedDirection = MatrixOperations.mul(aMatrix, aRay.getDirection());
		
		// Make sure it returns a new ray, rather than modifying the ray in place!
		// You need to keep the original, untransformed ray, so that you can use it to calculate 
		// locations in world space later
		Ray result = Factory.ray(transformedOrigin, transformedDirection);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Transformed Ray = " + result);
		return result;
	}
	
	/*
	 * Returns a new ray that starts at the camera and passes through the indicated (x, y) pixel on 
	 * the canvas.
	 * 
	 * The function must compute the world coordinates at the center of the given pixel, and then 
	 * construct a ray that passes through that point. 
	 * Assuming two inputs, px (the x position of the pixel) and py (the y position of the pixel)
	 */
	public static Ray rayForPixel(Camera aCamera, double px, double py) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Finding Ray for the Camera: " + aCamera + " - px: " + px + " - py:" + py);
		
		// The offset from the edge of the canvas to the pixel's center
		double xOffset = (px + 0.5) * aCamera.getPixelSize();
		double yOffset = (py + 0.5) * aCamera.getPixelSize();
		
		// The untransformed coordinates of the pixel in world space.
		// (remember that the camera looks toward -z, so +x is to the *left*)
		double worldX = aCamera.getHalfWidth() - xOffset;
		double worldY = aCamera.getHalfHeight() - yOffset;
		
		// Using the camera matrix, transform the canvas point and the origin, and then compute the 
		// ray's direction vector (remember that the canvas is at z=-1)
		Tuple pixel = MatrixOperations.mul(MatrixOperations.inverse(aCamera.getTransform()), Factory.point(worldX, worldY, -1));
		Tuple origin = MatrixOperations.mul(MatrixOperations.inverse(aCamera.getTransform()), Factory.point(0, 0, 0));
		Tuple direction = TupleOperations.normalize(TupleOperations.sub(pixel, origin));
		
		Ray result = Factory.ray(origin, direction);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Ray found = " + result);
		return result;
	}
	
}
