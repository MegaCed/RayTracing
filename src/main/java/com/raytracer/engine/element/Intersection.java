package com.raytracer.engine.element;

import java.util.Objects;

/*
 * Aggregate two things: 
 * 1. The t value of the intersection
 * 2. The object that was intersected
 */
public class Intersection {
	
	// The points that lie any distance t along the ray.
	// Why t? Blame the mathematicians! It stands for time, which only makes sense once you think of 
	// the ray’s direction vector as its speed
	private double t;
	
	// The intersected object
	private Shape object;

	/*
	 * Constructor.
	 */
	public Intersection(double t, Shape o) {
		this.t = t;
		this.object = o;
	}
	
	/*
	 * Prints this Intersection.
	 */
	@Override
	public String toString() {
		String result = "Intersection (t = " + t + " - object = " + object + ")";
		
		return result;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(object, t);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Intersection other = (Intersection) obj;
		
		return Objects.equals(object, other.object) 
				&& Double.doubleToLongBits(t) == Double.doubleToLongBits(other.t);
	}

	public double getT() {
		return t;
	}

	public void setT(double t) {
		this.t = t;
	}

	public Shape getObject() {
		return object;
	}

	public void setObject(Shape object) {
		this.object = object;
	}
	
}
