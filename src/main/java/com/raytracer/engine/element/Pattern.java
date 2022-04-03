package com.raytracer.engine.element;

import java.util.Objects;

import com.raytracer.engine.Factory;
import com.raytracer.engine.operation.PatternOperations;

/*
 * A data structure that encapsulates the colors used by the pattern.
 */
public abstract class Pattern {

	private Color a;
	private Color b;
	
	// The Shape's name
	protected String name;
	
	// Every pattern will have a transformation matrix, and every pattern will need to use it to 
	// help transform a given point from world space to pattern space before producing a color
	private Matrix transform;
	
	// The associated Operations
	protected PatternOperations operations;
	
	/*
	 * Constructor.
	 */
	public Pattern(Color a, Color b) {
		this.a = a;
		this.b = b;
		
		// This pattern has a transformation matrix and that the transformation is (by default) the 
		// identity matrix
		this.transform = Factory.identityMatrix();
	}

	public Color getA() {
		return a;
	}

	public Color getB() {
		return b;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Matrix getTransform() {
		return transform;
	}

	public void setTransform(Matrix transform) {
		this.transform = transform;
	}

	public PatternOperations getOperations() {
		return operations;
	}

	public void setOperations(PatternOperations operations) {
		this.operations = operations;
	}

	@Override
	public String toString() {
		String result = "Pattern (A = " + a + ", B = " + b + ", Transform = " + transform +")";
		
		return result;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(a, b, transform);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pattern other = (Pattern) obj;
		return Objects.equals(a, other.a) && Objects.equals(b, other.b) && Objects.equals(transform, other.transform);
	}
	
}
