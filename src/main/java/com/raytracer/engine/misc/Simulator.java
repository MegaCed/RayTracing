package com.raytracer.engine.misc;

import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.operation.TupleOperations;

/*
 * Try playing with this little program, firing virtual Projectiles and seeing how far they go.
 */
public class Simulator {
	
	/*
	 * Returns a new Projectile, representing the given Projectile after one unit of time has 
	 * passed. (The actual units here don’t really matter—maybe they’re seconds, or milliseconds. 
	 * Whatever. We’ll just call them “ticks”).
	 */
	public static Projectile tick(Environment environment, Projectile projectile) {
		// Update the projectile's new position
		// By adding its velocity to its position
		Tuple position = TupleOperations.add(projectile.getPosition(), projectile.getVelocity());
		
		// Update the projectile's new velocity
		// By adding wind and gravity to its actual velocity
		Tuple movement = TupleOperations.add(environment.getGravity(), environment.getWind());
		Tuple velocity = TupleOperations.add(projectile.getVelocity(), movement);
				
		// Returns the new Projectile
		return new Projectile(position, velocity);
	}

}
