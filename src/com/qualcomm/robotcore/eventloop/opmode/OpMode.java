package com.qualcomm.robotcore.eventloop.opmode;

import org.firstinspires.ftc.robotcore.external.BasicTelemetry;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public abstract class OpMode {

	protected Gamepad gamepad1, gamepad2;
	protected HardwareMap hardwareMap;
	protected Telemetry telemetry;
	protected double time;
	
	public abstract void init();
	public abstract void loop();
	public void start() { }
	public void stop() { }
	public void init_loop() {
		time = System.currentTimeMillis() / 1000.0;
	}
	
	public void setup(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
		this.hardwareMap = hardwareMap;
		telemetry = new BasicTelemetry();
		this.gamepad1 = gamepad1;
		this.gamepad2 = gamepad2;
	}
	
	public void updateTelemetry() {
		telemetry.update();
	}

	public void resetStartTime() {
		
	}
	
	public double getRuntime() {
		return 0;
	}
	
	public void setGamepad1(Gamepad gamepad1) {
		this.gamepad1 = gamepad1;
	}
	
	public void setGamepad2(Gamepad gamepad2) {
		this.gamepad2 = gamepad2;
	}
}
