// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Intake.IntakeState;

/** Turns the robot's intake on/off */
public class ToggleIntake extends InstantCommand {
  private Intake intake;
  private Hopper hopper;

  /** Creates a new IntakeToggle. */
  public ToggleIntake() {
    intake = Robot.m_robotContainer.intakeSys;
    hopper = Robot.m_robotContainer.hopperSys;
    // addRequirements(intake);
    // addRequirements(hopper);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (intake.intakeStatus == IntakeState.ON) {
      intake.intakeOffMethod();
      hopper.hopperOff();
    } else {
      intake.intakeOnMethod();
      // hopper.hopperOn();
    }
  }
}
