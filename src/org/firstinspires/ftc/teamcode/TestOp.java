package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcode.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class TestOp extends OpMode {

	DcMotor motor1;
	DcMotor motor2;
	
	public void init() {
		telemetry.addData("Initialized", true);
		motor1 = hardwareMap.dcMotor.get("w1");
		motor2 = hardwareMap.dcMotor.get("w2");
	}
	
	public void start() {
		telemetry.addData("Starting", true);
		motor1.setPower(1);
		//motor2.setPower(-1);
	}
	
	public void loop() {
		
	}
}
