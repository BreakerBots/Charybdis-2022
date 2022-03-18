// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.Devices;

import com.ctre.phoenix.sensors.WPI_Pigeon2;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BreakerMath;

public class BreakerPigeon2 extends SubsystemBase {
  private WPI_Pigeon2 pigeon;
  private double imuInvert;
  private boolean isInverted;
  private double pitch;
  private double yaw;
  private double roll;
  private double [] wxyz = new double [3];
  private double xSpeed;
  private double ySpeed;
  private double zSpeed;

  /** Creates a new PigeonIMU. */
  public BreakerPigeon2(int deviceID, boolean isInverted) {
    pigeon = new WPI_Pigeon2(deviceID);
    isInverted = this.isInverted;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if (isInverted) {
      imuInvert = -1;
    } else {
      imuInvert = 1;
    }
    pitch = BreakerMath.constrainAngle(pigeon.getPitch());
    yaw = BreakerMath.constrainAngle(pigeon.getYaw()) * imuInvert;
    roll = BreakerMath.constrainAngle(pigeon.getRoll());
    
    setName("IMU");
    addChild("Pigeon", pigeon);
    
    calculate4DPosition();
  }

  /** Returns pitch angle within +- 360 degrees */
  public double getPitch() {
    return pitch;
  }

  /** Returns yaw angle within +- 360 degrees */
  public double getYaw() {
    return yaw;
  }

  /** Returns roll angle within +- 360 degrees */
  public double getRoll() {
    return roll;
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

  public short getRawAccelerometerVals(int arrayElement) {
    short[] accelVals = new short[2];
    pigeon.getBiasedAccelerometer(accelVals);
    return accelVals[arrayElement];
  }

  public double getIns2AccelX() {
    return (frc.robot.BreakerLib.Util.BreakerMath.fixedToFloat(getRawAccelerometerVals(0), 14) * 7.721772);
  }

  public double getIns2AccelY() {
    return (frc.robot.BreakerLib.Util.BreakerMath.fixedToFloat(getRawAccelerometerVals(1), 14) * 7.721772);
  }

  public double getIns2AccelZ() {
    return (frc.robot.BreakerLib.Util.BreakerMath.fixedToFloat(getRawAccelerometerVals(2), 14) * 7.721772);
  }

  private void calculate4DPosition() {
   wxyz[0] = yaw;
   xSpeed += getIns2AccelX();
   ySpeed += getIns2AccelY();
   zSpeed += getIns2AccelZ();
   wxyz[1] += xSpeed;
   wxyz[2] += ySpeed;
   wxyz[3] += zSpeed;
  }

  /** Returns WXYZ psoition of imu, W is angle and XYZ are position */
  public double[] get4DPosition() {
    return wxyz;
  }

  public void set4DPosition(double[] wxyz) {
    wxyz = this.wxyz;
  }

  public void reset4DPosition() {
    wxyz[0] = 0;
    wxyz[1] = 0;
    wxyz[2] = 0;
    wxyz[3] = 0;
    reset();
    xSpeed = 0;
    ySpeed = 0;
    zSpeed = 0;
  }

}
