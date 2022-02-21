// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.devices;

import frc.robot.Constants;
import frc.robot.subsystems.DashboardControl;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AirCompressor extends SubsystemBase {
  private Compressor compressor;
  private PneumaticsControlModule pcm;
  private long cycleCount;


  /** Creates a new AirCompressor. */
  public AirCompressor(PneumaticsControlModule pcmArg) {
    compressor = new Compressor(Constants.PCM_ID, PneumaticsModuleType.CTREPCM);
    pcm = pcmArg;
    compressor.enableDigital();
    compressor.disable();
    // compressor.enableAnalog(Constants.MIN_PSI, Constants.MAX_PSI);
  }

  public void startCompressor() {
    DashboardControl.log("Compressor enabled!");
    compressor.enableDigital();
  }

  public void stopCompressor() {
    DashboardControl.log("Compressor disabled!");
    compressor.disable();
  }

  public boolean compressorIsEnabled() {
    return compressor.enabled();
  }

  public double getPressure() {
    return pcm.getCompressorCurrent();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("PSI", pcm.getCompressorCurrent());
    if (compressorIsEnabled()) {
      cycleCount++;
      if (cycleCount > 6000) {
        stopCompressor();
        cycleCount = 0;
        SmartDashboard.putString("WARNING","COMPRESSOR TIMED OUT!");
      }
    } else {
      cycleCount = 0;
    }
  }
}
