package org.firstinspires.ftc.teamcode;

import org.pumatech.simulator.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by vvestin & dani & gabefowiel on 9/23/17.
 */
@TeleOp(name = "BasicTeleOp", group = "D")
public class BasicTeleOp extends OpMode {

	private DcMotor motor1;
	private DcMotor motor2;
	private DcMotor motor3;
	private DcMotor motor4;

	private OrientationSensor orientationSensor;

//	private double pos = 0.5;

	@Override
	public void init() {
		motor1 = hardwareMap.dcMotor.get("w1");
		motor2 = hardwareMap.dcMotor.get("w2");
		motor3 = hardwareMap.dcMotor.get("w3");
		motor4 = hardwareMap.dcMotor.get("w4");

		// motor2.setDirection(DcMotorSimple.Direction.REVERSE);
		// motor3.setDirection(DcMotorSimple.Direction.REVERSE);

		orientationSensor = new OrientationSensor(hardwareMap);
	}

	@Override
	public void loop() {
		double x = gamepad1.right_stick_y;
		double y = -gamepad1.right_stick_x;
		double dir = (gamepad1.right_trigger > gamepad1.left_trigger?-gamepad1.right_trigger:gamepad1.left_trigger);
		move(x, y, dir);
		/*
		double heading = orientationSensor.getOrientation();
		motor1.setPower(0);
		motor2.setPower(0);
		motor3.setPower(0);
		motor4.setPower(0);
		double newx = gamepad1.left_stick_x;
		double newy = gamepad1.left_stick_y;
		// double newx = gamepad1.right_stick_x;
		// double newy = gamepad1.right_stick_y;
		if (Math.abs(newx) > .1 || Math.abs(newy) > .1) {
			double arctanVar = Math.toDegrees(Math.atan2(newy, newx));
			double target = 0;
			if (0 <= arctanVar && arctanVar < 180) {
				target = arctanVar - 180;
			} else if (-180 <= arctanVar && arctanVar < 0) {
				target = arctanVar + 180;
			}

			if (heading - target < 180) {
				if (heading < target) {
					motor1.setPower(1);
					motor2.setPower(-1);
					motor3.setPower(-1);
					motor4.setPower(1);
					return;
				} else {
					motor1.setPower(-1);
					motor2.setPower(1);
					motor3.setPower(1);
					motor4.setPower(-1);
					return;
				}

			} else {
				if (heading < target) {
					motor1.setPower(-1);
					motor2.setPower(1);
					motor3.setPower(1);
					motor4.setPower(-1);
					return;
				} else {
					motor1.setPower(1);
					motor2.setPower(-1);
					motor3.setPower(-1);
					motor4.setPower(1);
					return;
				}
			}
		}

		// Equations:
		// x'=xcos0+ysin0
		// y'=-xsin0+ycos0
		double rawx = gamepad1.right_stick_x;
		double rawy = gamepad1.right_stick_y;
		double x = rawx * Math.cos(Math.toRadians(-heading)) - rawy * Math.sin(Math.toRadians(-heading));
		double y = rawx * Math.sin(Math.toRadians(-heading)) + rawy * Math.cos(Math.toRadians(-heading));

		if ((x != 0 || y != 0) && (gamepad1.left_trigger > 0.05 || gamepad1.right_trigger > 0.05)) {
			double n = ((x + y) / 2.0); // n is the power of the motors in the +x +y direction
			double m = -((x - y) / 2.0); // m is the power of the motors in the +x -y direction
			motor1.setPower(m);
			motor2.setPower(n);
			if (gamepad1.left_trigger > 0.05) {
				motor3.setPower(-1 * gamepad1.left_trigger);
				motor4.setPower(gamepad1.left_trigger);
				return;
			}
			if (gamepad1.right_trigger > 0.05) {
				motor3.setPower(gamepad1.right_trigger);
				motor4.setPower(-1 * gamepad1.right_trigger);
				return;
			}
		} else if (x != 0 || y != 0) {
			double n = ((x + y) / 2.0); // n is the power of the motors in the +x +y direction
			double m = -((x - y) / 2.0); // m is the power of the motors in the +x -y direction
			motor1.setPower(m);
			motor2.setPower(n);
			motor3.setPower(m);
			motor4.setPower(n);
		} else if (gamepad1.left_trigger > 0.05) {
			motor1.setPower(gamepad1.left_trigger);
			motor2.setPower(-1 * gamepad1.left_trigger);
			motor3.setPower(-1 * gamepad1.left_trigger);
			motor4.setPower(gamepad1.left_trigger);
			return;
		} else if (gamepad1.right_trigger > 0.05) {
			motor1.setPower(-1 * gamepad1.right_trigger);
			motor2.setPower(gamepad1.right_trigger);
			motor3.setPower(gamepad1.right_trigger);
			motor4.setPower(-1 * gamepad1.right_trigger);
			return;
		}
		// if (gamepad1.a) {
		// pos -= .01;
		// }
		// if (gamepad1.b) {
		// pos += .01;
		// }
		 */
	}

	// double x = gamepad1.right_stick_x;
	// double y = gamepad1.right_stick_y;
	// double dir = gamepad1.right_trigger or gamepad1.left_trigger;
	public void move(double x, double y, double dir) {
		double w4 = 0.5;
		double w1 = -2 * y - 2 * dir + w4;
		double w2 = 2 * y + 2 * x - w4;
		double w3 = 2 * x + 2 * dir - w4;
		double magnitude = Math.sqrt(w1 * w1 + w2 * w2 + w3 * w3 + w4 * w4);
		if (magnitude < 1) {
			magnitude = 1;
		}
		motor1.setPower(w1 / magnitude);
		motor2.setPower(w2 / magnitude);
		motor3.setPower(w3 / magnitude);
		motor4.setPower(w4 / magnitude);
	}

}