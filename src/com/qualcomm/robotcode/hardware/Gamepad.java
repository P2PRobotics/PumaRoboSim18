package com.qualcomm.robotcode.hardware;

import net.java.games.input.Component;
import net.java.games.input.Controller;

public class Gamepad {

	public boolean a, b;
	public boolean back;
	public boolean dpad_down, dpad_left, dpad_right, dpad_up;
	public boolean guide;
	public boolean left_bumper, right_bumper;
	public float left_trigger, right_trigger;
	protected float dpadThreshold;
	protected float joystickDeadzone;

	private Controller controller;
	private double[] values;

	public Gamepad(Controller controller) {
		this.controller = controller;
	}

	public void update(double dt) {
		if (controller != null) {
			controller.poll();

			Component[] components = controller.getComponents();
			if (values == null) {
				values = new double[components.length];
			}
			for (int i = 0; i < components.length; i++) {
				if (Math.abs(components[i].getPollData() - values[i]) > .3) {
					values[i] = components[i].getPollData();
					System.out.println(components[i].getName() + " " + i + " " + values[i]);
				}
			}
		}
	}

}
