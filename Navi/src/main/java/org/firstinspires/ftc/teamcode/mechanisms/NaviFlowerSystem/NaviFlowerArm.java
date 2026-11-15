package org.firstinspires.ftc.teamcode.mechanisms.NaviFlowerSystem;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.baseCode.hardware.extension.ServoLinkage;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class NaviFlowerArm extends ServoLinkage {

    public NaviFlowerArm(Gamepad gamepad1, Telemetry telemetry, HardwareMap hardwareMap){
        super(hardwareMap);

        addDegreeOfFreedom(new String[]{"leftServoArm", "rightServoArm"});


    }

}
