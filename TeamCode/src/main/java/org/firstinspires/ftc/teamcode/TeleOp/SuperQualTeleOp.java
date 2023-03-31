package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@TeleOp(name="SuperQualTeleOp", group="TeleOp")

public class SuperQualTeleOp extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();
    Robot robot = new Robot(hardwareMap);
    DriverPreset daniel = new DriverPreset(0.73, 0.63, 1, 0.05, 2, 0.2);
    DriverPreset kai = new DriverPreset(1, 1, 1, 0.05, 1, 0.2);
    DriverPreset SEMI = new DriverPreset(0.85, 0.7, 1, 0.05, 2,  0.2);
    DriverPreset SPEED = new DriverPreset(1, 0.8, 1, 0.05, 2, 0.2);
    DriverPreset ethan = new DriverPreset(0.05, 0.2);

    @Override
    public void init() {
        robot.initRobot(hardwareMap);
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {
        robot.setActiveDrivers(daniel, ethan);
        runtime.reset();
    }

    @Override
    public void loop() {

        if (gamepad1.right_trigger > robot.activeDriver1.triggerDeadzone || gamepad1.left_trigger > robot.activeDriver1.triggerDeadzone) {
            robot.setActiveDrivers(daniel, ethan);
        } else if (gamepad1.left_bumper) {
            robot.setActiveDrivers(SPEED, ethan);
        } else if (gamepad1.right_bumper) {
            robot.setActiveDrivers(SEMI, ethan);
        }


        // Sets and then applies velos to drivebase based on gamepad 1 stick inputs.
        robot.drivebase.setVelos(new Pose2d(-gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x));
        robot.drivebase.applyVelos();

        if (gamepad2.a || gamepad2.right_trigger > robot.activeDriver2.triggerDeadzone) {
            robot.frontClaw.grab();
            robot.backClaw.grab();
        } else if (gamepad2.b || gamepad2.left_trigger > robot.activeDriver2.triggerDeadzone) {
            robot.frontClaw.release();
            robot.backClaw.release();
        } else if (gamepad2.right_bumper) {
            robot.frontClaw.release();
        } else if (gamepad2.left_bumper) {
            robot.backClaw.release();
        } else if (gamepad2.dpad_up) {
            robot.backClaw.grab();
        } else if (gamepad2.dpad_down) {
            robot.frontClaw.grab();
        } else {
            robot.frontClaw.off();
            robot.backClaw.off();
        }

        if (Math.abs(gamepad2.right_stick_y) > robot.activeDriver2.stickDeadzone && robot.frontLiftState != Robot.frontLiftStates.FRONTMOVING) { // robot.frontLift.activeHeight != LiftSubsystem.Level.CUSTOM && robot.frontLift.inBounds()
            robot.frontLift.userAdjustHeight(-gamepad2.right_stick_y);
        } else {
            if (robot.frontLiftState != Robot.frontLiftStates.FRONTMOVING) { // robot.frontLift.activeHeight != LiftSubsystem.Level.CUSTOM
                robot.frontLift.holdPos();
            }
        }

        if (Math.abs(gamepad2.left_stick_y) > robot.activeDriver2.stickDeadzone && robot.backLiftState != Robot.backLiftStates.BACKMOVING) { // robot.backLift.activeHeight != LiftSubsystem.Level.CUSTOM && robot.backLift.inBounds()
            robot.backLift.userAdjustHeight(-gamepad2.left_stick_y);
        } else {
            if (robot.backLiftState != Robot.backLiftStates.BACKMOVING) { // robot.backLift.activeHeight != LiftSubsystem.Level.CUSTOM
                robot.backLift.holdPos();
            }
        }

        if (robot.frontLift.lift.getCurrentPosition() == robot.frontLift.lift.getTargetPosition()) {
            robot.frontLiftState = Robot.frontLiftStates.FRONTINPOS;
        }
        if (robot.backLift.lift.getCurrentPosition() == robot.backLift.lift.getTargetPosition()) {
            robot.backLiftState = Robot.backLiftStates.BACKINPOS;
        }

        if (runtime.seconds() ==  85) { // Endgame warning
            gamepad1.rumble(1000);
            gamepad2.rumble(1000);
        }
        if (runtime.seconds() ==  115) { // End of Match warning
            gamepad1.rumble(1000);
            gamepad2.rumble(1000);
        }

        telemetry.addData("Front Lift Height", robot.frontLift.lift.getCurrentPosition());
        telemetry.addData("Back Lift Height", robot.backLift.lift.getCurrentPosition());
        telemetry.addData("Elapsed Time: ", runtime);

    }

    @Override
    public void stop() {

    }

}
