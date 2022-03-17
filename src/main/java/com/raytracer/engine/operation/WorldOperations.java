package com.raytracer.engine.operation;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Camera;
import com.raytracer.engine.element.Canvas;
import com.raytracer.engine.element.Color;
import com.raytracer.engine.element.Computations;
import com.raytracer.engine.element.Intersection;
import com.raytracer.engine.element.Intersections;
import com.raytracer.engine.element.Matrix;
import com.raytracer.engine.element.Ray;
import com.raytracer.engine.element.Shape;
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
		for (Shape aShape: aWorld.getObjects()) {
			// Add all the intersections to the List
			intersections.addAll(aShape.getOperations().intersects(aShape, aRay));
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
		
		Shape aShape = (Shape)result.getObject();
		Tuple normalVector = aShape.getOperations().normalAt(aShape, result.getPoint());
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
		
		// Computers cannot represent floating point numbers very precisely. In general they do 
		// okay, but because of rounding errors, it will be impossible to say exactly where a ray 
		// intersectsa surface. The answer you get will be close—generally within a tiny margin
		// of error—but that wiggle is sometimes just enough to cause the calculated point of 
		// intersection to lie beneath the actual surface of the sphere.
		// EPSILON is the tiny number discussed in Comparing Floating Point Numbers, and is used 
		// here to bump the point just a bit in the direction of the normal
		Tuple overPoint = TupleOperations.add(result.getPoint(), TupleOperations.mul(result.getNormalVector(), MiscOperations.EPSILON));
		result.setOverPoint(overPoint);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Computations created = " + result);
		return result;
	}
	
	/*
	 * The function ought to return the color at the intersection encapsulated by Computations, in 
	 * the given world.
	 */
	public static Color shadeHit(World aWorld, Computations computations) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Shading the color between: " + aWorld + " and: " + computations);
		
		// You need to check whether the point is in shadow or not
		boolean inShadow = isShadowed(aWorld, computations.getOverPoint());
		
		Color result = ColorOperations.lithting(((Shape)(computations.getObject())).getMaterial(), 
				aWorld.getLight(), 
				computations.getPoint(), 
				computations.getEyeVector(), 
				computations.getNormalVector(),
				inShadow);
		
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
		Intersection hit = ShapeOperations.hit(intersections);
		if (hit == null) {
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

	/*
	 * Pretends the eye moves instead of the world.
	 * You specify where you want the eye to be in the scene (the from parameter), the point in the 
	 * scene at which you want to look (the to parameter), and a vector indicating which direction 
	 * is up.
	 * The function then returns to you the corresponding transformation matrix.
	 */
	public static Matrix viewTransform(Tuple from, Tuple to, Tuple up) {
		logger.info(Constants.SEPARATOR_OPERATION + "Transforming view from: " + from + " - to: " + to + " - up: " + up);
		
		// Compute the forward vector by subtracting from from to. Normalize the result
		Tuple vector = TupleOperations.sub(to, from);
		Tuple forward = TupleOperations.normalize(vector);
		
		Tuple upNormalized = TupleOperations.normalize(up);
		
		// Compute the left vector by taking the cross product of forward and the normalized up 
		// vector
		Tuple left = TupleOperations.cross(forward, upNormalized);
		
		// Compute the trueUp vector by taking the cross product of left and forward.
		// This allows your original up vector to be only approximately up, which makes framing 
		// scenes a lot easier, since you don’t need to personally break out a calculator to figure 
		// out the precise upward direction
		Tuple trueUp = TupleOperations.cross(left, forward);
		
		// With these left, true_up, and forward vectors, you can now construct a matrix that 
		// represents the orientation transformation
		Matrix orientation = Factory.orientationMatrix(left, trueUp, forward);
		
		// All that’s left is to append a translation to that transformation to move the scene into 
		// place before orienting it. Multiply orientation by 
		// translation(-from.x, -from.y, -from.z), and you’re golden!
		Matrix result = MatrixOperations.mul(orientation, Factory.translationMatrix(-from.getX(), -from.getY(), -from.getZ()));
		
		logger.info(Constants.SEPARATOR_RESULT + "Transformation Matrix = " + result);
		return result;
	}
	
	/*
	 * Uses the camera to render an image of the given world.
	 * 
	 * Creates a canvas and cast a ray through each of its pixels, coloring the pixels with the 
	 * colors of the corresponding intersections.
	 * That’s exactly what this function will do, except instead of computing the location of each 
	 * pixel, you’ll let your new rayForPixel() function do the work.
	 */
	public static Canvas render(Camera aCamera, World theWorld) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Rendering image for: " + aCamera + " and: " + theWorld);
		
		Canvas image = Factory.canvas(aCamera.gethSize(), aCamera.getvSize());
		
		for (int y = 0; y < aCamera.getvSize() -1; y++) {
			String percentage = String.format("%" + 2 + ".0f", (double)y/(double)aCamera.getvSize()*100);
			logger.info("- Processing: " + percentage + " %");
			
			for (int x = 0; x < aCamera.gethSize() -1; x++) {
				Ray theRay = RayOperations.rayForPixel(aCamera, x, y);
				
				Color theColor = colorAt(theWorld, theRay);
				
				image.writePixel(x, y, theColor);
			}
		}
		
		logger.debug(Constants.SEPARATOR_RESULT + "Rendered image = " + image);
		return image;
	}
	
	/*
	 * A ray tracer computes shadows by casting a ray, called a shadow ray, from each point of 
	 * intersection toward the light source. If something intersects that shadow ray between the 
	 * point and the light source, then the point is considered to be in shadow.
	 */
	public static boolean isShadowed(World theWorld, Tuple aPoint) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Checks if the point: " + aPoint + " is shadowed for the world: " + theWorld);
		
		boolean result;
		
		// Measure the distance from point to the light source by subtracting point from the light 
		// position, and taking the magnitude of the resulting vector.
		// Call this distance
		Tuple vector = TupleOperations.sub(theWorld.getLight().getPosition(), aPoint);
		double distance = TupleOperations.magnitude(vector);
		
		// Create a ray from point toward the light source by normalizing the vector from step 1
		Tuple direction = TupleOperations.normalize(vector);
		Ray ray = Factory.ray(aPoint, direction);
		
		// Intersect the world with that ray
		Intersections intersections = WorldOperations.intersectWorld(theWorld, ray);
		
		// Check to see if there was a hit, and if so, whether t is less than distance. If so, the 
		// hit lies between the point and the light source, and the point is in shadow
		Intersection hit = SphereOperations.hit(intersections);
		
		// The hit’s t will never be negative, so you don’t need to worry about checking for 
		// intersections that occur behind the point
		if (hit != null && hit.getT() < distance) {
			result = true;
		} else {
			result = false;
		}
		
		logger.debug(Constants.SEPARATOR_RESULT + "Is point shadowed = " + result);
		return result;
	}
	
}
