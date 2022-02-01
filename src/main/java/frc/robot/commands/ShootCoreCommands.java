// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootCoreCommands extends SequentialCommandGroup {
  /** Creates a new ShootCoreCommands. */
  Shooter shooter;
  Intake intake;
  Hopper hopper;
  XboxController xbox;
  public ShootCoreCommands(Shooter shooterArg, Intake intakeArg, Hopper hopperArg, XboxController controllerArg) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    shooter = shooterArg;
    intake = intakeArg;
    hopper = hopperArg;
    xbox = controllerArg;
    addCommands(new ChargeFlywheel(shooter, xbox), new ShootAll(shooter, hopper, xbox));
  }
}
