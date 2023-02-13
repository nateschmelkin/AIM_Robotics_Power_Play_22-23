package org.firstinspires.ftc.teamcode.hardware.subsystems;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class CSController {


    public HardwareMap hwMap = null; // Local access to hardware map

    public ColorSensor csConeDetect = null;
    public ColorSensor csConeConfirm = null;

    public int[] csVals; // Array to house cs values

    public boolean isRed; // boolean to toggle red versus blue detection

    public int csMinimum = 325; // Minimum value cs must read to trigger true reading


    // Constructor that sets the red/blue based on input
    public CSController(boolean isRedTeam) {
        isRed = isRedTeam;
    }

    // initClaw initializes the servos and sets their directions
    public void initCSController(HardwareMap ahwMap) {
        hwMap = ahwMap;

        csConeDetect = hwMap.get(ColorSensor.class, "cs1");
        csConeConfirm = hwMap.get(ColorSensor.class, "cs2");
        setCSModes(isRed);
    }

    public void setCSModes(boolean isRed) {
        csVals[0] = csConeDetect.blue();
        csVals[1] = csConeConfirm.blue();

        if (isRed) {
            csVals[0] = csConeDetect.red();
            csVals[1] = csConeConfirm.red();
        }
    }

    // checkCone checks the specified color sensor for a greater than threshold value
    public boolean checkCone(int cs) {
        if (csVals[cs] > csMinimum) {
            return true;
        } else {
            return false;
        }
    }
}
