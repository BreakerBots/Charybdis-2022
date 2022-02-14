// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auto.actions.MoveStraight;
import frc.robot.commands.auto.actions.Pivot;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Pickup1_Shoot2_H3 extends SequentialCommandGroup {
  /** Creates a new Pickup1_Shoot2_H1. */
  public Pickup1_Shoot2_H3() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new MoveStraight(60, 0.4),
      new Pivot(25, 0.4),
      new MoveStraight(-80, 0.6),
      new Pivot(-60, 0.3),
      new MoveStraight(-22, 0.2)
    );
  }
}
