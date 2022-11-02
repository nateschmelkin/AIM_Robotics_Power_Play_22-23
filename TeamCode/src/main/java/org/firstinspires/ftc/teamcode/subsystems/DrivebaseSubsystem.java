package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DrivebaseSubsystem{


    public HardwareMap hwMap = null;

    public DcMotorEx leftFront = null;
    public DcMotorEx rightFront = null;
    public DcMotorEx leftRear = null;
    public DcMotorEx rightRear = null;

    double maxSpeed = .8;
    double drivebaseDeadzone = .3;

    double xVelo = 0;
    double yVelo = 0;
    double rxVelo = 0;

    double strafingSense = .7;
    double turningSense = .7;

    double frontLeftPower;
    double backLeftPower;
    double frontRightPower;
    double backRightPower;

    public void initDrivebase(HardwareMap ahwMap) {
        hwMap = ahwMap;

        leftFront = hwMap.get(DcMotorEx.class, "fld");
        rightFront = hwMap.get(DcMotorEx.class, "frd");
        leftRear = hwMap.get(DcMotorEx.class, "bld");
        rightRear = hwMap.get(DcMotorEx.class, "brd");
    }

    public void setVelos() {
        if (Math.abs(-gamepad1.left_stick_x) > drivebaseDeadzone) {
            xVelo = -gamepad1.left_stick_x * strafingSense;
        } else {
            xVelo = 0;
        }
        if (Math.abs(gamepad1.left_stick_y) > drivebaseDeadzone) {
            yVelo = gamepad1.left_stick_y * strafingSense;
        } else {
            yVelo = 0;
        }
        if (Math.abs(gamepad1.right_stick_x) > drivebaseDeadzone) {
            rxVelo = gamepad1.right_stick_x * turningSense;
        } else {
            rxVelo = 0;
        }

        double denominator = Math.max(Math.abs(yVelo) + Math.abs(xVelo) + Math.abs(rxVelo), 1);

        frontLeftPower = (yVelo + xVelo + rxVelo) / denominator;
        backLeftPower = (yVelo - xVelo - rxVelo) / denominator;
        frontRightPower = (yVelo - xVelo + rxVelo) / denominator;
        backRightPower = (yVelo + xVelo - rxVelo) / denominator;
    }

    public void setZeroBehavior() {
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void applyVelos() {
        leftFront.setPower(frontLeftPower * maxSpeed);
        leftRear.setPower(backLeftPower * maxSpeed);
        rightFront.setPower(frontRightPower * maxSpeed);
        rightRear.setPower(backRightPower * maxSpeed);
    }

}
