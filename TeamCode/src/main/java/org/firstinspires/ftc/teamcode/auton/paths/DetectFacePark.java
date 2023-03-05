package org.firstinspires.ftc.teamcode.auton.paths;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;


@Autonomous(name="DetectFacePark", group="Comp Autos")

public class DetectFacePark extends LinearOpMode {


    private Robot robot = new Robot(hardwareMap);

    private static double startX = 34.5;
    private static double startY = 63.5;
    private static double startAngle = Math.toRadians(0);

    private static double coneLifterOffset = 5.8;
    private static double approachDistanceOffset = 2.5;

    private static double parkingY = 35;
    private static double parkingRedX = 60;
    private static double parkingGreenX = 36;
    private static double parkingBlueX = 12;

    public static double scorePoleX = 24;
    public static double scorePoleY = 0;

    public static Pose2d startPose = new Pose2d(startX, startY, startAngle);


    @Override
    public void runOpMode() {

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        robot.initLiftsClawsCam(hardwareMap);
        robot.camera.initPipeline();
        robot.camera.webcamStream();

        drive.setPoseEstimate(startPose);

        TrajectorySequence redPath = drive.trajectorySequenceBuilder(startPose)
                .strafeTo(new Vector2d(parkingGreenX, parkingY))
                .strafeTo(new Vector2d(parkingRedX, parkingY))
                .build();

        TrajectorySequence greenPath = drive.trajectorySequenceBuilder(startPose)
                .strafeTo(new Vector2d(parkingGreenX, parkingY))
                .build();

        TrajectorySequence bluePath = drive.trajectorySequenceBuilder(startPose)
                .strafeTo(new Vector2d(parkingGreenX, parkingY))
                .strafeTo(new Vector2d(parkingBlueX, parkingY))
                .build();

        waitForStart();

        while (opModeIsActive()) {
            robot.camera.processImage();
            telemetry.addData("ConeFace", robot.camera.coneFace);
            telemetry.update();
            if (robot.camera.coneFace == 1) {
                drive.followTrajectorySequence(redPath);
            } else if (robot.camera.coneFace == 2) {
                drive.followTrajectorySequence(greenPath);
            } else if (robot.camera.coneFace == 3) {
                drive.followTrajectorySequence(bluePath);
            }
            robot.frontLift.setHeightAuto(robot.frontLift.pickupInches); // SET PICKUP
            robot.backLift.setHeightAuto(robot.backLift.pickupInches); // SET PICKUP
            sleep(2000);
            break;
        }
    }
}