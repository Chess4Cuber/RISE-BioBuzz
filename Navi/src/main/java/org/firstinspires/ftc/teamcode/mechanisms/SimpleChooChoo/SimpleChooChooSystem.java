package org.firstinspires.ftc.teamcode.mechanisms.SimpleChooChoo;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SimpleChooChooSystem {
    Gamepad gamepad1;
    Telemetry telemetry;
    boolean lastToggleUp = false;
    boolean lastToggleA = false;
    SimpleChooChoo intake;
    public SimpleChooChooStates intakeState;

    public SimpleChooChooSystem(Gamepad gamepad1, Telemetry telemetry, HardwareMap hardwareMap) {

        intake = new SimpleChooChoo(gamepad1, telemetry, hardwareMap);

        this.gamepad1 = gamepad1;
        this.telemetry = telemetry;
        intakeState = SimpleChooChooStates.RESTING;

    }

    public void setPositions(){
        switch(intakeState){
            case RESTING:
                intake.setPower(0);
                break;

            case INTAKING:
                intake.setPower(.7);
                break;

            case OUTTAKING:
                intake.setPower(-.7);
                break;
        }
    }

    public void controllerInput(){
        switch(intakeState) {
            case RESTING:
                if ((gamepad1.a != lastToggleA) && gamepad1.a) {
                    intakeState = SimpleChooChooStates.INTAKING;
                }
                if ((gamepad1.dpad_up != lastToggleUp) && gamepad1.dpad_up) {
                    intakeState = SimpleChooChooStates.OUTTAKING;
                }
                break;

            case INTAKING:
                if((gamepad1.dpad_up != lastToggleUp) && gamepad1.dpad_up){
                    intakeState = SimpleChooChooStates.OUTTAKING;
                }
                if((gamepad1.a != lastToggleA) && gamepad1.a){
                    intakeState = SimpleChooChooStates.RESTING;
                }
                break;

            case OUTTAKING:
                if((gamepad1.a != lastToggleA) && gamepad1.a) {
                    intakeState = SimpleChooChooStates.INTAKING;
                }
                if((gamepad1.dpad_up != lastToggleUp) && gamepad1.dpad_up){
                    intakeState = SimpleChooChooStates.RESTING;
                }
                break;
        }
        lastToggleUp = gamepad1.dpad_up;
        lastToggleA = gamepad1.a;
    }

    public void setMotorIntakeState(SimpleChooChooStates state){
        intakeState = state;
    }



}