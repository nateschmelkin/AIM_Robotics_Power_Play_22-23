package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.hardware.subsystems.LiftSubsystem;

@TeleOp(name="SuperQualTeleOp", group="TeleOp")

public class SuperQualTeleOp extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();
    Robot robot = new Robot(hardwareMap);
    DriverPreset daniel = new DriverPreset(0.6, 0.6, 1, 0.05, 0.2);
    DriverPreset kai = new DriverPreset(0.6, 0.6, 1, 0.05, 0.2);
    DriverPreset SEMI = new DriverPreset(0.8, 0.7, 1, 0.05, 0.2);
    DriverPreset SPEED = new DriverPreset(1, 0.8, 1, 0.05, 0.2);
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
        robot.setActivePresets(daniel, ethan);
        runtime.reset();
    }

    @Override
    public void loop() {

        if (gamepad1.right_trigger > daniel.triggerDeadzone || gamepad1.left_trigger > daniel.triggerDeadzone) {
            if (gamepad1.right_trigger > daniel.triggerDeadzone && gamepad1.left_trigger > daniel.triggerDeadzone) {
                robot.setActivePresets(SPEED, ethan);
            } else {
                robot.setActivePresets(SEMI, ethan);
            }
        }


        // Sets and then applies velos to drivebase based on gamepad 1 stick inputs.
        robot.drivebase.setVelos(-gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, robot.gamepad1StickDeadzone);
        robot.drivebase.applyVelos();

        if (gamepad2.a || gamepad2.right_trigger > ethan.triggerDeadzone) {
            robot.frontClaw.grab();
            robot.backClaw.grab();
        } else if (gamepad2.b || gamepad2.left_trigger > ethan.triggerDeadzone) {
            robot.frontClaw.release();
            robot.backClaw.release();
        } else if (gamepad2.y) {
            robot.frontLift.setHeight(LiftSubsystem.Level.HIGH);
            robot.frontLiftState = Robot.frontLiftStates.FRONTMOVING;
        } else if (gamepad2.dpad_up) {
            robot.backLift.setHeight(LiftSubsystem.Level.HIGH);
            robot.backLiftState = Robot.backLiftStates.BACKMOVING;
        } else {
            robot.frontClaw.off();
            robot.backClaw.off();
        }

        if (Math.abs(gamepad2.right_stick_y) > robot.gamepad2StickDeadzone && robot.frontLiftState != Robot.frontLiftStates.FRONTMOVING) {
            robot.frontLift.userAdjustHeight(-gamepad2.right_stick_y);
        } else {
            if (robot.frontLiftState != Robot.frontLiftStates.FRONTMOVING) {
                robot.frontLift.holdPos();
            }
        }

        if (Math.abs(gamepad2.left_stick_y) > robot.gamepad2StickDeadzone && robot.backLiftState != Robot.backLiftStates.BACKMOVING) {
            robot.backLift.userAdjustHeight(-gamepad2.left_stick_y);
        } else {
            if (robot.backLiftState != Robot.backLiftStates.BACKMOVING) {
                robot.backLift.holdPos();
            }
        }

        if (robot.frontLift.lift.getCurrentPosition() == robot.frontLift.lift.getTargetPosition()) {
            robot.frontLiftState = Robot.frontLiftStates.FRONTINPOS;
        }
        if (robot.backLift.lift.getCurrentPosition() == robot.backLift.lift.getTargetPosition()) {
            robot.backLiftState = Robot.backLiftStates.BACKINPOS;
        }

        telemetry.addData("Front Lift Height", robot.frontLift.lift.getCurrentPosition());
        telemetry.addData("Back Lift Height", robot.backLift.lift.getCurrentPosition());


    }

    @Override
    public void stop() {

    }

}
