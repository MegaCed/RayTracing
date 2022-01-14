package com.raytracer.engine.misc;

import com.raytracer.engine.element.Color;

public interface Constants {
	
	// Separator used in Matrices
	public static final String MATRIX_SEPARATOR = "|";
	
	// End of line character
	public static final String END_OF_LINE = "\n";
	
	// Space separator
	public static final String SPACE = " ";
	
	// Length of the numbers to be printed
	public static final int MATRIX_NUMBER_LENGTH = 8;
	
	// Precision of the numbers to be printed
	public static final int MATRIX_NUMBER_PRECISION = 2;
	
	// Number formats
	public static final String NUMBER_BIG	= "%8.5f";
	public static final String NUMBER_SMALL	= "%5.2f";
	
	// Separators
	public static final String SEPARATOR_JUNIT		= "---------- ";
	public static final String SEPARATOR_CREATION	= "+++++ ";
	public static final String SEPARATOR_OPERATION	= "<<=== ";
	public static final String SEPARATOR_RESULT		= "===>> ";
	
	// Default Material
	public static final double MATERIAL_AMBIENT = 0.1;
	public static final double MATERIAL_DIFFUSE = 0.9;
	public static final double MATERIAL_SPECULAR = 0.9;
	public static final double MATERIAL_SHININESS = 200;
	
	// Some colors
	public static final Color COLOR_BLACK = new Color(0, 0, 0);
	public static final Color COLOR_WHITE = new Color(1, 1, 1);
	public static final Color COLOR_RED = new Color(1, 0, 0);

}
