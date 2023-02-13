package org.firstinspires.ftc.teamcode.hardware.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.RunToPositionMotorUtil;

public class LiftSubsystem extends RunToPositionMotorUtil {

    public HardwareMap hwMap = null; // Local access to hardware map

    public DcMotorEx lift = null; // Lift motor

    private int liftMaxHeight = 4350; // Max height of lift

    private int liftMinimumHeight = 0; // Minimum height of lift

    public final int lowTicks = 1200;
    public final int mediumTicks = 2500;
    public final int highTicks = 3920;
    public final int groundTicks = 250;
    public final int resetTicks = 500;
    public final int pickupTicks = 0;

    private float liftSpeed = 1;
    private int lastHeight = 0;

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
        //BALLS -- Will
    }

    // Variable to access current lift height for arm automation
    public Level activeHeight = Level.RESET;


    // initLift initializes the lift motor and prepares its different behaviors
    public void initLift(HardwareMap ahwMap, String name) {
        hwMap = ahwMap;
        lift = hwMap.get(DcMotorEx.class, name);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
//        if (lift.getCurrentPosition() <= liftMaxHeight && lift.getCurrentPosition() >= liftMinimumHeight) {
//            lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            lift.setPower(power);
//            setLastHeight();
//        }
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setPower(power);
    }

    private void setActiveLow() {
        motorToPosition(lift, liftSpeed, lowTicks);
        activeHeight = Level.LOW;
    }

    private void setActiveMedium() {
        motorToPosition(lift, liftSpeed, mediumTicks);
        activeHeight = Level.MEDIUM;
    }

    private void setActiveHigh() {
        motorToPosition(lift, liftSpeed, highTicks);
        activeHeight = Level.HIGH;
    }

    private void setActiveReset() {
        motorToPosition(lift, liftSpeed, resetTicks);
        activeHeight = Level.RESET;
    }

    private void setActivePickup() {
        motorToPosition(lift, liftSpeed, pickupTicks);
        activeHeight = Level.PICKUP;
    }

    private void setActiveGround() {
        motorToPosition(lift, liftSpeed, groundTicks);
        activeHeight = Level.GROUND;
    }

    public void holdPos() {
        activeHeight = Level.CUSTOM;
        setLastHeight();
        motorToPosition(lift, liftSpeed, lastHeight);
    }

    public void setLastHeightClamped() {
        lastHeight = Math.max(liftMinimumHeight, Math.min(lift.getCurrentPosition(), liftMaxHeight)); // Clamping functionality to keep the lift holding withing defined range
    }

    public void setLastHeight() {
        lastHeight = lift.getCurrentPosition();
    }

    public void setHeightAuto(int ticks) {
        lift.setTargetPosition(ticks);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(1);
    }
}
