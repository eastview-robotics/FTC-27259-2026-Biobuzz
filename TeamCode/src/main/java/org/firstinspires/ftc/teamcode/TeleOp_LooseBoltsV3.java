package org.firstinspires.ftc.teamcode;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCompatibilityManager;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import java.util.List;


@TeleOp(name = "TeleOpV3")
public class TeleOp_LooseBoltsV3 extends OpMode {


    //General Variables
    boolean dropIntake = false;
    boolean gateOpen = false;
    boolean spindexerOn = false;
    boolean spindexerCW = true;
    int spindexerDirection;
    boolean intakeIn = true;
    int intakeDirection;
    boolean shooterMotorOn = false;

    double targetRPM = 0;
    double sensitivity = 1;
    boolean autoAdjustOn = false;
    boolean scoring = true;
    boolean allianceBlue = true;
    String alliance = "BLUE";



    //Toggle Button
    ToggleButton toggleDropIntake = new ToggleButton(dropIntake);
    ToggleButton toggleGate = new ToggleButton(gateOpen);
    ToggleButton toggleSpindexer = new ToggleButton(spindexerOn);
    ToggleButton toggleSpindexerDirection = new ToggleButton(spindexerCW);
    ToggleButton toggleIntakeDirection = new ToggleButton(intakeIn);
    ToggleButton toggleShooterMotor = new ToggleButton(shooterMotorOn);
    ToggleButton toggleAutoAdjust = new ToggleButton(autoAdjustOn);
    ToggleButton toggleAlliance = new ToggleButton(allianceBlue);





    //Intake
    Motor intakeMotor = new Motor();
    PositionalServo dropIntakePositionalServo = new PositionalServo();


    //Shooter
    MotorEx shooterMotor = new MotorEx(24);
    RotationalServo turretServo = new RotationalServo();
    PositionalServo gatePositionalServo = new PositionalServo();


    //Drive Motors
    Drive drive = new Drive();
    Motor backLeftMotor = new Motor();
    Motor frontLeftMotor = new Motor();
    Motor backRightMotor = new Motor();
    Motor frontRightMotor = new Motor();

    //Camera

        //Declare the timer
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    /**
     * The variable to store our instance of the AprilTag processor.
     */
    private AprilTagProcessor aprilTag;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;

    // To find the VID/PID for a camera:
    //
    // Linux: open a terminal, run "lsusb", locate the line for your camera,
    // and find the section that resembles "ID 1d6b:0002"; this is VID:PID
    //
    // OSX: open a terminal, run "system_profiler SPUSBDataType", locate the
    // section for your camera, and find the "Product ID:" and "Vendor ID:"
    // listings in the output
    //
    // Windows: open a PowerShell, run:
    // Get-PnpDevice -PresentOnly | Where-Object { $_.InstanceId -like 'USB*' } | Select-Object FriendlyName, InstanceId
    // and locate the line for your camera. The VID and PID is listed directly in the line.
    static final int VENDOR_ID_SUNPLUS_INNOVATION_TECHNOLOGY = 0x1BCF;
    static final int PRODUCT_ID_ARDUCAM_OV5648 = 0x284C;



    @Override
    public void init() {




        // intake
        intakeMotor.init(hardwareMap, "intake_motor");

        dropIntakePositionalServo.init(hardwareMap, "intake_drop_servo");
        dropIntakePositionalServo.setServoPosition(0);

        // shooter

        shooterMotor.init(hardwareMap, "shooter_motor");

        shooterMotor.setDirection("R");

        gatePositionalServo.init(hardwareMap, "gate_servo");
        gatePositionalServo.setServoPosition(0);
        turretServo.init(hardwareMap, "turret_servo");


        // drive
        backLeftMotor.init(hardwareMap, "back_left");
        frontLeftMotor.init(hardwareMap, "front_left");
        backRightMotor.init(hardwareMap, "back_right");
        frontRightMotor.init(hardwareMap, "front_right");

        backRightMotor.setDirection("F");
        frontRightMotor.setDirection("F");
        backLeftMotor.setDirection("R");
        frontLeftMotor.setDirection("R");


        // Camera


        // Demonstrate how to add a camera compatibility quirk
        // these can sometimes be needed if a camera behaves poorly.
        // Quirks have no effect unless the camera you are using matches the specified VID/PID
        CameraCompatibilityManager.getInstance()
                .addQuirk(
                        VENDOR_ID_SUNPLUS_INNOVATION_TECHNOLOGY,
                        PRODUCT_ID_ARDUCAM_OV5648,
                        CameraCompatibilityManager.Quirk.AVOID_LIB_USB_RESET_DEVICE);

        initAprilTag();

        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch START to start OpMode");
        telemetry.update();
    }


    @Override
    // runs approximately 50 - 150 times per sec
    public void loop() {


        //GAMEPAD KEY BINDS:

        //Gamepad1 (Driver):
        double increaseSensitivityBind = gamepad1.right_trigger; //variable input
        double decreaseSensitivityBind = gamepad1.left_trigger; //variable input
        double forwardBind = gamepad1.left_stick_y; //variable input
        double rightBind = gamepad1.left_stick_x; //variable input
        double rotationCWBind = gamepad1.right_stick_x; //variable input
        boolean lightsBind = gamepad1.y; //boolean input
        boolean toggleGateBind = gamepad1.a; //boolean input
        boolean autoAdjustOnBind = gamepad1.x;
        boolean setAllianceBind = gamepad1.options;

        //Gamepad2 (Operator):
        boolean dropIntakeBind = gamepad2.y; //boolean input
        double intakeMotorPowerBind = gamepad2.left_trigger; //variable input
        boolean intakeMotorDirectionBind = gamepad2.dpad_down; //boolean input
        double turretDirectionBind = gamepad2.right_stick_x; //boolean input
        toggleGateBind = gamepad2.x; //boolean input
        boolean toggleShooterMotorBind = gamepad2.b; //boolean input
        boolean increaseShooterMotorSpeedBind = gamepad2.rightBumperWasPressed(); //boolean input, ***MUST ADD: gamepad2.""WasPressed()***
        boolean decreaseShooterMotorSpeedBind = gamepad2.leftBumperWasPressed(); //boolean input, ***MUST ADD: gamepad2.""WasPressed()***
        boolean setPollenShooterMotorSpeedBind = gamepad2.dpadLeftWasPressed();




        double[] motorPowerBlFlBrFr;

        //====================================\/
        // **LOGI CAMERA**
        telemetryAprilTag();

        // Push telemetry to the Driver Station.
        telemetry.update();
        // Save CPU resources; can resume streaming when needed.
        if (gamepad1.dpad_down) {
            visionPortal.stopStreaming();
        } else if (gamepad1.dpad_up) {
            visionPortal.resumeStreaming();
        }

        //====================================/\

        //====================================\/
        // **SENSITIVITY ADJUSTMENT**
        if (decreaseSensitivityBind > 0 && sensitivity >= 0.1) {
            sensitivity -= (decreaseSensitivityBind * 0.001);
        } else if (increaseSensitivityBind > 0 && sensitivity < 1) {
            sensitivity += (increaseSensitivityBind * 0.001);
        }
        //====================================/\

        //====================================\/
        // **DROP INTAKE**
        dropIntake = toggleDropIntake.toggle(dropIntakeBind);

        if (dropIntake) {
            dropIntakePositionalServo.setServoPosition(0.3);
        } else {
            dropIntakePositionalServo.setServoPosition(0);
        }
        //====================================/\

        //====================================\/
        // **TOGGLE GATE**
        gateOpen = toggleGate.toggle(toggleGateBind);

        if (gateOpen) {
            gatePositionalServo.setServoPosition(0.25);
        } else {
            gatePositionalServo.setServoPosition(0);
        }
        //====================================/\


        //====================================\/
        shooterMotorOn = toggleShooterMotor.toggle(toggleShooterMotorBind);
        // **POLLEN V. NECTAR SHOOTER MOTOR SPEED**
        if (setPollenShooterMotorSpeedBind){
            targetRPM = 3300;
        }

        // **VARIABLE SHOOTER MOTOR SPEED**
        if (increaseShooterMotorSpeedBind){
            targetRPM += 100;
        } else if (decreaseShooterMotorSpeedBind){
            targetRPM -= 100;
        }
        if (targetRPM > 6000) {
            targetRPM = 6000;
        } else if (targetRPM < 0) {
            targetRPM = 0;
        }
        if (shooterMotorOn) {
            shooterMotor.setMotorRPM((targetRPM));
        } else {
            shooterMotor.setMotorRPM(0);
        }

        //====================================/\

        //====================================\/
        // **SET TURRET DIRECTION**

        turretServo.setServoRotationSpeed(turretDirectionBind * 0.25);
        //====================================/\

        //====================================\/
        // **TOGGLE INTAKE DIRECTION**
        intakeIn = toggleIntakeDirection.toggle(intakeMotorDirectionBind);

        if (intakeIn) {
            intakeDirection = 1;
        } else {
            intakeDirection = -1;
        }

        //====================================/\

        //====================================\/
        // **TOGGLE ALLIANCE DIRECTION**
        allianceBlue = toggleAlliance.toggle(setAllianceBind);

        if (allianceBlue) {
            alliance = "BLUE";
        } else {
            alliance = "RED";
        }

        //====================================/\

        //====================================\/
        // **INTAKE MOTOR POWER**
        intakeMotor.setMotorPower(abs(intakeMotorPowerBind) * intakeDirection);

        //====================================/\


        //====================================\/
        // **HAPTICS**
        if(gateOpen) {
            gamepad1.rumble(0.25, 0.75, 100);
        }
        //====================================/\


        //====================================\/
        // **TOGGLE AUTO_ADJUST**
        autoAdjustOn = toggleAutoAdjust.toggle(autoAdjustOnBind);

        if (gamepad1.leftBumperWasPressed()){
            scoring = true;
        }
        if (gamepad1.rightBumperWasPressed()){
            scoring = false;
        }

        double autoForward = 0;
        double autoRight = 0;
        double autoRotationCW = 0;
        if (autoAdjustOn) {
            if (scoring) {
                if (returnCameraData("range", alliance + "_SCORING") > 36) {
                    autoForward = 0.25;
                } else if (returnCameraData("range", alliance + "_SCORING") < 30) {
                    autoForward = -0.25;
                }
                if (returnCameraData("bearing", alliance + "_SCORING") > 5) {
                    autoRotationCW = 0.25;
                } else if (returnCameraData("bearing", alliance + "_SCORING") < -5) {
                    autoRotationCW = -0.25;
                } else {
                    if (returnCameraData("range", alliance + "_AUDIENCE") > 36) {
                        autoForward = 0.25;
                    } else if (returnCameraData("range", alliance + "_AUDIENCE") < 30) {
                        autoForward = -0.25;
                    }
                    if (returnCameraData("bearing", alliance + "_AUDIENCE") > 5) {
                        autoRotationCW = 0.25;
                    } else if (returnCameraData("bearing", alliance + "_AUDIENCE") < -5) {
                        autoRotationCW = -0.25;
                    }
                }
            }

            motorPowerBlFlBrFr = drive.getMotorPower(
                    autoForward,
                    autoRight,
                    autoRotationCW);
        } else {
            motorPowerBlFlBrFr = drive.getMotorPower(
                    forwardBind * sensitivity,
                    rightBind * sensitivity,
                    rotationCWBind * sensitivity);
        }
        //====================================/\



        // Apply the speeds to motor controllers
        backLeftMotor.setMotorPower(motorPowerBlFlBrFr[0]);
        frontLeftMotor.setMotorPower(motorPowerBlFlBrFr[1]);
        backRightMotor.setMotorPower(motorPowerBlFlBrFr[2]);
        frontRightMotor.setMotorPower(motorPowerBlFlBrFr[3]);


        // telemetry
        telemetry.addData("Sensitivity", sensitivity);
        telemetry.addData("targetRPM", targetRPM);
        telemetry.addData("Gate Open", gateOpen);
        telemetry.addData("Intake In", intakeIn);
        //telemetry.addData("Pollen Speed On", setPollenShooterMotorSpeedBind);
        //telemetry.addData("Nectar Speed On", setNectarShooterMotorSpeedBind);
        //telemetry.addData("Left Stick x", gamepad2.left_stick_x);
        //telemetry.addData("Left Stick y", gamepad2.left_stick_y);
        //telemetry.addData("Right Stick x", gamepad2.right_stick_x);
        //telemetry.addData("Right Stick x", gamepad2.right_stick_y);
        //telemetry.addData("a Button", gamepad2.a);
        //telemetry.addData("b Button", gamepad2.b);
        //telemetry.addData("x Button", gamepad2.x);
        //telemetry.addData("y Button", gamepad2.y);




    }

    private void initAprilTag() {

        // Create the AprilTag processor.
        aprilTag = new AprilTagProcessor.Builder()

                // The following default settings are available to un-comment and edit as needed.
                //.setDrawAxes(true) // Changed in V12.0
                //.setDrawTagOutline(true)
                //.setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                //.setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                //.setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
                //.setDrawCubeProjection(false)

                // == CAMERA CALIBRATION ==
                // If you do not manually specify calibration parameters, the SDK will attempt
                // to load a predefined calibration for your camera.
                //.setLensIntrinsics(578.272, 578.272, 402.145, 221.506)
                // ... these parameters are fx, fy, cx, cy.

                .build();

        // Adjust Image Decimation to trade-off detection-range for detection-rate.
        // eg: Some typical detection data using a Logitech C920 WebCam
        // Decimation = 1 ..  Detect 2" Tag from 10 feet away at 10 Frames per second
        // Decimation = 2 ..  Detect 2" Tag from 6  feet away at 22 Frames per second
        // Decimation = 3 ..  Detect 2" Tag from 4  feet away at 30 Frames Per Second (default)
        // Decimation = 3 ..  Detect 5" Tag from 10 feet away at 30 Frames Per Second (default)
        // Note: Decimation can be changed on-the-fly to adapt during a match.
        //aprilTag.setDecimation(3);

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        // Choose a camera resolution. Not all cameras support all resolutions.
        //builder.setCameraResolution(new Size(640, 480));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        //builder.enableLiveView(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        //builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(aprilTag);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Disable or re-enable the aprilTag processor at any time.
        //visionPortal.setProcessorEnabled(aprilTag, true);

    }   // end method initAprilTag()


    private void telemetryAprilTag() {

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection instanceof AprilTagSingleDetection) {
                AprilTagSingleDetection singleDet = (AprilTagSingleDetection) detection;

                if (singleDet.metadata != null) {
                    telemetry.addLine(String.format("\n==== (ID %d) %s", singleDet.id, singleDet.metadata.name));
                    telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                    telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                    telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
                } else {
                    telemetry.addLine(String.format("\n==== (ID %d) Unknown", singleDet.id));
                    telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", singleDet.center.x, singleDet.center.y));
                }
            } else {
                AprilTagClusterDetection clusterDet = (AprilTagClusterDetection) detection;
                telemetry.addLine(String.format("\n==== Tag Cluster (%s)", clusterDet.metadata.name));
                telemetry.addLine(String.format("Percent tags found: %d", clusterDet.percentClusterFound));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            }
        }   // end for() loop

        // Add "key" information to telemetry
        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        telemetry.addLine("RBE = Range, Bearing & Elevation");
    }   // end method telemetryAprilTag()
    private double returnCameraData(String dataToReturn, String aprilTagCluster) {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());
        double returnValue = 0;

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection instanceof AprilTagSingleDetection) {
                returnValue = 0;
            } else {
                AprilTagClusterDetection clusterDet = (AprilTagClusterDetection) detection;
                if (clusterDet.metadata.name.equals(aprilTagCluster)) {
                    switch (dataToReturn) {
                        case "x":
                            returnValue = clusterDet.ftcPose.x;
                            break;
                        case "y":
                            returnValue = clusterDet.ftcPose.y;
                            break;
                        case "z":
                            returnValue = clusterDet.ftcPose.z;
                            break;
                        case "pitch":
                            returnValue = clusterDet.ftcPose.pitch;
                            break;
                        case "roll":
                            returnValue = clusterDet.ftcPose.roll;
                            break;
                        case "yaw":
                            returnValue = clusterDet.ftcPose.yaw;
                            break;
                        case "range":
                            returnValue = clusterDet.ftcPose.range;
                            break;
                        case "bearing":
                            returnValue = clusterDet.ftcPose.bearing;
                            break;
                        case "elevation":
                            returnValue = clusterDet.ftcPose.elevation;
                            break;
                        default:
                            returnValue = 0;
                            break;
                    }
                }
            }
        }

        return returnValue;
    }
}

