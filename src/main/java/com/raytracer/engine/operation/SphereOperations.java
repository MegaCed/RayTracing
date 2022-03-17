package com.raytracer.engine.operation;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Intersection;
import com.raytracer.engine.element.Ray;
import com.raytracer.engine.element.Shape;
import com.raytracer.engine.element.Sphere;
import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.misc.Constants;

/*
 * Performs various operations on Spheres.
 */
public class SphereOperations extends ShapeOperations {
	
	private static Logger logger = LoggerFactory.getLogger(SphereOperations.class);
	
	/*
	 * See method in super class.
	 */
	@Override
	public List<Intersection> localIntersect(Shape aShape, Ray aRay) {		
		// Get real object's type
		Sphere aSphere = (Sphere) aShape;
		
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
			return new ArrayList<Intersection>();
		}
		
		// Otherwise, you’ll see either one (for rays that hit the sphere at a perfect tangent) or 
		// two intersections
		double t1 = (-b - (Math.sqrt(discriminant))) / (2 * a);
		double t2 = (-b + (Math.sqrt(discriminant))) / (2 * a);
		
		Intersection intersection1 = new Intersection(t1, aSphere);
		Intersection intersection2 = new Intersection(t2, aSphere);
		
		// Also, make sure the intersections are returned in increasing order, to make it easier to 
		// determine which intersections are significant, later
		List<Intersection> intersections = new ArrayList<Intersection>();
		
		if (t1 <= t2) {
			intersections.add(intersection1);
			intersections.add(intersection2);
		} else {
			intersections.add(intersection1);
			intersections.add(intersection2);
		}
		
		return intersections;
	}
	
	/*
	 * See method in super class.
	 */
	@Override
	public Tuple localNormalAt(Shape aShape, Tuple objectPoint) {
		// With that point now in object space, you can compute the normal as before, because in 
		// object space, the sphere’s origin is at the world’s origin
		Tuple sphereOrigin = Factory.point(0, 0, 0);
		Tuple objectNormal = TupleOperations.normalize(TupleOperations.sub(objectPoint, sphereOrigin));
		
		return objectNormal;
	}
	
}
