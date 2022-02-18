// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climb.sequenceManagement;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DashboardControl;

public class SequenceWatchdog extends CommandBase {
  /** Creates a new ManualTerminateSequence. */
  XboxController xbox;
  private long cycleCount;
  public SequenceWatchdog(XboxController controllerArg) {
    // Use addRequirements() here to declare subsystem dependencies.
    xbox = controllerArg;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    cycleCount ++;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    cycleCount = 0;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (xbox.getStartButtonPressed()) {
      DashboardControl.log("CLIMB SEQUENCE MANUALLY STOPPED!");
      return true;
    } else if (cycleCount > 2250) {
      DashboardControl.log("CLIMB SEQUENCE TIMED OUT!");
      return true;
    }
    else {
      return false;
    }
  }
}
