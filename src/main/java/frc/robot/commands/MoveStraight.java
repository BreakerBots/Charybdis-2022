// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Convert;
import frc.robot.subsystems.Drive;

public class MoveStraight extends CommandBase {
  private Drive drive;
  private double targetDistance;
  /** Creates a new MoveStraight. */
  public MoveStraight(Drive driveArg, double distanceInches) {
    drive = driveArg;
    addRequirements(drive);
    targetDistance = distanceInches;

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drive.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double curDist = Convert.TICK_TO_IN(drive.getLeftTicks());
    double feedForwardVal = drive.feedForwardCalc(4, 2); // Constants for desired vel, desired acc
    double feedBackVal = drive.distPIDCalc(curDist, targetDistance);
    double motorspeed = feedBackVal + feedForwardVal;
    
    drive.move(motorspeed, 0);
    // 1D movement back and forth
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return drive.atSetpointDist();
  }
}
