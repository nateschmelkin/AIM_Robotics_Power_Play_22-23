package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.Robot;

public class LiftSubsystem extends RunToPositionMotor{

    public HardwareMap hwMap = null;

    public DcMotorEx lift = null;

    public enum Level{
        LOW,
        MEDIUM,
        HIGH,
        GROUND,
        RESET,
        PICKUP
    }

    public Level activeHeight = Level.RESET;

    public void initLift(HardwareMap ahwMap) {
        hwMap = ahwMap;
        lift = hwMap.get(DcMotorEx.class, "lift");
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setHeight(Level height, double speed) {
        Level targetHeight = height;
        switch(targetHeight) {
            case LOW:
                motorToPosition(lift, speed, 1550);
                break;
            case MEDIUM:
                motorToPosition(lift, speed, 2650);
                break;
            case HIGH:
                motorToPosition(lift, speed, 3800);
                break;
            case RESET:
                motorToPosition(lift, speed, 1100);
                break;
            case PICKUP:
                motorToPosition(lift, speed, 0);
                break;
            case GROUND:
                motorToPosition(lift, speed, 300);
                break;
        }
    }

}
