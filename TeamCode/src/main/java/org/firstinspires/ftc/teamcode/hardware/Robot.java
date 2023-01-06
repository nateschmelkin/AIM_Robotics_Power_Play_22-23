package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.CSController;
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
    public CSController csController = null;

    public enum coneStates{
        NONE,
        READYFORPICKUP,
        GRABBING,
        GRABBED,
        DROPPING
    }

    public coneStates activeConeState = coneStates.NONE;

    public Robot(HardwareMap ahwMap, boolean isRed) {
        hwMap = ahwMap;
        spinner = new SpinnerSubsystem(isRed);
        lift = new LiftSubsystem();
        drivebase = new DrivebaseSubsystem();
        claw = new ClawSubsystem();
        camera = new CameraSubsystem();
        csController = new CSController(isRed);
    }

    public void initRobot(HardwareMap hwMap) {
        spinner.initSpinner(hwMap);
        lift.initLift(hwMap);
        drivebase.initDrivebase(hwMap);
        claw.initClaw(hwMap);
        camera.initCamera(hwMap);
        csController.initCSController(hwMap);
    }
}

