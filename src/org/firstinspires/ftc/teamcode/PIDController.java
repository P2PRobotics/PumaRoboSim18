package org.firstinspires.ftc.teamcode;

public class PIDController {
	
	private int goal;
	private double lastposition;
	private double position;
	private double pterm;
	private double iterm;
	private double dterm;
	
	public double updatePterm() {
		pterm = goal-position;
		return pterm;
	}
	
	public double updateIterm(double dt) {
		double numInt = 0;
		numInt = dt*(goal-position) + numInt;
		iterm = numInt;
		return iterm;
	}
	
	public double updateDterm(double dt) {
		dterm = (lastposition-position)/dt;
		return dterm;
	}
	
	public double updatePID(double error, double position) {
		
		lastposition = position;
		position = pterm + iterm + dterm;
		return position;
		
	}
}
