package org.firstinspires.ftc.teamcode.hardware.mechanismClasses;

import com.aimrobotics.aimlib.util.Mechanism;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.drive.GamepadConstants;

public class ScoringSystem extends Mechanism {

    private Claws claws = new Claws();
    private Lifts lifts = new Lifts();

    @Override
    public void init(HardwareMap hwMap) {
        claws.init(hwMap);
        lifts.init(hwMap);
        lifts.setMotionConstraints(Lifts.MAX_VEL, Lifts.MAX_ACCEL);
    }

    public enum frontLiftLevels {
        PICKUP,
        LOW,
        MEDIUM,
        HIGH,
        CUSTOM
    }

    public enum backLiftLevels {
        PICKUP,
        LOW,
        MEDIUM,
        HIGH,
        CUSTOM
    }

    public frontLiftLevels frontLiftLevel = frontLiftLevels.PICKUP;
    public backLiftLevels backLiftLevel = backLiftLevels.PICKUP;

    @Override
    public void loop(Gamepad gamepad) {

        lifts.updatePositions();

        switch (frontLiftLevel) {
            case PICKUP:
                lifts.extendToPickup(lifts.frontLift);
            case LOW:
                lifts.extendToLow(lifts.frontLift);
            case MEDIUM:
                lifts.extendToMedium(lifts.frontLift);
            case HIGH:
                lifts.extendToHigh(lifts.frontLift);
            case CUSTOM:
                lifts.setPower(lifts.frontLift, gamepad.left_stick_y);
        }

        switch (backLiftLevel) {
            case PICKUP:
                lifts.extendToPickup(lifts.backLift);
            case LOW:
                lifts.extendToLow(lifts.backLift);
            case MEDIUM:
                lifts.extendToMedium(lifts.backLift);
            case HIGH:
                lifts.extendToHigh(lifts.backLift);
            case CUSTOM:
                lifts.setPower(lifts.backLift, gamepad.right_stick_y);
        }

        if (gamepad.a) {
            frontLiftLevel = frontLiftLevels.PICKUP;
        } else if (gamepad.b) {
            frontLiftLevel = frontLiftLevels.LOW;
        } else if (gamepad.x) {
            frontLiftLevel = frontLiftLevels.MEDIUM;
        } else if (gamepad.y) {
            frontLiftLevel = frontLiftLevels.HIGH;
        } else if (Math.abs(gamepad.left_stick_y) > GamepadConstants.GAMEPAD2_STICK_DEADZONE) {
            frontLiftLevel = frontLiftLevels.CUSTOM;
        } else if (gamepad.dpad_down) {
            backLiftLevel = backLiftLevels.PICKUP;
        } else if (gamepad.dpad_left) {
            backLiftLevel = backLiftLevels.LOW;
        } else if (gamepad.dpad_right) {
            backLiftLevel = backLiftLevels.MEDIUM;
        } else if (gamepad.dpad_up) {
            backLiftLevel = backLiftLevels.HIGH;
        } else if (Math.abs(gamepad.right_stick_y) > GamepadConstants.GAMEPAD2_STICK_DEADZONE) {
            backLiftLevel = backLiftLevels.CUSTOM;
        }

        if (gamepad.right_trigger > GamepadConstants.GAMEPAD2_TRIGGER_DEADZONE) {
            claws.frontIntake.intake(1);
            claws.backIntake.intake(1);
        } else if (gamepad.left_trigger > GamepadConstants.GAMEPAD2_TRIGGER_DEADZONE) {
            claws.frontIntake.outtake(1);
            claws.backIntake.outtake(1);
        } else {
            claws.frontIntake.stop();
            claws.backIntake.stop();
        }

    }
}
