package org.firstinspires.ftc.teamcode.hardware.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DrivebaseSubsystem{

    public HardwareMap hwMap = null; // Local access to hardware map

    public DcMotorEx leftFront; // Front left motor
    public DcMotorEx rightFront; // Front right motor
    public DcMotorEx leftRear; // Back left motor
    public DcMotorEx rightRear; // Back right motor


    public double strafingSense; // Multiplier to clamp strafing speed
    public double turningSense; // Multiplier to clamp turning speed
    public double maxSpeed; // Multiplier to clamp speed

    double frontLeftPower; // Local access to flp
    double backLeftPower; // Local access to blp
    double frontRightPower; // Local access to frp
    double backRightPower; // Local access to brp

    // initDrivebaes initializes the drivebase motors and reverses the direction of some
    // To make meccanum movement work.
    public void initDrivebase(HardwareMap ahwMap) {

        hwMap = ahwMap;

        leftFront = hwMap.get(DcMotorEx.class, "fld");
        rightFront = hwMap.get(DcMotorEx.class, "frd");
        leftRear = hwMap.get(DcMotorEx.class, "bld");
        rightRear = hwMap.get(DcMotorEx.class, "brd");

        rightFront.setDirection(DcMotorEx.Direction.REVERSE);
        rightRear.setDirection(DcMotorEx.Direction.REVERSE);

    }

    // setZeroBehavior sets zero power behavior for all drivebase motors
    public void setZeroBehavior() {
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    // setVelos inputs gamepad stick values and then converts them into motor powers
    public void setVelos(double xLeftInput, double yLeftInput, double xRightInput, double deadzone) {

        double xVelo;
        double yVelo;
        double rxVelo;

        if (Math.abs(xLeftInput) > deadzone) {
            xVelo = xLeftInput * strafingSense;
        } else {
            xVelo = 0;
        }
        if (Math.abs(yLeftInput) > deadzone) {
            yVelo = yLeftInput * strafingSense;
        } else {
            yVelo = 0;
        }
        if (Math.abs(xRightInput) > deadzone) {
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

    // applyVelos applies the motor powers the the motors with a maxSpeed limiting factor
    public void applyVelos() {
        leftFront.setPower(frontLeftPower * maxSpeed);
        leftRear.setPower(backLeftPower * maxSpeed);
        rightFront.setPower(frontRightPower * maxSpeed);
        rightRear.setPower(backRightPower * maxSpeed);
    }

}
