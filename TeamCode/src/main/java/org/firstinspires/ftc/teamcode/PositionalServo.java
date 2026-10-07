package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class PositionalServo {
    private Servo servo;

    public void init(HardwareMap hwMap, String servoName) {
        //true is positional
        this.servo = hwMap.get(Servo.class, servoName);

    }

    public void setServoPosition(double angle) {
        this.servo.setPosition(angle);
    }
    public double getServoPosition() {
        return this.servo.getPosition();
    }
}
