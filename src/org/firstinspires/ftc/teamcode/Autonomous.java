package org.firstinspires.ftc.teamcode;

import org.firstinsires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinsires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinsires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinsires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinsires.ftc.robotcore.external.navigation.Orientation;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.ModernRoboticsI2cRangeSensor;

public class Autonomous  extends OpMode {

    public static final String TAG = "Vuforia VuMark Sample";
    public static final String VUFORIA_KEY = "AdrNx7L/////AAAAGWscSRJwJ0For7OwbugY3Fd0I3f+mAuH+BAHpz7UBuNJnU+QudRFM8gzxBh+mZcuiwi2TStZTxHuDQvVJHER5zuUmh7X6dr/7uEnPy+OBd72HjBc2gM+w7DNmcBhY8SmEgLRlzhI4dRCAmjADeVQd9c/vTTyqWSYdy7F2fE2eQbSoXKyKN1uFV6P6lN3NlHSazLOaniTLpAQQlbOwb9S2KxXy7PQK1ZBAmWMdHb5jwAXaqz+HXMPBez6/7behYzk1eu4a/0hFZ6jWo9Khoc9MRrhmCac0SCzmNRjfD8Y9Q61EtWvmo+WlbyzFJUNsZbND80BXAKaOWXvCAsdCo58qGtmVr36Bau5iljOe5HBbvov";

    private DcMotor motor1;
    private DcMotor motor2;
    private DcMotor motor3;
    private DcMotor motor4;

    private State state;

    int objectCount;
    boolean seeColumn = false;

    //Changes
    private int keyColumn = 3; //Number 1-3
    boolean redTeam = true;
    boolean topField = false;
    double scc;
    int timeThrough = 0;

    ModernRoboticsI2cRangeSensor rangeSensor;

    private OrientationSensor orientationSensor;

    public void init() {
        motor1 = hardwareMap.dcMotor.get("w1");
        motor2 = hardwareMap.dcMotor.get("w2");
        motor3 = hardwareMap.dcMotor.get("w3");
        motor4 = hardwareMap.dcMotor.get("w4");

        //motor1.setDirection(DcMotorSimple.Direction.REVERSE);
        //motor2.setDirection(DcMotorSimple.Direction.REVERSE);

        rangeSensor = hardwareMap.range.get("sensor_range");

        orientationSensor = new OrientationSensor(hardwareMap);

        state = State.START;

        telemetry.addData(">", "Press Play to start");
        telemetry.update();
    }

    public void loop() {

            

        double heading = orientationSensor.getOrientation();

        double distance = rangeSensor.getDistance(DistanceUnit.CM);

        if (distance > 200 || distance == 0) {
            return;
        }
        
        switch (state) {

            case START:
                if (topField == true) {
                    state = State.TOP_FIELD;
                }
                else if (topField == false) {
                    state = State.BOTTOM_FIELD;
                }
                break;
            case COLUMN_COUNTING:
                //Oriented with sensor facing Cryptobox
                if (redTeam == true) {
                    move(-1,0,0);
                }
                if (redTeam == false) {
                    move(1,0,0);
                }
                if (distance > 200 || distance == 0) {
                    return;
                }
                if (distance < 32 && seeColumn == false) {
                    seeColumn = true;
                }
                if (distance > 34 && seeColumn == true) {
                    seeColumn = false;
                    objectCount++;
                }
                if(keyColumn == 1){
                    if (objectCount == 1) {
                        state=State.MOVE_TIME_START;
                    }
                    else if(redTeam==true){
                        move(-0.01,0,0);
                    }
                    else {
                        move(0.01,0,0);
                    }
                }
                if(keyColumn == 2){
                    if (objectCount == 2) {
                        state=State.MOVE_TIME_START;
                    }
                    else if(redTeam==true){
                        move(-0.01,0,0);
                    }
                    else {
                        move (0.01,0,0);
                    }
                }
                if(keyColumn == 3){
                    if (objectCount == 1) {
                        state=State.MOVE_TIME_START;
                    }
                    else if(redTeam==true){
                        move(-0.01,0,0);
                    }
                    else {
                        move (0.01,0,0);
                    }
                }
                telemetry.addData("Current distance is ", distance);
                telemetry.addData("Objects passed: ", objectCount);
                if(keyColumn==objectCount){
                    state=State.MOVE_TIME_START;
                }
                break;
            case STOP:
            	move(0, 0, 0);
            break;
            case MOVE_TIME_START:
            	scc = time;
            	timeThrough += 1;
            	state = State.MOVE_TIME;
            break;
            case MOVE_TIME:
            	if (keyColumn == 3) {
            		if (time < scc + 5) {
            			move (0.01,0,0);
            		}
            		else if (time >= scc + 5) {
            			scc = time;
            			if (time < scc + 2) {
            				move (0,0.01,0);
            			}
            			else {
            				state = State.STOP;
            			}
            		}
            	}
            	else {
            		if (time < scc + 2) {
            			move (-0.01,0,0);
            		}
            		else if (time >= scc + 2) {
            			if (time < scc + 2) {
            				move (0,0.01,0);
            			}
            			else {
            				state = State.STOP;
            			}
            		}
            	}
            	break;
            case TOP_FIELD:
                    if (redTeam == true) {
                        //if (vuMark == RelicRecoveryVuMark.CENTER || vuMark == RelicRecoveryVuMark.LEFT || vuMark == RelicRecoveryVuMark.RIGHT) {
                            move(0,-1,0);
                        if (distance < 20) {
                            move(0, 0, 0);
                            //state = State.COLUMN_COUNTING;
                            state = State.STOP;
                        }
                        //}
                    } else if (redTeam == false) {
                        //if (vuMark == RelicRecoveryVuMark.CENTER || vuMark == RelicRecoveryVuMark.LEFT || vuMark == RelicRecoveryVuMark.RIGHT) {
                            move(0,1,0);
                            if (distance < 20) {
                                move(0, 0, 0);
                                //state = State.COLUMN_COUNTING;
                                state = State.STOP;
                            }
                        //}
                    }
                break;
            case BOTTOM_FIELD:
                //if (vuMark == RelicRecoveryVuMark.CENTER || vuMark == RelicRecoveryVuMark.LEFT || vuMark == RelicRecoveryVuMark.RIGHT) {
            		if (distance > 40) {
            			move(0, 0.01, 0);
            		}
            		else if (distance < 40) {
                        state = State.COLUMN_COUNTING;
                    }
                //}
                break;
        }
        telemetry.addData("heading and distance is: ", heading + " , " + distance);
        telemetry.addData("State", state);
        
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
