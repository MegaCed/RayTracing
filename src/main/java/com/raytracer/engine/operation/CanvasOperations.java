package com.raytracer.engine.operation;

import com.raytracer.engine.element.Canvas;
import com.raytracer.engine.element.Color;

/*
 * Perform some operations on Canvas.
 */
public class CanvasOperations {

	/*
	 * Write a pixel to a Canvas.
	 */
	public void writePixel(Canvas aCanvas, int x, int y, Color aColor) {
		aCanvas.setPixel(x, y, aColor);
	}
	
}
