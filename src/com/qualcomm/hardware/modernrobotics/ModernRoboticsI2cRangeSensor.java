
package com.qualcomm.hardware.modernrobotics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.List;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.pumatech.physics.Attachment;
import org.pumatech.physics.Body;
import org.pumatech.physics.PhysicsEngine;
import org.pumatech.physics.Polygon;
import org.pumatech.physics.Vec2;

import com.qualcomm.robotcore.hardware.HardwareDevice;

public class ModernRoboticsI2cRangeSensor implements HardwareDevice {

	private Attachment attachment;
	private double direction;
	private PhysicsEngine engine;
	private Vec2 vision;
	
	public ModernRoboticsI2cRangeSensor(Attachment attachment, double direction, PhysicsEngine engine) {
		this.attachment = attachment;
		this.direction = direction;
		this.engine = engine;
	}
	
	public void setDirection(double direction) {
		this.direction = direction;
	}
	
	public double getDistance(DistanceUnit unit) {
		if (vision == null) 
			return 0;
		return vision.length() * 2.54;
	}
	
	public void draw(Graphics2D g) {
		if (vision != null) {
			g.setColor(Color.RED);
			Vec2 point = attachment.getPoint();
			g.draw(new Line2D.Double(point.x, point.y, point.x + vision.x, point.y + vision.y));
		}
	}
	
	public void update(double dt) {
		vision = null;
		List<Body> bodies = engine.getBodies();
		for (Body b : bodies) {
			if (b instanceof Polygon && b != attachment.getBody()) {
				Polygon p = (Polygon) b;
				Vec2 first = p.getVertex(0);
				Vec2 prev = first;
				for (int i = 1; i < p.getNumSides(); i++) {
					Vec2 vertex = p.getVertex(i);
					Vec2 vis = getVision(prev, vertex);
					if (vision == null || vis != null && vis.length() < vision.length())
						vision = vis;
					prev = vertex;
				}
				Vec2 vis = getVision(prev, first);
				if (vision == null || vis != null && vis.length() < vision.length())
					vision = vis;
			}
		}
	}
	
	private Vec2 getVision(Vec2 a, Vec2 b) {
		Vec2 pos = attachment.getPoint();
//		System.out.println(pos + " " + a + " " + b + " " + (direction - attachment.getBody().direction() + Math.PI / 2));
		if (a.y == b.y) {
			double angle = direction - attachment.getBody().direction();
			Vec2 v = new Vec2((a.y - pos.y) * Math.tan(angle), a.y - pos.y);
			//System.out.println("dir " + new Vec2(-angle - Math.PI / 2));
			if (v.dot(new Vec2(-angle - Math.PI / 2)) > 0)
				return v;
		}
		a = new Vec2(a.x, -a.y);
		b = new Vec2(b.x, -b.y);
		pos = new Vec2(pos.x, -pos.y);
		
		if (a.x > b.x) {
			Vec2 moo = b;
			b = a;
			a = moo;
		}

		double m1 = Math.tan(direction - attachment.getBody().direction() + Math.PI / 2);
		double m2 = (b.y - a.y) / (b.x - a.x);
		double x, y;
		
		if (Math.abs(m1 - m2) < 0.01) {
			return null;
		}

		if (m1 > 100 || m1 < -100) {
			x = pos.x;
			y = m2 * (x - a.x) + a.y;

		} else if (a.x == b.x) {
			x = a.x;
			y = m1 * (x - pos.x) + pos.y;
		} else {
			x = (m1 * pos.x - m2 * a.x + a.y - pos.y) / (m1 - m2);
			y = m1 * (x - pos.x) + pos.y;
		
		}
		
//		Vec2 center = new Vec2(attachment.getBody().centerPoint().x, -attachment.getBody().centerPoint().y);
		Vec2 vis = new Vec2(x, y);
		double lengthA = pos.subtracted(vis).lengthSquared();
		double lengthB = new Vec2(attachment.getBody().centerPoint().x, -attachment.getBody().centerPoint().y).subtracted(vis).lengthSquared();
		double lengtha = Math.sqrt(Math.pow(x-pos.x, 2) + Math.pow(y-pos.y, 2));
		double lengthb = Math.sqrt(Math.pow(x - attachment.getBody().centerPoint().x, 2) + Math.pow(y-attachment.getBody().centerPoint().y, 2));
//		System.out.println("a " + lengthA);
//		System.out.println("b " + lengthB);
//		System.out.println(attachment.getBody().centerPoint().subtracted(pos).lengthSquared());
		
		if (a.x <= x && x <= b.x && (a.y <= y && y <= b.y || b.y <= y && y <= a.y) && lengthA<lengthB) {
			//System.out.println(new Vec2(x - pos.x, -y + pos.y));
			return new Vec2(x - pos.x, -y + pos.y);
		}
		return null;
	}
}