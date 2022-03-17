package com.raytracer.engine.operation;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Intersection;
import com.raytracer.engine.element.Plane;
import com.raytracer.engine.element.Ray;
import com.raytracer.engine.element.Shape;
import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.misc.Constants;

/*
 * Performs various operations on Planes.
 */
public class PlaneOperations extends ShapeOperations {
	
	private static Logger logger = LoggerFactory.getLogger(PlaneOperations.class);
	
	/*
	 * See method in super class.
	 */
	@Override
	public List<Intersection> localIntersect(Shape aShape, Ray aRay) {		
		// Get real object's type
		Plane aPlane = (Plane) aShape;
		
		List<Intersection> intersections = new ArrayList<Intersection>();
		
		// To know if a ray is parallel to the plane, you need to note that the plane is in xz—it 
		// has no slope in y at all. Thus, if your ray’s direction vector also has no slope in y 
		// (its y component is 0), it is parallel to the plane. In practice, you’ll want to treat 
		// any tiny number as 0 for this comparison
		if (Math.abs(aRay.getDirection().getY()) < MiscOperations.EPSILON) {
			logger.debug(Constants.SEPARATOR_RESULT + "No interactions!");
			return intersections;
		}
		
		// Computing the intersection of a ray with a plane.
		// Note that this formula only works if the plane is as described above—in xz, with the 
		// normal pointing in the positive y direction.
		double t = (-aRay.getOrigin().getY()) / aRay.getDirection().getY();
		
		Intersection theIntersection = new Intersection(t, aPlane);
		intersections.add(theIntersection);

		return intersections;
	}
	
	/*
	 * See method in super class.
	 */
	@Override
	public Tuple localNormalAt(Shape aShape, Tuple objectPoint) {
		// Because a plane has no curvature, its normal vector is constant everywhere—it doesn’t 
		// change. Every single point on the plane has the same normal: vector(0, 1, 0).
		return Factory.vector(0, 1, 0);
	}

}
