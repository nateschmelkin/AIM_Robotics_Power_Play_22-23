package org.firstinspires.ftc.teamcode.auton.paths;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name="TestCrazyThing", group="Comp Autos")

public class TestCrazyThing extends LinearOpMode {

    private Robot robot = new Robot(hardwareMap);

    private static double startX = -32.2;
    private static double startY = 64.2;
    private static double startAngle = Math.toRadians(0);

    private static double coneLifterOffset = 6.5;
    private static double approachDistanceOffset = 3.3;

    private static double parkingY = 14;
    private static double parkingRedX = -11;
    private static double parkingGreenX = -35;
    private static double parkingBlueX = -66.5;

    private static double stackX = -67.8;
    private static double stackY = 14;

    private static double scorePoleX = -24;
    private static double scorePoleY = 0;

    public static Pose2d startPose = new Pose2d(startX, startY, startAngle);


    @Override
    public void runOpMode() {

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        robot.initLiftsClawsCam(hardwareMap);
        robot.camera.initPipeline();
        robot.camera.webcamStream();

        drive.setPoseEstimate(startPose); //Set drive to the starting position

        TrajectorySequence coneCycles = drive.trajectorySequenceBuilder(startPose)
                .lineToLinearHeading(new Pose2d(scorePoleX - coneLifterOffset - approachDistanceOffset, scorePoleY, Math.toRadians(-1.7)))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.frontLift.setHeightAuto(robot.frontLift.highInches + 80); // SET HIGH
                })
                .waitSeconds(2.2)
                .strafeTo(new Vector2d(scorePoleX - coneLifterOffset + 1.2, scorePoleY))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.frontLift.setHeightAuto(robot.frontLift.highInches - 400); // SET HIGH
                    robot.frontClaw.release(); // DROP CONE
                })
                .waitSeconds(1.4)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.frontClaw.off(); // CLAW OFF AND RESET HEIGHT
                    robot.backLift.setHeightAuto(1200);
                })
                .strafeTo(new Vector2d(scorePoleX - coneLifterOffset - approachDistanceOffset, scorePoleY))
                .strafeTo(new Vector2d(startX, stackY))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.frontLift.setHeightAuto(robot.frontLift.pickupInches);
                })
                .strafeTo(new Vector2d(stackX + coneLifterOffset, stackY))
                //
                // Stack #1
                //
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.backLift.setHeightAuto(480);
                    robot.backClaw.grab(); // PICKUP CONE
                })
                .waitSeconds(1.4)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.backClaw.off();
                    robot.backLift.setHeightAuto(robot.backLift.highInches + 80);
                })
                .waitSeconds(.5)
                .lineToSplineHeading(new Pose2d(scorePoleX + 2, scorePoleY + coneLifterOffset + approachDistanceOffset, Math.toRadians(90)))
                .strafeTo(new Vector2d(scorePoleX + 2, scorePoleY + coneLifterOffset))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.backLift.setHeightAuto(robot.backLift.highInches - 400); // SET HIGH
                    robot.backClaw.release(); // DROP CONE
                })
                .waitSeconds(1.4)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.frontClaw.off(); // CLAW OFF AND RESET HEIGHT
                    robot.backLift.setHeightAuto(1200);
                })
                .strafeTo(new Vector2d(scorePoleX, scorePoleY + coneLifterOffset + approachDistanceOffset))
                .lineToSplineHeading(new Pose2d(stackX + coneLifterOffset, stackY, Math.toRadians(0)))
                //
                // Stack #2
                //
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.backLift.setHeightAuto(380);
                    robot.backClaw.grab(); // PICKUP CONE
                })
                .waitSeconds(1.4)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.backClaw.off();
                    robot.backLift.setHeightAuto(robot.backLift.highInches + 80);
                })
                .waitSeconds(.5)
                .lineToSplineHeading(new Pose2d(scorePoleX + 2, scorePoleY + coneLifterOffset + approachDistanceOffset, Math.toRadians(90)))
                .strafeTo(new Vector2d(scorePoleX + 2, scorePoleY + coneLifterOffset))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.backLift.setHeightAuto(robot.backLift.highInches - 400); // SET HIGH
                    robot.backClaw.release(); // DROP CONE
                })
                .waitSeconds(1.4)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.frontClaw.off(); // CLAW OFF AND RESET HEIGHT
                    robot.backLift.setHeightAuto(1200);
                })
                .strafeTo(new Vector2d(scorePoleX, scorePoleY + coneLifterOffset + approachDistanceOffset))
                .lineToSplineHeading(new Pose2d(stackX + coneLifterOffset, stackY, Math.toRadians(0)))
                //
                // Stack #3
                //
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.backLift.setHeightAuto(320);
                    robot.backClaw.grab(); // PICKUP CONE
                })
                .waitSeconds(1.4)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.backClaw.off();
                    robot.backLift.setHeightAuto(robot.backLift.highInches + 80);
                })
                .waitSeconds(.5)
                .lineToSplineHeading(new Pose2d(scorePoleX + 2, scorePoleY + coneLifterOffset + approachDistanceOffset, Math.toRadians(90)))
                .strafeTo(new Vector2d(scorePoleX + 2, scorePoleY + coneLifterOffset))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.backLift.setHeightAuto(robot.backLift.highInches - 400); // SET HIGH
                    robot.backClaw.release(); // DROP CONE
                })
                .waitSeconds(1.4)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.frontClaw.off(); // CLAW OFF AND RESET HEIGHT
                    robot.backLift.setHeightAuto(1200);
                })
                .strafeTo(new Vector2d(scorePoleX, scorePoleY + coneLifterOffset + approachDistanceOffset))
                .lineToSplineHeading(new Pose2d(stackX + coneLifterOffset, stackY, Math.toRadians(0)))
                .build();




        TrajectorySequence redPath = drive.trajectorySequenceBuilder(coneCycles.end())
                .strafeTo(new Vector2d(parkingRedX, parkingY))
                .build();

        TrajectorySequence greenPath = drive.trajectorySequenceBuilder(coneCycles.end())
                .strafeTo(new Vector2d(parkingGreenX, parkingY))
                .build();



        waitForStart();

        while (opModeIsActive()) {
            robot.camera.processImage();
            telemetry.addData("ConeFace", robot.camera.coneFace);
            telemetry.update();
            drive.followTrajectorySequence(coneCycles);
//            if (robot.camera.coneFace == 1) {
//                drive.followTrajectorySequence(redPath);
//            } else if (robot.camera.coneFace == 2) {
//                drive.followTrajectorySequence(greenPath);
//            }
//            robot.frontLift.setHeightAuto(robot.frontLift.pickupTicks); // SET PICKUP
//            robot.backLift.setHeightAuto(robot.backLift.pickupTicks); // SET PICKUP
//            sleep(2000);
            break;
        }
    }
}