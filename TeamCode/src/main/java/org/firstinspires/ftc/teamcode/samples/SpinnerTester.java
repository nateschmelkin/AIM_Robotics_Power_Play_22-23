package org.firstinspires.ftc.teamcode.samples;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.samples.MecanumHardware;
import org.firstinspires.ftc.teamcode.subsystems.*;

@TeleOp(name="SpinnerTester", group="Samples")

public class SpinnerTester extends OpMode {

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

        robot.lift.setHeight(LiftSubsystem.Level.RESET, 1);

        if (gamepad1.a) {
            robot.spinner.setSide(SpinnerSubsystem.Side.FORWARD, .4);
        } else if (gamepad1.b) {
            robot.spinner.setSide(SpinnerSubsystem.Side.RIGHT, .4);
        } else if (gamepad1.y) {
            robot.spinner.setSide(SpinnerSubsystem.Side.LEFT, .4);
        } else if (gamepad1.x) {
            robot.spinner.setSide(SpinnerSubsystem.Side.BACK, .4);
        }


        telemetry.addData("Spinner Current Position: ", robot.spinner.spinner.getCurrentPosition());
        telemetry.addData("Spinner Target Position: ", robot.spinner.spinner.getTargetPosition());
    }

    @Override
    public void stop() {

    }

}
