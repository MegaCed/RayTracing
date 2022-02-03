package com.raytracer.engine.operation;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Color;
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
		
		// How can you know — mathematically — if the normal points away from the eye vector? 
		// Take the dot product of the two vectors, and if the result is negative, they’re pointing 
		// in (roughly) opposite directions
		if (TupleOperations.dot(normalVector, eyeVector) < 0) {
			result.setInside(true);
			result.setNormalVector(TupleOperations.neg(normalVector));
		} else {
			result.setInside(false);
		}
		
		logger.debug(Constants.SEPARATOR_RESULT + "Computations created = " + result);
		return result;
	}
	
	/*
	 * The function ought to return the color at the intersection encapsulated by Computations, in 
	 * the given world.
	 */
	public static Color shadeHit(World aWorld, Computations computations) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Shading the color between: " + aWorld + " and: " + computations);
		
		Color result = ColorOperations.lithting(((Sphere)(computations.getObject())).getMaterial(), 
				aWorld.getLight(), 
				computations.getPoint(), 
				computations.getEyeVector(), 
				computations.getNormalVector());
		
		logger.debug(Constants.SEPARATOR_RESULT + "Shaded color = " + result);
		return result;
	}
	
	/*
	 * For convenience’s sake, tie up the intersect(), prepareComputations(), and shadeHit() 
	 * functions.
	 * It will intersect the world with the given ray and then return the color at the resulting 
	 * intersection.
	 */
	public static Color colorAt(World aWorld, Ray aRay) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Intersect the World: " + aWorld + " with the Ray: " + aRay);
		
		// Call intersectWorld to find the intersections of the given ray with the given world
		Intersections intersections = intersectWorld(aWorld, aRay);
		
		// Find the hit from the resulting intersections
		Intersection hit = null;
		if (intersections.getIntersections().size() > 0) {
			hit = intersections.getIntersections().get(0);
		} else {
			// Return the color black if there is no such intersection.
			return Constants.COLOR_BLACK;
		}
		
		// Otherwise, precompute the necessary values with prepareComputations
		Computations computations = prepareComputations(hit, aRay);
		
		// Finally, call shadeHit to find the color at the hit
		Color result = shadeHit(aWorld, computations);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Color at the intersection = " + result);
		return result;
	}
	
}
