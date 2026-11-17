package org.firstinspires.ftc.teamcode.mechanisms.NaviFlowerSystem;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class NaviFlowerArmSystem {

    Gamepad gamepad1;
    NaviFlowerArm arm;
    public NaviFlowerStates flowerState;
    boolean lastToggleX = false;
    public NaviFlowerArmSystem(Gamepad gamepad1, Telemetry telemetry, HardwareMap hardwareMap){
        arm = new NaviFlowerArm(gamepad1, telemetry, hardwareMap);

        this.gamepad1 = gamepad1;
        flowerState = NaviFlowerStates.INITIALIZED;

    }

    public void setPositions(){
        switch (flowerState) {
            case INITIALIZED:
                arm.setPosition(.5, 0);
                break;
            case RETRACTED:
                arm.setPosition(.25, 0);
                break;
            case EXTENDED:
                arm.setPosition(0, 0);
                break;
        }
    }

    public void controllerInput(){
        switch (flowerState) {
            case INITIALIZED:
                if ((gamepad1.x != lastToggleX) && gamepad1.x) {
                    flowerState = NaviFlowerStates.RETRACTED;
                }
                break;

            case RETRACTED:
                if ((gamepad1.x != lastToggleX) && gamepad1.x) {
                    flowerState = NaviFlowerStates.EXTENDED;
                }

                break;
            case EXTENDED:
                if ((gamepad1.x != lastToggleX) && gamepad1.x) {
                    flowerState = NaviFlowerStates.RETRACTED;
                }
                break;
        }
        lastToggleX = gamepad1.x;
    }

    public void setFlowerState(NaviFlowerStates state){
        flowerState = state;
    }

}
