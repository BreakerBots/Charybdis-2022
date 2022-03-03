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

  /**
   * Starts the compressor.
   * <p>
   * NOTE: The compressor will turn off automatically when it reaches the desired
   * pressure.
   */
  public void startCompressor() {
    DashboardControl.log("Compressor enabled!");
    compressor.enableDigital();
  }

  /**
   * Completely disables the compressor.
   * <p>
   * NOTE: Only call this method when premature disabling of the compressor is
   * desired.
   */
  public void stopCompressor() {
    DashboardControl.log("Compressor disabled!");
    compressor.disable();
  }

  /**
   * Returns the state of the compressor.
   * 
   * @return true if enabled, false if disabled.
   */
  public boolean compressorIsEnabled() {
    return compressor.enabled();
  }

  /**
   * Returns the pressurization of air on the robot.
   * 
   * @return Current air pressure, in PSI.
   */
  public double getPressure() {
    return pcm.getCompressorCurrent();
  }

  /**
   * Automatically disables the compressor if it has been running for more than
   * 6000 cycles.
   */
  public void autoTimeout() {
    if (compressorIsEnabled()) {
      cycleCount++;
      if (cycleCount > Constants.COMPRESSOR_TIMEOUT_CYCLES) {
        stopCompressor();
        cycleCount = 0;
        DashboardControl.log("WARNING: " + "COMPRESSOR TIMED OUT!");
      }
    } else {
      cycleCount = 0;
    }
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("PSI", pcm.getCompressorCurrent());
    autoTimeout();
   // System.out.println(compressor.getPressureSwitchValue());
  }
}
