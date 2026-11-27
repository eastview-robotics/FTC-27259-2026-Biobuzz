package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class MotorEx {
    private DcMotorEx motor;
    private final double ticksPerRev;


    public MotorEx(double ticksPerRev) {
        this.ticksPerRev = ticksPerRev;
    }



    public void init(HardwareMap hwMap, String motorName) {
        motor = hwMap.get(DcMotorEx.class, motorName);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double p = 25.0; // Increase this to ramp up faster
        double i = 0.0;  // Keep at 0 initially, or small value (e.g., 0.1 * P) if it falls just short
        double d = 1.5;  // Add a tiny amount only if the speed oscillates/overshoots
        double f = 11.7; // Baseline power estimate (32767 / max motor ticks per second)

        motor.setVelocityPIDFCoefficients(p, i, d, f);
    }

    public void setDirection(String direction) {
        if (direction.equals("F")) {
            motor.setDirection(DcMotorSimple.Direction.FORWARD);
        } else if (direction.equals("R")) {
            motor.setDirection(DcMotorSimple.Direction.REVERSE);
        }

    }
    public double getMotorPosition() {
        return motor.getCurrentPosition();
    }
    public void setMotorRPM(double targetRPM) {
        // Math: (RPM / 60) * 28
        double ticksPerSecond = (targetRPM / 60.0) * ticksPerRev;

        // Pass the calculated ticks per second to the motor
        motor.setVelocity(ticksPerSecond);
    }

    public void setMotorPower(double motorSpeed) {
        motor.setPower(motorSpeed);
    }
}
