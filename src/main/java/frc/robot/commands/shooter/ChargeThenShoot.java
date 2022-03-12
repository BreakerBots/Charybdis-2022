// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ChargeThenShoot extends SequentialCommandGroup {
  /** Creates a new ShootCoreCommands. */
  Shooter shooter;
  Intake intake;
  Hopper hopper;
  XboxController xbox;
  public ChargeThenShoot(XboxController controllerArg, Intake intakeArg, Hopper hopperArg, Shooter shooterArg) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    shooter = shooterArg;
    intake = intakeArg;
    hopper = hopperArg;
    xbox = controllerArg;
    addCommands(new ResetFlywheelPID(shooterArg), new ChargeFlywheel(shooter, xbox), new ShootAll(shooter, hopper, xbox, intake));
    //addCommands(new ResetFlywheelPID(shooterArg), new ChargeFlywheel(shooter, xbox));
  }
}
