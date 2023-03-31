package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class RunToPositionMotorUtil {

    public void motorToPosition(DcMotorEx motor, double speed, int position) {
        int target = position;
        motor.setTargetPosition(target);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motor.setPower(Math.abs(speed));

        if (motor.isBusy()) {

        } else {
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public boolean checkClose(DcMotorEx motor, int howClose) {
        if (Math.abs(motor.getTargetPosition() - motor.getCurrentPosition()) < howClose) {
            return true;
        } else {
            return false;
        }
    }

    public void dropPosition(DcMotorEx motor, double speed, int ticks) {
        int target = motor.getCurrentPosition() - ticks;
        motor.setTargetPosition(target);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motor.setPower(Math.abs(speed));

        if (motor.isBusy()) {

        } else {
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

    }

    public boolean detectVoltageSpike(DcMotorEx motor, double currentThreshold) { // TODO: EXPERIMENTAL
        if (motor.getCurrent(CurrentUnit.AMPS) > currentThreshold) return true;
        return false;
    }



}
