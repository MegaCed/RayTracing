package com.raytracer.engine.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raytracer.engine.Factory;
import com.raytracer.engine.element.Tuple;
import com.raytracer.engine.misc.Constants;

/*
 * Perform some Operations (comparisons, operations, ...) on Tuples.
 */
public class TupleOperations {
	
	private static Logger logger = LoggerFactory.getLogger(TupleOperations.class);
	
	/*
	 * Addition.
	 * 
	 * Imagine that you have a point and a vector, and you want to know where you would be if you 
	 * followed the vector from that point. The answer comes via adding the two tuples together.
	 * 
	 */
	public static Tuple add(Tuple tuple1, Tuple tuple2) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Adding 2 Tuples...");
		logger.debug("1st Tuple: " + tuple1);
		logger.debug("2nd Tuple: " + tuple2);
		
		// You make a new tuple by adding the corresponding components of each of the operands
		float x = tuple1.getX() + tuple2.getX();
		float y = tuple1.getY() + tuple2.getY();
		float z = tuple1.getZ() + tuple2.getZ();
		
		// Adding a Point to a Point doesn't makes sense (W would be equals to 2!)
		float w = tuple1.getW() + tuple2.getW();
		
		Tuple result = new Tuple(x, y, z, w);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Result of addition = " + result);
		return result;
	}
	
	/*
	 * Subtraction.
	 * 
	 * It come in handy when you need to find the vector that points to your light source.
	 *
	 * Point  - Point  = Vector (w=0)
	 * Point  - Vector = Point (w=1)
	 * Vector - Vector = Vector (w=0)
	 * Vector - Point  = nonsense!
	 */
	public static Tuple sub(Tuple tuple1, Tuple tuple2) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Subtracting 2 Tuples...");
		logger.debug("1st Tuple: " + tuple1);
		logger.debug("2nd Tuple: " + tuple2);
		
		float x = tuple1.getX() - tuple2.getX();
		float y = tuple1.getY() - tuple2.getY();
		float z = tuple1.getZ() - tuple2.getZ();
		
		// Adding a Point to a Point doesn't makes sense (W would be equals to 2!)
		float w = tuple1.getW() - tuple2.getW();
		
		Tuple result = new Tuple(x, y, z, w);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Result of subtraction = " + result);
		return result;
	}
	
	/*
	 * Negating.
	 * 
	 * Sometimes you’ll want to know what the opposite of some vector is. That is to say, given a 
	 * vector that points from a surface toward a light source, what vector points from the light 
	 * source back to the surface?
	 */
	public static Tuple neg(Tuple aTuple) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Negating Tuple: " + aTuple);
		
		// Negate each component of the tuple
		float x = aTuple.getX() * -1;
		float y = aTuple.getY() * -1;
		float z = aTuple.getZ() * -1;
		float w = aTuple.getW() * -1;
		
		Tuple result = new Tuple(x, y, z, w);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Negated Tuple = " + result);
		return result;
	}
	
	/*
	 * Scalar multiplication.
	 * 
	 * Let’s say you have some vector and you want to know what point lies 3.5 times farther in that 
	 * direction (When you’re finding where a ray intersects a sphere).
	 * So you lay that vector end-to-end 3.5 times to see just how far the point is from the start.
	 */
	public static Tuple mul(Tuple aTuple, Float scalar) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Multiply Tuple: " + aTuple + " by " + scalar);
		
		// Multiply each component of the Tuple by the scalar
		float x = aTuple.getX() * scalar;
		float y = aTuple.getY() * scalar;
		float z = aTuple.getZ() * scalar;
		float w = aTuple.getW() * scalar;
		
		Tuple result = new Tuple(x, y, z, w);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Result of multiplication = " + result);
		return result;
	}
	
	/*
	 * Division.
	 * 
	 * You can always implement division with multiplication, but sometimes it’s simpler to describe 
	 * an operation as division.
	 */
	public static Tuple div(Tuple aTuple, Float scalar) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Dividing Tuple: " + aTuple + " by " + scalar);
		
		// Divide each component of the tuple by the scalar
		float x = aTuple.getX() / scalar;
		float y = aTuple.getY() / scalar;
		float z = aTuple.getZ() / scalar;
		float w = aTuple.getW() / scalar;
		
		Tuple result = new Tuple(x, y, z, w);
				
		logger.debug(Constants.SEPARATOR_RESULT + "Result of division = " + result);
		return result;
	}
	
	/*
	 * Magnitude.
	 * 
	 * The distance represented by a vector is called its magnitude, or length. It’s how far you 
	 * would travel in a straight line if you were to walk from one end of the vector to the other.
	 */
	public static float magnitude(Tuple aVector) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Retrieving magnitude of vector: " + aVector);
		
		// Pythagoras’ theorem taught us how to compute this, with some squares and a square root
		float x2 = (float) Math.pow(aVector.getX(), 2);
		float y2 = (float) Math.pow(aVector.getY(), 2);
		float z2 = (float) Math.pow(aVector.getZ(), 2);
		float w2 = (float) Math.pow(aVector.getW(), 2);
		
		float result = (float) Math.sqrt(x2+y2+z2+w2);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Magnitude = " + result);
		return result;
	}
	
	/*
	 * Normalization.
	 * 
	 * Normalization is the process of taking an arbitrary vector and converting it into a unit 
	 * vector. It will keep your calculations anchored relative to a common scale (the unit vector).
	 * 
	 * If you were to skip normalizing your ray vectors or your surface normals, your calculations 
	 * would be scaled differently for every ray you cast, and your scenes would look terrible.
	 */
	public static Tuple normalize(Tuple aTuple) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Normalizing Tuple: " + aTuple);
		
		// Retrieve the magnitude of the Tuple
		float magnitude = magnitude(aTuple);
		
		// You normalize a Tuple by dividing each of its components by its magnitude
		float x = aTuple.getX() / magnitude;
		float y = aTuple.getY() / magnitude;
		float z = aTuple.getZ() / magnitude;
		float w = aTuple.getW() / magnitude;
		
		Tuple result = new Tuple(x, y, z, w);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Normalized Tuple = " + result);
		return result;
	}
	
	/*
	 * Dot product.
	 * 
	 * When dealing with vectors, a dot product (also called a scalar product, or inner product) is 
	 * going to turn up when you start intersecting rays with objects, as well as when you compute 
	 * the shading on a surface. The dot product takes two vectors and returns a scalar value.
	 * 
	 * The dot product can feel pretty abstract, but here’s one quick way to internalize it: the 
	 * smaller the dot product, the larger the angle between the vectors. For example, given two 
	 * unit vectors, a dot product of 1 means the vectors are identical, and a dot product of -1 
	 * means they point in opposite directions.
	 */
	public static float dot(Tuple vector1, Tuple vector2) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Dot product of 2 Vectors...");
		logger.debug("1st Vector: " + vector1);
		logger.debug("2nd Vector: " + vector2);
		
		
		// The dot product is computed as the sum of the products of the corresponding components 
		// of each vector
		float x = vector1.getX() * vector2.getX();
		float y = vector1.getY() * vector2.getY();
		float z = vector1.getZ() * vector2.getZ();
		float w = vector1.getW() * vector2.getW();
		
		float result = x + y + z + w;
		
		logger.debug(Constants.SEPARATOR_RESULT + "Dot product = " + result);
		return result;
	}
	
	/*
	 * Cross product.
	 * 
	 * The cross product is another vector operation, but unlike the dot product, it returns another 
	 * vector instead of a scalar.
	 * 
	 * Note that this is specifically testing vectors, not tuples. This is because the 
	 * four-dimensional cross product is significantly more complicated than the three-dimensional 
	 * cross product, and the ray tracer really only needs the three-dimensional version anyway.
	 * 
	 * You’ll use this primarily when working with view transformations, but it will also pop up 
	 * when you start rendering triangles.
	 * 
	 */
	public static Tuple cross(Tuple vector1, Tuple vector2) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Cross product of 2 Vectors...");
		logger.debug("1st Vector: " + vector1);
		logger.debug("2nd Vector: " + vector2);
		
		float x = (vector1.getY() * vector2.getZ()) - (vector1.getZ() * vector2.getY());
		float y = (vector1.getZ() * vector2.getX()) - (vector1.getX() * vector2.getZ());
		float z = (vector1.getX() * vector2.getY()) - (vector1.getY() * vector2.getX());
		
		// You get a new vector that is perpendicular to both of the original vectors.
		Tuple result = Factory.vector(x, y, z);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Cross product = " + result);
		return result;		
	}
	
	/*
	 * Test the equality of 2 Tuples.
	 */
	public static boolean equals(Tuple tuple1, Tuple tuple2) {
		logger.debug(Constants.SEPARATOR_OPERATION + "Comparing 2 Tuples...");
		logger.debug("1st Tuple: " + tuple1);
		logger.debug("2nd Tuple: " + tuple2);
		
		boolean result = tuple1.equals(tuple2);
		
		logger.debug(Constants.SEPARATOR_RESULT + "Tuples equality = " + result);
		return result;	
	}
	
}
