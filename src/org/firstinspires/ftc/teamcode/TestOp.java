package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcode.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class TestOp extends OpMode {

	DcMotor motor;
	
	public void init() {
		telemetry.addData("Initialized", true);
		motor = hardwareMap.dcMotor.get("w1");
	}
	
	public void start() {
		telemetry.addData("Starting", true);
		motor.setPower(1);
	}

	public void loop() {
		
	}
}
