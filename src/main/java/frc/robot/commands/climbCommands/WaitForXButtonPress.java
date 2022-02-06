// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climbCommands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class WaitForXButtonPress extends CommandBase {
  /** Creates a new WaitForButtonPress. */
  XboxController xbox;
  Climber climber;
  private long cycleCount;
  public WaitForXButtonPress(XboxController controllerArg, Climber climberArg) {
    // Use addRequirements() here to declare subsystem dependencies.
    climber = climberArg;
    addRequirements(climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("PLEASE PRESS X BUTTON AGAIN TO COMPLETE CLIMB");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    cycleCount++;
    if ((cycleCount % 400) == 0) {
      System.out.println("PLEASE PRESS X BUTTON AGAIN TO COMPLETE CLIMB");
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    climber.climbSequenceProgress ++;
    System.out.println("CIMB SEQUENCE PROGRESS: " + climber.climbSequenceProgress + " of " + climber.climbSequenceTotal);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (xbox.getXButtonPressed()) {
      return true;
    }
    else {
      return false;
    }
  }
}
