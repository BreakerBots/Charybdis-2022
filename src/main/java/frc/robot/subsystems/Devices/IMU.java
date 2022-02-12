// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Devices;

import java.lang.reflect.Array;

import com.ctre.phoenix.sensors.Pigeon2;
import com.ctre.phoenix.sensors.WPI_PigeonIMU;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Convert;

public class IMU extends SubsystemBase {
  private Pigeon2 pigeon;
  private double imuInvert;

  /** Creates a new PigeonIMU. */
  public IMU() {
    pigeon = new Pigeon2(Constants.IMU_ID);
  }

  @Override
  public void periodic() {
    //System.out.println("IMU yaw: " + getYaw());
    // This method will be called once per scheduler run
    if (Constants.IMU_INVERTED == true) {
      imuInvert = -1;
    }
    else {
      imuInvert = 1;
    }
  }

  /** Returns pitch angle within +- 180 degrees */
  public double getPitch() {
    return Convert.ANGLE_CONVERT(pigeon.getPitch());
  }

  /** Returns yaw angle within +- 180 degrees */
  public double getYaw() {
    return imuInvert * (Convert.ANGLE_CONVERT(pigeon.getYaw())); // Convert.ANGLE_CONVERT(pigeon.getYaw());
  }

  /** Returns roll angle within +- 180 degrees */
  public double getRoll() {
    return Convert.ANGLE_CONVERT(pigeon.getRoll());
  }

  /** Returns raw yaw, pitch, and roll angles in an array */
  public double[] getRawAngles() {
    double[] RawYPR = new double[3];
    pigeon.getYawPitchRoll(RawYPR);
    return RawYPR;
  }

  /** Resets yaw to 0 degrees */
  public void reset() {
    pigeon.setYaw(0);
  }

  public double getGyroRates(int arrayElement) {
    double[] rawRates = new double[2];
    pigeon.getRawGyro(rawRates);
    return rawRates[arrayElement];
  }

  public double getPitchRate() {
    return getGyroRates(0);
  }

  public double getYawRate() {
    return getGyroRates(1);
  }

  public double getRollRate() {
    return getGyroRates(2);
  }
}
