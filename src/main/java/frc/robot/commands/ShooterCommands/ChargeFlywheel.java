// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooterCommands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class ChargeFlywheel extends CommandBase {
  Shooter shooter;
  XboxController xbox;
  public ChargeFlywheel(Shooter shooterArg, XboxController controllerArg) {
    shooter = shooterArg;
    xbox = controllerArg;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (shooter.flyweelState == false) {
      shooter.flyweelOn();
    }
    else if (shooter.flyweelState) {
      System.out.println("Cant charge flywheel twice at once!");
    }
  } 

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    xbox.setRumble(RumbleType.kLeftRumble, 1);
    xbox.setRumble(RumbleType.kRightRumble, 1);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(shooter.getFlywheelRPM() == Constants.FLYWHEEL_TAR_SPEED) {
      System.out.println("FLYWHEEL CHARGED!");
      return true;
    } else if (xbox.getLeftBumperPressed()) {
      shooter.flyweelOff();
      System.out.println("FLYWHEEL MANUALY STOPED!");
      return true;
    }
    
    else {
      return false;
    }
}}
