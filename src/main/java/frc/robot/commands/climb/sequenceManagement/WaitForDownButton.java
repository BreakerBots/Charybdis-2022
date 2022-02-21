// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climb.sequenceManagement;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DashboardControl;
import frc.robot.subsystems.devices.ClimbWatchdog;

public class WaitForDownButton extends CommandBase {
  private XboxController xbox;
  private Climber climber;
  private ClimbWatchdog husky;
  private long cycleCount;
  /** Pauses climb sequence to wait for user imput to continue (D_PAD Down Button) */
  public WaitForDownButton(XboxController controllerArg, Climber climberArg, ClimbWatchdog watchdogArg) {
    // Use addRequirements() here to declare subsystem dependencies.
    climber = climberArg;
    husky = watchdogArg;
    addRequirements(climber);
    addRequirements(husky);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    climber.setIsClimbing(true);
    if (!husky.getClimbForceEnd()) {
    DashboardControl.log("PLEASE PRESS D-PAD DOWN BUTTON TO COMPLETE CLIMB");
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!husky.getClimbForceEnd()) {
      cycleCount++;
      if ((cycleCount % 400) == 0) {
        DashboardControl.log("PLEASE PRESS D-PAD DOWN BUTTON TO COMPLETE CLIMB");
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
   return (xbox.getPOV() == Constants.D_DOWN) || husky.getClimbForceEnd();
  }
}
