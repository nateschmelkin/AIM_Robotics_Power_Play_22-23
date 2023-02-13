//package org.firstinspires.ftc.teamcode.auton;
//
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.firstinspires.ftc.teamcode.hardware.Robot;
//
//@Autonomous(name="CameraParkingAutoExtraScoreBLUE", group="Autos")
//
//public class CameraParkingAutoExtraScoreBLUE extends LinearOpMode {
//    @Override
//    public void runOpMode() {
//        Robot robot = new Robot(hardwareMap);
//
//        robot.initRobot(hardwareMap);
//        robot.camera.initPipeline();
//        robot.camera.webcamStream();
//
//        waitForStart();
//
//        while (opModeIsActive()) {
//            telemetry.addData("ConeFace", robot.camera.coneFace);
//            telemetry.update();
//            robot.camera.processImage();
//
//            robot.drivebase.strafe(.5, 0, 0, .5);
//            sleep(1450);
//            robot.drivebase.stopMovement();
//            sleep(1000);
//            robot.drivebase.strafe(-.5, 0, 0, -.5);
//            sleep(1300);
//            robot.drivebase.stopMovement();
//            sleep(1000);
//
//            if (robot.camera.coneFace == 1) {
//                robot.drivebase.strafe(-.5, 0, 0, -.5);
//                sleep(1300);
//                robot.drivebase.stopMovement();
//            } else if (robot.camera.coneFace == 3) {
//                robot.drivebase.strafe(.5, 0, 0, .5);
//                sleep(1300);
//                robot.drivebase.stopMovement();
//            }
//            sleep(2000);
//            robot.drivebase.strafe(0, .5, .5, 0);
//            sleep(2000);
//            robot.drivebase.stopMovement();
//            break;
//        }
//    }
//}