package org.pumatech.robot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.List;

import org.pumatech.physics.Body;
import org.pumatech.physics.Material;
import org.pumatech.physics.PhysicsEngine;
import org.pumatech.physics.Polygon;
import org.pumatech.physics.Vec2;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {

	private Wheel w1, w2, w3, w4, w5, w6;
	private Arm arm1;
	private Body chassis;
	private HardwareMap hardwareMap;
	private BNO055IMU imu;
	private ModernRoboticsI2cRangeSensor rangeSensor;
	private USPivot usp;

	public Robot(Vec2 pos, PhysicsEngine engine, double direction) {
		
		
		Vec2[] vertices = { new Vec2(9, 9), new Vec2(-9, 9), new Vec2(-9, -9), new Vec2(9, -9) };
		chassis = new Polygon(vertices, Material.ROBOT);
	
		w1 = new Wheel(2, 4.7, chassis.getAttachment(new Vec2(8, -8)));
		w2 = new Wheel(2, 4.7, chassis.getAttachment(new Vec2(-8, -8)));
		w3 = new Wheel(2, 4.7, chassis.getAttachment(new Vec2(-8, 8)));
		w4 = new Wheel(2, 4.7, chassis.getAttachment(new Vec2(8, 8)));
		w5 = new Wheel(2, 4.7, chassis.getAttachment(new Vec2(8, 0)));
		w6 = new Wheel(2, 4.7, chassis.getAttachment(new Vec2(-8, 0)));
		
		arm1 = new Arm(2, 4.7, chassis.getAttachment(new Vec2(-6, 0)), 
				Arm.getVerts(chassis.getAttachment(new Vec2(-6, 0)), 2, 4.7, false), Material.ARM);

		chassis.moveBy(pos);
		arm1.moveBy(pos);

		
		hardwareMap = new HardwareMap();

		hardwareMap.dcMotor.put("w1", w1);
		hardwareMap.dcMotor.put("w2", w2);
		hardwareMap.dcMotor.put("w3", w3);
		hardwareMap.dcMotor.put("w4", w4);
		hardwareMap.dcMotor.put("w5", w5);
		hardwareMap.dcMotor.put("w6", w6);
		hardwareMap.dcMotor.put("arm1", arm1);
		
		//rangeSensor = new ModernRoboticsI2cRangeSensor(chassis.getAttachment(pos.added(new Vec2(7, -9))), 0, engine);
		//hardwareMap.range.put("range", rangeSensor);
		
		//usp = new USPivot(rangeSensor);
		//hardwareMap.servo.put("usp", usp);
		
		chassis.rotateBy(Math.PI);
		arm1.rotateBy(Math.PI);
		
		imu= new BNO055IMU(chassis);
		hardwareMap.imu.put("imu",imu);
		
		chassis.rotateBy(2.3);
		arm1.rotateBy(2.3);
	}

	public void draw(Graphics2D g) {
		chassis.draw(g);
		w1.draw(g);
		w2.draw(g);
		w3.draw(g);
		w4.draw(g);
		w5.draw(g);
		w6.draw(g);
		arm1.draw(g);
		g.setColor(Color.GREEN);
		Vec2 dir = new Vec2(chassis.direction() - Math.PI / 2).scaled(9);
		Vec2 pos = chassis.centerPoint();
		g.draw(new Line2D.Double(pos.x, pos.y, pos.x + dir.x, pos.y + dir.y));
		//rangeSensor.draw(g);
	}

	public void update(double dt) {
		w1.update(dt);
		w2.update(dt);
		w3.update(dt);
		w4.update(dt);
		w5.update(dt);
		w6.update(dt);
		arm1.update(dt);
		double ratio = arm1.getMass()/chassis.getMass();
		arm1.applyForce(chassis.getVelocity().scaled((-10000/ratio)*dt));
		arm1.applyTorque(chassis.getAngularVelocity() * -175 * dt);
		chassis.applyForce(chassis.getVelocity().scaled(-10000*dt));
		chassis.applyTorque(chassis.getAngularVelocity() * -175 * dt);
		//rangeSensor.update(dt);
	}

	public List<Body> getBodies() {
		List<Body> bodies = new LinkedList<Body>();
		bodies.add(chassis);
		bodies.add(arm1);
		return bodies;
	}

	public HardwareMap getHardwareMap() {
		return hardwareMap;
	}
}
