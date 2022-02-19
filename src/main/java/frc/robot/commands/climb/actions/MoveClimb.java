// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climb.actions;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DashboardControl;
import frc.robot.subsystems.devices.ClimbWatchdog;

public class MoveClimb extends CommandBase {
  private Climber climber;
  private double target;
  private ClimbWatchdog watchdog;
  /** Uses PID to move climber and robot to set distance 
   * (NOTE: distance base on motor ticks and winch extension, 
   * does not use actual climber extension distance) */
  public MoveClimb( Climber climbArg, double targetExtension, ClimbWatchdog watchdogArg) {
    // Use addRequirements() here to declare subsystem dependencies.
    climber = climbArg;
    target = targetExtension;
    watchdog = watchdogArg;
    addRequirements(climber);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    climber.setIsClimbing(true);
    DashboardControl.log("MOVEING CLIMB");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!watchdog.getClimbForceEnd()) {
        double curExtL = climber.getLeftClimbTicks();
        double lSpeed = climber.lClimbPID.calculate(curExtL, target);
        climber.moveLClimb(lSpeed);
        double curExtR = climber.getLeftClimbTicks();
        double rSpeed = climber.rClimbPID.calculate(curExtR, target);
        climber.moveRClimb(rSpeed);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (climber.lClimbPID.atSetpoint() && climber.rClimbPID.atSetpoint()) || watchdog.getClimbForceEnd();
  }
}
