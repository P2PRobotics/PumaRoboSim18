package org.pumatech.physics;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

// Defines a circle as a point and a radius
public class Circle extends Body {
	private Vec2 pos;
	private double radius;

	public Circle(Vec2 pos, double radius, Material mat) {
		super(mat);
		this.pos = pos;
		this.radius = radius;
	}

	public void draw(Graphics2D g) {
		// Draws an ellipse2D (double precision drawing) in the color of circle material
		g.setColor(mat.color());
		g.draw(new Ellipse2D.Double(pos.x - radius, pos.y - radius, 2 * radius, 2 * radius));
	}

	public void moveBy(Vec2 v) {
		pos.add(v);
	}

	public double area() {
		return Math.PI * radius * radius;
	}

	public Vec2 centerPoint() {
		return pos;
	}

	public Vec2 getPos() {
		return pos;
	}
	
	public double direction() {
		return 0; // TODO actual rotation 
	}
	
	public double getRadius() {
		return radius;
	}
	
	public boolean containsPoint(Vec2 p) {
		return pos.distanceSquared(p) < radius * radius;
	}

	public boolean isColliding(Body b) {
		// TODO check collisions with Polygons
		if (b instanceof Circle) {
			Circle other = (Circle) b;
			return other.pos.distanceSquared(pos) < (other.radius + radius) * (other.radius + radius);
		} else if (b instanceof AABB) {
			return true;
		}
		return false;
	}
}
