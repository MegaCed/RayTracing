package com.raytracer.engine.element;

/*
 * Data structure encapsulating some precomputed information relating to the intersection.
 */
public class Computations {

	// The points that lie any distance t along the ray.
	private double t;
	
	// The object
	private Object object;
	
	// The point (in world space) where the intersection occurred
	private Tuple point;
	
	// The eye vector (pointing back toward the eye, or camera)
	private Tuple eyeVector;
	
	// The normal vector
	private Tuple normalVector;
	
	// Will be true if the hit occurs inside the object, and false otherwise
	private boolean inside;

	public double getT() {
		return t;
	}

	public void setT(double t) {
		this.t = t;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Tuple getPoint() {
		return point;
	}

	public void setPoint(Tuple point) {
		this.point = point;
	}

	public Tuple getEyeVector() {
		return eyeVector;
	}

	public void setEyeVector(Tuple eyeVector) {
		this.eyeVector = eyeVector;
	}

	public Tuple getNormalVector() {
		return normalVector;
	}

	public void setNormalVector(Tuple normalVector) {
		this.normalVector = normalVector;
	}

	public boolean isInside() {
		return inside;
	}

	public void setInside(boolean inside) {
		this.inside = inside;
	}

	@Override
	public String toString() {
		return "Computations (t=" + t + ", object=" + object + ", point=" + point + ", eyeVector=" + eyeVector
				+ ", normalVector=" + normalVector + ", inside=" + inside + ")";
	}
	
}
