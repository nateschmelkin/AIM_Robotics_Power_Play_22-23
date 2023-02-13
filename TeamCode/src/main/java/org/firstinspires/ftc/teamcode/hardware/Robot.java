package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.hardware.subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DrivebaseSubsystem;
import org.firstinspires.ftc.teamcode.hardware.subsystems.LiftSubsystem;

import org.firstinspires.ftc.teamcode.TeleOp.DriverPreset;

public class Robot {

    public HardwareMap hwMap;

    public double gamepad1StickDeadzone;
    public double gamepad2StickDeadzone;

    public DrivebaseSubsystem drivebase;
    public LiftSubsystem frontLift;
    public LiftSubsystem backLift;
    public ClawSubsystem frontClaw;
    public ClawSubsystem backClaw;
    public CameraSubsystem camera;

    public enum frontLiftStates{
        FRONTMOVING,
        FRONTINPOS
    }

    public enum backLiftStates{
        BACKMOVING,
        BACKINPOS
    }

    public frontLiftStates frontLiftState = frontLiftStates.FRONTINPOS;
    public backLiftStates backLiftState = backLiftStates.BACKINPOS;

    public Robot(HardwareMap ahwMap) {
        hwMap = ahwMap;
        drivebase = new DrivebaseSubsystem();
        frontLift = new LiftSubsystem();
        frontClaw = new ClawSubsystem();
        backLift = new LiftSubsystem();
        backClaw = new ClawSubsystem();
        camera = new CameraSubsystem();
    }

    public void initRobot(HardwareMap hwMap) {
        drivebase.initDrivebase(hwMap);
        initLiftsClawsCam(hwMap);
    }

    public void setActivePresets(DriverPreset gamepad1Driver, DriverPreset gamepad2Driver) {
        gamepad1StickDeadzone = gamepad1Driver.stickDeadzone;
        drivebase.strafingSense = gamepad1Driver.strafeMultiplier;
        drivebase.turningSense = gamepad1Driver.turnMultiplier;
        drivebase.maxSpeed = gamepad1Driver.maxSpeedMultiplier;
        gamepad2StickDeadzone = gamepad2Driver.stickDeadzone;
    }


    public void initLiftsClawsCam(HardwareMap hwMap) {
        camera.initCamera(hwMap);
        frontLift.initLift(hwMap, "frontLift");
        frontClaw.initClaw(hwMap, "frontLeftClaw", "frontRightClaw");
        backLift.initLift(hwMap, "backLift");
        backClaw.initClaw(hwMap, "backLeftClaw", "backRightClaw");
    }
}

