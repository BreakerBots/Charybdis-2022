// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.DrivePivot;
import frc.robot.commands.drive.DriveStraight;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.devices.IMU;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class OffTarmack_G extends SequentialCommandGroup {
  /** Creates a new OffTarmack_H1. */
  public OffTarmack_G(Drive driveArg, IMU imuArg) {
    addCommands(new DriveStraight(driveArg, imuArg, 100, 0.3, 4)
                );
  }
}
