package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCompatibilityManager;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;


@Autonomous(name = "AutoV3")
public class AutoV3_elapsedTime_LooseBolts extends OpMode {

    enum MotorState {STOPPED, DROP, FORWARD, BACK, LEFT,LEFT2, RIGHT,FORWARD2, CLOCKWISE, COUNTERCLOCKWISE, INITSHOOT, SHOOT, DONE }

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


    // camera
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    /**
     * The variable to store our instance of the AprilTag processor.
     */
    private AprilTagProcessor aprilTag;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;

    static final int VENDOR_ID_SUNPLUS_INNOVATION_TECHNOLOGY = 0x1BCF;
    static final int PRODUCT_ID_ARDUCAM_OV5648 = 0x284C;


    @Override
    public void init(){


        // intake
        intakeMotor.init(hardwareMap, "intake_motor");

        dropIntakePositionalServo.init(hardwareMap, "intake_drop_servo");
        dropIntakePositionalServo.setServoPosition(0);

        // shooter

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
                gatePositionalServo.setServoPosition(0);
                currentState = MotorState.DROP;
                gatePositionalServo.setServoPosition(0);
                break;

            case DROP:
                forward = 0;
                right = 0;
                rotateCW = 0;
                dropIntakePositionalServo.setServoPosition(0.3);
                intakeMotor.setMotorPower(1);
                if (ActionDuration.seconds() >= duration) {
                    ActionDuration.reset();
                    currentState = MotorState.INITSHOOT;
                }
                break;

            case INITSHOOT:
                forward = 0;
                right = 0;
                rotateCW = 0;
                shooterMotor.setMotorRPM(3350);
                gatePositionalServo.setServoPosition(0.75);
                if (ActionDuration.seconds() >= duration*3) {
                    ActionDuration.reset(); // Reset for CLOCKWISE
                    currentState = MotorState.SHOOT;
                }
                break;

            case SHOOT:
                forward = 0;
                right = 0;
                rotateCW = 0;
                if (ActionDuration.seconds() >= duration * 10) {
                    ActionDuration.reset();
                    currentState = MotorState.FORWARD;
                }
                break;

            case FORWARD:
                forward = speed;
                right = 0;
                rotateCW = 0;
                shooterMotor.setMotorRPM(0);
                gatePositionalServo.setServoPosition(0);
                if (ActionDuration.seconds() >= duration) {
                    ActionDuration.reset();
                    currentState = MotorState.LEFT;
                }
                break;


            case LEFT:
                forward = 0;
                right = speed;
                rotateCW = 0;

                if (ActionDuration.seconds() >= duration * 1.5) {
                    ActionDuration.reset();
                    currentState = MotorState.FORWARD2;
                }
                break;

            case FORWARD2:
                forward = speed;
                right = 0;
                rotateCW = 0;

                if (ActionDuration.seconds() >= duration*3) {
                    ActionDuration.reset();
                    currentState = MotorState.LEFT2;
                }
                break;

            case LEFT2:
                forward = 0;
                right = speed;
                rotateCW = 0;

                if (ActionDuration.seconds() >= duration * 1) {
                    ActionDuration.reset();
                    currentState = MotorState.DONE;
                }
                break;

            case DONE:
                shooterMotor.setMotorRPM(0);
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
