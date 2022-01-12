package com.raytracer.engine.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
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
	public static Tuple position(Ray aRay, float distance) {
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
		// Transform Ray's origin
		Tuple transformedOrigin = MatrixOperations.mul(aMatrix, aRay.getOrigin());
		
		// Transform Ray's direction
		Tuple transformedDirection = MatrixOperations.mul(aMatrix, aRay.getDirection());
		
		// Make sure it returns a new ray, rather than modifying the ray in place!
		// You need to keep the original, untransformed ray, so that you can use it to calculate 
		// locations in world space later
		return Factory.ray(transformedOrigin, transformedDirection);
	}
	
}
