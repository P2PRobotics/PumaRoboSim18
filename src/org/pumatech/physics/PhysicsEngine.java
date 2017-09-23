package org.pumatech.physics;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.List;

public class PhysicsEngine {

	private List<Body> bodies;
	
	public PhysicsEngine(List<Body> bodies) {
		this.bodies = bodies;
	}
	
	public void update(double dt) {
		List<Collision> collisions = new LinkedList<Collision>();
		for (int i = 0; i < bodies.size(); i++) {
			Body b = bodies.get(i);
			// Apply gravitational force:
			// b.applyForce(new Vec2(0, 1).scaled(b.getMass()));
			b.update(dt);
			for (int j = i + 1; j < bodies.size(); j++) {
				if (b.isColliding(bodies.get(j))) {
					collisions.add(new Collision(b, bodies.get(j)));
				}
			}
		}
		
		for (Collision c : collisions) {
			c.resolve();
		}
	}
	
	public void draw(Graphics2D g) {
		for (Body b : bodies) {
			b.draw(g);
			g.fill(new Ellipse2D.Double(b.centerPoint().x - 1.5, b.centerPoint().y - 1.5, 3, 3));
		}
	}
	
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
}
