package org.firstinspires.ftc.teamcode.mechanisms.NaviChooChooSystem;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class NaviChooChooSystem {

    Gamepad gamepad1;
    Telemetry telemetry;
    NaviChooChoo chooChoo;

    public NaviChooChooStates chooChooState;

    boolean lastToggleB = false;

    // Mechanism angles
    private static final double ROTATION_DIRECTION = 1.0;

    private static final double INITIAL_ANGLE =
            270.0 * ROTATION_DIRECTION;

    private static final double LOOP_ANGLE =
            360.0 * ROTATION_DIRECTION;

    private static final double MAX_POWER = .6;

    private double targetAngle = INITIAL_ANGLE;

    public NaviChooChooSystem(Gamepad gamepad1, Telemetry telemetry, HardwareMap hardwareMap) {

        chooChoo = new NaviChooChoo(gamepad1, telemetry, hardwareMap);

        this.gamepad1 = gamepad1;
        this.telemetry = telemetry;

        chooChooState = NaviChooChooStates.INITIALIZING;
    }

    public void setPositions() {

        switch (chooChooState) {

            case INITIALIZING:

                chooChoo.setTargetAngle(targetAngle, MAX_POWER);

                break;

            case RESTING:

                chooChoo.setTargetAngle(targetAngle, MAX_POWER);

                break;

            case ROTATING:

                chooChoo.setTargetAngle(targetAngle, MAX_POWER);

                break;
        }
    }

    public void controllerInput() {

        switch (chooChooState) {

            case INITIALIZING:
                if (chooChoo.isAtTarget(targetAngle)) {
                    chooChooState = NaviChooChooStates.RESTING;
                }

                break;

            case RESTING:
                // RESTING is only entered after reaching the 3/4 position, so B is always accepted here.
                // targetAngle (not the measured angle) is advanced, so any drift never accumulates.
                if ((gamepad1.b != lastToggleB) && gamepad1.b) {

                    targetAngle += LOOP_ANGLE;
                    chooChooState = NaviChooChooStates.ROTATING;
                }

                break;

            case ROTATING:
                if (chooChoo.isAtTarget(targetAngle)) {
                    chooChooState = NaviChooChooStates.RESTING;
                }

                break;
        }

        lastToggleB = gamepad1.b;
    }

    public void initializePosition() {

        chooChoo.setTargetAngle(targetAngle, MAX_POWER);

        if (chooChoo.isAtTarget(targetAngle)) {
            chooChooState = NaviChooChooStates.RESTING;
        }

        lastToggleB = gamepad1.b;
    }


    public void displayTelemetry() {
        telemetry.addData("Choo Choo State", chooChooState);
        telemetry.addData("Choo Choo Position", "%.1f deg", chooChoo.getAngleDegrees());
        telemetry.addData("Choo Choo Target", "%.1f deg", targetAngle);
        telemetry.addData("Choo Choo Error", "%.1f deg", chooChoo.getError(targetAngle));
        telemetry.addData("Choo Choo Encoder", "%.0f ticks", chooChoo.getEncoderTicks());
        telemetry.addData("Choo Choo At Target", chooChoo.isAtTarget(targetAngle));
        telemetry.addData("Choo Choo P", chooChoo.chooChooPID.P);
        telemetry.addData("Choo Choo I", chooChoo.chooChooPID.I);
        telemetry.addData("Choo Choo D", chooChoo.chooChooPID.D);
    }
}