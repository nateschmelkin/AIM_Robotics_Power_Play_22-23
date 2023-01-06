package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ClawSubsystem{

    public HardwareMap hwMap = null; // Local access to hardware map

    public CRServo claw; // Servo hand one
    public CRServo claw1; // Servo hand two

    // initClaw initializes the servos and sets their directions
    public void initClaw(HardwareMap ahwMap) {
        hwMap = ahwMap;
        claw = hwMap.get(CRServo.class, "claw");
        claw1 = hwMap.get(CRServo.class, "claw1");
        claw.setDirection(CRServo.Direction.REVERSE);

        claw1.setDirection(CRServo.Direction.REVERSE);

    }

    // grab turns on the claw servos
    public void grab() {
        claw.setPower(1);
        claw1.setPower(1);
    }

    // release reverses the claw servos
    public void release() {
        claw.setPower(-1);
        claw1.setPower(-1);
    }

    // off stops the claw servos
    public void off() {
        claw.setPower(0);
        claw1.setPower(0);
    }

}
