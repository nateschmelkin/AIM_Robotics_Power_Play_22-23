package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.RunToPositionMotorUtil;

public class LiftSubsystem extends RunToPositionMotorUtil {

    public HardwareMap hwMap = null; // Local access to hardware map

    public DcMotorEx lift = null; // Lift motor

    private int liftMaxHeight = 4000; // Max height of lift

    private int liftMinimumHeight = 0; // Minimum height of lift

    private float liftSpeed = 1;

    // An enumerator with values for all of the preset
    // heights that the lift goes to. In addition has a custom
    // state which allows lift height to be set manually.
    public enum Level{
        LOW,
        MEDIUM,
        HIGH,
        GROUND,
        RESET,
        PICKUP,
        CUSTOM
    }

    // Variable to access current lift height for arm automation
    public Level activeHeight = Level.RESET;


    // initLift initializes the lift motor and prepares its different behaviors
    public void initLift(HardwareMap ahwMap) {
        hwMap = ahwMap;
        lift = hwMap.get(DcMotorEx.class, "lift");
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        lift.setTargetPositionTolerance(10); TODO: EXPERIMENTAL
    }

    // setHeight takes in a Level and a speed and uses a switch statement to
    // tell the lift what height to go to
    public void setHeight(Level height) {
        Level targetHeight = height;
        switch(targetHeight) {
            case LOW:
                setActiveLow();
                break;
            case MEDIUM:
                setActiveMedium();
                break;
            case HIGH:
                setActiveHigh();
                break;
            case RESET:
                setActiveReset();
                break;
            case PICKUP:
                setActivePickup();
                break;
            case GROUND:
                setActiveGround();
                break;
        }
    }

    // userAdjustHeight takes in a power and sets the lift to that power
    // to manually adjust the height
    public void userAdjustHeight(double power) {
        if (lift.getCurrentPosition() >= liftMaxHeight || lift.getCurrentPosition() <= liftMinimumHeight) {
            lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lift.setPower(power);
        }
    }

    private void setActiveLow() {
        motorToPosition(lift, liftSpeed, 1550);
        activeHeight = Level.LOW;
    }

    private void setActiveMedium() {
        motorToPosition(lift, liftSpeed, 2650);
        activeHeight = Level.MEDIUM;
    }

    private void setActiveHigh() {
        motorToPosition(lift, liftSpeed, 3800);
        activeHeight = Level.HIGH;
    }

    private void setActiveReset() {
        motorToPosition(lift, liftSpeed, 950);
        activeHeight = Level.RESET;
    }

    private void setActivePickup() {
        motorToPosition(lift, liftSpeed, 0);
        activeHeight = Level.PICKUP;
    }

    private void setActiveGround() {
        motorToPosition(lift, liftSpeed, 300);
        activeHeight = Level.GROUND;
    }
}
