package org.firstinspires.ftc.teamcode.mechanisms.RobotNameChooChoo;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.baseCode.hardware.PassiveIntake;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotChooChooMotor extends PassiveIntake {

    public double gearRatio = 1;

    public double toleranceDegrees = 3;

    public RobotChooChooMotor(Gamepad gamepad1, Telemetry telemetry, HardwareMap hardwareMap){
        super(1, new String[]{"chooChooMotor"}, 387.5, gamepad1, telemetry, hardwareMap);

        motors[0].setBreakMode();
    }

    //spins toward the target using the encoder, stops once it gets there
    public void setPosition(double targetDegrees){
        double error = targetDegrees - getPositionDegrees();

        if (Math.abs(error) < toleranceDegrees) {
            setPower(0);
        } else if (error > 0) {
            setPower(1);
        } else {
            setPower(-1);
        }
    }

    public double getPositionDegrees(){
        return motors[0].getCurrPosDegrees() * gearRatio;
    }
}