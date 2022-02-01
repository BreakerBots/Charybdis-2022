// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AirCompressor extends SubsystemBase {
  private Compressor compressor;

  /** Creates a new AirCompressor. */
  public AirCompressor() {
    compressor = new Compressor(PneumaticsModuleType.CTREPCM);
  }

  /** Tells the air compressor to compress air */
  public void start() {
    compressor.enableDigital();
  }

  /** Tells the air compressor to stop compressing air */
  public void stop() {
    compressor.disable();
  }

  /** Returns air pressure in psi */
  public double getPSI() {
    return compressor.getPressure();
  }

  /** Checks if air was compressed to desired psi, stops compressor if true, starts it if false */
  public boolean hasCompressed(double psi) {
    boolean atTarget = getPSI() >= psi;
    if (atTarget) {
      stop();
    } else {
      start();
    }
    return atTarget;
  }

  /** Checks for default of 120 psi */
  public boolean hasCompressed() {
    return hasCompressed(120);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
