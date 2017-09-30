package org.pumatech.simulator;

// Entry class, contains only main method
public class Main {

	public static void main(String[] args) {
		// Start simulator in a new Thread
		new Thread(new Simulator()).start(); 
	}

}  
