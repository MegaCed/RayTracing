package com.raytracer.engine.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Intersection;
import com.raytracer.engine.element.Intersections;
import com.raytracer.engine.element.Ray;
import com.raytracer.engine.element.Sphere;
import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.misc.Constants;

import java.util.Arrays;

/*
 * Performs various operations on Spheres.
 */
public class SphereOperations {
	
	private static Logger logger = LoggerFactory.getLogger(SphereOperations.class);
	
	/*
	 * Returns the collection of t values where the ray intersects the sphere.
	 */
	public static Intersection[] intersects(Sphere aSphere, Ray originalRay) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Computing intersections between: " + aSphere + " and " + originalRay);
		
		// You’ll need to make sure the ray passed to intersect is transformed by the inverse of the 
		// sphere’s transformation matrix.
		Ray aRay = RayOperations.transform(originalRay, MatrixOperations.inverse(aSphere.getTransform()));
		
		// The vector from the sphere's center, to the ray origin
		// Remember: the sphere is centered 
		// at the world origin
		Tuple worldOrigin = Factory.point(0, 0, 0);
		
		// Begin the algorithm by computing the discriminant — a number that tells you whether the 
		// ray intersects the sphere at all
		Tuple sphereToRay = TupleOperations.sub(aRay.getOrigin(), worldOrigin);
		
		// The math behind intersecting a ray and a sphere is really quite elegant, but for the sake 
		// of brevity we’ll skip the derivations and jump straight to the implementation...
		float a = TupleOperations.dot(aRay.getDirection(), aRay.getDirection());
		float b = TupleOperations.dot(aRay.getDirection(), sphereToRay) * 2;
		float c = TupleOperations.dot(sphereToRay, sphereToRay) - 1;
		
		// discriminant = b² - 4 * a * c
		float discriminant = (float)Math.pow(b, 2) - 4 * a * c;
		
		// That discriminant value is the key. If it’s negative, then the ray misses and no 
		// intersections occur between the sphere and the ray
		if (discriminant < 0) {
			logger.debug(Constants.SEPARATOR_RESULT + "No interactions!");
			return new Intersection[] {};
		}
		
		// Otherwise, you’ll see either one (for rays that hit the sphere at a perfect tangent) or 
		// two intersections
		float t1 = (float)(-b - (Math.sqrt(discriminant))) / (2 * a);
		float t2 = (float)(-b + (Math.sqrt(discriminant))) / (2 * a);
		
		Intersection intersection1 = new Intersection(t1, aSphere);
		Intersection intersection2 = new Intersection(t2, aSphere);
		
		// Also, make sure the intersections are returned in increasing order, to make it easier to 
		// determine which intersections are significant, later
		Intersection[] intersections;
		
		if (t1 <= t2) {
			intersections = new Intersection[] {intersection1, intersection2};
		} else {
			intersections = new Intersection[] {intersection2, intersection1};
		}
		
		logger.debug(Constants.SEPARATOR_RESULT + "Intersections = " + Arrays.toString(intersections));
		return intersections;
	}
	
	/*
	 * This function returns the hit from a collection of intersection records.
	 * 
	 * You can ignore all intersections with negative t values when determining the hit. 
	 * In fact, the hit will always be the intersection with the lowest nonnegative t value.
	 */
	@Deprecated
	public static Intersection hit(Intersection... intersections) {
		// MAX_VALUE is the mathematical maximum value for floats
		float lowestT = Float.MAX_VALUE;
		Intersection result = null;
		
		// Find the lowest non-negative t value
		for (int i = 0; i < intersections.length; i++) {
			Intersection anIntersection = intersections[i];
			
			if (0 <= anIntersection.getT() && lowestT > anIntersection.getT()) {
				// This is the current lowest hit
				lowestT = anIntersection.getT();
				result = anIntersection;
			}
		}
		
		return result;
	}
	
	/*
	 * Enhanced version of the previous method, taking a sorted Intersections object.
	 */
	public static Intersection hit(Intersections intersections) {
		Intersection result = null;
		
		// Return the first non-negative t value
		for (int i = 0; i < intersections.getIntersections().length; i++) {
			Intersection anIntersection = intersections.getIntersections()[i];
			
			if (anIntersection.getT() >= 0) {
				// This is the current lowest hit
				result = anIntersection;
				break;
			}
		}
		
		return result;
	}

}
