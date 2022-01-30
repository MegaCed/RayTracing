package com.raytracer.engine.operation;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Computations;
import com.raytracer.engine.element.Intersection;
import com.raytracer.engine.element.Intersections;
import com.raytracer.engine.element.Ray;
import com.raytracer.engine.element.Sphere;
import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.element.World;
import com.raytracer.engine.misc.Constants;

/*
 * World-related operations.
 */
public class WorldOperations {
	
	private static Logger logger = LoggerFactory.getLogger(WorldOperations.class);
	
	/*
	 * Accepts a world and a ray, and returns the intersections.
	 * The function should iterate over all of the objects that have been added to the world, 
	 * intersecting each of them with the ray, and aggregating the intersections into a single 
	 * collection. Note that for the test to pass, it must return the intersections in sorted order.
	 */
	public static Intersections intersectWorld(World aWorld, Ray aRay) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Finding intersections between: " + aWorld + " and: " + aRay);
		
		List<Intersection> intersections = new ArrayList<Intersection>();
		
		// Iterate over the World's intersections
		for (Object anObject: aWorld.getObjects()) {
			// Add all the intersections to the List
			intersections.addAll(SphereOperations.intersects((Sphere)anObject, aRay));
		}
		
		Intersections result = Factory.intersections(intersections);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Intersections found = " + result);
		return result;
	}
	
	/*
	 * return a new data structure encapsulating some precomputed information relating to the 
	 * intersection. 
	 * This will help you later by making it easier to reuse these computations in different 
	 * calculations.
	 */
	public static Computations prepareComputations(Intersection anIntersection, Ray aRay) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Preparing computations between: " + anIntersection + " and: " + aRay);
		
		// Instantiate a data structure for storing some precomputed values
		Computations result = Factory.computations();
		
		// Copy the intersection's properties, for convenience
		result.setT(anIntersection.getT());
		result.setObject(anIntersection.getObject());
		
		// Precompute some useful values
		Tuple position = RayOperations.position(aRay, result.getT());
		result.setPoint(position);
		
		Tuple eyeVector = TupleOperations.neg(aRay.getDirection());
		result.setEyeVector(eyeVector);
		
		Tuple normalVector = SphereOperations.normalAt((Sphere)result.getObject(), result.getPoint());
		result.setNormalVector(normalVector);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Intersections found = " + result);
		return result;
	}
	
}
