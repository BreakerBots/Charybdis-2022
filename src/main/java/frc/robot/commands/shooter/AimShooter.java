// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.devices.Limelight;

public class AimShooter extends CommandBase {
  /** Creates a new AimShooter. */
  Limelight camera;
  Drive drive;
  public AimShooter(Limelight limelightArg, Drive driveArg) {
    // Use addRequirements() here to declare subsystem dependencies.
    camera = limelightArg;
    drive = driveArg;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    camera.aimPID.calculate(camera.getTargetInfo("tx"), 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return !camera.hasTarget();
  }
}
