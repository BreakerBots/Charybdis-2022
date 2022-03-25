// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.paths;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.DrivePivot;
import frc.robot.commands.drive.DriveStraight;
import frc.robot.commands.intake.ToggleIntake;
import frc.robot.commands.shooter.ChargeThenShoot;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.devices.IMU;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Pickup1_Shoot2_P2 extends SequentialCommandGroup {
  /** Creates a new Pickup1_Shoot2_P2. */
  public Pickup1_Shoot2_P2(Drive driveArg, IMU imuArg, Intake intakeArg, Hopper hopperArg, XboxController controllerArg, Shooter shooterArg) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new InstantCommand(intakeArg :: activateIntake),
      new DriveStraight(driveArg, imuArg, 48, 0.3, 3  ),
      new DriveStraight(driveArg, imuArg, -60, 0.3, 4),
      new ToggleIntake(intakeArg, hopperArg),
      new DrivePivot(driveArg, imuArg, 25, 0.3),
      new DriveStraight(driveArg, imuArg, -16, 0.3, 2),
      new ChargeThenShoot(controllerArg, intakeArg, hopperArg, shooterArg)
    );
  }
}
