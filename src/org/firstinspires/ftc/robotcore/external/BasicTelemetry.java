	package org.firstinspires.ftc.robotcore.external;

import java.awt.Graphics2D;

public class BasicTelemetry implements Telemetry {

	public Item addData(String caption, Object value) {
		System.out.println(caption + ": " + value);
		return null;
	}

	public void clearAll() {
		
	}

	public boolean update() {
		return false;
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

}
