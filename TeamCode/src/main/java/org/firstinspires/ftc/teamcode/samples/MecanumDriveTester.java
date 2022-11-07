package org.firstinspires.ftc.teamcode.samples;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Robot;

import java.lang.*;


@TeleOp(name="mecanumDriveTester", group="Samples")

public class MecanumDriveTester extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();
    Robot robot = new Robot(hardwareMap);
    double deadzone = .3;
    double maxSpeed = .8;
    double x = 0;
    double y = 0;
    double rx = 0;



    @Override
    public void init() {
        robot.initRobot(hardwareMap);
        telemetry.addData("Status", "Initialized");
        robot.drivebase.setZeroBehavior();
    }


    @Override
    public void init_loop() {
    }


    @Override
    public void start() {
    }


    @Override
    public void loop() {

        robot.drivebase.setVelos(-gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
        robot.drivebase.applyVelos();
    }

    @Override
    public void stop() {
    }

}
