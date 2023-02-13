package org.firstinspires.ftc.teamcode.auton.paths;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name="BlueConeCyclePark", group="Comp Autos")

public class BlueConeCyclePark extends LinearOpMode {

//    private Robot robot = new Robot(hardwareMap, true);

    private static double startX = -36.0;
    private static double startY = 63.3;
    private static double startAngle = Math.toRadians(0);

    private static double coneLifterOffset = 8;
    private static double approachDistanceOffset = 2.3;

    private static double parkingY = 35;
    private static double parkingRedX = -12;
    private static double parkingBlueX = -36;
    private static double parkingGreenX = -60;

    private static double stackX = -70;
    private static double stackY = 12;

    private static double firstScorePoleX = -48;
    private static double firstScorePoleY = 24;

    private static double secondScorePoleX = -24;
    private static double secondScorePoleY = 0;

    private static double thirdScorePoleX = -24;
    private static double thirdScorePoleY = 24;

    public static Pose2d startPose = new Pose2d(startX, startY, startAngle);


    @Override
    public void runOpMode() {

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

//        robot.initRobot(hardwareMap);

        drive.setPoseEstimate(startPose); //Set drive to the starting position

        TrajectorySequence coneCycles = drive.trajectorySequenceBuilder(startPose)
                .strafeTo(new Vector2d(firstScorePoleX + coneLifterOffset + approachDistanceOffset, firstScorePoleY))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    // LIFT UP CODE
                })
                .waitSeconds(2)
                .strafeTo(new Vector2d(firstScorePoleX + coneLifterOffset, firstScorePoleY))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    // DROP CONE CODE
                })
                .waitSeconds(1)
                .strafeTo(new Vector2d(startX, stackY))
                .strafeTo(new Vector2d(stackX + coneLifterOffset, stackY))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    // PICK UP CONE CODE
                })
                .waitSeconds(1.25)
                .splineToConstantHeading(new Vector2d(secondScorePoleX + coneLifterOffset + approachDistanceOffset, secondScorePoleY), Math.toRadians(270))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    // LIFT UP CODE
                })
                .waitSeconds(2)
                .strafeTo(new Vector2d(secondScorePoleX + coneLifterOffset, secondScorePoleY))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    // DROP CONE CODE
                })
                .waitSeconds(1)
                .splineToConstantHeading(new Vector2d(secondScorePoleX + coneLifterOffset + approachDistanceOffset, stackY), Math.toRadians(90))
                .strafeTo(new Vector2d(stackX + coneLifterOffset, stackY))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    // PICK UP CONE CODE
                })
                .waitSeconds(1.25)
                .splineToConstantHeading(new Vector2d(thirdScorePoleX + coneLifterOffset + approachDistanceOffset, thirdScorePoleY), Math.toRadians(-270))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    // LIFT UP CODE
                })
                .waitSeconds(2)
                .strafeTo(new Vector2d(thirdScorePoleX + coneLifterOffset, thirdScorePoleY))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    // DROP CONE CODE
                })
                .waitSeconds(1)
                .splineToConstantHeading(new Vector2d(parkingRedX, parkingY), Math.toRadians(90))
                .build();

        TrajectorySequence bluePath = drive.trajectorySequenceBuilder(coneCycles.end())
                .strafeTo(new Vector2d(parkingBlueX, parkingY))
                .build();

        TrajectorySequence greenPath = drive.trajectorySequenceBuilder(coneCycles.end())
                .strafeTo(new Vector2d(parkingGreenX, parkingY))
                .build();



        waitForStart();

        while (opModeIsActive()) {
            drive.followTrajectorySequence(coneCycles);
//            if (robot.camera.coneFace == 2) {
//                drive.followTrajectorySequence(bluePath);
//            } else if (robot.camera.coneFace == 3) {
//                drive.followTrajectorySequence(greenPath);
//            }
            break;
        }
    }
}