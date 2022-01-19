package com.raytracer.engine.operation;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Intersection;
import com.raytracer.engine.element.Intersections;
import com.raytracer.engine.element.Ray;
import com.raytracer.engine.element.Sphere;
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
		logger.info(Constants.SEPARATOR_OPERATION + "Finding intersections between: " + aWorld + " and: " + aRay);
		
		List<Intersection> intersections = new ArrayList<Intersection>();
		
		// TODO: Clean this fucking mess...
		
		// Iterate over the World's intersections
		for (Object anObject: aWorld.getObjects()) {
			Intersection[] intersectionsArray = SphereOperations.intersects((Sphere)anObject, aRay);
			
			// Add all the intersections in the List
			for (int i = 0; i < intersectionsArray.length; i++) {
				intersections.add(intersectionsArray[i]);	
			}
		}
		
		Intersection[] temp = new Intersection[intersections.size()];
		temp = intersections.toArray(temp);
		Intersections result = Factory.intersections(temp);
		
		logger.info(Constants.SEPARATOR_RESULT + "Intersections found = " + result);
		return result;
	}
	
}
