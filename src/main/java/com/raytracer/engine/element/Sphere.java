package com.raytracer.engine.element;

import java.util.Objects;
import java.util.UUID;

import com.raytracer.engine.Factory;

/*
 * Just a simple motherfucking Sphere...
 */
public class Sphere {
	
	// Unique ID
	private String id;
	
	// Transformation
	private Matrix transform;
	
	// The Material
	private Material material;
	
	/*
	 * Constructor.
	 */
	public Sphere() {
		// Should return a unique value each time it is invoked
		id = UUID.randomUUID().toString();
		
		// A sphere has a default transformation
		transform = Factory.identityMatrix();
		
		// A sphere has a default material
		material = Factory.material();
	}
	
	/*
	 * Prints this Sphere.
	 */
	@Override
	public String toString() {
		String result = "Sphere (id = " + id + ")";
		
		// TODO: print the transform property??
		
		return result;
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

	public Matrix getTransform() {
		return transform;
	}

	public void setTransform(Matrix transform) {
		this.transform = transform;
	}

	public String getId() {
		return id;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
	
}
