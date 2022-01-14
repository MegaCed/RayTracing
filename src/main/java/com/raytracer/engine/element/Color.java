package com.raytracer.engine.element;

import java.util.Objects;

import com.raytracer.engine.operation.MiscOperations;

/*
 * Each pixel on your computer monitor is a composite of three colors: red, green, and blue. If you 
 * take those three colors and mix them in different quantities, you get just about every other 
 * color you can imagine, from red, yellow, and green, to cyan, blue, and purple, and everything in 
 * between.
 * 
 * If you let red, green, and blue each be a value between 0 and 1 (with 0 meaning the color is 
 * entirely absent, and 1 meaning the color is fully present).
 * 
 * If all components are 1, you get white. If all components are 0, you get black.
 * In practice, you’ll only use numbers between 0 and 1.
 */
public class Color {
	
	// Red color
	private double red;
	
	// Green color
	private double green;
	
	// Blue color
	private double blue;
	
	/*
	 * Constructor.
	 */
	public Color(double red, double green, double blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	/*
	 * Prints this Color.
	 */
	@Override
	public String toString() {
		String result = "Color (red = " + red + ", green = " + green + ", blue = " + blue + ")";
		
		return result;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(blue, green, red);
	}

	/*
	 * Test the equality of 2 Colors.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Color other = (Color) obj;
		
		return MiscOperations.equalsEpsilon(blue, other.getBlue())
				&& MiscOperations.equalsEpsilon(green, other.getGreen())
				&& MiscOperations.equalsEpsilon(red, other.getRed());
	}

	public double getRed() {
		return red;
	}

	public void setRed(double red) {
		this.red = red;
	}

	public double getGreen() {
		return green;
	}

	public void setGreen(double green) {
		this.green = green;
	}

	public double getBlue() {
		return blue;
	}

	public void setBlue(double blue) {
		this.blue = blue;
	}

}
