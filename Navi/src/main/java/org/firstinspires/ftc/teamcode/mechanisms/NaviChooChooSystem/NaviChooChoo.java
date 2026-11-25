package org.firstinspires.ftc.teamcode.mechanisms.NaviChooChooSystem;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.baseCode.control.PID_Controller;
import org.firstinspires.ftc.baseCode.hardware.PassiveIntake;
import org.firstinspires.ftc.robotcore.external.Telemetry;


public class NaviChooChoo extends PassiveIntake {

    public PID_Controller chooChooPID;

    // goBILDA 5203 series 84 RPM motor (71.2:1) = 1993.6 ticks per output revolution
    // goBILDA 5203 series 84 RPM motor (71.2:1) = 1993.6 ticks per motor output revolution
    public static final double MOTOR_CPR = 1993.6;

    // Mechanism turns per motor output turn. Measured: encoder said 270 deg but the mechanism turned ~360 deg
    // so ~4/3. Fine tune this number: GEAR_RATIO = (real degrees turned) / (degrees telemetry says)
    public static final double GEAR_RATIO = 1.4;

    // Ticks per revolution of the MECHANISM, so every angle in the code is a real mechanism angle
    public static final double CHOO_CHOO_CPR = MOTOR_CPR / GEAR_RATIO;

    private static final double KP = 0.05;
    private static final double KD = 0.0002;
    private static final double KI = 0.0;

    // Wide enough that the motor can actually settle inside it (kp * tolerance must beat static friction)
    private static final double ANGLE_TOLERANCE = 5.0;

    // Spring load makes P-only control stop short of the target, so near the target we slowly add power
    // until it gets there. Only builds inside HOLD_ZONE so it never winds up during the big moves.
    private static final double KI_HOLD = 0.01;     // power per (degree * second) of error
    private static final double HOLD_ZONE = 25.0;   // degrees
    private static final double MAX_HOLD_POWER = 0.4;

    private double holdIntegral = 0;
    private ElapsedTime holdTimer = new ElapsedTime();

    public NaviChooChoo(Gamepad gamepad1, Telemetry telemetry, HardwareMap hardwareMap) {

        super(1, new String[]{"chooChooMotor"}, CHOO_CHOO_CPR, gamepad1, telemetry, hardwareMap);

        motors[0].setBreakMode();

        chooChooPID = new PID_Controller(KP, KD, 0.7, KI);
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

        double error = targetAngle - currentAngle;
        double deltaTime = Math.min(holdTimer.seconds(), 0.1);
        holdTimer.reset();

        if (Math.abs(error) < HOLD_ZONE) {
            holdIntegral += error * deltaTime;
            holdIntegral = Math.max(-MAX_HOLD_POWER / KI_HOLD, Math.min(MAX_HOLD_POWER / KI_HOLD, holdIntegral));
        } else {
            holdIntegral = 0;
        }

        pidPower += KI_HOLD * holdIntegral;

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