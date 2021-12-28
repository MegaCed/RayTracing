package com.raytracer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Canvas;
import com.raytracer.engine.element.Color;
import com.raytracer.engine.element.Matrix;
import com.raytracer.engine.element.PortablePixmap;
import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.misc.Environment;
import com.raytracer.engine.misc.Projectile;
import com.raytracer.engine.misc.Simulator;
import com.raytracer.engine.operation.MatrixOperations;
import com.raytracer.engine.operation.TupleOperations;

/*
 * Used for miscellaneous testing...
 */
public class RayTracerApp {
	
	private static Logger logger = LoggerFactory.getLogger(RayTracerApp.class);

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
		analogClock();
		
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
		TupleOperations.normalize(velocity);
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
		TupleOperations.normalize(velocity);
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
		ppmFile.writeToFile("//home//cedric//Projets//Ray Tracing//catapult.ppm");
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
		Canvas aCanvas = Factory.canvas(500, 500);
		Color red = Factory.color(1, 0, 0);
		
		// First, assume the clock is centered at the origin, point(0,0,0). Let the origin be in the 
		// middle of your canvas
		Tuple origin = Factory.point(0, 0, 0);
		
		
		// Next, choose an axis to orient the clock. If, for example, it’s oriented along the y axis 
		// and you’re looking at it face-on, then you’re looking toward the negative end of the y 
		// axis
		
		
		// Now, rotate the twelve o’clock point around the y axis to find the other hour positions. 
		// There are 2PI radians in a circle, so each hour is rotated 2PI/12 (or PI/6) radians
		
		
		// Decide how large the clock is to be drawn on your canvas. For example, if your canvas is 
		// square, you might let the clock’s radius be 3⁄8 the canvas’s width. For each point that 
		// you compute, multiply the x and z components by this radius, and then move them to the 
		// center of your canvas by adding the coordinates of the center point. Let x be the x 
		// coordinate of the pixel, and z be the y coordinate
		
		
		
		for (int hour = 0; hour < 12; hour++) {
			Matrix rotation = Factory.yRotationMatrix((float)(hour * (Math.PI / 6)));
			
			// TODO
			
			
			
		}
		
		
		int xAdjusted = aCanvas.getHeight() / 2;
		int yAdjusted = aCanvas.getWidth() / 2;
		aCanvas.writePixel(xAdjusted, yAdjusted, red);
	
		// Save canvas to a file
		PortablePixmap ppmFile = aCanvas.canvasToPPM();
		ppmFile.writeToFile("//home//cedric//Projets//Ray Tracing//clock.ppm");
	}
	
}
