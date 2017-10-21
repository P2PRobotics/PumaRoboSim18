package org.pumatech.robot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.List;

import org.pumatech.physics.Body;
import org.pumatech.physics.Material;
import org.pumatech.physics.Polygon;
import org.pumatech.physics.Vec2;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {

	private Wheel w1, w2, w3, w4;
	private Body chassis;
	private HardwareMap hardwareMap;
	private BNO055IMU imu;

	public Robot(Vec2 pos) {
		Vec2[] vertices = { new Vec2(9, 9), new Vec2(-9, 9), new Vec2(-9, -9), new Vec2(9, -9) };
		chassis = new Polygon(vertices, Material.WOOD);
	
		w1 = new Wheel(2, -Math.PI / 4, chassis.getAttachment(new Vec2(8, 8)));
		w2 = new Wheel(2, Math.PI / 4, chassis.getAttachment(new Vec2(8, -8)));
		w3 = new Wheel(2, -Math.PI / 4, chassis.getAttachment(new Vec2(-8, -8)));
		w4 = new Wheel(2, Math.PI / 4, chassis.getAttachment(new Vec2(-8, 8)));

		chassis.moveBy(pos);

		hardwareMap = new HardwareMap();

		hardwareMap.dcMotor.put("w1", w1);
		hardwareMap.dcMotor.put("w2", w2);
		hardwareMap.dcMotor.put("w3", w3);
		hardwareMap.dcMotor.put("w4", w4);
		
		imu= new BNO055IMU(chassis);
		
		hardwareMap.imu.put("imu",imu);
	}

	public void draw(Graphics2D g) {
		chassis.draw(g);
		w1.draw(g);
		w2.draw(g);
		w3.draw(g);
		w4.draw(g);
		g.setColor(Color.GREEN);
		Vec2 dir = new Vec2(chassis.direction() - Math.PI / 2).scaled(9);
		Vec2 pos = chassis.centerPoint();
		g.draw(new Line2D.Double(pos.x, pos.y, pos.x + dir.x, pos.y + dir.y));
	}

	public void update(double dt) {
		w1.update(dt);
		w2.update(dt);
		w3.update(dt);
		w4.update(dt);
		chassis.applyForce(chassis.getVelocity().scaled(-10000*dt));
		chassis.applyTorque(chassis.getAngularVelocity() * -175 * dt);
	}

	public List<Body> getBodies() {
		List<Body> bodies = new LinkedList<Body>();
		bodies.add(chassis);
		return bodies;
	}

	public HardwareMap getHardwareMap() {
		return hardwareMap;
	}
}
