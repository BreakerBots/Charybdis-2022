// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.devices;

import frc.robot.Constants;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AirCompressor extends SubsystemBase {
  private Compressor compressor;

  /** Creates a new AirCompressor. */
  public  AirCompressor() {
    compressor = new Compressor(Constants.PCM_ID, PneumaticsModuleType.CTREPCM);
    compressor.enableAnalog(Constants.MIN_PSI, Constants.MAX_PSI);
  }

  public void startCompressor() {
    System.out.print("Compressor enabled!\n");
    compressor.enableDigital();
  }

  public void stopCompressor() {
    System.out.print("Compressor disabled!\n");
    compressor.disable();
  }

  public boolean getCompressorState() {
    return compressor.enabled();
  }

  @Override
  public void periodic() {
    // System.out.println(compressor.());
    // This method will be called once per scheduler run
  }
}
