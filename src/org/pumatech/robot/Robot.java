package org.pumatech.robot;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import org.pumatech.physics.Body;
import org.pumatech.physics.Material;
import org.pumatech.physics.Polygon;
import org.pumatech.physics.Vec2;

import com.qualcomm.robotcode.hardware.HardwareMap;

public class Robot {

	private Wheel w1, w2, w3, w4;
	private Body chassis;
	private HardwareMap hardwareMap;
	
	public Robot(Vec2 pos) {
		Vec2[] vertices = {new Vec2(25, 25), new Vec2(-25, 25), new Vec2(-25, -25), new Vec2(25, -25)};
		chassis = new Polygon(vertices, Material.WOOD);
		w1 = new Wheel(8.3, 0, chassis.getAttachment(new Vec2(0, -24)));
		w2 = new Wheel(8.3, 0, chassis.getAttachment(new Vec2(0, 24)));
		w3 = new Wheel(8.3, -Math.PI / 2, chassis.getAttachment(new Vec2(-24, 0)));
		w4 = new Wheel(8.3, -Math.PI / 2, chassis.getAttachment(new Vec2(24, 0)));
		
		chassis.moveBy(pos);
		
		hardwareMap = new HardwareMap();
		
		hardwareMap.dcMotor.put("w1", w1);
		hardwareMap.dcMotor.put("w2", w2);
		hardwareMap.dcMotor.put("w3", w3);
		hardwareMap.dcMotor.put("w4", w4);
	}
	
	public void draw(Graphics2D g) {
		chassis.draw(g);
		w1.draw(g);
		w2.draw(g);
		w3.draw(g);
		w4.draw(g);
	}
	
	public void update(double dt) {
		w1.update(dt);
		w2.update(dt);
		w3.update(dt);
		w4.update(dt);
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
