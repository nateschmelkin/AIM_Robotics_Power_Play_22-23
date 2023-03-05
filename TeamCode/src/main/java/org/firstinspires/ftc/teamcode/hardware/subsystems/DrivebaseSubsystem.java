package org.firstinspires.ftc.teamcode.hardware.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.TeleOp.DriverPreset;

import java.util.Arrays;
import java.util.List;

public class DrivebaseSubsystem{

    public HardwareMap hwMap = null; // Local access to hardware map

    public DcMotorEx leftFront; // Front left motor
    public DcMotorEx rightFront; // Front right motor
    public DcMotorEx leftRear; // Back left motor
    public DcMotorEx rightRear; // Back right motor

    private List<DcMotorEx> motors;


    public double strafingSense; // Multiplier to clamp strafing speed
    public double turningSense; // Multiplier to clamp turning speed
    public double maxSpeed; // Multiplier to clamp speed
    public double stickDeadzone;

    double frontLeftPower; // Local access to flp
    double backLeftPower; // Local access to blp
    double frontRightPower; // Local access to frp
    double backRightPower; // Local access to brp

    double strafeMult = 1.1;

    int exponent = 2;

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

        motors = Arrays.asList(leftFront, leftRear, rightRear, rightFront);

        setZeroBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    // setZeroBehavior sets zero power behavior for all drivebase motors
    public void setZeroBehavior(DcMotor.ZeroPowerBehavior behavior) {
        for (DcMotorEx motor : motors) {
            motor.setZeroPowerBehavior(behavior);
        }
//        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    // setVelos inputs gamepad stick values and then converts them into motor powers
    public void setVelos(double xLeftInput, double yLeftInput, double xRightInput) {

        double xVelo;
        double yVelo;
        double rxVelo;

        if (Math.abs(xLeftInput) > stickDeadzone) {
            double poweredxLeftInput = poweredInput(xLeftInput * strafeMult);
//            double poweredxLeftInput = Math.pow(xLeftInput * strafeMult, exponent) * Math.signum(xLeftInput);
            xVelo = poweredxLeftInput * strafingSense;
        } else {
            xVelo = 0;
        }
        if (Math.abs(yLeftInput) > stickDeadzone) {
            double poweredyLeftInput = poweredInput(yLeftInput);
//            double poweredyLeftInput = Math.pow(yLeftInput, exponent) * Math.signum(yLeftInput);
            yVelo = poweredyLeftInput * strafingSense;
        } else {
            yVelo = 0;
        }
        if (Math.abs(xRightInput) > stickDeadzone) {
            double poweredxRightInput = poweredInput(xRightInput);
//            double poweredxRightInput = Math.pow(xRightInput, exponent) * Math.signum(xRightInput);
            rxVelo = poweredxRightInput * turningSense;
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

    public void setActiveDrivingPresets(DriverPreset drivebaseDriver) {
        strafingSense = drivebaseDriver.strafeMultiplier;
        turningSense = drivebaseDriver.turnMultiplier;
        maxSpeed = drivebaseDriver.maxSpeedMultiplier;
        stickDeadzone = drivebaseDriver.stickDeadzone;
        exponent = drivebaseDriver.exponentialDriveModifier;
    }

    public double poweredInput(double base) {
        if (exponent % 2 == 0) {
            return Math.pow(base, exponent) * Math.signum(base);
        } else {
            return Math.pow(base, exponent);
        }
    }

}
