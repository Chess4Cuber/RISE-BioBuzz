package org.firstinspires.ftc.teamcode.mechanisms.NaviChooChooSystem;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.baseCode.control.PID_Controller;
import org.firstinspires.ftc.baseCode.hardware.PassiveIntake;
import org.firstinspires.ftc.robotcore.external.Telemetry;


public class NaviChooChoo extends PassiveIntake {

    public PID_Controller chooChooPID;

    private static final double ANGLE_TOLERANCE = 3.0;

    public NaviChooChoo(Gamepad gamepad1, Telemetry telemetry, HardwareMap hardwareMap) {

        super(1, new String[]{"chooChooMotor"}, 537.7, gamepad1, telemetry, hardwareMap);

        motors[0].setBreakMode();
        chooChooPID = new PID_Controller(0.012, 0.0005, 0.7, 0.0);
        chooChooPID.tolerance = ANGLE_TOLERANCE;
    }

    public double getAngleDegrees() {
        return motors[0].getCurrPosDegrees();
    }

    public double getEncoderTicks() {
        return motors[0].getCurrPosTicks();
    }

    public double setTargetAngle(double targetAngle, double maxPower) {

        double currentAngle = getAngleDegrees();

        double pidPower = chooChooPID.PID_Power(
                currentAngle,
                targetAngle
        );

        double clampedPower = Math.max(-maxPower, Math.min(maxPower, pidPower));

        setPower(clampedPower);
        return clampedPower;
    }

    public boolean isAtTarget(double targetAngle) {
        return Math.abs(
                targetAngle - getAngleDegrees()
        ) < ANGLE_TOLERANCE;
    }

    public double getError(double targetAngle) {
        return targetAngle - getAngleDegrees();
    }
}
