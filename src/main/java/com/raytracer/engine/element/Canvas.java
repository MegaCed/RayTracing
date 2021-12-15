package com.raytracer.engine.element;

/*
 * A canvas is just a rectangular grid of pixels—much like your computer screen.
 * 
 * You won’t need much methods for writing to your canvas, since your raytracer will work 
 * pixel-by-pixel over the entire scene.
 * 
 * (Height - Y - row)
 * ^
 * |
 * |
 * |
 * |
 * +---------> (Width - X - column)
 */
public class Canvas {
	// How wide this Canvas is (X coordinate)
	private int width;
	
	// How high this Canvas is (Y coordinate)
	private int height;
	
	// Pixels of this Canvas
	private Color[][] pixels;
	
	/*
	 * Constructor.
	 * 
	 * Note that the x and y parameters are assumed to be 0-based.
	 * That is to say, x may be anywhere from 0 to width - 1 (inclusive), and y may be anywhere from 
	 * 0 to height - 1 (inclusive).
	 */
	public Canvas(int width, int height) {
		this.width = width;
		this.height = height;
		this.pixels = new Color[height][width];
		
		// Initialize all pixels black
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				this.pixels[y][x] = new Color(0, 0, 0);
			}
		}
	}
	
	/*
	 * Set a pixel with a given Color.
	 */
	public void writePixel(int x, int y, Color aColor) {
		// It’s going to be really, really easy to accidentally plot a point that is outside the 
		// bounds of your canvas. Make sure you handle this case, either by having the canvas ignore 
		// points outside its bounds or by preventing your program from plotting such points in the 
		// first place.
		if (x <= width && y <= height) {
			this.pixels[y][x] = aColor;
		}
	}
	
	/*
	 * Retrieve the Color of a given pixel.
	 */
	public Color pixelAt(int x, int y) {
		if (x <= width && y <= height) {
			return this.pixels[y][x];			
		} else {
			return null;
		}
	}
	
	/*
	 * Creates a PPM file from this Canvas.
	 */
	public PortablePixmap canvasToPPM() {
		PortablePixmap ppm = new PortablePixmap(getWidth(), getHeight(), pixels);
		return ppm;
	}
	
	/*
	 * Prints this Canvas.
	 */
	public String toString() {
		String result = "Canvas (width=" + width + ", height=" + height + ")";
		return result;
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
