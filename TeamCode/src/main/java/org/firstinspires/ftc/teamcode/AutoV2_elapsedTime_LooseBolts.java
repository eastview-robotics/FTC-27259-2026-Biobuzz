package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "AutoV2")
public class AutoV2_elapsedTime_LooseBolts extends OpMode {

    enum MotorState {STOPPED, DROP, FORWARD, BACK, LEFT, RIGHT, CLOCKWISE, COUNTERCLOCKWISE, INITSHOOT, SHOOT, DONE }

    double duration = 1;

    double speed = 0.4;

    double forward;
    double right;
    double rotateCW;
    MotorState currentState = MotorState.STOPPED;
    ElapsedTime ActionDuration = new ElapsedTime();

    //Intake
    Motor intakeMotor = new Motor();
    PositionalServo dropIntakePositionalServo = new PositionalServo();


    //Shooter
    MotorEx shooterMotor = new MotorEx(24);
    RotationalServo spindexerServo = new RotationalServo();
    PositionalServo gatePositionalServo = new PositionalServo();

    // drive motors
    Drive drive = new Drive();
    Motor backLeftMotor = new Motor();
    Motor frontLeftMotor = new Motor();
    Motor backRightMotor = new Motor();
    Motor frontRightMotor = new Motor();

    @Override
    public void init(){


        // intake
        intakeMotor.init(hardwareMap, "intake_motor");

        dropIntakePositionalServo.init(hardwareMap, "intake_drop_servo");
        dropIntakePositionalServo.setServoPosition(0);

        // shooter
        spindexerServo.init(hardwareMap, "spindexer_servo");

        shooterMotor.init(hardwareMap, "shooter_motor");
        shooterMotor.setDirection("R");

        gatePositionalServo.init(hardwareMap, "gate_servo");
        gatePositionalServo.setServoPosition(0);


        // drive



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
                currentState = MotorState.DROP;
                break;

            case DROP:
                forward = 0;
                right = 0;
                rotateCW = 0;
                dropIntakePositionalServo.setServoPosition(0.3);
                intakeMotor.setMotorPower(1);
                if (ActionDuration.seconds() >= duration) {
                    ActionDuration.reset(); // Reset for CLOCKWISE
                    currentState = MotorState.FORWARD;
                }
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
                    currentState = MotorState.BACK;
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

                if (ActionDuration.seconds() >= duration-0.2) {
                    // DONE doesn't use a timer, no reset required
                    currentState = MotorState.INITSHOOT;
                }
                break;

            case INITSHOOT:
                forward = 0;
                right = 0;
                rotateCW = 0;
                shooterMotor.setMotorRPM(3800);
                gatePositionalServo.setServoPosition(0.75);
                if (ActionDuration.seconds() >= duration*5) {
                    currentState = MotorState.SHOOT;
                }
                break;

            case SHOOT:
                forward = 0;
                right = 0;
                rotateCW = 0;
                spindexerServo.setServoRotationSpeed(-0.2);
                if (ActionDuration.seconds() >= duration * 20) {
                    // DONE doesn't use a timer, no reset required
                    currentState = MotorState.DONE;
                }
                break;

            case DONE:
                shooterMotor.setMotorRPM(0);
                spindexerServo.setServoRotationSpeed(0);
                gatePositionalServo.setServoPosition(0);
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
        backLeftMotor.setMotorPower(motorPowerBlFlBrFr[0]);
        frontLeftMotor.setMotorPower(motorPowerBlFlBrFr[1]);
        backRightMotor.setMotorPower(motorPowerBlFlBrFr[2]);
        frontRightMotor.setMotorPower(motorPowerBlFlBrFr[3]);

        // Telemetry outputs
        telemetry.addData("Current State", currentState);
        telemetry.addData("State Time", ActionDuration.seconds());
        telemetry.update();
    }
}
