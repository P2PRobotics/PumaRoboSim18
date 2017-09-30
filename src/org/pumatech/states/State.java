package org.pumatech.states;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

// Top level class representing a state such as the main menu, autonomous, teleop, etc.
public abstract class State {
	// Signifies that this state is finished executing and to go the the previous game state
	private boolean done;
	// Tells container (Simulator) what state to enter next
	private State nextState;
	
	// Map of key codes to booleans representing whether a key is pressed
	protected boolean[] keyDown;
	
	public State() {
		done = false;
		nextState = null;
		
		// Initialize keyDown with 256 keys that can be pressed or not pressed (true and false)
		// There are some keyCodes outside the 256 range, which will be ignored
		keyDown = new boolean[256];
	}

	// Abstract methods to be implemented by concrete states
	public abstract void draw(Graphics2D g, Dimension d);
	public abstract void update(double dt);

	// Returning anything non null will cause returned state to be pushed on the state stack
	public State nextState() {
		return nextState;
	}
	
	protected void setNextState(State nextState) {
		this.nextState = nextState;
	}
	
	public boolean isDone() {
		return done;
	}
	
	protected void exit() {
		done = true;
	}
	
	// Event handler for when a key is pressed, sets keyDown to true at appropriate index
	public void keyPressed(int k) {
		if (k < keyDown.length)
			keyDown[k] = true;
	}

	// Event handler for when a key is released, sets keyDown to false at appropriate index
	public void keyReleased(int k) {
		if (k < keyDown.length)
			keyDown[k] = false;
	}
	
	// Event handler for mouse scroll wheel movement. No default behavior.
	public void mouseWheelMoved(MouseWheelEvent e) { }
	
	// Other unused event handlers
	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) { }
	
	public void mouseReleased(MouseEvent e) {}

	public void mouseDragged(MouseEvent e) {}

	public void mouseMoved(MouseEvent e) {}
	
}
