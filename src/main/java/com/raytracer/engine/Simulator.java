package com.raytracer.engine;

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
		Tuple position = TupleOperation.add(projectile.getPosition(), projectile.getVelocity());
		
		// Update the projectile's new velocity
		// By adding wind and gravity to its actual velocity
		Tuple movement = TupleOperation.add(environment.getGravity(), environment.getWind());
		Tuple velocity = TupleOperation.add(projectile.getVelocity(), movement);
				
		// Returns the new Projectile
		return new Projectile(position, velocity);
	}

}
