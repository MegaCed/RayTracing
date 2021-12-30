package com.raytracer.engine.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.element.Color;
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
		
		float red = color1.getRed() + color2.getRed();
		float green = color1.getGreen() + color2.getGreen();
		float blue = color1.getBlue() + color2.getBlue();
		
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
		
		float red = color1.getRed() - color2.getRed();
		float green = color1.getGreen() - color2.getGreen();
		float blue = color1.getBlue() - color2.getBlue();
		
		Color result = new Color(red, green, blue);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Result of subtraction = " + result);
		
		return result;
	}
	
	/*
	 * Multiplying a color by a scalar.
	 */
	public static Color mul(Color aColor, Float scalar) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Multiply Color: " + aColor + " by " + scalar);
		
		// Multiply each component of the Color by the scalar
		float red = aColor.getRed() * scalar;
		float green = aColor.getGreen() * scalar;
		float blue = aColor.getBlue() * scalar;
		
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
		
		float red = color1.getRed() * color2.getRed();
		float green = color1.getGreen() * color2.getGreen();
		float blue = color1.getBlue() * color2.getBlue();

		// This method of blending two colors works by multiplying corresponding components of each 
		// color to form a new color. It’s technically called the Hadamard product (or Schur 
		// product)
		Color result = new Color(red, green, blue);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Result of multiplication = " + result);
		
		return result;
	}
	
}
