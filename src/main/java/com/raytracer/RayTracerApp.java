package com.raytracer;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.TupleOperation;
import com.raytracer.engine.Color;
import com.raytracer.engine.Environment;
import com.raytracer.engine.Factory;
import com.raytracer.engine.Projectile;
import com.raytracer.engine.Simulator;
import com.raytracer.engine.Tuple;

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

		// Play with a catapult
		//catapult();
		
		// Test Colors
		playWithColors();
		
		logger.info("Done!");
	}
	
	/*
	 * Simulates a catapult.
	 */
	private static void catapult() {
		// Now, initialize a projectile and an environment. Use whatever values you want, but these might get you started:
		// - Projectile starts one unit above the origin (Y=1)
		// - Velocity is normalized to 1 unit/tick (X=1, Y=1)
		Tuple position = Factory.point(0, 1, 0);
		Tuple velocity = TupleOperation.normalize(Factory.vector(1, 1, 0));
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
	 * Plays with Colors...
	 */
	public static void playWithColors() {
		Color aColor = Factory.color(0f, 0.5f, 1f);
		
		logger.info("aColor: [" + aColor.print() + "]");
		
		// TODO: remove this!
		DecimalFormat df = new DecimalFormat("###");
		String formatted = df.format(12.456345); 

		logger.info("[" + formatted + "]");
	}

}
