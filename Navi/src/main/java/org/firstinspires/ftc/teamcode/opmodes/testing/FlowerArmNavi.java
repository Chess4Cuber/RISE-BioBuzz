package org.firstinspires.ftc.teamcode.opmodes.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.mechanisms.NaviFlowerSystem.NaviFlowerArmSystem;
import org.firstinspires.ftc.teamcode.mechanisms.RadahnChassis;
import org.firstinspires.ftc.teamcode.mechanisms.otherMechanisms.RadahnTransfer.RadahnServoIntakeSystem;

@TeleOp
public class FlowerArmNavi extends LinearOpMode {
    NaviFlowerArmSystem flowerSystem;

    public ElapsedTime runtime = new ElapsedTime();
    double previousTime = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        flowerSystem = new NaviFlowerArmSystem(gamepad1, telemetry, hardwareMap);

        while (opModeInInit()){
            telemetry.addLine("Waiting For Start");
            telemetry.update();
        }

        while (opModeIsActive()){

            flowerSystem.controllerInput();
            flowerSystem.setPositions();

//            telemetry.addData("Pose Estimate", chassis.getPose());
            telemetry.addData("loop time", runtime.seconds()-previousTime);
            telemetry.update();

            previousTime = runtime.seconds();
        }
    }
}
