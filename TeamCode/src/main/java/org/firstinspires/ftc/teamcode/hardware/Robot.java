package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.TeleOp.DriverPreset;
import org.firstinspires.ftc.teamcode.hardware.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.hardware.subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.hardware.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.hardware.subsystems.RRDrivebaseSubsystem;

public class Robot {

    public HardwareMap hwMap;

    public DriverPreset activeDriver1;
    public DriverPreset activeDriver2;

    public RRDrivebaseSubsystem drivebase;
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
        drivebase = new RRDrivebaseSubsystem(hwMap);
        frontLift = new LiftSubsystem();
        frontClaw = new ClawSubsystem();
        backLift = new LiftSubsystem();
        backClaw = new ClawSubsystem();
        camera = new CameraSubsystem();
    }

    public void initRobot(HardwareMap hwMap) {
//        drivebase.initDrivebase(hwMap);
        initLiftsClawsCam(hwMap);
    }

    public void setActiveDrivers(DriverPreset gamepad1Driver, DriverPreset gamepad2Driver) {
        activeDriver1 = gamepad1Driver;
        activeDriver2 = gamepad2Driver;
//        drivebase.setActiveDrivingPresets(gamepad1Driver);
    }


    public void initLiftsClawsCam(HardwareMap hwMap) {
        camera.initCamera(hwMap);
        frontLift.initLift(hwMap, "frontLift");
        frontClaw.initClaw(hwMap, "frontLeftClaw", "frontRightClaw");
        backLift.initLift(hwMap, "backLift");
        backClaw.initClaw(hwMap, "backLeftClaw", "backRightClaw");
    }
}

