package com.qualcomm.robotcore.hardware;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.List;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.pumatech.physics.Attachment;
import org.pumatech.physics.Body;
import org.pumatech.physics.Material;
import org.pumatech.physics.PhysicsEngine;
import org.pumatech.physics.Polygon;
import org.pumatech.physics.Vec2;
import org.pumatech.robot.Arm;

import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;

public class colorSensor extends Polygon implements HardwareDevice
{
	
	private Attachment attachment;
	private double direction;
	private PhysicsEngine engine;
	private Vec2 vision;
	private double radius;
	private double power;

	Material mat;
	Vec2 point;
	Vec2 dirVec;
	Vec2[] vertices;
	
    private colorSensor colorSensor;

	public colorSensor(Attachment attachment, double direction, PhysicsEngine engine, colorSensor colorSensor, Vec2[] vertices, Material mat)
	{
		super(vertices, mat);
		this.attachment = attachment;
		this.direction = direction; 
		this.engine = engine;
		this.colorSensor = colorSensor;
		
		dirVec = new Vec2(direction + attachment.getBody().direction()).scaled(radius);
	}
	
//	public void draw(Graphics2D g)
//	{  
//		vertices = colorSensor.getVerts(attachment, radius, direction, false);
//		if (power != 0) {
//			vertices = colorSensor.getVerts(attachment, radius, direction, true);
//		} 
//		Polygon polyArm = new Polygon(vertices, Material.ARM);
//		polyArm.draw(g);
//		point = attachment.getPoint();
//		dirVec = new Vec2(direction + attachment.getBody().direction()).scaled(radius);
//	}
	
	public void setDirection(double direction)
	{
		this.direction = direction;
	}
	
//	public double getColor()
//	{
//		double angle = this.vision.angle();
//		System.out.println(vision.distance(vision));
//		field.
//		return angle;
//	}
	
	public void draw(Graphics2D g)
	{
		if (vision != null) {
			g.setColor(Color.RED);
			Vec2 point = attachment.getPoint();
			g.draw(new Line2D.Double(point.x, point.y, point.x + vision.x, point.y + vision.y));
		}
	}
	
	public void update(double dt)
	{
		vision = null;
		List<Body> bodies = engine.getBodies();
		for (Body b : bodies) {
			if (b instanceof Polygon && b != attachment.getBody())
			{
				Polygon p = (Polygon) b;
				Vec2 first = p.getVertex(0);
				Vec2 prev = first;
				for (int i = 1; i < p.getNumSides(); i++)
				{
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
	
	private Vec2 getVision(Vec2 a, Vec2 b)
	{
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
		
		if (a.x > b.x)
		{
			Vec2 moo = b;
			b = a;
			a = moo;
		}

		double m1 = Math.tan(direction - attachment.getBody().direction() + Math.PI / 2);
		double m2 = (b.y - a.y) / (b.x - a.x);
		double x, y;
		
		if (Math.abs(m1 - m2) < 0.01)
		{
			return null;
		}

		if (m1 > 100 || m1 < -100)
		{
			x = pos.x;
			y = m2 * (x - a.x) + a.y;

		}
		else if (a.x == b.x)
		{
			x = a.x;
			y = m1 * (x - pos.x) + pos.y;
		}
		else
		{
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
		
		if (a.x <= x && x <= b.x && (a.y <= y && y <= b.y || b.y <= y && y <= a.y) && lengthA<lengthB)
		{
			//System.out.println(new Vec2(x - pos.x, -y + pos.y));
			return new Vec2(x - pos.x, -y + pos.y);
		}
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
		return colorSensor;
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


	public void getColor() {
		// TODO Auto-generated method stub
		
	}

}
