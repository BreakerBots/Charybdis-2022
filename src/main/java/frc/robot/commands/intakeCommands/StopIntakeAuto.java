// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intakeCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class StopIntakeAuto extends CommandBase {
  /** Creates a new StopIntakeAuto. */
  private long cycleCount;
  private double seconds;
  private boolean end;
  public StopIntakeAuto(double secondsArg) {
    // Use addRequirements() here to declare subsystem dependencies.
    seconds = secondsArg;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    cycleCount++;
    if (cycleCount >= (seconds * 200)) {
      end = true;
    }
    else {
      end = false;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return end;
  }
}
