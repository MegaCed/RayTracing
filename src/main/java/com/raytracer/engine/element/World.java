package com.raytracer.engine.element;

import com.raytracer.engine.Factory;

/*
 * Think of how much work it was to render a single sphere, and then multiply that by dozens of 
 * objects. You begin to see what you gain by having something that will keep track of all of those 
 * things for you.
 */
public class World {
	
	// The World's Light
	PointLight light;
	
	// The World's Objects
	Object[] objects;

	/*
	 * Constructor.
	 */
	public World() {
		// The default Light for this World
		this.light = Factory.pointLight(Factory.point(-10, 10, -10), Factory.color(1, 1, 1));
	}
	
	public PointLight getLight() {
		return light;
	}

	public void setLight(PointLight light) {
		this.light = light;
	}

	public Object[] getObjects() {
		return objects;
	}

	public void setObjects(Object[] objects) {
		this.objects = objects;
	}
	
}
