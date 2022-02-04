// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Devices;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Convert;
import frc.robot.subsystems.Drive;

public class Odometry extends SubsystemBase {

  private Drive driveArg;
  private IMU imuArg;

  private DifferentialDriveOdometry odometer;
  private double xPos;
  private double yPos;
  private double anglePos;
  
  /** Creates a new Odometry. */
  public Odometry(Drive driveArg, IMU imuArg) {
    odometer = new DifferentialDriveOdometry(getRot2D());
  }

  @Override
  public void periodic() {
    odometer.update(getRot2D(), getMetersL(), getMetersR());
    xPos = Convert.M_TO_IN(odometer.getPoseMeters().getX());
    yPos = Convert.M_TO_IN(odometer.getPoseMeters().getY());
    anglePos = odometer.getPoseMeters().getRotation().getDegrees();
    // This method will be called once per scheduler run
  }

  public double getXPos() {
    return xPos;
  }

  public double getYPos() {
    return yPos;
  }

  public double getAnglePos() {
    return anglePos;
  }

  public void resetOdometer() {
    driveArg.resetEncoders();
    odometer.resetPosition(new Pose2d(0, 0, new Rotation2d(0)), getRot2D());
    // Must be called if we ever reset the gyro
  }

  public Rotation2d getRot2D() {
    return Rotation2d.fromDegrees(imuArg.getYaw());
  }

  public double getMetersL() {
    return Convert.IN_TO_M(Convert.TICK_TO_IN(driveArg.getLeftTicks()));
  }

  public double getMetersR() {
    return Convert.IN_TO_M(Convert.TICK_TO_IN(driveArg.getLeftTicks()));
  }

}
