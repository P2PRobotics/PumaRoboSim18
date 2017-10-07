package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by vvestin on 9/23/17.
 */

public class BasicTeleOp extends OpMode {

    private DcMotor motor1;
    private DcMotor motor2;
    private DcMotor motor3;
    private DcMotor motor4;
    
    @Override
    public void init() {
        motor1 = hardwareMap.dcMotor.get("w1");
        motor2 = hardwareMap.dcMotor.get("w2");
        motor3 = hardwareMap.dcMotor.get("w3");
        motor4 = hardwareMap.dcMotor.get("w4");
    }

    @Override
    public void loop() {
        if (gamepad1.left_trigger > 0.1) {
            motor1.setPower(gamepad1.left_trigger);
            motor2.setPower(-gamepad1.left_trigger);
            motor3.setPower(-gamepad1.left_trigger);
            motor4.setPower(gamepad1.left_trigger);
            return;
        }
        if (gamepad1.right_trigger > 0.1) {
            motor1.setPower(-gamepad1.right_trigger);
            motor2.setPower(gamepad1.right_trigger);
            motor3.setPower(gamepad1.right_trigger);
            motor4.setPower(-gamepad1.right_trigger);
            return;
        }
        double x = gamepad1.right_stick_x;
        double y = gamepad1.right_stick_y;
        double m;
        double n;
        if (x != 0 || y != 0) {
            n = (x + y)/2.0;
            m = -(x - y)/2.0;
            motor1.setPower(m);
            motor2.setPower(n);
            motor3.setPower(m);
            motor4.setPower(n);
        }
    }
}