// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climb.actions;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class MoveClimb extends CommandBase {
  Climber climber;
  double target;
  /** Uses PID to move climber and robot to set distance 
   * (NOTE: distance base on motor ticks and winch extension, 
   * does not use actual climber extension distance) */
  public MoveClimb( Climber climbArg, double targetExtension) {
    // Use addRequirements() here to declare subsystem dependencies.
    climber = climbArg;
    target = targetExtension;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double curExt = climber.getClimbTicks();
    double climbMoveSpeed = climber.climbPID.calculate(curExt, target);
    climber.extendClimb(climbMoveSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    climber.climbSequenceProgress ++;
    System.out.println("CLIMB SEQUENCE PROGRESS: " + climber.climbSequenceProgress + " of " + climber.climbSequenceTotal);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return climber.climbPID.atSetpoint();
  }
}
