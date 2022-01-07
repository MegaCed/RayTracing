package com.raytracer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Canvas;
import com.raytracer.engine.element.Color;
import com.raytracer.engine.element.Intersections;
import com.raytracer.engine.element.Matrix;
import com.raytracer.engine.element.PortablePixmap;
import com.raytracer.engine.element.Ray;
import com.raytracer.engine.element.Sphere;
import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.misc.Environment;
import com.raytracer.engine.misc.Projectile;
import com.raytracer.engine.misc.Simulator;
import com.raytracer.engine.operation.MatrixOperations;
import com.raytracer.engine.operation.SphereOperations;
import com.raytracer.engine.operation.TupleOperations;

/*
 * Used for miscellaneous testing...
 */
public class RayTracerApp {
	
	private static Logger logger = LoggerFactory.getLogger(RayTracerApp.class);
	
	private static final String PATH_LAPTOP = "//home//cedric//Projets//Ray Tracing//";
	private static final String PATH_DESKTOP = "//tmp//";

	/*
	 * Make some tests...
	 */
	public static void main(String[] args) {
		logger.info("Starting App...");

		// Miscellaneous stuff
		//misc();
		
		// Play with a catapult
		//catapult();
		
		// Play with a visual catapult
		//catapultWithUI();
		
		// Some questions related to matrices
		//matricesQuestions();
		
		// Analog clock
		// analogClock();
		
		// Shadow of a sphere
		sphereShadow();
		
		logger.info("Done!");
	}
	
	/*
	 * Miscellaneous...
	 */
	private static void misc() {
		// ...
	}
	
	/*
	 * Chapter 1: Tuples.
	 * 
	 * Try playing with this little program, firing virtual projectiles and seeing how far they go. 
	 * It’ll let you exercise the vector and point routines you’ve written.
	 */
	private static void catapult() {
		// Now, initialize a projectile and an environment. Use whatever values you want, but these might get you started:
		// - Projectile starts one unit above the origin (Y=1)
		// - Velocity is normalized to 1 unit/tick (X=1, Y=1)
		Tuple position = Factory.point(0, 1, 0);
		Tuple velocity = Factory.vector(1, 1, 0);
		velocity = TupleOperations.normalize(velocity);
		Projectile aProjectile = new Projectile(position, velocity);
		
		// - Gravity -0.1 unit/tick
		// - And wind is -0.01 unit/tick.
		Tuple gravity = Factory.vector(0, -0.1f, 0);
		Tuple wind = Factory.vector(-0.01f, 0, 0);
		Environment anEnvironment = new Environment(gravity, wind);
		
		// Then, run tick repeatedly until the projectile’s y position is less than or equal to 0. 
		// Report the projectile’s position after each tick, and the number of ticks it takes for 
		// the projectile to hit the ground.
		while (aProjectile.getPosition().getY() > 0) {
			aProjectile = Simulator.tick(anEnvironment, aProjectile);
			
			logger.info("---> " + aProjectile);
		}
		
		// Try multiplying the projectile’s initial velocity by larger and larger numbers to see how 
		// much farther the projectile goes!
	}
	
	/*
	 * Chapter 2: Canvas.
	 * 
	 * For this challenge, you’ll once again compute the trajectory of a projectile, just as before, 
	 * but this time you’ll plot its course on your brand-new canvas.
	 * After each tick, take the coordinates of the projectile and color the corresponding pixel on 
	 * the canvas. When the loop finishes, save your canvas to disk and view the result.
	 */
	private static void catapultWithUI() {
		Canvas aCanvas = Factory.canvas(900, 500);
		Color red = Factory.color(1, 0, 0);
		
		// The projectile’s velocity was normalized to a unit vector, and then multiplied by 11.25 
		// to increase its magnitude. That, and the velocity vector, and the canvas size, were all 
		// determined empirically.
		Tuple start = Factory.point(0, 1, 0);
		Tuple velocity = Factory.vector(1, 1.8f, 0);
		velocity = TupleOperations.normalize(velocity);
		velocity.setX(velocity.getX() * 11.25f);
		velocity.setY(velocity.getY() * 11.25f);
		Projectile aProjectile = new Projectile(start, velocity);
		
		// - Gravity -0.1 unit/tick
		// - And wind is -0.01 unit/tick.
		Tuple gravity = Factory.vector(0, -0.1f, 0);
		Tuple wind = Factory.vector(-0.01f, 0, 0);
		Environment anEnvironment = new Environment(gravity, wind);
		
		while (aProjectile.getPosition().getY() > 0) {
			aProjectile = Simulator.tick(anEnvironment, aProjectile);
			
			// The canvas y coordinate is upside-down compared to your world coordinates. It’s zero 
			// at the top of your canvas, and increases as you move down.
			// To convert your projectile’s coordinates to canvas coordinates, subtract the 
			// projectile’s y from the canvas’s height.
			int xAdjusted = (int)aProjectile.getPosition().getX();
			int yAdjusted = aCanvas.getHeight() - (int)aProjectile.getPosition().getY();
			
			aCanvas.writePixel(xAdjusted, yAdjusted, red);
		}
		
		// After your loop finishes, be sure to save your canvas to a file!
		PortablePixmap ppmFile = aCanvas.canvasToPPM();
		ppmFile.writeToFile(PATH_LAPTOP + "catapult.ppm");
	}
	
	/*
	 * Chapter 3: Matrices.
	 */
	private static void matricesQuestions() {
		question1();
		
		question2();
		
		question3();
		
		question4();
	}
	
	/*
	 * 1. What happens when you invert the identity matrix?
	 */
	private static void question1() {
		Matrix identityMatrix = Factory.identityMatrix();
		
		Matrix identityInverse = MatrixOperations.inverse(identityMatrix);
		
		logger.info("Identity Matrix inversed ---> " + identityInverse.toString());
		
		// Response: You get the identity matrix!
	}
	
	/*
	 * 2. What do you get when you multiply a matrix by its inverse?
	 */
	private static void question2() {
		float[][] values = {
				{6, 4, 4, 4},
				{5, 5, 7, 6},
				{4, -9, 3, -7},
				{9, 1, 7, -6}
		};

		Matrix aMatrix = new Matrix(values);

		Matrix inverse = MatrixOperations.inverse(aMatrix);

		Matrix result = MatrixOperations.mul(aMatrix, inverse);

		logger.info("Multiplied inversed ---> " + result.toString());
		
		// Response: You get the identity matrix!
	}
	
	/*
	 * 3. Is there any difference between the inverse of the transpose of a matrix, and the 
	 * transpose of the inverse?
	 */
	private static void question3() {
		float[][] values = {
				{6, 4, 4, 4},
				{5, 5, 7, 6},
				{4, -9, 3, -7},
				{9, 1, 7, -6}
		};

		Matrix aMatrix = new Matrix(values);
		
		Matrix transposed = MatrixOperations.transpose(aMatrix);
		
		Matrix inverseTransposed = MatrixOperations.inverse(transposed);
		
		Matrix inverse = MatrixOperations.inverse(aMatrix);
		
		Matrix transposedInversed = MatrixOperations.transpose(inverse);
		
		logger.info("inverseTransposed ---> " + inverseTransposed.toString());
		logger.info("transposedInversed ---> " + transposedInversed.toString());
		
		// Response: No, same thing.
	}
	
	/*
	 * 4. Remember how multiplying the identity matrix by a tuple gives you the tuple, unchanged?
	 * Now, try changing any single element of the identity matrix to a different number, and then 
	 * multiplying it by a tuple. What happens to the tuple?
	 */
	private static void question4() {
		Matrix identityMatrix = Factory.identityMatrix();
		
		identityMatrix.setElement(3, 0, 0);
		
		Tuple aTuple = new Tuple(1, 2, 3, 1);
		
		Tuple result = MatrixOperations.mul(identityMatrix, aTuple);
		
		logger.info("Identity Matrix ---> " + identityMatrix.toString());
		logger.info("Tuple ---> " + result.toString());
		
		// Response: The tuple is modified.
	}
	
	/*
	 * Chapter 4: Picture an analog clock.
	 */
	private static void analogClock() {
		Color white = Factory.color(1, 1, 1);
		
		// The Canvas is square
		int canvasSize = 500;
		
		// First, assume the clock is centered at the origin, point(0,0,0). Let the origin be in the 
		// middle of your canvas
		Canvas aCanvas = Factory.canvas(canvasSize, canvasSize);
		int canvasCenter = aCanvas.getHeight() / 2;
		
		// Decide how large the clock is to be drawn on your canvas. For example, if your canvas is 
		// square, you might let the clock’s radius be 3⁄8 the canvas’s width. 
		int radius = canvasSize * 3 / 8;
		
		// Twelve o’clock is on the +y axis at point(0,1,0), and three o’clock is on the +x axis at 
		// point(1,0,0)
		Tuple twelveOclock = Factory.point(0, 1, 0);
		
		// Now, rotate the twelve o’clock point around the z axis to find the other hour positions. 
		// There are 2PI radians in a circle, so each hour is rotated 2PI/12 (or PI/6) radians
		for (int hour = 0; hour < 12; hour++) {
			// Next, choose an axis to orient the clock. If, for example, it’s oriented along the z 
			// axis and you’re looking at it face-on, then you’re looking toward the negative end of 
			// the z axis
			Matrix rotation = Factory.zRotationMatrix((float)(hour * (Math.PI / 6)));
			
			Tuple result = MatrixOperations.mul(rotation, twelveOclock);
			
			// For each point that you compute, multiply the x and z components by this radius, and 
			// then move them to the center of your canvas by adding the coordinates of the center 
			// point
			int xAdjusted = (int)(result.getX() * radius) + canvasCenter;
			int yAdjusted = (int)(result.getY() * radius) + canvasCenter;
			
			aCanvas.writePixel(xAdjusted, yAdjusted, white);
		}
		
		// Save canvas to a file
		PortablePixmap ppmFile = aCanvas.canvasToPPM();
		ppmFile.writeToFile(PATH_LAPTOP + "clock.ppm");
	}

	/*
	 * Chapter 5: Casts rays at a sphere and draws the picture to a canvas.
	 * Any ray that hits the sphere should result in a colored pixel (red, for example), and any 
	 * miss should be drawn in black. The result will be a silhouette of the sphere—not 
	 * three-dimensional, but definitely round!
	 */
	// TODO: DISABLE LOGGING BEFORE RUNNING THIS!!
	private static void sphereShadow() {
		// Think as if you’re trying to cast the shadow of your object onto some wall behind it
		Color red = Factory.color(1, 0, 0);
		Sphere aSphere = Factory.sphere();
		
		// Try deforming the sphere with some transformations and see what happens.
		// Shrink it along the y axis
		//aSphere.setTransform(Factory.scalingMatrix(1, 0.5f, 1));
		// Shrink it along the x axis
		//aSphere.setTransform(Factory.scalingMatrix(0.5f, 1, 1));
		// Shrink it, and rotate it!
		//aSphere.setTransform(MatrixOperations.mul(Factory.xRotationMatrix((float)Math.PI / 4), Factory.scalingMatrix(0.5f, 1, 1)));
		// Shrink it, and skew it!
		//aSphere.setTransform(MatrixOperations.mul(Factory.shearingMatrix(1, 0, 0, 0, 0, 0), Factory.scalingMatrix(0.5f, 1, 1)));
		
		// Decide how large you want your canvas to be (in pixels).
		// A canvas 100 pixels on a side is probably good for starting with.
		// (Larger images will take exponentially longer to render)
		int canvasPixels = 100;
		Canvas aCanvas = Factory.canvas(canvasPixels, canvasPixels);

		// Figure out how far your ray’s origin is from the sphere. Also, decide where your wall 
		// will be.
		// Moving the ray origin closer to the sphere will make the sphere in the drawing larger. 
		// Moving it farther away will make the sphere smaller. Moving the wall will do similarly
		float wallZ = 10;
		float wallSize = 7;
		
		// Once you know how many pixels fit along each side of the wall, you can divide the wall 
		// size by the number of pixels to get the size of a single pixel (in world space units)
		float pixelSize = wallSize / canvasPixels;
		
		// Then, assume you’re looking directly at the center of the sphere. Half of the wall will 
		// be to the left of that, and half to the right.
		// Since the wall is centered around the origin (because the sphere is at the origin), this 
		// means that this half variable describes the minimum and maximum x and y coordinates of 
		// your wall
		float half = wallSize / 2;
		
		// Start the ray here, everything will be computed from this point
		Tuple rayOrigin = Factory.point(0, 0, -5);
		
		// For each row of pixels in the canvas
		for (int y = 0; y < canvasPixels; y++) {
			// Compute the world y coordinate (top = +half, bottom = -half)
			// In world	space, the y coordinate increases as you go up, and decreases as you go 
			// down. But on the canvas, the top is at y = 0, and y increases as you go down. Thus, 
			// to render the circle correctly, you have to flip the y coordinate, which is 
			// accomplished by subtracting it from its maximum value (the top of the wall, or half)
			float worldY = half - pixelSize * y;
			
			// For each pixel in the row
			for (int x = 0; x < canvasPixels; x++) {
				// Compute the world x coordinate (left = -half, right = half)
				float worldX = half - pixelSize * x;
				
				// Describe the point on the wall that the ray will target
				Tuple position = Factory.point(worldX, worldY, wallZ);
				
				// You cast each ray from some starting point toward some point on the wall that 
				// corresponds to a position on your canvas. If the ray intersects the sphere, a 
				// shadow is cast, which you’ll mark with a colored pixel
				Tuple rayDirection = TupleOperations.normalize(TupleOperations.sub(position, rayOrigin));
				Ray aRay = Factory.ray(rayOrigin, rayDirection);
				Intersections intersections = Factory.intersections(SphereOperations.intersects(aSphere, aRay));
				
				if (SphereOperations.hit(intersections) != null) {
					aCanvas.writePixel(x, y, red);
				}
			}
		}
		
		// Save canvas to a file
		PortablePixmap ppmFile = aCanvas.canvasToPPM();
		ppmFile.writeToFile(PATH_DESKTOP + "shadow.ppm");
	}
	
}
