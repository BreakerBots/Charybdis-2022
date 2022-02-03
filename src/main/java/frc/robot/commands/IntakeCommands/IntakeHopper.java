// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;

public class IntakeHopper extends CommandBase {
  /** Creates a new IntakeHopper. */
  Hopper hopper;
  Intake intake;
  public IntakeHopper(Hopper hopperArg, Intake intakeArg) {
    // Use addRequirements() here to declare subsystem dependencies.
    hopper = hopperArg;
    intake = intakeArg;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (hopper.getHopperPos1() && hopper.getHopperPos2() == false) {
      hopper.hopperOn();
    } 
    else if (hopper.getHopperPos1() == false && hopper.getHopperPos2()){
      hopper.hopperOff();
    }
    else if ((hopper.getHopperPos1() == false && hopper.getHopperPos2() == false)) {
      hopper.hopperOn();
    }
    else if (hopper.getHopperPos1() && hopper.getHopperPos2()) {
      hopper.hopperOn();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (intake.intakeState == false) {
      return true;
    }
    else {
      return false;
    }
  }
}