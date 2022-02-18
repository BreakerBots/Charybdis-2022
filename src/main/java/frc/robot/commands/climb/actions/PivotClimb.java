// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climb.actions;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DashboardControl;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PivotClimb extends InstantCommand {
  private Climber climber;
  /** Toggles climber pistons to move arms between extended and retracted positions */
  public PivotClimb(Climber climbArg) {
    // Use addRequirements() here to declare subsystem dependencies.
    climber = climbArg;
    addRequirements(climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
   // climber.toggleClimbSol();
    DashboardControl.log("CLIMB ARM PIVOTED");
    climber.climbSequenceProgress ++;
    System.out.println("CLIMB SEQUENCE PROGRESS: " + climber.climbSequenceProgress + " of " + climber.climbSequenceTotal);
  }
}
