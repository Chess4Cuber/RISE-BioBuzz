package org.firstinspires.ftc.teamcode.opmodes.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.mechanisms.RadahnChassis;
import org.firstinspires.ftc.teamcode.mechanisms.SimpleChooChoo.SimpleChooChooSystem;
import org.firstinspires.ftc.teamcode.mechanisms.otherMechanisms.RadahnTransfer.RadahnServoIntakeSystem;

@TeleOp
public class ChooChooTesting extends LinearOpMode {
    //RadahnChassis chassis;
    SimpleChooChooSystem chooChoo;
    public ElapsedTime runtime = new ElapsedTime();
    double previousTime = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        //chassis = new RadahnChassis(gamepad1, telemetry, hardwareMap);

        chooChoo = new SimpleChooChooSystem(gamepad1, telemetry, hardwareMap);

        while (opModeInInit()){
            telemetry.addLine("Waiting For Start");
            telemetry.update();
        }

        while (opModeIsActive()){
//            chassis.robotCentricDrive();
//            chassis.updatePose();

            chooChoo.controllerInput();
            chooChoo.setPositions();

//            telemetry.addData("Pose Estimate", chassis.getPose());
            telemetry.addData("loop time", runtime.seconds()-previousTime);
            telemetry.update();

            previousTime = runtime.seconds();
        }
    }
}
