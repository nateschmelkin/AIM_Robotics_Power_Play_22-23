package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.samples.MecanumHardware;
import org.firstinspires.ftc.teamcode.subsystems.*;

public class AIMTeleOp extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();
    Robot robot = new Robot(true);



    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {

        robot.drivebase.setVelos();
        robot.drivebase.applyVelos();

        if (robot.activeConeState == Robot.coneStates.NONE) { // TODO COMMENT TO EXPLAIN ARM AUTOMATION
            robot.lift.setHeight(LiftSubsystem.Level.RESET);
            if (robot.spinner.checkSides() && robot.lift.activeHeight == LiftSubsystem.Level.RESET && !robot.lift.lift.isBusy()) {
                robot.activeConeState = Robot.coneStates.READYFORPICKUP;
            }
        }

        if (robot.activeConeState == Robot.coneStates.READYFORPICKUP) {
            robot.spinner.setSide(robot.spinner.activeSide);
            if (!robot.spinner.spinner.isBusy()) {
                robot.lift.setHeight(LiftSubsystem.Level.PICKUP);
            }
        }

        if (robot.lift.activeHeight == LiftSubsystem.Level.PICKUP && !robot.lift.lift.isBusy()) {
            robot.claw.grab();
            robot.activeConeState = Robot.coneStates.GRABBED;
        }

        if (robot.activeConeState == Robot.coneStates.GRABBED) {
            if (gamepad1.a) {
                robot.lift.setHeight(LiftSubsystem.Level.HIGH);
            } else if (gamepad1.b) {
                robot.lift.setHeight(LiftSubsystem.Level.MEDIUM);
            } else if (gamepad1.y) {
                robot.lift.setHeight(LiftSubsystem.Level.LOW);
            } else if (gamepad1.x) {
                robot.lift.setHeight(LiftSubsystem.Level.GROUND);
            } else if (!robot.lift.lift.isBusy() && gamepad1.right_trigger > robot.triggerDeadzone) {
                robot.claw.release();
                robot.activeConeState = Robot.coneStates.NONE;
            }
        }


//        telemetry.addData("Red 1 ", robot.freightDetector1.red()); TODO ADD TELEMETRY
    }

    @Override
    public void stop() {

    }

}
