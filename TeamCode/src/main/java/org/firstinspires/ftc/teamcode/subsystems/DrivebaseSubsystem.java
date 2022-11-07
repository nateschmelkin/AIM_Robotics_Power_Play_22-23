package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DrivebaseSubsystem{


    public HardwareMap hwMap = null;

    public DcMotorEx leftFront;
    public DcMotorEx rightFront;
    public DcMotorEx leftRear;
    public DcMotorEx rightRear;

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
        rightFront.setDirection(DcMotorEx.Direction.REVERSE);
        rightRear.setDirection(DcMotorEx.Direction.REVERSE);
    }

    public void setVelos(double xLeftInput, double yLeftInput, double xRightInput) {
        if (Math.abs(xLeftInput) > drivebaseDeadzone) {
            xVelo = xLeftInput * strafingSense;
        } else {
            xVelo = 0;
        }
        if (Math.abs(yLeftInput) > drivebaseDeadzone) {
            yVelo = yLeftInput * strafingSense;
        } else {
            yVelo = 0;
        }
        if (Math.abs(xRightInput) > drivebaseDeadzone) {
            rxVelo = xRightInput * turningSense;
        } else {
            rxVelo = 0;
        }

        double denominator = Math.max(Math.abs(yVelo) + Math.abs(xVelo) + Math.abs(rxVelo), 1);

        frontLeftPower = (yVelo + xVelo - rxVelo) / denominator;
        backLeftPower = (yVelo - xVelo - rxVelo) / denominator;
        frontRightPower = (yVelo - xVelo + rxVelo) / denominator;
        backRightPower = (yVelo + xVelo + rxVelo) / denominator;
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

    public void strafe(double flpower, double frpower, double blpower, double brpower) {
        leftFront.setPower(flpower);
        leftRear.setPower(blpower);
        rightFront.setPower(frpower);
        rightRear.setPower(brpower);
    }

    public void stopMovement() {
        leftFront.setPower(0);
        leftRear.setPower(0);
        rightFront.setPower(0);
        rightRear.setPower(0);
    }
}
