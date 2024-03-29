// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climb.sequenceManagement;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DashboardControl;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class endClimbSequence extends InstantCommand {
  Climber climb;
  public endClimbSequence(Climber climbArg) {
    // Use addRequirements() here to declare subsystem dependencies.
    climb = climbArg;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    DashboardControl.log("CLIMB SEQUENCE END!");
    climb.setIsClimbing(false);
  }
}
