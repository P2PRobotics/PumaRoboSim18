package org.firstinspires.ftc.robotcore.external;

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

}
