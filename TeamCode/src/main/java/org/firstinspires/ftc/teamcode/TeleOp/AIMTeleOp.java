package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.samples.MecanumHardware;
import org.firstinspires.ftc.teamcode.subsystems.*;

@TeleOp(name="AIMTeleOp", group="TeleOp")

public class AIMTeleOp extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();
    Robot robot = new Robot(hardwareMap);



    @Override
    public void init() {
        robot.initRobot(hardwareMap);
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

        robot.drivebase.setVelos(-gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
        robot.drivebase.applyVelos();

        if (robot.activeConeState == Robot.coneStates.NONE) { // TODO COMMENT TO EXPLAIN ARM AUTOMATION
            robot.lift.setHeight(LiftSubsystem.Level.RESET, 1);
            robot.lift.activeHeight = LiftSubsystem.Level.RESET;
            robot.claw.release();
            if (robot.spinner.checkSides() && robot.lift.activeHeight == LiftSubsystem.Level.RESET && robot.lift.checkClose(robot.lift.lift, 15)) { // && !robot.lift.lift.isBusy()
                robot.activeConeState = Robot.coneStates.READYFORPICKUP;
            }
        }

        if (robot.activeConeState == Robot.coneStates.READYFORPICKUP) {
            robot.spinner.setSide(robot.spinner.activeSide, .5);
            if (robot.spinner.checkClose(robot.spinner.spinner, 15)) { //!robot.spinner.spinner.isBusy()
                robot.lift.setHeight(LiftSubsystem.Level.PICKUP, 1);
                robot.lift.activeHeight = LiftSubsystem.Level.PICKUP;
                telemetry.addData("This just ran: ", true);
            }
        }

        if (robot.lift.activeHeight == LiftSubsystem.Level.PICKUP && robot.lift.checkClose(robot.lift.lift, 15)) { // && !robot.lift.lift.isBusy()
            robot.claw.grab();
            robot.activeConeState = Robot.coneStates.GRABBED;
        }

        if (robot.activeConeState == Robot.coneStates.GRABBED) {
            if (gamepad2.y) {
                robot.lift.setHeight(LiftSubsystem.Level.HIGH, 1);
                robot.lift.activeHeight = LiftSubsystem.Level.HIGH;
            } else if (gamepad2.b) {
                robot.lift.setHeight(LiftSubsystem.Level.MEDIUM, 1);
                robot.lift.activeHeight = LiftSubsystem.Level.MEDIUM;
            } else if (gamepad2.x) {
                robot.lift.setHeight(LiftSubsystem.Level.LOW, 1);
                robot.lift.activeHeight = LiftSubsystem.Level.LOW;
            } else if (gamepad2.a) {
                robot.lift.setHeight(LiftSubsystem.Level.GROUND, 1);
                robot.lift.activeHeight = LiftSubsystem.Level.GROUND;
            } else if (robot.lift.checkClose(robot.lift.lift, 15) && gamepad2.right_trigger > robot.triggerDeadzone) { // && !robot.lift.lift.isBusy()
                robot.claw.release();
                robot.activeConeState = Robot.coneStates.DROPPED;
            }
        }

        if (robot.activeConeState == Robot.coneStates.DROPPING) {
            robot.lift.dropPosition(robot.lift.lift, 1);
            if (robot.lift.checkClose(robot.lift.lift, 15)) {
                robot.activeConeState = Robot.coneStates.DROPPED;
            }
        }



        if (robot.activeConeState == Robot.coneStates.DROPPED) {
            if (gamepad2.left_trigger > robot.triggerDeadzone) {
                robot.activeConeState = Robot.coneStates.NONE;
            }
        }


        telemetry.addData("Lift Current Position: ", robot.lift.lift.getCurrentPosition());
        telemetry.addData("Lift Target Position: ", robot.lift.lift.getTargetPosition());
        telemetry.addData("Lift Target Height: ", robot.lift.activeHeight);
        telemetry.addData("Spinner Current Position: ", robot.spinner.spinner.getCurrentPosition());
        telemetry.addData("Spinner Target Position: ", robot.spinner.spinner.getTargetPosition());
        telemetry.addData("Spinner Target Side: ", robot.spinner.activeSide);

        telemetry.addData("Is Lift Busy: ", robot.lift.lift.isBusy());
        telemetry.addData("Is Spinner Busy: ", robot.spinner.spinner.isBusy());

        telemetry.addData("Lift Power: ", robot.lift.lift.getPower());
        telemetry.addData("Spinner Power: ", robot.spinner.spinner.getPower());

        telemetry.addData("Active State: ", robot.activeConeState);

        telemetry.addData("cs1Red: ", robot.spinner.cs1.red());
        telemetry.addData("cs1Red True: ", robot.spinner.checkConeRed(robot.spinner.cs1));
        telemetry.addData("cs2Red: ", robot.spinner.cs2.red());
        telemetry.addData("cs2Red True: ", robot.spinner.checkConeRed(robot.spinner.cs2));
        telemetry.addData("cs3Red: ", robot.spinner.cs3.red());
        telemetry.addData("cs3Red True: ", robot.spinner.checkConeRed(robot.spinner.cs3));
        telemetry.addData("cs4Red: ", robot.spinner.cs4.red());
        telemetry.addData("cs4Red True: ", robot.spinner.checkConeRed(robot.spinner.cs4));

        telemetry.addData("cs1Blue: ", robot.spinner.cs1.blue());
        telemetry.addData("cs1Blue True: ", robot.spinner.checkConeBlue(robot.spinner.cs1));
        telemetry.addData("cs2Blue: ", robot.spinner.cs2.blue());
        telemetry.addData("cs2Blue True: ", robot.spinner.checkConeBlue(robot.spinner.cs2));
        telemetry.addData("cs3Blue: ", robot.spinner.cs3.blue());
        telemetry.addData("cs3Blue True: ", robot.spinner.checkConeBlue(robot.spinner.cs3));
        telemetry.addData("cs4Blue: ", robot.spinner.cs4.blue());
        telemetry.addData("cs4Blue True: ", robot.spinner.checkConeBlue(robot.spinner.cs4));

        telemetry.addData("Servo Position: ", robot.claw.claw.getPosition());

        telemetry.addData("Is Spinner Close: ", robot.spinner.checkClose(robot.spinner.spinner, 15));
    }

    @Override
    public void stop() {
        robot.lift.setHeight(LiftSubsystem.Level.RESET, .2);
    }

}
