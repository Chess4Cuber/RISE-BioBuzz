package org.firstinspires.ftc.teamcode.mechanisms.NaviFlowerSystem;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class NaviFlowerArmSystem {

    Gamepad gamepad1;
    NaviFlowerArm linkage;
    public FlowerStages linkageState;
    boolean lastToggleX = false;
    public NaviFlowerArmSystem(Gamepad gamepad1, Telemetry telemetry, HardwareMap hardwareMap){
        linkage = new NaviFlowerArm(gamepad1, telemetry, hardwareMap);

        this.gamepad1 = gamepad1;
        linkageState = FlowerStages.INITIALIZED;

    }

    public void setPositions(){
        switch (linkageState) {
            case INITIALIZED:
                linkage.setPosition(.5, 0);
                break;
            case RETRACTED:
                linkage.setPosition(.25, 0);
                break;
            case EXTENDED:
                linkage.setPosition(0, 0);
                break;
        }
    }

    public void controllerInput(){
        switch (linkageState) {
            case INITIALIZED:
                if ((gamepad1.x != lastToggleX) && gamepad1.x) {
                    linkageState = FlowerStages.RETRACTED;
                }
                break;

            case RETRACTED:
                if ((gamepad1.x != lastToggleX) && gamepad1.x) {
                    linkageState = FlowerStages.EXTENDED;
                }

                break;
            case EXTENDED:
                if ((gamepad1.x != lastToggleX) && gamepad1.x) {
                    linkageState = FlowerStages.RETRACTED;
                }
                break;
        }
        lastToggleX = gamepad1.x;
    }

}
