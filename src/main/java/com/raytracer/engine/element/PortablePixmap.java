package com.raytracer.engine.element;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.raytracer.engine.misc.Constants;

/*
 * the Portable Pixmap (PPM) format from the Netpbm project.
 * This version (called “plain” PPM) is straight text.
 * 
 * 
 * 
 */
public class PortablePixmap {
	
	// PPM magic number
	public static final String MAGIC_NUMBER = "P3";
	
	// Used to scale the Colors
	public static final int MAXIMUM_COLOR_VALUE = 255;
	
	// Most image programs tend to accept PPM images with lines longer than that
	public static final int MAXIMUM_LINE_LENGTH = 70;
	
	// The size of the header (in # of lines)
	public static final int HEADER_OFFSET = 3;
	
	// The array of lines (contents of the PPM file)
	private String[] contents;
	
	// How wide this image is
	private int width;
	
	// How high this image is
	private int height;
	
	/*
	 * Constructor.
	 */
	public PortablePixmap(int width, int height, Color[][] pixels) {
		this.width = width;
		this.height = height;
		
		// Create the correct number of lines
		this.contents =new String[height + HEADER_OFFSET];
		
		// Create the header
		setHeader(width, height);
		
		// Create the data
		setData(pixels);
	}
	
	/*
	 * Every plain PPM file begins with a header consisting of three lines of text. The following 
	 * figure shows one possible header:
	 *     P3
	 *     80 40
	 *     255
	 * The first line is the string P3 (which is the identifier, or “magic number,” for the flavor 
	 * of PPM we’re using), followed by a new line.
	 * The second line consists of two numbers which describe the image’s width and height in 
	 * pixels. The header in the previous figure describes an image that is 80 pixels wide, and 40 
	 * tall. 
	 * The third line (255) specifies the maximum color value, which means that each red, green, and 
	 * blue value will be scaled to lie between 0 and 255, inclusive.
	 */
	public void setHeader(int width, int height) {
		// 1st line
		this.contents[0] = MAGIC_NUMBER;
		
		// 2nd line
		this.contents[1] = width + Constants.SPACE + height;
		
		// 3rd line
		this.contents[2] = "" + MAXIMUM_COLOR_VALUE;
	}
	
	/*
	 * Returns the 3 first lines of the PPM file.
	 */
	public String getHeader() {
		StringBuffer result = new StringBuffer();
		
		for (int line = 0; line < HEADER_OFFSET; line++) {
			result.append(contents[line]).append(Constants.END_OF_LINE);
		}
		
		return result.toString();
	}
	
	/*
	 * Immediately following this header is the pixel data, which contains each pixel represented as 
	 * three integers: red, green, and blue. Each component should be scaled to between 0 and the 
	 * maximum color value given in the header (for example, 255), and each value should be 
	 * separated from its neighbors by a space.
	 */
	public void setData(Color[][] pixels) {
		// Create all the lines of data
		for (int lineNumber = 0; lineNumber < height; lineNumber++) {
			// A new line of data
			StringBuffer line = new StringBuffer();
			
			for (int pixelNumber = 0; pixelNumber < width; pixelNumber++) {
				// Retrieve the current pixel's color
				double red = pixels[lineNumber][pixelNumber].getRed();
				double green = pixels[lineNumber][pixelNumber].getGreen();
				double blue = pixels[lineNumber][pixelNumber].getBlue();
				
				// Scale the colors to their scaled values
				int redScaled = scale(red, MAXIMUM_COLOR_VALUE);
				int greenScaled = scale(green, MAXIMUM_COLOR_VALUE);
				int blueScaled = scale(blue, MAXIMUM_COLOR_VALUE);
				
				// Pretty print this pixel
				String pixel = redScaled + Constants.SPACE + greenScaled + Constants.SPACE + blueScaled + Constants.SPACE;
				
				line.append(pixel);
			}
			
			// Terminate line. Some image programs won’t process PPM files correctly unless the 
			// files are terminated by a newline character.
			line.append(Constants.END_OF_LINE);
			
			// Write it to the PPM data
			this.contents[lineNumber + HEADER_OFFSET] = line.toString();
		}
	}
	
	/*
	 * Returns the PPM file's data.
	 */
	public String getData() {
		StringBuffer result = new StringBuffer();
		
		// Exclude the header
		for (int line = HEADER_OFFSET; line < getHeight() + HEADER_OFFSET; line++) {
			// Don't exceed the maximum length
			String aLine = splitLine(contents[line], MAXIMUM_LINE_LENGTH);
			
			result.append(aLine);
		}
		
		return result.toString();
	}
	
	/*
	 * Each component should be scaled to between 0 and the maximum color value given in the header.
	 */
	private int scale(double aNumber, int maximumValue) {
		// Round it up to the next integer
		int result = (int) Math.ceil(maximumValue * aNumber);
		
		// Don't keep out-of-bounds results
		if (result < 0) {
			return 0;
		}
		if (result > maximumValue) {
			return maximumValue;
		}
		
		return result;
	}
	
	/*
	 * No line in a PPM file should be more than 70 characters long.
	 * Recursively split the line for the given length.
	 */
	private String splitLine(String aString, int maximumLength) {
		if ((aString.length() <= maximumLength) || (Constants.SPACE.equals(aString))) {
			// The line is short enough
			return aString;
		} else {
			String aCharacter = null;
			int splitIndex = maximumLength + 1;
			
			// Just be careful to put the new line where a space would have gone, so you don’t split a 
			// number in half!
			do {
				// Compute the value where the line must be split
				splitIndex--;
				
				// Get the character at the index
				aCharacter = Character.toString(aString.charAt(splitIndex));
			} while (!Constants.SPACE.equals(aCharacter));	
			
		    String firstPart = aString.substring(0, splitIndex);
		    
		    // Recursively split the rest of the line
		    return firstPart + Constants.END_OF_LINE + splitLine(aString.substring(splitIndex, aString.length()), maximumLength);
		}
	}
	
	/*
	 * Writes this PPM to a file.
	 */
	public void writeToFile(String fileName) {
		try {
			StringBuffer contents = new StringBuffer();
			contents.append(getHeader());
			contents.append(getData());
			
		    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		    writer.write(contents.toString());
		    
		    writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Prints this PPM.
	 */
	public String toString() {
		StringBuffer result = new StringBuffer();
		
		result.append("PPM File: ").append(Constants.END_OF_LINE);
		result.append(getHeader());
		result.append(getData());
		
		return result.toString();
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
