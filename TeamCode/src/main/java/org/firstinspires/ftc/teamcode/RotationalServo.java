package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class RotationalServo {

    private CRServo servo;
    public void init(HardwareMap hwMap, String servoName) {
        //true is positional
        servo = hwMap.get(CRServo.class, "intake_drop_servo");

    }

    public void setServoRotationSpeed(double speed) {
        servo.setPower(speed);
    }

    public void setServoRotationDirection(String direction) {
        if (direction.equals("F")) {
            servo.setDirection(DcMotorSimple.Direction.FORWARD);
        } else if (direction.equals("R")) {
            servo.setDirection(DcMotorSimple.Direction.REVERSE);
        }

    }
}
