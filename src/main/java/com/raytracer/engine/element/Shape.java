package com.raytracer.engine.element;

import java.util.UUID;

import com.raytracer.engine.Factory;
import com.raytracer.engine.operation.ShapeOperations;

/*
 * Parent class for all shapes.
 */
public abstract class Shape {
	
	// Unique ID
	protected String id;
	
	// All Shapes have a transformation matrix. Unless explicitly set, this will be the identity 
	// matrix
	protected Matrix transform;
	
	// All Shapes have a material, which should default to the one described in The Phong Reflection 
	// Model
	protected Material material;
	
	// The associated Operations
	protected ShapeOperations operations;
	
	// TODO: For testing only!
	protected Ray savedRay;
	
	// The Shape's name
	protected String name;
	
	/*
	 * Constructor.
	 */
	public Shape() {
		// Should return a unique value each time it is invoked
		id = UUID.randomUUID().toString();
		
		// The default transformation
		transform = Factory.identityMatrix();
		
		// The a default material
		material = Factory.material();
	}
	
	/*
	 * Prints this Shape.
	 */
	@Override
	public String toString() {
		String result = name + " (id = " + id + ")";
		
		// TODO: print the transform property??
		
		return result;
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

	public ShapeOperations getOperations() {
		return operations;
	}

	public void setOperations(ShapeOperations operations) {
		this.operations = operations;
	}

	public Ray getSavedRay() {
		return savedRay;
	}

	public void setSavedRay(Ray savedRay) {
		this.savedRay = savedRay;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
