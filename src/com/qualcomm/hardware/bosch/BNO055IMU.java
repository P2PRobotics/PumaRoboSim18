package com.qualcomm.hardware.bosch;

import org.pumatech.physics.Body;

public class BNO055IMU {
	private Body body;

	public BNO055IMU(Body corpse) {
		body = corpse;
		System.out.println(body);
	}
}
