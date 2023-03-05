package org.firstinspires.ftc.teamcode.TeleOp;

public class DriverPreset {

    public double strafeMultiplier;
    public double turnMultiplier;
    public double maxSpeedMultiplier;
    public double stickDeadzone;
    public int exponentialDriveModifier;
    public double triggerDeadzone;

    DriverPreset(double strafeMult, double turnMult, double maxSpeedMult, double stickDead, int exponentialDriveMod, double triggerDead) {
        strafeMultiplier = strafeMult;
        turnMultiplier = turnMult;
        maxSpeedMultiplier = maxSpeedMult;
        stickDeadzone = stickDead;
        exponentialDriveModifier = exponentialDriveMod;
        triggerDeadzone = triggerDead;

    }

    DriverPreset(double stickDead, double triggerDead) {
        stickDeadzone = stickDead;
        triggerDeadzone = triggerDead;
    }

}
