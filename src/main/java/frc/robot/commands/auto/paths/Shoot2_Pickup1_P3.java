package frc.robot.commands.auto.paths;

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

import javax.crypto.AEADBadTagException;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.DrivePivot;
import frc.robot.commands.drive.DriveStraight;
import frc.robot.commands.drive.DriveTurn;
import frc.robot.commands.intake.ToggleIntake;
import frc.robot.commands.shooter.ChargeFlywheel;

import frc.robot.commands.shooter.ChargeThenShoot;
import frc.robot.commands.shooter.ShootAll;
import frc.robot.commands.shooter.ToggleShooterMode;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.devices.IMU;

public class Shoot2_Pickup1_P3 extends SequentialCommandGroup {
    /** Creates a new Pickup2_Shoot3_P2. */
    public Shoot2_Pickup1_P3(Drive driveArg, IMU imuArg, Intake intakeArg, Hopper hopperArg, XboxController controllerArg, Shooter shooterArg) {
      // Add your commands in the addCommands() call, e.g.
      // addCommands(new FooCommand(), new BarCommand());
      addCommands(
        
        new ChargeThenShoot(controllerArg, intakeArg, hopperArg, shooterArg),
       new ToggleIntake(intakeArg, hopperArg),
       new DriveStraight(driveArg, imuArg, 30, 0.4, 2.5),
       new DrivePivot(driveArg, imuArg, -25, 0.2),
       new DriveStraight(driveArg, imuArg, 40, 0.4, 2.5),
       new ToggleIntake(intakeArg, hopperArg),
       new DriveStraight(driveArg, imuArg, -40, 0.4, 2.5),
       new DrivePivot(driveArg, imuArg, 25, 0.2),
       new DriveStraight(driveArg, imuArg, -30, 0.4, 2.5),
       new ChargeThenShoot(controllerArg, intakeArg, hopperArg, shooterArg)
      );
    }
  }
  