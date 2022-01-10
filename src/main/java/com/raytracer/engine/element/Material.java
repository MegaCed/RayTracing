package com.raytracer.engine.element;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;

/*
 * Encapsulates not just the surface color, but also the four new attributes from the Phong 
 * reflection model: ambient, diffuse, specular, and shininess.
 * Each should accept a nonnegative floating point number.
 */
public class Material {
	
	private static Logger logger = LoggerFactory.getLogger(Material.class);
	
	// The surface color
	private Color color;
	
	// Ambient reflection is background lighting, or light reflected from other objects in the 
	// environment. The Phong model treats this as a constant, coloring all points on the surface 
	// equally
	private float ambient;
	
	// Diffuse reflection is light reflected from a matte surface. It depends only on the angle 
	// between the light source and the surface normal
	private float diffuse;
	
	// Specular reflection is the reflection of the light source itself and results in what is 
	// called a specular highlight—the bright spot on a curved surface.
	// It depends only on the angle between the reflection vector and the eye vector and is 
	// controlled by a parameter that we’ll call shininess.
	private float specular;
	
	// The higher the shininess, the smaller and tighter the specular highlight
	private float shininess;
	
	/*
	 * Constructor.
	 */
	public Material() {
		// Default Material
		setColor(Factory.color(1, 1, 1));
		setAmbient(0.1f);
		setDiffuse(0.9f);
		setSpecular(0.9f);
		setShininess(200f);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public float getAmbient() {
		return ambient;
	}

	public void setAmbient(float ambient) {
		// The typical values are between 0 and 1
		if (ambient >= 0) {
			this.ambient = ambient;
		} else {
			logger.error("ERROR: Material.ambient must be positive!");
		}
	}

	public float getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(float diffuse) {
		// The typical values are between 0 and 1
		if (diffuse >= 0) {
			this.diffuse = diffuse;
		} else {
			logger.error("ERROR: Material.diffuse must be positive!");
		}
	}

	public float getSpecular() {
		return specular;
	}

	public void setSpecular(float specular) {
		// The typical values are between 0 and 1
		if (specular >= 0) {
			this.specular = specular;
		} else {
			logger.error("ERROR: Material.specular must be positive!");
		}
	}

	public float getShininess() {
		return shininess;
	}

	public void setShininess(float shininess) {
		// Values between 10 (very large highlight) and 200 (very small highlight) seem to work 
		// best, though there is no actual upper bound
		if (shininess >= 0) {
			this.shininess = shininess;
		} else {
			logger.error("ERROR: Material.shininess must be positive!");
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(ambient, color, diffuse, shininess, specular);
	}

	/*
	 * Test the equality of 2 Materials.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Material other = (Material) obj;
		
		return Float.floatToIntBits(ambient) == Float.floatToIntBits(other.ambient)
				&& Objects.equals(color, other.color)
				&& Float.floatToIntBits(diffuse) == Float.floatToIntBits(other.diffuse)
				&& Float.floatToIntBits(shininess) == Float.floatToIntBits(other.shininess)
				&& Float.floatToIntBits(specular) == Float.floatToIntBits(other.specular);
	}
	
}
