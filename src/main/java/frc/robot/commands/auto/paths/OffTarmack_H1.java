// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.DriveStraight;
import frc.robot.commands.drive.DrivePivot;
import frc.robot.commands.drive.DriveTurn;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.devices.IMU;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class OffTarmack_H1 extends SequentialCommandGroup {
  /** Creates a new OffTarmack_H1. */
  public OffTarmack_H1(Drive driveArg, IMU imuArg) {
        // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    //addCommands( new Pivot(driveArg, imuArg, 180, 0.5));
    addCommands(new DriveTurn(driveArg, imuArg, 90, 72, 0.65),
                new DriveStraight(driveArg, imuArg, -72, 0.3),
                new DriveTurn(driveArg, imuArg, 90, 72, 0.65),
                new DrivePivot(driveArg, imuArg, 90, 0.5), 
                new DriveStraight(driveArg, imuArg, 60, 0.3),
                new DrivePivot(driveArg, imuArg, 90, 0.5)
                // new MoveStraight(driveArg, 60, 0.3)
                );
  }
}
