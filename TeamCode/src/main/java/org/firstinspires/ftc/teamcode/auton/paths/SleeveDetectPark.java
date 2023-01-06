package org.firstinspires.ftc.teamcode.auton.paths;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;


@Autonomous(name="SleeveDetectPark", group="Comp Autos")

public class SleeveDetectPark extends LinearOpMode {


    private Robot robot = new Robot(hardwareMap, true);

    public static double startX = 36.0;
    public static double startY = 62.9;
    public static double startAngle = Math.toRadians(270);

    public static double coneLifterOffset = 2;
    public static double approachDistanceOffset = 2.5;

    public static double parkingY = 35;
    public static double parkingRedX = 61;
    public static double parkingBlueX = 36;
    public static double parkingGreenX = 13;

    public static double stackX = 70;
    public static double stackY = 12;

    public static double firstScorePoleX = 24;
    public static double firstScorePoleY = 0;

    public static double firstScoreScoringAngle = Math.toRadians(0);



    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        robot.initRobot(hardwareMap);

        Pose2d startPose = new Pose2d(startX, startY, startAngle);

        drive.setPoseEstimate(startPose);

        TrajectorySequence complexPath = drive.trajectorySequenceBuilder(startPose)
                .lineToSplineHeading(new Pose2d(startX, firstScorePoleY, firstScoreScoringAngle))
                .strafeTo(new Vector2d(firstScorePoleX + coneLifterOffset, firstScorePoleY))
                .build();

        TrajectorySequence redPath = drive.trajectorySequenceBuilder(startPose)
                .build();

        TrajectorySequence bluePath = drive.trajectorySequenceBuilder(startPose)
                .build();

        TrajectorySequence greenPath = drive.trajectorySequenceBuilder(startPose)
                .build();

        waitForStart();

        while (opModeIsActive()) {
            if (robot.camera.coneFace == 1) {
                drive.followTrajectorySequence(redPath);
            } else if (robot.camera.coneFace == 2) {
                drive.followTrajectorySequence(bluePath);
            } else {
                drive.followTrajectorySequence(greenPath);
            }
            break;
        }
    }
}