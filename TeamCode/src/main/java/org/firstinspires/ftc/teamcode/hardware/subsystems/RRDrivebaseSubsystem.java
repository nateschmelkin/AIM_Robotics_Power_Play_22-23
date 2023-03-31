package org.firstinspires.ftc.teamcode.hardware.subsystems;

import static org.firstinspires.ftc.teamcode.drive.DriveConstants.BACK_LEFT_DRIFT_MULTIPLIER;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.BACK_RIGHT_DRIFT_MULTIPLIER;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.EXPONENT_MODIFIER;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.FRONT_LEFT_DRIFT_MULTIPLIER;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.FRONT_RIGHT_DRIFT_MULTIPLIER;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.OPTIMAL_VOLTAGE;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.STRAFE_MULTIPLIER;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.VRX_WEIGHT;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.VX_WEIGHT;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.VY_WEIGHT;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class RRDrivebaseSubsystem extends SampleMecanumDrive{

    double desiredFrontLeftPower; // Local access to flp
    double desiredBackLeftPower; // Local access to blp
    double desiredFrontRightPower; // Local access to frp
    double desiredBackRightPower; // Local access to brp

    public RRDrivebaseSubsystem(HardwareMap hardwareMap) {
        super(hardwareMap);
    }

    // setVelos inputs gamepad stick values and then converts them into motor powers
    public void setVelos(Pose2d drivePower) {

        double poweredX = poweredInput(drivePower.getX() * STRAFE_MULTIPLIER) * VX_WEIGHT;
        double poweredY = poweredInput(drivePower.getY()) * VY_WEIGHT;
        double poweredRX = poweredInput(drivePower.getHeading()) * VRX_WEIGHT;

        double denominator = Math.max(Math.abs(poweredY) + Math.abs(poweredX) + Math.abs(poweredRX), 1);

        desiredFrontLeftPower = (poweredY + poweredX - poweredRX) / denominator;
        desiredBackLeftPower = (poweredY - poweredX - poweredRX) / denominator;
        desiredFrontRightPower = (poweredY - poweredX + poweredRX) / denominator;
        desiredBackRightPower = (poweredY + poweredX + poweredRX) / denominator;
    }

    // applyVelos applies the motor powers the the motors with a maxSpeed limiting factor
    public void applyVelos() {
        double batteryMultiplier = OPTIMAL_VOLTAGE/batteryVoltageSensor.getVoltage();
        setMotorPowers(desiredFrontLeftPower * batteryMultiplier * FRONT_LEFT_DRIFT_MULTIPLIER,
                desiredBackLeftPower * batteryMultiplier * BACK_LEFT_DRIFT_MULTIPLIER,
                desiredBackRightPower * batteryMultiplier * BACK_RIGHT_DRIFT_MULTIPLIER,
                desiredFrontRightPower * batteryMultiplier * FRONT_RIGHT_DRIFT_MULTIPLIER);
    }

    public double poweredInput(double base) {
        if (EXPONENT_MODIFIER % 2 == 0) {
            return Math.pow(base, EXPONENT_MODIFIER) * Math.signum(base);
        } else {
            return Math.pow(base, EXPONENT_MODIFIER);
        }
    }

}
