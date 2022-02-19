// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climb.actions;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DashboardControl;
import frc.robot.subsystems.devices.ClimbWatchdog;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PivotClimb extends InstantCommand {
  private Climber climber;
  private ClimbWatchdog terrier;
  private boolean manual;
  /** Toggles climber pistons to move arms between extended and retracted positions */
  public PivotClimb(Climber climbArg, ClimbWatchdog watchdogArg, boolean isManual) {
    // Use addRequirements() here to declare subsystem dependencies.
    climber = climbArg;
    terrier = watchdogArg;
    manual = isManual;
    addRequirements(climber);
    addRequirements(terrier);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (!manual) {
      climber.setIsClimbing(true);
    }
    if (!terrier.getClimbForceEnd()) {
      climber.toggleClimbSol();
      DashboardControl.log("CLIMB ARM PIVOTED");
    }
  }
}
