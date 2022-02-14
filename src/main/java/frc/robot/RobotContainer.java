// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.fasterxml.jackson.databind.deser.std.ContainerDeserializerBase;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Robot.RobotMode;
import frc.robot.commands.ToggleCompressor;
import frc.robot.commands.auto.actions.MotorTest;
import frc.robot.commands.auto.actions.MoveStraight;
import frc.robot.commands.auto.actions.Pivot;
import frc.robot.commands.auto.actions.Turn;
import frc.robot.commands.auto.paths.OffTarmack_H1;
import frc.robot.commands.auto.paths.Pickup1_Shoot2_ARC_H3;
import frc.robot.commands.auto.paths.Pickup1_Shoot2_H3;
import frc.robot.commands.climb.HighbarClimbSequence;
import frc.robot.commands.drive.DriveWithJoystick;
import frc.robot.commands.intake.IntakeToggle;
import frc.robot.commands.shooter.ShootCoreCommands;
import frc.robot.commands.shooter.ToggleShooterPos;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.devices.AirCompressor;
import frc.robot.subsystems.devices.IMU;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  // Subsystems
  public final Drive driveTrain = new Drive();
  public final Intake intakeSys = new Intake();
  public final Hopper hopperSys = new Hopper(intakeSys);
  public final IMU imuSys = new IMU();
  public final AirCompressor compressorSys = new AirCompressor();
  // public final Climber climbSys = new Climber();
  public final Shooter shooterSys = new Shooter(hopperSys);

  // Miscellaneous objects
  public final PowerDistribution pdp = new PowerDistribution();
  public final PneumaticsControlModule pcm = new PneumaticsControlModule(Constants.PCM_ID);
  public final XboxController xbox = new XboxController(0);
  public final RobotMode robotMode = Robot.mode;

  public final DriveWithJoystick driveWithJoystick;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    driveWithJoystick = new DriveWithJoystick(driveTrain, xbox, pdp);
    driveWithJoystick.addRequirements(driveTrain);
    driveTrain.setDefaultCommand(driveWithJoystick);
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // new JoystickButton(xbox, Constants.A).whenPressed(new
    // MoveStraight(driveTrain, imuSys, 80, 0.5));
    new JoystickButton(xbox, Constants.A).whenPressed(new IntakeToggle(intakeSys, hopperSys));
    new POVButton(xbox, Constants.RIGHT).whenPressed(new ToggleShooterPos(shooterSys));
    // // B button shoots, Left bumper cancles
    new JoystickButton(xbox, Constants.B).whenPressed(new ShootCoreCommands(shooterSys, intakeSys, hopperSys, xbox));
    new JoystickButton(xbox, Constants.BACK).whenPressed(new ToggleCompressor(compressorSys));
    // new JoystickButton(xbox, Constants.UP).whenPressed(new
    // HighbarClimbSequence(climbSys, imuSys));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new Pickup1_Shoot2_ARC_H3(driveTrain, imuSys);

  }
}
