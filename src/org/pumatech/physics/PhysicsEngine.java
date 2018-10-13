package org.pumatech.physics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.List;

public class PhysicsEngine {
	public static final Vec2 GRAVITY = new Vec2(0, 0); // Acceleration due to gravity

	// Bodies being simulated
	private List<Body> bodies;
	
	public PhysicsEngine() {
		bodies = new LinkedList<Body>();
	}
	
	public PhysicsEngine(List<Body> bodies) {
		this.bodies = bodies;
	}
	
	public void update(double dt) {
		// List of collisions found that need to be resolved this update
		List<Collision> collisions = new LinkedList<Collision>();
		// Update each body and check pairs of bodies for a collision 
		for (int i = 0; i < bodies.size(); i++) {
			Body b = bodies.get(i);
			// Apply gravitational force:
			b.applyForce(GRAVITY.scaled(b.getMass()));
			b.update(dt);
			
			// Check other bodies for a collision
			for (int j = i + 1; j < bodies.size(); j++) {
				if (b.isColliding(bodies.get(j))) {
					collisions.add(new Collision(b, bodies.get(j)));
				}
			}
		}
		
		// Resolve the collisions found
		for (Collision c : collisions) {
				c.resolve();
		}
	}
	
	public void draw(Graphics2D g) {
		// Draw a dot in the center of each body
		g.setColor(Color.GREEN);
		for (Body b : bodies) {
			g.fill(new Ellipse2D.Double(b.centerPoint().x - 1, b.centerPoint().y - 1, 2, 2));
		}
	}
	
	// Returns a body which contains the point coord
	public Body getBodyAtCoord(Vec2 coord) {
		for (Body b : bodies) {
			if (b.containsPoint(coord)) {
				return b;
			}
		}
		return null;
	}
	
	public List<Body> getBodies() {
		return bodies;
	}
	
	public void addBody(Body b) {
		bodies.add(b);
	}
	
	public void addBodies(List<Body> bodies) {
		this.bodies.addAll(bodies);
	}
}
