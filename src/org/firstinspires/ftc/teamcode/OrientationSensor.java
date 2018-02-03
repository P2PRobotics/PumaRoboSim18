package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class OrientationSensor {
	private BNO055IMU imu;
	private double startAngle;
	private boolean hasStarted;
	
	public OrientationSensor(HardwareMap map) {
		imu = map.imu.get("imu");
	}
	public double getOrientation() {
		double angle = imu.getOrientation();
		if (!hasStarted) {
			startAngle = angle;
			hasStarted = true;
		}
		angle = -Math.toDegrees(startAngle - angle) % 360;
		if(angle > 180) {
			angle = angle-360;
		}else if(angle < -180) {
			angle = 360 + angle;
		}
		return -angle;
	}
}
