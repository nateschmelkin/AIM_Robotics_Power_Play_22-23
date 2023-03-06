package org.firstinspires.ftc.teamcode.auton.paths;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name="OnePlusTwoRight", group="AA FINALS AUTOS", preselectTeleOp="SuperQualTeleOp")

public class OnePlusTwoRight extends LinearOpMode {

    private Robot robot = new Robot(hardwareMap);

    public static double startX = 35;
    public static double startY = -62.25;
    private static double startAngle = Math.toRadians(180);

    public static double coneLifterOffset = 6.457;
    public static double coneLifterPythagoreanOffset = 5.5;

    private static double parkingY = -14.5;
    private static double parkingRedX = 12;
    private static double parkingGreenX = 35;
    private static double parkingBlueX = 66.5;

    public static double stackFrontLiftPickupAngle = Math.toRadians(195);
    public static double stackBackLiftPickupAngle = Math.toRadians(0);
    public static double stackAdjustment = Math.toRadians(4);
    public static double endAngle = Math.toRadians(170);

    public static double stackX = 71.9;
    public static double stackY = -12.8;

    public static double scorePoleX = 24;
    public static double scorePoleY = 0;
    public static double scorePoleFrontLiftAngle = Math.toRadians(315);
    public static double scorePoleBackLiftAngle = Math.toRadians(135);
    public static double scoreAdjustment = Math.toRadians(6);
    public static double scorePoleWait = 0.2;

    public static double scoreDropDistance = 2.4;

    public static double coneStack1Height = 9;
    public static double coneStack2Height = 7.75;
    public static double coneStack3Height = 6.5;

    public static double prepSpotX = 33.5;
    public static double prepSpotY = -13.9;

    public static double pickupTime = 0.55;
    public static double dropTime = 0.6;

    public static Pose2d startPose = new Pose2d(startX, startY, startAngle);


    @Override
    public void runOpMode() {

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        robot.initLiftsClawsCam(hardwareMap);
        robot.camera.initPipeline();
        robot.camera.webcamStream();

        drive.setPoseEstimate(startPose); //Set drive to the starting position

        TrajectorySequence cycling = drive.trajectorySequenceBuilder(startPose)
                //
                // PRELOAD
                //
                .strafeTo(new Vector2d(scorePoleX + coneLifterOffset + 4, scorePoleY + .5))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.frontLift.setHeightAuto(robot.frontLift.highInches); // SET HIGH
                    robot.frontClaw.drawPower();
                })
                .waitSeconds(2.3)
                .forward(5)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.frontLift.setHeightAuto(robot.frontLift.highInches - scoreDropDistance); // SET HIGH
                    robot.frontClaw.release(); // DROP CONE
                })
                .waitSeconds(dropTime)
                .UNSTABLE_addTemporalMarkerOffset(.5, () -> {
                    robot.frontClaw.off();
                    robot.backLift.setHeightAuto(robot.backLift.resetInches);
                })
                .back(2)
                .splineToConstantHeading(new Vector2d(startX, stackY + 1), Math.toRadians(90))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.frontLift.setHeightAuto(robot.frontLift.resetInches);
                })
                .strafeTo(new Vector2d(stackX - coneLifterOffset - .75, stackY))
                //
                // STACK #1
                //
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.backLift.setHeightAuto(coneStack1Height);
                    robot.backClaw.grab(); // PICKUP CONE
                })
                .waitSeconds(pickupTime)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.backLift.setHeightAuto(robot.backLift.lowInches);
                    robot.backClaw.off();
                })
                .strafeTo(new Vector2d(prepSpotX, prepSpotY))
                .turn(Math.toRadians(127))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.backLift.setHeightAuto(robot.backLift.highInches + 1); // SET HIGH
                })
                .strafeTo(new Vector2d(scorePoleX + coneLifterPythagoreanOffset - .75, scorePoleY - coneLifterPythagoreanOffset))
                .back(.5)
//                .forward(5)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.backLift.setHeightAuto(robot.backLift.lowInches - scoreDropDistance); // SET HIGH
                    robot.backClaw.release(); // DROP CONE
                })
                .waitSeconds(dropTime)
                .UNSTABLE_addTemporalMarkerOffset(.5, () -> {
                    robot.backClaw.off();
                    robot.backLift.setHeightAuto(robot.backLift.resetInches);
                })
                .lineToSplineHeading(new Pose2d(prepSpotX, prepSpotY, stackBackLiftPickupAngle)) // ADDED ADJUSTMENT HERE
                .strafeTo(new Vector2d(stackX - coneLifterOffset, stackY))
                //
                // STACK #2
                //
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.frontLift.setHeightAuto(coneStack2Height);
                    robot.frontClaw.grab(); // PICKUP CONE
                })
                .waitSeconds(pickupTime)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.frontLift.setHeightAuto(robot.frontLift.lowInches);
                    robot.frontClaw.off();
                })
                .strafeTo(new Vector2d(prepSpotX, prepSpotY))
                .turn(Math.toRadians(125))
                .UNSTABLE_addTemporalMarkerOffset(-.3, () -> {
                    robot.frontLift.setHeightAuto(robot.frontLift.highInches + 1); // SET HIGH
                })
                .strafeTo(new Vector2d(scorePoleX + coneLifterPythagoreanOffset - .75, scorePoleY - coneLifterPythagoreanOffset))
                .forward(1.3)
                .waitSeconds(scorePoleWait)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.frontLift.setHeightAuto(robot.frontLift.highInches - scoreDropDistance); // SET HIGH
                    robot.frontClaw.release(); // DROP CONE
                })
                .waitSeconds(dropTime)
                .UNSTABLE_addTemporalMarkerOffset(0.8, () -> {
                    robot.frontClaw.off(); // CLAW OFF AND RESET HEIGHT
                    robot.frontLift.setHeightAuto(robot.frontLift.resetInches);
                })
                .lineToSplineHeading(new Pose2d(prepSpotX, stackY, endAngle))
                .build();

        TrajectorySequence redPath = drive.trajectorySequenceBuilder(cycling.end())
                .strafeTo(new Vector2d(parkingRedX, stackY))
                .strafeTo(new Vector2d(parkingRedX, parkingY))
                .build();

        TrajectorySequence greenPath = drive.trajectorySequenceBuilder(cycling.end())
                .strafeTo(new Vector2d(parkingGreenX, parkingY))
                .build();

        TrajectorySequence bluePath = drive.trajectorySequenceBuilder(cycling.end())
                .strafeTo(new Vector2d(stackX - coneLifterOffset - .75, stackY))
//                //
//                // STACK #3
//                //
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.backLift.setHeightAuto(coneStack3Height);
                    robot.backClaw.grab(); // PICKUP CONE
                })
                .waitSeconds(pickupTime)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.backLift.setHeightAuto(robot.backLift.resetInches); // SET HIGH
                    robot.backClaw.off();
                })
                .strafeTo(new Vector2d(parkingBlueX, parkingY))
                .build();



        waitForStart();

        while (opModeIsActive()) {
            robot.camera.processImage();
            telemetry.addData("ConeFace", robot.camera.coneFace);
            telemetry.update();
            drive.followTrajectorySequence(cycling);
            if (robot.camera.coneFace == 1) {
                drive.followTrajectorySequence(redPath);
            } else if (robot.camera.coneFace == 2) {
                drive.followTrajectorySequence(greenPath);
            } else if (robot.camera.coneFace == 3) {
                drive.followTrajectorySequence(bluePath);
            }
//            robot.frontLift.setHeightAuto(robot.frontLift.resetInches); // SET PICKUP
//            robot.backLift.setHeightAuto(robot.backLift.resetInches); // SET PICKUP
//            sleep(2000);
            break;
        }
    }
}