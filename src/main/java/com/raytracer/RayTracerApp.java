package com.raytracer;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Canvas;
import com.raytracer.engine.element.Color;
import com.raytracer.engine.element.PortablePixmap;
import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.misc.Environment;
import com.raytracer.engine.misc.Projectile;
import com.raytracer.engine.misc.Simulator;
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
		CatapultWithUI();
		
		logger.info("Done!");
	}
	
	/*
	 * Miscellaneous...
	 */
	private static void misc() {
		// ...
	}
	
	/*
	 * Simulates a catapult.
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
	 * Catapult with a UI.
	 */
	private static void CatapultWithUI() {
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
	
}
