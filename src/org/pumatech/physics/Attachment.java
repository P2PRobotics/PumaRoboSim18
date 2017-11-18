package org.pumatech.physics;

// Attachment links to a certain point on a body.
public class Attachment {
	private Body b; // Body that point attaches to
	private Vec2 point; // Point relative to center of body
	
	public Attachment(Body b, Vec2 point) {
		this.b = b;
		this.point = point;
	}
	
	public Body getBody() {
		return b;
	}
	
	// This is the magic of attachment. The point updates when the body moves and rotates
	public Vec2 getPoint() {
		return b.centerPoint().added(point.rotated(b.direction()));
	}
	
}
