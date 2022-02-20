// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.Robot.RobotMode;
import frc.robot.commands.auto.paths.OffTarmack_G;
import frc.robot.commands.auto.paths.Pickup1_Shoot2_ARC_P1;
import frc.robot.commands.auto.paths.Pickup1_Shoot2_H1;
import frc.robot.commands.auto.paths.Pickup1_Shoot2_P1;
import frc.robot.commands.auto.paths.Pickup1_Shoot2_P2;
import frc.robot.commands.auto.paths.Pickup2_Shoot3_P2;
import frc.robot.commands.climb.actions.ManuallyMoveClimb;
import frc.robot.commands.climb.actions.MoveClimb;
import frc.robot.commands.climb.actions.PivotClimb;
import frc.robot.commands.climb.sequences.HighbarClimbSequence;
import frc.robot.commands.compressor.ToggleCompressor;
import frc.robot.commands.drive.DriveWithJoystick;
import frc.robot.commands.intake.ToggleIntake;
import frc.robot.commands.intake.ToggleIntakeArm;
import frc.robot.commands.shooter.ChargeThenShoot;
import frc.robot.commands.shooter.ToggleShooterMode;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.DashboardControl;
import frc.robot.subsystems.devices.AirCompressor;
import frc.robot.subsystems.devices.ClimbWatchdog;
import frc.robot.subsystems.devices.FMS_Handler;
import frc.robot.subsystems.devices.IMU;

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
  public RobotMode robotMode = Robot.mode;
  // Devices
  private final AirCompressor compressorSys = new AirCompressor();
  private final IMU imuSys = new IMU();
  private final PowerDistribution pdpSys = new PowerDistribution(Constants.PDH_ID, ModuleType.kRev);
  private final PneumaticsControlModule pcmSys = new PneumaticsControlModule(Constants.PCM_ID);
  private final XboxController xboxSys = new XboxController(0);
  // Subsystems
  private final Drive driveSys = new Drive(pdpSys);
  private final Intake intakeSys = new Intake();
  private final Hopper hopperSys = new Hopper(intakeSys);
  private final Climber climbSys = new Climber();
  private final Shooter shooterSys = new Shooter(hopperSys);
  private final FMS_Handler fmsSys = new FMS_Handler();
  private final ClimbWatchdog watchdogSys = new ClimbWatchdog(xboxSys, climbSys);
  // private Joystick joystick1 = new Joystick(Constants.XBOX_PORT);
  private final DashboardControl dashboardSys = new DashboardControl(compressorSys, shooterSys, intakeSys, pdpSys, fmsSys, climbSys);

  private final DriveWithJoystick driveWithJoystick;
  private Hopper hopperArg;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    driveWithJoystick = new DriveWithJoystick(xboxSys, pdpSys, driveSys);
    driveWithJoystick.addRequirements(driveSys);
    driveSys.setDefaultCommand(driveWithJoystick);

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
    //  MoveStraight(driveTrain, imuSys, 80, 0.5));
    new JoystickButton(xboxSys, Constants.A).whenPressed(new ToggleIntake(intakeSys, hopperSys));
    new POVButton(xboxSys, Constants.RIGHT).whenPressed(new ToggleShooterMode(shooterSys));
    new JoystickButton(xboxSys, Constants.Y).whenPressed(new PivotClimb(climbSys, watchdogSys, true));
    new JoystickButton(xboxSys, Constants.X).whenPressed(new ToggleIntakeArm(intakeSys, hopperSys));
    new JoystickButton(xboxSys, Constants.R_BUMP).whenPressed(new ManuallyMoveClimb(climbSys, xboxSys));
    // // B button shoots, Left Menu cancles
    new JoystickButton(xboxSys, Constants.B).whenPressed(new ChargeThenShoot(xboxSys, intakeSys, hopperSys, shooterSys));
    new JoystickButton(xboxSys, Constants.BACK).whenPressed(new ToggleCompressor(compressorSys));
    //new JoystickButton(xboxSys, Constants.UP).whenPressed(new HighbarClimbSequence(climbSys, imuSys, xboxSys, watchdogSys));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
//return new Pickup1_Shoot2_H3(controllerArg, driveArg, imuArg, intakeArg, hopperArg, shooterArg);
    
    int PathNum = 1; // sets auto path

    switch (PathNum) {
    case 0: return null;
    case 1: return new Pickup1_Shoot2_P1(xboxSys, driveSys, imuSys, intakeSys, hopperSys, shooterSys);
    case 2: return new Pickup1_Shoot2_ARC_P1(driveSys, imuSys);
    case 3: return new OffTarmack_G(driveSys, imuSys);
    case 4: return new Pickup1_Shoot2_H1(driveSys, imuSys, intakeSys, hopperSys, xboxSys, shooterSys);
    case 5: return new Pickup1_Shoot2_P2(driveSys, imuSys, intakeSys, hopperSys, xboxSys, shooterSys);
    case 6: return new Pickup2_Shoot3_P2(driveSys, imuSys, intakeSys, hopperSys, xboxSys, shooterSys);
    default: return null;
    }
  }
}
