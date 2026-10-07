package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;




@TeleOp(name = "TeleOpV3")
public class TeleOp_LooseBoltsV3 extends OpMode {

    boolean dropIntake = false;
    double sensitivity = 1;
    boolean shootInProgress = false;
    double pushShooterPosServoPosition = 0;
    int delay = 0;

    // button toggle
    ToggleButton dropIntakeToggle = new ToggleButton(false);

    // intake
    Motor intakeMotor = new Motor();
    PositionalServo dropIntakePositionalServo = new PositionalServo();
    PositionalServo pushShooterPositionalServo = new PositionalServo();

    // drive motors
    Drive drive = new Drive();
    Motor backLeftMotor = new Motor();
    Motor frontLeftMotor = new Motor();
    Motor backRightMotor = new Motor();
    Motor frontRightMotor = new Motor();




    @Override
    public void init() {
        // intake
        intakeMotor.init(hardwareMap, "intake_motor");



        dropIntakePositionalServo.init(hardwareMap, "intake_drop_servo");
        dropIntakePositionalServo.setServoPosition(0);


        pushShooterPositionalServo.init(hardwareMap, "push_into_shooter_servo");
        pushShooterPositionalServo.setServoPosition(0);

        // drive
        backLeftMotor.init(hardwareMap, "back_left");
        frontLeftMotor.init(hardwareMap, "front_left");
        backRightMotor.init(hardwareMap, "back_right");
        frontRightMotor.init(hardwareMap, "front_right");

        backRightMotor.setDirection("R");
        frontRightMotor.setDirection("R");
        backLeftMotor.setDirection("F");
        frontLeftMotor.setDirection("F");





    }

    @Override
    // runs approximately 50 - 150 times per sec
    public void loop() {

        //====================================

        if (gamepad1.left_trigger > 0 && sensitivity >= 0.1) {
            sensitivity -= gamepad1.left_trigger * 0.001;
        } else if (gamepad1.right_trigger > 0 && sensitivity < 1) {
            sensitivity += gamepad1.right_trigger * 0.001;
        }

        //====================================

        dropIntake = dropIntakeToggle.toggle(gamepad2.x);

        if (dropIntake) {
            dropIntakePositionalServo.setServoPosition(0.3);
        } else {
            dropIntakePositionalServo.setServoPosition(0);
        }

        //====================================

        pushShooterPositionalServo.setServoPosition(gamepad2.right_trigger * 0.2);

        //====================================

        intakeMotor.setMotorSpeed(gamepad2.right_stick_x);

        double[] motorPowerBlFlBrFr = drive.getMotorPower(
                gamepad1.left_stick_y * sensitivity,
                gamepad1.left_stick_x * sensitivity,
                gamepad1.right_stick_x * sensitivity);

        // Apply the speeds to motor controllers
        backLeftMotor.setMotorSpeed(motorPowerBlFlBrFr[0]);
        frontLeftMotor.setMotorSpeed(motorPowerBlFlBrFr[1]);
        backRightMotor.setMotorSpeed(motorPowerBlFlBrFr[2]);
        frontRightMotor.setMotorSpeed(motorPowerBlFlBrFr[3]);


        // telemetry
        telemetry.addData("Intake Drop Status", dropIntakeToggle.currentStatus());
        telemetry.addData("Sensitivity", sensitivity);
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

