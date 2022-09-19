// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.paths;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.DriveStraight;
import frc.robot.commands.shooter.ChargeFlywheel;
import frc.robot.commands.shooter.ShootAll;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.devices.IMU;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html

/** Shoots balls, then taxis off the TARMAC. Load with 1-2 balls. Place right next to the HUB. */
public class Shoot_OffTarmac extends SequentialCommandGroup {
  /** Creates a new OffTarmack_H1. */
  public Shoot_OffTarmac(Drive driveArg, IMU imuArg, Intake intakeArg, Hopper hopperArg,
      XboxController controllerArg, Shooter shooterArg) {
    addCommands(
        new ChargeFlywheel(shooterArg, controllerArg),
        new ShootAll(shooterArg, hopperArg, controllerArg, intakeArg),
        new DriveStraight(driveArg, imuArg, 100, 0.3, 4));
  }
}
