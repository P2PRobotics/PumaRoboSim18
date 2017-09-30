package org.pumatech.simulator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class DriverStation {
	@SuppressWarnings("unused")
	private static final int WIDTH = 350, HEIGHT = WIDTH * 690 / 505;
	private static final double PIXEL = WIDTH / 505d;
	private static final Color[] RED = {
			new Color(16,  1, 2),   new Color(45, 1, 4), 
			new Color(63,  2, 6),   new Color(73, 12, 16),
			new Color(83,  2, 7),   new Color(98, 2, 8),
			new Color(121, 14, 21), new Color(166, 4, 14),
			new Color(192, 11, 13)};

	// boolean for whether DS is shown (toggles with tab)
	private boolean show;
	
	public DriverStation() {
		show = false; // DS starts hidden
	}
	
	public void draw(Graphics2D g) {
		if (!show) return;
		// remember previous clip bounds because new clip will be set
		// (clips define the region of the screen which can be drawn on)
		Rectangle oldClip = g.getClipBounds();
		
		// Ugly code to draw the DS GUI. Good Luck debuggin this.
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, (int) (505 * PIXEL), 2000);
		g.setColor(RED[6]);
		g.fillRect(0, (int) (112 * PIXEL), (int) (505 * PIXEL), (int) (223 * PIXEL));
		g.setColor(RED[7]);
		g.fillRect((int) (3 * PIXEL), (int) (339 * PIXEL), (int) (500 * PIXEL), (int) (67 * PIXEL));
		g.setColor(RED[4]);
		g.fillRect((int) (3 * PIXEL), (int) (409 * PIXEL), (int) (500 * PIXEL), (int) (282 * PIXEL));
		g.setClip((int) (3 * PIXEL), (int) (409 * PIXEL), (int) (500 * PIXEL), (int) (282 * PIXEL));
		g.setColor(RED[5]);
		g.fillOval((int) (33 * PIXEL), (int) (331 * PIXEL), (int) (438 * PIXEL), (int) (428 * PIXEL));
		g.setColor(RED[2]);
		g.fillOval((int) (112 * PIXEL), (int) (444 * PIXEL), (int) (211 * PIXEL), (int) (211 * PIXEL));
		g.setColor(Color.WHITE);
		g.fillOval((int) (124 * PIXEL), (int) (456 * PIXEL), (int) (188 * PIXEL), (int) (188 * PIXEL));
		g.setClip(oldClip);
		g.setColor(RED[2]);
		g.fillRect((int) (3 * PIXEL), (int) (115 * PIXEL), (int) (499 * PIXEL), (int) (141 * PIXEL));
		
		// Draw line dividing DS from rest of simulation
		g.setColor(Color.WHITE);
		g.drawLine((int) (505 * PIXEL), -30, (int) (505 * PIXEL), 2000);
		// Translate the rest of the simulation over to the right, but only half the SD width to keep simulation centered
		g.translate(252.5 * PIXEL, 0);
		// Set the clip of the rest of the simulation to avoid overwriting DS area
		g.setClip((int) (252.5 * PIXEL + 1), 0, 10000, 2000); // Should be big rectangle size
	}

	public void toggle() {
		show = !show;
	}
}
