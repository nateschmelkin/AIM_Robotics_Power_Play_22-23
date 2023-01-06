package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.subsystems.LiftSubsystem;

@TeleOp(name="SuperQualTeleOp", group="TeleOp")

public class SuperQualTeleOp extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();
    Robot robot = new Robot(hardwareMap, true);



    @Override
    public void init() {
        robot.initRobot(hardwareMap);
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {

        // Sets and then applies velos to drivebase based on gamepad 1 stick inputs.
        robot.drivebase.setVelos(-gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
        robot.drivebase.applyVelos();

        if (gamepad2.a) {
            robot.lift.setHeight(LiftSubsystem.Level.LOW);
        } else if (gamepad2.b) {
            robot.lift.setHeight(LiftSubsystem.Level.MEDIUM);
        } else if (gamepad2.y) {
            robot.lift.setHeight(LiftSubsystem.Level.HIGH);
        } else if (gamepad2.x) {
            robot.lift.setHeight(LiftSubsystem.Level.GROUND);
        }

        if (gamepad2.right_stick_y > robot.drivebase.drivebaseDeadzone) {
            robot.lift.userAdjustHeight(gamepad2.right_stick_y);
        }

        if (gamepad2.right_trigger > robot.triggerDeadzone) {
            robot.claw.grab();
        } else if (gamepad2.left_trigger > robot.triggerDeadzone) {
            robot.claw.release();
        } else {
            robot.claw.off();
        }
    }

    @Override
    public void stop() {

    }

}
