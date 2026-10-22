package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name = "AutoV1")
public class AutoV1_elapsedTime_LooseBolts extends OpMode {

    enum MotorState { START, SPINNING, DONE }
    MotorState currentState = MotorState.START;
    ElapsedTime motorTimer = new ElapsedTime();

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

        // --- In your loop() ---
        switch (currentState) {
            case START:
                motorTimer.reset();      // Start the clock
                intakeMotor.setMotorPower(0.5);     // Turn motor on
                currentState = MotorState.SPINNING;
                break;

            case SPINNING:
                // Check if 1.2 seconds have passed (approx. time for 1 rotation)
                if (motorTimer.seconds() >= 1.2) {
                    intakeMotor.setMotorPower(0);   // Stop the motor
                    currentState = MotorState.DONE;
                }
                break;

            case DONE:
                // Motor is stopped, but this case keeps the loop alive safely
                break;
        }

// Any code placed out here runs completely uninterrupted every single loop cycle!
        telemetry.addData("Time Elapsed", motorTimer.seconds());
        telemetry.update();
    }
}
