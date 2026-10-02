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

    public void setDirection(String direction) {
        if (direction.equals("F")) {
            motor.setDirection(DcMotorSimple.Direction.FORWARD);
        } else if (direction.equals("R")) {
            motor.setDirection(DcMotorSimple.Direction.REVERSE);
        }

    }

    public void setMotorSpeed(double motorSpeed) {
        motor.setPower(motorSpeed);
    }
}
