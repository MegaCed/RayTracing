package com.raytracer.engine.element;

import java.util.Objects;

/*
 * A light source.
 */
public class PointLight {
	
	// Exists at a single point in space
	private Tuple position;
	
	// It is also defined by its intensity, or how bright it is.
	// This intensity also describes the color of the light source
	private Color intensity;
	
	/*
	 * Constructor.
	 */
	public PointLight(Tuple position, Color intensity) {
		this.position = position;
		this.intensity = intensity;
	}
	
	/*
	 * Prints this PointLight.
	 */
	@Override
	public String toString() {
		String result = "PointLight (position = " + position + " - intensity = " + intensity + ")";
		
		return result;
	}

	@Override
	public int hashCode() {
		return Objects.hash(intensity, position);
	}

	/*
	 * Test the equality of 2 PointLight.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		PointLight other = (PointLight) obj;
		
		return Objects.equals(intensity, other.intensity) && Objects.equals(position, other.position);
	}

	public Tuple getPosition() {
		return position;
	}

	public Color getIntensity() {
		return intensity;
	}
	
}
