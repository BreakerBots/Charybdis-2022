// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.ShooterMode;
import frc.robot.subsystems.Shooter;

public class ToggleShooterMode extends CommandBase {
  Shooter shooter;
  public ToggleShooterMode(Shooter shooterArg) {
    shooter = shooterArg;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (shooter.shooterMode == ShooterMode.UP) {
      shooter.shooterMode = ShooterMode.LOW;
    } else if (shooter.shooterMode == ShooterMode.LOW) {
      shooter.shooterMode = ShooterMode.LAUNCH;
    } else if (shooter.shooterMode == ShooterMode.LAUNCH) {
      shooter.shooterMode = ShooterMode.UP;
    } else {
      shooter.shooterMode = ShooterMode.UP;
    }
  }


  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
