package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.subsystems.LiftSubsystem;

@TeleOp(name="AIMTeleOp", group="TeleOp")

public class AIMTeleOp extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();
    Robot robot = new Robot(hardwareMap, true);



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

        // Sets and then applies velos to drivebase based on gamepad 1 stick inputs.
        robot.drivebase.setVelos(-gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
        robot.drivebase.applyVelos();

        switch (robot.activeConeState) {

            // First state of arm automation. Sets height of lift to RESET and releases claw.
            // If a cone is detected and the lift is at the correct height switch to case READYFORPICKUP
            case NONE:
                robot.lift.setHeight(LiftSubsystem.Level.RESET);
                robot.claw.off();
                if (robot.csController.checkCone(1) && robot.lift.activeHeight == LiftSubsystem.Level.RESET && robot.lift.checkClose(robot.lift.lift, 15)) {
                    robot.activeConeState = Robot.coneStates.READYFORPICKUP;
                }

            // Preps arm to grab cone. Turns to active cone side. Sets to pickup height.
            // If lift is at pickup height then grab and switch to case GRABBED
            case READYFORPICKUP:
                    robot.lift.setHeight(LiftSubsystem.Level.PICKUP);
                    robot.claw.grab();
                if (robot.csController.checkCone(1) && robot.lift.activeHeight == LiftSubsystem.Level.PICKUP && robot.lift.checkClose(robot.lift.lift, 15)) {
                    robot.activeConeState = Robot.coneStates.GRABBED;
                }

            // Here button inputs on gamepad 2 dictate the height of the lift. If the lift is not moving
            // And gamepad 2 right trigger is pressed then release cone and switch case to DROPPING
            case GRABBED:
//                if (gamepad2.y) {
//                    robot.lift.setHeight(LiftSubsystem.Level.HIGH, 1);
//                } else if (gamepad2.b) {
//                    robot.lift.setHeight(LiftSubsystem.Level.MEDIUM, 1);
//                } else if (gamepad2.x) {
//                    robot.lift.setHeight(LiftSubsystem.Level.LOW, 1);
//                } else if (gamepad2.a) {
//                    robot.lift.setHeight(LiftSubsystem.Level.GROUND, 1);
//                } else

                if (Math.abs(gamepad2.left_stick_y) > robot.drivebase.drivebaseDeadzone) {
                    robot.lift.activeHeight = LiftSubsystem.Level.CUSTOM;
                    robot.lift.userAdjustHeight(-gamepad2.left_stick_y);
                } else if ((robot.lift.checkClose(robot.lift.lift, 15) || robot.lift.activeHeight == LiftSubsystem.Level.CUSTOM) && gamepad2.right_trigger > robot.triggerDeadzone)  { // Either lift is stopped at preset height or at any other custom height
                    robot.activeConeState = Robot.coneStates.DROPPING;
                }

            // Run little automation to slightly lower arm to ensure cone is on pole
            // Once complete, switch to state DROPPED
            case DROPPING:
                robot.lift.dropPosition(robot.lift.lift, 1, 200);
                if (gamepad2.left_trigger > robot.triggerDeadzone) { // Arbitrary button input TODO SET BUTTON INPUT
                    robot.activeConeState = Robot.coneStates.GRABBED;
                }
                if (robot.lift.checkClose(robot.lift.lift, 15)) {
                    robot.claw.release();
                    if (!robot.csController.checkCone(1)) {
                        robot.activeConeState = Robot.coneStates.NONE;
                    }
                }
        }


        telemetry.addData("Lift Current Position: ", robot.lift.lift.getCurrentPosition());
        telemetry.addData("Lift Target Position: ", robot.lift.lift.getTargetPosition());
        telemetry.addData("Lift Target Height: ", robot.lift.activeHeight);
        telemetry.addData("Spinner Current Position: ", robot.spinner.spinner.getCurrentPosition());
        telemetry.addData("Spinner Target Position: ", robot.spinner.spinner.getTargetPosition());
        telemetry.addData("Spinner Target Side: ", robot.spinner.activeSide);

        telemetry.addData("Active State: ", robot.activeConeState);

        telemetry.addData("cs1Val: ", robot.spinner.csVals[0]);
        telemetry.addData("cs1Val True: ", robot.spinner.checkCone(1));
        telemetry.addData("cs2Val: ", robot.spinner.csVals[1]);
        telemetry.addData("cs2Val True: ", robot.spinner.checkCone(2));
    }

    @Override
    public void stop() {

    }

}
