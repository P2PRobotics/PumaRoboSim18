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

	private OpMode opmode; // Currently running opmode
	private DriverStation ds;
	private PhysicsEngine engine;
	
	private Camera cam;
	private Body viewer; // Sets the center of the camera view (moves around with arrow keys)
	
	private Robot robot; // TODO consider moving into driver station
	private Field field;
	private Gamepad gamepad1, gamepad2;
	
	public SimulationState() {
		// Initialize robot and field
		robot = new Robot(new Vec2(200, 200));
		field = new Field();
		
		// Get physical bodies for simulation and initialize physics engine to simulate those bodies
		List<Body> bodies = new LinkedList<Body>();
		bodies.addAll(robot.getBodies());
		bodies.addAll(field.getBodies());
		engine = new PhysicsEngine(bodies);
		
		// Camera follows the viewer body (not being simulated), which moves with arrow keys
		viewer = new Body(null) {
			private static final int SPEED = 150; // 150 in/s
			
			private Vec2 pos = new Vec2(200, 200);
			// keyDown is an array of booleans corresponding to a specific key (index is KeyEvent.VK)
			// references the keyDown array in SimulationState (inherited from State)
			private boolean[] kd = keyDown;

			public void draw(Graphics2D g) { }
			public void update(double dt) {
				// Moves based on arrow keys, proportional to time elapsed to give consistent movement
				if (kd[KeyEvent.VK_W])
					pos.add(new Vec2(0, -SPEED * dt));
				if (kd[KeyEvent.VK_A])
					pos.add(new Vec2(-SPEED * dt, 0));
				if (kd[KeyEvent.VK_S])
					pos.add(new Vec2(0, SPEED * dt));
				if (kd[KeyEvent.VK_D])
					pos.add(new Vec2(SPEED * dt, 0));
			}
			
			// centerPoint() is the point tracked by Camera
			public Vec2 centerPoint() {
				return pos;
			}
			
			// Methods necessary for a body, but not a camera viewer
			public void moveBy(Vec2 v) {}
			public double area() { return 0; }
			public boolean containsPoint(Vec2 p) { return false; }
			public boolean isColliding(Body b) { return false; }
			public double direction() { return 0; }
		};
		cam = new Camera(viewer);
		
		// Use JInput to discover input devices
        System.out.println("Searching for input devices...");				
        Controller[] ca = ControllerEnvironment.getDefaultEnvironment().getControllers();

        // Initialize gamepad1 and gamepad2 with first gamepads discovered
        // TODO use start+A and start+B to select gamepads 1 and 2
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
		
        // Initialize opmode and connect it to robot and gamepads (and telemetry later)
        // TODO have DriverStation dynamically setup opmode
		opmode = new TestOp();
		opmode.setup(robot.getHardwareMap(), gamepad1, gamepad2);
		
        // TODO have DriverStation initialize and start on user input
		opmode.init();
		opmode.start();
		
		ds = new DriverStation();
	}

	public void draw(Graphics2D g, Dimension d) {
		ds.draw(g); // DriverStation drawn first because it shifts other elements when opened
		
		// Draw everything else in the camera view
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

		// Repeatedly runs opmode loop about 60 times per second
		opmode.loop();
	}
	
	// super.keyPressed updates keyDown array. Pressing tab toggles DriverStation view
	public void keyPressed(int k) {
		super.keyPressed(k);
		if (k == KeyEvent.VK_TAB)
			ds.toggle();
	}
	
	// Dispatches mouse scroll event to camera, which zooms in/out
	public void mouseWheelMoved(MouseWheelEvent e) {
		cam.mouseWheelMoved(e);
	}
}
