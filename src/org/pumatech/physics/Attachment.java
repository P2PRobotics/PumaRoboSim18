package org.pumatech.physics;

public class Attachment {
	
	private Body b;
	private Vec2 point;
	
	public Attachment(Body b, Vec2 point) {
		this.b = b;
		this.point = point;
	}
	
	public Body getBody() {
		return b;
	}
	
	public Vec2 getPoint() {
		return b.centerPoint().added(point.rotated(b.direction()));
	}
	
}
