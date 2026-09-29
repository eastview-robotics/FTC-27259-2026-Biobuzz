package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "TeleOpV1")
public class TeleOpV1 extends OpMode {

    // button toggle
    boolean previousButtonState = false;
    ToggleButton buttonActionA = new ToggleButton(false);


    // drive motors
    Motor intakeMotor = new Motor();
    Motor backLeftMotor = new Motor();
    Motor frontLeftMotor = new Motor();
    Motor backRightMotor = new Motor();
    Motor frontRightMotor = new Motor();

    @Override
    public void init() {
        // intake motor
        intakeMotor.init(hardwareMap, "intake_motor");


        // drive
        backLeftMotor.init(hardwareMap, "back_left");
        frontLeftMotor.init(hardwareMap, "front_left");
        backRightMotor.init(hardwareMap, "back_right");
        frontRightMotor.init(hardwareMap, "front_right");

    }

    @Override
    // runs approximately 50 - 150 times per sec
    public void loop() {



        intakeMotor.setMotorSpeed(gamepad2.right_stick_y);

        // drive
        //======================================================
        // Invert yAxis if your joystick forward is negative
        double forward = -gamepad2.left_stick_y;
        double right  = gamepad2.right_stick_x;
        double rotate  = gamepad2.left_stick_x;

        // Calculate individual wheel speeds
        double frontLeftSpeed  = forward + right + rotate;
        double frontRightSpeed = forward - right - rotate;
        double backLeftSpeed   = forward - right + rotate;
        double backRightSpeed  = forward + right - rotate;

        // Normalize speeds if any value exceeds 1.0 to maintain directional integrity
        double max = Math.max(1.0, Math.max(
                Math.max(Math.abs(frontLeftSpeed), Math.abs(frontRightSpeed)),
                Math.max(Math.abs(backLeftSpeed), Math.abs(backRightSpeed))
        ));

        frontLeftSpeed  /= max;
        frontRightSpeed /= max;
        backLeftSpeed   /= max;
        backRightSpeed  /= max;

        // Apply the speeds to motor controllers (e.g., Victor, Talon, Spark)
        frontLeftMotor.setMotorSpeed(frontLeftSpeed);
        frontRightMotor.setMotorSpeed(frontRightSpeed);
        backLeftMotor.setMotorSpeed(backLeftSpeed);
        backRightMotor.setMotorSpeed(backRightSpeed);
        //======================================================


        // telemetry

        //telemetry.addData("Left Stick x", gamepad2.left_stick_x);
        //telemetry.addData("Left Stick y", gamepad2.left_stick_y);
        //telemetry.addData("Right Stick x", gamepad2.right_stick_x);
        //telemetry.addData("Right Stick x", gamepad2.right_stick_y);
        //telemetry.addData("a Button", gamepad2.a);
        //telemetry.addData("b Button", gamepad2.b);
        //telemetry.addData("x Button", gamepad2.x);
        //telemetry.addData("y Button", gamepad2.y);
    }
}
