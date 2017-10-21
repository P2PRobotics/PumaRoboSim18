package org.firstinspires.ftc.robotcore.external;

import java.awt.Graphics2D;

public interface Telemetry {
	
	Telemetry.Item addData(String caption, Object value);
	void clearAll();
	boolean update();
	void draw(Graphics2D g);
	public interface Item {
		
	}
	
	public interface Line {
		
	}
	
	public interface Log {
		
	}
}
