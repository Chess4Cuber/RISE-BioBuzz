package org.firstinspires.ftc.teamcode.mechanisms.NaviFlowerSystem;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class NaviFlowerArmSystem {

    Gamepad gamepad1;
    NaviFlowerArm linkage;
    public FlowerStages linkageState;
    boolean lastToggleA = false;
    public NaviFlowerArmSystem(Gamepad gamepad1, Telemetry telemetry, HardwareMap hardwareMap){
        linkage = new NaviFlowerArm(gamepad1, telemetry, hardwareMap);

        this.gamepad1 = gamepad1;
        linkageState = FlowerStages.RETRACTED;

    }

    public void setPositions(){
        switch (linkageState) {
            case INITIALIZED:
                    linkage.setPosition(0, 0);
            case RETRACTED:
                linkage.setPosition(.47, 0);
                break;
            case EXTENDED:
                linkage.setPosition(.7, 0);
                break;
        }
    }

    public void controllerInput(){
        switch (linkageState) {
            case RETRACTED:
                if ((gamepad1.a != lastToggleA) && gamepad1.a) {
                    linkageState = FlowerStages.EXTENDED;
                }

                break;
            case EXTENDED:
                if ((gamepad1.a != lastToggleA) && gamepad1.a) {
                    linkageState = FlowerStages.RETRACTED;
                }
                break;
        }
        lastToggleA = gamepad1.a;
    }

}
