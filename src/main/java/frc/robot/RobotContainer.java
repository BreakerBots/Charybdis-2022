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
import frc.robot.commands.autoActionCommands.MotorTest;
import frc.robot.commands.autoActionCommands.MoveStraight;
import frc.robot.commands.autoActionCommands.Pivot;
import frc.robot.commands.autoActionCommands.Turn;
import frc.robot.commands.autoPaths.OffTarmack_H1;
import frc.robot.commands.autoPaths.Pickup1_Shoot2_ARC_H3;
import frc.robot.commands.autoPaths.Pickup1_Shoot2_H3;
import frc.robot.commands.climbCommands.HighbarClimbSequence;
import frc.robot.commands.driveCommands.DriveWithJoystick;
import frc.robot.commands.intakeCommands.IntakeToggle;
import frc.robot.commands.shooterCommands.ShootCoreCommands;
import frc.robot.commands.shooterCommands.ToggleShooterPos;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Devices.AirCompressor;
import frc.robot.subsystems.Devices.IMU;
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
  public static PowerDistribution pdp = new PowerDistribution();
  public static PneumaticsControlModule pcm = new PneumaticsControlModule(Constants.PCM_ID);

  public RobotMode robotMode = Robot.mode;
  private final Drive driveTrain = new Drive(pdp);
  private final Intake intakeSys = new Intake();
  private final Hopper hopperSys = new Hopper(intakeSys);
  private final IMU imuSys = new IMU();
  private final AirCompressor compressorSys = new AirCompressor();
  // private final Climber climbSys = new Climber();
  private final Shooter shooterSys = new Shooter(hopperSys);
  private final XboxController xbox = new XboxController(0);
  // private Joystick joystick1 = new Joystick(Constants.XBOX_PORT);

  private final DriveWithJoystick driveWithJoystick;

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
