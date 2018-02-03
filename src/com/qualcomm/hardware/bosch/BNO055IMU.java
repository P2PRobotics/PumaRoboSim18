package com.qualcomm.hardware.bosch;

import org.pumatech.physics.Body;

import com.qualcomm.robotcore.hardware.HardwareDevice;

public class BNO055IMU implements HardwareDevice  {
	private Body b;
	
	public BNO055IMU(Body b) {
		this.b = b;
	}
	
	public double getOrientation() {
		return b.direction();
	}
}
