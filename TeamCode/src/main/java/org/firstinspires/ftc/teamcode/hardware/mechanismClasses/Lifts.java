package org.firstinspires.ftc.teamcode.hardware.mechanismClasses;

import com.aimrobotics.aimlib.util.Mechanism;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.stuyfission.fissionlib.motion.MotionProfiledDcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Lifts extends Mechanism {

    public MotionProfiledDcMotor frontLift;
    public MotionProfiledDcMotor backLift;

    private static final double WHEEL_RADIUS = 0.7519685;
    private static final double GEAR_RATIO = 1.0;
    private static final double TICKS_PER_REV = 537.6;

    public static double MAX_VEL = 100;
    public static double MAX_ACCEL = 100;
    public static double RETRACTION_MULTIPLIER = 0.75;

    public static double kP = 0.25;
    public static double kI = 0.0;
    public static double kD = 0.0;
    public static double kF = 0.0;

    // TODO CHANGE THESE FOR NEW MOTION
    public static  double LOW_POS = 15.5;
    public static  double MEDIUM_POS = 26.5;
    public static double HIGH_POS = 38.4;
    public static double PICKUP_POS = 4.5;

    @Override
    public void init(HardwareMap hwMap) {
        frontLift = new MotionProfiledDcMotor(hwMap, "frontLift");
        frontLift.setWheelConstants(WHEEL_RADIUS, GEAR_RATIO, TICKS_PER_REV);
        frontLift.setRetractionMultiplier(RETRACTION_MULTIPLIER);
        frontLift.setPIDCoefficients(kP, kI, kD, kF);
        frontLift.setTargetPosition(0);
        // TODO set directions
        frontLift.setDirection(DcMotorSimple.Direction.FORWARD);

        backLift = new MotionProfiledDcMotor(hwMap, "backLift");
        backLift.setWheelConstants(WHEEL_RADIUS, GEAR_RATIO, TICKS_PER_REV);
        backLift.setRetractionMultiplier(RETRACTION_MULTIPLIER);
        backLift.setPIDCoefficients(kP, kI, kD, kF);
        backLift.setTargetPosition(0);
        // TODO set directions
        backLift.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setMotionConstraints(double vel, double accel) {
        frontLift.setMotionConstraints(vel, accel);
        backLift.setMotionConstraints(vel, accel);
    }

    public void extendToLow(MotionProfiledDcMotor lift) {
        lift.setTargetPosition(LOW_POS);
    }

    public void extendToMedium(MotionProfiledDcMotor lift) {
        lift.setTargetPosition(MEDIUM_POS);
    }

    public void extendToHigh(MotionProfiledDcMotor lift) {
        lift.setTargetPosition(HIGH_POS);
    }

    public void extendToPickup(MotionProfiledDcMotor lift) {
        lift.setTargetPosition(PICKUP_POS);
    }

    public void extendToPosition(MotionProfiledDcMotor lift, double position) {
        lift.setTargetPosition(position);
    }

    public void dropPos(MotionProfiledDcMotor lift) {
        lift.setTargetPosition(lift.getPosition() - 0.5); //TODO DETERMINE AMT
    }

    public void ascendPos(MotionProfiledDcMotor lift) {
        lift.setTargetPosition(lift.getPosition() + 0.5); //TODO DETERMINE AMT
    }

    public void setPower(MotionProfiledDcMotor lift, double power) {
        lift.setPower(power);
    }

    public void updatePositions() {
        frontLift.update();
        backLift.update();
    }

    @Override
    public void telemetry(Telemetry telemetry) {
//        telemetry.addData()
    }
}
