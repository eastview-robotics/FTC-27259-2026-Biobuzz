package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;




@TeleOp(name = "TeleOpV3")
public class TeleOp_LooseBoltsV3 extends OpMode {

    // button toggle
    boolean previousButtonState = false;
    ToggleButton dropIntake = new ToggleButton(false);



    // motors
    Motor intakeMotor = new Motor();

    // drive motors
    Drive drive = new Drive();
    Motor backLeftMotor = new Motor();
    Motor frontLeftMotor = new Motor();
    Motor backRightMotor = new Motor();
    Motor frontRightMotor = new Motor();

    // rotation lock
    double rotationLock = 0;


    @Override
    public void init() {
        // intake motor
        intakeMotor.init(hardwareMap, "intake_motor");

        // drive
        backLeftMotor.init(hardwareMap, "back_left");
        frontLeftMotor.init(hardwareMap, "front_left");
        backRightMotor.init(hardwareMap, "back_right");
        frontRightMotor.init(hardwareMap, "front_right");


        backRightMotor.setDirectionReverse();
        frontRightMotor.setDirectionReverse();

    }

    @Override
    // runs approximately 50 - 150 times per sec
    public void loop() {
        if (dropIntake.toggle(gamepad2.x)) {
            rotationLock = 1;
        } else {
            rotationLock = 0;
        }

        intakeMotor.setMotorSpeed(gamepad2.right_stick_y);

        double[] motorPowerBlFlBrFr = drive.getMotorPower(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        // Apply the speeds to motor controllers
        backLeftMotor.setMotorSpeed(motorPowerBlFlBrFr[0]);
        frontLeftMotor.setMotorSpeed(motorPowerBlFlBrFr[1]);
        backRightMotor.setMotorSpeed(motorPowerBlFlBrFr[2]);
        frontRightMotor.setMotorSpeed(motorPowerBlFlBrFr[3]);


        // telemetry
        telemetry.addData("Intake Drop Status", dropIntake.currentStatus());

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

