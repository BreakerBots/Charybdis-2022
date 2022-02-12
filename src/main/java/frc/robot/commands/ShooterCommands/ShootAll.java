// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooterCommands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.FlywheelState;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

/**
 * Shoots all the balls in the hopper
 */
public class ShootAll extends CommandBase {
  Shooter shooter;
  XboxController xbox;
  Hopper hopper;
  Intake intake;
  private long cycleCount;
  private long timedStopCount;

  /**
   * Creates a new ShootAll.
   * 
   * @param shooterArg    Shooter subsystem from RobotContainer.
   * @param hopperArg     Hopper subsystem from RobotContainer.
   * @param controllerArg Xbox controller from RobotContainer.
   */
  public ShootAll(Shooter shooterArg, Hopper hopperArg, XboxController controllerArg, Intake intakeArg) {
    shooter = shooterArg;
    xbox = controllerArg;
    hopper = hopperArg;
    intake = intakeArg;
    addRequirements(shooter);
    addRequirements(hopper);
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("STARTED SHOOTING");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    cycleCount++;
    if (cycleCount > 450) {
      xbox.setRumble(RumbleType.kLeftRumble, 0);
      xbox.setRumble(RumbleType.kRightRumble, 0);
    }
    if (cycleCount % 400 == 0) {
      System.out.println("PLEASE PRESS B BUTTON TO SHOOT (IF IN TELEOP)");
    }
    if (shooter.flywheelState == frc.robot.FlywheelState.CHARGED) {
      hopper.hopperOn();
      intake.lIndexerHopper();
      System.out.println("SHOOTER STARTED!");

    }
    if (hopper.getHopperPos1() == false && hopper.getHopperPos2() == false) {
      timedStopCount++;
    }
    if (hopper.getHopperPos1() == false && hopper.getHopperPos2() == false && timedStopCount == 250) {
      hopper.hopperOff();
      intake.lIndexerHopper();
      timedStopCount = 0;
      shooter.flyweelOff();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (shooter.flywheelState == FlywheelState.OFF) {
      System.out.println("HOPPER DEPLETED - SHOOTER STOPED!");
      return true;
    } else if (xbox.getLeftBumperPressed()) {
      System.out.println("SHOOTER MANUALY STOPED!");
      hopper.hopperOff();
      shooter.flyweelOff();
      return true;
    } else {
      return false;
    }
  }
}
