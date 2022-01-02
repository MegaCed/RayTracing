package com.raytracer.engine.element;

import java.util.Objects;
import java.util.UUID;

/*
 * Just a simple motherfucking Sphere...
 */
public class Sphere {
	
	// Unique ID
	private String id;
	
	/*
	 * Constructor.
	 */
	public Sphere() {
		// Should return a unique value each time it is invoked
		id = UUID.randomUUID().toString();
	}
	
	/*
	 * Prints this Sphere.
	 */
	@Override
	public String toString() {
		String result = "Sphere (id = " + id + ")";
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Sphere other = (Sphere) obj;
		
		return Objects.equals(id, other.id);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}	

}
