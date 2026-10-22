package org.firstinspires.ftc.teamcode;

public class Drive {
    public double[] getMotorPower(double forward, double right, double rotateCW) {

        // inverting y stick
        forward *= -1;

        // Calculate individual wheel speeds
        double backLeftPower;
        double frontLeftPower;
        double backRightPower;
        double frontRightPower;

        if (rotateCW < 0.2 && rotateCW > -0.2) {
            backLeftPower = forward - right + rotateCW;
            frontLeftPower = forward + right + rotateCW;
            backRightPower = forward - right - rotateCW;
            frontRightPower = forward + right - rotateCW;
        } else if (rotateCW < 0.7 || rotateCW > -0.7){
            double multiplier = 0.9;
            backLeftPower = (forward * multiplier) - (right * multiplier) + rotateCW;
            frontLeftPower = (forward * multiplier) + (right * multiplier) + rotateCW;
            backRightPower = (forward * multiplier) - (right * multiplier) - rotateCW;
            frontRightPower = (forward * multiplier) + (right * multiplier) - rotateCW;
        } else {
            double multiplier = 0.75;
            backLeftPower = (forward * multiplier) - (right * multiplier) + rotateCW;
            frontLeftPower = (forward * multiplier) + (right * multiplier) + rotateCW;
            backRightPower = (forward * multiplier) - (right * multiplier) - rotateCW;
            frontRightPower = (forward * multiplier) + (right * multiplier) - rotateCW;
        }


        // Normalize speeds if any value exceeds 1.0 to maintain directional integrity
        double max = Math.max(1.0, Math.max(
                Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower)),
                Math.max(Math.abs(backLeftPower), Math.abs(backRightPower))
        ));
        backLeftPower /= max;
        frontLeftPower /= max;
        backRightPower /= max;
        frontRightPower /= max;


        return new double[] {backLeftPower, frontLeftPower, backRightPower, frontRightPower};
    }
}