package com.raytracer.engine.element;

import java.util.Objects;

import com.raytracer.engine.operation.PlaneOperations;

/*
 * A plane is a perfectly flat surface that extends infinitely in two dimensions. 
 * For simplicity, your ray tracer will implement a plane in xz—that is, extending infinitely far in 
 * both x and z dimensions, passing through the origin. 
 * Using transformation matrices, though, you’ll be able to rotate and translate your planes into 
 * any orientation you like.
 */
public class Plane extends Shape {
	
	private static final String NAME = "Plane";
	
	/*
	 * Constructor.
	 */
	public Plane() {
		super();
		
		// Set the shape's operations to be used
		setOperations(new PlaneOperations());
		
		// Set the shape's name
		setName(NAME);
	}
	
	/*
	 * Test the equality of 2 Planes.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Plane other = (Plane) obj;
		
		return Objects.equals(id, other.id) && Objects.equals(transform, other.transform);
	}

}
