package com.raytracer.engine.operation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.element.Intersection;
import com.raytracer.engine.element.Intersections;
import com.raytracer.engine.element.Ray;
import com.raytracer.engine.element.Shape;
import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.misc.Constants;

/*
 * Performs various operations on Shapes.
 */
public abstract class ShapeOperations {
	
	private static Logger logger = LoggerFactory.getLogger(ShapeOperations.class);

	/*
	 * This function returns the hit from a collection of intersection records.
	 * 
	 * You can ignore all intersections with negative t values when determining the hit. 
	 * In fact, the hit will always be the intersection with the lowest nonnegative t value.
	 */
	public static Intersection hit(Intersections intersections) {
		Intersection result = null;
		
		// Return the first non-negative t value
		for (Intersection anIntersection : intersections.getIntersections()) {
			if (anIntersection.getT() >= 0) {
				// This is the current lowest hit
				result = anIntersection;
				break;
			}
		}
		
		return result;
	}
	
	/*
	 * Returns the collection of t values where the ray intersects the shape.
	 */
	public List<Intersection> intersects(Shape aShape, Ray originalRay) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Computing intersections between: " + aShape + " and " + originalRay);
		
		// When intersecting the shape with a ray, all shapes need to first convert the ray into 
		// object space, transforming it by the inverse of the shape’s transformation matrix
		Ray localRay = RayOperations.transform(originalRay, MatrixOperations.inverse(aShape.getTransform()));
		
		// TODO: For testing only!
		aShape.setSavedRay(localRay);
		
		// Call the concrete implementation for the current Shape
		List<Intersection> intersections = localIntersect(aShape, localRay);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Intersections = " + intersections);
		return intersections;
	}
	
	/*
	 * Needs to be implemented by each concrete subclasses of this class.
	 */
	public abstract List<Intersection> localIntersect(Shape aShape, Ray originalRay);
	
	/*
	 * Return the normal on the given shape, at the given point.
	 * You may assume that the point will always be on the surface of the shape.
	 * 
	 * A surface normal (or just normal) is a vector that points perpendicular to a surface at a 
	 * given point.
	 * 
	 * Let’s say you want to find the normal at a point. Draw an arrow from the origin of the circle 
	 * to that point. It turns out that this arrow—this vector!—is perpendicular to the surface of 
	 * the circle at the point where it intersects. It’s the normal! 
	 * Algorithmically speaking, you find the normal by taking the point in question and subtracting 
	 * the origin of the shape.
	 */
	public Tuple normalAt(Shape aShape, Tuple worldPoint) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Computing normal for: " + aShape + " and " + worldPoint);
		
		// You have a point in world space, and you want to know the normal on the corresponding 
		// surface in object space. What to do? Well, first you have to convert the point from world 
		// space to object space by multiplying the point by the inverse of the transformation 
		// matrix
		Tuple objectPoint = MatrixOperations.mul(MatrixOperations.inverse(aShape.getTransform()), worldPoint);
		
		// Call the concrete implementation for the current Shape
		Tuple objectNormal = localNormalAt(aShape, objectPoint);
		
		// However! The normal vector you get will also be in object space... and to draw anything 
		// useful with it you’re going to need to convert it back to world space.
		// So how do you go about keeping the normals perpendicular to their surface? The answer is 
		// to multiply the normal by the inverse transpose matrix instead. So you take your 
		// transformation matrix, invert it, and then transpose the result. This is what you need to 
		// multiply the normal by.
		Tuple worldNormal = MatrixOperations.mul(MatrixOperations.transpose(MatrixOperations.inverse(aShape.getTransform())), objectNormal);
				
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
	
	/*
	 * The goal here is to make it so that individual concrete shapes don’t have to worry about 
	 * transforming points or normals—all they have to do is compute the normal itself.
	 * 
	 * Accepts a point in local (object) space, and returns the normal in the same space.
	 */
	public abstract Tuple localNormalAt(Shape aShape, Tuple localPoint);
	
}
