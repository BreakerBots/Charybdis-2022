// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intakeCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;

/** Turns the robot's intake on/off */
public class IntakeToggle extends CommandBase {
  Intake intake;
  Hopper hopper;

  /**
   * Creates a new IntakeToggle.
   * 
   * @param intakeArg Intake subsystem from RobotContainer.
   * @param hopperArg Hopper subsystem from RobotContainer.
   */
  public IntakeToggle(Intake intakeArg, Hopper hopperArg) {
    intake = intakeArg;
    hopper = hopperArg;
    // addRequirements(intake);
    // addRequirements(hopper);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (intake.intakeState) {
      intake.intakeOffMethod();
      hopper.hopperOff();
    } else {
      intake.intakeOnMethod();
      // hopper.hopperOn();
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
