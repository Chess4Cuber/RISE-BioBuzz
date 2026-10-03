package org.firstinspires.ftc.teamcode.opmodes.testing.OldTesting;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.mechanisms.RobotNameChassis;
import org.firstinspires.ftc.teamcode.mechanisms.otherMechanisms.turretSystem.RadahnTurretSystem;

@TeleOp
public class TurretTesting extends LinearOpMode {
    RadahnTurretSystem turret;
    RobotNameChassis chassis;

    public ElapsedTime runtime = new ElapsedTime();
    double previousTime = 0;

    @Override
    public void runOpMode() throws InterruptedException{
        chassis = new RobotNameChassis(gamepad1, telemetry, hardwareMap);
        turret = new RadahnTurretSystem(gamepad1, telemetry, hardwareMap);

        while (opModeInInit()) {
            telemetry.addLine("Waiting for start");
            telemetry.update();
        }

        while (opModeIsActive()) {
            turret.controllerInput();
            turret.setPositions();

            chassis.robotCentricDrive();
            chassis.updatePose();

            telemetry.update();

            previousTime = runtime.seconds();
        }
    }
}
