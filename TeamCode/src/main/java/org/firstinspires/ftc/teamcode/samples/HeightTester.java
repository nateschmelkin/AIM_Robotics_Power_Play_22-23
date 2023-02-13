package org.firstinspires.ftc.teamcode.samples;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@TeleOp(name="HeightTester", group="Samples")

public class HeightTester extends OpMode {

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

//        if (gamepad1.a) {
//            robot.lift.setHeight(LiftSubsystem.Level.HIGH);
//        } else if (gamepad1.b) {
//            robot.lift.setHeight(LiftSubsystem.Level.MEDIUM);
//        } else if (gamepad1.y) {
//            robot.lift.setHeight(LiftSubsystem.Level.LOW);
//        } else if (gamepad1.x) {
//            robot.lift.setHeight(LiftSubsystem.Level.GROUND);
//        } else if (gamepad1.dpad_up) {
//            robot.lift.setHeight(LiftSubsystem.Level.PICKUP);
//        } else if (gamepad1.dpad_down) {
//            robot.lift.setHeight(LiftSubsystem.Level.RESET);
//        }

//        if (gamepad1.dpad_left) {
//            robot.claw.grab();
//        } else if (gamepad1.dpad_right) {
//            robot.claw.release();
//        }


//        telemetry.addData("Lift Current Position: ", robot.lift.lift.getCurrentPosition());
//        telemetry.addData("Lift Target Position: ", robot.lift.lift.getTargetPosition());
    }

    @Override
    public void stop() {

    }

}
