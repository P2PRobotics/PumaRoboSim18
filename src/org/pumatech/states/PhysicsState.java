package org.pumatech.states;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import org.pumatech.physics.Attachment;
import org.pumatech.physics.Body;
import org.pumatech.physics.Circle;
import org.pumatech.physics.Material;
import org.pumatech.physics.PhysicsEngine;
import org.pumatech.physics.Polygon;
import org.pumatech.physics.Vec2;
import org.pumatech.simulator.Camera;

// Don't worry about this class. It won't be commented.
public class PhysicsState extends State {

	private PhysicsEngine engine;
	private Camera cam;
	private Body viewer;
	private Attachment attachment;
	private Vec2 mouseCoord;
	
	public PhysicsState() {
		List<Body> bodies = new ArrayList<Body>();
		
		bodies.add(new Polygon(new Vec2(300, 200), 30, 4, Material.RUBBER));
		bodies.add(new Polygon(new Vec2(100, 200), 40, 5, Material.ROBOT));
		bodies.add(new Polygon(new Vec2(200, 500), 200, 4, Material.IMMOVEABLE));
		bodies.get(bodies.size() - 1).rotateBy(Math.PI / 4);
				
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
	}
	
	public void draw(Graphics2D g, Dimension d) {
		cam.activate(g, d);
		engine.draw(g);
		if (attachment != null) {
			g.setColor(Color.GREEN);
			g.fill(new Ellipse2D.Double(attachment.getBody().centerPoint().x - 1.5, attachment.getBody().centerPoint().y - 1.5, 3, 3));
			g.draw(new Line2D.Double(attachment.getPoint().x, attachment.getPoint().y, mouseCoord.x, mouseCoord.y));
		}
		cam.deactivate(g, d);
	}

	public void update(double dt) {
		cam.update(dt);
		viewer.update(dt);
		
		if (attachment != null) {
			attachment.getBody().applyImpulse(mouseCoord.subtracted(attachment.getPoint()).scaled(150), attachment.getPoint());
		}
		
		engine.update(dt);
	}
	
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			mouseCoord = cam.getCoordinate(e);
			for (Body b : engine.getBodies()) {
				attachment = b.getAttachment(mouseCoord);
				if (attachment != null) return;
			}
		} else {
			engine.addBody(new Circle(cam.getCoordinate(e), 20, Material.ROBOT));
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		mouseCoord = cam.getCoordinate(e);
	}
	
	public void mouseReleased(MouseEvent e) {
		attachment = null;
	}
}
