package org.firstinspires.ftc.teamcode.opmodes.testing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.mechanisms.NaviChooChooSystem.NaviChooChooSystem;

@TeleOp
public class ChooChooTesting extends LinearOpMode {

    NaviChooChooSystem chooChoo;

    public ElapsedTime runtime = new ElapsedTime();
    double previousTime = 0;

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        chooChoo = new NaviChooChooSystem(gamepad1, telemetry, hardwareMap);

        while (opModeInInit()) {

            telemetry.addLine("Waiting For Start");
            chooChoo.displayTelemetry();
            telemetry.update();
        }

        runtime.reset();

        while (opModeIsActive()) {

            chooChoo.controllerInput();
            chooChoo.setPositions();

            chooChoo.displayTelemetry();

            telemetry.addData("Loop Time", runtime.seconds() - previousTime);
            telemetry.update();

            previousTime = runtime.seconds();
        }
    }
}