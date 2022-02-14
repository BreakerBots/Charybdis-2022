// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.FlywheelState;

/** Spins the shooter's flywheel */
public class ChargeFlywheel extends CommandBase {
  Shooter shooter;
  XboxController xbox;
  /**
   * Creates a new ChargeFlywheel.
   * @param shooterArg Shooter subsystem from RobotContainer.
   * @param controllerArg Xbox controller from RobotContainer.
   */
  public ChargeFlywheel(Shooter shooterArg, XboxController controllerArg) {
    shooter = shooterArg;
    xbox = controllerArg;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("ChargeFlywheel initaited");
    //if (shooter.flywheelState == FlywheelState.IDLE || shooter.flywheelState == FlywheelState.OFF) {
      shooter.flyweelFullOn();
    //}
  } 

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("RPM: " + shooter.getFlywheelRPM());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(shooter.getFlywheelRPM() > Constants.FLYWHEEL_TAR_SPEED) {
      System.out.println("FLYWHEEL CHARGED!");
      shooter.flywheelState = FlywheelState.CHARGED;
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
