// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.subsystems.devices.AirCompressor;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ToggleCompressor extends InstantCommand {
  private AirCompressor compressor;
  public ToggleCompressor() {
    compressor = Robot.m_robotContainer.compressorSys;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (compressor.isEnabled()) {
      compressor.stopCompressor();
    } else {
      compressor.startCompressor();
    }
  }
}
