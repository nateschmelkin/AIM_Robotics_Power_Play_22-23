package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DrivebaseSubsystem{


    public HardwareMap hwMap = null; // Local access to hardware map

    public DcMotorEx leftFront; // Front left motor
    public DcMotorEx rightFront; // Front right motor
    public DcMotorEx leftRear; // Back left motor
    public DcMotorEx rightRear; // Back right motor

    public DcMotorEx[] activeMotorOrder; // List to access all motors

    public double maxSpeed = .5; // Multiplier to clamp speed
    public double drivebaseDeadzone = .15; // Deadzone for gamepad sticks

    double strafingSense = .7; // Multiplier to clamp strafing speed
    double turningSense = .45; // Multiplier to clamp turning speed

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
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    // setVelos inputs gamepad stick values and then converts them into motor powers
    public void setVelos(double xLeftInput, double yLeftInput, double xRightInput) {

        double xVelo;
        double yVelo;
        double rxVelo;

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

    // applyVelos applies the motor powers the the motors with a maxSpeed limiting factor
    public void applyVelos() {
        leftFront.setPower(frontLeftPower * maxSpeed);
        leftRear.setPower(backLeftPower * maxSpeed);
        rightFront.setPower(frontRightPower * maxSpeed);
        rightRear.setPower(backRightPower * maxSpeed);
    }

    //
    //
    // EXPERIMENTAL TELEOP CAPABILITIES
    //
    //

    public void setFront(int howManySides) { // Motors go in clockwise order
        activeMotorOrder[0 + howManySides] = leftFront;
        activeMotorOrder[1 + howManySides] = rightFront;
        activeMotorOrder[2 + howManySides] = rightRear;
        activeMotorOrder[3 + howManySides] = leftRear;
    }

    //
    //
    // DEPRECIATED AUTO CAPABILITIES
    //
    //

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
