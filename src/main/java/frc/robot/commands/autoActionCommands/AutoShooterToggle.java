// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autoActionCommands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoShooterToggle extends InstantCommand {
  Shooter shooter;
  public AutoShooterToggle(Shooter shooterArg) {
    // Use addRequirements() here to declare subsystem dependencies.
    shooter = shooterArg;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (shooter.autoShoot == false) {
      shooter.autoShoot = true;
    }
    else {
      shooter.autoShoot = false;
    }
  }
}
