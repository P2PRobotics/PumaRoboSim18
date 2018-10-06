package org.firstinspires.ftc.teamcode;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Autonomous", group = "AAAAAARP")
public class Autonomous extends OpMode implements RobotConstants {
	// Actuators
	private DcMotor motor1;
	private DcMotor motor2;
	private DcMotor motor3;
	private DcMotor motor4;
	private DcMotor motor5;
	private DcMotor motor6;

	// Servo
	private Servo USpivot;

	// Sensors
	private ModernRoboticsI2cRangeSensor rangeSensor;
	private OrientationSensor orientationSensor;
	private VuforiaHelper2 vuforia;

	// State machine
	private State state;
	private Stack<State> nextStates;

	// State machine variables
	private double delay;
	private double delayStart;

	// Autonomous variables
	private Stack<Integer> readings;
	private static final int FILTER_BUFFER_LENGTH = 3;
	private Queue<Integer> distanceFilter;

	private int objectCount;
	private int keyColumn;
	private double distance = 255;

	private double prevdistance;
	private double distBeforeIncrease;

	private int readingDistNum;
	private Stack<Integer> times = new Stack<Integer>();

	public static final double SPEED = 0.18;

	public void init() {
		motor1 = hardwareMap.dcMotor.get("w1");

		motor2 = hardwareMap.dcMotor.get("w2");

		motor3 = hardwareMap.dcMotor.get("w3");

		motor4 = hardwareMap.dcMotor.get("w4");

		motor5 = hardwareMap.dcMotor.get("w5");

		motor6 = hardwareMap.dcMotor.get("w6");

		rangeSensor = hardwareMap.range.get("range");

		orientationSensor = new OrientationSensor(hardwareMap);

		state = State.MOVE;
		nextStates = new Stack<State>();

		vuforia = new VuforiaHelper2(hardwareMap);

		USpivot = hardwareMap.servo.get("usp");
		USpivot.setPosition(1);

		readings = new Stack<Integer>();
		distanceFilter = new LinkedList<Integer>();
	}

	public void start() {
		vuforia.start();

		USpivot.setPosition(0.5);
	}

	@SuppressWarnings("incomplete-switch")
	public void loop() {
		if (keyColumn == 0) {
			vuforia.loop();
			keyColumn = vuforia.getKeyColumn();
		}
		double heading = orientationSensor.getOrientation();
		telemetry.addData("heading", heading);

		switch (state) {

		case MOVE:
			System.out.println("Distance: " + rangeSensor.getDistance(DistanceUnit.CM));
			move(0,0,0);
			if (rangeSensor.getDistance(DistanceUnit.CM) > 160) {
				move(0, Movements.FORWARD.getValue(), 0.5);
			}
			
			break;

//            case START:
//                delay = 1;
//                state = State.DELAY;
//                if (TOP_FIELD) {
//                    nextStates.push(State.TOP_FIELD);
//                } else {
//                    nextStates.push(State.BOTTOM_FIELD);
//                }
//                break;
//            case TOP_FIELD:
//                state = State.START_COLUMN_COUNTING;
//                break;
//            case BOTTOM_FIELD:
//                if (keyColumn == 0) {
//                    break;
//                }
//                move(0, SPEED + 0.1, 0);
//                delay = 1.8;
//                state = State.DELAY;
//                nextStates.push(State.BOTTOM_FIELD_DIST);
//                break;
//            case BOTTOM_FIELD_DIST:
//                move(0, SPEED + .1, 0);
//                if (distance < 38) {
//                    move(0, 0, 0);
//
//                    delay = 1;
//                    state = State.DELAY;
//                    nextStates.push(State.BOTTOM_FIELD_DIST_ADJUST);
//                    nextStates.push(State.GET_DISTANCE);
//                }
//                else {
//                    state = State.GET_DISTANCE;
//                    nextStates.push(State.BOTTOM_FIELD_DIST);
//                }
//                break;
//            case BOTTOM_FIELD_DIST_ADJUST:
//                if (distance < 33) {
//                    move (0, -SPEED, 0);
//                    state = State.GET_DISTANCE;
//                    nextStates.push(State.BOTTOM_FIELD_DIST_ADJUST);
//                } else if (distance > 36) {
//                    move (0, SPEED, 0);
//                    state = State.GET_DISTANCE;
//                    nextStates.push(State.BOTTOM_FIELD_DIST_ADJUST);
//                } else {
//                    move(0, 0, 0);
//                    state = State.DELAY;
//                    delay = 1;
//                    nextStates.push(State.BOTTOM_FIELD_ADJUST_2);
//                }
//                break;
//            case BOTTOM_FIELD_ADJUST_2:
//                if (distance < 33) {
//                    move (0, -SPEED, 0);
//                    state = State.GET_DISTANCE;
//                    nextStates.push(State.BOTTOM_FIELD_DIST_ADJUST);
//                } else if (distance > 36) {
//                    move (0, SPEED, 0);
//                    state = State.GET_DISTANCE;
//                    nextStates.push(State.BOTTOM_FIELD_DIST_ADJUST);
//                } else {
//                    move(0, 0, 0);
//                    state = State.DELAY;
//                    delay = 1;
//                    nextStates.push(State.START_COLUMN_COUNTING);
//                    nextStates.push(State.STRAIGHTEN);
//                }
//                break;
//            case STRAIGHTEN:
//                if (heading < -5) {
//                    move(0, 0, -.2);
//                } else if (heading > 5) {
//                    move(0, 0, .2);
//                } else {
//                    move(0, 0, 0);
//                    state = State.DELAY;
//                    delay = .3;
//                }
//                break;
//            case START_COLUMN_COUNTING:
//                move(0, 0, 0);
//                objectCount = 0;
//                USpivot.setPosition(.35);
//                state = State.DELAY;
//                delay = 2;
//                nextStates.push(State.COLUMN_COUNTING);
//                nextStates.push(State.GET_DISTANCE);
//                prevdistance = 0;
//                distanceFilter.clear();
//                while (distanceFilter.size() < FILTER_BUFFER_LENGTH)
//                    distanceFilter.add(255);
//                break;
//            case COLUMN_COUNTING:
//                USpivot.setPosition(.35);
//                int curdistance = (int) rangeSensor.getDistance(DistanceUnit.CM);
//                if (curdistance == 0 || curdistance > 100)
//                    break;
//                distanceFilter.add(curdistance);
//                distanceFilter.remove();
//                double bufferTotal = 0;
//                for (int dist : distanceFilter)
//                    bufferTotal += dist;
//                distance = bufferTotal / FILTER_BUFFER_LENGTH;
//                // Oriented with sensor facing Cryptobox
//                if (RED_TEAM) {
//                    move(-SPEED, SPEED * Math.tan(Math.toRadians(7)), 0); // 7 degree fudge factor
//                } else {
//                    move(SPEED, SPEED * Math.tan(Math.toRadians(7)), 0);
//                }
//                readings.push((int) distance);
//                if (distance - prevdistance <= 1 && prevdistance != 0) {
//                    if (prevdistance > distBeforeIncrease + 4 && distBeforeIncrease != 0)
//                        objectCount++;
//                    distBeforeIncrease = distance;
//                }
//                prevdistance = distance;
//
//                if (keyColumn == objectCount) {
//                    /*if (RED_TEAM) {
//                        move(SPEED, 0, 0);
//                    } else {
//                        move(-SPEED, 0, 0);
//                    }
//                    */
//                    move(0, 0, 0);
//                    delay = 1;
//                    nextStates.push(State.PUSH_GLYPH_IN_ADJUST);
//                    state = State.DELAY;
//                    break;
//                }
//                //state = State.GET_DISTANCE;
//                //nextStates.push(State.COLUMN_COUNTING);
//                break;
//            case PUSH_GLYPH_IN_ADJUST:
//                if (heading < -20) {
//                    move(0, 0, -.2);
//                } else if (heading > -13 + (keyColumn == 3 ? 3 : 0)) {
//                    move(0, 0, .2);
//                } else {
//                    move(0, 0, 0);
//                    state = State.DELAY;
//                    delay = 1;
//                    nextStates.push(State.PUSH_GLYPH_IN_LOWER);
//                }
//                break;
//            case PUSH_GLYPH_IN_LOWER:
//                delay = 2;
//                state = State.DELAY;
//                nextStates.push(State.PUSH_GLYPH_IN);
//                break;
//            case PUSH_GLYPH_IN:
//                // Once key column has been found, the robot oscillates between PUSH_GLYPH_IN and PUSH_GLYPH_IN_ADJUST to ensure glyph gets into key column.
//                move(0, SPEED, 0);
//
//                delay = 1.2;
//                nextStates.push(State.PUSH_GLYPH_IN_RELEASE);
//                state = State.DELAY;
//                break;
//            case PUSH_GLYPH_IN_RELEASE:
//                move(0, 0, 0);
//                delay = .8;
//                state = State.DELAY;
//                nextStates.push(State.BACK_OFF_GLYPH);
//                break;
//            case BACK_OFF_GLYPH:
//                move(0, -SPEED, 0);
//                delay = 1;
//                state = State.DELAY;
//                nextStates.push(State.STOP);
//                break;
//            case DELAY:
//                delayStart = time;
//                state = State.DELAY_LOOP;
//                break;
//            case DELAY_LOOP:
//                telemetry.addData("delaying", nextStates.peek());
//                if (time > delayStart + delay)
//                    state = nextStates.pop();
//                break;
//            case STOP:
//                telemetry.addData("readings", readings);
//                telemetry.addData("timings", times);
//                move(0, 0, 0);
//                break;
//            case GET_DISTANCE:
//                distance = 255;
//                readingDistNum = 2;
//                state = State.GET_DISTANCE_LOOP;
//                break;
//            case GET_DISTANCE_LOOP:
//                double currentdist= rangeSensor.getDistance(DistanceUnit.CM);
//                if(currentdist > 200 || currentdist == 0){
//                    return;
//                }
//                readingDistNum--;
//                if(currentdist < distance ) {
//                    distance = currentdist;
//                }
//                if (readingDistNum == 0){
//                    state = nextStates.pop();
//                }
//                break;
		}

		telemetry.addData("State", state);
		telemetry.addData("Current distance is ", distance);
		telemetry.addData("Objects passed: ", objectCount);
		telemetry.addData("Key column", keyColumn);
	}

	// double x = gamepad1.right_stick_x;
	// double y = gamepad1.right_stick_y;
	// double dir = gamepad1.right_trigger or gamepad1.left_trigger;
	public void move(double x, double y, double dir) {
		if (dir != 0.5) { // Rotating
			motor1.setPower(dir);
			motor2.setPower(-dir);
			motor3.setPower(-dir);
			motor4.setPower(dir);
//			motor5.setPower(dir);
//			motor6.setPower(-dir);
			// Translating
		} else {
			motor1.setPower(y);
			motor2.setPower(y);
			motor3.setPower(y);
			motor4.setPower(y);
//			motor5.setPower(y);
//			motor6.setPower(y);
		}

//            else { // Translating
//            double n = ((x + y) / Math.sqrt(2.0)); // n is the power of the motors in the +x +y direction
//            double m = ((x - y) / Math.sqrt(2.0)); // m is the power of the motors in the +x -y direction
//            motor1.setPower(m);
//            motor2.setPower(n);
//            motor3.setPower(m);
//            motor4.setPower(n);
//            motor5.setPower(m);
//            motor6.setPower(n);
//        }
	}
}