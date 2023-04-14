package org.firstinspires.ftc.teamcode.samples;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.mechanismClasses.ScoringSystem;


@TeleOp(name="LiftClawMechanismTest", group="Samples")
public class LiftClawMechanismTest extends OpMode {

    private ScoringSystem scoringSystem = new ScoringSystem();

    @Override
    public void init() {
        scoringSystem.init(hardwareMap);
    }

    @Override
    public void init_loop() {

    }


    @Override
    public void start() {

    }

    @Override
    public void loop() {
        scoringSystem.loop(gamepad2);
    }

    @Override
    public void stop() {

    }
}
