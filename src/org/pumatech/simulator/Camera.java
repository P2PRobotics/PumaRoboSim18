package org.pumatech.simulator;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import org.pumatech.physics.Body;
import org.pumatech.physics.Vec2;

public class Camera {
	// The distance the tracked entity is allowed to get away before camera moves to
	// keep it in frame
	public static final int X_DIST_TOLERANCE = 100;
	public static final int Y_DIST_TOLERANCE = 100;

	// The body the camera is tracking
	private Body tracking;
	// The current position of the camera (The center of the camera view)
	private Vec2 pos;
	private double scale;

	// Initialize tracking body and start camera with tracked entity in the center
	public Camera(Body tracking) {
		this.tracking = tracking;
		pos = tracking.centerPoint();
		scale = 50 / 9.0;
	}

	// Activates camera so that everything drawn is in perspective.
	public void activate(Graphics2D g, Dimension d) {
		g.translate(d.getWidth() / 2, d.getHeight() / 2);
		g.scale(scale, scale);
		g.translate(-pos.x, -pos.y);
	}

	// Deactivates camera & returns Graphics2D object to original position
	public void deactivate(Graphics2D g, Dimension d) {
		g.translate(pos.x, pos.y);
		g.scale(1 / scale, 1 / scale);
		g.translate(-d.getWidth() / 2, -d.getHeight() / 2);
	}

	// TODO update both getCoordinate methods to work with scale
	// Returns a position vector for the coordinates in the system instead of screen
	// coordinates
	public Vec2 getCoordinate(Vec2 mouseCoord) {
		return new Vec2(mouseCoord.x + pos.x, mouseCoord.y + pos.y);
	}

	// Returns a position vector for the coordinates in the system instead of screen
	// coordinates
	public Vec2 getCoordinate(MouseEvent e) {
		return new Vec2(e.getX() + pos.x, e.getY() + pos.y);
	}

	// Update the position of the camera to keep the tracked entity within the X and
	// Y tolerances
	public void update(double dt) {
		Vec2 centerPoint = tracking.centerPoint();
		if (centerPoint.x > pos.x + X_DIST_TOLERANCE)
			pos.x = centerPoint.x - X_DIST_TOLERANCE;
		if (centerPoint.x < pos.x - X_DIST_TOLERANCE)
			pos.x = centerPoint.x + X_DIST_TOLERANCE;
		if (centerPoint.y > pos.y + Y_DIST_TOLERANCE)
			pos.y = centerPoint.y - Y_DIST_TOLERANCE;
		if (centerPoint.y < pos.y - Y_DIST_TOLERANCE)
			pos.y = centerPoint.y + Y_DIST_TOLERANCE;
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		scale -= scale * (e.getPreciseWheelRotation() / 12);
	}
}
