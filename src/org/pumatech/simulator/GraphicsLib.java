package org.pumatech.simulator;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

// Contains static methods for drawing not provided by Graphics2D
public class GraphicsLib {
	public static final Font FONT = new Font("Courier", Font.PLAIN, 16); // Font that all Graphics is set to use by default

	// Static helper method to draw a string centered at an x and y coordinate
	public static void drawStringCentered(Graphics2D g, String msg, int x, int y, int size) {
		Font font = new Font("Courier", Font.PLAIN, size);
		g.setFont(font);
		FontMetrics metrics = g.getFontMetrics(font);
		x -= metrics.stringWidth(msg) / 2;
		y += metrics.getHeight() / 3;
		g.drawString(msg, x, y);
	}
	
	// TODO method to draw an arrow (for vectors and things)
}
