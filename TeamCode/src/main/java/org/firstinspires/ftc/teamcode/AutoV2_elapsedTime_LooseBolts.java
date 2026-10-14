package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "AutoV2")
public class AutoV2_elapsedTime_LooseBolts extends OpMode {

    enum MotorState { STOPPED, FORWARD, BACK, LEFT, RIGHT, CLOCKWISE, COUNTERCLOCKWISE, DONE }

    double duration = 1;

    double speed = 0.5;

    double forward;
    double right;
    double rotateCW;
    MotorState currentState = MotorState.STOPPED;
    ElapsedTime ActionDuration = new ElapsedTime();

    // motors
    Motor intakeMotor = new Motor();
    // drive motors
    Drive drive = new Drive();
    Motor backLeftMotor = new Motor();
    Motor frontLeftMotor = new Motor();
    Motor backRightMotor = new Motor();
    Motor frontRightMotor = new Motor();

    @Override
    public void init() {
        // drive hardware mapping
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
    public void loop() {

        switch (currentState) {
            case STOPPED:
                forward = 0;
                right = 0;
                rotateCW = 0;

                // Reset here prepares a completely fresh 0.0 for the FORWARD state
                ActionDuration.reset();
                currentState = MotorState.FORWARD;
                break;

            case FORWARD:
                forward = -speed;
                right = 0;
                rotateCW = 0;

                if (ActionDuration.seconds() >= duration) {
                    ActionDuration.reset(); // Reset for CLOCKWISE
                    currentState = MotorState.CLOCKWISE;
                }
                break;

            case CLOCKWISE:
                forward = 0;
                right = 0;
                rotateCW = speed;

                if (ActionDuration.seconds() >= duration) {
                    ActionDuration.reset(); // Reset for COUNTERCLOCKWISE
                    currentState = MotorState.COUNTERCLOCKWISE;
                }
                break;

            case COUNTERCLOCKWISE:
                forward = 0;
                right = 0;
                rotateCW = -speed;

                if (ActionDuration.seconds() >= duration) {
                    ActionDuration.reset(); // Reset for LEFT
                    currentState = MotorState.LEFT;
                }
                break;

            case LEFT:
                forward = 0;
                right = -speed;
                rotateCW = 0;

                if (ActionDuration.seconds() >= duration) {
                    ActionDuration.reset(); // Reset for RIGHT
                    currentState = MotorState.RIGHT;
                }
                break;

            case RIGHT:
                forward = 0;
                right = speed;
                rotateCW = 0;

                if (ActionDuration.seconds() >= duration) {
                    ActionDuration.reset(); // Reset for BACK
                    currentState = MotorState.BACK;
                }
                break;

            case BACK:
                forward = speed;
                right = 0;
                rotateCW = 0;

                if (ActionDuration.seconds() >= duration) {
                    // DONE doesn't use a timer, no reset required
                    currentState = MotorState.DONE;
                }
                break;

            case DONE:
                forward = 0;
                right = 0;
                rotateCW = 0;
                break;
        }

        // Calculate mecanum mathematical motor velocities
        double[] motorPowerBlFlBrFr = drive.getMotorPower(
                forward,
                right,
                rotateCW);

        // Apply the speeds to motor controllers
        backLeftMotor.setMotorSpeed(motorPowerBlFlBrFr[0]);
        frontLeftMotor.setMotorSpeed(motorPowerBlFlBrFr[1]);
        backRightMotor.setMotorSpeed(motorPowerBlFlBrFr[2]);
        frontRightMotor.setMotorSpeed(motorPowerBlFlBrFr[3]);

        // Telemetry outputs
        telemetry.addData("Current State", currentState);
        telemetry.addData("State Time", ActionDuration.seconds());
        telemetry.update();
    }
}
