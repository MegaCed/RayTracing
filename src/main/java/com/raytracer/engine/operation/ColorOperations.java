package com.raytracer.engine.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.element.Color;
import com.raytracer.engine.element.Material;
import com.raytracer.engine.element.Pattern;
import com.raytracer.engine.element.PointLight;
import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.misc.Constants;

/*
 * Colors, as you’ll see, tend to interact with each other. Whether it’s a green light reflecting on 
 * a yellow surface, or a blue surface viewed through a red glass, or some other combination of 
 * transparency and reflection, colors can affect each other.
 *
 * Fortunately, we can handle all of these combinations with just four operations: adding and 
 * subtracting colors, multiplying a color by a scalar, and multiplying a color by another color.
 */
public class ColorOperations {

	private static Logger logger = LoggerFactory.getLogger(ColorOperations.class);
	
	/*
	 * Adding Colors.
	 */
	public static Color add(Color color1, Color color2) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Adding 2 Colors...");
		logger.debug("1st Color: " + color1);
		logger.debug("2nd Color: " + color2);
		
		double red = color1.getRed() + color2.getRed();
		double green = color1.getGreen() + color2.getGreen();
		double blue = color1.getBlue() + color2.getBlue();
		
		Color result = new Color(red, green, blue);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Result of addition = " + result);
		
		return result;
	}
	
	/*
	 * Subtracting colors.
	 */
	public static Color sub(Color color1, Color color2) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Subtracting 2 Colors...");
		logger.debug("1st Color: " + color1);
		logger.debug("2nd Color: " + color2);
		
		double red = color1.getRed() - color2.getRed();
		double green = color1.getGreen() - color2.getGreen();
		double blue = color1.getBlue() - color2.getBlue();
		
		Color result = new Color(red, green, blue);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Result of subtraction = " + result);
		
		return result;
	}
	
	/*
	 * Multiplying a color by a scalar.
	 */
	public static Color mul(Color aColor, double scalar) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Multiply Color: " + aColor + " by " + scalar);
		
		// Multiply each component of the Color by the scalar
		double red = aColor.getRed() * scalar;
		double green = aColor.getGreen() * scalar;
		double blue = aColor.getBlue() * scalar;
		
		Color result = new Color(red, green, blue);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Result of multiplication = " + result);
		return result;
	}
	
	/*
	 * Multiplying colors.
	 * 
	 * Used to blend two colors together. You’ll use it when (for example) you want to know the 
	 * visible color of a yellow-green surface when illuminated by a reddish-purple light.
	 */
	public static Color mul(Color color1, Color color2) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Multiplying 2 Colors...");
		logger.debug("1st Color: " + color1);
		logger.debug("2nd Color: " + color2);
		
		double red = color1.getRed() * color2.getRed();
		double green = color1.getGreen() * color2.getGreen();
		double blue = color1.getBlue() * color2.getBlue();

		// This method of blending two colors works by multiplying corresponding components of each 
		// color to form a new color. It’s technically called the Hadamard product (or Schur 
		// product)
		Color result = new Color(red, green, blue);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Result of multiplication = " + result);
		return result;
	}
	
	/*
	 * This lighting() function is what will shade your objects so that they appear 
	 * three-dimensional. It expects five arguments: 
	 *  - the material itself
	 *  - the point being illuminated
	 *  - the light source
	 *  - the eye and normal vectors from the Phong reflection model
	 *  
	 *  In a nutshell, it will add together the material’s ambient, diffuse, and specular 
	 *  components, weighted by the angles between the different vectors.
	 */
	public static Color lithting(Material aMaterial, PointLight lightSource, Tuple illluminatedPoint, Tuple eyeVector, Tuple normalVector, boolean inShadow) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Lighting a point...");
		logger.debug("Material: " + aMaterial);
		logger.debug("Light source: " + lightSource);
		logger.debug("Illuminated point: " + illluminatedPoint);
		logger.debug("Eye vector: " + eyeVector);
		logger.debug("Normal vector: " + normalVector);
		
		// Combine the surface color with the light's color/intensity
		Color effectiveColor = ColorOperations.mul(aMaterial.getColor(), lightSource.getIntensity());
		
		// Find the direction to the light source
		Tuple lightVector = TupleOperations.normalize(TupleOperations.sub(lightSource.getPosition(), illluminatedPoint));
		
		// Compute the ambient contribution
		Color ambient = ColorOperations.mul(effectiveColor, aMaterial.getAmbient());
		
		// lightDotNormal represents the cosine of the angle between the light vector and the normal 
		// vector
		double lightDotNormal = TupleOperations.dot(lightVector, normalVector);
		
		Color diffuse;
		Color specular;
		Color result;
		
		// ignore the specular and diffuse components when in_shadow is true
		if (!inShadow) {
			// A negative number means the light is on the other side of the surface
			if (lightDotNormal < 0) {
				diffuse = Constants.COLOR_BLACK;
				specular = Constants.COLOR_BLACK;
			} else {
				// Compute the diffuse contribution
				diffuse = ColorOperations.mul(ColorOperations.mul(effectiveColor, aMaterial.getDiffuse()), lightDotNormal);
				
				// reflectDotEye represents the cosine of the angle between the reflection vector and 
				// the eye vector
				Tuple reflectVector = TupleOperations.reflect(TupleOperations.neg(lightVector), normalVector);
				double reflectDotEye = TupleOperations.dot(reflectVector, eyeVector);
				
				// A negative number means the light reflects away from the eye
				if (reflectDotEye < 0) {
					specular = Constants.COLOR_BLACK;
				} else {
					// compute the specular contribution
					double factor = Math.pow(reflectDotEye, aMaterial.getShininess());
					specular = ColorOperations.mul(ColorOperations.mul(lightSource.getIntensity(), aMaterial.getSpecular()), factor);
				}
			}
			
			// Add the three contributions together to get the final shading
			result = ColorOperations.add(ColorOperations.add(ambient, diffuse), specular);
		} else {
			// The diffuse component relies on the vector to the light source, and the specular 
			// component depends on the reflection vector.
			// Since both components have a dependency on the light source, the lighting() function 
			// should ignore them when the point is in shadow and use only the ambient component
			result = ambient;
		}
		
		logger.debug(Constants.SEPARATOR_RESULT + "Result of lighting = " + result);
		return result;
	}
	
	/*
	 * Function that chooses the color at a given point.
	 */
	public static Color stripeAt(Pattern aPattern, Tuple aPoint) {
		// As the x coordinate changes, the pattern alternates between the two colors. The other two 
		// dimensions, y and z, have no effect on it.
		double x = aPoint.getX();

		// That is to say, if the x coordinate is between 0 and 1, return the first color. If 
		// between 1 and 2, return the second, and so forth, alternating between the two.
		if (Math.floor(x) % 2 == 0) {
			return aPattern.getA();
		} else {
			return aPattern.getB();
		}
	}
	
}
