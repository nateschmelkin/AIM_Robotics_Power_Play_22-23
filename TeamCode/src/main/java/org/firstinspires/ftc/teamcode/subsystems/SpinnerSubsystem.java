package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class SpinnerSubsystem extends RunToPositionMotor{

    public HardwareMap hwMap = null;

    public DcMotorEx spinner = null;

    public ColorSensor cs1 = null;
    public ColorSensor cs2 = null;
    public ColorSensor cs3 = null;
    public ColorSensor cs4 = null;

    private int cs1Val = 0;
    private int cs2Val = 0;
    private int cs3Val = 0;
    private int cs4Val = 0;


    public TouchSensor limitSwitch = null;

    enum Side{
        FORWARD,
        RIGHT,
        LEFT,
        BACK
    }

    public Side activeSide = Side.FORWARD;

    public int csMinimum = 1000;
    public int csCertainty = 500;

    public SpinnerSubsystem(boolean isRed) {
        setCSModes(isRed);
    }

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
        if (checkCone(cs1)) {
            activeSide = Side.FORWARD;
            return true;
        } else if (checkCone(cs2)) {
            activeSide = Side.RIGHT;
            return true;
        } else if (checkCone(cs3)) {
            activeSide = Side.BACK;
            return true;
        } else if (checkCone(cs4)) {
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

    public boolean checkCone(ColorSensor cs) {
        if (cs.red() > csMinimum && rangeOfExtremes(csCertainty)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean rangeOfExtremes(int certainty){

        int biggest = Math.max(cs1Val, Math.max(cs2Val, Math.max(cs3Val, cs4Val)));
        int min = Math.min(cs1Val, Math.min(cs2Val, Math.min(cs3Val, cs4Val)));
        if(biggest - min > certainty) return true;
        return false;
    }

    public void setCSModes(boolean isRed) {
        cs1Val = cs1.blue();
        cs2Val = cs2.blue();
        cs3Val = cs3.blue();
        cs4Val = cs4.blue();

        if(isRed){
            cs1Val = cs1.red();
            cs2Val = cs2.red();
            cs3Val = cs3.red();
            cs4Val = cs4.red();
        }
    }
}
