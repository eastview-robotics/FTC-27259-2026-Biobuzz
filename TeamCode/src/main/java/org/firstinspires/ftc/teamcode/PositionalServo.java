package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class PositionalServo {
    private Servo servo;
    private CRServo servoRotational;

    public void init(HardwareMap hwMap, String servoName) {
        //true is positional
        servo = hwMap.get(Servo.class, "intake_drop_servo");

    }

    public void setServoPosition(double angle) {
        servo.setPosition(angle);
    }
}
