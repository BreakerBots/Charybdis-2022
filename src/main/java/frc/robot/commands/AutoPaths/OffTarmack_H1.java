// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutoPaths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.MoveStraight;
import frc.robot.commands.Pivot;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.IMU;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class OffTarmack_H1 extends SequentialCommandGroup {
  /** Creates a new OffTarmack_H1. */
  public OffTarmack_H1(Drive driveArg, IMU imuArg) {
        // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new MoveStraight(driveArg, 16), new Pivot(driveArg, imuArg, 40), new MoveStraight(driveArg, 90));
  }
}