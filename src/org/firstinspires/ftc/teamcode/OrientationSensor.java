package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class OrientationSensor {
	private BNO055IMU imu;
public OrientationSensor(HardwareMap map) {
	imu=map.imu.get("imu");
}
public double getOrientation() {
	double phat=-Math.toDegrees(imu.getOrientation())%360;
	if(phat>180) {
		phat=phat-360;
	}else if(phat<-180) {
		phat=360+phat;
	}
	return phat;
}
}
