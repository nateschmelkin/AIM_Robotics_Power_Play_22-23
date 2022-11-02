package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.hardware.Robot;

public class SpinnerSubsystem extends RunToPositionMotor{

    public HardwareMap hwMap = null;

    public DcMotorEx spinner = null;

    public ColorSensor cs1 = null;
    public ColorSensor cs2 = null;
    public ColorSensor cs3 = null;
    public ColorSensor cs4 = null;

    public TouchSensor limitSwitch = null;

    enum Side{
        FORWARD,
        RIGHT,
        LEFT,
        BACK
    }

    public Side activeSide = Side.FORWARD;

    public void initSpinner(HardwareMap ahwMap) {
        hwMap = ahwMap;

        spinner = hwMap.get(DcMotorEx.class, "spinner");

        cs1 = hwMap.get(ColorSensor.class, "cs1");
        cs2 = hwMap.get(ColorSensor.class, "cs2");
        cs3 = hwMap.get(ColorSensor.class, "cs3");
        cs4 = hwMap.get(ColorSensor.class, "cs4");

        limitSwitch = hwMap.get(TouchSensor.class, "limitSwitch");

    }

    public void setSide(SpinnerSubsystem.Side side) {
        switch(side) {
            case FORWARD:
                motorToPosition(spinner, 1, 10);
                break;
            case RIGHT:
                motorToPosition(spinner, 1, 30);
                break;
            case LEFT:
                motorToPosition(spinner, 1, 50);
                break;
            case BACK:
                motorToPosition(spinner, 1, 40);
                break;
        }
    }

    public boolean checkSides() {
        if (checkRed(cs1, 1600)) {
            activeSide = Side.FORWARD;
            return true;
        } else if (checkRed(cs2, 1600)) {
            activeSide = Side.RIGHT;
            return true;
        } else if (checkRed(cs3, 1600)) {
            activeSide = Side.BACK;
            return true;
        } else if (checkRed(cs4, 1600)) {
            activeSide = Side.LEFT;
            return true;
        } else {
            return false;
        }
    }

    public void resetSpinner() { // TODO WORK ON FUNCTION AS IT IS SIMPLY USED TO INCREASE ACCURACY BUT NOT 100% NECESSARY
        if (!limitSwitch.isPressed()) {
            spinner.setPower(1); // TODO Check direction
        } else {
            spinner.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }

    public boolean checkRed(ColorSensor cs, int threshold) {
        if (cs.red() > threshold) {
            return true;
        } else {
            return false;
        }
    }
}
