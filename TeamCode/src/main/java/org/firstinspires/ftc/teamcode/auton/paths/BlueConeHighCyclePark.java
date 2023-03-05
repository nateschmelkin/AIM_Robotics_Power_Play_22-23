package org.firstinspires.ftc.teamcode.auton.paths;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name="BlueConeHighCyclePark", group="Comp Autos")

public class BlueConeHighCyclePark extends LinearOpMode {

    private Robot robot = new Robot(hardwareMap);

    private static double startX = -32.3;
    private static double startY = 64.2;
    private static double startAngle = Math.toRadians(0);

    private static double coneLifterOffset = 5.8;
    private static double approachDistanceOffset = 2.5;

    private static double parkingY = 14;
    private static double parkingRedX = -11;
    private static double parkingGreenX = -35;
    private static double parkingBlueX = -66.5;

    private static double stackX = -67.3;
    private static double stackY = 14.3;

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
                .lineToLinearHeading(new Pose2d(scorePoleX - coneLifterOffset - approachDistanceOffset, scorePoleY + .75, Math.toRadians(-1.7)))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.frontLift.setHeightAuto(robot.frontLift.highInches); // SET HIGH
                })
                .waitSeconds(2.2)
                .strafeTo(new Vector2d(scorePoleX - coneLifterOffset + 1.3, scorePoleY + .75))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.frontLift.setHeightAuto(robot.frontLift.highInches - 1); // SET HIGH
                    robot.frontClaw.release(); // DROP CONE
                })
                .waitSeconds(1.4)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.frontClaw.off(); // CLAW OFF AND RESET HEIGHT
                    robot.backLift.setHeightAuto(6);
                })
                .strafeTo(new Vector2d(scorePoleX - coneLifterOffset - approachDistanceOffset, scorePoleY + .75))
                .strafeTo(new Vector2d(startX, stackY))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.frontLift.setHeightAuto(robot.frontLift.pickupInches);
                })
                .strafeTo(new Vector2d(stackX + coneLifterOffset, stackY))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.backLift.setHeightAuto(4);
                    robot.backClaw.grab(); // PICKUP CONE
                })
                .waitSeconds(1.4)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.backClaw.off();
                    robot.backLift.setHeightAuto(6);
                })
                .waitSeconds(.5)
                .strafeTo(new Vector2d(scorePoleX + coneLifterOffset + approachDistanceOffset + 4.3, stackY))
                .strafeTo(new Vector2d(scorePoleX + coneLifterOffset + approachDistanceOffset + 4.3, scorePoleY + .75))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.backLift.setHeightAuto(robot.frontLift.highInches); // SET HIGH
                })
                .waitSeconds(1.5)
                .strafeTo(new Vector2d(scorePoleX + coneLifterOffset + approachDistanceOffset - .6, scorePoleY + .75))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.backLift.setHeightAuto(robot.frontLift.highInches - 1); // SET HIGH
                    robot.backClaw.release(); // DROP CONE
                })
                .waitSeconds(1)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.backLift.setHeightAuto(6);
                })
                .strafeTo(new Vector2d(scorePoleX + coneLifterOffset + approachDistanceOffset + 4.3, scorePoleY + .75))
                .strafeTo(new Vector2d(scorePoleX + coneLifterOffset + approachDistanceOffset + 4.3, stackY))
                .strafeTo(new Vector2d(stackX + coneLifterOffset, stackY))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.backLift.setHeightAuto(3);
                    robot.backClaw.grab(); // PICKUP CONE
                })
                .waitSeconds(.7)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    robot.backClaw.off();
                    robot.backLift.setHeightAuto(6);
                })
                .waitSeconds(.35)
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
            if (robot.camera.coneFace == 1) {
                drive.followTrajectorySequence(redPath);
            } else if (robot.camera.coneFace == 2) {
                drive.followTrajectorySequence(greenPath);
            }
            robot.frontLift.setHeightAuto(robot.frontLift.pickupInches); // SET PICKUP
            robot.backLift.setHeightAuto(robot.backLift.pickupInches); // SET PICKUP
            sleep(2000);
            break;
        }
    }
}