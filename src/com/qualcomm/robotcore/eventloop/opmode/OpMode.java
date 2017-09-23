package com.qualcomm.robotcore.eventloop.opmode;

import org.firstinspires.ftc.robotcore.external.BasicTelemetry;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import com.qualcomm.robotcode.hardware.Gamepad;
import com.qualcomm.robotcode.hardware.HardwareMap;

public abstract class OpMode {

	protected Gamepad gamepad1, gamepad2;
	protected HardwareMap hardwareMap;
	protected Telemetry telemetry;
	protected double time;
	
	public abstract void init();
	public abstract void loop();
	public void start() { }
	public void stop() { }
	public void init_loop() { }
	
	public void setup(HardwareMap hardwareMap) {
		this.hardwareMap = hardwareMap;
		telemetry = new BasicTelemetry();
	}
	
	public void updateTelemetry() {
		telemetry.update();
	}

	public void resetStartTime() {
		
	}
	
	public double getRuntime() {
		return 0;
	}
	
}
