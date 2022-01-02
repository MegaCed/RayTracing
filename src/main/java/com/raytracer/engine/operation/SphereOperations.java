package com.raytracer.engine.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
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
	public static float[] intersects(Sphere aSphere, Ray aRay) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Computing intersections between: " + aSphere + " and " + aRay);
		
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
			return new float[] {};
		}
		
		// Otherwise, you’ll see either one (for rays that hit the sphere at a perfect tangent) or 
		// two intersections
		float t1 = (float)(-b - (Math.sqrt(discriminant))) / (2 * a);
		float t2 = (float)(-b + (Math.sqrt(discriminant))) / (2 * a);
		
		// Also, make sure the intersections are returned in increasing order, to make it easier to 
		// determine which intersections are significant, later
		float[] interactions;
		
		if (t1 <= t2) {
			interactions = new float[] {t1, t2};
		} else {
			interactions = new float[] {t2, t1};
		}
		
		logger.debug(Constants.SEPARATOR_RESULT + "Interactions = " + Arrays.toString(interactions));
		return interactions;
	}

}
