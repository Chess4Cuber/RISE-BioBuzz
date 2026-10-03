package org.firstinspires.ftc.teamcode.opmodes.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.mechanisms.RobotNameChassis;
import org.firstinspires.ftc.teamcode.mechanisms.RobotNameChooChoo.RobotChooChooMotorSystem;

@TeleOp
public class ChooChooTesting extends LinearOpMode {
    RobotNameChassis chassis;
    RobotChooChooMotorSystem chooChooMotorSystem;

    public ElapsedTime runtime = new ElapsedTime();
    double previousTime = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        chassis = new RobotNameChassis(gamepad1, telemetry, hardwareMap);
        chooChooMotorSystem = new RobotChooChooMotorSystem(gamepad1, telemetry, hardwareMap);

        telemetry.addLine("Waiting For Start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()){
            chassis.robotCentricDrive();
            chassis.updatePose();

            chooChooMotorSystem.controllerInput();
            chooChooMotorSystem.setPositions();

            telemetry.addData("loop time", runtime.seconds()-previousTime);
            telemetry.update();

            previousTime = runtime.seconds();
        }
    }
}