// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.devices;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FMS_Handler extends SubsystemBase {
  /** Creates a new FMS_Handler. */
  public FMS_Handler() {}

  /** Boolean getter of alliance (red or blue) for Dashbord 
   * Blue = true
   * red / invalid = false
  */
  public boolean getAllianceBool() {
    switch (DriverStation.getAlliance()) {
      case Blue: return true;
      case Red: return false;
      case Invalid: return false;
      default: return false;
    }
  }
 
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
