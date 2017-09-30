package org.firstinspires.ftc.robotcore.external;

public interface Telemetry {
	
	Telemetry.Item addData(String caption, Object value);
	void clearAll();
	boolean update();
	
	public interface Item {
		
	}
	
	public interface Line {
		
	}
	
	public interface Log {
		
	}
}
