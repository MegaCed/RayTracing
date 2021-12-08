package com.raytracer.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Colors, as you’ll see, tend to interact with each other. Whether it’s a green light reflecting on 
 * a yellow surface, or a blue surface viewed through a red glass, or some other combination of 
 * transparency and reflection, colors can affect each other.
 *
 * Fortunately, we can handle all of these combinations with just four operations: adding and 
 * subtracting colors, multiplying a color by a scalar, and multiplying a color by another color.
 */
public class ColorOperation {

	private static Logger logger = LoggerFactory.getLogger(ColorOperation.class);
	
	/*
	 * Adding Colors.
	 */
	public static Color add(Color color1, Color color2) {
		logger.debug("Adding 2 Colors...");
		logger.debug("1st Color: " + color1);
		logger.debug("2nd Color: " + color2);
		
		float red = color1.getRed() + color2.getRed();
		float green = color1.getGreen() + color2.getGreen();
		float blue = color1.getBlue() + color2.getBlue();
		
		Color result = new Color(red, green, blue);
		
		logger.debug("Result of addition: " + result);
		
		return result;
	}
	
}
