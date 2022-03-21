package com.raytracer.engine.element;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.misc.Constants;

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
	private double ambient;
	
	// Diffuse reflection is light reflected from a matte surface. It depends only on the angle 
	// between the light source and the surface normal
	private double diffuse;
	
	// Specular reflection is the reflection of the light source itself and results in what is 
	// called a specular highlight—the bright spot on a curved surface.
	// It depends only on the angle between the reflection vector and the eye vector and is 
	// controlled by a parameter that we’ll call shininess.
	private double specular;
	
	// The higher the shininess, the smaller and tighter the specular highlight
	private double shininess;
	
	// The Material's color Pattern
	private Pattern pattern;
	
	/*
	 * Constructor.
	 */
	public Material() {
		// Default Material
		setColor(Factory.color(1, 1, 1));
		setAmbient(Constants.MATERIAL_AMBIENT);
		setDiffuse(Constants.MATERIAL_DIFFUSE);
		setSpecular(Constants.MATERIAL_SPECULAR);
		setShininess(Constants.MATERIAL_SHININESS);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getAmbient() {
		return ambient;
	}

	public void setAmbient(double ambient) {
		// The typical values are between 0 and 1
		if (ambient >= 0) {
			this.ambient = ambient;
		} else {
			logger.error("ERROR: Material.ambient must be positive!");
		}
	}

	public double getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(double diffuse) {
		// The typical values are between 0 and 1
		if (diffuse >= 0) {
			this.diffuse = diffuse;
		} else {
			logger.error("ERROR: Material.diffuse must be positive!");
		}
	}

	public double getSpecular() {
		return specular;
	}

	public void setSpecular(double specular) {
		// The typical values are between 0 and 1
		if (specular >= 0) {
			this.specular = specular;
		} else {
			logger.error("ERROR: Material.specular must be positive!");
		}
	}

	public double getShininess() {
		return shininess;
	}

	public void setShininess(double shininess) {
		// Values between 10 (very large highlight) and 200 (very small highlight) seem to work 
		// best, though there is no actual upper bound
		if (shininess >= 0) {
			this.shininess = shininess;
		} else {
			logger.error("ERROR: Material.shininess must be positive!");
		}
	}

	public Pattern getPattern() {
		return pattern;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}
	
	/*
	 * Prints this Material.
	 */
	@Override
	public String toString() {
		return "Material (color=" + color 
				+ ", ambient=" + ambient 
				+ ", diffuse=" + diffuse 
				+ ", specular=" + specular
				+ ", shininess=" + shininess 
				+ ", pattern=" + pattern + ")";
	}

	@Override
	public int hashCode() {
		return Objects.hash(ambient, color, diffuse, pattern, shininess, specular);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Material other = (Material) obj;
		return Double.doubleToLongBits(ambient) == Double.doubleToLongBits(other.ambient)
				&& Objects.equals(color, other.color)
				&& Double.doubleToLongBits(diffuse) == Double.doubleToLongBits(other.diffuse)
				&& Objects.equals(pattern, other.pattern)
				&& Double.doubleToLongBits(shininess) == Double.doubleToLongBits(other.shininess)
				&& Double.doubleToLongBits(specular) == Double.doubleToLongBits(other.specular);
	}
	
}
