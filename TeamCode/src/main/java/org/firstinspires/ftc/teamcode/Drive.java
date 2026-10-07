package org.firstinspires.ftc.teamcode;

public class Drive {
    public double[] getMotorPower(double forward, double right, double rotateCW) {

        // inverting y stick
        forward *= -1;

        // Calculate individual wheel speeds
        double backLeftSpeed;
        double frontLeftSpeed;
        double backRightSpeed;
        double frontRightSpeed;

        if (rotateCW < 0.2 && rotateCW > -0.2) {
            backLeftSpeed   = forward - right + rotateCW;
            frontLeftSpeed  = forward + right + rotateCW;
            backRightSpeed  = forward - right - rotateCW;
            frontRightSpeed = forward + right - rotateCW;
        } else if (rotateCW < 0.7 || rotateCW > -0.7){
            double multiplier = 0.9;
            backLeftSpeed   = (forward * multiplier) - (right * multiplier) + rotateCW;
            frontLeftSpeed  = (forward * multiplier) + (right * multiplier) + rotateCW;
            backRightSpeed  = (forward * multiplier) - (right * multiplier) - rotateCW;
            frontRightSpeed = (forward * multiplier) + (right * multiplier) - rotateCW;
        } else {
            double multiplier = 0.75;
            backLeftSpeed   = (forward * multiplier) - (right * multiplier) + rotateCW;
            frontLeftSpeed  = (forward * multiplier) + (right * multiplier) + rotateCW;
            backRightSpeed  = (forward * multiplier) - (right * multiplier) - rotateCW;
            frontRightSpeed = (forward * multiplier) + (right * multiplier) - rotateCW;
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