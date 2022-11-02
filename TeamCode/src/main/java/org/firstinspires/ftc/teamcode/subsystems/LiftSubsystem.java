package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
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
    }

    public void setHeight(Level height) {
        Level targetHeight = height;
        switch(targetHeight) {
            case LOW:
                motorToPosition(lift, 1, 10);
                break;
            case MEDIUM:
                motorToPosition(lift, 1, 30);
                break;
            case HIGH:
                motorToPosition(lift, 1, 50);
                break;
            case RESET:
                motorToPosition(lift, 1, 40);
                break;
            case PICKUP:
                motorToPosition(lift, 1, 0);
                break;
        }
    }

}
