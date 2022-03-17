package com.raytracer.engine.element;

import java.util.Objects;
import java.util.UUID;

import com.raytracer.engine.Factory;
import com.raytracer.engine.operation.SphereOperations;

/*
 * Just a simple motherfucking Sphere...
 */
public class Sphere extends Shape {
	
	private static final String NAME = "Sphere";
	
	/*
	 * Constructor.
	 */
	public Sphere() {
		super();
		
		// Set the shape's operations to be used
		setOperations(new SphereOperations());
		
		// Set the shape's name
		setName(NAME);
	}
	
	/*
	 * Test the equality of 2 Spheres.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Sphere other = (Sphere) obj;
		
		return Objects.equals(id, other.id) && Objects.equals(transform, other.transform);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, transform);
	}
	
}
