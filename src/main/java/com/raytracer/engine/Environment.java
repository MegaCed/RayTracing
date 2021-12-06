package com.raytracer.engine;

/*
 * An Environment has:
 * - gravity (a vector)
 * - wind (a vector)
 */
public class Environment {
	
	private Tuple gravity;
	private Tuple wind;
	
	/*
	 * Constructor.
	 */
	public Environment(Tuple gravity, Tuple wind) {
		this.gravity = gravity;
		this.wind = wind;
	}

	public Tuple getGravity() {
		return gravity;
	}

	public void setGravity(Tuple gravity) {
		this.gravity = gravity;
	}

	public Tuple getWind() {
		return wind;
	}

	public void setWind(Tuple wind) {
		this.wind = wind;
	}

}
