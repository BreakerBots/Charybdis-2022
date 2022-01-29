// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.sensors.WPI_PigeonIMU;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Convert;

public class IMU extends SubsystemBase {
  private WPI_PigeonIMU pigeon;
  /** Creates a new IMU. */
  public IMU() {
    pigeon = new WPI_PigeonIMU(Constants.IMU_ID);
  }

  public double getPitch() {
    return Convert.ANGLE_CONVERT(pigeon.getPitch());
  }
  
  public double getYaw() {
    return Convert.ANGLE_CONVERT(pigeon.getYaw());
  }

  public double getRoll() {
    return Convert.ANGLE_CONVERT(pigeon.getRoll());
  }

  public double[] getRawAngles() {
    double[] RawYPR = new double[3];
    pigeon.getYawPitchRoll(RawYPR);
    return RawYPR;
  }

  public void reset() {
    pigeon.setYaw(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
