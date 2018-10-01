package org.pumatech.robot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;

import org.pumatech.physics.Attachment;
import org.pumatech.physics.Vec2;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Wheel implements DcMotor {

	private Attachment attachment;
	private double radius;
	private double direction;
	private double power;
	
	public Wheel(double radius, double direction, Attachment attachment) {
		this.radius = radius;
		this.direction = direction;
		this.attachment = attachment;
	}
	
	public void draw(Graphics2D g) {
		
		Stroke old = g.getStroke();
		g.setStroke(new BasicStroke(1));
		g.setColor(Color.DARK_GRAY);
		
		Vec2 point = attachment.getPoint();
		Vec2 dirVec = new Vec2(direction + attachment.getBody().direction()).scaled(radius);
		g.draw(new Line2D.Double(point.x - dirVec.x, point.y - dirVec.y, point.x + dirVec.x, point.y + dirVec.y));
		g.setColor(Color.GRAY);
		
		dirVec.scale(.7);
		g.draw(new Line2D.Double(point.x - dirVec.x, point.y - dirVec.y, point.x + dirVec.x, point.y + dirVec.y));
		g.setColor(Color.DARK_GRAY);
		
		dirVec.scale(.2);
		g.draw(new Line2D.Double(point.x - dirVec.x, point.y - dirVec.y, point.x + dirVec.x, point.y + dirVec.y));
		g.setStroke(old);
		if (power != 0) {
			g.setColor(Color.GREEN);
			dirVec = new Vec2(direction + attachment.getBody().direction()).scaled(radius * power);
			g.draw(new Line2D.Double(point.x, point.y, point.x + dirVec.x, point.y + dirVec.y));
		}
	}
	
	public void update(double dt) {
		if (power != 0)
			attachment.getBody().applyImpulse(new Vec2(direction + attachment.getBody().direction()).scaled(power * radius * 2000), attachment.getPoint());
	}

	public Direction getDirection() {
		return null;
	}

	public double getPower() {
		return power;
	}

	public void setDirection(Direction motorDir) {
		if (motorDir == Direction.FORWARD)
			direction = Math.abs(direction);
		else
			direction = -Math.abs(direction);
	}

	public void setPower(double power) {
		this.power = power;
	}
}
