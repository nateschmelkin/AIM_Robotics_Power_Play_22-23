package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.Robot;

public class ClawSubsystem{

    public HardwareMap hwMap = null;

    public Servo claw = null;

    public void initClaw(HardwareMap ahwMap) {
        hwMap = ahwMap;

        claw = hwMap.get(Servo.class, "claw");
    }

    public void grab() {
        claw.setPosition(1);
    }

    public void release() {
        claw.setPosition(-1);
    }

}
