// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Devices;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AirCompressor extends SubsystemBase {
  private Compressor compressor;

  /** Creates a new AirCompressor. */
  public  AirCompressor() {
    compressor = new Compressor(PneumaticsModuleType.CTREPCM);
    
  }

  /** Returns air pressure in psi */
  public double getPSI() {
    return compressor.getPressure();
  }

  public void startCompressor() {
    compressor.enableDigital();
  }

  @Override
  public void periodic() {
    
    // This method will be called once per scheduler run
  }
}
