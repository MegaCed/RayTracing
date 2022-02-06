package com.raytracer.engine.element;

import com.raytracer.engine.Factory;

/*
 * Just like a real camera, your virtual camera will let you “take pictures” of your scene. 
 * You can move it around, zoom in and out, and even rotate the camera upside down if that’s the 
 * shot you want.
 * 
 * One of the primary responsibilities of the camera is to map the three-dimensional scene onto a 
 * two-dimensional canvas. To do this, you’ll make the camera place the canvas somewhere in the 
 * scene so that rays can be projected through it. The camera’s canvas will always be exactly one 
 * unit in front of the camera.
 */
public class Camera {
	
	// The horizontal size (in pixels) of the canvas that the picture will be rendered to
	private double hSize;
	
	// The canvas’s vertical size (in pixels)
	private double vSize;
	
	// is an angle that describes how much the camera can see. When the field of view is small, the 
	// view will be “zoomed in,” magnifying a smaller area of the scene
	private double fieldOfView;
	
	// A matrix describing how the world should be oriented relative to the camera.
	// This is usually a view transformation
	private Matrix transform;
	
	// Half of the width
	private double halfWidth;
	
	// Half of the height
	private double halfHeight;
	
	// The size of a single pixel on the canvas
	private double pixelSize;
	
	/*
	 * Constructor.
	 */
	public Camera(double hSize, double vSize, double fieldOfView) {
		this.hSize = hSize;
		this.vSize = vSize;
		this.fieldOfView = fieldOfView;
		this.transform = Factory.identityMatrix();
		
		computePixelSize();
	}
	
	/*
	 * Make sure the camera knows the size (in world-space units) of the pixels on the canvas.
	 */
	private void computePixelSize() {
		// You know the canvas is one unit away, and you know the angle of the field of view.
		// By cutting the field of view in half, you create a right triangle
		// The width of that half of the canvas, then, can be computed by taking the tangent of half 
		// of the field of view
		double halfView = Math.tan(fieldOfView / 2);
		
		// The aspect ratio is the ratio of the horizontal size of the canvas, to its vertical size
		double aspect = hSize / vSize;
		
		if (aspect >= 1) {
			// Now, if the horizontal size is greater than or equal to the vertical size 
			// (aspect ≥ 1), then half_view is half the width of the canvas, and half_view⁄aspect is 
			// half the canvas’s height.
			halfWidth = halfView;
			halfHeight = halfView / aspect;
		} else {
			// If the vertical size is greater than the horizontal size (aspect < 1), then half_view 
			// is instead half the height of the canvas, and half the canvas’s width is half_view × 
			// aspect.
			halfWidth = halfView * aspect;
			halfHeight = halfView;
		}
		
		// Finally, compute the size of a single pixel on the canvas by dividing the full width the 
		// canvas (half_width × 2) by the horizontal size (in pixels) of the canvas (hsize).
		pixelSize = (halfWidth * 2) / hSize; 
	}
	
	/*
	 * Prints this object.
	 */
	@Override
	public String toString() {
		String result = "Camera (hSize = " + hSize + " - vSize = " + vSize + " - fieldOfView = " + fieldOfView + " - transform = " + transform + ")";
		
		return result;
	}

	public double gethSize() {
		return hSize;
	}


	public double getvSize() {
		return vSize;
	}

	public double getFieldOfView() {
		return fieldOfView;
	}

	public Matrix getTransform() {
		return transform;
	}

	public void setTransform(Matrix transform) {
		this.transform = transform;
	}

	public double getHalfWidth() {
		return halfWidth;
	}

	public double getHalfHeight() {
		return halfHeight;
	}

	public double getPixelSize() {
		return pixelSize;
	}
	
}
