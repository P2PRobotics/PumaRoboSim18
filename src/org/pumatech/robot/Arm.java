package org.pumatech.robot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;

import org.pumatech.physics.Attachment;
import org.pumatech.physics.Body;
import org.pumatech.physics.Material;
import org.pumatech.physics.Polygon;
import org.pumatech.physics.Vec2;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;

public class Arm implements DcMotor{
	private Attachment attachment;
	private double radius;
	private double direction;
	private double power;
	private Body arm;
	Vec2 point;
	Vec2 dirVec;
	
	public Arm(double radius, double direction, Attachment attachment) {
		this.radius = radius;
		this.direction = direction;
		this.attachment = attachment;
		point = attachment.getPoint();
		dirVec = new Vec2(direction + attachment.getBody().direction()).scaled(radius);

	}
	
	public void draw(Graphics2D g) {
		
		Vec2[] vertices = { 
				new Vec2((point.x - dirVec.x), (point.y - dirVec.y)), 
				new Vec2((point.x + dirVec.x), (point.y + dirVec.y)), 
				new Vec2((point.x - dirVec.x), (point.y - dirVec.y)),
				new Vec2((point.x + dirVec.x), (point.y + dirVec.y))};
		arm = new Polygon(vertices, Material.SILVER);
		arm.draw(g);
		point = attachment.getPoint();
		dirVec = new Vec2(direction + attachment.getBody().direction()).scaled(radius);
		if (power != 0) {
			Vec2[] vertices1 = { 
					new Vec2((point.x - dirVec.x), (point.y - dirVec.y)), 
					new Vec2((point.x + dirVec.x), (point.y + dirVec.y)), 
					new Vec2((point.x - dirVec.x), (point.y - dirVec.y)),
					new Vec2((point.x + dirVec.x)+20, (point.y + dirVec.y)+5)};
			arm = new Polygon(vertices1, Material.SILVER);
			arm.draw(g);
		}
	}
	
	public void update(double dt) {
		if (power != 0) {
			arm.applyForce(arm.getVelocity().scaled(-100*dt));
			arm.applyTorque(arm.getAngularVelocity() * -175 * dt);
		}
			//attachment.getBody().applyImpulse(new Vec2(direction + attachment.getBody().direction()).scaled(power * radius * 2000), attachment.getPoint());
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
	
	public Body returnBody() {
		return arm;
	}
}
