package org.firstinspires.ftc.teamcode.hardware.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.RunToPositionMotorUtil;

public class LiftSubsystem extends RunToPositionMotorUtil {

    public HardwareMap hwMap = null; // Local access to hardware map

    public DcMotorEx lift = null; // Lift motor

    private final double liftMaxHeight = 40; // Max height of lift

    private final double liftMinimumHeight = 4.5; // Minimum height of lift

    public final double lowInches = 15.5;
    public final double mediumInches = 26.5;
    public final double highInches = 38.4;
    public final double groundInches = 5.25;
    public final double resetInches = 11;
    public final double pickupInches = 4.5;

    public final double clawOffsetInches = 4.5;

    public final double pulsesPerInch = 113.813153388;

    private final float liftSpeed = 1;

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
        switch(height) {
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
//        if (inBounds()) {
//
//        }
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setPower(power);
    }

    private void setActiveLow() {
        motorToPosition(lift, liftSpeed, inchesToPulses(lowInches));
        activeHeight = Level.LOW;
    }

    private void setActiveMedium() {
        motorToPosition(lift, liftSpeed, inchesToPulses(mediumInches));
        activeHeight = Level.MEDIUM;
    }

    private void setActiveHigh() {
        motorToPosition(lift, liftSpeed, inchesToPulses(highInches));
        activeHeight = Level.HIGH;
    }

    private void setActiveReset() {
        motorToPosition(lift, liftSpeed, inchesToPulses(resetInches));
        activeHeight = Level.RESET;
    }

    private void setActivePickup() {
        motorToPosition(lift, liftSpeed, inchesToPulses(pickupInches));
        activeHeight = Level.PICKUP;
    }

    private void setActiveGround() {
        motorToPosition(lift, liftSpeed, inchesToPulses(groundInches));
        activeHeight = Level.GROUND;
    }

    public void holdPos() {
        activeHeight = Level.CUSTOM;
        setLastHeight();
        motorToPosition(lift, liftSpeed, lastHeight);
    }

    public void setLastHeightClamped() {
        lastHeight = Math.min(lift.getCurrentPosition(), inchesToPulses(liftMaxHeight)); // Clamping functionality to keep the lift holding withing defined range
    }

    public void setLastHeight() {
        lastHeight = lift.getCurrentPosition();
    }

    public void setHeightAuto(double ticks) {
        lift.setTargetPosition(inchesToPulses(ticks));
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(1);
    }

    public int inchesToPulses(double inputInches) {
        return (int) ((inputInches - clawOffsetInches) * pulsesPerInch);
    }

    public int pulsesToInches(double inputPulses) {
        return (int) ((inputPulses/pulsesPerInch) + clawOffsetInches);
    }

    public boolean inBounds() {
        //  && lift.getCurrentPosition() >= inchesToPulses(liftMinimumHeight)
        return (lift.getCurrentPosition() <= inchesToPulses(liftMaxHeight));
    }

    public void setEncoderZero() {
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
