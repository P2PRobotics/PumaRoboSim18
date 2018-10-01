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
	
	
	public int idx_a, idx_b, idx_x, idx_y;
	public int idx_back;
	public int idx_guide;
	public int idx_left_bumper, idx_right_bumper;
	public int idx_left_trigger, idx_right_trigger;
	public int idx_left_stick_x;
	public int idx_left_stick_y;
	public int idx_right_stick_x;
	public int idx_right_stick_y;
	public int idx_dpad;
	

	private Controller controller;

	public Gamepad(Controller controller) {
		this.controller = controller;
		if (controller == null)
			return;
		Component[] components = controller.getComponents();
		for (int i = 0; i<components.length; i++) {
			if (components[i].getName().equals("Button 0")) {
				idx_a = i;
			} else if (components[i].getName().equals("Button 1")) {
				idx_b = i;
			} else if (components[i].getName().equals("Button 2")) {
				idx_x = i;
			} else if (components[i].getName().equals("Button 3")) {
				idx_y = i;
			} else if (components[i].getName().equals("Button 4")) {
				idx_left_bumper = i;
			} else if (components[i].getName().equals("Button 5")) {
				idx_right_bumper = i;
			} else if (components[i].getName().equals("X Axis")) {
				idx_left_stick_x = i;
			} else if (components[i].getName().equals("Y Axis")) {
				idx_left_stick_y = i;
			} else if (components[i].getName().equals("X Rotation")) {
				idx_right_stick_x = i;
			} else if (components[i].getName().equals("Y Rotation")) {
				idx_right_stick_y = i;
				//The controller object does not have trigger components which is wack - CR
			} else if (components[i].getName().equals("Button none")) {
				idx_left_trigger = i;
			} else if (components[i].getName().equals("Button none")) {
				idx_right_trigger = i;
				// The dpad just maps to Hat Switch
			} else if (components[i].getName().equals("Hat Swtich")) {
				idx_dpad = i;
			}
		}
		
	}
	

	public void update(double dt) {
		if (controller != null) {
			controller.poll();

			Component[] components = controller.getComponents();
			System.out.println(components);

			// TRIGGERED
//			double trigger = components[i].getPollData();
//			if (trigger < .01 && trigger > -.01) {
//				trigger = 0;
//			}
//			if (trigger >= 0) {
//				left_trigger = (float) trigger;
//			}
//			if (trigger <= 0) {
//				right_trigger = (float) -trigger;
//			}

			//I am mapping the triggers to the values of the bumpers, see comment where the mapping happens. - CR
			right_trigger = (components[idx_left_bumper].getPollData() + 1) / 2;
			left_trigger = (components[idx_right_bumper].getPollData() + 1) / 2;

			// Buttons
			a = components[idx_a].getPollData() == 1;
			b = components[idx_b].getPollData() == 1;
			x = components[idx_x].getPollData() == 1;
			y = components[idx_y].getPollData() == 1;
			// if(x) {
			// System.exit(0) ;
			// Joysticks
			
			
			left_stick_x = components[idx_left_stick_x].getPollData();
			left_stick_y = components[idx_left_stick_y].getPollData();
			right_stick_x = components[idx_right_stick_x].getPollData();
			right_stick_y = components[idx_right_stick_y].getPollData();


			
			// D-Pad
			// dPad is really weird in which it doesn't really have a "index", this for each loop solves that particular problem
			double dpad = components[idx_dpad].getPollData();
			for (Component component : components) {
				if (component.getName().equals("Hat Switch")) {
					dpad = component.getPollData();
				}
			}
			if (dpad > .75) {
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

			// Bumpers, I commented this out because I changed the triggers to the bumpers - CR
			//left_bumper = components[idx_left_bumper].getPollData() == 1;
			//right_bumper = components[idx_right_bumper].getPollData() == 1;
		}
	}

	public boolean equals(Gamepad g) {
		return (g == null && controller == null) || g.controller == controller;
	}
}