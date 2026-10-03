package org.firstinspires.ftc.teamcode.mechanisms.RobotNameChooChoo;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class RobotChooChooMotorSystem {
    Gamepad gamepad1;
    Telemetry telemetry;
    RobotChooChooMotor chooChooMotor;

    boolean lastToggleB = false;
    boolean lastToggleRightBumper = false;
    boolean lastToggleLeftBumper = false;
    public ChooChooStates chooChooState;

    public double targetDegrees = 0;
    public double nudgeDegrees = 10;

    public RobotChooChooMotorSystem(Gamepad gamepad1, Telemetry telemetry, HardwareMap hardwareMap){
        chooChooMotor = new RobotChooChooMotor(gamepad1, telemetry, hardwareMap);

        chooChooState = ChooChooStates.RESTING;
        this.gamepad1 = gamepad1;
        this.telemetry = telemetry;
    }

    public void setPositions(){
        switch (chooChooState){
            case RESTING:
                chooChooMotor.setPosition(targetDegrees);
                break;

            case AUTO:
                chooChooMotor.setPosition(targetDegrees);
                break;

            case MANUAL:
                chooChooMotor.setPosition(targetDegrees);
                break;
        }
    }

    public void controllerInput(){
        switch (chooChooState) {
            case RESTING:
                if ((gamepad1.b != lastToggleB) && gamepad1.b) {
                    startFullRotation();
                }
                //right bumper = nudge clockwise, left bumper = nudge counter clockwise
                if ((gamepad1.right_bumper != lastToggleRightBumper) && gamepad1.right_bumper) {
                    targetDegrees += nudgeDegrees;
                    chooChooState = ChooChooStates.MANUAL;
                }
                if ((gamepad1.left_bumper != lastToggleLeftBumper) && gamepad1.left_bumper) {
                    targetDegrees -= nudgeDegrees;
                    chooChooState = ChooChooStates.MANUAL;
                }
                break;

            case AUTO:
                if (Math.abs(targetDegrees - chooChooMotor.getPositionDegrees()) < chooChooMotor.toleranceDegrees) {
                    chooChooState = ChooChooStates.RESTING;
                }
                break;

            case MANUAL:
                //keep nudging while in manual
                if ((gamepad1.right_bumper != lastToggleRightBumper) && gamepad1.right_bumper) {
                    targetDegrees += nudgeDegrees;
                }
                if ((gamepad1.left_bumper != lastToggleLeftBumper) && gamepad1.left_bumper) {
                    targetDegrees -= nudgeDegrees;
                }
                //B starts a full rotation from wherever it is
                if ((gamepad1.b != lastToggleB) && gamepad1.b) {
                    startFullRotation();
                }
                else if (Math.abs(targetDegrees - chooChooMotor.getPositionDegrees()) < chooChooMotor.toleranceDegrees) {
                    chooChooState = ChooChooStates.RESTING;
                }
                break;
        }
        lastToggleB = gamepad1.b;
        lastToggleRightBumper = gamepad1.right_bumper;
        lastToggleLeftBumper = gamepad1.left_bumper;

    }

    public void startFullRotation(){
        targetDegrees = chooChooMotor.getPositionDegrees() + 360;
        chooChooState = ChooChooStates.AUTO;
    }

    public void setChooChooState(ChooChooStates state){
        chooChooState = state;
    }

    public void setTelemetry(){
        telemetry.addData("Rotator State", chooChooState);
        telemetry.addData("Rotator Degrees", chooChooMotor.getPositionDegrees());
        telemetry.addData("Rotator Target", targetDegrees);
        telemetry.addData("Rotator Encoder", chooChooMotor.motors[0].getCurrPosTicks());
    }
}