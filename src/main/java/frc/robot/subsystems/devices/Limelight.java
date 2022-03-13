// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.devices;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class Limelight extends SubsystemBase {
  public PIDController aimPID;
  /** Creates a new Limelight. */
  public Limelight() {
    aimPID = new PIDController(Constants.KP_ANG, Constants.KI_ANG, Constants.KD_ANG);
    aimPID.setTolerance(Constants.ANG_POS_TOL, Constants.ANG_VEL_TOL);
  }

  public boolean hasTarget() {
    return (NetworkTableInstance.getDefault().getTable("shooter").getEntry("tv").getDouble(0) > 0);
  }

  public double getTargetInfo(String varName) {
    return NetworkTableInstance.getDefault().getTable("shooter").getEntry(varName).getDouble(0);
  }

  public boolean shooterIsAimed() {
    return aimPID.atSetpoint();
  }

  public double getTargetDistance() {
    double camHeight = Constants.HUB_HEIGHT_INS - Constants.SHOOT_CAM_HEIGHT;
    double corTarAng = Constants.SHOOT_CAM_ANG + getTargetInfo("ty");
    return (camHeight / Math.tan(corTarAng));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
