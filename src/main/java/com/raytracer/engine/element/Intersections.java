package com.raytracer.engine.element;

import java.util.Arrays;
import java.util.Comparator;

/*
 * Aggregates many Intersections.
 * 
 * The intersections are intentionally given in random order; it’s up to your Intersections() 
 * function to maintain a sorted list.
 */
public class Intersections {
	
	// The array of Intersections
	Intersection[] intersections;

	/*
	 * Constructor.
	 */
	public Intersections(Intersection... intersections) {
		// First sort the Intersections array
		Arrays.sort(intersections, new Comparator<Intersection>() {
			   public int compare(Intersection i1, Intersection i2) {
				      return (int)(i1.getT() - i2.getT());
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
		
		int numberOfIntersections = intersections.length;
		
		result.append("Intersection (size = " + numberOfIntersections);
		
		if (numberOfIntersections > 0) {
			result.append(" - intersections = ");
			
			for (int i = 0; i < intersections.length; i++) {
				result.append(intersections[i]);
				
				if (i != intersections.length - 1) {
					// Don't print separator for the last item of the collection
					result.append(" - ");
				}
			}
		}
		
		result.append(")");
		
		return result.toString();
	}
	
	public Intersection[] getIntersections() {
		return intersections;
	}

	public void setIntersections(Intersection[] intersections) {
		this.intersections = intersections;
	}
	
}
