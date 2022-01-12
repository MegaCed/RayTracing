package com.raytracer.engine.element;

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

	public Tuple getPosition() {
		return position;
	}

	public Color getIntensity() {
		return intensity;
	}
	
}
