package com.raytracer.engine.element;

/*
 * A canvas is just a rectangular grid of pixels—much like your computer screen.
 */
public class Canvas {
	// How wide this Canvas is
	private int width;
	
	// How high this Canvas is
	private int height;
	
	// Pixels of this Canvas
	private Color[][] pixels;
	
	/*
	 * Constructor.
	 */
	public Canvas(int width, int height) {
		this.width = width;
		this.height = height;
		this.pixels = new Color[width][height];
		
		// Initialize all pixels black
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				this.pixels[x][y] = new Color(0, 0, 0);
			}
		}
	}
	
	/*
	 * Set a pixel with a given Color.
	 */
	public void setPixel(int x, int y, Color aColor) {
		this.pixels[x][y] = aColor;
	}
	
	/*
	 * Retrieve the Color of a given pixel.
	 */
	public Color getColorAt(int x, int y) {
		return this.pixels[x][y];
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
