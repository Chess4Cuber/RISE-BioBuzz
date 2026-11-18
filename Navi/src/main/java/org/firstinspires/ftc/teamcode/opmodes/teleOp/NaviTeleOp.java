package org.firstinspires.ftc.teamcode.opmodes.teleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.mechanisms.NaviFlowerSystem.NaviFlowerArmSystem;
import org.firstinspires.ftc.teamcode.mechanisms.NaviFlowerSystem.NaviFlowerStates;
import org.firstinspires.ftc.teamcode.mechanisms.NaviGate;
import org.firstinspires.ftc.teamcode.mechanisms.RadahnChassis;
import org.firstinspires.ftc.teamcode.mechanisms.motorIntakeSystem.NaviMotorIntakeSystem;
import org.firstinspires.ftc.teamcode.mechanisms.NaviChooChooSystem.NaviChooChooSystem;

@TeleOp
public class NaviTeleOp extends LinearOpMode {

    RadahnChassis chassis;
    NaviMotorIntakeSystem intake;
    NaviChooChooSystem chooChoo;
    NaviFlowerArmSystem flowerArm;
    NaviGate gate;

    public ElapsedTime runtime = new ElapsedTime();
    double previousTime = 0;

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        chassis = new RadahnChassis(gamepad1, telemetry, hardwareMap);
        intake = new NaviMotorIntakeSystem(gamepad1, telemetry, hardwareMap);
        chooChoo = new NaviChooChooSystem(gamepad1, telemetry, hardwareMap);
        flowerArm = new NaviFlowerArmSystem(gamepad1, telemetry, hardwareMap);
        gate = new NaviGate(gamepad1, hardwareMap);

        while (opModeInInit()) {
            gate.openClaw();

            flowerArm.setFlowerState(NaviFlowerStates.INITIALIZED);

            telemetry.addLine("Waiting For Start");
            telemetry.update();
        }

        while (opModeIsActive()) {

            chassis.robotCentricDrive();
            chassis.updatePose();

            intake.controllerInput();
            intake.setPositions();

            chooChoo.controllerInput();
            chooChoo.setPositions();

            flowerArm.setPositions();
            flowerArm.controllerInput();

            gate.toggleClaw();

            telemetry.addData("Loop Time", runtime.seconds() - previousTime);
            chooChoo.displayTelemetry();
            telemetry.update();

            previousTime = runtime.seconds();
        }
    }
}