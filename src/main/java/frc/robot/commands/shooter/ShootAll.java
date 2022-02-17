// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.FlywheelState;

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
    // STOP SETTING FLYWHEEL STATES! REWORK LOGIC!
    cycleCount++;
    if (shooter.getFlywheelState() == FlywheelState.CHARGED && shooter.flywheelPIDAtSetpoint()) {
      shooter.isShooting = true;
      hopper.activateHopper();
      intake.toggleHopperFeed();
      System.out.println("SHOOTER STARTED!");
    }
    System.out.println(shooter.getFlywheelTPS());
    if (!hopper.slot2IsFull() && !hopper.slot1IsFull()) { //&& hopper.slot1IsFull() == false
      timedStopCount++;
    }
    if (!hopper.slot2IsFull() && !hopper.slot1IsFull() && timedStopCount > 150) { // && hopper.slot1IsFull() == false 
      hopper.deactivateHopper();
      intake.deactivateIntake();
      timedStopCount = 0;
      shooter.setOff();
      shooter.setFlywheelState(FlywheelState.OFF);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.resetFlywheelPID();
    cycleCount = 0;
    shooter.isShooting = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (shooter.getFlywheelState() == FlywheelState.OFF) {
      System.out.println("HOPPER DEPLETED - SHOOTER STOPED!");
      return true;
    } else if (xbox.getStartButtonPressed()) {
      System.out.println("SHOOTER MANUALY STOPED!");
      hopper.deactivateHopper();
      shooter.setOff();
      intake.deactivateIntake();
      return true;
    } else if (cycleCount > 400) {
      System.out.println("SHOOTER TIMED OUT!");
      hopper.deactivateHopper();
      shooter.setOff();
      intake.deactivateIntake();
      return true;
    } else {
      return false;
    }
  }
}
