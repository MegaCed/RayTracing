package com.raytracer.engine.element;

/*
 * Each ray created by your ray tracer will have a starting point called the origin, and a vector 
 * called the direction which says where it points.
 * 
 * Ray casting is the process of creating a ray, or line, and finding the intersections of that ray 
 * with the objects in a scene.
 */
public class Ray {
	
	// The Ray's starting point
	private Tuple origin;
	
	// The Ray's direction
	private Tuple direction;
	
	/*
	 * Constructor.
	 */
	public Ray(Tuple origin, Tuple direction) {
		this.origin = origin;
		this.direction = direction;
	}
	
	/*
	 * Prints this Ray.
	 */
	@Override
	public String toString() {
		String result = "Ray (origin = " + origin + " - direction = " + direction + ")";
		
		return result;
	}

	public Tuple getOrigin() {
		return origin;
	}

	public Tuple getDirection() {
		return direction;
	}

}
