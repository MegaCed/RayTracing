package com.raytracer.engine.element;

import java.util.Objects;

/*
 * A data structure that encapsulates the colors used by the pattern.
 */
public class Pattern {

	private Color a;
	private Color b;
	
	/*
	 * Constructor.
	 */
	public Pattern(Color a, Color b) {
		this.a = a;
		this.b = b;
	}

	public Color getA() {
		return a;
	}

	public Color getB() {
		return b;
	}

	/*
	 * Prints this Pattern,
	 */
	@Override
	public String toString() {
		String result = "PAttern (A = " + a + ", B = " + b + ")";
		
		return result;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(a, b);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pattern other = (Pattern) obj;
		return Objects.equals(a, other.a) && Objects.equals(b, other.b);
	}
	
}
