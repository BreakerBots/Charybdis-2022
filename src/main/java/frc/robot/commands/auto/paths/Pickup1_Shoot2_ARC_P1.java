// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auto.actions.MoveStraight;
import frc.robot.commands.auto.actions.Pivot;
import frc.robot.commands.auto.actions.Turn;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.devices.IMU;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Pickup1_Shoot2_ARC_H3 extends SequentialCommandGroup {
  /** Creates a new Pickup1_Shoot2_H3. */
  public Pickup1_Shoot2_ARC_H3(Drive driveArg, IMU imuArg) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
    new MoveStraight(driveArg, imuArg, 60, 0.3),
    new Pivot(driveArg, imuArg, 40, 0.3),
    new MoveStraight(driveArg, imuArg, -44, 0.3),
    new Turn(driveArg, imuArg, 65, 48, -0.7),
    new MoveStraight(driveArg, imuArg, -20, 0.3)
    );
  }
}
