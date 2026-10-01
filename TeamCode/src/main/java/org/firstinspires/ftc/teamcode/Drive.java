package org.firstinspires.ftc.teamcode;

public class Drive {
    public double[] getMotorPower(double forward, double right, double rotateCW) {

        // inverting y stick
        forward *= -1;
        double rotateMultiplier = 0.5;

        // Calculate individual wheel speeds
        double backLeftSpeed;
        double frontLeftSpeed;
        double backRightSpeed;
        double frontRightSpeed;

        if (rotateCW < 0.2) {
            backLeftSpeed   = forward - right + rotateCW;
            frontLeftSpeed  = forward + right + rotateCW;
            backRightSpeed  = forward - right - rotateCW;
            frontRightSpeed = forward + right - rotateCW;
        } else if (rotateCW < 0.7){
            backLeftSpeed   = (forward * 0.75) - (right * 0.75) + rotateCW;
            frontLeftSpeed  = (forward * 0.75) + (right * 0.75) + rotateCW;
            backRightSpeed  = (forward * 0.75) - (right * 0.75) - rotateCW;
            frontRightSpeed = (forward * 0.75) + (right * 0.75) - rotateCW;
        } else {
            backLeftSpeed   = (forward * 0.6) - (right * 0.6) + rotateCW;
            frontLeftSpeed  = (forward * 0.6) + (right * 0.6) + rotateCW;
            backRightSpeed  = (forward * 0.6) - (right * 0.6) - rotateCW;
            frontRightSpeed = (forward * 0.6) + (right * 0.6) - rotateCW;
        }



        // Normalize speeds if any value exceeds 1.0 to maintain directional integrity
        double max = Math.max(1.0, Math.max(
                Math.max(Math.abs(frontLeftSpeed), Math.abs(frontRightSpeed)),
                Math.max(Math.abs(backLeftSpeed), Math.abs(backRightSpeed))
        ));
        backLeftSpeed   /= max;
        frontLeftSpeed  /= max;
        backRightSpeed  /= max;
        frontRightSpeed /= max;


        return new double[] {backLeftSpeed, frontLeftSpeed, backRightSpeed, frontRightSpeed};
    }
}