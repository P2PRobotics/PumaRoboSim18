package org.pumatech.physics;

import java.awt.Color;

// Material of a body. Contains info about friction, restitution, color, density
public enum Material {
	ROBOT (5, 1, .05, new Color(130, 82, 1)),
	RUBBER (10, 1, .1, new Color(11, 38, 81)),
	WOOD1(5, 1, .05, Color.RED),
	WOOD2(5, 1, .05, Color.BLUE),
	SILVER(5, 1, 0.02, Color.GRAY),
	ARM(5, 1, 1, Color.GRAY),
	IMMOVEABLE (0, .2, .6, Color.DARK_GRAY);

	private double density;
	private double restitution; // Constant for "bounciness" (0 = inelastic 1 = elastic)
	private double staticFriction;
	private Color color;
	
	Material(double density, double restitution, double staticFriction, Color color) {
		this.density = density;
		this.restitution = restitution;
		this.staticFriction = staticFriction;
		this.color = color;
	}
	
	public double density() {
		return density;
	}
	
	public double restitution() {
		return restitution;
	}
	
	public double staticFriction() {
		return staticFriction;
	}
	
	public double dynamicFriction() {
		return 0.1 * staticFriction();
	}
	
	public Color color() {
		return color;
	}
	
	public static double combinedFriction(double f1, double f2) {
		return f1 + f2 / 2;
	}
}
