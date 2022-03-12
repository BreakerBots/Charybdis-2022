// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.devices;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Drive;



public class Limelight extends SubsystemBase {
  public enum TargetDirection {
    FRONT,
    SHOOTER,
    LEFT,
    RIGHT,
    FRONT_RIGHT,
    FRONT_LEFT,
    SHOOTER_RIGHT,
    SHOOTER_LEFT,
    NONE
  }

  private double aimSpd = 0;
  public boolean aiming = false;

  public TargetDirection targetDirection;
  /** Creates a new Limelight. */
  Drive drive;
  public Limelight(Drive driveArg) {
    drive = driveArg;
  }
  
  public boolean frontHasTgt() {
    return NetworkTableInstance.getDefault().getTable("front").getEntry("tv").getDouble(0) >= 1;
  }
  
  public boolean shooterHasTgt() {
    return NetworkTableInstance.getDefault().getTable("shooter").getEntry("tv").getDouble(0) >= 1;
  }

  public boolean leftHasTgt() {
    return NetworkTableInstance.getDefault().getTable("left").getEntry("tv").getDouble(0) >= 1;
  }

  public boolean rightHasTgt() {
    return NetworkTableInstance.getDefault().getTable("right").getEntry("tv").getDouble(0) >= 1;
  }

  public double getTgtInfo(String camName, String varName) {
    return NetworkTableInstance.getDefault().getTable(camName).getEntry(varName).getDouble(0);
    
  }

  private void setAimSpd(double spd) {
    aimSpd = spd;
  }

  public void aimShooter() {
    if (rightHasTgt()) {
      setAimSpd(1);
    } else if (leftHasTgt()) {
      setAimSpd(-1);
    } else if (frontHasTgt() && !leftHasTgt() && !rightHasTgt()) {
      double spd = getTgtInfo("front", "tx") <= 0 ? -1 : 1;
      setAimSpd(spd);
    } else if (shooterHasTgt() && !leftHasTgt() && !rightHasTgt()) {
      double spd = drive.aimPID.calculate(getTgtInfo("shooter", "tx"), 0);
      setAimSpd(spd);
    }
  }

  public double getAimSpd() {
      return aimSpd;
  }

  public void startAiming() {
    aiming = true;
  }

  public void stopAiming() {
    aiming = false;
  }

  public boolean aimPIDAtSetPoint() {
    return drive.aimPID.atSetpoint();
  }

  @Override
  public void periodic() {
    if (aiming) {
      aimShooter();
      drive.teleopMove(0, getAimSpd());
    } else {
      setAimSpd(0);
    }
  }
}
