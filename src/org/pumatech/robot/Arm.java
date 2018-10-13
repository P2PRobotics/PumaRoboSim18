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
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;

public class Arm extends Polygon implements DcMotor{
	private Attachment attachment;
	private double radius;
	private double direction;
	private double power;
	private Arm arm;
	Material mat;
	Vec2 point;
	Vec2 dirVec;
	Vec2[] vertices;
	
	public Arm(double radius, double direction, Attachment attachment, Vec2[] vertices, Material mat) {
		super(vertices, mat);
		this.radius = radius;
		this.direction = direction;
		this.attachment = attachment;
		this.vertices = vertices;
		this.mat = mat;
		point = attachment.getPoint();
		dirVec = new Vec2(direction + attachment.getBody().direction()).scaled(radius);

	}
	
	public void draw(Graphics2D g) {
		vertices = Arm.getVerts(attachment, radius, direction, false);
		if (power != 0) {
			vertices = Arm.getVerts(attachment, radius, direction, true);
		} 
		Polygon polyArm = new Polygon(vertices, Material.ARM);
		polyArm.draw(g);
		point = attachment.getPoint();
		dirVec = new Vec2(direction + attachment.getBody().direction()).scaled(radius);
	}
	
	public void update(double dt) {
		vertices = Arm.getVerts(attachment, radius, direction, false);
		if (power != 0) {
			vertices = Arm.getVerts(attachment, radius, direction, true);
		} 
		arm = new Arm(radius, direction, attachment, vertices, Material.ARM);
		point = attachment.getPoint();
		dirVec = new Vec2(direction + attachment.getBody().direction()).scaled(radius);

		// Approximates continues movement by slowly moving position and velocity
		vel = attachment.getBody().getVelocity();
		force = attachment.getBody().getForce();
		angularVel = attachment.getBody().getAngularVelocity();
		torque = attachment.getBody().getTorque();
		moveBy(vel.scaled(dt));
		vel.add(force.scaled(attachment.getBody().getMassInv())); // F = ma => vel = fnInt(F/m,T,Ti,Tf)
		force.scale(0); // Set force to 0 so that only new forces applied every update affect body

		// Do above with scalar rotation
		rotateBy(angularVel * dt);
		angularVel += torque * dt;
		torque = 0;
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
	
	
	public Vec2 getVelocity() {
		return attachment.getBody().getVelocity();
	}
	
	public static Vec2[] getVerts(Attachment att, double radius, double direction, boolean extended) {
		Vec2[] verts = null;
		if (extended) {
			Vec2 tempPoint = att.getPoint();
			Vec2 tempDirVec = new Vec2(direction + att.getBody().direction()).scaled(radius);
			Vec2[] tempVerts = { 
					new Vec2((tempPoint.x - tempDirVec.x), (tempPoint.y - tempDirVec.y)), 
					new Vec2((tempPoint.x + tempDirVec.x), (tempPoint.y + tempDirVec.y)), 
					new Vec2((tempPoint.x - tempDirVec.x), (tempPoint.y - tempDirVec.y)),
					new Vec2((tempPoint.x + tempDirVec.x*20), (tempPoint.y + tempDirVec.y*20))};
			verts = tempVerts;
		} else {
			Vec2 tempPoint = att.getPoint();
			Vec2 tempDirVec = new Vec2(direction + att.getBody().direction()).scaled(radius);
			Vec2[] tempVerts = { 
					new Vec2((tempPoint.x - tempDirVec.x), (tempPoint.y - tempDirVec.y)), 
					new Vec2((tempPoint.x + tempDirVec.x), (tempPoint.y + tempDirVec.y)), 
					new Vec2((tempPoint.x - tempDirVec.x), (tempPoint.y - tempDirVec.y)),
					new Vec2((tempPoint.x + tempDirVec.x), (tempPoint.y + tempDirVec.y))};
			verts = tempVerts;
		}	

		return verts;
	}

	@Override
	public Direction getDirection() {
		if (power > 0) {
			return DcMotorSimple.Direction.FORWARD;
		}
		return DcMotorSimple.Direction.REVERSE;
	}
	
//	public Vec2 centerPoint() {
//		return attachment.getPoint();
//	}
	
}
