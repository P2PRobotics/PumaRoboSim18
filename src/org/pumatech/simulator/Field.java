package org.pumatech.simulator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;

import org.pumatech.physics.Body;

public class Field implements Drawable {

	public void draw(Graphics2D g) {
		Stroke old = g.getStroke();
		g.setStroke(new BasicStroke(5));
		g.setColor(Color.DARK_GRAY);
		g.draw(new Rectangle2D.Double(0, 0, 400, 400));
		g.setStroke(old);
	}

	public List<Body> getBodies() {
		List<Body> bodies = new LinkedList<Body>();
		return bodies;
	}
	
}
