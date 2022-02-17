// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;

/** Turns the robot's intake on/off */
public class ToggleIntake extends InstantCommand {
  private Intake intake;
  private Hopper hopper;
  /**
   * Creates a new IntakeToggle.
   * 
   * @param intakeArg Intake subsystem from RobotContainer.
   * @param hopperArg Hopper subsystem from RobotContainer.
   */
  public ToggleIntake(Intake intakeArg, Hopper hopperArg) {
    intake = intakeArg;
    hopper = hopperArg;
    // addRequirements(intake);
    // addRequirements(hopper);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (intake.intakeIsRunning()) {
      intake.deactivateIntake();
      hopper.deactivateHopper();
    } else {
      intake.activateIntake();
      // hopper.hopperOn();
    }
  }
}
