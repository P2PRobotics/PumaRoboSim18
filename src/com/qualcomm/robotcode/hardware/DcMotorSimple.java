package com.qualcomm.robotcode.hardware;

public interface DcMotorSimple extends HardwareDevice {

	public DcMotorSimple.Direction getDirection();
	public double getPower();
	public void setDirection(Direction motorDir);
	public void setPower(double power);
	
	public enum Direction {
		FORWARD, REVERSE;
	}
}
