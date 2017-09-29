package org.pumatech.states;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.util.LinkedList;
import java.util.List;

import org.firstinspires.ftc.teamcode.TestOp;
import org.pumatech.field.Field;
import org.pumatech.physics.Body;
import org.pumatech.physics.PhysicsEngine;
import org.pumatech.physics.Vec2;
import org.pumatech.robot.Robot;
import org.pumatech.simulator.Camera;
import org.pumatech.simulator.DriverStation;

import com.qualcomm.robotcode.hardware.Gamepad;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class SimulationState extends State {

	private OpMode opmode;
	private DriverStation ds;
	private PhysicsEngine engine;
	private Camera cam;
	private Body viewer;
	private Robot robot;
	private Field field;
	private Gamepad gamepad1, gamepad2;
	
	public SimulationState() {
		robot = new Robot(new Vec2(200, 200));
		field = new Field();
		
		List<Body> bodies = new LinkedList<Body>();
		bodies.addAll(robot.getBodies());
		bodies.addAll(field.getBodies());
		engine = new PhysicsEngine(bodies);
		
		viewer = new Body(null) {
			private static final int SPEED = 150;
			
			private Vec2 pos = new Vec2(200, 200);
			private boolean[] kd = keyDown;

			public void draw(Graphics2D g) { }
			public void update(double dt) {
				if (kd[KeyEvent.VK_W])
					pos.add(new Vec2(0, -SPEED * dt));
				if (kd[KeyEvent.VK_A])
					pos.add(new Vec2(-SPEED * dt, 0));
				if (kd[KeyEvent.VK_S])
					pos.add(new Vec2(0, SPEED * dt));
				if (kd[KeyEvent.VK_D])
					pos.add(new Vec2(SPEED * dt, 0));
			}
			
			public Vec2 centerPoint() {
				return pos;
			}
			
			public void moveBy(Vec2 v) {}
			public double area() { return 0; }
			public boolean containsPoint(Vec2 p) { return false; }
			public boolean isColliding(Body b) { return false; }
			public double direction() { return 0; }
		};
		cam = new Camera(viewer);
		
        System.out.println("Searching for input devices");				
        Controller[] ca = ControllerEnvironment.getDefaultEnvironment().getControllers();

        for (int i = 0; i < ca.length; i++) {
        	System.out.println(ca[i].getType() + " : " + ca[i].getName());
        	if (ca[i].getType() == Controller.Type.GAMEPAD) {
        		if (gamepad1 == null) {
        			System.out.println("Gamepad1 found: " + ca[i].getName());
        			gamepad1 = new Gamepad(ca[i]);
        		} else if (gamepad2 == null) {
        			System.out.println("Gamepad2 found: " + ca[i].getName());
        			gamepad2 = new Gamepad(ca[i]);
        		}
        	}
        }
		
		opmode = new TestOp();
		opmode.setup(robot.getHardwareMap(), gamepad1, gamepad2);
		
		opmode.init();
		opmode.start();
		
		ds = new DriverStation();
	}

	public void draw(Graphics2D g, Dimension d) {
		ds.draw(g);
		cam.activate(g, d);
		field.draw(g);
		robot.draw(g);
		engine.draw(g);
		cam.deactivate(g, d);
	}

	public void update(double dt) {
		cam.update(dt);
		viewer.update(dt);
		robot.update(dt);
		engine.update(dt);
		
		if (gamepad1 != null)
			gamepad1.update(dt);
		if (gamepad2 != null)
			gamepad2.update(dt);

		opmode.loop();
	}
	
	public void keyPressed(int k) {
		super.keyPressed(k);
		if (k == KeyEvent.VK_TAB)
			ds.toggle();
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		cam.mouseWheelMoved(e);
	}
}
