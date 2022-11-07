package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.checkerframework.checker.units.qual.C;
import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DrivebaseSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SpinnerSubsystem;

public class Robot {

    public HardwareMap hwMap;

    public double triggerDeadzone = .3;

    public SpinnerSubsystem spinner = null;
    public LiftSubsystem lift = null;
    public DrivebaseSubsystem drivebase = null;
    public ClawSubsystem claw = null;
    public CameraSubsystem camera = null;

    public enum coneStates{
        NONE,
        READYFORPICKUP,
        GRABBED,
        DROPPING,
        DROPPED
    }

    public coneStates activeConeState = coneStates.NONE;

    public Robot(HardwareMap ahwMap) {
        hwMap = ahwMap;
        spinner = new SpinnerSubsystem();
        lift = new LiftSubsystem();
        drivebase = new DrivebaseSubsystem();
        claw = new ClawSubsystem();
        camera = new CameraSubsystem();
    }

    public void initRobot(HardwareMap hwMap) {
        spinner.initSpinner(hwMap);
        lift.initLift(hwMap);
        drivebase.initDrivebase(hwMap);
        claw.initClaw(hwMap);
        camera.initCamera(hwMap);
    }
}

