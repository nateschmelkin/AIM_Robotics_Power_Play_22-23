package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.util.RunToPositionMotorUtil;

public class SpinnerSubsystem extends RunToPositionMotorUtil {

    public HardwareMap hwMap = null; // Local access to hardware map

    public DcMotorEx spinner = null; // Spinner

    public ColorSensor cs1 = null; // 1st Color Sensor
    public ColorSensor cs2 = null; // 2nd Color Sensor

    public int[] csVals; // Array to house cs values

    public boolean isRed; // boolean to toggle red versus blue detection

    // An enumerator with values for all of the preset
    // sides that the spinner goes to.
    public enum Side{
        FORWARD,
        BACK
    }

    public Side activeSide = Side.FORWARD; // Variable to track active side and it defaults to FORWARD

    public int csMinimum = 325; // Minimum value cs must read to trigger true reading

    // Constructor that sets the red/blue based on input
    public SpinnerSubsystem(boolean isRedTeam) {
        isRed = isRedTeam;
    }

    // initSpinner initializes the spinner and its color sensors
    public void initSpinner(HardwareMap ahwMap) {
        hwMap = ahwMap;

        spinner = hwMap.get(DcMotorEx.class, "spinner");
        spinner.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        spinner.setDirection(DcMotorSimple.Direction.REVERSE);

        cs1 = hwMap.get(ColorSensor.class, "cs1");
        cs2 = hwMap.get(ColorSensor.class, "cs2");

//        limitSwitch = hwMap.get(TouchSensor.class, "limitSwitch");
        setCSModes(isRed);
    }

    // setSide takes an Enum input and then uses a switch statement to match it to a motor position that corresponds to a side
    public void setSide(SpinnerSubsystem.Side side, double speed) {
        switch(side) {
            case FORWARD:
                motorToPosition(spinner, speed, 0);
                break;
            case BACK:
                motorToPosition(spinner, speed, 1400);
                break;
        }
    }

    // checkSides checks all color sensors for a cone and then sets the active side accordingly
    public boolean checkSides() {
        if (checkCone(1)) {
            activeSide = Side.FORWARD;
            return true;
        } else if (checkCone(2)) {
            activeSide = Side.BACK;
            return true;
        } else {
            return false;
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

    // setCSModes sets the color sensors to either detect red or blue
    public void setCSModes(boolean isRed) {
        csVals[0] = cs1.blue();
        csVals[1] = cs2.blue();

        if (isRed) {
            csVals[0] = cs1.red();
            csVals[1] = cs2.red();
        }
    }
}
