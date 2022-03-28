package com.raytracer;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Camera;
import com.raytracer.engine.element.Canvas;
import com.raytracer.engine.element.Color;
import com.raytracer.engine.element.Intersection;
import com.raytracer.engine.element.Intersections;
import com.raytracer.engine.element.Material;
import com.raytracer.engine.element.Matrix;
import com.raytracer.engine.element.Plane;
import com.raytracer.engine.element.PointLight;
import com.raytracer.engine.element.PortablePixmap;
import com.raytracer.engine.element.Ray;
import com.raytracer.engine.element.Sphere;
import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.element.World;
import com.raytracer.engine.misc.Constants;
import com.raytracer.engine.misc.Environment;
import com.raytracer.engine.misc.Projectile;
import com.raytracer.engine.misc.Simulator;
import com.raytracer.engine.operation.ColorOperations;
import com.raytracer.engine.operation.MatrixOperations;
import com.raytracer.engine.operation.RayOperations;
import com.raytracer.engine.operation.SphereOperations;
import com.raytracer.engine.operation.TupleOperations;
import com.raytracer.engine.operation.WorldOperations;

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
		Instant start = Instant.now();
		logger.info("Computing...");

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
		//sphereShadow();
		
		// 3D sphere
		//sphere3d();
		//sphere3dMultiThreaded();
		
		// Test multi-threading
		//multiThreading();
		
		// Complete scene
		//basicScene();
		
		// More complete scene
		smallScene();
		
		logger.info("Done!");
		
		Instant end = Instant.now();
		Duration timeElapsed = Duration.between(start, end);
		logger.info("Elapsed time: " + timeElapsed.getSeconds() + " second(s) (" + timeElapsed.getSeconds() / 60 + " minute(s))");
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
		Tuple gravity = Factory.vector(0, -0.1, 0);
		Tuple wind = Factory.vector(-0.01, 0, 0);
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
		Tuple velocity = Factory.vector(1, 1.8, 0);
		velocity = TupleOperations.normalize(velocity);
		velocity.setX(velocity.getX() * 11.25);
		velocity.setY(velocity.getY() * 11.25);
		Projectile aProjectile = new Projectile(start, velocity);
		
		// - Gravity -0.1 unit/tick
		// - And wind is -0.01 unit/tick.
		Tuple gravity = Factory.vector(0, -0.1, 0);
		Tuple wind = Factory.vector(-0.01, 0, 0);
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
		double[][] values = {
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
		double[][] values = {
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
			Matrix rotation = Factory.zRotationMatrix((hour * (Math.PI / 6)));
			
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
		//aSphere.setTransform(Factory.scalingMatrix(1, 0.5, 1));
		// Shrink it along the x axis
		//aSphere.setTransform(Factory.scalingMatrix(0.5, 1, 1));
		// Shrink it, and rotate it!
		//aSphere.setTransform(MatrixOperations.mul(Factory.xRotationMatrix(Math.PI / 4), Factory.scalingMatrix(0.5, 1, 1)));
		// Shrink it, and skew it!
		//aSphere.setTransform(MatrixOperations.mul(Factory.shearingMatrix(1, 0, 0, 0, 0, 0), Factory.scalingMatrix(0.5, 1, 1)));
		
		// Decide how large you want your canvas to be (in pixels).
		// A canvas 100 pixels on a side is probably good for starting with.
		// (Larger images will take exponentially longer to render)
		int canvasPixels = 100;
		Canvas aCanvas = Factory.canvas(canvasPixels, canvasPixels);

		// Figure out how far your ray’s origin is from the sphere. Also, decide where your wall 
		// will be.
		// Moving the ray origin closer to the sphere will make the sphere in the drawing larger. 
		// Moving it farther away will make the sphere smaller. Moving the wall will do similarly
		double wallZ = 10;
		double wallSize = 7;
		
		// Once you know how many pixels fit along each side of the wall, you can divide the wall 
		// size by the number of pixels to get the size of a single pixel (in world space units)
		double pixelSize = wallSize / canvasPixels;
		
		// Then, assume you’re looking directly at the center of the sphere. Half of the wall will 
		// be to the left of that, and half to the right.
		// Since the wall is centered around the origin (because the sphere is at the origin), this 
		// means that this half variable describes the minimum and maximum x and y coordinates of 
		// your wall
		double half = wallSize / 2;
		
		// Start the ray here, everything will be computed from this point
		Tuple rayOrigin = Factory.point(0, 0, -5);
		
		// For each row of pixels in the canvas
		for (int y = 0; y < canvasPixels; y++) {
			logger.info("Computing line " + y + " / " + canvasPixels);
			
			// Compute the world y coordinate (top = +half, bottom = -half)
			// In world	space, the y coordinate increases as you go up, and decreases as you go 
			// down. But on the canvas, the top is at y = 0, and y increases as you go down. Thus, 
			// to render the circle correctly, you have to flip the y coordinate, which is 
			// accomplished by subtracting it from its maximum value (the top of the wall, or half)
			double worldY = half - pixelSize * y;
			
			// For each pixel in the row
			for (int x = 0; x < canvasPixels; x++) {
				// Compute the world x coordinate (left = -half, right = half)
				double worldX = half - pixelSize * x;
				
				// Describe the point on the wall that the ray will target
				Tuple position = Factory.point(worldX, worldY, wallZ);
				
				// You cast each ray from some starting point toward some point on the wall that 
				// corresponds to a position on your canvas. If the ray intersects the sphere, a 
				// shadow is cast, which you’ll mark with a colored pixel
				Tuple rayDirection = TupleOperations.normalize(TupleOperations.sub(position, rayOrigin));
				Ray aRay = Factory.ray(rayOrigin, rayDirection);
				Intersections intersections = Factory.intersections(aSphere.getOperations().intersects(aSphere, aRay));
				
				if (SphereOperations.hit(intersections) != null) {
					aCanvas.writePixel(x, y, red);
				}
			}
		}
		
		// Save canvas to a file
		PortablePixmap ppmFile = aCanvas.canvasToPPM();
		ppmFile.writeToFile(PATH_LAPTOP + "shadow.ppm");
	}

	/*
	 * Chapter 6: Take a look at the program you wrote at the end of the previous chapter, the one 
	 * where you drew the silhouette of a sphere on a canvas. It’s time to revisit that and turn the 
	 * silhouette into a full-on 3D rendering.
	 */
	// TODO: DISABLE LOGGING BEFORE RUNNING THIS!!
	private static void sphere3d() {
		Sphere aSphere = Factory.sphere();
		
		// Assign a material to your sphere
		Material aMaterial = Factory.material();
		aMaterial.setColor(Factory.color(1, 0.2, 1));
		aSphere.setMaterial(aMaterial);
		
		// Add a light source. Here’s one possible configuration, with a white light behind, above 
		// and to the left of the eye:
		Tuple lightPosition = Factory.point(-10, 10, -10);
		Color lightColor = Factory.color(1, 1, 1);
		PointLight light = Factory.pointLight(lightPosition, lightColor);
		
		int canvasPixels = 100;
		Canvas aCanvas = Factory.canvas(canvasPixels, canvasPixels);

		double wallZ = 10;
		double wallSize = 7;
		double pixelSize = wallSize / canvasPixels;
		double half = wallSize / 2;
		
		Tuple rayOrigin = Factory.point(0, 0, -5);
		
		for (int y = 0; y < canvasPixels; y++) {
			double worldY = half - pixelSize * y;
				
			for (int x = 0; x < canvasPixels; x++) {
				double worldX = half - pixelSize * x;
				
				Tuple position = Factory.point(worldX, worldY, wallZ);
				
				Tuple rayDirection = TupleOperations.normalize(TupleOperations.sub(position, rayOrigin));
				Ray aRay = Factory.ray(rayOrigin, rayDirection);
				Intersections intersections = Factory.intersections(aSphere.getOperations().intersects(aSphere, aRay));
				
				Intersection hit = SphereOperations.hit(intersections);
				
				if (hit != null) {
					// In the loop where you cast your rays, make sure you’re normalizing the ray 
					// direction. It didn’t matter before, but it does now! Also, once you’ve got an 
					// intersection, find the normal vector at the hit (the closest intersection), 
					// and calculate the eye vector
					Tuple point = RayOperations.position(aRay, hit.getT());
					Sphere theHit = (Sphere)hit.getObject();
					Tuple normal = aSphere.getOperations().normalAt(theHit, point);
					Tuple eye = TupleOperations.neg(aRay.getDirection());
					
					// Finally, calculate the color with your lighting() function before applying it 
					// to the canvas
					Color theColor = ColorOperations.lithting(hit.getObject().getMaterial(), hit.getObject(), light, point, eye, normal, false);
					
					aCanvas.writePixel(x, y, theColor);
				}
			}
		}
		
		// Save canvas to a file
		PortablePixmap ppmFile = aCanvas.canvasToPPM();
		ppmFile.writeToFile(PATH_LAPTOP + "sphere.ppm");
		
		// From there, experiment with different transformations of the sphere. Squash it, rotate 
		// it, scale it. Try different colors, and different material parameters. What happens when 
		// you increase the ambient value? What if the diffuse and specular are both low? What 
		// happens when you move the light source, or change its intensity?
	}
	
	/*
	 * Multi-thread version of the same method.
	 */
	// TODO: DISABLE LOGGING BEFORE RUNNING THIS!!
	private static void sphere3dMultiThreaded() {
		Sphere aSphere = Factory.sphere();
		
		Material aMaterial = Factory.material();
		aMaterial.setColor(Factory.color(1, 0.2, 1));
		aSphere.setMaterial(aMaterial);
		
		Tuple lightPosition = Factory.point(10, 10, -10);
		Color lightColor = Factory.color(1, 1, 1);
		PointLight light = Factory.pointLight(lightPosition, lightColor);
		
		int canvasPixels = 150;
		Canvas aCanvas = Factory.canvas(canvasPixels, canvasPixels);

		double wallZ = 10;
		double wallSize = 7;
		double pixelSize = wallSize / canvasPixels;
		double half = wallSize / 2;
		
		Tuple rayOrigin = Factory.point(0, 0, -5);
		
		// Create a Thread pool
		int maxThreadsCount = 4;
		ExecutorService threadPool = Executors.newFixedThreadPool(maxThreadsCount);
		Collection<Future<?>> futures = new LinkedList<Future<?>>();
		
		for (int y = 0; y < canvasPixels; y++) {
			final int yFinal = y;
			
			// Launch the Threads
			futures.add(threadPool.submit(() -> {
			    logic(canvasPixels, half, pixelSize, yFinal, wallZ, rayOrigin, aSphere, aCanvas, light);
			}));
		}
		
		// Wait for Threads end
		for (Future<?> future:futures) {
		    try {
				future.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		threadPool.shutdownNow();
		
		// Save canvas to a file
		PortablePixmap ppmFile = aCanvas.canvasToPPM();
		ppmFile.writeToFile(PATH_LAPTOP + "sphere_multi_threaded.ppm");
	}
	
	/*
	 * Thread's payload.
	 */
	private static void logic(int canvasPixels, double half, double pixelSize, int y, double wallZ, Tuple rayOrigin, Sphere aSphere, Canvas aCanvas, PointLight light) {
		logger.info("Processing line " + y + " / " + canvasPixels);
		
		double worldY = half - pixelSize * y;
		
		for (int x = 0; x < canvasPixels; x++) {
			double worldX = half - pixelSize * x;
			
			Tuple position = Factory.point(worldX, worldY, wallZ);
			
			Tuple rayDirection = TupleOperations.normalize(TupleOperations.sub(position, rayOrigin));
			Ray aRay = Factory.ray(rayOrigin, rayDirection);
			Intersections intersections = Factory.intersections(aSphere.getOperations().intersects(aSphere, aRay));
			
			Intersection hit = SphereOperations.hit(intersections);
			
			if (hit != null) {
				// In the loop where you cast your rays, make sure you’re normalizing the ray 
				// direction. It didn’t matter before, but it does now! Also, once you’ve got an 
				// intersection, find the normal vector at the hit (the closest intersection), 
				// and calculate the eye vector
				Tuple point = RayOperations.position(aRay, hit.getT());
				Sphere theHit = (Sphere)hit.getObject();
				Tuple normal = theHit.getOperations().normalAt(theHit, point);
				Tuple eye = TupleOperations.neg(aRay.getDirection());
				
				// Finally, calculate the color with your lighting() function before applying it 
				// to the canvas
				Color theColor = ColorOperations.lithting(hit.getObject().getMaterial(), hit.getObject(), light, point, eye, normal, false);
				
				synchronized(RayTracerApp.class) {
					aCanvas.writePixel(x, y, theColor);
				}
			}
		}
	}
	
	/*
	 * Test for multi-threading.
	 */
	private static void multiThreading() {
		int maxThreadsCount = 4;
		ExecutorService threadPool = Executors.newFixedThreadPool(maxThreadsCount);
		Collection<Future<?>> futures = new LinkedList<Future<?>>();
		
		// Create tasks
		for (int cpt = 0; cpt < 10; cpt++) {
			// Add them to the pool
			futures.add(threadPool.submit(() -> {
				String name = Thread.currentThread().getName();
			    System.out.println("Thread: " + name +" - START");
			    
			    try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			    
			    System.out.println("Thread: " + name +" - FINISH");
			}));
		}

		// Wait for completion
		for (Future<?> future:futures) {
		    try {
				future.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		threadPool.shutdownNow();
	}
	
	/*
	 * Chapter 7: Look back at the program you wrote at the end of the previous chapter. 
	 * It’s time to clean that up, taking advantage of the world and camera that you’ve just written 
	 * and adding a few more spheres to make the scene more interesting.
	 */
	private static void basicScene() {
		// The floor is an extremely flattened sphere with a matte texture
		Sphere floor = Factory.sphere();
		floor.setTransform(Factory.scalingMatrix(10, 0.01, 10));
		Material floorMaterial = Factory.material();
		floorMaterial.setColor(Factory.color(1, 0.9, 0.9));
		floorMaterial.setSpecular(0);
		floor.setMaterial(floorMaterial);
		
		// The wall on the left has the same scale and color as the floor, but is also rotated and 
		// translated into place
		Sphere leftWall = Factory.sphere();
		// Note the order in which the transformations are multiplied: the wall needs to be scaled, 
		// then rotated in x, then rotated in y, and lastly translated, so the transformations are 
		// multiplied in the reverse order!
		Matrix leftTransformation = MatrixOperations.mul(Factory.translationMatrix(0, 0, 5), Factory.yRotationMatrix(-Math.PI / 4));
		leftTransformation = MatrixOperations.mul(leftTransformation, Factory.xRotationMatrix(Math.PI / 2));
		leftTransformation = MatrixOperations.mul(leftTransformation, Factory.scalingMatrix(10, 0.01, 10));
		leftWall.setTransform(leftTransformation);
		leftWall.setMaterial(floorMaterial);
		
		// The wall on the right is identical to the left wall, but is rotated the opposite 
		// direction in y
		Sphere rightWall = Factory.sphere();
		Matrix rightTransformation = MatrixOperations.mul(Factory.translationMatrix(0, 0, 5), Factory.yRotationMatrix(Math.PI / 4));
		rightTransformation = MatrixOperations.mul(rightTransformation, Factory.xRotationMatrix(Math.PI / 2));
		rightTransformation = MatrixOperations.mul(rightTransformation, Factory.scalingMatrix(10, 0.01, 10));
		rightWall.setTransform(rightTransformation);
		rightWall.setMaterial(floorMaterial);
		
		// The large sphere in the middle is a unit sphere, translated upward slightly and colored 
		// green
		Sphere middle = Factory.sphere();
		middle.setTransform(Factory.translationMatrix(-0.5, 1, 0.5));
		Material middleMaterial = Factory.material();
		middleMaterial.setColor(Factory.color(0.1, 1, 0.5));
		middleMaterial.setDiffuse(0.7);
		middleMaterial.setSpecular(0.3);
		middle.setMaterial(middleMaterial);
		
		// The smaller green sphere on the right is scaled in half
		Sphere right = Factory.sphere();
		right.setTransform(MatrixOperations.mul(Factory.translationMatrix(1.5, 0.5, -0.5), Factory.scalingMatrix(0.5, 0.5, 0.5)));
		Material rightMaterial = Factory.material();
		rightMaterial.setColor(Factory.color(0.5, 1, 0.1));
		rightMaterial.setDiffuse(0.7);
		rightMaterial.setSpecular(0.3);
		right.setMaterial(rightMaterial);
		
		// The smallest sphere is scaled by a third, before being translated
		Sphere left = Factory.sphere();
		left.setTransform(MatrixOperations.mul(Factory.translationMatrix(-1.5, 0.33, -0.75), Factory.scalingMatrix(0.33, 0.33, 0.33)));
		Material leftMaterial = Factory.material();
		leftMaterial.setColor(Factory.color(1, 0.8, 0.1));
		leftMaterial.setDiffuse(0.7);
		leftMaterial.setSpecular(0.3);
		left.setMaterial(leftMaterial);
		
		// The light source is white, shining from above and to the left
		PointLight light = Factory.pointLight(Factory.point(-10, 10, -10), Factory.color(1, 1, 1));
		World theWorld = Factory.world();
		theWorld.setLight(light);
		
		// Add the objects to the world
		theWorld.addObject(floor);
		theWorld.addObject(leftWall);
		theWorld.addObject(rightWall);
		theWorld.addObject(middle);
		theWorld.addObject(right);
		theWorld.addObject(left);
		
		// And the camera is configured like so
		Camera theCamera = Factory.camera(100, 50, Math.PI / 3);
		theCamera.setTransform(WorldOperations.viewTransform(Factory.point(0, 1.5, -5), Factory.point(0, 1, 0), Factory.vector(0, 1, 0)));
		
		// Render the result to a canvas
		Canvas theCanvas = WorldOperations.render(theCamera, theWorld);
		PortablePixmap ppmFile = theCanvas.canvasToPPM();
		ppmFile.writeToFile(PATH_LAPTOP + "basicScene.ppm");
	}
	
	/*
	 * Chapter 8: Write a small scene consisting of a single plane as the floor, and a sphere or two 
	 * sitting atop it.
	 */
	private static void smallScene() {
		// The floor
		Plane floor = Factory.plane();
		Material floorMaterial = Factory.material();
		floorMaterial.setColor(Factory.color(1, 0.9, 0.9));
		floorMaterial.setSpecular(0);
		floor.setMaterial(floorMaterial);
		
		// The large sphere in the middle
		Sphere ball = Factory.sphere();
		ball.setTransform(Factory.translationMatrix(-0.5, 1, 0.5));
		Material middleMaterial = Factory.material();
		middleMaterial.setColor(Factory.color(0.1, 1, 0.5));
		middleMaterial.setDiffuse(0.7);
		middleMaterial.setSpecular(0.3);
		ball.setMaterial(middleMaterial);
		
		// The light source
		PointLight light = Factory.pointLight(Factory.point(-10, 10, -10), Constants.COLOR_WHITE);
//		PointLight light = Factory.pointLight(Factory.point(-5, 5, -5), Constants.COLOR_WHITE);
		World theWorld = Factory.world();
		theWorld.setLight(light);
		
		// Add a wall as a backdrop by rotating it π⁄2 radians around the x axis and translating it 
		// a few units in the positive z direction
		Plane wall = Factory.plane();
		Material wallMaterial = Factory.material();
		wallMaterial.setColor(Constants.COLOR_RED);
		wallMaterial.setDiffuse(0.7);
		wallMaterial.setSpecular(0.3);
		wall.setMaterial(wallMaterial);
		wall.setTransform(MatrixOperations.mul(Factory.translationMatrix(0, 0, 50), Factory.xRotationMatrix(Math.PI/2)));
		
		// Make a hexagonal-shaped room by carefully rotating and translating planes, and then 
		// position the camera from above, looking down, so you can see the geometry in action
//		Plane wall2 = Factory.plane();
//		wall2.setMaterial(wallMaterial);
//		Matrix wallMatrix = MatrixOperations.mul(Factory.translationMatrix(0, 0, 20), Factory.xRotationMatrix(Math.PI/2));
//		wallMatrix = MatrixOperations.mul(wallMatrix, Factory.zRotationMatrix(Math.PI/3));
//		wall2.setTransform(wallMatrix);
		
//		Plane wall3 = Factory.plane();
//		wall3.setMaterial(wallMaterial);
//		wallMatrix = MatrixOperations.mul(wallMatrix, Factory.zRotationMatrix(Math.PI/3));
//		wall3.setTransform(wallMatrix);
		
//		Plane wall4 = Factory.plane();
//		wall4.setMaterial(wallMaterial);
//		Matrix wallMatrix2 = MatrixOperations.mul(Factory.translationMatrix(0, 0, -6), Factory.xRotationMatrix(Math.PI/2));
//		wall4.setTransform(wallMatrix2);
		
		// Add a ceiling by translating another plane vertically, in y. (Be careful to position your 
		// light source below the ceiling!)
//		Plane ceiling = Factory.plane();
//		Material ceilingMaterial = Factory.material();
//		ceilingMaterial.setColor(Constants.COLOR_GREEN);
//		ceilingMaterial.setDiffuse(0.7);
//		ceilingMaterial.setSpecular(0.3);
//		ceiling.setMaterial(ceilingMaterial);
//		ceiling.setTransform(Factory.translationMatrix(0, 15, 0));
		
		// Instead of displaying an entire sphere atop the plane, translate the sphere so it is 
		// partially embedded in the plane.
		ball.setTransform(Factory.translationMatrix(0, -0.2, 0));
		
		// Add the objects to the world
		theWorld.addObject(floor);
		theWorld.addObject(ball);
		theWorld.addObject(wall);
//		theWorld.addObject(ceiling);
//		theWorld.addObject(wall2);
//		theWorld.addObject(wall3);
//		theWorld.addObject(wall4);
		
		// The camera
		Camera theCamera = Factory.camera(50, 50, Math.PI / 3);
		theCamera.setTransform(WorldOperations.viewTransform(Factory.point(0, 1.5, -5), Factory.point(0, 1, 0), Factory.vector(0, 1, 0)));
//		theCamera.setTransform(WorldOperations.viewTransform(Factory.point(0, 8, -2), Factory.point(0, 1, 0.1), Factory.vector(0, 1, 0)));
		
		// Render the result to a canvas
		Canvas theCanvas = WorldOperations.render(theCamera, theWorld);
		PortablePixmap ppmFile = theCanvas.canvasToPPM();
		ppmFile.writeToFile(PATH_LAPTOP + "smallScene.ppm");
	}
	
}
