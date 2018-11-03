package org.firstinspires.ftc.teamcode;

import org.pumatech.simulator.TeleOp;
import org.pumatech.states.SimulationState;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.colorSensor;

import net.java.games.input.Component;
import net.java.games.input.Controller;

/**
 * Created by vvestin & dani & gabefowiel on 9/23/17.
 */
@TeleOp(name = "BasicTeleOp", group = "D")
public class BasicTeleOp extends OpMode {

	private DcMotor motor1;
	private DcMotor motor2;
	private DcMotor motor3;
	private DcMotor motor4;
	private DcMotor motor5;
	private DcMotor motor6;
	
	private DcMotor armMotor;

	private colorSensor cSensor;
	
	private OrientationSensor orientationSensor;

	public static final boolean moveRelative = true;
	public static final boolean turnRelative = true;

	// private double pos = 0.5;

	@Override
	public void init() {
		motor1 = hardwareMap.dcMotor.get("w1");
		motor2 = hardwareMap.dcMotor.get("w2");
		motor3 = hardwareMap.dcMotor.get("w3");
		motor4 = hardwareMap.dcMotor.get("w4");
		motor5 = hardwareMap.dcMotor.get("w5");
		motor6 = hardwareMap.dcMotor.get("w6");
		
		armMotor = hardwareMap.dcMotor.get("arm1");

		cSensor = hardwareMap.color.get("colorSensor");

		// motor2.setDirection(DcMotorSimple.Direction.REVERSE);
		// motor3.setDirection(DcMotorSimple.Direction.REVERSE);

		orientationSensor = new OrientationSensor(hardwareMap);
	}

	@Override
	public void loop() {
		double x = gamepad1.right_stick_y;
		double y = -gamepad1.right_stick_x;
		
		//This line uses some high level java, Weston didnt comment it so I'm going to - CR
		// The ? and : is basically an if else statement but with 2 symbols
		// If the stuff in the parenthesis returns true, it will be variable to the left of the colon.
		// else it will be to the right
		double dir = (gamepad1.right_trigger > gamepad1.left_trigger) ? -gamepad1.right_trigger : gamepad1.left_trigger;
				
		
		double dirx = gamepad1.left_stick_x;
		double diry = gamepad1.left_stick_y;
		
		//stops random difting from the controller not being perfectally at 0;
		if (Math.abs(x) < 0.05) {
			x = 0;
		}
		
		if (Math.abs(y) < 0.05) {
			y = 0;
		}
		

		
		move(-y, -x, dir);
		
		System.out.println(-y + " " + -x + " " + dir);
		
		for (Controller c : SimulationState.gamepads) {
			Component[] components = c.getComponents();
			for (Component com : components) {
				if (com.getPollData() == 1) {
					if (com.getName().equals("Button 3")) {
						extend();
					}
				}
			}
		}
		
		
		/*if (moveRelative && turnRelative)
			moveRR(x, y, dir);
		if (!moveRelative && turnRelative)
			moveRA(x, y, dir);
		if (!moveRelative && !turnRelative)
			moveAA(x, y, dirx, diry);
		if (moveRelative && !turnRelative)
			moveAR(x, y, dirx, diry);*/
	}
	
    public void move(double x, double y, double dir) {
        if (dir != 0.5) { // Rotating
            motor1.setPower(dir);
            motor2.setPower(-dir);
            motor3.setPower(-dir);
            motor4.setPower(dir);
        } else { // moving
        	motor1.setPower(y);
        	motor2.setPower(y);
        	motor3.setPower(y);
        	motor4.setPower(y);
        }
    }
    
    
    public void extend() {
    	armMotor.setPower(1);
    }

    public void color()
    {
    	//getColor does not actually do anything
    	cSensor.getColor();
    }
	// double x = gamepad1.right_stick_x;
	// double y = gamepad1.right_stick_y;
	// double dir = gamepad1.right_trigger or gamepad1.left_trigger;

	/////////////////////////////////////// Relative turn, relative drive
	/////////////////////////////////////// ///////////////////////////////////////
	public void moveRR(double x, double y, double dir) {

		double w4 = 0.5;
		double w1 = -2 * y - 2 * dir + w4;
		double w2 = 2 * y + 2 * x - w4;
		double w3 = 2 * x + 2 * dir - w4;

		double magnitude = Math.sqrt(w1 * w1 + w2 * w2 + w3 * w3 + w4 * w4);
		if (magnitude < 1)
			magnitude = 1;

		motor1.setPower(w1 / magnitude);
		motor2.setPower(w2 / magnitude);
		motor3.setPower(w3 / magnitude);
		motor4.setPower(w4 / magnitude);
	}

	/////////////////////////////////////// Absolute turn, relative drive /////////////////////////////////////// 
	public void moveAR(double x, double y, double dirx, double diry) {

		double heading = orientationSensor.getOrientation();
		System.out.println("                " + dirx + " " + diry + " "  + heading);
//		if (heading<=0) {
//			heading = heading + 180;
//		} else {
//			heading = heading - 180;
//		}
		
		motor1.setPower(0);
		motor2.setPower(0);
		motor3.setPower(0);
		motor4.setPower(0);

		double target = Math.toDegrees(Math.atan2(dirx, -diry));
		
		//target is in 2nd quadrant
		if (target <= 0 && target > -90) {
			// If heading is 4th quadrant
			if (heading <= 180 && heading > 90) {
				if ((360-heading+target)>(heading-target)) {
					//left
					moveRR(x, y, -1);
					return;
				} else {
					//right
					moveRR(x, y, 1);
					return;
				}
			} //heading is in 3rd quadrant
			else if (heading <= -90 && heading > -180) {
				//right
				moveRR(x, y, 1);
				return;
			} //heading is in 1st quadrant
			else if (heading <= 90 && heading > 0) {
				//left
				moveRR(x, y, -1);
				return;
			} //heading is in 2nd quadrant
			else if (heading <= 0 && heading > -90) {
				if (target<heading) {
					//left
					moveRR(x, y, -1);
					return;
				} else if (target > heading) {	
					//right
					moveRR(x, y, 1);
					return;
				}
			}
		}
		
		//target is in 4th quadrant
		if (target <= 180 && target > 90) {
			// If heading is 2nd quadrant
			if (heading <= 0 && heading > -90) {
				if (target-heading<=180) {
					//right
					moveRR(x, y, 1);
					return;
				} else {
					//left
					moveRR(x, y, -1);
					return;
				}
			} //heading is in 3rd quadrant
			else if (heading <= -90 && heading > -180) {
				//left
				moveRR(x, y, -1);
				return;
			} //heading is in 1st quadrant
			else if (heading <= 90 && heading > 0) {
				//right
				moveRR(x, y, 1);
				return;
			} //heading is in 4th quadrant
			else if (heading <= 0 && heading > -90) {
				if (target<heading) {
					//left
					moveRR(x, y, -1);
					return;
				} else if (target > heading) {	
					//right
					moveRR(x, y, 1);
					return;
				}
			}
		}
		
		//target is in 1st quadrant
		if (target <= 90 && target > 0) {
			// If heading is 3rd quadrant
			if (heading <= -90 && heading > -180) {
				if (target-heading<=180) {
					//right
					moveRR(x, y, 1);
					return;
				} else {
					//left
					moveRR(x, y, -1);
					return;
				}
			} //heading is in 2nd quadrant
			else if (heading <= 0 && heading > -90) {
				//right
				moveRR(x, y, 1);
				return;
			} //heading is in 1st quadrant
			else if (heading <= 90 && heading > 0) {
				if (heading>target)
					//left
					moveRR(x, y, -1);
					return;
				} else if (heading<target) {
					//right
					moveRR(x, y, 1);
					return;
			} //heading is in 4th quadrant
			else if (heading <= 180 && heading > 90) {
				//left
				moveRR(x, y, -1);
				return;
			}
		}
		
		//target is in 3rd quadrant
		if (target <= -90 && target > -180) {
			// If heading is 1st quadrant
			if (heading <= 90 && heading > 0) {
				if (heading-target<=180) {
					//left
					moveRR(x, y, -1);
					return;
				} else {
					//right
					moveRR(x, y, 1);
					return;
				}
			} //heading is in 2nd quadrant
			else if (heading <= 0 && heading > -90) {
				//left
				moveRR(x, y, -1);
				return;
			} //heading is in 3rd quadrant
			else if (heading <= 90 && heading > 0) {
				if (heading>target)
					//left
					moveRR(x, y, -1);
					return;
				} else if (heading<target) {
					//right
					moveRR(x, y, 1);
					return;
			} //heading is in 4th quadrant
			else if (heading <= 180 && heading > 90) {
				//right
				moveRR(x, y, 1);
				return;
			}
		}
	}

	/////////////////////////////////////// Absolute turn, absolute drive //////////////////////////////////////////////////////////////////////////////
	public void moveAA(double x, double y, double dirx, double diry) {

		double heading = orientationSensor.getOrientation();

		// Equations:
		// x'=xcos0+ysin0
		// y'=-xsin0+ycos0
		double rawx = gamepad1.right_stick_x;
		double rawy = gamepad1.right_stick_y;

		double relx = rawx * Math.cos(Math.toRadians(-heading)) - rawy * Math.sin(Math.toRadians(-heading));
		double rely = rawx * Math.sin(Math.toRadians(-heading)) + rawy * Math.cos(Math.toRadians(-heading));

		double n = ((relx + rely) / 2.0); // n is the power of the motors in the +x +y direction
		double m = -((relx - rely) / 2.0); // m is the power of the motors in the +x -y direction
		moveAR(rely, -relx, dirx, diry);
	}

	/////////////////////////////////////// Relative turn, absolute drive //////////////////////////////////////////////////////////////////////////////
	public void moveRA(double x, double y, double dir) {

		double heading = orientationSensor.getOrientation();

		// Equations:
		// x'=xcos0+ysin0
		// y'=-xsin0+ycos0
		double rawx = gamepad1.right_stick_x;
		double rawy = gamepad1.right_stick_y;

		double relx = rawx * Math.cos(Math.toRadians(-heading)) - rawy * Math.sin(Math.toRadians(-heading));
		double rely = rawx * Math.sin(Math.toRadians(-heading)) + rawy * Math.cos(Math.toRadians(-heading));

		double n = ((relx + rely) / 2.0); // n is the power of the motors in the +x +y direction
		double m = -((relx - rely) / 2.0); // m is the power of the motors in the +x -y direction

		moveRR(relx, rely, dir);
	}

	/*
	 * double heading = orientationSensor.getOrientation(); motor1.setPower(0);
	 * motor2.setPower(0); motor3.setPower(0); motor4.setPower(0); double newx =
	 * gamepad1.left_stick_x; double newy = gamepad1.left_stick_y; // double newx =
	 * gamepad1.right_stick_x; // double newy = gamepad1.right_stick_y; if
	 * (Math.abs(newx) > .1 || Math.abs(newy) > .1) { double arctanVar =
	 * Math.toDegrees(Math.atan2(newy, newx)); double target = 0; if (0 <= arctanVar
	 * && arctanVar < 180) { target = arctanVar - 180; } else if (-180 <= arctanVar
	 * && arctanVar < 0) { target = arctanVar + 180; }
	 * 
	 * if (heading - target < 180) { if (heading < target) { motor1.setPower(1);
	 * motor2.setPower(-1); motor3.setPower(-1); motor4.setPower(1); return; } else
	 * { motor1.setPower(-1); motor2.setPower(1); motor3.setPower(1);
	 * motor4.setPower(-1); return; }
	 * 
	 * } else { if (heading < target) { motor1.setPower(-1); motor2.setPower(1);
	 * motor3.setPower(1); motor4.setPower(-1); return; } else { motor1.setPower(1);
	 * motor2.setPower(-1); motor3.setPower(-1); motor4.setPower(1); return; } } }
	 * 
	 * // Equations: // x'=xcos0+ysin0 // y'=-xsin0+ycos0 double rawx =
	 * gamepad1.right_stick_x; double rawy = gamepad1.right_stick_y; double x = rawx
	 * * Math.cos(Math.toRadians(-heading)) - rawy *
	 * Math.sin(Math.toRadians(-heading)); double y = rawx *
	 * Math.sin(Math.toRadians(-heading)) + rawy *
	 * Math.cos(Math.toRadians(-heading));
	 * 
	 * if ((x != 0 || y != 0) && (gamepad1.left_trigger > 0.05 ||
	 * gamepad1.right_trigger > 0.05)) { double n = ((x + y) / 2.0); // n is the
	 * power of the motors in the +x +y direction double m = -((x - y) / 2.0); // m
	 * is the power of the motors in the +x -y direction motor1.setPower(m);
	 * motor2.setPower(n); if (gamepad1.left_trigger > 0.05) { motor3.setPower(-1 *
	 * gamepad1.left_trigger); motor4.setPower(gamepad1.left_trigger); return; } if
	 * (gamepad1.right_trigger > 0.05) { motor3.setPower(gamepad1.right_trigger);
	 * motor4.setPower(-1 * gamepad1.right_trigger); return; } } else if (x != 0 ||
	 * y != 0) { double n = ((x + y) / 2.0); // n is the power of the motors in the
	 * +x +y direction double m = -((x - y) / 2.0); // m is the power of the motors
	 * in the +x -y direction motor1.setPower(m); motor2.setPower(n);
	 * motor3.setPower(m); motor4.setPower(n); } else if (gamepad1.left_trigger >
	 * 0.05) { motor1.setPower(gamepad1.left_trigger); motor2.setPower(-1 *
	 * gamepad1.left_trigger); motor3.setPower(-1 * gamepad1.left_trigger);
	 * motor4.setPower(gamepad1.left_trigger); return; } else if
	 * (gamepad1.right_trigger > 0.05) { motor1.setPower(-1 *
	 * gamepad1.right_trigger); motor2.setPower(gamepad1.right_trigger);
	 * motor3.setPower(gamepad1.right_trigger); motor4.setPower(-1 *
	 * gamepad1.right_trigger); return; } // if (gamepad1.a) { // pos -= .01; // }
	 * // if (gamepad1.b) { // pos += .01; // }
	 */

}