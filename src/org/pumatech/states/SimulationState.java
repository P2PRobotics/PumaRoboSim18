package org.pumatech.states;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.util.LinkedList;
import java.util.List;

import org.pumatech.field.Field;
import org.pumatech.physics.Body;
import org.pumatech.physics.PhysicsEngine;
import org.pumatech.physics.Vec2;
import org.pumatech.robot.Robot;
import org.pumatech.simulator.Camera;
import org.pumatech.simulator.DriverStation;

import com.qualcomm.robotcore.hardware.Gamepad;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class SimulationState extends State {

	private DriverStation ds;
	private PhysicsEngine engine;
	
	private Camera cam;
	private Body viewer; // Sets the center of the camera view (moves around with arrow keys)
	
	private Robot robot; 
	private Field field;
	private List<Controller> gamepads;
	
	public SimulationState() {
		// Initialize robot and field
		robot = new Robot(new Vec2(72, 72));
		field = new Field();
		
		// Get physical bodies for simulation and initialize physics engine to simulate those bodies
		List<Body> bodies = new LinkedList<Body>();
		bodies.addAll(robot.getBodies());
		bodies.addAll(field.getBodies());
		engine = new PhysicsEngine(bodies);
		
		// Camera follows the viewer body (not being simulated), which moves with arrow keys
		viewer = new Body(null) {
			private static final int SPEED = 50; // 150 in/s
			
			private Vec2 pos = new Vec2(72, 72);
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
		
		ds = new DriverStation(robot, field);
		
		// Use JInput to discover input devices
		gamepads = new LinkedList<Controller>();
        System.out.println("Searching for input devices...");				
        Controller[] ca = ControllerEnvironment.getDefaultEnvironment().getControllers();
        for (Controller c : ca) {
        	if (c.getType() == Controller.Type.GAMEPAD)
        		gamepads.add(c);
        }
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
		ds.update(dt);
		robot.update(dt);
		cam.update(dt);
		viewer.update(dt);
		engine.update(dt);
		
		for (Controller c : gamepads) {
			c.poll();
			
			Component[] components = c.getComponents();
			if (components[12].getPollData() == 1 && components[5].getPollData() == 1) {
				ds.setGamepad1(new Gamepad(c));
			} else if (components[12].getPollData() == 1 && components[6].getPollData() == 1) {
				ds.setGamepad2(new Gamepad(c));
			}
		}
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
