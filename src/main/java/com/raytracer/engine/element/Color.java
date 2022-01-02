package com.raytracer.engine.element;

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
	private float red;
	
	// Green color
	private float green;
	
	// Blue color
	private float blue;
	
	/*
	 * Constructor.
	 */
	public Color(float red, float green, float blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	/*
	 * Prints this Color.
	 */
	@Override
	public String toString() {
		String result = "Color (red=" + red + ", green=" + green + ", blue=" + blue + ")";
		
		return result;
	}
	
	public float getRed() {
		return red;
	}

	public void setRed(float red) {
		this.red = red;
	}

	public float getGreen() {
		return green;
	}

	public void setGreen(float green) {
		this.green = green;
	}

	public float getBlue() {
		return blue;
	}

	public void setBlue(float blue) {
		this.blue = blue;
	}

}
