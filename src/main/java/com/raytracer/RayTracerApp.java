package com.raytracer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.Tuple;

public class RayTracerApp {
	
	private static Logger logger = LoggerFactory.getLogger(RayTracerApp.class);

	public static void main(String[] args) {
		System.out.println("Starting App...");

		// Simple point
		Tuple tuple1 = new Tuple(1.0f, 1.0f, 1.0f, 1.0f);
		logger.info(tuple1.toString());
		
		// Simple vector
		Tuple tuple2 = new Tuple(1.0f, 1.0f, 1.0f, 0.0f);
		logger.info(tuple2.toString());
		
		System.out.println("Done!");
	}

}
