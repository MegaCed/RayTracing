package com.raytracer;

import com.raytracer.engine.Tuple;

public class RayTracerApp {

	public static void main(String[] args) {
		System.out.println("Starting App...");

		// Simple point
		Tuple tuple1 = new Tuple(1.0f, 1.0f, 1.0f, 1.0f);
		tuple1.print();
		
		// Simple vector
		Tuple tuple2 = new Tuple(1.0f, 1.0f, 1.0f, 0.0f);
		tuple2.print();
		
		System.out.println("Done!");
	}

}
