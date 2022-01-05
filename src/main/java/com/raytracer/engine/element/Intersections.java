package com.raytracer.engine.element;

// TODO: Delete this!
/*
 * Aggregates many Intersections.
 */
public class Intersections {
	
	// The array of Intersections
	Intersection[] intersections;

	/*
	 * Constructor.
	 */
	public Intersections(Intersection... intersections) {
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
