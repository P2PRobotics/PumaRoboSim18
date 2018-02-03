package org.pumatech.robot;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.Servo;

public class USPivot extends Servo {

	private ModernRoboticsI2cRangeSensor rangeSensor;
	
	public USPivot(ModernRoboticsI2cRangeSensor rangeSensor) {
		this.rangeSensor = rangeSensor;
	}
	
	public void setPosition(double position) {
		rangeSensor.setDirection(Math.asin((.5 - position) * 2));
	}

}
