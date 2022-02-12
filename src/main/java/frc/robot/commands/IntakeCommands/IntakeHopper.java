// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intakeCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;

/** Runs the hopper subsystem while the intake is active. */
public class IntakeHopper extends CommandBase {
  Hopper hopper;
  Intake intake;
  private long pauseCountA;
  private boolean end;

  /**
   * Creates a new IntakeHopper.
   * 
   * @param hopperArg Hopper subsystem from RobotContainer.
   * @param intakeArg Intake subsystem from RobotContainer.
   */
  public IntakeHopper(Hopper hopperArg, Intake intakeArg) {
    // Use addRequirements() here to declare subsystem dependencies.
    hopper = hopperArg;
    intake = intakeArg;
    // addRequirements(hopper);
    // addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!intake.intakeState) {
      end = true;
    } else if (intake.intakeState) {
      end = false;
    }
    if (hopper.getHopperPos1() && !hopper.getHopperPos2()) {
      hopper.hopperOn();
    } else if (!hopper.getHopperPos1() && hopper.getHopperPos2()) {
      pauseCountA++;
      if (pauseCountA >= Constants.HOPPER_DELAY_CYCLES) {
        hopper.hopperOff();
        pauseCountA = 0;
      }
    } else if (!hopper.getHopperPos1() && !hopper.getHopperPos2()) {
      hopper.hopperOn();
    } else if (hopper.getHopperPos1() && hopper.getHopperPos2()) {
      intake.intakeOffMethod();
      hopper.hopperOff();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    hopper.hopperOff();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return end;
  }
}
