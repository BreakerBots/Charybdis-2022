// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autoActionCommands;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;

public class MotorTest extends CommandBase {
  /** Creates a new MotorRotationTest. */
  Drive drive;
  double speed;
  double rotate;
  public MotorTest(Drive driveArg, double rotationsArg, double speedArg) {
    // Use addRequirements() here to declare subsystem dependencies.
    drive = driveArg;
    speed = speedArg;
    rotate = rotationsArg;
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drive.move(speed, 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return drive.getLeftTicks() >= (rotate * Constants.TALON_FX_TICKS);
  }
}
