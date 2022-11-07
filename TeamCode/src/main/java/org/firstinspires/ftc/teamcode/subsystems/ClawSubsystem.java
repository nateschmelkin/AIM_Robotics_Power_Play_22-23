package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.Robot;

public class ClawSubsystem{

    public HardwareMap hwMap = null;

    public Servo claw;
    public Servo claw1;

    public void initClaw(HardwareMap ahwMap) {
        hwMap = ahwMap;
        claw = hwMap.get(Servo.class, "claw");
        claw1 = hwMap.get(Servo.class, "claw1");
        claw.setDirection(Servo.Direction.REVERSE);

        claw1.setDirection(Servo.Direction.REVERSE);

    }

    public void grab() {
        claw.setPosition(.06);
        claw1.setPosition(.72);
    }

    public void release() {
        claw.setPosition(0);
        claw1.setPosition(1);
    }

}
