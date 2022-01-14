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
		double a = TupleOperations.dot(aRay.getDirection(), aRay.getDirection());
		double b = TupleOperations.dot(aRay.getDirection(), sphereToRay) * 2;
		double c = TupleOperations.dot(sphereToRay, sphereToRay) - 1;
		
		// discriminant = b² - 4 * a * c
		double discriminant = Math.pow(b, 2) - 4 * a * c;
		
		// That discriminant value is the key. If it’s negative, then the ray misses and no 
		// intersections occur between the sphere and the ray
		if (discriminant < 0) {
			logger.debug(Constants.SEPARATOR_RESULT + "No interactions!");
			return new Intersection[] {};
		}
		
		// Otherwise, you’ll see either one (for rays that hit the sphere at a perfect tangent) or 
		// two intersections
		double t1 = (-b - (Math.sqrt(discriminant))) / (2 * a);
		double t2 = (-b + (Math.sqrt(discriminant))) / (2 * a);
		
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
		// MAX_VALUE is the mathematical maximum value for doubles
		double lowestT = Double.MAX_VALUE;
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
	
	/*
	 * Return the normal on the given sphere, at the given point.
	 * You may assume that the point will always be on the surface of the sphere.
	 * 
	 * A surface normal (or just normal) is a vector that points perpendicular to a surface at a 
	 * given point.
	 * 
	 * Let’s say you want to find the normal at a point. Draw an arrow from the origin of the circle 
	 * to that point. It turns out that this arrow—this vector!—is perpendicular to the surface of 
	 * the circle at the point where it intersects. It’s the normal! 
	 * Algorithmically speaking, you find the normal by taking the point in question and subtracting 
	 * the origin of the sphere.
	 */
	public static Tuple normalAt(Sphere aSphere, Tuple worldPoint) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Computing normal for: " + aSphere + " and " + worldPoint);
		
		// You have a point in world space, and you want to know the normal on the corresponding 
		// surface in object space. What to do? Well, first you have to convert the point from world 
		// space to object space by multiplying the point by the inverse of the transformation 
		// matrix
		Tuple objectPoint = MatrixOperations.mul(MatrixOperations.inverse(aSphere.getTransform()), worldPoint);
		
		// With that point now in object space, you can compute the normal as before, because in 
		// object space, the sphere’s origin is at the world’s origin
		Tuple sphereOrigin = Factory.point(0, 0, 0);
		Tuple objectNormal = TupleOperations.normalize(TupleOperations.sub(objectPoint, sphereOrigin));
		
		// However! The normal vector you get will also be in object space... and to draw anything 
		// useful with it you’re going to need to convert it back to world space.
		// So how do you go about keeping the normals perpendicular to their surface? The answer is 
		// to multiply the normal by the inverse transpose matrix instead. So you take your 
		// transformation matrix, invert it, and then transpose the result. This is what you need to 
		// multiply the normal by.
		Tuple worldNormal = MatrixOperations.mul(MatrixOperations.transpose(MatrixOperations.inverse(aSphere.getTransform())), objectNormal);
				
		// Multiplying by its transpose will wind up mucking with the w coordinate in your vector, 
		// which will wreak all kinds of havoc in later computations. But if you don’t mind a bit of 
		// a hack, you can avoid all that by just setting world_normal.w to 0 after multiplying by 
		// the 4x4 inverse transpose matrix
		worldNormal.setW(0);
		
		// The inverse transpose matrix may change the length of your vector, so if you feed it a 
		// vector of length 1 (a normalized vector), you may not get a normalized vector out! It’s 
		// best to be safe, and always normalize the result
		Tuple normal = TupleOperations.normalize(worldNormal);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Normal = " + normal);
		return normal;
	}

}
