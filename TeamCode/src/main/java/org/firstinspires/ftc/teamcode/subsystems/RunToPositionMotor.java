package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class RunToPositionMotor {

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

}
