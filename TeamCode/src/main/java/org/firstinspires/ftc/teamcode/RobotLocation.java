package org.firstinspires.ftc.teamcode;

public class RobotLocation {
    // In degrees
    double rotationAngle;
    // In ft
    double xPosition;
    // In ft
    double yPosition;


    public RobotLocation(double rotationAngle, double xPosition, double yPosition) {
        this.rotationAngle = rotationAngle;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public double getRotationAngle() {
        while (rotationAngle > 180) {
            rotationAngle -= 360;
        }
        while (rotationAngle <= 180) {
            rotationAngle += 360;
        }
        return rotationAngle;
    }
    public double getXPosition() {
        return xPosition;
    }
    public double getYPosition() {
        return yPosition;
    }


    public void setRotationAngle(double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }
    public void setXPosition(double xPosition) {
        this.xPosition = xPosition;
    }
    public void setYPosition(double yPosition) {
        this.yPosition = yPosition;
    }
}
