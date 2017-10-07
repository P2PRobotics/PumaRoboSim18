package com.qualcomm.robotcore.hardware;

import net.java.games.input.Component;
import net.java.games.input.Controller;
// Index of each button in the controller's component array:
// 0 Y axis of left joystick
// 1 X axis of left joystick
// 2 Y axis (rotation) of right joystick
// 3 X axis (rotation) of right joystick
// 4 Left trigger Z axis = positive; right trigger Z axis = negative
// 5 A button
// 6 B button
// 7 X button
// 8 Y button
// 9 Left Bumper button
// 10 Right Bumper button
// 11 Back button
// 12 Start button
// 13 Push down on left joystick button
// 14 Push down on right joystick button
// 15 D-Pad; up - 0.25; left - 1.0; right - 0.5; down - 0.75
public class Gamepad {

	public boolean a, b, x, y;
	public boolean back;
	public boolean dpad_down, dpad_left, dpad_right, dpad_up;
	public boolean guide;
	public boolean left_bumper, right_bumper;
	public float left_trigger, right_trigger;
	protected float dpadThreshold;
	protected float joystickDeadzone;
	public float left_stick_x;
	public float left_stick_y;
	public float right_stick_x;
	public float right_stick_y;

	private Controller controller;

	public Gamepad(Controller controller) {
		this.controller = controller;
	}

	public void update(double dt) {
		if (controller != null) {
			controller.poll();

			Component[] components = controller.getComponents();

			// TRIGGERED
			double trigger = components[4].getPollData();
			if (trigger > 0) {
				left_trigger = (float) trigger;
			}
			if (trigger < 0) {
				right_trigger = (float) -trigger;
			}

			// Buttons
			a = components[5].getPollData() == 1;
			b = components[6].getPollData() == 1;
			x = components[7].getPollData() == 1;
			y = components[8].getPollData() == 1;

			// Joysticks
			left_stick_x = components[0].getPollData();
			left_stick_y = components[1].getPollData();
			right_stick_x = components[2].getPollData();
			right_stick_y = components[3].getPollData();

			// D-Pad
			double dpad = components[15].getPollData();
			if (dpad < .25 && dpad > .75) {
				dpad_left = true;
			} else {
				dpad_left = false;
			}
			if (dpad < .5 && dpad > .0) {
				dpad_up = true;
			} else {
				dpad_up = false;
			}
			if (dpad < .75 && dpad > .25) {
				dpad_right = true;
			} else {
				dpad_right = false;
			}
			if (dpad < .0 && dpad > .5) {
				dpad_down = true;
			} else {
				dpad_down = false;
			}

			// Bumpers
			left_bumper = components[9].getPollData() == 1;
			right_bumper = components[10].getPollData() == 1;
		}
	}

	public boolean equals(Gamepad g) {
		return (g == null && controller == null) || g.controller == controller;
	}
}