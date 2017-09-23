package org.pumatech.simulator;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class GraphicsLib {
	public static final Font FONT = new Font("Courier", Font.PLAIN, 16); // Font that all Graphics is set to use by default

	// Static helper method to draw a string centered at an x and y coordinate
	public static void drawStringCentered(Graphics2D g, String msg, int x, int y) {
		FontMetrics metrics = g.getFontMetrics(FONT);
		x -= metrics.stringWidth(msg) / 2;
		y += metrics.getHeight() / 3;
		g.drawString(msg, x, y);
	}
	
}
