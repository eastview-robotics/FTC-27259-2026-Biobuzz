package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Motor {
    private DcMotor motor;

    public void init(HardwareMap hwMap, String motorName) {
        motor = hwMap.get(DcMotor.class, motorName);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setDirectionReverse() {
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void setDirectionForward() {
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setMotorSpeed(double motorSpeed) {
        motor.setPower(motorSpeed);
    }
}
