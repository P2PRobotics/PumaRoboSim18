package org.firstinspires.ftc.teamcode;

public enum Movements {
	FORWARD(1), BACKWARD(-1);

    private final int value;

    Movements(final int newValue) {
        value = newValue;
    }

    public int getValue() { 
    	return value; 
    }
}

