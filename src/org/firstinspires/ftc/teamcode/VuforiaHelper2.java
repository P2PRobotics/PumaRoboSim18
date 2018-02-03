package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class VuforiaHelper2 {
    private int keyColumn;

    public VuforiaHelper2(HardwareMap hardwareMap) {
    	
    }

    public void start() {
    	keyColumn = (int) (3 * Math.random() + 1);
    }

    public void loop() { }

    public int getKeyColumn() {
        return keyColumn;
    }
}