package com.raytracer.engine.element;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 * Aggregates many Intersections.
 * 
 * The intersections are intentionally given in random order; it’s up to your Intersections() 
 * function to maintain a sorted list.
 */
public class Intersections {
	
	// The list of Intersections
	List<Intersection> intersections;

	/*
	 * Constructor.
	 */
	public Intersections(List<Intersection> intersections) {
		// First sort the Intersections array
		Collections.sort(intersections, new Comparator<Intersection>() {
			   public int compare(Intersection i1, Intersection i2) {
				   return Double.compare(i1.getT(), i2.getT());
				   }
				});
		
		// Then keep it here
		this.intersections = intersections;
	}
	
	/*
	 * Prints the Intersections.
	 */
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		
		int numberOfIntersections = intersections.size();
		result.append("Intersection (size = " + numberOfIntersections);
		
		if (numberOfIntersections > 0) {
			result.append(" - intersections = " + intersections);
		}
		
		result.append(")");
		
		return result.toString();
	}
	
	public List<Intersection> getIntersections() {
		return intersections;
	}

	public void setIntersections(List<Intersection> intersections) {
		this.intersections = intersections;
	}
	
}
